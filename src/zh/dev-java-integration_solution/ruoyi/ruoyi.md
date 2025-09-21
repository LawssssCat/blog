---
title: RuoYi 单应用版本
order: 10
---

代码仓：

- 单应用
  - https://github.com/yangzongzhuan/RuoYi-fast
- 多模块
  - https://github.com/yangzongzhuan/RuoYi
  - https://gitee.com/y_project/RuoYi

## 对比单应用、多模块

目录：

::: tabs

@tab 单应用

```
├─bin/
├─sql/
├─src/
│  ├─main/java/com.ruoyi/
│  |  ├─common/ —— 常量、工具
│  |  |  ├─constant/
│  |  |  ├─exception/
│  |  |  ├─utils/
│  |  |  └─xss/
│  |  ├─framework/ —— 框架集成配置、二次封装
│  |  |  ├─aspectj/
│  |  |  ├─config/
│  |  |  ├─datasource/
│  |  |  ├─shiro/
│  |  |  ├─...
│  |  |  └─web/
│  |  └─project/ —— mvc：controller/service/mapper
│  |     ├─common/
│  |     ├─demo/
│  |     ├─monitor/
│  |     ├─system/
│  |     └─tool/
│  └─main/resources/
│     ├─ehcache/
│     ├─mybatis/
│     ├─static/
│     ├─templates/
│     ├─vm/
│     ├─application.yaml
│     ├─application-druid.yaml
│     └─logback.xml
├─pom.xml
├─LICENSE
├─README.md
├─ry.bat
└─ry.sh
```

@tab 多模块

```

```

:::
