import { defineClientConfig } from "vuepress/client";
import RepoLink from "./components/RepoLink.vue";

export default defineClientConfig({
  enhance: ({ app, router, siteData }) => {
    app.component("RepoLink", RepoLink);
  },
});
