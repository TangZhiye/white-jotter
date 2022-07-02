<template>
  <el-card class="admin-header">
    <a href="/index">
      <img src="../../assets/img/icon/icon2.png" alt="" width="55px" style="float: left;margin-top: -5px;">
    </a>
    <span style="font-size: 32px;font-weight: bold;position:absolute;left: 100px">白  卷</span>
    <i class="el-icon-switch-button" v-on:click="logout" style="font-size: 40px;float: right"></i>
  </el-card>
</template>

<script>
export default {
  name: 'Header',
  methods: {
    logout () {
      const _this = this
      this.$confirm('确定登出账号吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.get('/logout')
          .then(resp => {
            if (resp && resp.status === 200) {
              _this.$store.commit('logout')
              this.$message({
                type: 'success',
                message: resp.data.result
              })
              _this.$router.replace('/login')
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消登出'
        })
      })
    }
  }
}
</script>

<style scoped>
  .admin-header {
    height: 80px;
    opacity: 0.85;
    line-height: 40px;
    min-width: 900px;
  }
  .el-icon-switch-button {
    cursor: pointer;
    outline:0;
  }
</style>
