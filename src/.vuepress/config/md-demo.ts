/**
 * 演示配置
 */
import { PlaygroundGlobalOptions } from "vuepress-plugin-md-enhance";

const playground: PlaygroundGlobalOptions = {
  // 添加预设
  presets: ["ts", "vue", "unocss"],
};

export default {
  demo: true,
  playground: playground,

  // install @vue/repl before enabling it
  // https://theme-hope.vuejs.press/zh/guide/markdown/code/vue-playground.html
  // vuePlayground: true,

  // install sandpack-vue3 before enabling it
  // sandpack: true,
};
