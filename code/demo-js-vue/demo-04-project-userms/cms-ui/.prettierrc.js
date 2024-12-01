// 统一不同开发人员编码风格
// 配置生效
// 方式一：prettier --write src/
// 方式二：安装并配置 Prettier 插件
// 配置参考
// https://www.prettier.cn/docs/options.html
export default {
  $schema: 'https://json.schemastore.org/prettierrc',
  semi: true, // 是否末尾加 ; 符号
  // trailingComma: "none" // 并行行末尾加 , 符号
  singleQuote: true, // 单引号
  printWidth: 150, // 行长度
  useTab: false, // 使用 tab 缩进
  tabWidth: 2, // 缩进个数
};
