import { createRouter, createWebHashHistory } from 'vue-router'
import SellerPage from '@/views/SellerPage.vue'
import ScreenPage from '@/views/ScreenPage.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/screen',
    },
    {
      path: '/screen',
      component: ScreenPage,
    },
    {
      path: '/sellerpage',
      component: SellerPage,
    },
  ],
})

export default router
