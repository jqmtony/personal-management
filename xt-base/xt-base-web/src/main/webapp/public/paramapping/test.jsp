<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
        <title>test</title>
</head>
<body>
        form表单提交映射List数据类型
        <form method="get" action="${pageContext.request.contextPath}/paramapping/form-list">
                <input name="username" type="text" value="aaa" />
                <input name="password" type="text" value="123" />


                <input type="submit" />
        </form>
</body>
</html>
