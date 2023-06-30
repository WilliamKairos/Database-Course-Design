<template>
  <div>
    <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="studentId" label="学号" width="120"></el-table-column>
      <el-table-column prop="avatar" label="头像" width="120">
        <template #default="{ row }">
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
        <template #default="{ row }">
          <el-button type="text" size="small" @click="showEditDialog(row)">编辑</el-button>
          <el-button type="text" size="small" @click="showDeleteConfirm(row)">删除</el-button>
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

    <el-dialog v-model="editDialogVisible" title="编辑学生信息" :visible.sync="editDialogVisible">
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
      <div class="dialog-footer">
        <el-button @click="cancelEdit">取消</el-button>
        <el-button type="primary" @click="saveEditedStudent">保存</el-button>
      </div>
    </el-dialog>

    <!-- 删除学生确认弹窗 -->
    <el-dialog v-model="deleteConfirmVisible" title="确认删除学生" :visible.sync="deleteConfirmVisible" width="30%">
      <p>确定要删除该学生吗？</p>
      <span class="dialog-footer">
    <el-button @click="cancelDelete">取消</el-button>
    <el-button type="danger" @click="confirmDelete">确定</el-button>
  </span>
    </el-dialog>
  </div>
</template>
<script>
import { reactive, ref } from 'vue';
import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElPagination,
  ElDialog,
  ElForm,
  ElFormItem,
  ElInput,
  ElRadioGroup,
  ElRadio,
} from 'element-plus';
import 'element-plus/dist/index.css';
import axios from "axios";

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
    ElRadioGroup,
    ElRadio,
  },
  setup() {
    const students = ref([]);
    const selectedStudents = ref([]);
    const pageSize = ref(10);
    const currentPage = ref(1);
    const totalItems = ref(0);
    const editDialogVisible = ref(false);
    const editForm = reactive({
      id: '',
      studentId: '',
      name: '',
      gender: '',
      phoneNumber: '',
      email: '',
      grade: '',
      major: '',
    });
    const deleteConfirmVisible = ref(false);
    const deleteTarget = ref(null);

    const handleSelectionChange = (val) => {
      selectedStudents.value = val;
    };

    const handleSizeChange = (newSize) => {
      pageSize.value = newSize;
      currentPage.value = 1; // Reset to the first page when the page size changes
      getStudents();
    };

    const handleCurrentChange = (newPage) => {
      currentPage.value = newPage;
      getStudents();
    };

    const showEditDialog = (row) => {
      editForm.id = row.id;
      editForm.studentId = row.studentId;
      editForm.name = row.name;
      editForm.gender = row.gender;
      editForm.phoneNumber = row.phoneNumber;
      editForm.email = row.email;
      editForm.grade = row.grade;
      editForm.major = row.major;
      editDialogVisible.value = true;
    };

    const cancelEdit = () => {
      editDialogVisible.value = false;
    };

    const saveEditedStudent = () => {
      // Save edited student logic using Element Plus components
      axios
          .put(`http://localhost:8080/api/students/${editForm.id}`, editForm)
          .then(() => {
            editDialogVisible.value = false;
            getStudents();
            console.log('Edit student:', editForm);
          })
          .catch((error) => {
            console.error(error);
          });
    };

    const showDeleteConfirm = (row) => {
      deleteTarget.value = row;
      deleteConfirmVisible.value = true;
    };

    const cancelDelete = () => {
      deleteConfirmVisible.value = false;
    };

    const confirmDelete = () => {
      if (deleteTarget.value) {
        // Delete student logic using Element Plus components
        axios
            .delete(`http://localhost:8080/api/students/${deleteTarget.value.id}`)
            .then(() => {
              deleteConfirmVisible.value= false;
              getStudents();
              console.log("Delete student:", deleteTarget.value);
            })
            .catch((error) => {
              console.error(error);
            });
      }
    };
    const resetDeleteConfirm = () => {
      deleteTarget.value = null;
    };

    const getStudents = () => {
      const requestData = {
        pageSize: pageSize.value,
        currentPage: currentPage.value,
      };

      // Get students logic using Element Plus components
      axios
          .post("http://localhost:8080/api/students/getStudent", requestData)
          .then((response) => {
            students.value = response.data.data;
            totalItems.value = response.data.totalItems;
          })
          .catch((error) => {
            console.error(error);
          });
    };

    getStudents();

    return {
      students,
      selectedStudents,
      pageSize,
      currentPage,
      totalItems,
      editDialogVisible,
      editForm,
      deleteConfirmVisible,
      deleteTarget,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      showEditDialog,
      cancelEdit,
      saveEditedStudent,
      showDeleteConfirm,
      cancelDelete,
      confirmDelete,
      resetDeleteConfirm,
    };
  },
};
</script>

<style>
/* Add your custom styles here */
</style>