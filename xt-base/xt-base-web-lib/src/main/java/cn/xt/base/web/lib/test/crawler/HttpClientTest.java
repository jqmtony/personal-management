/*
package cn.xt.base.web.lib.test.crawler;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.types.FileList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projo.SearchKey;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class HttpClientTest {
*
     * 定义手机号码段


    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,173,180"
            .split(",");

*
     * 获取随机范围内的数字


    public static int getNum(int start, int end) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
//        return random.nextInt( (end - start + 1) + start);
        return random.nextInt(Math.abs(start - end));
    }

*
     * 获取伪造的手机号


    private static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + " " + second + " " + thrid;
    }

*
     * 获取伪造的ip


    public static String getIPProxy() {
        StringBuffer sb = new StringBuffer();
        sb.append(getNum(2, 254));
        sb.append(".");
        sb.append(getNum(2, 254));
        sb.append(".");
        sb.append(getNum(2, 254));
        sb.append(".");
        sb.append(getNum(2, 254));
        return sb.toString();
    }

    public static void main2(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getIPProxy());
        }
    }

*
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果


    public static void post(String url, String number, String ip_proxy) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Connection", "keep-alive");
        httppost.setHeader("Referer", "从哪个网站连入过来的");
        httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
        httppost.setHeader("x-forwarded-for", ip_proxy);//伪造的ip地址
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("phone", number));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //        // 创建默认的httpClient实例.
    static CloseableHttpClient httpclient = HttpClients.createDefault();
    //    static String searchKey="SIP%2F2.0+200+OK";
    static String page = "1";
    static String loginUrl = "https://account.shodan.io/login";
    static String searchUrl = "https://www.shodan.io/search?query=";
    static String summaryUrl = "https://www.shodan.io/search/_summary?query=";
    static String jsonUrl = "https://www.shodan.io/data/export?query=" + "" + "&csrf_token=6bd5e348698b9efc8d11a8c532907670d1e80b49&filetype=json&credits=0";
    static String downUrl = "https://www.shodan.io/data";

    public static void main(String[] args) {
*
         * 获取文件


        getIPs_Search();
        getIPs_json();
*
         * ip入库


        JsonToIP.JsontoIP_Dir("D:\\VOS IP\\write", "购买");
    }

    //getIPs_json
    public static void getIPs_json() {
*
         * json


        try {
*
             * d登录  setcookie


            Header setcookie = login();
*
             * token


            CloseableHttpResponse responseSummary = doit(searchUrl + "SIP/2.0%20200%20OK", "get", null, setcookie);
            String htmlSummary = EntityUtils.toString(responseSummary.getEntity(), "UTF-8");
            org.jsoup.nodes.Document docSummary = Jsoup.parse(htmlSummary);
            String token = docSummary.select("input[name=csrf_token]").attr("value");
            System.out.println(token);
*
             * 准备导出


            String jsonURl = "https://www.shodan.io/data/export?query=SIP/2.0%20200%20OK&csrf_token=" + token + "&filetype=json&credits=0";
            CloseableHttpResponse responseJson = doit(jsonURl, "get", null, setcookie);
            String jsonHtml = EntityUtils.toString(responseJson.getEntity(), "UTF-8");
            org.jsoup.nodes.Document jsonDoc = Jsoup.parse(jsonHtml);
            System.out.println(jsonDoc.select("div.alert.alert-error"));
            System.out.println("开始沉睡10分钟,等待网站导出json文件");
//            Thread.sleep(10*60*1000);
            if(responseJson.getStatusLine().getStatusCode() == 200){

            }
*
             * 获取导出的json文件


            CloseableHttpResponse responseDown = doit(downUrl, "get", null, setcookie);
            String htmlDown = EntityUtils.toString(responseDown.getEntity(), "UTF-8");
            org.jsoup.nodes.Document docDown = Jsoup.parse(htmlDown);
            Elements as = docDown.select("tbody a.btn.btn-success");
            System.out.println(as.size());
            int num = 0;
            String dowmLoadURL = "";
            n:
            for (Element a : as) {
                num++;
                if (num == 1) {
                    //  下载路径
                    dowmLoadURL = "https://www.shodan.io" + a.attr("href");
                    System.out.println(dowmLoadURL);
                    break n;
                }
            }
            CloseableHttpResponse responseDownLoad = doit(dowmLoadURL, "get", null, setcookie);
            HttpGet httpget = new HttpGet(dowmLoadURL);
            httpget.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpget.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            File file = new File("D:\\VOS IP\\jsonDownLoad\\SIP2.0%20200%20OK" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".rar");
            try {
                FileOutputStream fout = new FileOutputStream(file);
                int l = -1;
                byte[] tmp = new byte[1024];
                while ((l = in.read(tmp)) != -1) {
                    fout.write(tmp, 0, l);
                }
                fout.flush();
                fout.close();
            } finally {
                // 关闭低层流。
                in.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

*
         * 解压缩 查找下载的文件夹里当天下载的压缩文件


        try {
            // 获取要压缩的文件
            List<String> listName = FileList.getList("D:\\VOS IP\\jsonDownLoad");

            // 解压该文件
            for (String fname : listName) {
                // 输出文件名
                String outName = fname.replace("jsonDownLoad", "write").replace("rar", "json");
                System.out.println(fname);
                System.out.println(outName);
                UnCompressFile.unGzipFile(fname, outName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

*
     * 获取查询的ip


    public static void getIPs_Search() {
        try {
*
             * 登录 cookie


            Header setcookie = login();
            System.out.println(setcookie);
*
             * c查询


            List<SearchKey> searchKeyList = HttpClientDao.getSearchKeys();
            ExecutorService executorServer = Executors.newFixedThreadPool(10);
            for (SearchKey searchKey : searchKeyList) {
                Future<Boolean> future = executorServer.submit(new GetIps(searchKey, setcookie));
            }
            executorServer.shutdown();
            System.out.println("-------------end-----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*
     * 登录 setcookie



    public static Header login() {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", "ctanonymous"));
        formparams.add(new BasicNameValuePair("password", "c1ntn1t:)"));
        formparams.add(new BasicNameValuePair("grant_type", "password"));
        formparams.add(new BasicNameValuePair("continue", "https://account.shodan.io/?language=en"));
*
         * 登录 cookie


        CloseableHttpResponse response = doit(loginUrl, "post", formparams, null);
        Header setcookie = response.getHeaders("set-cookie")[0];
        System.out.println(setcookie);
        return setcookie;
    }

    static class GetIps implements Callable<Boolean> {
        SearchKey searchKey;
        Header setcookie;

        public GetIps(SearchKey searchKey, Header setcookie) {
            this.searchKey = searchKey;
            this.setcookie = setcookie;
        }

        @Override
        public Boolean call() throws Exception {
*
             * 查询 总数


            CloseableHttpResponse responseSummary = doit(summaryUrl + (searchKey.getSearchKey()), "get", null, setcookie);
            String htmlSummary = EntityUtils.toString(responseSummary.getEntity(), "UTF-8");
            org.jsoup.nodes.Document docSummary = Jsoup.parse(htmlSummary);
            String strNums = docSummary.select("div.bignumber").html().replace(",", "");
            int counts = Integer.parseInt(strNums);
            int pages = (counts - searchKey.getCounts()) <= 0 ? 0 : (int) ((counts - searchKey.getCounts()) / 10) + 1;
            String url = searchUrl + searchKey.getSearchKey();
            System.out.println(searchKey.getSearchKey() + " 页数 : " + pages + "  记录数差: " + (counts - searchKey.getCounts()) + " 条");
            // 更改关键词使之能作为文件名
            if (searchKey.getSearchKey().indexOf("Call") != -1) {
                searchKey.setSearchKey("call_id");
            } else if (searchKey.getSearchKey().indexOf("SIP") != -1) {
                searchKey.setSearchKey("SIP20");
            }
            String path = searchKey.getSearchKey() + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".json";
            FileOutputStream out = new FileOutputStream("D:\\VOS IP\\write\\" + path, true);
            String firstIp = "";
            n:
            for (int i = 1; i <= pages; i++) {
                if (i == 201) {
                    break n;
                }
                System.out.println("搜索条件:" + searchKey.getSearchKey() + " 页数:" + i);
                CloseableHttpResponse responseSearch = doit(url + "&page=" + i, "get", null, setcookie);
                String html = EntityUtils.toString(responseSearch.getEntity(), "UTF-8");
                org.jsoup.nodes.Document doc = Jsoup.parse(html);
                Elements es = doc.select("div.ip > a");
                int num = 0;
                for (Element e : es) {
                    System.out.println(e.html());
                    num++;
                    out.write(("{" + "\"ip_str\": \"" + e.html() + "\"}" + "\r\n").getBytes());
                    if (num == 1) {
                        firstIp = e.html();
                    }
                }
                System.out.println(searchKey.getSearchKey() + " IP数量:" + num);
            }
            out.close();
            System.out.println(searchKey.getSearchKey() + " 已完成!");
            searchKey.setCounts(counts);
            searchKey.setFirtIP(firstIp);
            HttpClientDao.updateSearchKey(searchKey);
            return null;
        }
    }

*
     * 执行方法



    public static CloseableHttpResponse doit(String url, String method, List<NameValuePair> formparams, Header setcookie) {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            if (method.equalsIgnoreCase("get")) {
                HttpGet httpGet1 = new HttpGet(url);
                httpGet1.setHeader("Content-type", "application/x-www-form-urlencoded");
                httpGet1.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                httpGet1.setHeader("Connection", "keep-alive");
                httpGet1.setHeader("Referer", "https://www.baidu.com");
                httpGet1.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
                httpGet1.setHeader("x-forwarded-for", getIPProxy());//伪造的ip地址
                if (setcookie != null)
                    httpGet1.setHeader(setcookie.getName(), setcookie.getValue());
                closeableHttpResponse = httpclient.execute(httpGet1);
            } else {
                HttpPost httppost = new HttpPost(url);
                httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
                httppost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                httppost.setHeader("Connection", "keep-alive");
                httppost.setHeader("Referer", "https://www.baidu.com");
                httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
                httppost.setHeader("x-forwarded-for", getIPProxy());//伪造的ip地址
                if (setcookie != null)
                    httppost.setHeader(setcookie.getName(), setcookie.getValue());
                UrlEncodedFormEntity uefEntity = null;
                if (formparams != null)
                    uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httppost.setEntity(uefEntity);
                closeableHttpResponse = httpclient.execute(httppost);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return closeableHttpResponse;
    }
}
*/
