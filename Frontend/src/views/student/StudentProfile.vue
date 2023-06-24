<template>
  <div class="profile-container">
    <el-form :model="profileForm" ref="profileForm" label-width="120px">

      <!-- 学号 -->
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="profileForm.studentId"></el-input>
      </el-form-item>

      <!-- 头像 -->
      <el-form-item label="头像">
        <el-upload
            action="upload-url"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <el-button size="small" type="primary">上传头像</el-button>
        </el-upload>
        <img :src="profileForm.avatar" alt="avatar" class="avatar-preview">
      </el-form-item>

      <!-- 学生姓名 -->
      <el-form-item label="姓名" prop="name">
        <el-input v-model="profileForm.name"></el-input>
      </el-form-item>

      <!-- 性别 -->
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="profileForm.gender">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 手机号码 -->
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="profileForm.phone"></el-input>
      </el-form-item>

      <!-- 邮箱 -->
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="profileForm.email"></el-input>
      </el-form-item>

      <!-- 年级 -->
      <el-form-item label="年级" prop="grade">
        <el-input v-model="profileForm.grade"></el-input>
      </el-form-item>

      <!-- 专业 -->
      <el-form-item label="专业" prop="major">
        <el-input v-model="profileForm.major"></el-input>
      </el-form-item>

      <!-- 修改密码 -->
      <el-form-item label="修改密码" prop="password">
        <el-input type="password" v-model="profileForm.password" auto-complete="off"></el-input>
      </el-form-item>

      <!-- 提交按钮 -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profileForm: {
        studentId: '',
        avatar: '',
        name: '',
        gender: '',
        phone: '',
        email: '',
        grade: '',
        major: '',
        password: ''
      }
    };
  },
  methods: {
    handleAvatarSuccess(response) {
      this.profileForm.avatar = response.url;
    },
    handleSubmit() {
      this.$refs.profileForm.validate((valid) => {
        if (valid) {
          // 这里提交表单，调用API更新个人信息
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
};
</script>

<style>
.profile-container {
  margin: 20px;
}

.avatar-preview {
  width: 50px;
  height: 50px;
  margin-left: 10px;
}
</style>