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
          <a href="${ctx}/coach/package/list" class="tile-return">返回上页</a>
          <h4>学术作业审核</h4>
          <p>在本模块中，您可以审核新教师提交的学术作业。</p>
        </div>
        <table class="table">
          <tr>
            <td width="20%">作业包</td>
            <td colspan="3">${bean.fdName}</td>
          </tr>
          <tr>
            <td>状态</td>
            <td colspan="3"><j:ifelse test="${bean.through eq true}">
                <j:then>通过</j:then>
                <j:else>驳回</j:else>
              </j:ifelse></td>
          </tr>
          <tr>
            <td>评价</td>
            <td colspan="3">${bean.fdComment}</td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>