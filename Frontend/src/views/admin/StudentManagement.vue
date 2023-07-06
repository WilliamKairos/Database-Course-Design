<template>
  <div>
    <el-table :data="students" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="studentId" label="学号" width="120"></el-table-column>
      <el-table-column prop="name" label="学生姓名"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="phoneNumber" label="手机号码"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="grade" label="年级">
        <template #default="{ row }">
          {{ getGradeLabel(row.grade) }}
        </template>
      </el-table-column>
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
      <el-form :model="editForm" :rules="rules" ref="editFormRef" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="editForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学生姓名" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="editForm.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="editForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-select v-model="editForm.grade">
            <el-option label="研一年级" value="grade1"></el-option>
            <el-option label="研二年级" value="grade2"></el-option>
            <el-option label="研三年级" value="grade3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="editForm.major"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button @click="cancelEdit">取消</el-button>
        <el-button type="primary" @click="saveEditedStudent">保存</el-button>
      </div>
    </el-dialog>

    <el-dialog v-model="deleteConfirmVisible" title="确认删除学生" :visible.sync="deleteConfirmVisible" width="30%">
      <p>确定要删除该学生吗？</p>
      <span class="dialog-footer">
      <el-button @click="cancelDelete">取消</el-button>
      <el-button type="danger" @click="confirmDelete">确定</el-button>
      </span>
    </el-dialog>
    <el-button type="primary" @click="showAddDialog">添加学生</el-button>
    <el-dialog v-model="addDialogVisible" title="添加学生信息" :visible.sync="addDialogVisible">
      <el-form :model="addForm" :rules="rules" ref="addFormRef" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="addForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学生姓名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="addForm.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="addForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="addForm.grade"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="addForm.major"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button @click="cancelAdd">取消</el-button>
        <el-button type="primary" @click="saveNewStudent">保存</el-button>
      </div>
    </el-dialog>
    <el-button type="primary" @click="showSearchDialog">查询学生</el-button>
    <el-dialog v-model="searchDialogVisible" title="查询学生" :visible.sync="searchDialogVisible">
      <el-form :model="searchForm" ref="searchFormRef" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="searchForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学生姓名" prop="name">
          <el-input v-model="searchForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="searchForm.gender" placeholder="请选择">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="searchForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="searchForm.email"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="searchForm.grade"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="searchForm.major"></el-input>
        </el-form-item>
        <!-- Add more search fields as needed -->
      </el-form>
      <div class="dialog-footer">
        <el-button @click="cancelSearch">取消</el-button>
        <el-button type="primary" @click="performSearch">确认查找</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>
import {reactive, ref} from 'vue';
import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElPagination,
  ElDialog,
  ElForm,
  ElFormItem,
  ElInput,
  ElMessage,
  ElRadioGroup,
  ElRadio,
  ElSelect,
  ElOption,
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
    ElMessage,
    ElSelect,
    ElOption,
  },
  setup() {
    const students = ref([]);
    const selectedStudents = ref([]);
    const pageSize = ref(10);
    const currentPage = ref(1);
    const totalItems = ref(0);
    const editDialogVisible = ref(false);
    const editForm = reactive({
      // id: '',
      studentId: '',
      name: '',
      gender: '',
      phoneNumber: '',
      email: '',
      grade: '',
      major: '',
    });
    const getGradeLabel = (grade) => {
      const gradeMap = {
        grade1: "研一年级",
        grade2: "研二年级",
        grade3: "研三年级",
      };
      return gradeMap[grade] || "";
    };
    const deleteConfirmVisible = ref(false);
    const deleteTarget = ref(null);
    const addDialogVisible = ref(false);
    const addForm = reactive({
      studentId: '',
      name: '',
      gender: '',
      phoneNumber: '',
      email: '',
      grade: '',
      major: '',
    });
    const editFormRef = ref(null);
    const addFormRef = ref(null);

    const searchDialogVisible = ref(false);
    const searchForm = ref({
      studentId: '',
      name: '',
      gender: '',
      phoneNumber: '',
      email: '',
      grade: '',
      major: '',
    });


    const showSearchDialog = () => {
      searchDialogVisible.value = true;
    };

    const cancelSearch = () => {
      searchDialogVisible.value = false;
    };
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
      editFormRef.value.validate((valid) => {
        if (valid) {
          // 验证通过，保存编辑的学生
          const data = {
            studentId: editForm.studentId,
            name: editForm.name,
            gender: editForm.gender,
            phoneNumber: editForm.phoneNumber,
            email: editForm.email,
            grade: editForm.grade,
            major: editForm.major,
          };
          axios
              .put(`http://localhost:8080/api/students/${data.studentId}`, data)
              .then((response) => {
                if (response.status === 200 || response.status === 204) {
                  getStudents(); // 重新获取一次数据，确保数据是最新的
                  editDialogVisible.value = false; // 关闭编辑对话框
                  console.log('Edit student:', editForm);
                } else {
                  console.error('Error while editing student:', response);
                }
              })
              .catch((error) => {
                console.error(error);
              });
        } else {
          ElMessage.error('请确保所有字段都已填写并且格式正确');
          return false;
        }
      });
    };

    const showDeleteConfirm = (row) => {
      deleteTarget.value = row; // 设置要删除的目标学生
      deleteConfirmVisible.value = true; // 显示删除确认对话框
    };

    const cancelDelete = () => {
      deleteTarget.value = null; // 取消删除目标学生
      deleteConfirmVisible.value = false; // 隐藏删除确认对话框
    };

    const confirmDelete = () => {
      const studentId = deleteTarget.value.studentId;
      console.log('Delete student:', studentId);
      axios.delete(`http://localhost:8080/api/students/delete/${studentId}`)
          .then((response) => {
            const result = response.data;
            if (result.code === 200) {
              // 删除成功，刷新学生列表
              getStudents();
            } else {
              // 删除失败，显示错误信息
              ElMessage.error(result.message);
            }
            // 重置删除相关变量
            deleteTarget.value = null;
            deleteConfirmVisible.value = false;
          })
          .catch((error) => {
            console.error(error);
            console.log('有错误')
            // 处理错误，显示错误提示
            ElMessage.error('删除学生失败');
            // 重置删除相关变量
            deleteTarget.value = null;
            deleteConfirmVisible.value = false;
          });
    };

    // const showDeleteConfirm = (row) => {
    //   deleteTarget.value = row;
    //   deleteConfirmVisible.value = true;
    // };
    //
    // const cancelDelete = () => {
    //   deleteConfirmVisible.value = false;
    //   deleteTarget.value = null; // add this line
    // };
    //
    // const confirmDelete = () => {
    //   if (deleteTarget.value) {
    //     // Delete student logic using Element Plus components
    //     axios
    //         .delete(`http://localhost:8080/api/students/delete/${deleteTarget.value.id}`)
    //         .then(() => {
    //           deleteConfirmVisible.value = false;
    //           getStudents();
    //           console.log("Delete student:", deleteTarget.value);
    //           deleteTarget.value = null; // add this line
    //         })
    //         .catch((error) => {
    //           console.error(error);
    //         });
    //   }
    // };
    const resetDeleteConfirm = () => {
      deleteTarget.value = null;
    };

    const getStudents = () => {
      const requestData = {
        pageSize: pageSize.value,
        currentPage: currentPage.value,
      };

      // Get students logic using Element Plus components
      axios.post('http://localhost:8080/api/students/getStudent', requestData)
          .then(response => {
            if (response.data.code === 200) {
              students.value = response.data.data.students; //将students数组赋值给Table的data
              totalItems.value = response.data.data.total; //可以把total用于分页
            }
          });

    };

    const showAddDialog = () => {
      addDialogVisible.value = true;
    };

    const cancelAdd = () => {
      addDialogVisible.value = false;
    };

    const rules = reactive({
      studentId: [
        {required: true, message: '请输入学号', trigger: 'blur'},
      ],
      name: [
        {required: true, message: '请输入姓名', trigger: 'blur'},
      ],
      gender: [
        {required: true, message: '请选择性别', trigger: 'blur'},
      ],
      grade: [
        {required: true, message: '请输入年级', trigger: 'blur'},
      ],
      major: [
        {required: true, message: '请输入专业', trigger: 'blur'},
      ],
      phoneNumber: [
        {required: true, message: '请输入手机号码', trigger: 'blur'},
        {pattern: /^1[3456789]\d{9}$/, message: '手机号码格式不正确', trigger: 'blur'},
      ],
      email: [
        {required: true, message: '请输入邮箱地址', trigger: 'blur'},
        {type: 'email', message: '邮箱地址格式不正确', trigger: 'blur'},
      ],
    });
    const saveNewStudent = () => {
      addFormRef.value.validate((valid) => {
        if (valid) {
          // 验证通过，保存新的学生
          axios
              .post('http://localhost:8080/api/students/saveStudent', addForm)
              .then(response => {
                if (response.status === 200 || response.status === 204) {
                  getStudents();
                  addDialogVisible.value = false;
                  console.log('Add student:', addForm);
                } else {
                  console.error('Error while adding 学生:', response);
                }
              })
              .catch((error) => {
                console.error(error);
              });
        } else {
          ElMessage.error('请确保所有字段都已填写并且格式正确');
          return false;
        }
      });
    };

    const performSearch = () => {
      const searchData = {
        studentId: searchForm.value.studentId,
        name: searchForm.value.name,
        gender: searchForm.value.gender,
        phoneNumber: searchForm.value.phoneNumber,
        email: searchForm.value.email,
        grade: searchForm.value.grade,
        major: searchForm.value.major,
      };

      // 清空搜索表单
      Object.keys(searchForm.value).forEach((key) => {
        searchForm.value[key] = '';
      });
      axios
          .post('http://localhost:8080/api/students/search', searchData)
          .then((response) => {
            if (response.status === 200 && response.data.code === 200) {
              students.value = response.data.data; // 更新为返回的学生数据
              totalItems.value = response.data.data.length; // 可以把total用于分页
              searchDialogVisible.value = false; // 关闭查询对话框
            } else {
              console.error('Error while searching students:', response);
            }
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
      addDialogVisible,
      addForm,
      rules,
      editFormRef,
      addFormRef,
      searchDialogVisible,
      searchForm,
      getGradeLabel,
      showSearchDialog,
      cancelSearch,
      performSearch,
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
      showAddDialog,
      cancelAdd,
      saveNewStudent,
    };
  },
};
</script>

<style>
/* Add your custom styles here */
</style>
