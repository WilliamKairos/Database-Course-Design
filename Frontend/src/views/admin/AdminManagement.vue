<template>
  <div>
    <el-table :data="adminData" style="width: 100%">
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="password" label="密码"></el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="scope">
          <el-button type="text" @click="showEditDialog(scope.row)">修改</el-button>
          <el-button type="text" @click="deleteAdmin(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="修改管理员信息" :visible.sync="editDialogVisible">
      <el-form :model="editForm">
        <!-- 在这里添加表单项来编辑管理员信息 -->
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
      adminData: [], // 管理员数据
      editDialogVisible: false,
      editForm: {},
    };
  },
  methods: {
    showEditDialog(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    submitEditForm() {
      // 在这里提交表单，并将数据发送到后端更新管理员信息
      // 关闭对话框
      this.editDialogVisible = false;
    },
    deleteAdmin(row) {
      // 在这里发送请求到后端，删除管理员
    },
    // 可以添加一个方法来从后端获取管理员数据
    fetchAdminData() {
      // 在这里发送请求到后端获取管理员数据
      // 更新 adminData
    }
  },
  mounted() {
    // 当组件挂载时，获取管理员数据
    this.fetchAdminData();
  }
};
</script>

<style>
/* 可以在这里添加样式来优化表格和对话框的外观 */
</style>
