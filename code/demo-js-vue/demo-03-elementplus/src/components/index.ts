import { type App } from "vue";

import chooseArea from "./chooseArea";
import chooseIcon from "./chooseIcon";
import textTrend from "./textTrend";

const components = [chooseArea, chooseIcon, textTrend];

export default {
  install(app: App) {
    components.forEach((item) => {
      app.use(item);
    });
  },
};
