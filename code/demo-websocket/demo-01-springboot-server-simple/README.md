# demo-01-springboot-server-simple

参考：

+ <https://springdoc.cn/spring-boot-websocket/>

websocket是h5中提供的在单个tcp连接上进行全双工通信的协议。

标准：
RFC6455定义了websocket的通信标准。

java：
JSR356定义了websocket在java中的api。
web容器，如tomcat的`tomcat-websocket.jar`中实现了上述接口。

```xml
<dependency>
    <groupId>javax.websocket</groupId>
    <artifactId>javax.websocket-api</artifactId>
    <version>1.1</version>
    <scope>provided</scope>
</dependency>
```
