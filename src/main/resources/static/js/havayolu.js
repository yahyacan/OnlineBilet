var havayoluListesi = [];

function getirHavayolu  (id) {
  return havayoluListesi[findById(id)];
}

function findById (id) {
  for (var key = 0; key < havayoluListesi.length; key++) {
    if (havayoluListesi[key].id == id) {
      return key;
    }
  }
}

var havayoluServis = {
  getirTumHavayolu(fn) {
    axios
      .get('/api/v1/havayolu')
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  getirHavayolu(id, fn) {
    axios
      .get('/api/v1/havayolu/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  kaydetHavayolu(havayolu, fn) {
    axios
      .post('/api/v1/havayolu', havayolu)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  },

  silHavayolu(id, fn) {
    axios
      .delete('/api/v1/havayolu/' + id)
      .then(response => fn(response.data))
      .catch(error => console.log(error))
  }
}

var List = Vue.extend({
  template: '#havayolu-list',
  data: function () {
    return {havayoluListesi: [], aramaAnahtari: ''};
  },
  computed: {
    filtreleHavayolu() {
      return this.havayoluListesi.filter((havayolu) => {
      	return havayolu.adi.indexOf(this.aramaAnahtari) > -1
      	  || havayolu.ucusKodu.indexOf(this.aramaAnahtari) > -1
      })
    }
  },
  mounted() {
    havayoluServis.getirTumHavayolu(r => {this.havayoluListesi = r.data; havayoluListesi = r.data})
  }
});

var Havayolu = Vue.extend({
  template: '#havayolu',
  data: function () {
    return {havayolu: getirHavayolu(this.$route.params.havayolu_id)};
  }
});

var HavayoluDuzenle = Vue.extend({
  template: '#havayolu-duzenle',
  data: function () {
    return {havayolu: getirHavayolu(this.$route.params.havayolu_id)};
  },
  methods: {
    kaydetHavayolu: function () {
      havayoluServis.kaydetHavayolu(this.havayolu, r => router.push('/'))
    }
  }
});

var HavayoluSil = Vue.extend({
  template: '#havayolu-sil',
  data: function () {
    return {havayolu: getirHavayolu(this.$route.params.havayolu_id)};
  },
  methods: {
    silHavayolu: function () {
      havayoluServis.silHavayolu(this.havayolu.id, r => router.push('/'))
    }
  }
});

var HavayoluKaydet = Vue.extend({
  template: '#havayolu-kaydet',
  data() {
    return {
      havayolu: {kodu: '', adi: ''}
    }
  },
  methods: {
    kaydetHavayolu() {
      havayoluServis.kaydetHavayolu(this.havayolu, r => router.push('/'))
    }
  }
});

var router = new VueRouter({
	routes: [
		{path: '/', component: List},
		{path: '/havayolu/:havayolu_id', component: Havayolu, name: 'havayolu'},
		{path: '/havayolu-kaydet', component: HavayoluKaydet},
		{path: '/havayolu/:havayolu_id/edit', component: HavayoluDuzenle, name: 'havayolu-duzenle'},
		{path: '/havayolu/:havayolu_id/delete', component: HavayoluSil, name: 'havayolu-sil'}
	]
});

new Vue({
  router
}).$mount('#app')
