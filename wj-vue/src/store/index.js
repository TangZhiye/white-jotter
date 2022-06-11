import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // user: {
    //   username: window.localStorage.getItem('user') === null ? '' : JSON.parse(window.localStorage.getItem('user')).username
    //   // username: window.sessionStorage.getItem('user') === null ? '' : JSON.parse(window.sessionStorage.getItem('user')).username
    // }
    username: window.localStorage.getItem('username') === null ? '' : window.localStorage.getItem('username')

  },
  mutations: {
    login (state, username) {
      state.username = username
      window.localStorage.setItem('username', username)
      // window.sessionStorage.setItem('user', JSON.stringify(user))
    },
    logout (state) {
      state.username = ''
      window.localStorage.removeItem('username')
    }
  }
})
