<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <style>
        .img { display:block; position: relative; width:800px; height:450px; margin:0 auto;}
        .img:before { content: ""; position: absolute; width:200px; height: 100%; top: 0; left: -150px; overflow: hidden;
            background: -moz-linear-gradient(left, rgba(255,255,255,0)0, rgba(255,255,255,.2)50%, rgba(255,255,255,0)100%);
            background: -webkit-gradient(linear, left top, right top, color-stop(0%, rgba(255,255,255,0)), color-stop(50%, rgba(255,255,255,.2)), color-stop(100%, rgba(255,255,255,0)));
            background: -webkit-linear-gradient(left, rgba(255,255,255,0)0, rgba(255,255,255,.2)50%, rgba(255,255,255,0)100%);
            background: -o-linear-gradient(left, rgba(255,255,255,0)0, rgba(255,255,255,.2)50%, rgba(255,255,255,0)100%);
            -webkit-transform: skewX(-25deg);
            -moz-transform: skewX(-25deg)
        }
        .img:hover:before { left: 150%; transition: left 1s ease 0s; }
    </style>
</head>
<body>
<a href="#" class="img"><img src="http://img.loveqiao.com/pic1.jpg" width="800"></a>
</body>
</html>
