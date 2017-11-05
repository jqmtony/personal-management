package cn.xt.base.web.lib.test.crawler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AutoDownloadIp {
    private static final String SEARCH_KEY = "SIP/2.0 200 OK"; //SIP/2.0 200 OK
    private static final String LOGIN_URL = "https://account.shodan.io/login";
    private static final String SEARCH_URL = "https://www.shodan.io/search?query=%s";
    private static final String SEARCH_SUMARY_URL = "https://www.shodan.io/search/_summary?query=%s";
    private static final String EXPORT_URL = "https://www.shodan.io/data/export?query=%s&csrf_token=%s&filetype=json&credits=%s";

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void main(String[] args) throws Exception {

        Map<String,String> data = new HashMap<String,String>();
        data.put("username","ctanonymous");
        data.put("password","c1ntn1t:)");
        data.put("grant_type","password");
        data.put("continue","https://account.shodan.io/?language=en");

        HttpResponse loginResp = process(LOGIN_URL,"post",data);

        Header setCookie = loginResp.getHeaders("set-cookie")[0];
        /*if (loginResp.getStatusLine().getStatusCode() == 302) {
            String locationUrl = loginResp.getLastHeader("Location").getValue();
        }*/

        String searchKey = URLEncoder.encode(SEARCH_KEY,"UTF-8");

        System.out.println("查询总记录数,关键词："+SEARCH_KEY);
        String searchSumaryURL = String.format(SEARCH_SUMARY_URL,searchKey);
        HttpResponse searchSumaryResp = process(searchSumaryURL,"get",null,setCookie);
        String searchSumaryHtml = EntityUtils.toString(searchSumaryResp.getEntity(), "UTF-8");
        Document leftdoc = Jsoup.parse(searchSumaryHtml);

        System.out.print(SEARCH_KEY+" 总记录数:");
        Elements totalelement = leftdoc.select(".bignumber");
        System.out.println(totalelement.html());
        System.out.println();

        //获取每条记录的ip和国家
        int r = Integer.parseInt(totalelement.html().replace(",",""))/10;
        int page = Integer.parseInt(totalelement.html().replace(",",""))%10==0?r:r+1;

        ExecutorService pool = Executors.newFixedThreadPool(100);

        System.out.println("查询所有记录...");

        String fileName = searchKey+"_"+new SimpleDateFormat("yyyyMMdd").format(new Date());
        String path = "C:\\Users\\lucy\\Desktop\\autoGetUrl\\"+fileName;
        File file = new File(path);
        if(file.exists())file.delete();

        for(int i=1; i<=page; i++){
            Future<Integer> future = pool.submit(new SearchTask(searchKey,i,setCookie,path));
            if(future.get()>0){
                System.out.println("获取第"+future.get()+"页完毕。");
            }
        }

        pool.shutdown();
        System.out.println("程序结束。");

        /*String exprotURL = String.format(EXPORT_URL,searchKey,token,0);
        HttpResponse exportResp = process(exprotURL,"get",null,setCookie);

        String exportHtml = EntityUtils.toString(exportResp.getEntity(), "UTF-8");
        File file = new File("C:\\Users\\lucy\\Desktop\\result.html");
        OutputStream out = new FileOutputStream(file);
        out.write(exportHtml.getBytes());
        out.close();*/
    }

    static class SearchTask implements Callable<Integer> {
        private int page;
        private String searchKey;
        private Header setCookie;
        private String path ;
        public SearchTask(String searchKey,int page,Header setCookie,String path){
            this.page = page;
            this.setCookie = setCookie;
            this.searchKey = searchKey;
            this.path = path;
        }

        @Override
        public Integer call() throws Exception {
            try {
                String searchURL = String.format(SEARCH_URL,searchKey)+"&page="+page;
                HttpResponse searchResp = process(searchURL,"get",null,setCookie);
                String searchHtml = EntityUtils.toString(searchResp.getEntity(), "UTF-8");
                Document rightdoc = Jsoup.parse(searchHtml);

                /*Element tokenelement = rightdoc.select("form.form-horizontal").get(0);
                String token = tokenelement.select("[name=csrf_token]").val();
                System.out.println("token is:"+token);*/

                OutputStream out = new FileOutputStream(path,true);

                System.out.println(SEARCH_KEY+" 第"+page+"页数据...");
                Elements searchResults = rightdoc.select("#search-results .search-result");
                for(Element e : searchResults){
                    String ip = e.select(".ip a").get(0).html();
                    String country = e.select(".search-result-summary .city").get(0).html();
                    System.out.println(ip+"="+country);
                    out.write((ip+"="+country+"\r\n").getBytes());
                }
                out.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return page;
        }
    }

    static HttpResponse process(String url,String method,Map<String,String> params,Header... header){

        HttpUriRequest httpReq = ("get".equalsIgnoreCase(method))?new HttpGet(url):new HttpPost(url);

        httpReq.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpReq.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        for(Header h : header){
            httpReq.setHeader(h.getName(),h.getValue());
        }

        HttpResponse resp = null;
        try {
            if("get".equalsIgnoreCase(method)){
                HttpGet get = (HttpGet) httpReq;
                resp = httpClient.execute(get);
            }else{
                HttpPost post = (HttpPost) httpReq;
                if(params!=null){
                    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                    post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                }
                resp = httpClient.execute(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
