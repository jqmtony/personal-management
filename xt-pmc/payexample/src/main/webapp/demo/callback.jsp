<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="Md5.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	String memberid         = request.getParameter("memberid");
    String orderid          = request.getParameter("orderid");
    String transaction_id   = request.getParameter("transaction_id");
    String amount           = request.getParameter("amount");
    String datetime         = request.getParameter("datetime");
    String returncode       = request.getParameter("returncode");
    String sign             = request.getParameter("sign");

    String Md5key           = 'tryqn5n6uv1z9819kch7x5ir9wphg8sy';

    String SignTemp = "amount="+amount+"&datetime="+datetime+"&memberid="+memberid+"&orderid="+orderid+"&returncode="+returncode+"&transaction_id="+transaction_id+"&key="+Md5key+"";

	String md5sign  =  md5(SignTemp , 32 , 1);//MD5加密

	PrintWriter pw = response.getWriter();

    pw.write("sign => " + sign + " : md5sign => " + md5sign);

	if (sign.equals(md5sign)){

		if(returncode.equals("00")){
			//支付成功，写返回数据逻辑
			PrintWriter pw=response.getWriter();
			pw.write("ok");
		}else{
			PrintWriter pw=response.getWriter();
			pw.write("支付失败");
		}
	}else{
		PrintWriter pw=response.getWriter();
		pw.write("验签失败");
	}
%>

</body>
</html>