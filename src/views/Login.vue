<template>
  <div class="login-container">
    <el-input placeholder="请输入账号" v-model="username" class="input-field"></el-input>
    <el-input placeholder="请输入密码" v-model="password" show-password class="input-field"></el-input>
    <div class="radio-group">
      <label>
        <input type="radio" value="student" v-model="userType" /> 学生
      </label>
      <label>
        <input type="radio" value="admin" v-model="userType" /> 管理员
      </label>
    </div>
    <el-button type="primary" @click="login">登录</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      userType: 'student',
    };
  },
  methods: {
    login() {
      // 这里应该调用后端API进行验证
      localStorage.setItem('userType', this.userType);
      const redirect = this.$route.query.redirect;
      if (this.userType === 'admin') {
        this.$router.push(redirect || '/admin');
      } else {
        this.$router.push(redirect || '/student');
      }
    },
  },
};
</script>

<style>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  flex-direction: column;
}

.radio-group {
  margin-bottom: 10px;
}

.input-field {
  width: 200px;
}
</style>
