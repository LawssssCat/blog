---
title: StarUml 使用
date: 2024-09-19
order: 1
---

深度使用，难用：该预定的地方太灵活，该灵活的地方太死板！

---

todo 4+1 View Model

todo Entity-Relationship diagram (ERD)

todo Data-flow diagram (DFD)

todo Flowchart diagram

todo Model-driven development

todo Open APIs

todo Asynchronous model validation

## 安装

https://blog.csdn.net/weixin_51623642/article/details/128476503

```bash
npm install -g asar
cd C:\Program Files\StarUML\resources
asar extract app.asar app
vim C:\Program Files\StarUML\resources\app\src\engine\license-manager.js # 检查证书
// checkLicenseValidity
/*
  getLicenseInfo() {
  return {
    licenseType: "PRO",
    name: "good name",
    quantity: "10"
  };
}
*/
vim C:\Program Files\StarUML\resources\app\src\app-context.js # 检查更新
// ipcRenderer.send('check-update')
asar pack app app.asar
```

## 概念

https://docs.staruml.io/user-guide/basic-concepts

目录管理概念

- Project —— 项目，包含多个模型（Model）
- Model —— 模型/系统，包含多个视图（Diagram）
- Diagram —— 图，一个模型的一种展示方式。如：类图（展示模型中类之间关系）、...
- Model element —— 模型元素，如：一个类
