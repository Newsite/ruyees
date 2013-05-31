<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript">
	function selFlow() {
		var projectId = $("#projectId").val();
		if (projectId == '') {
			alert('按排批课计划之前必选相应流程模板！');
			return $("#projectId").focus();
		}
		document.form.method = "get";
		document.form.action = '${ctx}/coach/grade/add/' + projectId;
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
      <h4>批课安排</h4>
      <p>在本模块中，您可以安排新教师参加批课活动。</p>
    </div>
    <form class="form-inline" id="inputForm" action="" name="form">
      <p>
        <label>安排批课</label>&nbsp;
        <select name="projectId" id="projectId" class="span5">
          <option value="">选择备课活动</option>
          <j:iter items="${projects}" var="project" status="vs">
            <option value="${project.fdId}">${project.template.fdName}&nbsp;/&nbsp;${project.name}</option>
          </j:iter>
        </select>
        <button class="btn btn-info" onclick="selFlow()">
          <i class="icon-plus-sign icon-white"></i>&nbsp;添加
        </button>
      </p>
      <table class="table ">
        <thead>
          <tr>
            <th width="10px">#</th>
            <th width="10%">新教师</th>
            <th>主题</th>
            <th width="10%">平均分</th>
            <th width="40%">操作</th>
          </tr>
        </thead>
        <j:iter items="${page.list}" var="bean" status="vstatus">
          <tr>
            <td>${vstatus.index + 1}</td>
            <td>${bean.newTeach.realName}</td>
            <td>${bean.title}</td>
            <td>${bean.value}</td>
            <td>
            <j:ifelse test="${bean.through eq false}">
            	<j:then>
            		<a href="${ctx}/coach/grade/twoAdd/${bean.fdId}">追加批课</a>&nbsp;&nbsp;
	                <a href="${ctx}/coach/grade/edit/${bean.fdId}">评价</a>&nbsp;&nbsp;
	              	<a href="${ctx}/coach/grade/itemEdit/${bean.fdId}">编辑</a>&nbsp;&nbsp;
            	</j:then>
            	<j:else>
            		追加批课&nbsp;&nbsp;
	                                        评价&nbsp;&nbsp;
	              	编辑&nbsp;&nbsp;
            	</j:else>
            </j:ifelse>
              <a href="${ctx}/coach/grade/view/${bean.fdId}">查看</a>&nbsp;&nbsp;
              <a href="${ctx}/coach/grade/edit/${bean.fdId}">评价</a>&nbsp;&nbsp;
              <a href="${ctx}/coach/grade/excelExp/${bean.fdId}">导出</a>&nbsp;&nbsp;
            </td>
          </tr>
        </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" paginationSize="5" />
    </form>
  </div>
</body>
</html>