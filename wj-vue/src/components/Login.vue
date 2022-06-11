<template>
  <body id="poster">
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login-title">系统登录</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" style="width: 100%;background: lightsteelblue; border: none" v-on:click="register">注册</el-button>
      </el-form-item>
      <el-footer>
        <el-checkbox v-model="rememberMe" style="float: right">十天内记住我</el-checkbox>
      </el-footer>
    </el-form>
  </body>

</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: 'admin',
        password: '123'
      },
      responseResult: [],
      rememberMe: false
    }
  },
  methods: {
    login () {
      var _this = this
      // console.log(this.$store.state)
      this.$axios.post('/login?rememberMe=' + this.rememberMe, {
        username: this.loginForm.username,
        password: this.$md5(this.loginForm.password)
      })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.$store.commit('login', _this.loginForm.username)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
            // this.$router.replace({path: '/index'})
          } else {
            this.$message({
              message: resp.data.message,
              type: 'warning'
            })
          }
        })
        .catch(resp => {
          this.$message({
            message: resp.data.message,
            type: 'warning'
          })
        })
    },
    register () {
      this.$router.push({path: '/register'})
    }
  }
}
</script>

<style scoped>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width:350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login-title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  #poster {
    background: url("../assets/img/bg/eva1.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: 0px;
  }
</style>
