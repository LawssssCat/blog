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
  prismjs: {
    prismjs: false,
  },
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
