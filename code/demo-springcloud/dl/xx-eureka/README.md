# 说明

```bash
java -jar xxx.jar --server.port=8701
java -jar xxx.jar --spring.profiles.active=eureka8701
```

## 功能：身份认证

服务端

```bash
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

spring.security.user.name=cat
spring.security.user.password=123456

// 设置 csrf 防护关闭，让服务能被接收和注册
@Configuration
@EnableWebSecurity
public class EurekaSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity hhtp) throws Exception {
    http.csrf().disable();
    super.configure(http);
  }
}
```

客户端

```bash
eureka.client.service-url.defaultZone=http://cat:123456@localhost:8761/eureka
```
