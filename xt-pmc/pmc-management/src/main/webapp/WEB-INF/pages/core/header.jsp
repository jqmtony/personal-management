<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="./taglib.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="viewport"
      content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no"/>

<meta name="keywords" content="qwkxq 空间 博客 java redis jquery javascript spring springmvc hibernate maven" />
<meta name="description" content="qwkxq 每天分享一点生活技术小知识" />

<link href="${pageContext.request.contextPath}/public/plugin//bootstrap-3.3.7-dist/css/bootstrap.css"
      rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/plugin//prism/prism.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/plugin/prism/prism-line-numbers.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/core/header.css" rel="stylesheet">


<script src="${pageContext.request.contextPath}/public/plugin//bootstrap-3.3.7-dist/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/public/plugin//bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<%--<script src="${pageContext.request.contextPath}/public/plugin//prism/prism-core.js"></script>
<script src="${pageContext.request.contextPath}/public/plugin//prism/prism-java.js"></script>--%>
<script src="${pageContext.request.contextPath}/public/plugin/showdown-1.8.2/dist/showdown.min.js"></script>
<script src="${pageContext.request.contextPath}/public/plugin/tabIndent.js-master/js/tabIndent.js"></script>
<%-- 自动提交网址到百度 --%>
<script>
    (function(){
        var bp = document.createElement('script');
        var curProtocol = window.location.protocol.split(':')[0];
        if (curProtocol === 'https') {
            bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
        }
        else {
            bp.src = 'http://push.zhanzhang.baidu.com/push.js';
        }
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(bp, s);
    })();
</script>

<%--
          class="navbar navbar-static-top"
    --%>
<header class="hidden-xs hidden-sm" style="position: relative; z-index:999;">
    <nav>
        <div class="navbar-container container-fluid">
            <div class="row">
                <div class="col-xs-1 col-sm-1"></div>
                <div class="col-xs-10 col-sm-10">
                    <ul class="nav navbar-nav navbar-main">
                        <li class="active-tab">
                            <a href="${ctx}/index">首页</a>
                        </li>
                        <li>
                            <a href="#">导航二</a>
                            <ul class="nav sub-menu">
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">导航三</a>
                            <ul class="nav sub-menu">
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                                <li><a href="#">子菜单</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right navbar-main" style="margin-right: 0;">
                        <li>
                            <shiro:authenticated>
                                <a href="javascript:void(0);">
                                    <shiro:principal property="username"/>
                                </a>
                            </shiro:authenticated>
                            <shiro:notAuthenticated>
                                <a href="${ctx}/login">
                                    登录
                                </a>
                            </shiro:notAuthenticated>
                            <ul class="nav sub-menu">
                                <li><a href="${ctx}/blog/blogging">写博客</a></li>
                                <li><a href="#">传文件</a></li>
                                <li><a href="#">赚积分</a></li>
                                <shiro:authenticated>
                                    <li><a href="${ctx}/logout">退出</a></li>
                                </shiro:authenticated>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</header>

<header class="visible-xs visible-sm" style="position: relative; z-index:999;">
    <nav>
        <div class="navbar-container container-fluid">
            <div class="row">
                <div class="col-xs-3 col-sm-3" style="height: 56px; line-height: 56px; text-align: center;">
                    <a href="${ctx}/index">首页</a>
                </div>
                <div class="col-xs-3 col-sm-3" style="height: 56px; line-height: 56px; text-align: center;">
                    <a href="${ctx}/blog/blogging">写博客</a>
                </div>
                <div class="col-xs-3 col-sm-3" style="height: 56px; line-height: 56px; text-align: center;">
                    <a href="#">传文件</a>
                </div>
                <div class="col-xs-3 col-sm-3" style="height: 56px; line-height: 56px; text-align: center;">
                    <a href="#">赚积分</a>
                </div>
            </div>
        </div>
    </nav>
</header>