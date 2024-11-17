---
title: JJwt 使用
date: 2024-11-16
order: 66
---


```xml
<!--JWT令牌-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.12.5</version>
</dependency>
```

JWT（JSON Web Token）令牌登录，是一种用于身份验证的开放标准。
它是一个基于 JSON 格式的安全令牌，主要用于在网络上传输声明或者用户身份信息。
JWT 通常被用作 API 的认证方式，以及跨域身份验证。

jwt 优点：

- 支持跨域访问：cookie 是无法跨域的，而 token 由于没有用到 cookie （前提是将 token 放到请求头中），所以跨域后不会存在信息丢失问题
- 无状态：token 机制在服务端不需要存储 session 信息，因为 token 自身包含了所有登录用户的信息，所以可以减轻服务端压力
- 更适用 CDN：可以通过内容分发网络请求服务端的所有资料
- 更适用于移动端：当客户端是非浏览器平台时，cookie 是不被支持的，此时采用 token 认证方式会简单很多
- 无需考虑 CSRF：由于不再依赖 cookie，所以采用 token 认证方式不会发生 CSRF，所以也就无需考虑 CSRF 的防御

JWT 令牌由三部分组成，分别是头部（Header）、载荷（Payload）和签名（Signature）：

- Header：记录令牌类型，加密算法。
- Payload：包含声明，声明是有关实体（通常是用户）和其他数据的语句。

  - 已注册声明：这些是一组预定义的声明，这些声明不是必需的，但建议使用，以提供一组有用的、可互操作的声明。其中一些是：iss（发行人），exp（到期时间），sub（主题），aud（受众）等。

    ```
    注册的声明（建议但不强制使用）：
    iss: jwt签发者
    sub: jwt所面向的用户
    aud: 接收jwt的一方
    exp: jwt的过期时间，这个过期时间必须要大于签发时间
    nbf: 定义在什么时间之前，该jwt都是不可用的.
    iat: jwt的签发时间
    jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
    ```

  - 公共声明：这些可以由使用 JWT 的人随意定义。但为了避免冲突，它们应该在 IANA JSON Web 令牌注册表中定义，或者定义为包含抗冲突命名空间的 URI。
  - 专用声明：这些是创建的自定义声明，用于在同意使用它们的各方之间共享信息，既不是注册声明也不是公共声明。

- Signature：签名，防止被篡改。(header+payload+密钥)\*签名算法。

JWT 两种主要使用方式

- JWS (JSON Web Signature): `<header>.<payload>.<signature>` —— 签名的 JWT 由三个部分组成：头部（Header）、载荷（Payload）和签名（Signature）。这种方式下，JWT 是签名的，确保消息的完整性和真实性。签名的目的是防止数据被篡改，并验证发送者的身份。常见的签名算法包括 HMAC、RSA 和 ECDSA。

  常见：登录功能在一般情况下，不需要在用户端保存敏感信息，也就不用加密处理；但需要保证用户请求不被伪造、篡改，就需要进行数字前面。所以一般用 jws 实现登录验证。

- JWE (JSON Web Encryption): `<header>.<encryptedKey>.<iv>.<ciphertext>.<tag>` —— 加密的 JWT 包含五个部分：头部（Header）、加密密钥（Encrypted Key）、初始化向量（Initialization Vector）、密文（Ciphertext）和认证标签（Authentication Tag）。这种方式下，JWT 是加密的，用于保护敏感数据。加密的目的是确保只有授权方能够读取数据内容。常见的加密算法包括 AES 和 RSA。

  常见：加密的 JWT 对载荷进行加密处理，使得未经授权的一方无法读取载荷内容。

::: tabs

@tab 哈希签名方式

```java
package com.ioufev;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        // 使用固定密钥：使用固定的字符串生成 HMAC 密钥，方便在开发和测试时使用。
//        String secretKey = "r-k.Uv4D@rrX2aYiLOJJC-)!XBD=-#[^i,&vykXQYtU6p.pF4'xQ#GZ-4AS+ri)vhBEAyQFfpZ+";
//        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 根据物理地址哈希得到，服务每次重启都不一样。
        // 使用生成密钥：使用 HS512 算法生成对称密钥。每次生成的密钥不同，因为是基于系统资源（如物理地址）的哈希值。
        MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
        SecretKey key = alg.key().build();

        // 设置自定义声明：：可选的自定义的声明，即设置到信息中的键值对
        Map<String, Object> inputClaims = new HashMap<>();
        inputClaims.put("name", "张三");

        // 签名方式或者加密方式，二选一
        String token = Jwts.builder()
                .issuer("auth-server") // 可选，令牌的发行者，在解析 JWT 时可以验证这个值来确保它是由预期的授权服务器签发的。
                .subject("李四")
                .claims(inputClaims)
                .audience().add("api-client").and() // 可选，令牌的受众, 在解析 JWT 时可以验证这个值，确保令牌是为特定的客户端或 API 生成的。
//                .notBefore(notBefore) // 可选，java.util.Date，不早于
                .issuedAt(new Date()) // 设置签发时间为当前时间。
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))  // 设置 JWT 的过期时间为 30 分钟后。
                .id(UUID.randomUUID().toString()) // 设置一个随机生成的唯一标识符。
                .signWith(key) // 签名方式
//                .encryptWith(key, Jwts.ENC.A256CBC_HS512) // 加密方式，使用 AES 对称加密方式对 JWT 进行加密。
                .compact(); // 生成 JWT 并压缩为一个字符串形式


        System.out.println(token);

        // 解析，对应的签名或者加密方式
        Claims claims = Jwts.parser()
                .verifyWith(key) // 指定用于验证的密钥
//                .decryptWith(key) // 加密方式，指定用于解密的密钥。
                .build()
                .parseSignedClaims(token) // 签名方式
//                .parseEncryptedClaims(token) // 加密方式，对 JWT 进行解密，并获取其声明部分。
                .getPayload(); // 获取解密后的声明（claims）内容。
        System.out.println(claims.getSubject());
        System.out.println(claims.get("name"));
    }
}
```

@tab 对称加密方式

```java
package com.ioufev;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        // 使用固定密钥：使用固定的字符串生成 HMAC 密钥，方便在开发和测试时使用。
//        String secretKey = "r-k.Uv4D@rrX2aYiLOJJC-)!XBD=-#[^i,&vykXQYtU6p.pF4'xQ#GZ-4AS+ri)vhBEAyQFfpZ+";
//        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 根据物理地址哈希得到，服务每次重启都不一样。
        // 使用生成密钥：使用 HS512 算法生成对称密钥。每次生成的密钥不同，因为是基于系统资源（如物理地址）的哈希值。
        MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
        SecretKey key = alg.key().build();

        // 设置自定义声明：：可选的自定义的声明，即设置到信息中的键值对
        Map<String, Object> inputClaims = new HashMap<>();
        inputClaims.put("name", "张三");

        // 签名方式或者加密方式，二选一
        String token = Jwts.builder()
                .issuer("auth-server") // 可选，令牌的发行者，在解析 JWT 时可以验证这个值来确保它是由预期的授权服务器签发的。
                .subject("李四")
                .claims(inputClaims)
                .audience().add("api-client").and() // 可选，令牌的受众, 在解析 JWT 时可以验证这个值，确保令牌是为特定的客户端或 API 生成的。
//                .notBefore(notBefore) // 可选，java.util.Date，不早于
                .issuedAt(new Date()) // 设置签发时间为当前时间。
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))  // 设置 JWT 的过期时间为 30 分钟后。
                .id(UUID.randomUUID().toString()) // 设置一个随机生成的唯一标识符。
//                .signWith(key) // 签名方式
                .encryptWith(key, Jwts.ENC.A256CBC_HS512) // 加密方式，使用 AES 对称加密方式对 JWT 进行加密。
                .compact(); // 生成 JWT 并压缩为一个字符串形式


        System.out.println(token);

        // 解析，对应的签名或者加密方式
        Claims claims = Jwts.parser()
                .verifyWith(key) // 指定用于验证的密钥
                .decryptWith(key) // 加密方式，指定用于解密的密钥。
                .build()
//                .parseSignedClaims(token) // 签名方式
                .parseEncryptedClaims(token) // 加密方式，对 JWT 进行解密，并获取其声明部分。
                .getPayload(); // 获取解密后的声明（claims）内容。
        System.out.println(claims.getSubject());
        System.out.println(claims.get("name"));
    }
}
```

:::
