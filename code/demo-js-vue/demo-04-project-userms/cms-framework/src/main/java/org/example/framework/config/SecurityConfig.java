package org.example.framework.config;

import javax.annotation.Resource;

import org.example.framework.security.filter.JwtAuthenticationTokenFilter;
import org.example.framework.security.handle.AuthenticationEntryPointImpl;
import org.example.framework.security.handle.LogoutSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security配置
 *
 * @see <a href="https://springdoc.cn/spring-enablemethodsecurity-annotation/">@EnableMethodSecurity 注解</a>
 */
@EnableMethodSecurity(
    prePostEnabled = true, // 开启 @PreAuthorize、@PostAuthorize、@PreFilter、@PostFilter 注解支持
    securedEnabled = true // 开启 @Secured 注解支持
)
@Configuration
public class SecurityConfig {
    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 身份验证实现
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    /**
     * <table>
     * <tr><th>鉴权行为</th><th>说明</th></tr>
     * <tr><td>anyRequest</td><td>匹配所有请求路径</td></tr>
     * <tr><td>access</td><td>SpringEl表达式结果为true时可以访问</td></tr>
     * <tr><td>anonymous</td><td>匿名可以访问</td></tr>
     * <tr><td>denyAll</td><td>用户不能访问</td></tr>
     * <tr><td>fullyAuthenticated</td><td>用户完全认证可以访问（非remember-me下自动登录）</td></tr>
     * <tr><td>hasAnyAuthority</td><td>如果有参数，参数表示权限，则其中任何一个权限可以访问</td></tr>
     * <tr><td>hasAnyRole</td><td>如果有参数，参数表示角色，则其中任何一个角色可以访问</td></tr>
     * <tr><td>hasAuthority</td><td>如果有参数，参数表示权限，则其权限可以访问</td></tr>
     * <tr><td>hasIpAddress</td><td>如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问</td></tr>
     * <tr><td>hasRole</td><td>如果有参数，参数表示角色，则其角色可以访问</td></tr>
     * <tr><td>permitAll</td><td>用户可以任意访问</td></tr>
     * <tr><td>rememberMe</td><td>允许通过remember-me登录的用户访问</td></tr>
     * <tr><td>authenticated</td><td>用户登录后可访问</td></tr>
     * </table>
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            // 基于token，所以不需要session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // CSRF禁用，因为不使用session
            .csrf(csrf -> csrf.disable())
            // 禁用HTTP响应标头
            .headers((headersCustomizer) -> {
                headersCustomizer.cacheControl(cache -> cache.disable()) // 禁用缓存
                    .frameOptions(options -> options.sameOrigin()); // 同源
            })
            // 认证失败处理类
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            // 注解标记允许匿名访问的url
            .authorizeHttpRequests((requests) -> {
                // TODO @Anonymous
                // permitAllUrl.getUrls().forEach(url -> requests.antMatchers(url).permitAll());
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                requests.antMatchers("/login", "/register", "/captchaImage").permitAll()
                    // 静态资源，可匿名访问
                    .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                    .antMatchers("/test/**").permitAll()
                    // .antMatchers("/swagger-ui.html", "/swagger-resources/**",
                    // "/webjars/**","/*/api-docs","/druid/**")
                    // .permitAll()
                    // 除上面外的所有请求全部需要鉴权认证
                    .anyRequest().authenticated();
            })
            // 添加Logout filter
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler))
            .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class)
            .addFilterBefore(corsFilter, LogoutFilter.class)
            .build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
