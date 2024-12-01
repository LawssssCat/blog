import { LOGIN_TOKEN } from '@/global/constants';
import { localCache } from '@/utils/cache';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/main', // 需加上导航守卫
    },
    {
      path: '/login',
      component: () => import('@/views/login/LoginView.vue'),
    },
    {
      path: '/main',
      component: () => import('@/views/main/MainView.vue'),
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/:pathMatch(.*)', // 全局额外配置
      component: () => import('@/views/exception/404View.vue'),
    },
  ],
});

/**
 * 导航守卫
 *
 * @param to 去哪里
 * @param from 从哪里来
 * @return 导航的路径
 */
router.beforeEach((to, from) => {
  // 只有登录成功(token)，才能真正进入到main页面
  const token = localCache.getCache(LOGIN_TOKEN);
  if (!token && to.path != '/login') {
    return '/login';
  }
});

export default router;
