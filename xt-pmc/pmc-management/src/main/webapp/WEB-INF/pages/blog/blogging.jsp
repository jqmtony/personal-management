<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${siteInfo.titlePrefix}写博客</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/header.jsp"/>
    <link rel="stylesheet" href="${ctx}/public/css/mymarkdown.css"/>
    <link rel="stylesheet" href="${ctx}/public/css/blog/Blog.css"/>
</head>
<body>
<div style="width:50%; height: 500px; float: left; overflow: hidden;">
    <textarea class="tabIndent" id="writePanel"
              style="width:100%; height: 100%; border: none; outline:none; resize:none;"></textarea>
</div>
<div style="width:50%; height: 500px; float: left;">
    <%--background: #272822; color:#42cdef;--%>
    <div id="previewPanel" style="width:100%; height: 100%; overflow: auto; background: #f6f6f6;">
    </div>
</div>
<hr style="clear: both; background: #000; height: 1px;"/>
<div style="width:100%; float: left; overflow: auto; text-align: right; padding: 0 2em;">
    <form action="${ctx}/blog/blogging" method="post">
        <input type="hidden" name="html" id="htmlText"/>
        <input type="hidden" name="original" id="originalText"/>
        <button class="btn btn-success" style="width:40%; height: 3em;">提交</button>
    </form>
</div>
<script src="${ctx}/public/js/blog/Blog.js"></script>
<script>
    Blog.init();
</script>
</body>
</html>