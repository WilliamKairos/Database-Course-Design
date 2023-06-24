<template>
  <div class="login-container">
    <div class="radio-group">
      <label>
        <input type="radio" value="student" v-model="userType"/> 学生
      </label>
      <label>
        <input type="radio" value="admin" v-model="userType"/> 管理员
      </label>
    </div>
    <el-input placeholder="请输入账号" v-model="username" class="input-field"></el-input>
    <el-input placeholder="请输入密码" v-model="password" show-password class="input-field"></el-input>
    <el-button type="primary" @click="login">登录</el-button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      username: '',
      password: '',
      userType: 'student',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('http://localhost:8080/login', {
          username: this.username,
          password: this.password
        });

        if (response.status === 200) {
          // 登录成功
          const user = response.data; // 获取用户信息
          localStorage.setItem('userType', this.userType);
          const redirect = this.$route.query.redirect;

          if (this.userType === 'admin') {
            this.$router.push(redirect || '/admin');
          } else {
            this.$router.push(redirect || '/student/profile');
          }
        } else {
          // 登录失败
          alert('Login failed');
        }
      } catch (error) {
        console.error(error);
        alert('An error occurred');

      }
    }
  }
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
