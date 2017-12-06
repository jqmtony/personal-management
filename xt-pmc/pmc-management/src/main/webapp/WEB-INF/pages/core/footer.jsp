<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="./taglib.jsp" %>

<link href="${pageContext.request.contextPath}/public/css/core/footer.css" rel="stylesheet">
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-1 col-sm-1"></div>
        <div class="col-xs-10 col-sm-10">
            <nav class="common-pager" aria-label="Page navigation">
                <ul class="pagination  pagination-lg">
                    <c:if test="${pager.page!=1}">
                        <li>
                            <a href="${ctx}/index?blogPage=1" aria-label="Previous All">
                                <span aria-hidden="true">&laquo;&laquo;</span>
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/index?blogPage=${pager.page-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach var="num" items="${pager.pageNums}" varStatus="vs">
                        <c:if test="${num==pager.page}">
                            <li class="active"><a href="${ctx}/index?blogPage=${num}">${num}</a></li>
                        </c:if>
                        <c:if test="${num!=pager.page}">
                            <li><a href="${ctx}/index?blogPage=${num}">${num}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${pager.page!=pager.pageSize}">
                        <li>
                            <a href="${ctx}/index?blogPage=${pager.page+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/index?blogPage=${pager.pageSize}" aria-label="Next All">
                                <span aria-hidden="true">&raquo;&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>