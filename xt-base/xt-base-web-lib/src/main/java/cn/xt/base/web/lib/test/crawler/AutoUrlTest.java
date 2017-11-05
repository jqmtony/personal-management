package cn.xt.base.web.lib.test.crawler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoUrlTest {
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getHttpHeaders(URL url, int timeout) {
        try {
            trustEveryone();
            Connection conn = HttpConnection.connect(url);
            conn.timeout(timeout);
            conn.header("Accept-Encoding", "gzip,deflate,sdch");
            conn.header("Connection", "close");
            conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
            conn.get();
//            conn.data("username","ctanonymous");
//            conn.data("password","c1ntn1t:)");
//            conn.data("grant_type","password");
//            conn.data("continue","https://account.shodan.io/?language=en");
            String result=conn.response().body();
//            Map<String, String> result = conn.response().headers();
//            result.put("title", conn.response().parse().title());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        try {
//            URL url = new URL("http", "onlinelibrary.wiley.com/subject/code/000017", -1, "");
//            System.out.println(getHttpHeaders(url, 10000));

            Map<String,String> data = new HashMap<String,String>();
            data.put("username","ctanonymous");
            data.put("password","c1ntn1t:)");
            data.put("grant_type","password");
            data.put("continue","https://account.shodan.io/?language=en");
            /*Connection.Response resp = Jsoup.connect("https://account.shodan.io/login")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .method(Connection.Method.POST)
                    .execute();
            Map<String,String> cookies = resp.cookies();
            for(Map.Entry<String,String> cookie : cookies.entrySet()){
                System.out.println(cookie.getKey()+"="+cookie.getValue());
            }*/

            //创建httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // get method
            HttpPost httpPost = new HttpPost("https://account.shodan.io/login");

            //装填参数
            List<BasicNameValuePair> nvps = new ArrayList<>();
            if(data!=null){
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            //设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            //headers
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            HttpResponse response = httpClient.execute(httpPost);
            httpPost.abort();//释放post请求

            //处理http返回码302的情况
            if (response.getStatusLine().getStatusCode() == 302) {
                String locationUrl=response.getLastHeader("Location").getValue();
                Header setCookie = response.getHeaders("set-cookie")[0];


                HttpGet httpGet = new HttpGet("https://www.shodan.io/search?query=SIP/2.0%20200%20OK");

                httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
                httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

                httpGet.setHeader(setCookie.getName() ,setCookie.getValue());
                HttpResponse resp = httpClient.execute(httpGet);

                String html = EntityUtils.toString(resp.getEntity(), "UTF-8");
                Document doc = Jsoup.parse(html);
                System.out.println("/////////////////////////////////////////////////");
                Element element = doc.select("form.form-horizontal").get(0);
                String token = element.select("[name=csrf_token]").val();
                System.out.println(token);

                String downloadurl = "https://www.shodan.io/data/export" +
                        "?query=SIP/2.0%20200%20OK" +
                        "&csrf_token="+token +
                        "&filetype=json" +
                        "&credits=0";
                System.out.println(downloadurl);
                HttpGet downloadPage = new HttpGet(downloadurl);

                downloadPage.setHeader("Content-type", "application/x-www-form-urlencoded");
                downloadPage.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                downloadPage.setHeader(setCookie.getName() ,setCookie.getValue());

                HttpResponse downResp = httpClient.execute(downloadPage);

                String downHTML = EntityUtils.toString(downResp.getEntity(), "UTF-8");

                System.out.println(downHTML);
                File file = new File("C:\\Users\\lucy\\Desktop\\result.html");
                OutputStream out = new FileOutputStream(file);
                out.write(downHTML.getBytes());
                out.close();
            }

            /*String session = resp.cookie("__cfduid");
//            https://www.shodan.io/data/download/59ad1908e449851ea56d9c7e
            Connection conn = Jsoup.connect("https://www.shodan.io/data")
//                    .cookies(cookies)
                    .cookie("__cfduid",session)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .method(Connection.Method.GET);
            Document doc = conn.get();
            System.out.println(doc.title());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
