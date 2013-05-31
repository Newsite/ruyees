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
          <a href="${ctx}/coach/package/edit/${bean.bamOperation.bamPackage.fdId}" class="tile-return">返回上页</a>
          <h4>学术作业审核</h4>
          <p>在本模块中，您可以审核新教师提交的学术作业。</p>
        </div>
        <table class="table">
          <thead>
            <tr><td>学术作业审核表</td><td></td></tr>
          </thead>
          <tbody>
          <tr>
            <td width="20%">成果指标</td>
            <td>${bean.fdIndexName}</td>
          </tr>
          <tr>
            <td>查看内容</td>
            <td><a href="${ctx}/common/download/${bean.bamAttMain.fdId}">${bean.fdFileName}</a></td>
          </tr>
          <tr>
            <td>提交时间</td>
            <td>${fn:substring(bean.uploadTime, 0, 19)}</td>
          </tr>
          <tr>
            <td>审核时间</td>
            <td>${fn:substring(bean.appoveTime, 0, 19)}</td>
          </tr>
          <tr>
            <td>指标分值</td>
            <td>${bean.fdValue}&nbsp;分</td>
          </tr>
          <j:if test="${bean.status==4}">
	          <tr>
	            <td>导师评分</td>
	            <td>${bean.fdToValue}&nbsp;分</td>
	          </tr>
          </j:if>
          <tr>
            <td>审核结果</td>
            <td><j:ifelse test="${bean.status==4}">
                <j:then><span class="text-success">通过</span></j:then>
                <j:else><span class="text-error">驳回</span></j:else>
              </j:ifelse></td>
          </tr>
          <tr>
            <td>导师评语</td>
            <td colspan="3">${bean.remark}</td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>