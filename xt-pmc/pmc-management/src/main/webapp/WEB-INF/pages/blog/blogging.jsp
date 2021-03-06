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
    <form action="${ctx}/blog/bloggingSubmit" method="post">
        <div class="writePanel-title">
            <input name="title" style="text-indent: 2em;" placeholder="请输入标题" value="${blog.title}" />
        </div>
        <div style="width:100%;  padding: 1em 2em; background: #EDEDED;">
            <label for="firLvl">文章类别：</label>
            <select name="classifypid" id="firLvl">
                <option value="">-- 选择分类 --</option>
            </select>
            <select name="classify" id="secLvl">
                <option value="">-- 选择分类 --</option>
            </select>
        </div>
        <div class="visible-xs visible-sm blog-container">
            <div class="writePanel-container" style="width:100%;">
                <textarea class="tabIndent writePanel">${decodeOrginal}</textarea>
            </div>
            <div style="width:100%; height: 500px; float: left;">
                <%--background: #272822; color:#42cdef;--%>
                <div class="previewPanel markdown-panel">
                    ${decodeHtml}
                </div>
            </div>
        </div>
        <div class="hidden-xs hidden-sm blog-container">
            <div class="writePanel-container">
                <textarea class="tabIndent writePanel">${decodeOrginal}</textarea>
            </div>
            <div style="width:50%; height: 500px; float: left;">
                <%--background: #272822; color:#42cdef;--%>
                <div class="previewPanel markdown-panel">
                    ${decodeHtml}
                </div>
            </div>
        </div>
        <hr style="clear: both; background: #ededed; height: 1px;"/>
        <div class="submit-btn-container">
            <input type="hidden" name="html" id="htmlText" value="${blog.html}"/>
            <input type="hidden" name="original" id="originalText" value="${blog.original}"/>
            <input type="hidden" name="id" value="${blog.id}" />
            <button class="btn btn-success" style="width:40%; height: 100%;">提交</button>
        </div>
    </form>
</div>
<script src="${ctx}/public/js/blog/Blog.js"></script>
<script>
    Blog.init();
    Blog.classifyInit("${blog.classify}","${blog.classifypid}");
    if("${errorMsg}"){
        alert("${errorMsg}")
    }
</script>
</body>
</html>