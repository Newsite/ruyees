<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/datepicker.css"></link>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/addUser/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
</head>
<body>
  <form class="form-horizontal" method="" id="inputForm" action="" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/grade/list" class="tile-return">返回上页</a>
          <h4>批课安排</h4>
          <p>在本模块中，您可以安排新教师参加批课活动。</p>
        </div>
        <table class="table">
          <tr>
            <th width="20%">主题</th>
            <td colspan="3">${bean.title}</td>
          </tr>
          <tr>
            <th>新教师</th>
            <td colspan="3">${bean.newTeach.realName}</td>
          </tr>
          <tr>
            <th>导师打分</th>
            <td colspan="3">${bean.value}</td>
          </tr>
          <tr>
            <td colspan="4">
              <table class="table" id="u_list">
                <thead>
                  <tr>
                    <th>批课老师</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>评分表</th>
                  </tr>
                </thead>
                <tbody>
                  <j:iter items="${bean.gradeItems}" var="item" status="vs1">
                    <tr>
                      <td><input type="hidden" name="gradeItems[${vs1.index}].fdId" value="${item.fdId}" />
                        ${item.advier.realName}<input type="hidden" name="gradeItems[${vs1.index}].advierId"
                        value="${item.advierId}" /></td>
                      <td><input type="hidden" name="gradeItems[${vs1.index}].startTime" value="${item.startTime}" />
                        ${fn:substring(item.startTime, 0, 16)}</td>
                      <td><input type="hidden" name="gradeItems[${vs1.index}].endTime" value="${item.endTime}" />
                        ${fn:substring(item.endTime, 0, 16)}</td>
                      <td><a href="${ctx}/coach/grade/itemView/${bean.fdId}/${item.fdId}">查看</a>&nbsp;&nbsp;|
                      	  <a href="${ctx}/coach/grade/expRecord/${bean.fdId}/${item.fdId}">导出EXCEL</a>
                      </td>
                    </tr>
                    </td>
                    </tr>
                  </j:iter>
                </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <th>导师评语</th>
            <td colspan="3">${bean.remark}</td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>