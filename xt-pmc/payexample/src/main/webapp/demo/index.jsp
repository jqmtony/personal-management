<%@ page import="cn.xt.pay.model.PaymentVo" %>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>付费测试用例</title>
</head>
<body>
<%
    PaymentVo payment = new PaymentVo();
%>
<form id="payForm" action="https://oa.399ex.net/delegate-pay-front/delegatePay/pay" method="post">
    用户名：<input type="text" name="name"      ><br/>
    银行卡号：<input type="text" name="cardNo"    ><br/>
    目的：<input type="text" name="purpose"    ><br/>
    金额：<input type="text" name="amount"  ><br/>
    <input type="hidden" name="accountId"      value="<%=payment.getAccountId()%>">
    <input type="hidden" name="orderId"      value="<%=payment.getOrderId()%>">
    <input type="hidden" name="responseUrl"      value="<%=payment.getResponseUrl()%>">
    <input type="hidden" name="mac"      value="<%=payment.getMac()%>">
    <button>提交</button>
</form>
</body>
</html>