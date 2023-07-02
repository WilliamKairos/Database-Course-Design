<template>
  <div class="login-container">
    <h1 class="title">高校奖学金系统</h1>
    <div class="login-form">
      <el-input v-model="username" placeholder="请输入账号" class="input-field"></el-input>
      <el-input v-model="password" placeholder="请输入密码" show-password class="input-field"></el-input>
      <div class="login-button-container">
        <el-button type="primary" @click="login" class="login-button">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

export default {
  setup() {
    const username = ref('');
    const password = ref('');
    const route = useRoute();
    const router = useRouter();

    const login = async () => {
      try {
        const response = await axios.post(
            'http://localhost:8080/login/login',
            {
              username: username.value,
              password: password.value,
            },
            {
              headers: {
                'Content-Type': 'application/json',
              },
            }
        );

        if (response.status === 200) {
          // 登录成功
          const user = response.data.data; // 获取用户信息
          console.log(user);
          localStorage.setItem('userType', user.userType);
          localStorage.setItem('studentId', user.id);

          console.log(localStorage.getItem('studentId'))

          const redirect = route.query.redirect;

          if (user.userType === 'admin') {
            router.push(redirect || '/admin');
          } else {
            router.push(redirect || '/student/profile');
          }
        } else {
          // 登录失败
          alert('登录失败');
        }
      } catch (error) {
        console.error(error);
        if (error.response && error.response.status === 401) {
          alert('登录失败');
        } else {
          alert('发生错误');
        }
      }
    };

    return {
      username,
      password,
      login,
    };
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

.title {
  font-size: 24px;
  margin-bottom: 20px;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.input-field {
  width: 200px;
  margin-bottom: 10px;
}

.login-button-container {
  display: flex;
  justify-content: center;
}

.login-button {
  width: 100px;
}
</style>
