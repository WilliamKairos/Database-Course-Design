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
          <el-button type="primary" size="mini" @click="showApprovalDialog(row)">审批</el-button>
          <el-button type="success" size="mini" @click="viewAttachments(row)">查看</el-button>
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
    <el-dialog v-model="addStudentDialogVisible" title="审批" width="30%">
      <el-form>
        <el-form-item label="姓名">
          <el-input v-model="newStudent.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="newStudent.studentId" disabled></el-input>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-radio-group v-model="newStudent.approvalStatus">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="不通过">不通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="不通过理由" v-show="newStudent.approvalStatus === '不通过'">
          <el-input v-model="newStudent.rejectionReason" placeholder="请输入不通过理由"></el-input>
        </el-form-item>
        <div style="text-align: center">
          <el-button type="primary" @click="submitApproval">确定</el-button>
          <el-button @click="cancelApproval">取消</el-button>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog v-model="deleteConfirmationVisible" title="确认删除" width="30%">
      <span>确定要删除该学生吗？</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="deleteConfirmationVisible = false">取消</el-button>
      <el-button type="primary" @click="deleteStudentConfirmed">确认</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted, watch, onBeforeUnmount } from 'vue';

import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElPagination,
  ElDialog,
  ElForm,
  ElFormItem,
  ElInput,
  ElInputNumber,
  ElMessage,
  ElMessageBox
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
    ElMessageBox,
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
    const showApprovalDialog = (student) => {
      newStudent.name = student.name; // Set the name of the student for the approval dialog
      newStudent.studentId = student.studentId; // Set the studentId for the approval dialog
      newStudent.approvalStatus = ''; // Reset the approval status
      newStudent.rejectionReason = ''; // Reset the rejection reason
      addStudentDialogVisible.value = true; // Show the approval dialog
    };

    const viewAttachments = (student) => {
      // Implement the logic to view the attachments of the selected student
      // You can use a modal or a separate page to display the attachments
      // You may need to fetch the attachments data from the server based on the student's information
    };

    const submitApproval = () => {
      if (newStudent.approvalStatus === '不通过' && !newStudent.rejectionReason) {
        // Display an error message or perform validation for the rejection reason
        return;
      }

      // Perform the approval submission logic
      // You can make an API call to update the student's approval status and rejection reason

      // Reset the approval dialog fields
      newStudent.approvalStatus = '';
      newStudent.rejectionReason = '';
      addStudentDialogVisible.value = false; // Close the approval dialog
    };

    const cancelApproval = () => {
      // Reset the approval dialog fields
      newStudent.approvalStatus = '';
      newStudent.rejectionReason = '';
      addStudentDialogVisible.value = false; // Close the approval dialog
    };


    const selectedStudents = ref([]);

    const deleteConfirmationVisible = ref(false);

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

    // Remove the existing `watch` import statement from the `setup` function

    const deleteStudent = async (student) => {
      try {
        await ElMessageBox.confirm('确定要删除该学生吗？', '确认删除', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        });

        // 执行删除操作
        await axios.delete(`http://localhost:8080/api/applicants/${student.studentId}`);

        // 更新学生列表
        students.value = students.value.filter((s) => s.studentId !== student.studentId);
      } catch (error) {
        console.error(error);
      }
    };

    const deleteStudentConfirmed = async () => {
      deleteConfirmationVisible.value = false; // Close the delete confirmation dialog

      // Perform the deletion logic
      if (selectedStudents.value.length > 0) {
        try {
          const selectedIds = selectedStudents.value.map((student) => student.studentId);
          await Promise.all(
              selectedIds.map((id) => axios.delete(`http://localhost:8080/api/applicants/${id}`))
          );
          students.value = students.value.filter(
              (student) => !selectedIds.includes(student.studentId)
          );
        } catch (error) {
          console.error(error);
        }
      }

      selectedStudents.value = []; // Clear the selected students array
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
      deleteConfirmationVisible,
      deleteStudentConfirmed,
      showApprovalDialog, // Add the showApprovalDialog method
      viewAttachments, // Add the viewAttachments method
      submitApproval, // Add the submitApproval method
      cancelApproval, // Add the cancelApproval method
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

