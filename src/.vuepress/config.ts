import { defineUserConfig } from "vuepress";
import theme from "./theme.js";
import { shikiPlugin } from "@vuepress/plugin-shiki";

export default defineUserConfig({
  base: "/",

  locales: {
    "/": {
      lang: "en-US",
      title: "Blog Demo",
      description: "A blog demo for vuepress-theme-hope",
    },
    "/zh/": {
      lang: "zh-CN",
      title: "博客演示",
      description: "vuepress-theme-hope 的博客演示",
    },
  },

  theme,

  plugins: [
    shikiPlugin({
      // 你的选项
      themes: {
        light: "github-light",
        dark: "one-dark-pro",
      },
    }),
  ]

  // Enable it with pwa
  // shouldPrefetch: false,
});
