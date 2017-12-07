<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${siteInfo.titlePrefix}写博客</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/header.jsp"/>
    <link rel="stylesheet" href="${ctx}/public/css/mymarkdown.css"/>
    <link rel="stylesheet" href="${ctx}/public/css/blog/Blog.css"/>
    <style>
        .detailBlog-panel{
            width:80%; margin:0 auto; background: #fff;
        }
        .detailBlog-panel h1,.detailBlog-panel h2,.detailBlog-panel h3,.detailBlog-panel h4,.detailBlog-panel h5{
            text-align: center; margin:0; padding: 1em 0;
        }
        .detailBlog-panel .detailsPreviewPanel{
            width:100%; height: 100%; padding:0 1em 3em 1em; overflow: auto; background: #fff;
        }
        .detailsBtn-container{
            width:100%; position: fixed; bottom:0; overflow: auto; text-align: right; height: 3em; background: #f4f4f4; opacity: 0.8;
        }
    </style>
</head>
<body>
<div  class="visible-xs visible-sm detailBlog-panel">
    <div>
        <h2>${blog.title}</h2>
    </div>
    <div class="detailsPreviewPanel markdown-panel">
        ${blog.html}
    </div>
    <div class="detailsBtn-container">
        <a href="${ctx}/blog/blogging?id=${blog.id}"><button class="btn btn-success" style="width:40%; height: 100%;">编辑</button></a>
    </div>
</div>
<div  class="hidden-xs hidden-sm  detailBlog-panel">
    <div>
        <h2>${blog.title}</h2>
    </div>
    <div class="detailsPreviewPanel markdown-panel">
        ${blog.html}
    </div>
    <div class="detailsBtn-container">
        <a href="${ctx}/blog/blogging?id=${blog.id}"><button class="btn btn-success" style="width:40%; height: 100%;">编辑</button></a>
    </div>
</div>
</body>
</html>