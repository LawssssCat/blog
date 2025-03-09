import { createRouter, createWebHashHistory } from 'vue-router'
import SellerPage from '@/views/SellerPage.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/sellerpage',
      component: SellerPage
    }
  ],
})

export default router
