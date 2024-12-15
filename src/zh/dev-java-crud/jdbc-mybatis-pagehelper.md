---
title: Mybatis PageHelper 使用
date: 2024-09-14
tag:
  - java
  - jdbc
  - mybatis
order: 66
---

官网：https://pagehelper.github.io/

Mybatis 插件，辅助分页功能编写。

参考：

- <https://www.bilibili.com/video/BV1n3411q7FW>

## 配置

三种配置方式

- 在 mybatis-config.xml 中配置
- 在 spring 的配置文件中配置
- 在配置类中配置

::: tabs

@tab XML 方式

```xml title="mybatis-config.xml"
<configuration>
  <plugins>
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
      <property name="pageSizeZero" value="true" />
    </plugin>
  </plugins>
</configuration>
```

@tab 配置类方式

```java
@Configuration
public class PageConfig {
  @Resource
  private List<SqlSessionFactory> sqlSessionFactoryList;

  @PostConstruct
  public void init() {
    PageInterceptor pageInterceptor = new PageInterceptor();
    Properties properties = new Properties();
    properties.put("pageSizeZero", "true");
    pageInterceptor.setProperties(properties);
    for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
      sqlSessionFactory.getConfiguration().addInterceptor(pageInterceptor);
    }
  }
}
```

:::

## 使用

```java
public TableResult<Author> listByPageViaPageHelper(AuthorDTO dto) {
  PageHelper.startPage(dto.getPageNow(), dto.getPageSize());
  TableResult<Autho> tableResult = new TableResult<>();
  List<Author> authors = authorMapper.selectViatPageHelper(dto);
  PageInfo<Author> authorPageInfo = new PageInfo<>(authors);
  tableResult.setRows(authorPageInfo.getList());
  tableResult.setTotalCount(authorPageInfo.getTotal());
  return tableResult;
}
```
