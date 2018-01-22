<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="./taglib.jsp" %>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/pages/core/common.jsp" />

<link href="${pageContext.request.contextPath}/public/plugin//bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
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
<script>
    const ROOT_PATH = "${ctx}";
</script>