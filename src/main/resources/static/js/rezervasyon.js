var rezervasyonListesi = [];

function getirRezervasyon  (id) {
  return rezervasyonListesi[findById(id)];
}

function findById (id) {
  for (var key = 0; key < rezervasyonListesi.length; key++) {
    if (rezervasyonListesi[key].id == id) {
      return key;
    }
  }
}

var rezervasyonServis = {
  getirTumRezervasyon(fn) {
    axios
      .get('/api/v1/rezervasyon')
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  getirRezervasyon(id, fn) {
    axios
      .get('/api/v1/rezervasyon/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  silRezervasyon(id, fn) {
    axios
      .delete('/api/v1/rezervasyon/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  }
}

var List = Vue.extend({
  template: '#rezervasyon-list',
  data: function () {
    return {rezervasyonListesi: [], aramaAnahtari: ''};
  },
  computed: {
    filtreleRezervasyon() {
      return this.rezervasyonListesi.filter((rezervasyon) => {
      	return rezervasyon.pnrNo.indexOf(this.aramaAnahtari) > -1
      	  || rezervasyon.musteri.adi.indexOf(this.aramaAnahtari) > -1
      	  || rezervasyon.musteri.soyadi.indexOf(this.aramaAnahtari) > -1
            || rezervasyon.ucus.ucusNo.indexOf(this.aramaAnahtari) > -1
      })
    }
  },
  mounted() {
    rezervasyonServis.getirTumRezervasyon(r => {this.rezervasyonListesi = r.data; rezervasyonListesi = r.data})
  }
});

var Rezervasyon = Vue.extend({
  template: '#rezervasyon',
  data: function () {
    return {rezervasyon: getirRezervasyon(this.$route.params.rezervasyon_id)};
  }
});


var RezervasyonSil = Vue.extend({
  template: '#rezervasyon-sil',
  data: function () {
    return {rezervasyon: getirRezervasyon(this.$route.params.rezervasyon_id)};
  },
  methods: {
    silRezervasyon: function () {
      rezervasyonServis.silRezervasyon(this.rezervasyon.id, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: List},
		{path: '/rezervasyon/:rezervasyon_id', component: Rezervasyon, name: 'rezervasyon'},
		{path: '/rezervasyon/:rezervasyon_id/delete', component: RezervasyonSil, name: 'rezervasyon-sil'}
	]
});

var formatter = {
  dateFormat: function (value) {
    if (value) {
      if (value.length > 5){
        return moment(value.slice(0, 5)).format('DD.MM.YYYY HH:mm')
      }else if(value.length === 5){
        return moment(value).format('DD.MM.YYYY HH:mm')
      }else{
        return '';
      }

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
