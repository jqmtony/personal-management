<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <jsp:include page="common.jsp" />
    <jsp:include page="header.jsp" />
</head>
<body>
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
</body>
</html>
