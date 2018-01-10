<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.math.BigInteger" %>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<%!
    public String genOrdId(){
        String keyup_prefix=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String keyup_append=String.valueOf(new Random().nextInt(899999)+100000);
        String pay_orderid=keyup_prefix+keyup_append;//订单号
        return pay_orderid;
    }

    public String generateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String md5(String key) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        messageDigest.update(key.getBytes());
        String password = new BigInteger(1, messageDigest.digest()).toString(16);
        return password;
    }
%>
<%
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int port = request.getServerPort();
    String path = request.getContextPath();
    String basePath = scheme + "://" + serverName + ":" + port + path;

    String APP_KEY = "fj6m9i769p2jdby3evjiinniw0kuro8w";
    String MEM_ID = "10066";
    String url = "http://oa.399ex.net/Pay_Index.html";
    String ordId = genOrdId();
    String applyDate = generateTime();
    String notifyUrl = basePath+"/pay/payCallback.jsp";
    String callbackUrl = basePath+"/pay/paySuccess.jsp";
    String ammout = "0.01";
    String goodsName = "测试商品";
    String signUrl = "pay_amount="+ammout
            +"&pay_applydate="+applyDate
            +"&pay_callbackurl="+callbackUrl
            +"&pay_memberid="+MEM_ID
            +"&pay_notifyurl="+notifyUrl
            +"&pay_orderid="+ordId
            +"&key="+APP_KEY;
    String sign = md5(signUrl).toUpperCase();
%>
<form action="<%=url%>" method="post">
    <input type="hidden" name="pay_memberid"     value="<%=MEM_ID%>">
    <input type="hidden" name="pay_orderid"      value="<%=ordId%>">
    <input type="hidden" name="pay_applydate"    value="<%=applyDate%>">
    <input type="hidden" name="pay_notifyurl"    value="<%=notifyUrl%>">
    <input type="hidden" name="pay_callbackurl"  value="<%=callbackUrl%>">
    <input type="hidden" name="pay_amount"       value="<%=ammout%>">
    <input type="hidden" name="pay_productname"  value="<%=goodsName%>">
    <input type="hidden" name="pay_md5sign"      value="<%=sign%>">
    <input type="submit" value="提交" />
</form>
</body>
</html>
