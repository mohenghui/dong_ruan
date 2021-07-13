<template>
  <div>
    <el-header>
      <el-row :gutter="20">
        <el-col :span="12"><img src="../assets/images/logo.jpg" /></el-col>
        <el-col :span="12"><h2>欢迎</h2></el-col>
      </el-row>
    </el-header>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.el-header {
  background-color: azure;
}
.el-header img {
  width: 50px;
  height: 60px;
}
.el-header h2 {
  line-height: 60px;
}
.el-col:first-child{
    text-align: right;
}
.el-col:last-child{
    text-align: left;
}
</style>
