<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="timeWithOrder.jsp" %>
<%@include file="Md5.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>To Pay</title>
<script>
self.moveTo(0,0);
self.resizeTo(screen.availWidth,screen.availHeight);
</script>
<style> 
.tabPages{
margin-top:150px;text-align:center;display:block; border:3px solid #d9d9de; padding:30px; font-size:14px;
}
</style>
<!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="row" style="margin:15px; 0;">
          <div class="col-md-12">


             <%
                String  AuthorizationURL = "http://oa.399ex.net/Pay_Index.html";
                String  merchantId       = "10057";
                String  Md5key           = "tryqn5n6uv1z9819kch7x5ir9wphg8sy" ;
                String	pay_memberid     = merchantId;//商户id
                String	pay_orderid      = generateOrderId();//20位订单号 时间戳+6位随机字符串组成
                String	pay_applydate    = generateTime();//yyyy-MM-dd HH:mm:ss
                String	pay_notifyurl    = "http://"+request.getRequestURI()+"/pay-java/notify.asp";//通知地址
                String	pay_callbackurl  = "http://"+request.getRequestURI()+"/pay-java/callback.asp";//回调地址
                String	pay_amount       = "0.01";
                String  pay_productname  = "测试商品";
                String  stringSignTemp   = "pay_amount="+pay_amount+"&pay_applydate="+pay_applydate+"&pay_callbackurl="+pay_callbackurl+"&pay_memberid="+pay_memberid+"&pay_notifyurl="+pay_notifyurl+"&pay_orderid="+pay_orderid+"&key="+Md5key+"";
                String  md5sign          =  md5(SignTemp , 32 , 1);//MD5加密
             %>

             <form action="<%=AuthorizationURL%>" method="post">
                <input type="hidden" name="pay_memberid"     value="<%=pay_memberid%>">
                <input type="hidden" name="pay_orderid"      value="<%=pay_orderid%>">
                <input type="hidden" name="pay_applydate"    value="<%=pay_applydate%>">
                <input type="hidden" name="pay_notifyurl"    value="<%=pay_notifyurl%>">
                <input type="hidden" name="pay_callbackurl"  value="<%=pay_callbackurl%>">
                <input type="hidden" name="pay_amount"       value="<%=pay_amount%>">
                <input type="hidden" name="pay_productname"  value="<%=pay_productname%>">
                <input type="hidden" name="pay_md5sign"      value="<%=pay_md5sign%>">
             </form>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</html>