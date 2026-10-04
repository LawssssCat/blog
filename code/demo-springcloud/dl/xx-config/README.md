# 说明

Spring Cloud Config 支持管理git仓库（默认）或者文件系统（`spring.profiles.active=native`）两种配置仓库格式。

配置文件映射规则： （[link_scc文档](http://springcloud.cc/spring-cloud-config.html)）
- `/{application}/{profile}[/{label}]` —— 查看信息
- `/{application}-{profile}.properties` —— 查看内容
- `/{label}/{application}-{profile}.properties`

路径关系：

``````bash
配置根路径：
spring.profiles.active=native
spring.cloud.config.server.native.search-locations=file:./xx-config/config-repo
System.getProperty("user.dir") + /config-repo
code/demo-springcloud/dl/xx-config/config-repo

访问：
config-repo/test-dev.properties
config-repo/{application}-{profile}.properties
+ 查看信息
http://localhost:9300/application/dev
http://localhost:9300/application/dev/master
```bash
{
  "name":"application",
  "profiles":["dev"],
  "label":null,
  "version":null,
  "state":null,
  "propertySources":[
    {"name":"file:xx-config/config-repo/application-dev.properties","source":{"my.name":"hello-world"}}
  ]
}
```
+ 读取内容
http://localhost:9300/test-dev.properties
http://localhost:9300/master/test-dev.properties
```bash
my.name: hello-world
```
+ 自动转换格式
http://localhost:9300/application-dev.yaml
```bash
my:
  name: hello-world
```
``````
