var ucusArama = {};
var ucusListesi = [];
var neredenhavalimaniListesi = [];
var nereyehavalimaniListesi = [];

var ucusServis = {
  getirTumHavalimani(fn) {
    axios
        .get('/api/v1/havalimani')
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },
  getirHavalimaniByNereden(havalimaniKodu,fn) {
    axios
        .get('/api/v1/havalimaniByNereden/'+ havalimaniKodu)
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },
  getirUcusByNeredenNereyeTarih(ucusArama, fn) {
    axios
        .post('/api/v1/getirUcusByNeredenNereyeTarih', ucusArama)
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },
  kaydetRezervasyon(rezervasyon, fn) {
    axios
        .post('/api/v1/rezervasyon', rezervasyon)
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },
}

var UcusList = Vue.extend({
  template: '#ucus-list',
  data: function () {
    return {
      ucusListesi : ucusServis.getirUcusByNeredenNereyeTarih(this.$route.params.ucusArama, r => {this.ucusListesi = r.data; ucusListesi = r.data;} )
    };
  }
});

var UcusArama = Vue.extend({
  template: '#ucus-arama',
  data: function () {
    return {
      ucusArama: {nereden: null, nereye: null, ucusTarihi: null},
      nereyehavalimaniListesi : [],
      neredenhavalimaniListesi : ucusServis.getirTumHavalimani(r => {this.neredenhavalimaniListesi = r.data; neredenhavalimaniListesi = r.data}),
    };
  },
  methods: {
    degistirNereden: function () {
      nereyehavalimaniListesi : ucusServis.getirHavalimaniByNereden(this.ucusArama.nereden.kodu, r => {this.nereyehavalimaniListesi = r.data; nereyehavalimaniListesi = r.data})
    }
  }
});

var UcusRezervasyon = Vue.extend({
  template: '#ucus-rezervasyon',
  data: function () {
    return {
      rezervasyon: {musteri: {}, ucus: this.$route.params.ucus},
      cinsiyetListesi : ['Bay','Bayan'],
      uyrukListesi : ['TC','Diğer']
    };
  },
  methods: {
    kaydetRezervasyon() {
      ucusServis.kaydetRezervasyon(this.rezervasyon, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: UcusArama},
        {path: '/ucus-list/:ucusArama', component: UcusList, name: 'ucus-list'},
        {path: '/ucus-rezervasyon/:ucus', component: UcusRezervasyon, name: 'ucus-rezervasyon'}
	]
});

var formatter = {
  dateFormat: function (value) {
    if (value) {
      return moment(value).format('DD.MM.YYYY HH:mm')
    }
  },
  date: function (value, format) {
    if (value) {
      return moment(String(value)).format(format || 'MM/DD/YY')
    }
  },
  time: function (value, format) {
    if (value) {
      return moment(String(value)).format(format || 'h:mm A');
    }
  }
};

Vue.component('format', {
  template: '<span>{{ formatter[fn](value, format) }}</span>',
  props: ['value', 'fn', 'format'],
  computed: {
    formatter() {
      return formatter;
    }
  }
});

new Vue({
  router
}).$mount('#app')
