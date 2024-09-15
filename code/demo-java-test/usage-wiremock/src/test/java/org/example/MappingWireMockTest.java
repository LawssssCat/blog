package org.example;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Slf4j
public class MappingWireMockTest {
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

    @RegisterExtension
    static WireMockExtension wm1 = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().usingFilesUnderClasspath("myWiremockFiles"))
            .configureStaticDsl(true) // Configuring the static DSL
            .build();

    @Test
    void test() throws Exception {
        getContent("http://127.0.0.1:"+wm1.getPort()+"/mock");
    }
}
