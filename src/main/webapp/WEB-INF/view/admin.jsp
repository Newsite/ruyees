<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<script charset="utf-8" src="${ctx }/resources/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx }/resources/kindeditor/lang/zh_CN.js"></script>
</head>
<body data-spy="scroll" data-target=".sidebar">
  <input type="hidden" name="fdId" value="${bean.fdId}" />
  <section>
    <div class="bk-pane">
      <%-- <div class="page-header">新教师备课后台管理</div>
			<tags:edit name="edit" editid="editid1"></tags:edit>
			<br />
			<tags:edit name="edit2" editid="editid2"></tags:edit> --%>
    </div>
  </section>
</body>
</html>