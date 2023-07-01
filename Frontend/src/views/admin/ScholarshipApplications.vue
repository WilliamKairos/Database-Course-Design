<template>
  <div class="container">
    <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="学生姓名">
        <template #default="{ row }">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="studentId" label="学号"></el-table-column>
      <el-table-column prop="academicScore" label="学业成绩"></el-table-column>
      <el-table-column prop="ideologyScore" label="思政表现"></el-table-column>
      <el-table-column prop="researchScore" label="科研能力"></el-table-column>
      <el-table-column prop="socialScore" label="社会服务"></el-table-column>
      <el-table-column label="总分">
        <template #default="{ row }">
          <span>{{ calculateTotalScore(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" size="mini" @click="deleteStudent(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="students.length"
    ></el-pagination>

    <div class="button-group">
      <el-button type="text" @click="fetchStudents('grade1')">研一年级</el-button>
      <el-button type="text" @click="fetchStudents('grade2')">研二年级</el-button>
      <el-button type="text" @click="fetchStudents('grade3')">研三年级</el-button>
      <el-button type="text" @click="showAddStudentDialog">添加学生</el-button>
    </div>

    <el-dialog v-model="addStudentDialogVisible" title="添加申请学生">
      <el-form :model="newStudent" label-width="100px" class="add-student-form">
        <el-form-item label="学生姓名">
          <el-input v-model="newStudent.name"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="newStudent.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学业成绩">
          <el-input-number v-model="newStudent.academicScore" :min="0" :max="20"></el-input-number>
        </el-form-item>
        <el-form-item label="思政表现">
          <el-input-number v-model="newStudent.ideologyScore" :min="0" :max="30"></el-input-number>
        </el-form-item>
        <el-form-item label="科研能力">
          <el-input-number v-model="newStudent.researchScore" :min="0" :max="30"></el-input-number>
        </el-form-item>
        <el-form-item label="社会服务">
          <el-input-number v-model="newStudent.socialScore" :min="0" :max="20"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addStudent">确认</el-button>
          <el-button @click="cancelAddStudent">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { ElTable, ElTableColumn, ElButton, ElPagination, ElDialog, ElForm, ElFormItem, ElInput, ElInputNumber } from 'element-plus';
import axios from 'axios';

export default {
  components: {
    ElTable,
    ElTableColumn,
    ElButton,
    ElPagination,
    ElDialog,
    ElForm,
    ElFormItem,
    ElInput,
    ElInputNumber,
  },
  name: 'StudentList',
  setup() {
    const students = ref([]);
    const loading = ref(false);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const addStudentDialogVisible = ref(false);
    const newStudent = ref({
      name: '',
      studentId: '',
      academicScore: 0,
      ideologyScore: 0,
      researchScore: 0,
      socialScore: 0,
    });
    const selectedStudents = ref([]);

    const fetchStudents = async (grade) => {
      try {
        loading.value = true;
        const response = await axios.get(`/api/students?grade=${grade}`);
        students.value = response.data;
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    };

    const deleteStudent = async (student) => {
      try {
        await axios.delete(`/api/students/${student.id}`);
        students.value = students.value.filter((s) => s.id !== student.id);
      } catch (error) {
        console.error(error);
      }
    };

    const calculateTotalScore = (student) => {
      return (
          student.academicScore +
          student.ideologyScore +
          student.researchScore +
          student.socialScore
      );
    };

    const handleSelectionChange = (selectedItems) => {
      selectedStudents.value = selectedItems;
    };

    const handleSizeChange = (newPageSize) => {
      currentPage.value = 1;
      pageSize.value = newPageSize;
    };

    const handleCurrentChange = (page) => {
      currentPage.value = page;
    };

    const addStudent = async () => {
      try {
        await axios.post('/api/students', newStudent.value);
        await fetchStudents();
        cancelAddStudent();
      } catch (error) {
        console.error(error);
      }
    };

    const cancelAddStudent = () => {
      addStudentDialogVisible.value = false;
      newStudent.value = {
        name: '',
        studentId: '',
        academicScore: 0,
        ideologyScore: 0,
        researchScore: 0,
        socialScore: 0,
      };
    };

    const showAddStudentDialog = () => {
      addStudentDialogVisible.value = true;
    };

    onMounted(() => {
      fetchStudents('grade1');
    });

    return {
      students,
      loading,
      currentPage,
      pageSize,
      addStudentDialogVisible,
      newStudent,
      selectedStudents,
      fetchStudents,
      deleteStudent,
      calculateTotalScore,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      addStudent,
      cancelAddStudent,
      showAddStudentDialog,
    };
  },
};
</script>

<style>
/* 你可以在这里添加一些样式来使页面看起来更好 */
</style>
