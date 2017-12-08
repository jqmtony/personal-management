package cn.xt.base.util;

import org.apache.commons.codec.Charsets;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.ErrorType;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.*;

public class HttpClientUtil {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientUtil.custom();
        /*RequestErrorCallBackExecutors.add(new RequestErrorCallBack() {
            @Override
            public void error() {
                System.out.println("aaa");
            }
        });*/
        String html = HttpClientUtil.get(httpClient, "http://www.sohu.com", null);
//        System.out.println(html);
        html = HttpClientUtil.get(httpClient, "http://ip.taobao.com/service/getIpInfo.php?ip=125.69.102.76", null);
//        System.out.println(html);

        Map<String, String> params = new HashMap<>();
        params.put("minId","12090");
        params.put("pageSize","20");
        html = HttpClientUtil.post(httpClient,"http://192.168.1.225:8080/api/hawkEye/findPageByMinId",params);
        System.out.println(html);
    }


    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 模拟浏览器的headers
     */
    private static final Map<String, String> SIMULATE_HEADERS = new HashMap<>();

    static {
        SIMULATE_HEADERS.put("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
        SIMULATE_HEADERS.put("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
        SIMULATE_HEADERS.put("Accept-Encoding", "utf-8");
        SIMULATE_HEADERS.put("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        SIMULATE_HEADERS.put("Keep-Alive", "300");
        SIMULATE_HEADERS.put("connnection", "keep-alive");
        SIMULATE_HEADERS.put("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
        SIMULATE_HEADERS.put("If-None-Match", "1261d8-4290-df64d224");
        SIMULATE_HEADERS.put("Cache-conntrol", "max-age=0");
        SIMULATE_HEADERS.put("Referer", "http://www.baidu.com");
    }

    public static CloseableHttpClient defaults() {
        return HttpClients.createDefault();
    }

    public static CloseableHttpClient custom() {
        return HttpClients.custom()
                .setConnectionManager(CustomHttpClientsConfig.getConnectionManager())
                .setRetryHandler(CustomHttpClientsConfig.getRetryHandler())
                .build();
    }

    public static String get(CloseableHttpClient httpClient, String url, Map<String, String> params) throws IOException {
        url = RequestParamsHelper.generateGetUrl(url,params);
        HttpGet get = new HttpGet(url);
        initRequest(get);
        HttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            logger.error("HttpClientRequestUtil Response Error,Status Code = {}", response
                    .getStatusLine().getStatusCode());
            for (Header header : response.getAllHeaders()) {
                logger.error("HttpClientRequestUtil Response Error,Header Info = {}", header);
            }
            RequestErrorCallBackExecutors.execute();
            get.abort();
            return null;
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, Charsets.UTF_8);
        return result;
    }

    public static String post(CloseableHttpClient httpClient, String url, Map<String, String> params,boolean...isJson) throws IOException {
        HttpPost post = new HttpPost(url);
        initRequest(post);
        if(isJson.length==0){
            RequestParamsHelper.setPostForm(post, params);
        }else{
            if(isJson[0]){
                RequestParamsHelper.setPostJson(post,params);
            }
        }
        HttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() != 200) {
            logger.error("HttpClientRequestUtil Response Error,Status Code = {}", response
                    .getStatusLine().getStatusCode());
            for (Header header : response.getAllHeaders()) {
                logger.error("HttpClientRequestUtil Response Error,Header Info = {}", header);
            }
            RequestErrorCallBackExecutors.execute();
            post.abort();
            return null;
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, Charsets.UTF_8);
        return result;
    }

    /**
     * 请求执行前初始化
     *
     * @param httpRequestBase
     */
    public static void initRequest(HttpRequestBase httpRequestBase,Map<String,String>...headersMap) {
        //模拟浏览器Headers
        SIMULATE_HEADERS.forEach((k, v) -> {
            httpRequestBase.setHeader(k, v);
        });
        if(headersMap!=null && headersMap.length!=0){
            headersMap[0].forEach((k,v) ->{
                httpRequestBase.setHeader(k,v);
            });
        }

        //请求配置项
        RequestConfig reqConfig = RequestConfig
                .custom()
                .setSocketTimeout(2000)// 传输超时
                .setConnectTimeout(2000)// 连接超时
                .build();
        httpRequestBase.setConfig(reqConfig);
    }

    /**
     * httpClient请求参数辅助类
     */
    private static class RequestParamsHelper {
        /**
         * 设置Post请求表单格式参数
         *
         * @param httpost
         * @param params
         * @throws UnsupportedEncodingException
         */
        private static void setPostForm(HttpPost httpost,
                                        Map<String, String> params) throws UnsupportedEncodingException {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
                httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8.displayName()));
            }
        }

        /**
         * 设置PostJSON格式参数
         *
         * @param httpPost
         * @param params
         */
        private static void setPostJson(HttpPost httpPost, Map<String, String> params) {
            if (params != null) {
                String json = JsonUtils.toJson(params);
                StringEntity requestEntity = new StringEntity(json, Consts.UTF_8);
                requestEntity.setContentEncoding(Consts.UTF_8.displayName());
                requestEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                httpPost.setEntity(requestEntity);
            }
        }

        /**
         * get请求，通过url拼接设置参数
         *
         * @param url
         * @param params
         */
        private static String generateGetUrl(String url, Map<String, String> params) throws IOException {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key)));
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, Charsets.UTF_8));
            }
            return url;
        }
    }

    /**
     * 自定义HttpClient配置项
     */
    private static class CustomHttpClientsConfig {
        /**
         * 获取Httpclient 请求重试处理策略
         *
         * @return
         */
        private static HttpRequestRetryHandler getRetryHandler() {
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                public boolean retryRequest(IOException exception,
                                            int executionCount, HttpContext context) {
                    if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }

                    HttpClientContext clientContext = HttpClientContext
                            .adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };
            return httpRequestRetryHandler;
        }

        /**
         * 获取HttpClient 并发连接管理器
         *
         * @return
         */
        private static PoolingHttpClientConnectionManager getConnectionManager() {
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                    .getSocketFactory();
            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                    .getSocketFactory();

            Registry<ConnectionSocketFactory> registry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", plainsf)
                    .register("https", sslsf)
                    .build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                    registry);
            // 将最大连接数,即总线程不能超过100
            connectionManager.setMaxTotal(100);
        /*
            设置路由到每个连接的最大并发量
            这里即同时请求httpClient管理url的线程最大不能超过10
         */
            connectionManager.setDefaultMaxPerRoute(10);

            //单独为某个url设置最大并发量
        /*HttpHost httpHost = new HttpHost("url", 000);
        connectionManager.setMaxPerRoute(new HttpRoute(httpHost), 10);*/
            return connectionManager;
        }
    }

    /**
     * 请求出错回调函数类
     */
    public static interface RequestErrorCallBack {
        void error();
    }

    private static class RequestErrorCallBackExecutors {
        private static final List<RequestErrorCallBack> REQUEST_ERROR_CALLBACKS = new LinkedList<>();
        public static void add(RequestErrorCallBack... callBacks){
            if(callBacks!=null){
                for (RequestErrorCallBack cb : callBacks) {
                    if (cb != null) {
                        REQUEST_ERROR_CALLBACKS.add(cb);
                    }
                }
            }
        }
        public static void execute() {
            for (RequestErrorCallBack cb : REQUEST_ERROR_CALLBACKS) {
                cb.error();
            }
            REQUEST_ERROR_CALLBACKS.clear();
        }
    }
}
