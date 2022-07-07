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
  // 只在用户已登录且访问以 /admin 开头的路径时请求一次菜单信息，并储存在 store 中
  if (store.state.username && to.path.startsWith('/admin')) {
    initAdminMenu(router, store)
  }
  // 已登录状态下访问 login 页面直接跳转到后台首页
  if (store.state.username && to.path.startsWith('/login')) {
    next({
      path: 'admin/dashboard'
    })
  }
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

const initAdminMenu = (router, store) => {
  // 防止重复触发加载菜单操作
  if (store.state.adminMenus.length > 0) {
    return
  }
  axios.get('/menu').then(resp => {
    if (resp && resp.status === 200) {
      // console.log(resp.data)
      var fmtRoutes = formatRoutes(resp.data)
      // console.log(fmtRoutes)
      // 向路由表中添加信息
      router.addRoutes(fmtRoutes)
      store.commit('initAdminMenu', fmtRoutes)
      // console.log(store.state.adminMenus)
    }
  })
}

// 将后端数据转化成 前端路由（router）与导航菜单需要的信息
const formatRoutes = (routes) => {
  var fmtRoutes = []
  // 遍历从后端获取的菜单列表，首先判断一条菜单项是否含子项，如果含则进行递归处理
  routes.forEach(route => {
    if (route.children) {
      route.children = formatRoutes(route.children)
    }
    let fmtRoute = {
      path: route.path,
      name: route.name,
      nameZh: route.nameZh,
      iconCls: route.iconCls,
      //  component 这个属性是一个对象，因此需要根据名称做出解析（即获取对象引用）。
      //  同时我们需要把组件导入进来，因此可以利用 Vue 的异步组件加载机制（也叫懒加载），在解析的同时完成导入。
      component: resolve => {
        require(['./components/admin/' + route.component + '.vue'], resolve)
      },
      meta: {
        requireAuth: true
      },
      children: route.children
    }
    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
