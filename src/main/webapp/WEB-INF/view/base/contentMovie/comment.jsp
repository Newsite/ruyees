<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">

	function delById(fdId) {
		if (!confirm('您确定要删除吗？')) {
			return false;
		}
		document.form.method = "post";
		document.form.action = '${ctx}/group/contentMovie/comment/delete/' + fdId ;
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/group/contentMovie/list" class="tile-return">返回上页</a>
      <h4>文档视频</h4>
      <p>在本模块中，您可以配置平台的所有文档、视频资源的评论记录。</p>
    </div>
  
    <form class="" id="inputForm" action="" name="form">
    <input name="cwid" id="cwid" type="hidden" value="${cwId }"/>
      <table class="table">
        <thead>
          <tr>
            <th width="10%">评论人</th>
            <th width="65%">评论内容</th>
            <th width="15%">评论时间</th>
            <th width="10%">操作</th>
          </tr>
        </thead>
        <tbody>
          <j:iter items="${page.list}" var="bean" status="s">
            <tr>
              <td>${bean.userName}</td>
              <td>${bean.fdContent}</td>
              <td>${fn:substring(bean.createdtime, 0, 19)}</td>
              <td><a href="#"
                onclick="delById('${bean.fdId}')">删除</a>
                </td>
            </tr>
          </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" searchParams="fdType=${fdType }&fdName=${fdName }" paginationSize="5" />
    </form>
    <script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
  </div>
</body>
</html>