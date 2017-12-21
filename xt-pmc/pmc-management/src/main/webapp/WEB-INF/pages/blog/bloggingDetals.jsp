<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../core/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${siteInfo.titlePrefix}博客详情</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/header.jsp"/>
    <link rel="stylesheet" href="${ctx}/public/css/mymarkdown.css"/>
    <link rel="stylesheet" href="${ctx}/public/css/blog/Blog.css"/>
    <style>
        .main-text-container{width: 100%; background: #fff; padding: 0 2em; margin-bottom: 4em;}
        .detailBlog-panel h1,.detailBlog-panel h2,.detailBlog-panel h3,.detailBlog-panel h4,.detailBlog-panel h5{
            text-align: center; margin:0; padding: 1em 0 1em 0;
        }
        .detailBlog-panel .detailsPreviewPanel{
            width:100%; height: 100%; overflow: auto; background: #fff;
        }
        .detailsBtn-container{
            width:100%; position: fixed; bottom:0; overflow: auto; text-align: right; height: 3em; background: #f4f4f4; opacity: 0.8;
        }
    </style>
</head>
<body>
<div  class="visible-xs visible-sm detailBlog-panel container-fluid">
    <div class="row">
        <div class="main-text-container">
            <div>
                <h2>${blog.title}</h2>
            </div>
            <div class="detailsPreviewPanel markdown-panel">
                ${blog.html}
            </div>
            <c:if test="${canEdit}">
                <div class="detailsBtn-container">
                    <a href="${ctx}/blog/blogging?id=${blog.id}"><button class="btn btn-success" style="width:40%; height: 100%;">编辑</button></a>
                </div>
            </c:if>
        </div>
    </div>
</div>
<div  class="hidden-xs hidden-sm detailBlog-panel container-fluid">
    <div class="row">
        <div class="col-xs-1 col-sm-1"></div>
        <div class="col-xs-10 col-sm-10">
            <div class="main-text-container">
                <div>
                    <h2>${blog.title}</h2>
                </div>
                <div class="detailsPreviewPanel markdown-panel">
                    ${blog.html}
                </div>
                <c:if test="${canEdit}">
                    <div class="detailsBtn-container">
                        <a href="${ctx}/blog/blogging?id=${blog.id}"><button class="btn btn-success" style="width:40%; height: 100%;">编辑</button></a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>