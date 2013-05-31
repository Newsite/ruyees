<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
<div class="well">
  <div class="page-header">
    <a href="${ctx}/campus/progress/guidList" class="tile-return">所回主页</a>
    <h4>导师:${guid.realName}评价</h4>
    <p>在本模块中，您可以查看新教师给导师的评价。</p>
  </div>
<form class="form-inline" id="inputForm" action="" name="form">
  <table class="table table-bordered">
  	<tr>
        <td>序号</td>
        <td>打分指标</td>
        <td>分数</td>
        <td>均分</td>
        <td>打分教师</td>
    </tr>
    <j:iter items="${page.list}" var="bean" status="vstatus">
    	<tr>
        	<td rowspan="5" style="vertical-align: middle;text-align:center;">${vstatus.index + 1}</td>
        	<td>TA帮我进行了定位</td>
        	<td>${bean.value1}</td>
        	<td rowspan="5" style="vertical-align: middle;text-align:center;">${bean.totalvalue}</td>
        	<td rowspan="5" style="vertical-align: middle;text-align:center;">${bean.newTeach.realName}</td>
       	</tr>
       	<tr><td>TA点评我的不足时很到位</td><td>${bean.value2}</td></tr>
       	<tr><td>TA给了可操作的建议</td><td>${bean.value3}</td></tr>
       	<tr><td>TA为我补充了专业备课知识</td><td>${bean.value4}</td></tr>
       	<tr><td>TA的工作态度让我很有动力</td><td>${bean.value5}</td></tr>
      </tr>
      <tr>
        <td>综合评价</td>
        <td colspan="4">${bean.appraise}</td>
      </tr>
    </j:iter>
    </tbody>
  </table>
  <tags:pagination page="${page}" paginationSize="5" />
</form>
</div>
</body>
</html>