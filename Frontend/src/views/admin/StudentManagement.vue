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
      <el-table-column label="操作" width="180">
        <template #default="{row}">
          <el-button @click="showEditDialog(row)">修改</el-button>
          <el-button type="danger" @click="deleteStudent(row)">删除</el-button>
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
        :total="totalItems">
    </el-pagination>

    <el-dialog :visible.sync="editDialogVisible" title="编辑学生信息">
      <el-form :model="editForm" label-width="120px">
        <!-- 在这里添加表单输入框，如el-input，绑定到editForm -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEditForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      students: [], // 从API或其他来源获取的学生数据
      selectedStudents: [],
      editDialogVisible: false,
      editForm: {},
      pageSize: 10,
      currentPage: 1,
      totalItems: 0,
    };
  },
  methods: {
    handleSelectionChange(val) {
      this.selectedStudents = val;
    },
    showEditDialog(student) {
      this.editForm = {...student};
      this.editDialogVisible = true;
    },
    deleteStudent(student) {
      // 在这里调用API或其他方法来删除学生
    },
    submitEditForm() {
// 在这里调用API或其他方法来提交编辑表单
      this.editDialogVisible = false;
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      // 可能需要重新获取数据
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      // 可能需要重新获取数据
    },
  },
  mounted() {
    // 在这里获取初始学生数据
    // 假设从API或其他来源获取数据
    // this.students = ...;
    // this.totalItems = ...;
  }
};
</script>

<style>
/* 你可以在这里添加一些样式来使页面看起来更好 */
</style>
