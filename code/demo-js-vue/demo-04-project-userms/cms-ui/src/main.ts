import "normalize.css";
import "./assets/css/index.less";

import { createApp } from "vue";

import App from "./App.vue";
import router from "./router";
import pinia from "./stores";

const app = createApp(App);

// 引入 Element Plus 组件
// 1. 全量引入
// import ElementPlus from "element-plus";
// import "element-plus/dist/index.css";
// app.use(ElementPlus);
// 2. 按需引入（手动）
// import { ElButton } from 'element-plus'
// app.component(ElButton.name, ElButton);
// 3. 按需引入（通过 unplugin-auto-import ）
// npm install -D unplugin-vue-components unplugin-auto-import

app.use(pinia);
app.use(router);

app.mount("#app");
