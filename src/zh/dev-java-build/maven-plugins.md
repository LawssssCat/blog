---
title: Maven 插件
date: 2024-04-13
tag:
  - maven
order: 91
---

通过 Maven 插件可以扩展 Maven 构建功能。
如通过插件，可以在 maven 生命周期的某个阶段做特定的事情，比如：打包源码、打包 fat jar、混淆、加密、重新打包等。
甚至通过插件，Maven 能管理 C\#、Ruby、Scala 和其他语言编写的项目，如 `scala-maven-plugin`、`dotnet-maven-plugin`。

<!-- more -->

## 插件生命周期

- 可以在 maven 生命周期（lifecycle）的每个阶段（phase）上绑定多个插件（plugin）的目标（goal）。
  - 当 maven 执行到对应的阶段时就会执行这些插件的目标。
  - 多个目标（goal）绑定同一个阶段（phase）时，maven 会从上到下依次执行。
  - 一个插件（plugin）可以包含多个目标（goal）。

## 插件命名规则

一般定义插件的 artifactId 为 `xxx-maven-plugin` 或者 `maven-xxx-plugin`。
这里的 xxx 会被识别为前缀（prefix）。

```bash
# gav:goal
mvn com.roadjava.clazz:encrypt-maven-plugin:1.0.0:encrypt
# prefix:goal
mvn encrypt:encrypt
```

上面两句是等价的，就是调用 encrypt 插件的 encrypt 目标的 `execute()` 方法。

## 插件配置使用

插件可以在 `pom.xml` 的 `build` 标签和 `reporting` 标签中配置

### build

todo

### reporting

`<reporting>` 中的配置作用于 Maven 的 site 阶段，用于生成报表。

`<reporting>` 中也可以配置插件 `<plugins>`，并通过一个 `<plugin>` 的 `<reportSet>` 为该插件配置参数。
注意，对于同时出现在 `<build>` 和 `<reporting>` 中的插件，`<reporting>` 中对该插件的配置也能够在构建过程中生效，即该插件的配置是 `<build>` 和 `<reporting>` 中的配置的合并。示例如下：

```xml
<reporting>
  <excludeDefaults></excludeDefaults> <!-- 执行 maven site 时，是否生成报表，默认 false -->
  <outputDirectory>${basedir}/target/site</outputDirectory>
  <plugins>
    <plugin>
      <artifactId>maven-project-info-reports-plugin</artifactId>
      <version>2.0.1</version>
      <reportSets>
        <reportSet> <!-- 对于该插件的某个 goal 的执行参数 -->
          <id>sunlink</id>
          <reports>
            <report>javadoc</report>
          </reports>
          <inherited>true</inherited>
          <configuration>
            <links>
              <link>http://java.sun.com/j2se/1.5.0/docs/api/</link>
            </links>
          </configuration>
        </reportSet>
      </reportSets>
    </plugin>
  </plugins>
</reporting>
```

## 插件开发

前置准备：

```xml title="pom.xml"
<!-- 1. 指定 packaging 打包方式：指定后 Maven 会引入 org.apache.maven.plugins:maven-plugin-plugin 插件  -->
<packaging>maven-plugin</packaging>
<dependencies>
  <!-- 2. 引入 api 依赖：在插件代码中调用 -->
  <dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-plugin-api</artifactId>
    <version>3.5.2</version>
  </dependency>
</dependencies>
```

自定义插件方式有两种

- 注释方式（过时）
- 注解方式

### 注释方式（过时）

<RepoLink path="/code/demo-java-maven/plugin-custom-with-comment/" />

::: tabs

@tab 插件定义

```java
<!-- @include: @project/code/demo-java-maven/plugin-custom-with-comment/src/main/java/org/example/CommentMojo.java -->
```

@tab 使用配置

```xml title="pom.xml"
<build>
    <plugins>
        <plugin>
            <groupId>org.example</groupId>
            <artifactId>plugin-custom-with-comment</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>comment</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

:::

### 注解方式

首先，需要引入注解相关的依赖

```xml title="pom.xml"
<packaging>maven-plugin</packaging>
<dependencies>
  <dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-plugin-api</artifactId>
    <version>3.5.2</version>
  </dependency>
  <dependency>
      <groupId>org.apache.maven.plugin-tools</groupId>
      <artifactId>maven-plugin-annotations</artifactId>
      <version>3.5.2</version>
  </dependency>
</dependencies>
```

::: tabs

@tab 插件定义

```java
<!-- @include: @project/code/demo-java-maven/plugin-custom-with-annotation/src/main/java/org/example/AnnotationMojo.java -->
```

@tab 使用配置

```xml title="pom.xml"
<build>
    <plugins>
        <plugin>
            <groupId>org.example</groupId>
            <artifactId>plugin-custom-with-comment</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <!-- <phase>compile</phase> --> <!-- 配置了默认阶段 -->
                    <goals>
                        <goal>annotation</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <name>test!, age=100\0</name>
            </configuration>
        </plugin>
    </plugins>
</build>
```

:::

## 插件插件 plugin

[`maven-plugin-plugin`](https://maven.apache.org/plugin-tools/maven-plugin-plugin/) —— 生成插件的插件。当打包方式选择 `maven-plugin` 后，该插件将自动导入。

- `plugin:descriptor` 生成插件描述符
- `plugin:addPluginArtifactMetadata` 插入元数据
- `plugin:helpmojo` 生成 help mojo

为了更多功能，我们可以手动导入并配置

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-plugin-plugin</artifactId>
  <configuration>
    <goalPrefix>file-list</goalPrefix>
  </configuration>
  <executions>
    <execution>
      <id>mojo-descriptor</id>
      <goals>
        <goal>descriptor</goal>
      </goals>
    </execution>
    <execution>
      <id>help-goal</id>
      <goals>
        <goal>helpmojo</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

---

参考：

1. B 站 | 乐之者 java - https://www.bilibili.com/video/BV1au4y1e7rE
