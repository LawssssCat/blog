import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";

// element-plus 按需引入
import AutoImport from "unplugin-auto-import/vite"; // 插件：自动导入组件（注意：只导入template内声明的组件，其他地方如script内引入的组件仍然需要手动配置）
import Components from "unplugin-vue-components/vite"; // 插件：声明组件
import { ElementPlusResolver } from "unplugin-vue-components/resolvers"; // 配置：导入ElementPlus的处理

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // vueDevTools(), // 使用浏览器插件调试
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)), // 指定打包文件位置
    },
  },
});
