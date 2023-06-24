<template>
  <div>
    <el-table :data="evaluationResults" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="applicationId" label="申请编号"></el-table-column>
      <el-table-column prop="studentId" label="学号"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名"></el-table-column>
      <el-table-column prop="grade" label="年级"></el-table-column>
      <el-table-column prop="major" label="专业"></el-table-column>
      <el-table-column prop="scholarshipType" label="奖学金种类"></el-table-column>
      <el-table-column prop="evaluation" label="教务评定"></el-table-column>
      <el-table-column prop="score" label="评分"></el-table-column>
      <el-table-column prop="scholarshipAmount" label="奖学金金额"></el-table-column>
      <el-table-column prop="ranking" label="排名"></el-table-column>
      <el-table-column prop="evaluationDate" label="评定日期"></el-table-column>
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="scope">
          <el-button type="text" @click="showEditDialog(scope.row)">修改</el-button>
          <el-button type="text" @click="deleteEvaluationResult(scope.row)">删除</el-button>
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
    ></el-pagination>
    <el-dialog title="修改评定结果" :visible.sync="editDialogVisible">
      <el-form :model="editForm">
        <!-- 在这里添加表单项来编辑评定结果 -->
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
      evaluationResults: [], // 评定结果数据
      totalItems: 0,
      pageSize: 10,
      currentPage: 1,
      editDialogVisible: false,
      editForm: {},
    };
  },
  methods: {
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      // 可能需要重新获取数据
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      // 可能需要重新获取数据
    },
    showEditDialog(row) {
      this.editForm = { ...row };
      this.editDialog
      this.editDialogVisible = true;
    },
    submitEditForm() {
      // 在这里提交表单，并将数据发送到后端更新评定结果
      // 关闭对话框
      this.editDialogVisible = false;
    },
    deleteEvaluationResult(row) {
      // 在这里发送请求到后端，删除评定结果
    },
    handleSelectionChange(selectedItems) {
      // 处理选中的行，例如可能需要保存选中的评定结果ID
    },
    // 可以添加一个方法来从后端获取评定结果数据
    fetchEvaluationResults() {
      // 在这里发送请求到后端获取评定结果数据
      // 更新 evaluationResults, totalItems
    }
  },
  mounted() {
    // 当组件挂载时，获取评定结果数据
    this.fetchEvaluationResults();
  }
};
</script>

<style>
/* 可以在这里添加样式来优化表格和对话框的外观 */
</style>
