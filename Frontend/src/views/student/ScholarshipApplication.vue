<template>
  <div class="application-container">
    <h2>奖学金申请</h2>
    <el-form ref="form" :model="form" label-width="120px" :rules="rules">
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="form.studentId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-input v-model="form.grade" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input v-model="form.major" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="奖学金种类" prop="scholarshipType">
        <el-select v-model="form.scholarshipType" placeholder="请选择">
          <el-option label="国家奖学金" value="国家奖学金"></el-option>
          <el-option label="省级奖学金" value="省级奖学金"></el-option>
          <el-option label="校级奖学金" value="校级奖学金"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="申请材料" prop="applicationMaterial">
        <el-upload action="#" :on-success="handleUploadSuccess">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="申请时间" prop="applicationDate">
        <el-date-picker v-model="form.applicationDate" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      form: {
        studentId: '',
        name: '',
        grade: '',
        major: '',
        scholarshipType: '',
        applicationMaterial: '',
        applicationDate: '',
      },
      rules: {
        scholarshipType: [{ required: true, message: '请选择奖学金种类', trigger: 'change' }],
        // applicationMaterial: [{ required: true, message: '请上传申请材料', trigger: 'change' }],
        applicationDate: [{ required: true, message: '请选择申请时间', trigger: 'change' }],
      },
    };
  },
  created() {
    this.fetchStudentInfo();
  },
  methods: {
    fetchStudentInfo() {
      const studentId = localStorage.getItem('studentId');
      console.log(studentId)
      axios
          .post('http://localhost:8080/api/students/info', { studentId })
          .then((response) => {
            const student = response.data.data;
            this.form.studentId = student.studentId;
            this.form.name = student.name;
            this.form.grade = student.grade;
            this.form.major = student.major;
          })
          .catch((error) => {
            console.error(error);
          });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const formData = {
            studentId: this.form.studentId,
            name: this.form.name,
            grade: this.form.grade,
            major: this.form.major,
            scholarshipType: this.form.scholarshipType,
            applicationMaterial: this.form.applicationMaterial,
            applicationDate: this.form.applicationDate,
          };

          axios
              .post('http://localhost:8080/api/applicants/apply', formData)
              .then((response) => {
                if (response.status === 200 && response.data.code === 200) {
                  // 提交成功，可以根据返回的数据做相应处理
                } else {
                  console.error('Error while submitting form:', response);
                }
              })
              .catch((error) => {
                console.error(error);
              });
        } else {
          console.log('表单校验失败');
          return false;
        }
      });
    },
    handleUploadSuccess(response, file) {
      // 处理文件上传成功的情况
    },
  },
};
</script>

<style>
.application-container {
  margin: 20px;
}
</style>


