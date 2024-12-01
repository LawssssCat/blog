import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";

import { createWebHistory, createRouter, RouteRecordRaw } from "vue-router";
import { createPinia } from 'pinia'

const routes: Readonly<RouteRecordRaw[]> = [
  {
    path: "/", component: () => import("./views/Home.vue"), children: [
      { path: "/page1", component: () => import("./views/page01.vue") },
      { path: "/page2", component: () => import("./views/page02.vue") },
    ]
  },
  {
    path: "/about", component: () => import("./views/About.vue")
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

createApp(App)
.use(router)
.use(createPinia())
.mount("#app");
