<%@ page import="java.net.URLDecoder" %>
<%@ page import="cn.xt.pmc.management.model.Blog" %>
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
<div class="container-fluid">
    <form action="${ctx}/blog/blogging" method="post">
        <div class="writePanel-title">
            <input name="title" placeholder="请输入标题" value="${blog.title}" />
        </div>
        <div class="visible-xs visible-sm">
            <div class="writePanel-container" style="width:100%;">
                <textarea class="tabIndent writePanel">${decodeOrginal}</textarea>
            </div>
            <div style="width:100%; height: 2em; float: left; border-bottom: 1px solid #000; border-top:1px solid #000; margin:1em auto;">预览：</div>
            <div style="width:100%; height: 500px; float: left;">
                <%--background: #272822; color:#42cdef;--%>
                <div class="previewPanel">
                    ${decodeHtml}
                </div>
            </div>
        </div>
        <div class="hidden-xs hidden-sm">
            <div class="writePanel-container">
                <textarea class="tabIndent writePanel">${decodeOrginal}</textarea>
            </div>
            <div style="width:50%; height: 500px; float: left;">
                <%--background: #272822; color:#42cdef;--%>
                <div class="previewPanel">
                    ${decodeHtml}
                </div>
            </div>
        </div>
        <hr style="clear: both; background: #ededed; height: 1px;"/>
        <div style="width:100%; position: fixed; bottom:0; overflow: auto; text-align: right; height: 3em; background: #f4f4f4; opacity: 0.8;">
            <input type="hidden" name="html" id="htmlText" value="${blog.original}"/>
            <input type="hidden" name="original" id="originalText" value="${blog.html}"/>
            <input type="hidden" name="id" value="${blog.id}" />
            <button class="btn btn-success" style="width:40%; height: 100%;">提交</button>
        </div>
    </form>
</div>
<script src="${ctx}/public/js/blog/Blog.js"></script>
<script>
    Blog.init();
    if("${errorMsg}"){
        alert("${errorMsg}")
    }
</script>
</body>
</html>