/// <reference types="vite/client" />

// 显式声明 .vue 文件的引入类型
// declare module "*.vue" {
//   import { DefineComponent } from "vue";
//   const component: DefineComponent;
//   export default component;
// }

interface ImportMetaEnv {
  readonly VITE_APP_BASE_TARGET: string;
  readonly VITE_APP_BASE_API: string;
  // 更多环境变量...
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
