<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <form class="form-horizontal" method="post" action="" name="form">
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/courseware/edit/4/${bean.bamCourseware.fdId}" class="tile-return">返回上页</a>
          <h4>课件审批</h4>
          <p>在本模块中，您可以审批新教师提交的课件。</p>
        </div>
        <table class="table">
          <tr>
            <th width="20%">新教师</th>
            <td colspan="3">${person.realName}</td>
          </tr>
          <tr>
            <th width="20%">课件名称</th>
            <td colspan="3">${bean.name}</td>
          </tr>
          <tr>
            <th width="20%">查看课件</th>
            <td colspan="3"><a href="${ctx}/common/courseware/${bean.fdId}">${bean.name}</a></td>
          </tr>
          <tr>
            <th>提交时间</th>
            <td>${fn:substring(bean.time, 0, 19)}</td>
            <th>审批时间</th>
            <td>${fn:substring(bean.approweTime, 0, 19)}</td>
          </tr>
          <tr>
            <th>审批状态</th>
            <td colspan="3"><j:ifelse test="${bean.status==4}">
                <j:then>通过</j:then>
                <j:else>驳回</j:else>
              </j:ifelse></td>
          </tr>
          <tr>
            <th>评语</th>
            <td colspan="3">${bean.remark}</td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>