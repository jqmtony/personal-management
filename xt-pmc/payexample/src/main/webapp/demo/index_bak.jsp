<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>付费测试用例</title>
</head>
<body>
<form id="submitForm" action="${pageContext.request.contextPath}/delegate-pay-front/delegatePay/pay" method="post">
    用户姓名：<input type="text"  name="name" /><br/>
    价格：<input type="text"  name="amount" /><br/>
    客户编号：<input type="text"  name="accountId" /><br/>
    银行卡号：<input type="text"  name="cardNo" /><br/>
    付款目的：<input type="text"  name="purpose" /><br/>
    <button>提交</button>
</form>
</body>
</html>