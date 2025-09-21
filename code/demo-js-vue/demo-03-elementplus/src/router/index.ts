import {
  createRouter,
  createWebHistory,
  type RouteRecordRaw,
} from "vue-router";

import Container from "../components/container/src/index.vue";
import Home from "../views/Home.vue";

const routes: RouteRecordRaw[] = [
  {
    path: "/",
    component: Container,
    children: [
      {
        path: "/",
        component: Home,
      },
      {
        path: "/test/2",
        component: () => import("../views/test/v2.vue"),
      },
      {
        path: "/chooseIcon",
        component: () => import("../views/chooseIcon/index.vue"),
      },
      {
        path: "/chooseArea",
        component: () => import("../views/chooseArea/index.vue"),
      },
      {
        path: "/textTrend",
        component: () => import("../views/textTrend/index.vue"),
      },
    ],
  },
];

const router = createRouter({
  routes,
  history: createWebHistory(),
});

export default router;
