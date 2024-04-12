/**
 * vuepress 客户端扩展配置
 */
import { defineClientConfig } from "vuepress/client";
import RepoLink from "./components/RepoLink.vue";
import SiteLink from "./components/SiteLink.vue";

// https://vuejs.press/zh/advanced/cookbook/usage-of-client-config.html
export default defineClientConfig({
  enhance: ({ app, router, siteData }) => {
    app.component("RepoLink", RepoLink);
    app.component("SiteLink", SiteLink);
  },
  setup() {},
  layouts: {},
  rootComponents: [],
});
