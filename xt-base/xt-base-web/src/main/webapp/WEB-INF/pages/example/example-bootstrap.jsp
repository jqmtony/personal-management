<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no"/>

    <link href="${pageContext.request.contextPath}/public/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/prism/prism.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-4 col-sm-6" style="background:red; height: 2em;"></div>
        <div class="col-xs-6 col-sm-4" style="background:cyan; height: 4em;"></div>
    </div>
</div>

<p class="text-lowercase">Lowercased text.</p>
<p class="text-uppercase">Uppercased text.</p>
<p class="text-capitalize">Capitalized text.</p>
<del>aaaaaaaaaaaaaaaaaaaa</del>

<br/> 缩略语
<abbr title="attribute">attr</abbr>
<abbr title="HyperText Markup Language" class="initialism">HTML</abbr>

<br/><br/>

<blockquote>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
    <footer>Someone famous in <cite title="Source Title">Source Title</cite></footer>
</blockquote>
<blockquote class="blockquote-reverse">
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
    <footer>Someone famous in <cite title="Source Title">Source Title</cite></footer>
</blockquote>

<ul>
    <li>aaa</li>
    <li>bbb</li>
    <li>ccc</li>
    <li>ddd</li>
</ul>
<ol>
    <li>aaa</li>
    <li>bbb</li>
    <li>ccc</li>
    <li>ddd</li>
</ol>
<ul class="list-inline">
    <li>aaa</li>
    <li>bbb</li>
    <li>ccc</li>
    <li>ddd</li>
</ul>
<dl>
    <dt>数码电器</dt>
    <dd>手机</dd>
    <dd>电脑</dd>
    <dd>相机</dd>
    <dt>书籍</dt>
    <dd>编程</dd>
    <dd>人生</dd>
    <dd>诗歌散文</dd>
</dl>
<dl class="dl-horizontal">
    <dt>aaa</dt>
    <dd>放松放松放松放松的斯蒂芬斯蒂芬沙发斯蒂芬</dd>
    <dt>bbb</dt>
    <dd>放松放松放松放松的斯蒂芬斯蒂芬沙发斯蒂芬</dd>
</dl>

<pre>
<code class="language-java">
    package org.elasticsearch.license;
    import java.io.*;

    public class LicenseVerifier
    {
        public static boolean verifyLicense(final License license) {
            return true;
        }
    }
</code>
</pre>

按<kbd>ctrl + /</kbd>单行注释<br/>
按<kbd>ctrl + alt + /</kbd>多行注释

<blockquote>表格</blockquote>
<table class="table table-striped">
    <thead>
    <tr>
        <th>#</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">1</th>
        <td>Mark</td>
        <td>Otto</td>
        <td>@mdo</td>
    </tr>
    <tr>
        <th scope="row">2</th>
        <td>Jacob</td>
        <td>Thornton</td>
        <td>@fat</td>
    </tr>
    <tr>
        <th scope="row">3</th>
        <td>Larry</td>
        <td>the Bird</td>
        <td>@twitter</td>
    </tr>
    </tbody>
</table>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th>#</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">1</th>
        <td>Mark</td>
        <td>Otto</td>
        <td>@mdo</td>
    </tr>
    <tr>
        <th scope="row">2</th>
        <td>Jacob</td>
        <td>Thornton</td>
        <td>@fat</td>
    </tr>
    <tr>
        <th scope="row">3</th>
        <td>Larry</td>
        <td>the Bird</td>
        <td>@twitter</td>
    </tr>
    </tbody>
</table>
<table class="table table-bordered table-condensed">
    <thead>
    <tr>
        <th>#</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">1</th>
        <td class="active">Mark</td>
        <td class="success">Otto</td>
        <td class="info">@mdo</td>
    </tr>
    <tr>
        <th scope="row">2</th>
        <td class="warning">Jacob</td>
        <td class="danger">Thornton</td>
        <td>@fat</td>
    </tr>
    <tr>
        <th scope="row">3</th>
        <td>Larry</td>
        <td>the Bird</td>
        <td>@twitter</td>
    </tr>
    </tbody>
</table>

响应式表格
<div class="table-responsive">
    <table class="table">
        <thead>
            <tr>
                <th>#</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Username</th>
                <th>Username</th>
                <th>Username</th>
                <th>Username</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
            </tr>
            <tr>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
            </tr>
        </tbody>
    </table>
</div>

<script src="${pageContext.request.contextPath}/public/bootstrap-3.3.7-dist/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/public/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/public/prism/prism-core.js"></script>
<script src="${pageContext.request.contextPath}/public/prism/prism-java.js"></script>
</body>
</html>
