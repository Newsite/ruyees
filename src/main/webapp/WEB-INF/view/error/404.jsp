<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setStatus(200);
%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <h2>404 - 页面不存在</h2>
  <p>
    <a href="<c:url value="/"/>">返回桌面</a>
  </p>
</body>
</html>