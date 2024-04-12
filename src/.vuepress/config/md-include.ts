/**
 * 增强 markdown 功能：
 * 通过 <!-- @include @xxx/path/to/include --> 引用站点文件内容到指定位置
 */
import { getDirname, path } from "vuepress/utils";

const __dirname = getDirname(import.meta.url);
const __project = path.resolve(__dirname, "../../../");

export default {
  resolvePath: (file) => {
    if (file.startsWith("@project")) return file.replace("@project", __project);
    return file;
  },
};
