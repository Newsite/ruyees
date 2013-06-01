<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
      <h4>课件审批</h4>
      <p>在本模块中，您可以审批新教师提交的课件。</p>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th width="10px">#</th>
          <th width="10%">新教师</th>
          <th>备课活动</th>
          <th width="10%">状态</th>
          <th width="15%">操作</th>
        </tr>
      </thead>
      <j:iter items="${page.list}" var="bean" status="vstatus">
        <tr>
          <td>${vstatus.index + 1}</td>
          <td>${bean.phase.newTeach.realName}</td>
          <td>${bean.phase.stage.project.name}&nbsp;/&nbsp;${bean.phase.stage.project.template.fdName}</td>
          <td><j:ifelse test="${bean.through eq true}">
              <j:then>通过</j:then>
              <j:else>未通过</j:else>
            </j:ifelse></td>
          <td>
	          <j:ifelse test="${bean.through eq true}">
	              <j:then>
	                <a href="${ctx}/coach/courseware/edit/4/${bean.fdId}">查看</a>
	              </j:then>
	              <j:else>
	                <a href="${ctx}/coach/courseware/edit/4/${bean.fdId}">审批</a>
	              </j:else>
	            </j:ifelse>
	            <j:if test="${bean.isSubmit eq true}">
	            &nbsp;&nbsp;(可审)
	            </j:if>
            </td>
        </tr>
      </j:iter>
      </tbody>
    </table>
    <tags:pagination page="${page}" paginationSize="5" />
  </div>
</body>
</html>