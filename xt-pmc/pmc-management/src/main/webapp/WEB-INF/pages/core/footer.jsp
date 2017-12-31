<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="./taglib.jsp" %>

<link href="${ctx}/public/css/core/footer.css" rel="stylesheet">
<link href="${ctx}/public/css/social-share-suspending.css" rel="stylesheet">
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-1 col-sm-1"></div>
        <div class="col-xs-10 col-sm-10">
            <nav class="common-pager" aria-label="Page navigation">
                <ul class="pagination  pagination-lg">
                    <c:if test="${pager.page>1}">
                        <li>
                            <a href="${ctx}/index?blogPage=1" aria-label="Previous All">
                                <span aria-hidden="true">&laquo;&laquo;</span>
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/index?blogPage=${pager.page<=1?1:pager.page-1}" aria-label="Previous">
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
                    <c:if test="${pager.page<pager.pageSize}">
                        <li>
                            <a href="${ctx}/index?blogPage=${pager.page>=pager.pageSize?pager.page:pager.page+1}" aria-label="Next">
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
<%--
分享悬浮按钮 小屏禁用
--%>
<div class="social-share-container hidden-xs hidden-sm">
    <div class="social-share-ui">
        <span>分<br/>享<br/>到</span>
        <div class="social-share-panel">
            <ul class="social-networks">
                <li><a href="#" target="_blank" title="领英" class="icon-linkedin">LinkedIn</a></li>
                <li><a href="#" target="_blank" title="Twitter" class="icon-twitter">Twitter</a></li>
                <li><a href="#" target="_blank" title="Facebook" class="icon-facebook">Facebook</a></li>
                <li><a href="#" target="_blank" title="Bilibili" class="icon-twitch">Twitch</a></li>
                <li><a href="#" target="_blank" title="Github" class="icon-github">GitHub</a></li>
                <li><a href="#" target="_blank" title="腾讯微博" class="icon-pinterest">Pinterest</a></li>
                <li><a href="#" target="_blank" title="" class="icon-instagram">Instagram</a></li>
                <li><a href="http://v.t.sina.com.cn/share/share.php?url=${fullUrl}&title=qwkxq.cn还不错，进来看看吧！" target="_blank" title="新浪微博" class="icon-vimeo">Vimeo</a></li>
            </ul>
        </div>
    </div>
</div>