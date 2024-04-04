import { getDirname, path } from "vuepress/utils";

const __dirname = getDirname(import.meta.url);
const __project = path.resolve(__dirname, "../../../")

export default {
    resolvePath: (file) => {
      if (file.startsWith("@project"))
        return file.replace("@project", __project);
      return file;
    }
}