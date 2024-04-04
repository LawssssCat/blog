import { navbar } from "vuepress-theme-hope";

export const zhNavbar = navbar([
  "/zh/",
  "/zh/router.md",
  {
    icon: "star",
    text: "星标",
    link: "/zh/star/",
  },
  {
    icon: "category",
    text: "分类",
    link: "/zh/category/",
  },
  {
    icon: "tag",
    text: "标签",
    link: "/zh/tag/",
  },
  {
    icon: "timeline",
    text: "时间轴",
    link: "/zh/timeline/",
  },
]);
