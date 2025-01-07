<template>
  <div id="PostList">
    用户列表
    <img :src="image" style="width: 120px">
  </div>
</template>

<script setup lang="ts">

import image from '../assets/unnamed.png'

</script>

<style scoped>
#PostList {
  display: flex;
}
</style>
