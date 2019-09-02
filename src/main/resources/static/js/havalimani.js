var havalimaniListesi = [];

function getirHavalimani  (id) {
  return havalimaniListesi[findById(id)];
}

function findById (id) {
  for (var key = 0; key < havalimaniListesi.length; key++) {
    if (havalimaniListesi[key].id == id) {
      return key;
    }
  }
}

var havalimaniServis = {
  getirTumHavalimani(fn) {
    axios
      .get('/api/v1/havalimani')
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  getirHavalimani(id, fn) {
    axios
      .get('/api/v1/havalimani/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  kaydetHavalimani(havalimani, fn) {
    axios
      .post('/api/v1/havalimani', havalimani)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  silHavalimani(id, fn) {
    axios
      .delete('/api/v1/havalimani/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  }
}

var List = Vue.extend({
  template: '#havalimani-list',
  data: function () {
    return {havalimaniListesi: [], aramaAnahtari: ''};
  },
  computed: {
    filtreleHavalimani() {
      return this.havalimaniListesi.filter((havalimani) => {
      	return havalimani.kodu.indexOf(this.aramaAnahtari) > -1
      	  || havalimani.adi.indexOf(this.aramaAnahtari) > -1
      	  || havalimani.sehir.indexOf(this.aramaAnahtari) > -1
            || havalimani.ulke.indexOf(this.aramaAnahtari) > -1
      })
    }
  },
  mounted() {
    havalimaniServis.getirTumHavalimani(r => {this.havalimaniListesi = r.data; havalimaniListesi = r.data})
  }
});

var Havalimani = Vue.extend({
  template: '#havalimani',
  data: function () {
    return {havalimani: getirHavalimani(this.$route.params.havalimani_id)};
  }
});

var HavalimaniDuzenle = Vue.extend({
  template: '#havalimani-duzenle',
  data: function () {
    return {havalimani: getirHavalimani(this.$route.params.havalimani_id)};
  },
  methods: {
    kaydetHavalimani: function () {
      havalimaniServis.kaydetHavalimani(this.havalimani, r => router.push('/'))
    }
  }
});

var HavalimaniSil = Vue.extend({
  template: '#havalimani-sil',
  data: function () {
    return {havalimani: getirHavalimani(this.$route.params.havalimani_id)};
  },
  methods: {
    silHavalimani: function () {
      havalimaniServis.silHavalimani(this.havalimani.id, r => router.push('/'))
    }
  }
});

var HavalimaniKaydet = Vue.extend({
  template: '#havalimani-kaydet',
  data() {
    return {
      havalimani: {kodu: '', adi: '', ulke: '', sehir: ''}
    }
  },
  methods: {
    kaydetHavalimani() {
      havalimaniServis.kaydetHavalimani(this.havalimani, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: List},
		{path: '/havalimani/:havalimani_id', component: Havalimani, name: 'havalimani'},
		{path: '/havalimani-kaydet', component: HavalimaniKaydet},
		{path: '/havalimani/:havalimani_id/edit', component: HavalimaniDuzenle, name: 'havalimani-duzenle'},
		{path: '/havalimani/:havalimani_id/delete', component: HavalimaniSil, name: 'havalimani-sil'}
	]
});

new Vue({
  router
}).$mount('#app')
