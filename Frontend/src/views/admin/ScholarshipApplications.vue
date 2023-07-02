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
      <el-table-column prop="ranking" label="排名"></el-table-column>
      <el-table-column prop="scholarshipType" label="奖学金种类"></el-table-column>
      <el-table-column prop="academicEvaluation" label="教务评定"></el-table-column>
      <el-table-column prop="applicationTime" label="申请时间"></el-table-column>
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
        :total="studentsTotal"
    ></el-pagination>
    <div class="button-group">
      <el-button type="text" @click="fetchStudents('grade1')">研一年级</el-button>
      <el-button type="text" @click="fetchStudents('grade2')">研二年级</el-button>
      <el-button type="text" @click="fetchStudents('grade3')">研三年级</el-button>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue';
import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElPagination,
  ElDialog,
  ElForm,
  ElFormItem,
  ElInput,
  ElInputNumber
} from 'element-plus';
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
    const studentsTotal = ref(0);
    const loading = ref(false);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const addStudentDialogVisible = ref(false);
    const newStudent = reactive({
      name: '',
      studentId: '',
      academicScore: 0,
      ideologyScore: 0,
      researchScore: 0,
      socialScore: 0,
      scholarshipType: '',
      academicEvaluation: '',
      ranking: '',
      applicationTime: '',
    });

    const selectedStudents = ref([]);

    const fetchStudents = async (grade) => {
      try {
        loading.value = true;
        const decodedGrade = decodeURIComponent(grade); // Decode the grade
        const response = await axios.post('http://localhost:8080/api/applicants', { grade: decodedGrade });
        students.value = response.data.data;
        studentsTotal.value = response.data.total;
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    };

    const deleteStudent = async (student) => {
      try {
        await axios.delete(`http://localhost:8080/api/applicants/${student.studentId}`);
        students.value = students.value.filter((s) => s.studentId !== student.studentId);
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
      fetchStudents(); // Fetch students again with the new page size
    };

    const handleCurrentChange = (page) => {
      currentPage.value = page;
      fetchStudents(); // Fetch students again with the new current page
    };

    onMounted(() => {
      fetchStudents('grade1');
    });

    return {
      students,
      studentsTotal,
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
    };
  },
};
</script>

<style>
/* You can add some styles here to improve the appearance of the page */
</style>

