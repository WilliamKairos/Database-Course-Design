<template>
  <div>
    <div class="header">高校奖金评定管理系统</div>
    <div class="main">
      <el-menu class="sidebar" router>
        <el-menu-item index="/admin/student-management">学生管理</el-menu-item>
        <el-menu-item index="/admin/scholarship-applications">奖学金申请管理</el-menu-item>
      </el-menu>
      <div class="content">
        <router-view></router-view>
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
