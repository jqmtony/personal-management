<%@page import="java.io.PrintWriter" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="cn.xt.pay.model.Payment" %>
<%@ page import="cn.xt.pay.service.PaymentService" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<%
    PaymentService service = new PaymentService();
    Payment payment = service.generatePayment(request, "测试商品", "0.01");

    String returncode = request.getParameter("returncode");
    String sign = request.getParameter("sign");

    String md5sign = payment.getSign();

    PrintWriter pw = response.getWriter();

    pw.write("sign => " + sign + " : md5sign => " + md5sign);

    System.out.println("sign=" + sign);
    System.out.println("md5sign=" + md5sign);
    System.out.println("returncode=" + returncode);

    if (sign.equals(md5sign)) {

        if (returncode.equals("00")) {
            System.out.println("支付成功，返回OK");
            //支付成功，写返回数据逻辑
            pw.write("ok");
        } else {
            System.out.println("支付失败。。。");
            pw.write("支付失败");
        }
    } else {
        System.out.println("验证签名失败");
        pw.write("验签失败");
    }
%>

</body>
</html>