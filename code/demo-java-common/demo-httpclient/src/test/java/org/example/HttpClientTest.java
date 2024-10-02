package org.example;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.classic.MinimalHttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpClientTest extends BaseTest {
    /**
     * 最小实现，一般不使用
     */
    @Deprecated
    @Test
    void test_hello_min() throws IOException, ParseException {
        // 客户端
        MinimalHttpClient minimal = HttpClients.createMinimal();
        // 请求/响应
        CloseableHttpResponse response = minimal.execute(new HttpGet(HOST + "/hello"));
        // 解析响应
        showResponse(response);
    }

    /**
     * 基本使用 GET
     */
    @Test
    void test_hello() throws IOException, ParseException {
        log.info("test HttpClient");

        // finial 关闭
        try (CloseableHttpClient aDefault = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(HOST + "/hello");

            // 模拟真实浏览器： 解决服务器对客户端的限制
            httpGet.addHeader("User-Agent", "e.g. chrome ...");
            // 防盗链
            httpGet.addHeader("Referer", "相关网站网址 ...");
            // MIME type, Multipurpose Internet Mail Extensions, 多用途互联网邮件扩展类型
            // Content-Type | 内容类型 | application/x-www-form-urlencoded （普通表单）, multipart/form-data （文件上传表单）, text/plain, application/json
            // Content-Transfer-Encoding | 编码格式 | 8bit, binary
            // Content-Disposition | 内容编排方式 | 上传文件时： Content-Disposition: form-data; name="filename"; filename="C:\Users\lenovo\Desktop\a.html" ； 下载文件时： Content-Disposition: attachment; filename=URLEncoder.encode("xx.zip", "UTF-8")

            showResponse(aDefault.execute(httpGet));
        }
    }

    /**
     * 基本使用 POST
     * Json 形式
     */
    @Test
    void test_hello_post() throws IOException, ParseException {
        log.info("test HttpClient post");

        try (CloseableHttpClient aDefault = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(HOST + "/hello");

            // headers
            httpPost.setHeader("content-type", ContentType.APPLICATION_JSON);

            // body
            JSONObject param = new JSONObject();
            param.put("xx", "xx~~");
            param.put("name", "test-01");
            param.put("password", "123456");
            HttpEntity entity = new StringEntity(param.toString());
            httpPost.setEntity(entity);

            showResponse(aDefault.execute(httpPost));
        }
    }

    /**
     * 基本使用 POST
     * 表单形式
     */
    @Test
    void test_hello_post_form() throws IOException, ParseException {
        log.info("test HttpClient post");

        try (CloseableHttpClient aDefault = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(HOST + "/hello");

            // 参数
            ArrayList<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("xx", "xx~~xx"));
            nvps.add(new BasicNameValuePair("name", "java5678"));
            nvps.add(new BasicNameValuePair("password", "78910"));

            // body
            HttpEntity entity = new UrlEncodedFormEntity(nvps);
            httpPost.setEntity(entity);

            showResponse(aDefault.execute(httpPost));
        }
    }

    /**
     * 主要配置
     */
    @Test
    void test_hello_custom() throws IOException, ParseException {
        log.info("test HttpClient custom");

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                /*
                 * 适用于需要传输大量数据的请求场景。
                 * 通过合理调整SocketTimeout的值，可以在数据传输过程中给予足够的时间，确保数据能够完整传输，避免因网络延迟或数据传输量大而导致的超时错误。
                 */
                .setSocketTimeout(5000, TimeUnit.MILLISECONDS) // 与服务器建立连接后，等待读写数据的超时时间 e.g. 响应体过大，在规定时间内没读取安全，就抛出 java.net.SocketTimeoutException: Read timed out
                /*
                 * 适用于网络不稳定或者服务器响应较慢的场景。
                 * 适当增加ConnectTimeout的值，可以给予HttpClient更多的时间来建立连接，减少因网络波动导致的连接失败情况。
                 */
                .setConnectTimeout(5000, TimeUnit.MILLISECONDS) // 向服务器请求建立连接的等待超时时间 | 三次握手成功的等待时间 e.g. IP不存，就抛出 java.net.SocketTimeoutException: connect timed out
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                /*
                 * 适用于高并发场景下，多个请求同时竞争连接池中的连接资源。
                 * 通过合理设置ConnectionRequestTimeout，可以防止因连接资源不足而导致的请求等待时间过长，确保请求能够及时处理。
                 */
                .setConnectionRequestTimeout(5000, TimeUnit.MILLISECONDS) // 从连接池请求一个连接的等待超时时间
                .setResponseTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(); // 连接池管理器
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setMaxTotal(300); // 配置最大的连接数
        connectionManager.setDefaultMaxPerRoute(20); // 最大路由数

        try (CloseableHttpClient aDefault = HttpClients.custom() // 连接客户端
                .setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build()) {
            HttpGet httpGet = new HttpGet(HOST + "/hello"); // 连接请求
            httpGet.setConfig(requestConfig);
            showResponse(aDefault.execute(httpGet));
        }
    }

    boolean isProxyHttpHostNotLive() {
        // todo
        return true;
    }

    /**
     * 配置代理
     */
    @DisabledIf(value = "isProxyHttpHostNotLive", disabledReason = "代理主机不存活")
    @Test
    void test_proxy() throws IOException, ParseException {
        CloseableHttpClient aDefault = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(new HttpHost(URIScheme.HTTP.getId(), "127.0.0.1", 10809)) // 设置 http 代理
                .build();
        HttpGet httpGet = new HttpGet(HOST + "/hello");
        httpGet.setConfig(requestConfig);
        showResponse(aDefault.execute(httpGet));
    }

    private void showResponse(CloseableHttpResponse response) throws IOException, ParseException {
        Assertions.assertEquals(HttpStatus.SC_OK, response.getCode());

        Header[] headers = response.getHeaders();
        log.info("headers: {}", Arrays.asList(headers));

        // 内容解析工具
        HttpEntity entity = response.getEntity();
        // 解析响应头
        log.info("Content-Type: {}", entity.getContentType());
        log.info("Content-Encoding: {}", entity.getContentEncoding());
        log.info("Content-Length: {}", entity.getContentLength());
        // 解析响应体
        String content = EntityUtils.toString(entity); // for 文字
//        byte[] byteArray = EntityUtils.toByteArray(entity); // for 二进制文件，如：图片
        log.info("content with apache util: {}", content);

        // 确保流关闭
        EntityUtils.consume(entity);
    }

    /**
     * 异步调用
     * 5.0 新特性
     */
    @Test
    void test_async() {
        // todo
    }
}
