<template>
  <div>
    <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="studentId" label="学号" width="120"></el-table-column>
      <el-table-column prop="avatar" label="头像" width="120">
        <template #default="{row}">
          <img :src="row.avatar" alt="avatar" width="50" height="50">
        </template>
      </el-table-column>
      <el-table-column prop="name" label="学生姓名"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="phoneNumber" label="手机号码"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="grade" label="年级"></el-table-column>
      <el-table-column prop="major" label="专业"></el-table-column>
      <el-table-column label="操作">
        <template #default="{row}">
          <el-button type="text" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalItems"
    >
    </el-pagination>

    <el-dialog :visible.sync="editDialogVisible" title="编辑学生信息">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="学号">
          <el-input v-model="editForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="editForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="editForm.grade"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="editForm.major"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveStudent">保存</el-button>
      </div>
    </el-dialog>
    <!-- Add Student Dialog -->
    <el-dialog title="Add Student" :visible.sync="dialogVisible" width="30%">
      <el-form :model="newStudent" label-width="100px">
        <!-- ...input fields for student data... -->
        <el-form :model="newStudent" label-width="100px">
          <el-form-item label="Student ID">
            <el-input v-model="newStudent.studentId"></el-input>
          </el-form-item>
          <el-form-item label="Name">
            <el-input v-model="newStudent.name"></el-input>
          </el-form-item>
          <el-form-item label="Gender">
            <el-select v-model="newStudent.gender" placeholder="Select Gender">
              <el-option label="Male" value="Male"></el-option>
              <el-option label="Female" value="Female"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Phone Number">
            <el-input v-model="newStudent.phoneNumber"></el-input>
          </el-form-item>
          <el-form-item label="Email">
            <el-input v-model="newStudent.email"></el-input>
          </el-form-item>
          <el-form-item label="Grade">
            <el-input v-model="newStudent.grade"></el-input>
          </el-form-item>
          <el-form-item label="Major">
            <el-input v-model="newStudent.major"></el-input>
          </el-form-item>
        </el-form>


        <el-form-item>
          <el-button type="primary" @click="saveStudent">Save</el-button>
          <el-button @click="dialogVisible = false">Cancel</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      students: [],
      selectedStudents: [],
      pageSize: 10,
      currentPage: 1,
      totalItems: 0,
      editDialogVisible: false,
      editForm: {
        studentId: "",
        name: "",
        gender: "",
        phoneNumber: "",
        email: "",
        grade: "",
        major: "",
      },
    };
  },
  methods: {
    handleSelectionChange(val) {
      this.selectedStudents = val;
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getStudents();
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getStudents();
    },
    handleEdit(row) {
      // 处理编辑操作
      axios.put(`http://localhost:8080/api/students/${row.id}`, row)
          .then(() => {
            // 在成功响应后更新数据
            this.getStudents();
            console.log("Edit student:", row);
          })
          .catch((error) => {
            console.error(error);
          });
    },
    handleDelete(row) {
      // 处理删除操作
      axios.delete(`http://localhost:8080/api/students/${row.id}`)
          .then(() => {
            // 在成功响应后更新数据
            this.getStudents();
            console.log("Delete student:", row);
          })
          .catch((error) => {
            console.error(error);
          });
    },
    getStudents() {
      // Prepare the request data
      const requestData = {
        pageSize: this.pageSize,
        currentPage: this.currentPage
      };

      // Send a POST request with JSON payload
      axios
          .post("http://localhost:8080/api/students/getStudent", requestData)
          .then(response => {
            this.students = response.data.data;
            this.totalItems = response.data.totalItems;
          })
          .catch(error => {
            console.error(error);
          });
    },
    editStudent(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    saveStudent() {
      axios.post('http://localhost:8080/api/students/saveStudent', this.newStudent)
          .then(response => {
            const savedStudent = response.data.data;
            this.students.push(savedStudent);
            this.dialogVisible = false;
          })
          .catch(error => {
            console.error(error);
          });
    },

    deleteStudent(row) {
      const studentId = row.studentId;
      axios.delete(`http://localhost:8080/api/students/${studentId}`)
          .then(response => {
            const deletedStudentId = response.data.data;
            this.students = this.students.filter(student => student.studentId !== deletedStudentId);
          })
          .catch(error => {
            console.error(error);
          });
    },
  },
  mounted() {
    this.getStudents();
  },
};
</script>

<style>
/* Add your custom styles here */
</style>


