通过传递参数，以两种模式启动 Spring Boot 程序：

```bash
# 普通模式：DAO + Web + some bean
# + DAO
# + Web
# + XxBean init
java -jar .\demo-cli-0.0.1-SNAPSHOT.jar

# 简易模式：DAO + a task
# + DAO
# + a task: say hello
java -jar .\demo-cli-0.0.1-SNAPSHOT.jar --plain
```
