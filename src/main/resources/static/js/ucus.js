var ucusListesi = [];
var rotaListesi = [];
var paraBirimiListesi = ['TRY','USD','EUR'];

function getirUcus  (id) {
  return ucusListesi[findById(id)];
}

function findById (id) {
  for (var key = 0; key < ucusListesi.length; key++) {
    if (ucusListesi[key].id == id) {
      return key;
    }
  }
}

var ucusServis = {
  getirTumRota(fn) {
    axios
        .get('/api/v1/rota')
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },

  getirTumUcus(fn) {
    axios
      .get('/api/v1/ucus')
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  getirUcus(id, fn) {
    axios
      .get('/api/v1/ucus/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  kaydetUcus(ucus, fn) {
    axios
      .post('/api/v1/ucus', ucus)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  silUcus(id, fn) {
    axios
      .delete('/api/v1/ucus/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  }
}

var List = Vue.extend({
  template: '#ucus-list',
  data: function () {
    return {ucusListesi: [], aramaAnahtari: ''};
  },
  computed: {
    filtreleUcus() {
      return this.ucusListesi.filter((ucus) => {
      	return ucus.ucusNo.indexOf(this.aramaAnahtari) > -1
      })
    }
  },
  mounted() {
    ucusServis.getirTumUcus(r => {this.ucusListesi = r.data; ucusListesi = r.data})
  }
});

var Ucus = Vue.extend({
  template: '#ucus',
  data: function () {
    return {ucus: getirUcus(this.$route.params.ucus_id)};
  }
});

var UcusDuzenle = Vue.extend({
  template: '#ucus-duzenle',
  data: function () {
    return {
      ucus: getirUcus(this.$route.params.ucus_id),
      rotaListesi : ucusServis.getirTumRota(r => {this.rotaListesi = r.data; rotaListesi = r.data}),
      paraBirimiListesi : ['TRYgetirUcus','USD','EUR']
    }
  },
  methods: {
    kaydetUcus: function () {
      ucusServis.kaydetUcus(this.ucus, r => router.push('/'))
    }
  }
});

var UcusSil = Vue.extend({
  template: '#ucus-sil',
  data: function () {
    return {ucus: getirUcus(this.$route.params.ucus_id)};
  },
  methods: {
    silUcus: function () {
      ucusServis.silUcus(this.ucus.id, r => router.push('/'))
    }
  }
});

var UcusKaydet = Vue.extend({
  template: '#ucus-kaydet',
  data() {
    return {
      ucus: {ucusNo: null, rota: null, ucusTarihi: null, fiyat: 0, paraBirimi: null, koltukSayisi: null},
      rotaListesi : ucusServis.getirTumRota(r => {this.rotaListesi = r.data; rotaListesi = r.data}),
      paraBirimiListesi : ['TRY','USD','EUR']
    }
  },
  methods: {
    kaydetUcus() {
      ucusServis.kaydetUcus(this.ucus, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: List},
		{path: '/ucus/:ucus_id', component: Ucus, name: 'ucus'},
		{path: '/ucus-kaydet', component: UcusKaydet},
		{path: '/ucus/:ucus_id/edit', component: UcusDuzenle, name: 'ucus-duzenle'},
		{path: '/ucus/:ucus_id/delete', component: UcusSil, name: 'ucus-sil'}
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
