<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="fdCategoryId" value="${fdCategoryId}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <section id="set-exam">
    <div class="well">
      <div class="page-header">
        <a href="${ctx}/group/exam/list?fdCategoryId=${fdCategoryId}" class="tile-return">返回上页</a>
        <h4>试卷试题</h4>
        <p>在本模块中，您可以配置平台的所有试卷与试题信息。</p>
      </div>
      <table class="table ">
        <tr>
          <th width="20%">试题名称</th>
          <td width="80%">${bean.fdSubject}</td>
        </tr>
        <tr>
          <th>题型</th>
          <c:choose>
            <c:when test="${bean.fdType==0}">
              <td>单选题</td>
            </c:when>
            <c:when test="${bean.fdType==1}">
              <td>多选题</td>
            </c:when>
          </c:choose>
        </tr>
        <tr>
          <th>考题选项（逗号分隔）</th>
          <td>${bean.fdOption}</td>
        </tr>
        <tr>
          <th width="20%">备注</th>
          <td width="80%">${bean.fdDescription}</td>
        </tr>
      </table>
    </div>
  </section>
</body>
</html>