import { defineClientConfig } from "vuepress/client";
import RepoLink from "./components/RepoLink.vue";
import SiteLink from "./components/SiteLink.vue";

export default defineClientConfig({
  enhance: ({ app, router, siteData }) => {
    app.component("RepoLink", RepoLink);
    app.component("SiteLink", SiteLink);
  },
});
