package org.example.framework.web.service;

import java.util.HashMap;
import java.util.Map;

import org.example.common.constant.Constants;
import org.example.common.core.domain.model.LoginUser;
import org.example.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        // loginUser.setToken(token);
        // setUserAgent(loginUser);
        // refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
            // 已注册声明
            .setIssuer("auth-server") // 可选，令牌的发行者，在解析 JWT 时可以验证这个值来确保它是由预期的授权服务器签发的。
            .setSubject("李四") // 可选，令牌发放的对象
            // 公共声明
            .setClaims(claims)
            // .audience().add("api-client").and() // 可选，令牌的受众, 在解析 JWT 时可以验证这个值，确保令牌是为特定的客户端或 API 生成的。
            // .notBefore(notBefore) // 可选，java.util.Date，不早于
            // .issuedAt(new Date()) // 设置签发时间为当前时间。
            // .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 设置 JWT 的过期时间为 30 分钟后。
            // .id(UUID.randomUUID().toString()) // 设置一个随机生成的唯一标识符。
            .signWith(SignatureAlgorithm.HS512, secret) // 签名方式
            // .encryptWith(key, Jwts.ENC.A256CBC_HS512) // 加密方式，使用 AES 对称加密方式对 JWT 进行加密。
            .compact();
        return token;
    }
}
