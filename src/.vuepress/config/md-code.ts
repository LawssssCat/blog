/**
 * 定义 markdown 的 code 高亮规则
 */
import { MarkdownHighlighterOptions } from "vuepress-theme-hope";
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

export default { highlighter };
