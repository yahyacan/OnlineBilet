var rotaListesi = [];
var havayoluListesi = [];
var havalimaniListesi = [];

function getirRota  (id) {
  return rotaListesi[findById(id)];
}

function findById (id) {
  for (var key = 0; key < rotaListesi.length; key++) {
    if (rotaListesi[key].id == id) {
      return key;
    }
  }
}

var rotaServis = {
  getirTumHavalimani(fn) {
    axios
        .get('/api/v1/havalimani')
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },
  getirTumHavayolu(fn) {
    axios
        .get('/api/v1/havayolu')
        .then(response => fn(response.data))
        .catch(error => console.log(error))
  },

  getirTumRota(fn) {
    axios
      .get('/api/v1/rota')
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  getirRota(id, fn) {
    axios
      .get('/api/v1/rota/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  kaydetRota(rota, fn) {
    axios
      .post('/api/v1/rota', rota)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  silRota(id, fn) {
    axios
      .delete('/api/v1/rota/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  }
}

var List = Vue.extend({
  template: '#rota-list',
  data: function () {
    return {rotaListesi: [], aramaAnahtari: ''};
  },
  computed: {
    filtreleRota() {
      return this.rotaListesi.filter((rota) => {
      	return rota.nereden.adi.indexOf(this.aramaAnahtari) > -1
      	  || rota.nereye.adi.indexOf(this.aramaAnahtari) > -1
      })
    }
  },
  mounted() {
    rotaServis.getirTumRota(r => {this.rotaListesi = r.data; rotaListesi = r.data})
  }
});

var Rota = Vue.extend({
  template: '#rota',
  data: function () {
    return {rota: getirRota(this.$route.params.rota_id)};
  }
});

var RotaDuzenle = Vue.extend({
  template: '#rota-duzenle',
  data: function () {
    return {
      rota: getirRota(this.$route.params.rota_id),
      havayoluListesi : rotaServis.getirTumHavayolu(r => {this.havayoluListesi = r.data; havayoluListesi = r.data}),
      havalimaniListesi : rotaServis.getirTumHavalimani(r => {this.havalimaniListesi = r.data; havalimaniListesi = r.data})
    }
  },
  methods: {
    kaydetRota: function () {
      rotaServis.kaydetRota(this.rota, r => router.push('/'))
    }
  }
});

var RotaSil = Vue.extend({
  template: '#rota-sil',
  data: function () {
    return {rota: getirRota(this.$route.params.rota_id)};
  },
  methods: {
    silRota: function () {
      rotaServis.silRota(this.rota.id, r => router.push('/'))
    }
  }
});

var RotaKaydet = Vue.extend({
  template: '#rota-kaydet',
  data() {
    return {
      rota: {havayolu: null, nereden: null, nereye: null, mesafe: 0},
      havayoluListesi : rotaServis.getirTumHavayolu(r => {this.havayoluListesi = r.data; havayoluListesi = r.data}),
      havalimaniListesi : rotaServis.getirTumHavalimani(r => {this.havalimaniListesi = r.data; havalimaniListesi = r.data})
    }
  },
  methods: {
    kaydetRota() {
      rotaServis.kaydetRota(this.rota, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: List},
		{path: '/rota/:rota_id', component: Rota, name: 'rota'},
		{path: '/rota-kaydet', component: RotaKaydet},
		{path: '/rota/:rota_id/edit', component: RotaDuzenle, name: 'rota-duzenle'},
		{path: '/rota/:rota_id/delete', component: RotaSil, name: 'rota-sil'}
	]
});

new Vue({
  router
}).$mount('#app')
