<template>
  <div>
    <el-table :data="scholarshipApplications" style="width: 100%">
      <el-table-column prop="applicationId" label="申请编号" width="120"></el-table-column>
      <el-table-column prop="studentId" label="学号" width="120"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" width="120"></el-table-column>
      <el-table-column prop="grade" label="年级" width="80"></el-table-column>
      <el-table-column prop="major" label="专业" width="120"></el-table-column>
      <el-table-column prop="scholarshipType" label="奖学金种类" width="150"></el-table-column>
      <el-table-column prop="applicationTime" label="申请时间" width="180"></el-table-column>
      <el-table-column prop="isReviewed" label="是否审核" width="100"></el-table-column>
      <el-table-column prop="reviewReply" label="审核回复" width="200"></el-table-column>
      <el-table-column label="申请材料" width="120">
        <template #default="{row}">
          <el-button @click="downloadApplicationMaterials(row)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{row}">
          <el-button @click="showReviewDialog(row)">审核</el-button>
          <el-button @click="showEditDialog(row)">修改</el-button>
          <el-button type="danger" @click="deleteApplication(row)">删除</el-button>
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

    <el-dialog :visible.sync="reviewDialogVisible" title="审核申请">
      <el-form :model="reviewForm" label-width="120px">
        <!-- 添加表单输入框，如el-input，绑定到reviewForm -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="reviewDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitReviewForm">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="editDialogVisible" title="编辑申请">
      <el-form :model="editForm" label-width="120px">
        <!-- 添加表单输入框，如el-input，绑定到editForm -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type
                       ="primary" @click="submitEditForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      scholarshipApplications: [], // 奖学金申请数据
      totalItems: 0,
      pageSize: 10,
      currentPage: 1,
      reviewDialogVisible: false,
      editDialogVisible: false,
      reviewForm: {},
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
    downloadApplicationMaterials(row) {
      // 在这里实现下载申请材料的功能
    },
    showReviewDialog(row) {
      this.reviewForm = { ...row };
      this.reviewDialogVisible = true;
    },
    submitReviewForm() {
      // 在这里实现提交审核表单的功能
      this.reviewDialogVisible = false;
    },
    showEditDialog(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    submitEditForm() {
      // 在这里实现提交编辑表单的功能
      this.editDialogVisible = false;
    },
    deleteApplication(row) {
      // 在这里实现删除申请的功能
    }
  },
  mounted() {
    // 在这里获取初始奖学金申请数据
    // 假设从API或其他来源获取数据
    // this.scholarshipApplications = ...;
    // this.totalItems = ...;
  }
};
</script>

<style>
/* 你可以在这里添加一些样式来使页面看起来更好 */
</style>
