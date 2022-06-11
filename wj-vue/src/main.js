// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import md5 from 'js-md5'

// 设置反向代理，前端请求默认发送到 http://localhost:8443/api
const axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'
// 允许跨域携带cookie信息
axios.defaults.withCredentials = true
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.prototype.$md5 = md5

Vue.use(ElementUI)

router.beforeEach((to, from, next) => {
  // 对每次请求，如果前端没有登录信息则直接前端拦截，如果有则判断后端是否正常登录（防止前端构造参数绕过）
  if (to.meta.requireAuth) {
    if (store.state.username) {
      axios.get('/authentication').then(resp => {
        if (resp.data) {
          next()
        } else {
          next({
            path: 'login',
            query: {redirect: to.fullPath}
          })
        }
      })
    } else {
      next({
        path: 'login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
