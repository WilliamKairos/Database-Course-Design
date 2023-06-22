<template>
  <div class="application-container">
    <h2>奖学金申请</h2>
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="申请编号">
        <el-input v-model="form.applicationId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="学号">
        <el-input v-model="form.studentId"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="form.grade"></el-input>
      </el-form-item>
      <el-form-item label="专业">
        <el-input v-model="form.major"></el-input>
      </el-form-item>
      <el-form-item label="奖学金种类">
        <el-select v-model="form.scholarshipType" placeholder="请选择">
          <!-- options -->
        </el-select>
      </el-form-item>
      <el-form-item label="申请材料">
        <el-upload action="#" :on-success="handleUploadSuccess">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="申请时间">
        <el-date-picker v-model="form.applicationDate" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        applicationId: '自动生成的ID', // 模拟自动生成
        studentId: '',
        name: '',
        grade: '',
        major: '',
        scholarshipType: '',
        applicationDate: ''
      }
    };
  },
  methods: {
    submitForm(formName) {
      // 在这里提交表单到后端
    },
    handleUploadSuccess(response, file) {
      // 处理文件上传成功的情况
    }
  }
};
</script>

<style>
.application-container {
  margin: 20px;
}
</style>
