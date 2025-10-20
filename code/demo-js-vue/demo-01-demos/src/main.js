import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

///////////////////////
// router
///////////////////////
import { createWebHistory, createWebHashHistory, createRouter } from "vue-router";
const routes = [
  {
    path: "/", component: () => import("./views/IndexRoute.vue"),
    children: [
      // { path: "/demo-01-open-xss", component: () => import("./views/demo-01-openxss/Index.vue") },
    ]
  },
  { path: "/demo-01-openxss", component: () => import("./views/demo-01-openxss/Index.vue") },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});

///////////////////////
// element plus
///////////////////////
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'

createApp(App)
.use(router)
.use(ElementPlus)
.mount('#app')
