<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
        <title>${siteInfo.titlePrefix}登录</title>
        <link href="${pageContext.request.contextPath}/public/css/login.css" rel="stylesheet"/>
</head>
<body>
<%--请您登录：<br/>
<form method="post" action="${pageContext.request.contextPath}/login">
        <input name="username" /><br/>
        <input name="password" type="password"/><br/>
        <input type="submit" value="登录" />${errorMsg}
</form>--%>

<section class="login-form-wrap">
        <h1>欢迎来到qwkxq.cn</h1>
        <form class="login-form" method="post" action="${ctx}/login">
                <label>
                        <input type="text" name="username" required placeholder="请输入用户名">
                </label>
                <label>
                        <input type="password" name="password" required placeholder="请输入密码">
                </label>
                <input type="submit" value="登录">
        </form>
        <h5><a href="${ctx}/index">返回首页</a></h5>
</section>
</body>
</html>
