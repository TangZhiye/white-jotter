<template>
  <div>
    <nav-menu></nav-menu>
<!--    用于动态显示子路由的组件-->
    <router-view/>
  </div>
</template>

<script>
import NavMenu from './common/NavMenu'
export default {
  name: 'Home',
  components: {NavMenu}
}
</script>

<style scoped>

</style>
