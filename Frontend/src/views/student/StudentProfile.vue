<template>
  <div class="profile-container">
    <el-form :model="profileForm" ref="profileForm" label-width="120px">

      <!-- 学号 -->
      <el-form-item label="学号" prop="studentId" :rules="[{ required: true, message: '请输入学号', trigger: 'blur' }]">
        <el-input v-model="profileForm.studentId"></el-input>
      </el-form-item>

      <!-- 学生姓名 -->
      <el-form-item label="姓名" prop="name" :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]">
        <el-input v-model="profileForm.name"></el-input>
      </el-form-item>

      <!-- 性别 -->
      <el-form-item label="性别" prop="gender" :rules="[{ required: true, message: '请选择性别', trigger: 'change' }]">
        <el-radio-group v-model="profileForm.gender">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 手机号码 -->
      <el-form-item label="手机号码" prop="phone" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' }]">
        <el-input v-model="profileForm.phone"></el-input>
      </el-form-item>

      <!-- 邮箱 -->
      <el-form-item label="邮箱" prop="email" :rules="[{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '请输入有效的邮箱', trigger: ['blur', 'change'] }]">
        <el-input v-model="profileForm.email"></el-input>
      </el-form-item>

      <!-- 年级 -->
      <el-form-item label="年级" prop="grade" :rules="[{ required: true, message: '请选择年级', trigger: 'change' }]">
        <el-select v-model="profileForm.grade">
          <el-option label="研一" value="研一"></el-option>
          <el-option label="研二" value="研二"></el-option>
          <el-option label="研三" value="研三"></el-option>
        </el-select>
      </el-form-item>

      <!-- 专业 -->
      <el-form-item label="专业" prop="major" :rules="[{ required: true, message: '请输入专业', trigger: 'blur' }]">
        <el-input v-model="profileForm.major"></el-input>
      </el-form-item>

      <!-- 修改密码 -->
      <el-form-item label="修改密码" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
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
    handleSubmit() {
      this.$refs.profileForm.validate((valid) => {
        if (valid) {
          // 这里提交表单，调用API更新个人信息
        } else {
          console.log('表单校验不通过');
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
</style>

