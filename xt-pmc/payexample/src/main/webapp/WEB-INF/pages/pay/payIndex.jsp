<%@ page import="cn.xt.pay.model.Payment" %>
<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<%
    Payment payment = (Payment) request.getAttribute("payment");
%>
<form id="payForm" action="<%=Payment.AUTHENTICATION_URL%>" method="post">
    <input type="hidden" name="accountId"     value="<%=Payment.MEMBER_ID%>">
    <input type="hidden" name="name"      value="<%=payment.getOrderNumber()%>">
    <input type="hidden" name="cardNo"    value="<%=payment.getApplyDate()%>">
    <input type="hidden" name="purpose"    value="<%=payment.getNotifyUrl()%>">
    <input type="hidden" name="amount"  value="<%=payment.getCallbackUrl()%>">
    <input type="hidden" name="responseUrl"       value="<%=payment.getAmmount()%>">
    <input type="hidden" name="mac"      value="<%=payment.getSign()%>">
</form>
请稍候...
<script>
    window.onload = function(){
        if("<%=payment.getGoodsName()%>" && "<%=payment.getAmmount()%>"){
           // document.getElementById("payForm").submit();
        }
    }

</script>
</body>
</html>
