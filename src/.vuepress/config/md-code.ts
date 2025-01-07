/**
 * 增强 markdown 功能：
 * 定义 markdown 的 code 高亮规则
 */
import {
  MarkdownOptions,
  MarkdownHighlighterOptions,
} from "vuepress-theme-hope";
import mdInclude from "./md-include.js";
import { shikiPlugin } from "@vuepress/plugin-shiki";
// import { transformerTwoslash, rendererRich } from "@shikijs/twoslash"; // https://shiki.style/packages/twoslash
import {
  transformerNotationDiff,
  transformerMetaHighlight,
  transformerNotationHighlight,
  // transformerNotationWordHighlight,
  transformerNotationFocus,
  // transformerNotationErrorLevel,
  // ...
} from "@shikijs/transformers"; // https://shiki.style/packages/transformers

const highlighter: MarkdownHighlighterOptions = {
  type: "shiki",
  // 启用 shiki 高亮解析器 —— 更高的扩展性（听说）
  useShikiPlugin: shikiPlugin({
    themes: {
      light: "github-light",
      dark: "one-dark-pro",
    },
    transformers: [
      transformerNotationDiff(),
      transformerMetaHighlight(),
      transformerNotationHighlight(),
      transformerNotationFocus({
        classActivePre: "has-focused-lines",
        classActiveLine: "has-focus",
      }),
    ],
  }),
};

const markdownOptions: MarkdownOptions = {
  align: true,
  attrs: true,
  codeTabs: true,
  component: true,
  demo: true,
  figure: true,
  imgLazyload: true,
  imgSize: true,
  include: {
    resolvePath: mdInclude.resolvePath,
  },
  mark: true,
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

  // playground: {
  //   presets: ["ts", "vue"],
  // },

  // install reveal.js before enabling it
  // revealJs: {
  //   plugins: ["highlight", "math", "search", "notes", "zoom"],
  // },

  // install @vue/repl before enabling it
  // vuePlayground: true,

  // install sandpack-vue3 before enabling it
  // sandpack: true,

  // 思维导图
  // 参考： https://theme-hope.vuejs.press/zh/guide/markdown/chart/markmap.html
  // pnpm add -D markmap-lib markmap-toolbar markmap-view
  markmap: true,

  highlighter,
};
export default markdownOptions;
