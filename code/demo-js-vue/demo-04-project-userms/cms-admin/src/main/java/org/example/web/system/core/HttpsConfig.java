package org.example.web.system.core;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * HTTPS的接口配置
 */
@Slf4j
@Configuration
public class HttpsConfig {
    @Value("${http.port:}")
    private Integer httpPort;
    @Value("${server.port}")
    private Integer httpsPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        // 创建Tomcat服务器工厂实例
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        // 添加Tomcat实例http连接参数
        Connector connector = createStandardConnector();
        tomcat.addAdditionalTomcatConnectors(connector);
        log.info("Connector [{}] ready", connector);
        return tomcat;
    }

    /**
     * 配置http
     *
     * @return connector
     */
    private Connector createStandardConnector() {
        // Connector port有两种运行模式(NIO和APR)，选择NIO模式：protocol="org.apache.coyote.http11.Http11NioProtocol"
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        // 启用http（80）端口
        connector.setScheme("http");
        // 设置安全连接标志，该标志将被分配给通过该连接接收的请求
        // secure新的安全连接标志
        // 如果connector.setSecure(true)，则http使用http, https使用https; 分离状态，因此设置false
        connector.setSecure(false);
        // http默认端口
        connector.setPort(httpPort == null ? httpsPort + 1 : httpPort);
        // 重定向证书端口443，便于http自动跳转https
        connector.setRedirectPort(httpsPort);
        return connector;
    }
}
