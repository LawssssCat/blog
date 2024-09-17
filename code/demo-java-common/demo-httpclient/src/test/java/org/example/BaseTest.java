package org.example;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Slf4j
public class BaseTest {
    @RegisterExtension
    static WireMockExtension WM1 = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().usingFilesUnderClasspath("myWiremockFiles").templatingEnabled(true))
            .build();

    static String HOST;

    @BeforeAll
    static void beforeEach() {
        log.warn("WM: {}", WM1);
        HOST = "http://127.0.0.1:"+ WM1.getPort();
        log.debug("HOST: {}", HOST);
    }

    @Test
    void test_setup() {
        log.info("hello world!");
    }
}
