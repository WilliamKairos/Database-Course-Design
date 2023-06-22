<template>
  <div class="scholarship-types-container">
    <h2>奖学金种类</h2>
    <el-table :data="scholarshipTypes" style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="奖学金ID" width="180"></el-table-column>
      <el-table-column prop="name" label="奖学金名称"></el-table-column>
      <el-table-column prop="description" label="奖学金描述"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="showDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="5"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      scholarshipTypes: [], // 模拟奖学金类型数据
      currentPage: 1,
      total: 50, // 模拟总记录数
    };
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    showDetail(row) {
      // 显示详细信息弹窗
    }
  },
};
</script>

<style>
.scholarship-types-container {
  margin: 20px;
}
</style>
