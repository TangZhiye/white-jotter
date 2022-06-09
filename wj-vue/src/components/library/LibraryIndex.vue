<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main>
      <Books class="books-area" ref="books"></Books>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Books from './Books'
export default {
  name: 'LibraryIndex',
  components: {Books, SideMenu},
  methods: {
    listByCategory () {
      const _this = this
      this.$axios.get('/books/category/' + _this.$refs.sideMenu.cid)
        .then(resp => {
          if (resp && resp.status === 200) {
            _this.$refs.books.books = resp.data
          }
        })
    }
  }
}
</script>

<style scoped>
  .books-area {
    /*width: 990px;*/
    width: 100%;
    margin-left: auto;
    margin-right: auto;
  }
</style>
