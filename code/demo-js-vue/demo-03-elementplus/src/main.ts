import { createApp } from 'vue'
import App from './App.vue'

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'
import * as Icons from '@element-plus/icons'

import router from './router'

import { toLine } from './utils';

import mUI from './components'; // 本项目分装的组件

const app = createApp(App)

// 全局注册图标
for (const i in Icons) {
  app.component(`el-icon-${toLine(i)}`, (Icons as any)[i])
}

app.use(router)
app.use(ElementPlus)
app.use(mUI)
app.mount('#app')
