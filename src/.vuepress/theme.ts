import { hopeTheme } from "vuepress-theme-hope";
import { zhNavbar } from "./navbar/index.js";
import { zhSidebar } from "./sidebar/index.js";
import { MR_HOPE_AVATAR } from "./logo.js";
import common from "./config/common.js";
import footer from "./config/footer.js";
import mdInclude from "./config/md-include.js";
import mdCode from "./config/md-code.js";

export default hopeTheme({
  hostname: common.github.blog,

  author: {
    name: "Steven",
    url: common.github.profile,
  },

  iconAssets: "fontawesome-with-brands",

  logo: "/logo.svg",

  repo: "LawssssCat/blog",

  docsDir: "src",

  blog: {
    medias: {
      // Baidu: "https://example.com",
      // BiliBili: "https://example.com",
      // Bitbucket: "https://example.com",
      // Dingding: "https://example.com",
      // Discord: "https://example.com",
      // Dribbble: "https://example.com",
      // Email: "mailto:info@example.com",
      // Evernote: "https://example.com",
      // Facebook: "https://example.com",
      // Flipboard: "https://example.com",
      // Gitee: "https://example.com",
      GitHub: common.github.profile,
      // Gitlab: "https://example.com",
      // Gmail: "mailto:info@example.com",
      // Instagram: "https://example.com",
      // Lark: "https://example.com",
      // Lines: "https://example.com",
      // Linkedin: "https://example.com",
      // Pinterest: "https://example.com",
      // Pocket: "https://example.com",
      // QQ: "https://example.com",
      // Qzone: "https://example.com",
      // Reddit: "https://example.com",
      // Rss: "https://example.com",
      // Steam: "https://example.com",
      // Twitter: "https://example.com",
      // Wechat: "https://example.com",
      // Weibo: "https://example.com",
      // Whatsapp: "https://example.com",
      // Youtube: "https://example.com",
      // Zhihu: "https://example.com",
      // MrHope: ["https://mister-hope.com", MR_HOPE_AVATAR],
    },
  },

  displayFooter: footer.displayFooter,

  locales: {
    /**
     * Chinese locale config
     */
    "/zh/": {
      // navbar
      navbar: zhNavbar,

      // sidebar
      sidebar: zhSidebar,

      displayFooter: true,

      blog: {
        description: "萌新",
        intro: "/zh/intro.html",
      },

      // page meta
      metaLocales: {
        editLink: "在 GitHub 上编辑此页",
      },

      copyright: footer.locales["/zh/"].copyright,
    },
  },

  // enable it to preview all changes in time
  // hotReload: true,

  plugins: {
    prismjs: mdCode.prismjs.prismjs,
    blog: true,

    // install @waline/client before enabling it
    // WARNING: This is a test server for demo only.
    // You should create and use your own comment service in production.
    // comment: {
    //   provider: "Waline",
    //   serverURL: "https://waline-comment.vuejs.press",
    // },

    components: {
      components: ["Badge", "VPCard"],
    },

    // all features are enabled for demo, only preserve features you need here
    mdEnhance: {
      align: true,
      attrs: true,
      codetabs: true,
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
    },

    // install @vuepress/plugin-pwa and uncomment these if you want a PWA
    // pwa: {
    //   favicon: "/favicon.ico",
    //   cacheHTML: true,
    //   cachePic: true,
    //   appendBase: true,
    //   apple: {
    //     icon: "/assets/icon/apple-icon-152.png",
    //     statusBarColor: "black",
    //   },
    //   msTile: {
    //     image: "/assets/icon/ms-icon-144.png",
    //     color: "#ffffff",
    //   },
    //   manifest: {
    //     icons: [
    //       {
    //         src: "/assets/icon/chrome-mask-512.png",
    //         sizes: "512x512",
    //         purpose: "maskable",
    //         type: "image/png",
    //       },
    //       {
    //         src: "/assets/icon/chrome-mask-192.png",
    //         sizes: "192x192",
    //         purpose: "maskable",
    //         type: "image/png",
    //       },
    //       {
    //         src: "/assets/icon/chrome-512.png",
    //         sizes: "512x512",
    //         type: "image/png",
    //       },
    //       {
    //         src: "/assets/icon/chrome-192.png",
    //         sizes: "192x192",
    //         type: "image/png",
    //       },
    //     ],
    //     shortcuts: [
    //       {
    //         name: "Demo",
    //         short_name: "Demo",
    //         url: "/demo/",
    //         icons: [
    //           {
    //             src: "/assets/icon/guide-maskable.png",
    //             sizes: "192x192",
    //             purpose: "maskable",
    //             type: "image/png",
    //           },
    //         ],
    //       },
    //     ],
    //   },
    // },
  },
});
