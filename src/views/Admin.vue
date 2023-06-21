<template>
  <div>
    <div class="header">高校奖金评定管理系统</div>
    <div class="main">
      <el-menu class="sidebar">
        <el-menu-item>学生管理</el-menu-item>
        <el-menu-item>奖学金种类管理</el-menu-item>
        <el-menu-item>奖学金申请管理</el-menu-item>
        <el-menu-item>评定结果管理</el-menu-item>
        <el-menu-item>管理员管理系统管理</el-menu-item>
      </el-menu>
      <div class="content">
        <!-- 主内容区域 -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // ...
};
</script>

<style>
.header {
  background-color: #409eff;
  color: white;
  padding: 20px;
  text-align: center;
}

.main {
  display: flex;
}

.sidebar {
  width: 200px;
}

.content {
  flex-grow: 1;
  padding: 20px;
}
</style>
