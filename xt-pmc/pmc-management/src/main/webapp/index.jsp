<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/simpleheader.jsp" />
    <link rel="stylesheet" href="${ctx}/public/css/index2.css" />
    <script src="${ctx}/public/js/plugins/Audio.js"></script>
</head>
<body>
<div class="main-nav">
    <a href="${ctx}/index">返回首页</a>
</div>
<div class="text-echo"></div>
<form id="robotForm" action="javascript:Audio.submit();" method="post">
    <input class="audioTextinput" name="text" required="required" maxlength="30" placeholder="跟我聊聊天呗" />
    <button class="sendSubmitBtn">
        <span class="icon icon-ok"></span>
    </button>
</form>
<ul id="memcon-icon">
    <li>
        <a href="javascript:$('#messagerecorder').show()" id="mysmrbtn" title="打开聊天记录">
            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 60 60" style="enable-background:new 0 0 60 60;" xml:space="preserve">
<path class="menulisvgp" fill="#000000" d="M48.9,23.9c0,0.5-0.1,1.1-0.2,1.7c-1.4,9.7-9.1,16.8-18.4,16.8h-3L26.8,43c2.1,3.9,5.9,6.4,10.3,6.4h3l5.9,6.2
	c0.4,0.4,0.9,0.6,1.4,0.6c0.2,0,0.5,0,0.7-0.1c0.8-0.3,1.2-1,1.2-1.8v-5.1c5.5-1.1,9.6-6.3,9.6-12.6C59,30.1,54.6,24.8,48.9,23.9z
	 M45.9,25.2c0.1-1,0.2-1.8,0.2-2.6c0-9.4-7.1-17-15.8-17H16.8C8.1,5.5,1,13.1,1,22.5c0,8.4,5.6,15.4,13,16.8v7.2
	c0,0.9,0.6,1.8,1.4,2.1c0.3,0.1,0.6,0.2,0.9,0.2c0.6,0,1.2-0.2,1.7-0.7l8.2-8.5h4.2C38.2,39.5,44.7,33.5,45.9,25.2z M12.4,17.3h22.2
	c0.6,0,1.1,0.5,1.1,1.2c0,0.7-0.5,1.2-1.1,1.2H12.4c-0.6,0-1.1-0.5-1.1-1.2C11.3,17.8,11.8,17.3,12.4,17.3z M25.7,28.3H12.4
	c-0.6,0-1.1-0.5-1.1-1.2c0-0.7,0.5-1.2,1.1-1.2h13.3c0.6,0,1.1,0.5,1.1,1.2C26.8,27.8,26.3,28.3,25.7,28.3z"></path>
</svg>
        </a>
    </li>
</ul>
<div id="messagerecorder" style="display: none;">
    <div id="messagerecordertitle">交谈记录</div>
    <ul>

    </ul>
    <div id="messagerecordercloser"><a href="javascript:$('#messagerecorder').hide()" title="隐藏交谈记录"><span class="icon icon-remove"></span></a></div>
</div>
<script>
    $(function(){
        $("#messagerecorder ul").css({
            height: $("#messagerecorder").height() - 100
        });
    });
</script>
</body>
</html>