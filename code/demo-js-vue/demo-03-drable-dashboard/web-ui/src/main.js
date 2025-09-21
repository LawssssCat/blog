import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// 接口
import axios from 'axios'
axios.defaults.baseURL = '/api/v1/'

// echarts主题
import 'echarts/theme/mint'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
