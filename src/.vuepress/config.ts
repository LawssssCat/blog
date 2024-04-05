import { defineUserConfig } from "vuepress";
import theme from "./theme.js";
import { shikiPlugin } from "@vuepress/plugin-shiki";

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
    shikiPlugin({
      themes: {
        light: "github-light",
        dark: "one-dark-pro",
      },
    }),
  ],

  // Enable it with pwa
  // shouldPrefetch: false,
});
