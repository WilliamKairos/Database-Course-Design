<template>
  <div>
    <el-table :data="scholarshipTypes" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="scholarshipType" label="奖学金种类" width="120"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{row}">
          <el-button @click="showDetailsDialog(row)">查看</el-button>
          <el-button @click="showEditDialog(row)">修改</el-button>
          <el-button type="danger" @click="deleteScholarshipType(row)">删除</el-button>
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

    <el-dialog :visible.sync="detailsDialogVisible" title="奖学金详情">
      <p>{{selectedScholarshipType.details}}</p>
    </el-dialog>

    <el-dialog :visible.sync="editDialogVisible" title="编辑奖学金种类">
      <el-form :model="editForm" label-width="120px">
        <!-- 添加表单输入框，如el-input，绑定到editForm -->
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
      scholarshipTypes: [], // 从API或其他来源获取的奖学金种类数据
      selectedScholarshipType: {},
      detailsDialogVisible: false,
      editDialogVisible: false,
      editForm: {},
      pageSize: 10,
      currentPage: 1,
      totalItems: 0,
    };
  },
  methods: {
    handleSelectionChange(val) {
      // 处理选择的奖学金种类
    },
    showDetailsDialog(scholarshipType) {
      this.selectedScholarshipType = scholarshipType;
      this.detailsDialogVisible = true;
    },
    showEditDialog(scholarshipType) {
      this.editForm = { ...scholarshipType };
      this.editDialogVisible = true;
    },
    deleteScholarshipType(scholarshipType) {
      // 调用API或其他方法来删除奖学金种类
    },
    submitEditForm() {
      // 调用API或其他方法来提交编辑表单
      this.editDialogVisible = false;
    },
    handleSizeChange(newSize) {
      this.pageSize
      this.pageSize = newSize;
      // 可能需要重新获取数据
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      // 可能需要重新获取数据
    },
  },
  mounted() {
    // 在这里获取初始奖学金种类数据
    // 假设从API或其他来源获取数据
    // this.scholarshipTypes = ...;
    // this.totalItems = ...;
  }
};
</script>

<style>
/* 你可以在这里添加一些样式来使页面看起来更好 */
</style>
