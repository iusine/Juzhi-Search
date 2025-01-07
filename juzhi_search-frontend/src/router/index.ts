import { createRouter, createWebHistory } from 'vue-router'
import IndexPage from '@/pages/IndexPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: IndexPage,
      children: [
        {
          path: '',
          name: '首页',
          component: IndexPage
        },
        {
          path: '/:category',
          component: IndexPage
        }
      ]
    }
  ]
})

export default router
