```bash
pnpm run docs:dev
```

文件结构介绍

```
.
├── docs → 由你指定的文档文件夹
│    │
│    ├── .vuepress (可选的) → 用于存放全局的配置、组件、静态资源等
│    │    │
│    │    ├── dist (默认的) → 构建输出目录
│    │    │
│    │    ├── public (可选的) → 静态资源目录
│    │    │
│    │    ├── styles (可选的) → 用于存放样式相关的文件
│    │    │
│    │    ├── config.{js,ts} (可选的) → 配置文件的入口文件
│    │    │
│    │    └── client.{js,ts} (可选的) → 客户端文件
│    │
│    ├── ... → 其他项目文档
│    │
│    └── README.md → 项目主页
│
└── package.json → Nodejs 配置文件
```

博客页面路径 <https://theme-hope.vuejs.press/zh/guide/blog/path.html>

`plugins.blog`

| 配置项         | 描述         | 默认路径           |
| -------------- | ------------ | ------------------ |
| `article`      | 文章列表     | `/article/`        |
| `category`     | 分类地图页   | `/category/`       |
| `categoryItem` | 特定分类列表 | `/category/:name/` |
| `tag`          | 标签地图页   | `/tag/`            |
| `tagItem`      | 特定标签列表 | `/tag/:name/`      |
| `star`         | 星标文章列表 | `/star/`           |
| `timeline`     | 时间线列表   | `/timeline/`       |

永久链接（permalink）

- <https://vuepress.vuejs.org/zh/guide/permalinks.html>
- <https://v2.vuepress.vuejs.org/zh/reference/frontmatter.html#permalink>

图标

- Iconify: https://icon-sets.iconify.design/
- Iconfont: https://www.iconfont.cn/
- Fontawesome: https://fontawesome.com/icons

组件库 https://plugin-components.vuejs.press/zh/
