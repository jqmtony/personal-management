<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="./core/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${siteInfo.titlePrefix}首页</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/header.jsp"/>
    <link href="${pageContext.request.contextPath}/public/css/index.css" rel="stylesheet"/>
</head>
<body>
<div class="visible-xs visible-sm container-main container-fluid">
    <div class="row">
        <%--<div class="message-container">
            <div class="trumpet">
                <span class="glyphicon glyphicon-volume-up"></span>
            </div>
            <div class="message-marquee" label="scrollDiv">
                <ul>
                    <li>市恩不如报德之为厚，要誉不如逃名之为适，矫情不如直节之为真</li>
                    <li>使人有面前之誉，不若使人无背后之毁；使人有乍交之欢，不若使人无久处之厌</li>
                    <li>攻人之恶毋太严，要思其堪受；教人以善莫过高，当原其可从</li>
                    <li>不近人情，举世皆畏途；不察物情，一生俱梦境</li>
                    <li>倚才高而玩世，背后须防射影之虫；饰厚貌以欺人，面前恐有照胆之镜</li>
                    <li>花繁柳密处，拨得开，才是手段；风狂雨急时，立得定，方见脚根</li>
                    <li>事穷势蹙之人，当原其初心；功成行满之士，要观其末路</li>
                </ul>
            </div>
        </div>--%>
    </div>
    <div class="row">
        <c:if test="${empty pager.data}">
            <h4 align="center">暂无文章</h4>
        </c:if>
        <c:forEach items="${pager.data}" var="blog" varStatus="status">
            <div class="flash-main-container">
                <div class="flash-box">
                    <img alt="${siteInfo.domainName}" src="${pageContext.request.contextPath}/public/img/default.jpg"/>
                </div>
                <div style="width: 100%; height: 2em;">
                    <h4><p class="" href="">${blog.title}</p></h4>
                </div>
                <div style="width: 100%; height: 5em; overflow-y: auto; overflow-x:hidden">
                    <p style="">
                            <%--
                                width:100%;  white-space:nowrap; text-overflow:ellipsis; overflow:hidden;
                                original
                            --%>
                        <c:out value="${fn:substring(blog.text, 0, 50)}" escapeXml="true"/>……
                    </p>
                </div>
                <div>
                    <span class="glyphicon glyphicon-time"></span>
                    <fmt:formatDate value="${blog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <p style="float: right; text-indent: -1em;">
                        <a href="${ctx}/blog/bloggingDetails?id=${blog.id}">详情</a>
                    </p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%--
PC
--%>
<div class="hidden-xs hidden-sm container-main container-fluid ">
    <div class="row">
        <div class="col-xs-1 col-sm-1"></div>
        <div class="col-xs-10 col-sm-10">
            <div class="message-container">
                <div class="trumpet">
                    <span class="glyphicon glyphicon-volume-up"></span>
                </div>
                <div class="message-marquee" label="scrollDiv">
                    <ul>
                        <li>市恩不如报德之为厚，要誉不如逃名之为适，矫情不如直节之为真</li>
                        <li>使人有面前之誉，不若使人无背后之毁；使人有乍交之欢，不若使人无久处之厌</li>
                        <li>攻人之恶毋太严，要思其堪受；教人以善莫过高，当原其可从</li>
                        <li>不近人情，举世皆畏途；不察物情，一生俱梦境</li>
                        <li>倚才高而玩世，背后须防射影之虫；饰厚貌以欺人，面前恐有照胆之镜</li>
                        <li>花繁柳密处，拨得开，才是手段；风狂雨急时，立得定，方见脚根</li>
                        <li>事穷势蹙之人，当原其初心；功成行满之士，要观其末路</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-1 col-sm-1"></div>
        <div class="col-xs-10 col-sm-10">
            <div class="flash-box-container">
                <div class="row">
                    <c:if test="${empty pager.data}">
                        <h4 align="center">暂无文章</h4>
                    </c:if>
                    <c:forEach items="${pager.data}" var="blog" varStatus="status">
                        <div class="col-xs-4 col-sm-4">
                            <div class="flash-main-container">
                                <div class="flash-box">
                                    <img alt="${siteInfo.domainName}" src="${pageContext.request.contextPath}/public/img/default.jpg"/>
                                </div>
                                <div style="width: 100%; height: 2em;">
                                    <h4><p class="" href="">${blog.title}</p></h4>
                                </div>
                                <div style="width: 100%; height: 5em; overflow-y: auto; overflow-x:hidden">
                                    <p style="">
                                            <%--
                                                width:100%;  white-space:nowrap; text-overflow:ellipsis; overflow:hidden;
                                                original
                                            --%>
                                        <c:out value="${fn:substring(blog.text, 0, 50)}" escapeXml="true"/>……
                                    </p>
                                </div>
                                <div>
                                    <span class="glyphicon glyphicon-time"></span>
                                    <fmt:formatDate value="${blog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    <p style="float: right; text-indent: -1em;">
                                        <a href="${ctx}/blog/bloggingDetails?id=${blog.id}">详情</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/public/js/index.js"></script>
<jsp:include page="${ctx}/WEB-INF/pages/core/footer.jsp" />
</body>
</html>
