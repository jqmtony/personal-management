package cn.xt.pay;

import cn.xt.base.util.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RequestTest {
    private static final String APP_KEY = "fj6m9i769p2jdby3evjiinniw0kuro8w";
    //商户id
    private static final String MEM_ID = "10066";

    @Test
    public void test() throws Exception {
        CloseableHttpClient client = HttpClientUtil.defaults();

        String md5Key = md5(APP_KEY);
        String ordId = genOrdId();
        String applyDate = generateTime();

        String url = "http://oa.399ex.net/Pay_Index.html";
        Map<String,String> params = new HashMap<>();
        params.put("pay_memberid",MEM_ID);
        params.put("pay_orderid",ordId);
        params.put("pay_applydate",applyDate);
        params.put("pay_notifyurl","http://qwkxq.cn");
        params.put("pay_callbackurl","http://qwkxq.cn");
        params.put("pay_amount","0.01");
        params.put("pay_productname","测试商品");
        params.put("pay_md5sign",md5Key);
        String response = HttpClientUtil.post(client,url,params);
        System.out.println(response);
    }

    static String genOrdId(){
        String keyup_prefix=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String keyup_append=String.valueOf(new Random().nextInt(899999)+100000);
        String pay_orderid=keyup_prefix+keyup_append;//订单号
        return pay_orderid;
    }

    public String generateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    static String md5(String key) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        messageDigest.update(key.getBytes());
        String password = new BigInteger(1, messageDigest.digest()).toString(16);
        return password;
    }
}
