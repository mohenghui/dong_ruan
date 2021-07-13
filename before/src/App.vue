<!-- 根页面 ，其他页面都会使用router-view注入过来-->
<!-- 三大块：template、script、style -->
<template>
  <!-- 只保留一个根目录 -->
  <div id="app">
    <!-- 占位符 -->
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'app'
  }
</script>

<style>

</style>
