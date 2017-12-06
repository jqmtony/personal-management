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
<div  class="visible-xs visible-sm" style="width:100%;">
    <div style="width:100%;">
        <h5 align="center">${blog.title}</h5>
    </div>
    <div class="previewPanel">
        ${blog.html}
    </div>
    <div style="width:100%; position: fixed; bottom:0; overflow: auto; text-align: right; height: 3em; background: #f4f4f4; opacity: 0.8;">
        <a href="${ctx}/blog/blogging?id=${blog.id}"><button class="btn btn-success" style="width:40%; height: 100%;">编辑</button></a>
    </div>
</div>
<div  class="hidden-xs hidden-sm" style="width:80%; padding: 0 3em; margin:0 auto;">
    <div style="text-align: left;">
        <h3>${blog.title}</h3>
    </div>
    <div class="previewPanel">
        ${blog.html}
    </div>
    <div style="width:100%; position: fixed; bottom:0; overflow: auto; text-align: right; height: 3em; background: #f4f4f4; opacity: 0.8;">
        <a href="${ctx}/blog/blogging?id=${blog.id}"><button class="btn btn-success" style="width:40%; height: 100%;">编辑</button></a>
    </div>
</div>
</body>
</html>