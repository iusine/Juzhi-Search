<template>
  <div id="IndexPage">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="请输入内容"
      enter-button
      @search="onSearch"
    />
    <DividerLine />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostList />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户" force-render>
        <UserList />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import PostList from '@/components/PostList.vue'
import UserList from '@/components/UserList.vue'
import PictureList from '@/components/PictureList.vue'
import DividerLine from '@/components/DividerLine.vue'
import { useRoute, useRouter } from 'vue-router'

const initSearchParams = {
  text: '',
  pageSize: 10,
  pageNum: 1
}

const router = useRouter()
const route = useRoute()
const searchParams = ref(initSearchParams)

watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text as string
  }
})

const onSearch = (value: string) => {
  alert(value)
  router.push({
    query: searchParams.value
  })
}

const activeKey = route.params.category

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value
  })
}


</script>

<style scoped>
#IndexPage {
  margin-top: 36px;
}
</style>
