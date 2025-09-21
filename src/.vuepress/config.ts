/**
 * vuepress 配置（入口）
 */
import { defineUserConfig } from "vuepress";
import theme from "./theme.js";
import mdGoogleAnalytics from "./config/md-googleanalytics.js";

export default defineUserConfig({
  base: "/blog/",

  locales: {
    "/zh/": {
      lang: "zh-CN",
      title: "个人博客",
      description: "学习、生活",
    },
  },

  markdown: {
    anchor: {
      level: [2, 3, 4],
    },
    headers: {
      level: [2, 3, 4, 5],
    },
    toc: {
      level: [2, 3, 4],
    },
  },

  theme,

  plugins: [
    mdGoogleAnalytics.googleAnalyticsPlugin, // 访问统计
  ],

  // Enable it with pwa
  // shouldPrefetch: false,

  extendsPage: (page) => {},
});
