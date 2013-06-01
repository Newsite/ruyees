<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <section id="set-exam">
    <div class="well">
      <div class="page-header">
        查看资源&nbsp;&nbsp; <a href="${ctx}/group/contentMovie/list">返回</a>
      </div>
      <table class="table ">
        <tr>
          <th width="20%">资源名称</th>
          <td width="80%">${bean.fdName}</td>
        </tr>
        <tr>
          <th width="20%">资源类型</th>
          <td width="80%">${bean.typeName}</td>
        </tr>
        <%-- <tr>
	   <th class="span1">资源附件</th>
	   <td class="span6">${bean.attMain.filePath}</td>
	</tr> --%>
        <tr>
          <th width="20%">资源备注</th>
          <td width="80%">${bean.fdDescribe}</td>
        </tr>
      </table>
    </div>
  </section>
</body>
</html>