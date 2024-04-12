/**
 * vuepress 配置（入口）
 */
import { defineUserConfig } from "vuepress";
import theme from "./theme.js";
import mdCode from "./config/md-code.js";

export default defineUserConfig({
  base: "/blog/",

  locales: {
    "/zh/": {
      lang: "zh-CN",
      title: "个人博客",
      description: "学习、生活",
    },
  },

  theme,

  plugins: [
    // 代码高亮
    mdCode.shiki.shikiPlugin,
  ],

  // Enable it with pwa
  // shouldPrefetch: false,

  extendsPage: (page) => {},
});
