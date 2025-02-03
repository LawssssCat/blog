---
title: Swagger 使用
date: 2024-12-08
order: 66
---

todo https://medium.com/@f.s.a.kuzman/using-swagger-3-in-spring-boot-3-c11a483ea6dc
https://github.com/fadykuzman/swagger-demo/blob/main/src/main/java/dev/codefuchs/swaggerdemo/plant/PlantApi.java

~~todo https://swagger.io/tools/open-source/getting-started/~~

> - Swagger 代码仓库： <https://github.com/swagger-api>
> - SpringDoc 文档： <https://springdoc.org/>

## Swagger 生态

<b style="background:#636">Swagger ecosystem: <https://swagger.io/docs/specification/v3_0/about/></b>

基于 [OpenAPI](https://github.com/OAI/OpenAPI-Specification) 规范编写的接口文档（yaml/json 格式），
可以通过 [swagger-js](https://github.com/swagger-api/swagger-js)（alias `swagger-client`） 解析其中内容，
然后通过 [swagger-ui](https://github.com/swagger-api/swagger-ui) 展示成 html 页面，方便前后端交流。

> 如果还没有接口，可以使用：
>
> - <https://randomuser.me/> —— 返回随机模拟的用户介绍信息接口
> - <https://swapi.co/documentation> —— 在线自定义模拟的接口响应

为了方便接口文档的编写，Swagger 提供了在线工具以便立即体验 Swagger 功能：

- <https://petstore.swagger.io> —— API 文档界面 【demo】 （[link_github_repo](https://github.com/swagger-api/swagger-js)）
- <https://editor.swagger.io/> —— API YAML 在线编辑器 （[link_github_repo](https://github.com/swagger-api/swagger-editor)）

其次，Swagger 还提供了一些文档自动化生成工具：

- <https://github.com/swagger-api/swagger-core> for java
- <https://github.com/swagger-api/swagger-codegen>
- <https://github.com/swagger-api/swagger-parser>

## Swagger 历史

### Swagger

Swagger 它最初由 Tony Tam 在 2011 年创建，并在之后被 SmartBear Software 公司收购。在过去几年中，Swagger 经历了许多重大的更新和变化，其发展史大概可以分为以下几个阶段：

1. **Swagger 1.x 阶段（2011-2014 年）**
   Swagger 最初是一个简单的 API 文档生成工具，通过对 JAX-RS 和 Jersey 注解的支持自动生成 API 文档，使得 API 文档的维护变得更加容易。
   在这个阶段，Swagger 还没有完全成熟，只能支持基本的 API 描述和文档生成。

1. **Swagger 2.x 阶段（2014-2017 年）**
   在 Swagger 2.x 阶段，Swagger 发生了重大变化。它不再仅仅是一个文档生成工具，而是一个完整的 API 开发和管理平台。Swagger 2.x 加入了强大的注解支持，可以描述 API 的细节信息，如请求参数、返回类型等，以及定义 RESTful API 的元数据，如 API 描述、标签等。
   此外，Swagger 2.x 还引入了 OpenAPI 规范，在 API 定义方面有了更严格的标准和规则。

1. **OpenAPI 阶段（2017-至今）（也被称为 Swagger 3.x）**
   在 2017 年，Swagger 2.x 的规范成为了 Linux 基金会旗下的 OpenAPI 规范。这标志着 Swagger 从一款工具演变成为了一个开放且标准的 API 定义框架。OpenAPI 规范不仅继承了 Swagger 2.x 的特性，还提供了更加全面和严格的 API 定义规范，并且扩展了对非 RESTful API 的支持。

随着 OpenAPI 规范的普及，越来越多的 API 开发者开始使用 Swagger/OpenAPI 来开发、测试和文档化他们的 RESTful API。所以，随着 Linux 基金会旗下的 OpenAPI 收购了 Swagger2.x 后对其进行了更严格的规范，又进行了各种优化，所以我们也可以称 OpenAPI 是一个全新的 Swagger3.x，因为 OpenAPI 对其作了更多的优化和规范。
除了上述几个主要阶段之外，还有一些其他重要的事件和版本，如 Swagger UI、Swagger Codegen、SwaggerHub 等等。
这些工具和服务进一步扩展了 Swagger 的功能，使其成为了一个更加完整、强大和易于使用的 API 定义和管理平台。

### OpenAPI

其实 OpenAPI 规范（也称为 Swagger 3.x 规范）是一种用于描述 RESTful API 的标准化格式，它定义了如何描述 API 的基本信息、结构、参数、响应等方面的规范。OpenAPI 规范以机器可读的方式定义了 RESTful API 的结构和特征，支持自动生成文档、客户端与服务端代码、Mock Server 和测试工具等。

OpenAPI 规范最初由开发 Swagger 的团队在 2010 年推出，从 Swagger 2.0 开始，Swagger 规范被正式更名为 OpenAPI 规范，并得到了许多社区的支持和贡献。OpenAPI 规范采用 JSON 或 YAML 格式编写，并支持多种数据类型，可以描述 API 的基本信息、路径、HTTP 方法、参数、响应等各种细节。通过遵循 OpenAPI 规范，开发者可以快速定义和构建 RESTful API，并且可以生成相应的文档和代码来帮助他们更快地开发与测试 API。
同时，OpenAPI 规范还可以促进不同系统之间的交互和集成，因为根据规范定义的 API 可以被多个客户端程序和服务端程序所理解和使用。

OpenAPI 阶段的 Swagger 也被称为 Swagger 3.0。在 Swagger 2.0 后，Swagger 规范正式更名为 OpenAPI 规范，并且根据 OpenAPI 规范的版本号进行了更新。
因此，Swagger 3.0 对应的就是 OpenAPI 3.0 版本，它是 Swagger 在 OpenAPI 阶段推出的一个重要版本。与前几个版本相比，Swagger 3.0 更加强调对 RESTful API 的支持和规范化，提供了更丰富和灵活的定义方式，并且可以用于自动生成文档、客户端代码、服务器代码和测试工具等。

## 名词解释

- **OpenAPI** —— 是一个组织（OpenAPI Initiative），他们指定了一个如何描述 HTTP API 的规范（OpenAPI Specification）。

- **Swagger** —— 是 SmartBear 这个公司的一个开源项目。

- ~~**Springfox** —— 是 Java Spring 生态的一个开源库，是 Swagger 与 OpenApi 规范的具体实现。我们使用它就可以在 spring 中生成 API 文档。~~

  ::: warning
  Springfox 以前基本上是行业标准，目前最新版本可以支持 Swagger2, Swagger3 以及 OpenAPI3 三种格式。
  但是其从 2020 年 7 月 14 号就不再更新了，不支持 springboot3，所以业界都在不断的转向我们今天要谈论的另一个库 Springdoc，新项目就不要用了。
  :::

* **Springdoc** —— 带着继任 Springfox 的使命而来。支持 OpenApi 规范，支持 Springboot3。

## Swagger 在线编辑

流程： Swagger Editor -> YAML -> JSON -> Swagger UI

::: tabs

@tab 在线编辑

- <https://editor.swagger.io>
- write documentation in yaml
- download `.json` file
- download [swagger-ui-dist](https://www.npmjs.com/package/swagger-ui-dist) files
- import `.json` file
- upload to your website

@tab 本地编辑

- clone [swagger-editor](https://github.com/swagger-api/swagger-editor) project
- write documentation in yaml
- write gulp task to generate `.json` file
- download [swagger-ui-dist](https://www.npmjs.com/package/swagger-ui-dist) files
- import `.json` file
- upload to your website

:::

### Swagger Editor

> 参考：
>
> - <https://swagger.org.cn/docs/open-source-tools/swagger-editor/>
> - <https://blog.csdn.net/rth362147773/article/details/78992043>

Swagger 提供了编辑 openapi yaml 的工具 [swagger-editor](https://github.com/swagger-api/swagger-editor)

online: https://editor.swagger.io/

local:

```bash
#############
# 启动
#############
# 或者 nginx
# 或者 npm install -g http-server
npm install -g http-server
wget https://github.com/swagger-api/swagger-editor/releases/download/v2.10.4/swagger-editor.zip
unzip swagger-editor.zip
http-server swagger-editor
# 或者 docker
docker pull swaggerapi/swagger-editor
docker run -p 80:8080 swaggerapi/swagger-editor
# 或者 build
git clone https://github.com/swagger-api/swagger-editor
cd swagger-editor
# npm install
npm install --legacy-peer-deps
npm run build
npm start
# ./index.html
```

通过 gulp 监控 yaml 变化生成 JSON

```js title="gulpfile.js"
var gulp = require("gulp");
var yaml = require("js-yaml");
var path = require("path");
var fs = require("fs");

// convert yaml to json
gulp.task("swagger", function () {
  var doc = yaml.safeLoad(
    fs.readFileSync(path.join(__dirname, "api/swagger/swagger.yaml"))
  );
  fs.writeFileSync(
    path.join(__dirname, "../swagger-gen.json"),
    JSON.stringify(doc, null, " ")
  );
});

// watchs for changes
gulp.task("watch", function () {
  gulp.watch("api/swagger/swagger.yaml", ["swagger"]);
});
```

```bash
npm install -g swagger
swagger project edit
```

### Swagger UI

> 参考： <https://swagger.org.cn/docs/open-source-tools/swagger-ui/usage/installation/>

`swagger-ui` 旨在供包含模块捆绑程序（如 Webpack、Browserify 和 Rollup）的 JavaScript Web 项目使用。
其主文件导出 Swagger UI 的主函数，该模块还包含位于 `swagger-ui/dist/swagger-ui.css` 的命名空间样式表。

```bash
npm install swagger-ui
```

demo: <https://github.com/swagger-api/swagger-ui/tree/master/docs/samples/webpack-getting-started>

配置： <https://swagger.org.cn/docs/open-source-tools/swagger-ui/usage/installation/>

### Swagger UI dist （次选）

`swagger-ui-dist` 旨在用于需要向客户端提供资产的服务器端项目。
导入该模块后，将包含一个 absolutePath 辅助函数，该函数返回 `swagger-ui-dist` 模块安装位置的绝对文件系统路径。

该模块的内容反映了您在 Git 存储库中看到的 dist 文件夹。
最有用的是 `swagger-ui-bundle.js`，它是一个 Swagger UI 构建，其中包含运行所需的所有代码，并包含在一个文件中。该文件夹还包含一个 index.html 资产，以便您可以轻松地像这样提供 Swagger UI

```bash
npm install swagger-ui-dist --save-dev
```

该模块还导出 SwaggerUIBundle 和 SwaggerUIStandalonePreset，因此如果您在无法处理传统 npm 模块的 JavaScript 项目中，您可以执行以下操作

```html title="index.html"
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Swagger UI</title>
    <link
      rel="stylesheet"
      type="text/css"
      href="./node_modules/swagger-ui-dist/swagger-ui.css"
    />
  </head>
  <body>
    <div id="swagger-ui"></div>
    <script src="./node_modules/swagger-ui-dist/swagger-ui-bundle.js"></script>
    <script src="./node_modules/swagger-ui-dist/swagger-ui-standalone-preset.js"></script>
    <script>
      window.onload = function () {
        SwaggerUIBundle({
          url: "http://petstore.swagger.io/v2/swagger.json",
          dom_id: "#swagger-ui",
          presets: [SwaggerUIBundle.presets.apis, SwaggerUIStandalonePreset],
          layout: "StandaloneLayout",
        });
      };
    </script>
  </body>
</html>
```

```bash
./index.html
```

## Swagger 二次开发

### swagger-js

https://blog.csdn.net/gitblog_00511/article/details/141445893

一个用于解析、验证、转换 Swagger 文档（OpenAPI 规范）的 JavaScript 库。
swagger-client 是一个 JavaScript 模块，用于处理 Swagger 和 OpenAPI 文档。它支持 Swagger 2.0 和 OpenAPI 3 规范，提供标签接口、HTTP 客户端和定义解析器等功能。该模块兼容多个 OpenAPI 版本，适用于 Node.js 和现代浏览器，简化了 API 文档的获取、解析和交互过程。

```txt
swagger-js/
├── docs/                - 包含项目文档和指南。
├── dist/                - 编译后的生产版本代码存放地。
├── examples/            - 示例应用，展示如何在实际项目中使用Swagger JS。
├── src/                 - 源代码目录，包含了所有核心功能的实现。
│   ├── lib/             - 核心库代码。
│   └── ...              - 其他相关源码文件和子目录。
├── test/                - 单元测试和集成测试文件。
├── package.json        - Node.js项目的描述文件，定义了依赖关系和脚本命令。
└── README.md           - 项目的主要说明文档。
```

## Java 集成

Java 项目一般用 spring。
将 Swagger 整合进 Java，就是将 Swagger 与 Spring 适配。

现有的已完成适配的，只需引入和简单配置就能用的三方件有 ~~springfox~~、springdoc、knie4j。
当然，也可以手动用 swagger 原生集成。

具体情况如下：

- ~~spfingfox —— swagger 与 spring 的整合~~ （不再更新，最后修改 2020 年）
- springdoc —— —— swagger 与 spring 的整合
- Knife4j —— Swagger2 + OpenAPI3 + ui 增强
- swagger-core —— swagger 原生与 spring 的整合

### 注解说明

注解例子 <https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations>

| Swagger2 注解        | Swagger3 注解                                              | 注解位置          |
| -------------------- | ---------------------------------------------------------- | ----------------- |
| `@Api`               | `@Tag(name = "接口类描述")`                                | Controller 类上   |
| `@ApiOperation`      | `@Operation(summary = "接口方法描述")`                     | Controller 方法上 |
| `@ApiImplicitParams` | `@Parameters`                                              | Controller 方法上 |
| `@ApiImplicitParam`  | `@Parameter(description = "参数描述")`                     | Controller 方法上 |
| `@ApiParam`          | `@Parameter(description = "参数描述")`                     | 方法参数上        |
| `@ApiIgnore`         | `@Parameter(hidden = true)` 或 `@Operation(hidden = true)` | -                 |
| `@ApiModel`          | `@Schema`                                                  | DTO 类上          |
| `@ApiModelProperty`  | `@Schema`                                                  | DTO 属性上        |

#### 配置类（Swagger2）

```java
@EnableSwagger2
@Configuration
public class SwaggerConfig {
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("org.example.xxx"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("xxx系统接口文档")
      .description("接口文档介绍")
      .version("V1.0")
      .contact(new Contact("xxName", "xxUrl", "xxEmail"))
      .build();
  }
}
```

#### 配置类（Swagger3）

:::::: tabs

@tab 配置类配置

::: tip

- `io.swagger.v3.oas.annotations` 是 Swagger 注解包
- `io.swagger.v3.oas.models` 是 Swagger 配置类对象方式

:::

```java title="SpringDocConfig.java"
package com.mcode.swaggertest.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: SpringDocConfig
 * Package: com.mcode.swaggertest.config
 * Description:
 */
@Configuration
public class SpringDocConfig {

//    @Bean
//    public GroupedOpenApi productApi() {
//        return GroupedOpenApi.builder()
//                .group("product-service")
//                .pathsToMatch("/product/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi orderApi() {
//        return GroupedOpenApi.builder()
//                .group("order-service")
//                .pathsToMatch("/order/**")
//                .build();
//    }

    @Bean
    public OpenAPI openAPI(){
       // 联系人信息(contact)，构建API的联系人信息，用于描述API开发者的联系信息，包括名称、URL、邮箱等
        Contact contact = new Contact()
                .name("xxx")  // 作者名称
                .email("xxxx@xxxx.com") // 作者邮箱
                .url("https://xxxx.example.org/xxx/") // 介绍作者的URL地址
                .extensions(new HashMap<String,Object>());// 使用Map配置信息（如key为"name","email","url"）

        License license = new License()
                .name("Apache 2.0")                         // 授权名称
                .url("https://www.apache.org/licenses/LICENSE-2.0.html")    // 授权信息
                .identifier("Apache-2.0")                   // 标识授权许可
                .extensions(new HashMap<String, Object>());// 使用Map配置信息（如key为"name","url","identifier"）

        //创建Api帮助文档的描述信息、联系人信息(contact)、授权许可信息(license)
        Info info = new Info()
                .title("Api接口文档标题")      // Api接口文档标题（必填）
                .description("项目描述")     // Api接口文档描述
                .version("1.0.0")                                  // Api接口版本
                .termsOfService("https://xxxx.example.org/xxx/")    // Api接口的服务条款地址
                .license(license)  //   授权名称
                .contact(contact); // 设置联系人信息

         List<Server>  servers = new ArrayList<>(); //多服务
         // 表示服务器地址或者URL模板列表，多个服务地址随时切换（只不过是有多台IP有当前的服务API）
         servers.add(new Server().url("http://localhost:8080").description("服务1"));
         servers.add(new Server().url("http://localhost:8081").description("服务2"));

        // // 设置 spring security apikey accessToken 认证的请求头 X-Token: xxx.xxx.xxx
        SecurityScheme securityScheme = new SecurityScheme()
                .name("x-token")
                .type(SecurityScheme.Type.APIKEY)
                .description("APIKEY认证描述")
                .in(SecurityScheme.In.HEADER);

        // 设置 spring security jwt accessToken 认证的请求头 Authorization: Bearer xxx.xxx.xxx
        SecurityScheme securityScheme1 = new SecurityScheme()
                .name("JWT认证")
                .scheme("bearer") //如果是Http类型，Scheme是必填
                .type(SecurityScheme.Type.HTTP)
                .description("认证描述")
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT");

        List<SecurityRequirement> securityRequirements = new ArrayList<>();

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("authScheme");

        securityRequirements.add(securityRequirement);

        // 返回信息
        return new OpenAPI()
                // 💡表示使用的 OpenAPI 规范版本（例如 3.0.1）。
                //.openapi("3.0.1")  // Open API 3.0.1(默认)
                // 💡表示API的基本信息，包括标题、版本号、描述、联系人等。使用Info类来创建这个对象。
                .info(info)
                // 💡表示服务器地址或者URL模板列表。每个URL模板可以包含占位符，这些占位符可以被路径参数或者查询参数替换。
                .servers(servers)
                // 💡paths属性（推荐使用注解方式，不推荐使用配置类配置）：
                // 表示API的所有路径和操作信息，使用PathItem类来描述每一个路径，使用Operation类来描述操作。
                // .paths(....)
                // 💡tags属性：表示API的标签信息，用于对相似的操作进行分组。
                // .tags(...)
                // 💡表示API的组件信息，比如响应模板、请求模板和安全方案等。
                // 使用Schema、Response、Parameter、SecurityScheme等类来创建这些对象。
                .components(new Components().addSecuritySchemes("authScheme",securityScheme1)) //添加鉴权组件
                .security(securityRequirements) //全部添加鉴权小锁
                .externalDocs(new ExternalDocumentation()
                        .description("对外说明") //对外说明
                        .url("https://xxxx.example.org/xxxx/"));       // 配置Swagger3.0描述信息
    }
}
```

@tab 注解方式配置

```java title="SpringDocConfig.java"
package com.mcode.swaggertest.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: SpringDocConfig
 * Package: com.mcode.swaggertest.config
 * Description:
 */

@Configuration
@OpenAPIDefinition(
        // 💡info：用于描述 API 基本信息的对象，包括标题、版本号、描述等属性；
        info = @Info(
                title = "Api接口文档标题",
                description = "项目描述",
                version = "1.0.0",
                termsOfService = "https://xxxx.example.org/xxxx/",
                contact = @Contact(
                        name = "xxxx",                            // 作者名称
                        email = "xxxx@xxxx.com",                  // 作者邮箱
                        url = "https://xxxx.example.org/xxxx/"  // 介绍作者的URL地址
                ),
                license = @License(name = "Apache 2.0",
                        url = "http://www.apache.org/licenses",
                        extensions = @Extension(name = "test", properties = @ExtensionProperty(name = "test", value = "1111")))
        ),
        // 💡security：用于描述 API 安全方案的数组，其中每个安全方案包含多个安全需求；
        security = @SecurityRequirement(name = "JWT认证"), //全部加上认证
        // 💡servers：用于描述 API 服务的 URL 和配置信息的数组；
        servers = {
                @Server(url = "http://localhost:8081", description = "服务1"),
                @Server(url = "http://localhost:8080", description = "服务2")
        },
        // 💡tags：用于描述 API 标签的数组，每个标签包含名称、描述等属性；
        // 💡externalDocs：用于描述外部文档链接的对象，包含描述和 URL 两个属性。
        externalDocs = @ExternalDocumentation(description = "对外说明", url = "https://xxxx.example.org/xxxx/")
)
@SecurityScheme(
        name = "JWT认证",                   // 认证方案名称
        type = SecuritySchemeType.HTTP,      // 认证类型，当前为http认证
        description = "这是一个认证的描述详细",  // 描述信息
        in = SecuritySchemeIn.HEADER,        // 代表在http请求头部
        scheme = "bearer",                   // 认证方案，如：Authorization: bearer token信息
        bearerFormat = "JWT")
public class SpringDocConfig2 {
}
```

::::::

#### 接口类（Swagger3）

配置文档标题及归类，就是在 Controller 上配置。

```txt
注解：@Tag 可以用于对接口进行分类和归类，便于开发人员组织和管理 API 文档
    具体属性：
        ①：name：表示标签的名称，必填属性，也得注意多个Controller上的name不要写一样的，这样就会把它们归类在一起。
        ②：description：表示标签的描述信息，非必填属性。
        ③：externalDocs：用于指定URL地址文档信息来追加描述接口的信息。非必填属性。
        示例：
            @Tag(name = "用户控制器", description = "用户控制器描述",
                externalDocs = @ExternalDocumentation(
                description = "文档接口描述",
                url = "https://xxxx.example.org/xxxx"))
```

#### 接口方法（Swagger3）

配置文档下的每一个接口信息，就是 Controller 里的每一个 RequestMapping

```txt
注解：@Operation用于对API操作（即方法）进行描述和标记。就是我们熟知的Controller下的一个个请求的方法上。
    具体可以参考 io.swagger.v3.oas.annotations。
    具体属性：
        ①：summary：用于简要描述API操作的概要。
        ②：description：用于详细描述API操作的描述信息。
        ③：parameters：用于指定API操作的参数列表，包括路径参数、请求参数、请求头部等。可以使用@Parameter注解进一步定义参数。
        ④：operationId：用于指定API操作的唯一标识符，可以用于生成客户端代码或文档等。
            说明：第三方工具使用operationId来唯一标识此操作。（具体我也没用过）
        ⑤：requestBody：用于定义API操作的请求体，可以使用@RequestBody注解进一步定义请求体。
            说明：这里的@RequestBody注解是@io.swagger.v3.oas.annotations.parameters.RequestBody包里的
        ⑥：responses：用于定义 API 操作的响应列表，包括成功响应和错误响应。可以使用@ApiResponse注解进一步定义响应。
        ⑦：security：用于对API操作进行安全控制，可以使用@SecurityRequirement注解进一步定义安全需求。（下个章节具体说）
        ⑧：deprecated：表示该API操作已经过时或不推荐使用。
        @Operation(
            summary = "根据用户标识号查询用户信息",
            description = "根据用户标识号查询用户信息，并返回响应结果信息",
//            security = @SecurityRequirement(name = "authScheme"), //定义单个认证
            parameters = {
                    @Parameter(name = "id", description = "用户标识号", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "ApiResult和User组合模型",
                                            description = "返回实体，ApiResult内data为User模型",
                                            anyOf = {ApiResult.class, User.class}),
                                    examples = @ExampleObject(
                                            name = "返回示例",
                                            summary = "返回示例",
                                            value =
                                                    "{\n" +
                                                            "  \"code\": 0,\n" +
                                                            "  \"msg\": \"操作成功\",\n" +
                                                            "  \"data\": {},\n" +
                                                            "  \"isSuccess\": true\n" +
                                                            "}"
                                    )
                            )
                    ),
```

#### 接口参数（Swagger3）

配置请求接口参数信息

```txt
注解：@Parameter用于描述HTTP请求的参数信息，它是一个Parameter[]类型的数组，每个元素表示一个请求参数；
    具体参考：io.swagger.v3.oas.annotations；它是一个注解，和Parameter类一样，只不过一个是注解一个是类的方式
        ①：name：参数名称。
        ②：in：参数位置，可以是 query、header、path、cookie 等。
        ③：description：参数描述。
        ④：required：参数是否必须，默认为 false。
        ⑤：deprecated：参数是否已过时，默认为 false。
        ⑥：allowEmptyValue：是否允许空值，默认为false。
        ⑦：style：参数的序列化风格，可以是 "matrix"、"label"、"form"、"simple"、
            "spaceDelimited"、"pipeDelimited"、"deepObject"；
        ⑧：explode：当参数值是对象或数组时，是否将其展开成多个参数，默认为 false。
        ⑨：schema：参数类型和格式的定义，通常使用@Schema注解。（下面介绍）
        ⑩：example：参数值的示例；
        示例：
            parameters = {
                    @Parameter(name = "id", description = "用户标识号", required = true, example = "1")
            },
```

#### 实体模型（Swagger3）

配置具体的实体模型信息

```txt
注解：@Schema 是用于描述数据模型的基本信息和属性，具体可以参考“io.swagger.v3.oas.annotations.media”
    具体属性：
        ①：description：用于描述该类或属性的作用。
        ②：name：指定属性名。该属性只对属性有效，对类无效。
        ③：title：用于显示在生成的文档中的标题。
        ④：requiredMode：用于指定该属性是否必填项。枚举Schema.RequiredMode内可选值如下：
            默认AUTO：可有可无；REQUIRED：必须存在此字段(会加红色*)；NOT_REQUIRED：不需要存在此字段
        ⑤：accessMode：用于指定该属性的访问方式。
            包括AccessMode.READ_ONLY（只读）、AccessMode.WRITE_ONLY（只写）、AccessMode.READ_WRITE（读写）
        ⑥：format：用于指定该属性的数据格式。例如：日期格式、时间格式、数字格式。
        ⑦：example：为当前的属性创建一个示例的值，后期测试可以使用此值。
        ⑧：deprecated：用于指定该属性是否为已过时的属性，默认为false。
        ⑨：defaultValue：用于指定该属性的默认值。
        ⑩：implementation：用于显示为该类或属性引入具体的实体路径，这代表当前指定的类或者属性将参考引入的实体。
            就是说有个实体类，这个类里面有个teacher属性，这时里面的teacher属性可以指定具体的实体类，如下：
            public class Student {
                ...
                @Schema(description = "老师信息",implementation = Teacher.class)
                private Teacher teacher;
                ...
            }
    其它属性：
        ①：type：用于指定数据类型（Data Type）或者元素类型（Element Type）
            基本类型：取值为相应的 Java 类型名，例如 int、long、float、double、boolean 等。
            包装类型：与基本类型相同，取值为相应的Java包装类型名，例如Integer、Long、Float、Double、Boolean等。
            字符串类型：取值为string。
            数组类型：取值为 array。对于数组类型，还可以使用 schema 属性指定其元素类型的 Schema 信息。
            对象类型：不用指定type，可以通过implementation属性引入。
            枚举类型：取值为enum。对于枚举类型，还需要使用enumAsRef属性指定是否将其定义为一个独立的引用类型。
            其它类型：不用指定type，可以通过implementation属性引入。

@Schema注解：提供了四个属性来描述复杂类型，分别是allOf、anyOf、oneOf和not。
    这四个属性可以用于组合不同的JSON Schema以描述一个复杂类型，具体如下：
    ①：allOf: 表示当前schema是多个其它schema的并集。
        例如，如果一个Java类型同时实现了两个接口，那么可以使用allOf来表示这个Java类型继承了这两个接口的所有属性和方法。
    ②：anyOf: 表示当前schema可以匹配其中任意一个schema，其本身也是一个组合体，可以嵌套使用。
        例如，一个返回类型可能是多个Java类型中的任意一个，可以使用anyOf来描述这种情况。
    ③：oneOf: 表示当前schema只能匹配其中一个schema，其本身也是一个组合体，可以嵌套使用。
        例如，一个Java类型只能是多个子类型中的任意一个，可以使用oneOf来描述这种情况。
    ④：not: 表示当前Schema不能匹配某个schema。
        例如，一个Java类型不能是某个子类型，可以使用not来描述这种情况。
    但是总感觉这个Swagger无法满足我特定要求的实体，具体解决如下：
        比如我现在有个AjaxResult类（code，msg，data），其中data为Object或其它类型，这时我返回的数据里data为其它类型的
        实体，所以我这里不理解如何返回的实体中，通过点击data而显示另外实体，我只能通过anyOf方式来实现（加上注解）
         @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "ApiResult和User组合模型",
                                            description = "返回实体，ApiResult内data为User模型",
                                            anyOf = {ApiResult.class, User.class}),
                                    examples = @ExampleObject(
                                            name = "返回示例",
                                            summary = "返回示例",
                                            value =
                                                    "{\n" +
                                                            "  \"code\": 0,\n" +
                                                            "  \"msg\": \"操作成功\",\n" +
                                                            "  \"data\": {},\n" +
                                                            "  \"isSuccess\": true\n" +
                                                            "}"
                                    )
                            )
                    ),
```

### 注解例子（Swagger3）

::::: tabs

@tab 接口定义

```java
package com.mcode.swaggertest.controller;

import com.mcode.swaggertest.common.ApiResult;
import com.mcode.swaggertest.entity.User;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UserController
 * Package: com.mcode.swaggertest.controller
 * Description:
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户控制器", description = "用户控制器描述",
        externalDocs = @ExternalDocumentation(
                description = "文档接口描述",
                url = "https://xxxx.example.org/xxxx/"))
public class UserController {
    /**
     * 根据ID查询用户信息（单条）
     * @param id
     * @return User
     */
    @GetMapping("/get/{id}")
    @Operation(
            summary = "根据用户标识号查询用户信息",
            description = "根据用户标识号查询用户信息，并返回响应结果信息",
//            security = @SecurityRequirement(name = "authScheme"), //定义单个认证
            parameters = {
                    @Parameter(name = "id", description = "用户标识号", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "ApiResult和User组合模型",
                                            description = "返回实体，ApiResult内data为User模型",
                                            anyOf = {ApiResult.class, User.class}),
                                    examples = @ExampleObject(
                                            name = "返回示例",
                                            summary = "返回示例",
                                            value =
                                                    "{\n" +
                                                            "  \"code\": 0,\n" +
                                                            "  \"msg\": \"操作成功\",\n" +
                                                            "  \"data\": {},\n" +
                                                            "  \"isSuccess\": true\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "ApiResult和User组合模型",
                                            description = "返回实体，ApiResult内data为null",
                                            implementation = ApiResult.class)
                            )
                    )
            }
    )
    public User get(@PathVariable(value = "id") Integer id) {
        User user = new User();
        user.setId(1);
        user.setName("xxxx");
        user.setEmail("xxxx@xxxx.com");
        user.setPassword("123456");
        user.setPhone("17873041739");
        return user;
    }
}
```

@tab 返回对象

通用返回对象定义

```java
package com.mcode.swaggertest.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName: ApiResult
 * Package: com.mcode.swaggertest.common
 * Description:
 */
@Data
@AllArgsConstructor
@Schema(description = "响应返回数据对象")
public class ApiResult {

    @Schema(
            title = "code",
            description = "响应码",
            format = "int32",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer code;

    @Schema(
            title = "msg",
            description = "响应信息",
            accessMode = Schema.AccessMode.READ_ONLY,
            example = "操作成功",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String msg;

    @Schema(title = "data", description = "响应数据", accessMode = Schema.AccessMode.READ_ONLY)
    private Object data;

    @Schema(title = "isSuccess",
            description = "是否成功",
            accessMode = Schema.AccessMode.READ_ONLY,
            format = "boolean",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSuccess;

    public static ApiResult success(Object data) {
        return new ApiResult(1, "操作成功", data, true
        );
    }

    public static ApiResult fail(String msg) {
        return new ApiResult(0, msg, null, false);
    }
}
```

@tab 模型定义

```java
package com.mcode.swaggertest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: User
 * Package: com.mcode.swaggertest.entity
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "用户实体",description = "用户实体描述")
public class User {
    @Schema(name = "用户标识号", description = "用户标识号描述", format = "int32", example = "1")
    private Integer id;
    @Schema(name = "用户名", description = "用户名描述", example = "xxx")
    private String name;
    @Schema(name = "邮件", description = "邮件描述", example = "xxx@xxx.com")
    private String email;
    @Schema(name = "密码", description = "密码描述", example = "123456")
    private String password;
    @Schema(name = "电话号码", description = "电话号码描述", example = "17873041739x")
    private String phone;
}
```

:::::

### swagger-core 原生

`swagger-core` 是 Swagger/OpenAPI 的开源 Java 实现，提供：

- `swagger-models`：OpenAPI 规范 Java 实现
- `swagger-core`：将 Java POJO 解析（注释）为 OpenAPI 模式，处理序列化/反序列化并提供集成机制。
- `swagger-jaxrs2`：将 JAX-RS（带注释的）资源解析为 OpenAPI 定义，并提供集成机制。
- `swagger-annotations`：一组注释，用于声明和操作由 和 / 或其他项目生成的输出。swagger-coreswagger-jaxrs2
- `swagger-maven-plugin` （自 2.0.5 起）：提供 Maven 插件以在构建时解析 OpenAPI 定义（使用）。请参阅模块自述文件 swagger-jaxrs2
- `swagger-gradle-plugin` （自 2.0.5 起）：提供 Gradle 插件以在构建时解析 OpenAPI 定义（使用）。请参阅模块自述文件 swagger-jaxrs2

```xml
swagger-core
- swagger-annotations
- swagger-models
swagger-maven-plugin
swagger-gradle-plugin
```

todo swagger-maven-plugin 使用
https://blog.csdn.net/qq_27720567/article/details/79430016
https://github.com/swagger-api/swagger-core/tree/master/modules/swagger-maven-plugin
https://github.com/openapi-tools/swagger-maven-plugin
https://community.smartbear.com/discussions/swaggerostools/
how-to-automatically-save-generated-swagger-json-into-a-local-file/188250

### ~~Springfox + Swagger2~~

::: warning
springfox 已不再被维护
:::

Springfox 是一套可以帮助 Java 开发者自动生成 API 文档的工具，它是基于 Swagger 2.x 基础上开发的。
Springfox 提供了一些注解来描述 API 接口、参数和返回值，并根据这些信息生成 Swagger UI 界面，从而方便其他开发人员查看和使用您的 API 接口。
此外，Springfox 还支持自动生成 API 文档和代码片段，简化了开发人员的工作量。除了集成 Swagger 2.x，Springfox 还提供了一些额外功能，例如自定义 Swagger 文档、API 版本控制、请求验证等等。这些功能使得 Springfox 可以胜任各种类型和规模的应用程序，同时还可以提高代码质量和开发效率。
总之，Springfox 是一个非常有用的工具，它可以帮助 Java 开发者快速、简单地集成 Swagger2.x，并为他们的应用程序生成高质量的 API 文档。无论您开发的是大型企业应用程序还是小型服务，使用 Springfox 都能够提高团队的生产力和代码质量。

::: warning
注意：
springfox 已不再被维护。
其坐标 `springfox-boot-starter` 从 3.0.0 版本（2020 年 7 月 14 日）开始就一直没有更新。
:::

坐标

```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.9.2</version>
</dependency>
```

启动类

```java
@EnableSwagger2
@SpringBootApplication
public class XxxApplication {
  public static void main(String[] args) {
    SpringApplication.run(Xxx.Application.class, args);
  }
}
```

开关配置

```yml title="application.yml"
swagger:
  enable: true
```

```yaml title="application.yml"
springfox:
  documentation:
    # 总开关（同时设置 auto-startup=false，否则/v3/api-docs等接口仍然能继续访问）
    enabled: false
    auto-startup: false
    swagger-ui:
      enabled: false
```

访问地址：（默认）
http://localhost:8080/swagger-ui.html

### Springdoc + Swagger3

官网： https://springdoc.org/ \
文档地址： https://springdoc.org/#properties

SpringDoc 对应坐标是 `springdoc-openapi-ui`，它是一个集成 Swagger UI 和 ReDoc 的接口文档生成工具。
其中除了可以生成 Swagger UI 风格的接口文档，还提供了 ReDoc 的文档渲染方式，可以自动注入 OpenAPI 规范的 JSON 描述文件，支持 OAuth2、JWT 等认证机制，并且支持全新的 OpenAPI 3.0 规范。

::: tip
**版本问题**：
在 Spring Boot 2.4 及以上版本中使用 `springdoc-openapi-ui` 坐标。
在 Spring Boot 3.0 及以上版本中使用 `springdoc-openapi-starter-webmvc-ui` 坐标。
:::

```xml
<!-- spring boot 3 -->
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.2</version>
</dependency>
<!-- spring boot 2 -->
<!-- <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.7.0</version>
</dependency> -->
```

参数

```yaml title="application.yml"
springdoc:
  api-docs:
    enabled: true # 是否开启
    path: "/docs"
  swagger-ui:
    path: ""
```

访问地址：（默认）
http://server:port/context-path/swagger-ui.html\
http://server:port/context-path/v3/api-docs

http://localhost:8080/swagger-ui/index.html \
http://localhost:8080/v3/api-docs

配置：
https://springdoc.org/#properties

### Knife4j

Swagger 是一个用于设计、构建、记录和使用 RESTful web 服务的开源软件框架。
Swagger 3（OpenAPI 3.0）提供了更加强大和灵活的 API 文档生成能力。
使用 Knife4j 作为 UI 界面。

```xml
<!--
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>3.0.0</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>3.0.0</version>
</dependency>
-->
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>knife4j-spring-boot-starter</artifactId>
  <version>3.0.2</version>
</dependency>

<!--swagger3-->
<!-- <dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.1.0</version>
</dependency> -->
```

::: tabs

@tab 配置类

```java
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("标题")
                    .contact(new Contact())
                    .description("我的API文档")
                    .version("v1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                    .description("外部文档")
                    .url("https://springshop.wiki.github.org/docs"));
    }
}
```

@tab 实体类

```java
// 实体类注解示例
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(name = "Employee", description = "$!{table.comment}")
public class Emp {
    @ExcelProperty("ID")
    @Schema(description = "ID")
    private int id;
    @ExcelProperty("用户名")
    @Schema(description = "用户名")
    private String names;
    @ExcelProperty("工资")
    @Schema(description = "工资")
    private double salary;
    @ExcelProperty("生日")
    @Schema(description = "生日")
    private Date birthday;
    @ColumnWidth(20)
    @ExcelProperty("头像")
    @Schema(description = "头像")
    private String photo;

//    @ColumnWidth(20)
//    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ExcelProperty("创建日期")
//    private Date u_create_time;
}
```

@tab 入口类

```java
// 控制层注解示例
import io.swagger.v3.oas.annotations.Operation;

@Operation(summary = "获取所有员工信息")
@GetMapping("/selectAll")
public List<Emp> selectAll() {
    // ...
}
```

:::

访问地址：

http://localhost:8080/doc.html \
http://localhost:8080/swagger-ui/index.html

## 案例：重写网关依赖，文档按微服务划分

```java
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
  // ...
}
```

todo

## 附录

## 参考

swagger 历史：

- Swagger 系列：Spring Boot 2.x 集成 Spring Doc（Swagger 3.0） https://www.cnblogs.com/vic-tory/p/17690501.html
