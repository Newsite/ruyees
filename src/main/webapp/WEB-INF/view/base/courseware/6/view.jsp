<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
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
          <a href="${ctx}/coach/courseware/list/6" class="tile-return">返回上页</a>
          <h4>课件审批</h4>
          <p>在本模块中，您可以审批新教师提交的课件。</p>
        </div>
        <table class="table ">
          <tr>
            <th width="20%">新教师</th>
            <td colspan="3">${person.realName}</td>
          </tr>
          <tr>
            <th>审批状态</th>
            <td colspan="3"><j:ifelse test="${bean.through eq true}">
                <j:then>通过</j:then>
                <j:else>驳回</j:else>
              </j:ifelse></td>
          </tr>
          <tr>
            <th>评价</th>
            <td colspan="3"><textarea name="fdComment" id="fdComment" rows="6" cols="80" class="span7">${bean.fdComment}</textarea></td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>