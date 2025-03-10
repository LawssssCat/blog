import { createRouter, createWebHashHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import SubRoute from '@/views/subRoute/index.vue'

import SellerPage from '@/views/screen/SellerPage.vue'
import ScreenPage from '@/views/screen/ScreenPage.vue'

import DemoPage from '@/views/DemoPage.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home',
    },
    {
      path: '/home',
      component: HomePage,
    },
    {
      path: '/subRoute',
      component: SubRoute,
      children: [
        {
          path: '',
          components: {
            a: () => import('@/views/subRoute/AxAx.vue'),
            b: () => import('@/views/subRoute/BxBx.vue'),
            c: () => import('@/views/subRoute/CxCx.vue'),
          },
        },
      ],
    },
    {
      path: '/screen/home',
      component: ScreenPage,
    },
    {
      path: '/screen/card/sellerpage',
      component: SellerPage,
    },
    {
      path: '/demo',
      component: DemoPage,
    },
  ],
})

export default router
