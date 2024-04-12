/**
 * 增强 markdown 功能：
 * 定义 markdown 的 code 高亮规则
 */
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

export default {
  // 弃用 prismjs 高亮解析器
  prismjs: {
    prismjs: false,
  },
  // 启用 shiki 高亮解析器 —— 更高的扩展性（听说）
  shiki: {
    shikiPlugin: shikiPlugin({
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
  },
};
