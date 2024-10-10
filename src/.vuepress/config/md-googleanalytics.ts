import { googleAnalyticsPlugin } from "@vuepress/plugin-google-analytics";

export default {
  googleAnalyticsPlugin: googleAnalyticsPlugin({
    // we have multiple deployments, which would use different id
    id: process.env.DOCS_GA_ID ?? "G-XXXXXXXXXX",
  }),
};
