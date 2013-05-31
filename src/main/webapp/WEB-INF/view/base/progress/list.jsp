<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
      <h4>学员备课跟踪</h4>
      <p>在本模块中，您可以跟踪新教师的备课进度。</p>
    </div>
    <form class="form-horizontal" action="" name="form">
      <table class="table">
        <thead>
          <tr>
            <th width="10px">#</th>
            <th width="10%">新教师</th>
            <th width="15%">开始时间</th>
            <th>备课活动</th>
            <th width="10%">最终得分</th>
            <th width="10%">详细进度</th>
            <th width="10%">提醒TA</th>
          </tr>
        </thead>
        <j:iter items="${page.list}" var="bean" status="vs">
          <tr>
            <td>${vs.index + 1}</td>
            <td>${bean.newTeach.realName}</td>
            <td>${fn:substring(bean.project.createTime, 0, 10)}</td>
            <td>${bean.project.name}&nbsp;/&nbsp;${bean.project.template.fdName}</td>
            <td <j:if test="${not empty bean.finalScore}">class="text-success"</j:if>>${bean.finalScore}</td>
            <td><a href="${ctx}/coach/progress/stageList/${bean.project.fdId}/${bean.newteachId}">查看</a></td>
            <td><i class="icon-envelope"></i>&nbsp;<a href="${ctx}/coach/progress/toAdd/${bean.newteachId}">消息</a></td>
          </tr>
        </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" paginationSize="5" />
    </form>
  </div>
</body>
</html>