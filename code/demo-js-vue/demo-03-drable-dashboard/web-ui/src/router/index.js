import { createRouter, createWebHashHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import SubRoute from '@/views/subRoute/index.vue'

import SellerPage from '@/views/screen/SellerPage.vue'
import ScreenPage from '@/views/screen/ScreenPage.vue'

import DemoPage from '@/views/demo/DemoPage.vue'

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
        {
          path: 'miss',
          components: {
            a: () => import('@/views/subRoute/AxAx.vue'),
            // b: () => import('@/views/subRoute/BxBx.vue'),
            // c: () => import('@/views/subRoute/CxCx.vue'),
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
      path: '/demo0',
      component: DemoPage,
      props: {
        title: '总Demo：测试参数、测试动态加载',
        columnNum: '4',
        demoList: [
          'HelloDemo',
          'HiDemo',
          'demo-elementp/01-button-01',
          'HiDemo',
          'HelloDemo',
          'HiDemo',
          'HelloDemo',
          'HiDemo',
          'HelloDemo',
          'HiDemo',
          'HelloDemo',
          'HiDemo',
          'HelloDemo',
          'HiDemo',
          'HelloDemo',
          'HiDemo',
        ],
      },
    },
    {
      path: '/demo1',
      component: DemoPage,
      props: {
        demoList: ['HiDemo', 'HelloDemo', 'HiDemo'],
      },
    },
  ],
})

export default router
