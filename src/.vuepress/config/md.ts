/**
 * 增强 markdown 功能
 */
import { MarkdownOptions } from "vuepress-theme-hope";
import mdInclude from "./md-include.js";
import mdDemo from "./md-demo.js";
import mdCode from "./md-code.js";

const markdownOptions: MarkdownOptions = {
  align: true,
  attrs: true, // 属性支持
  codeTabs: true,
  component: true,
  demo: mdDemo.demo,
  spoiler: true, // 剧透文字/高亮文字
  mark: true, // 标记
  figure: true,
  imgLazyload: true,
  imgSize: true,
  include: {
    resolvePath: mdInclude.resolvePath,
  },
  stylize: [
    {
      matcher: "Recommended",
      replacer: ({ tag }) => {
        if (tag === "em")
          return {
            tag: "Badge",
            attrs: { type: "tip" },
            content: "Recommended",
          };
      },
    },
  ],
  sub: true,
  sup: true,
  tabs: true,
  vPre: true,

  // install chart.js before enabling it
  // chart: true,

  // insert component easily

  // install echarts before enabling it
  // echarts: true,

  // install flowchart.ts before enabling it
  flowchart: true,

  // gfm requires mathjax-full to provide tex support
  // gfm: true,

  // install katex before enabling it
  // katex: true,

  // install mathjax-full before enabling it
  // mathjax: true,

  // install mermaid before enabling it
  mermaid: true,

  playground: mdDemo.playground,

  // install reveal.js before enabling it
  // revealJs: {
  //   plugins: ["highlight", "math", "search", "notes", "zoom"],
  // },

  // 思维导图
  // 参考： https://theme-hope.vuejs.press/zh/guide/markdown/chart/markmap.html
  // pnpm add -D markmap-lib markmap-toolbar markmap-view
  markmap: true,

  plantuml: true,

  highlighter: mdCode.highlighter,
};
export default markdownOptions;
