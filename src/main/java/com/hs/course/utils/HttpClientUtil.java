package com.hs.course.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientUtil {
    /**
     * 连接池中的最大连接数
     */
    private final static int MAX_CONN_TOTAL = 5_000;
    /**
     * 连接同一个route最大的并发数
     */
    private final static int MAX_CONN_PER_ROUTE = 2_000;
    /**
     * 从连接池中获取可用连接最大超时时间 单位：毫秒
     */
    private final static int CONNECT_REQUEST_TIMEOUT = 2000;
    /**
     * 连接目标url最大超时 单位：毫秒
     */
    private final static int CONNECT_TIMEOUT = 2_000;
    /**
     * 等待响应（读数据）最大超时 单位：毫秒
     */
    private final static int SOCKET_TIMEOUT = 20_000;
    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;

    static {
        requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        httpClient = HttpClientBuilder.create().setMaxConnTotal(MAX_CONN_TOTAL).setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setDefaultRequestConfig(requestConfig).build();
    }
    private HttpClientUtil() {
    }
    /**
     * get请求
     *
     * @param url     请求URL
     * @param params  参数
     * @param headers 请求头
     * @return String
     * @throws Exception 异常类
     */
    public static String get(final String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        String result;
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);

            if (null != params) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            java.net.URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            if (null != headers) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 执行发送请求
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return result;
    }


    /**
     * get请求
     *
     * @param url 请求url
     * @return String
     * @throws Exception 异常类
     */
    public static String get(final String url) throws Exception {
        return get(url, null, null);
    }

    /**
     * @param url    请求url
     * @param params 参数
     * @return String
     * @throws Exception 异常类
     */
    public static String get(final String url, Map<String, String> params) throws Exception {
        return get(url, params, null);
    }




}
