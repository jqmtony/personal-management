<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
        <title>${siteInfo.titlePrefix}登录</title>
</head>
<body>
        请您登录：<br/>
        <form method="post" action="${pageContext.request.contextPath}/login">
                <input name="username" /><br/>
                <input name="password" type="password"/><br/>
                <input type="submit" value="登录" />
        </form>
</body>
</html>
