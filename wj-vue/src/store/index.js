import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      // username: window.localStorage.getItem('user') === null ? '' : JSON.parse(window.localStorage.getItem('user')).username
      username: window.sessionStorage.getItem('user') === null ? '' : JSON.parse(window.sessionStorage.getItem('user')).username
    }
  },
  mutations: {
    login (state, user) {
      state.user = user
      // window.localStorage.setItem('user', JSON.stringify(user))
      window.sessionStorage.setItem('user', JSON.stringify(user))
    }
  }
})
