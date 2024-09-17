package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Slf4j
public class HttpURLConnTest extends BaseTest {

    @Test
    void test_hello() throws IOException {
        log.info("test HttpURLConnection");
        URL url = new URL(HOST + "/hello");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);
        urlConnection.setUseCaches(false);
        urlConnection.connect();

        String content = IOUtils.readLines(urlConnection.getInputStream(), Charsets.UTF_8).stream().collect(Collectors.joining("\n"));
        log.info("resp: {}", content);
    }
}
