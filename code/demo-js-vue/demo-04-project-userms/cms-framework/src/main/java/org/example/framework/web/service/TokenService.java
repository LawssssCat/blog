package org.example.framework.web.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.example.cache.context.CacheContext;
import org.example.common.constant.CacheConstants;
import org.example.common.constant.Constants;
import org.example.common.core.domain.model.LoginUser;
import org.example.common.utils.StringUtils;
import org.example.common.utils.key.UUIDUtils;
import org.example.common.utils.web.IpUtils;
import org.example.common.utils.web.UserAgentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {
    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    @Resource
    private CacheContext cacheContext;

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String loginUserKey = UUIDUtils.fastUUID();

        // 刷新登录令牌
        loginUser.setToken(loginUserKey);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        // 返回客户端需要记录的登录信息（后续接口请求需要携带该信息，以表明客户端用户身份）
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, loginUserKey);
        return createToken(claims);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    private void setUserAgent(LoginUser loginUser) {
        // 解析客户端IP信息
        String clientIp = IpUtils.getClientIpAddr();
        loginUser.setIpaddr(clientIp);
        loginUser.setLoginLocation(IpUtils.getRealAddressByIP(clientIp));
        // 解析客户端软件信息
        loginUser.setBrowser(UserAgentUtils.getBrowser());
        loginUser.setOs(UserAgentUtils.getPlatform());
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    private void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        cacheContext.setObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
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
            // TODO 完成
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

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String loginUserKey = (String)claims.get(Constants.LOGIN_USER_KEY);
                String tokenKey = getTokenKey(loginUserKey);
                return cacheContext.getObject(tokenKey, LoginUser.class);
            } catch (Exception e) {
                log.error("获取用户信息异常: {}", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void veirfyToken(LoginUser loginUser) {
        // TODO ???
        // long expireTime = loginUser.getExpireTime();
        // long currentTime = System.currentTimeMillis();

    }
}
