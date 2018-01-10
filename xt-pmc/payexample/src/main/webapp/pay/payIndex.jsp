<%@ page import="cn.xt.pay.service.PaymentService" %>
<%@ page import="cn.xt.pay.model.Payment" %>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<%
    PaymentService service = new PaymentService();
    Payment payment = service.generatePayment(request,"测试商品","0.01");
%>
<form action="<%=Payment.AUTHENTICATION_URL%>" method="post">
    <input type="hidden" name="pay_memberid"     value="<%=Payment.MEMBER_ID%>">
    <input type="hidden" name="pay_orderid"      value="<%=payment.getOrderNumber()%>">
    <input type="hidden" name="pay_applydate"    value="<%=payment.getApplyDate()%>">
    <input type="hidden" name="pay_notifyurl"    value="<%=payment.getNotifyUrl()%>">
    <input type="hidden" name="pay_callbackurl"  value="<%=payment.getCallbackUrl()%>">
    <input type="hidden" name="pay_amount"       value="<%=payment.getAmmount()%>">
    <input type="hidden" name="pay_productname"  value="<%=payment.getGoodsName()%>">
    <input type="hidden" name="pay_md5sign"      value="<%=payment.getSign()%>">
    <input type="submit" value="提交" />
</form>
</body>
</html>
