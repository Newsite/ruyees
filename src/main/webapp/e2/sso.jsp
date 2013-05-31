<%@page import="com.landray.kmss.xdf.sso.util.LogoutUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String logout = request.getParameter("logout");
		if (StringUtil.isNotNull(logout) && logout.equals("1")) {
			String ck = request.getParameter("ck"); //cookieValue
			//移除本应用登陆用户缓存，用于中心统一退出各应用
			if (StringUtil.isNotNull(ck))
				LogoutUtil.RemoveOnlineCache(ck, response);
			return;
		}
	%>
</body>
</html>