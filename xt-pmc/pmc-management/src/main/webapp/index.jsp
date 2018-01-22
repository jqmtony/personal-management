<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/simpleheader.jsp" />
    <link rel="stylesheet" href="${ctx}/public/css/index2.css" />
    <script src="${ctx}/public/js/plugins/Audio.js"></script>
</head>
<body>
<div class="main-nav">
    <a href="${ctx}/index">返回首页</a>
</div>
<div class="text-echo"></div>
<form id="robotForm" action="javascript:Audio.submit();" method="post">
    <input class="audioTextinput" name="text" required="required" maxlength="30" placeholder="跟我聊聊天呗" />
    <button class="sendSubmitBtn">
        <span class="icon icon-ok"></span>
    </button>
</form>
<script>

</script>
</body>
</html>