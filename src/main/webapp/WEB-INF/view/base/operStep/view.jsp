<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
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
          审批作业步骤查看&nbsp;&nbsp; <a href="${ctx}/coach/operation/list">返回</a>
        </div>
        <table class="table ">
          <tr>
            <th width="20%">作业步骤</th>
            <td colspan="3">${bean.fdName}</td>
          </tr>
          <tr>
            <th>审批状态</th>
            <td colspan="3"><j:ifelse test="${bean.through eq true}">
                <j:then>通过</j:then>
                <j:else>驳回</j:else>
              </j:ifelse></td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>