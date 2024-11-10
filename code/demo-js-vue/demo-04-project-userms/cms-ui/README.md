# cms-ui

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) to make the TypeScript language service aware of `.vue` types.

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

### Run Unit Tests with [Vitest](https://vitest.dev/)

```sh
npm run test:unit
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

## Project Init

初始化

```bash
 npm init vue@latest
Need to install the following packages:
create-vue@3.12.1
Ok to proceed? (y) y

Vue.js - The Progressive JavaScript Framework

√ Project name: ... cms-ui
√ Add TypeScript? ... No / Yes
√ Add JSX Support? ... No / Yes
√ Add Vue Router for Single Page Application development? ... No / Yes
√ Add Vitest for Unit Testing? ... No / Yes
√ Add an End-to-End Testing Solution? » No
√ Add ESLint for code quality? » Yes
√ Add Prettier for code formatting? ... No / Yes

Scaffolding project in C:\my\workspace\project\blog\code\demo-js-vue\demo-04-project-userms\cms-ui...

Done. Now run:

  cd cms-ui
  npm install
  npm run format
  npm run dev
```

升级组件

```bash
npm uninstall vue-tsc
npm cache clean --force
npm install vue-tsc -D
```

## Project Structure

```bash
.vscode/
node_modules/
public/          —— 静态资源，打包后在 /dest 中
src/
src/assets/      —— 资源
src/assets/css/
src/base-ui/
src/hooks/
src/router/
src/store/
src/utils/
src/views/
src/service/
src/components/  —— 组件
src/App.vue      —— 模板入口
src/main.ts      —— 脚本入口（引用 App.vue）
index.html       —— 项目入口（引用 main.ts）
package.json     —— 项目启动脚本/依赖管理
package-lock.json
env.d.ts         —— 类型声明文件
vite.config.ts   —— 项目打包（by vite）
vitest.config.ts
README.md
tsconfig.json         —— TypeScript配置 （只放引用） （指定ts编译和有助于vscode提示）
tsconfig.app.json     —— TypeScript配置 （只放app相关）
tsconfig.node.json    —— TypeScript配置 （只放node相关）
tsconfig.vitest.json  —— TypeScript配置 （只放test相关）
.gitignore
.editconfig           —— 统一不同 IDE 编码风格
.prettierrc.ts        —— 统一不同开发人员编码风格
.eslintrc.cjs         —— 统一不同开发人员代码风格
```

## Others

参考：

- 后台管理系统 —— https://www.bilibili.com/video/BV1RUxWemEEn
  - 接口文档：
    - v1 https://documenter.getpostman.com/view/12387168/TzsfmQvw
    - v2 https://documenter.getpostman.com/view/12387168/TzzDKb12

## Change Log

- CSS 样式重置
  - normalize.css
  - reset.css
