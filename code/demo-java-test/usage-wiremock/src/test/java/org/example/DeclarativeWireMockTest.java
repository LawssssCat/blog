package org.example;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Slf4j
@WireMockTest(
        httpPort = 28899, // fix port, defaulting to a random port
        httpsEnabled = false,
        proxyMode = true // 代理域名 e.g. withHost(equalTo("one.my.domain"))
)
public class DeclarativeWireMockTest {
    CloseableHttpClient client;

    @BeforeEach
    void init() {
        client = HttpClientBuilder.create()
                .useSystemProperties() // This must be enabled for auto proxy config
                .build();
    }

    private String getContent(String url) throws Exception {
        log.warn("get-url: {}", url);
        String content = null;
        try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            content = EntityUtils.toString(response.getEntity());
        }
        log.debug("content: {}", content);
        return content;
    }

    @BeforeEach
    void beforeEach(WireMockRuntimeInfo wmRuntimeInfo) {
        log.info("mock wit @WireMockTest");

        // The static DSL will be automatically configured for you
        stubFor(get("/static-dsl").willReturn(ok("1")));

        // Instance DSL can be obtained from the runtime info parameter
        WireMock wireMock = wmRuntimeInfo.getWireMock();
        wireMock.register(get("/instance-dsl").willReturn(ok("2")));

        // Info such as port numbers is also available
        int httpPort = wmRuntimeInfo.getHttpPort();
        Assertions.assertThrows(IllegalStateException.class, () -> wmRuntimeInfo.getHttpsPort());
        log.info("mock with @WireMockTest, httpPort={}, httpsPort={}", httpPort, null);

        // Do some testing...
    }

    @RegisterExtension
    static // 💡如果声明为静态，则只创建/销毁一次；如果没声明为静态，则每个方法前创建/销毁一次
    WireMockExtension wm1 = WireMockExtension.newInstance()
            .options(wireMockConfig()
                            .dynamicPort()
                            .dynamicHttpsPort() // 除了声明，还需要 “相关配置”
                    // .extensions(new ResponseTemplateTransformer(true) // 指定模板文件
            )
            // .configureStaticDsl(true) // Configuring the static DSL
            .failOnUnmatchedRequests(true) // 如果收到不匹配的请求，则会抛出断言错误，并且测试将失败
            .proxyMode(true) // 开启代理模式，代理域名 e.g. withHost(equalTo("one.my.domain"))
            .build();

    @BeforeEach
    void beforeEach01() {
        log.info("mock with vm1");
        // You can get ports, base URL etc. via WireMockRuntimeInfo
        WireMockRuntimeInfo wm1RuntimeInfo = wm1.getRuntimeInfo();
        int httpsPort = wm1RuntimeInfo.getHttpsPort();

        // or directly via the extension field
        int httpPort = wm1.getPort();

        log.info("mock with vm1, httpsPort={}, httpPort={}", httpsPort, httpPort);

        // You can use the DSL directly from the extension field
        wm1.stubFor(get("/api-1-thing").willReturn(ok("3")));
        wm1.stubFor(get("/api-2-stuff").withHost(equalTo("one.my.domain")).willReturn(ok("4"))); // proxy mode
    }

    @Test
    void test(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
        log.info("hello world!");

        Assertions.assertEquals("1", getContent("http://127.0.0.1:"+wmRuntimeInfo.getHttpPort()+"/static-dsl"));
        Assertions.assertEquals("2", getContent("http://127.0.0.1:"+wmRuntimeInfo.getHttpPort()+"/instance-dsl"));
        Assertions.assertEquals("3", getContent("http://127.0.0.1:"+wm1.getPort()+"/api-1-thing"));
        Assertions.assertEquals("4", getContent("http://one.my.domain/api-2-stuff")); // proxy mode
    }
}
