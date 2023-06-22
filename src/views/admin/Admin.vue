<template>
  <div>
    <div class="header">高校奖金评定管理系统</div>
    <div class="main">
      <el-menu class="sidebar">
        <el-menu-item>
          <router-link to="/admin">管理面板</router-link>
        </el-menu-item>
        <el-menu-item>
          <router-link to="/admin/student-management">学生管理</router-link>
        </el-menu-item>
        <el-menu-item>
          <router-link to="/admin/scholarship-types">奖学金种类管理</router-link>
        </el-menu-item>
        <el-menu-item>
          <router-link to="/admin/scholarship-applications">奖学金申请管理</router-link>
        </el-menu-item>
        <el-menu-item>
          <router-link to="/admin/evaluation-results">评定结果管理</router-link>
        </el-menu-item>
        <el-menu-item>
          <router-link to="/admin/admin-management">管理员管理系统管理</router-link>
        </el-menu-item>
      </el-menu>
      <div class="content">
        <router-view></router-view> <!-- 这里将显示子路由的内容 -->
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
