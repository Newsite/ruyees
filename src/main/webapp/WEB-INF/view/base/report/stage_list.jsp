<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <section id="exam-list">
    <div class="well">
      <div class="page-header">
        <a href="${ctx}/group/report/statList" class="tile-return">返回上页</a>
        <h4>集团备课跟踪</h4>
        <p>在本模块中，您可以跟踪集团所有国外考试项目新教师的备课进度。</p>
      </div>
      <form class="form-horizontal" action="${ctx}/group/report/query" name="form" method="post">
        <input type="hidden" name="param" value="lesson" />
        <table class="table ">
          <thead>
            <tr>
              <th width="10px">#</th>
              <th>关卡</th>
              <th width="15%">操作</th>
            </tr>
          </thead>
          <j:iter items="${page.list}" var="bean" status="vs">
            <tr>
              <td>${vs.index + 1}</td>
              <j:ifelse test="${bean.through eq true}">
                <j:then>
                  <td class="text-success">${bean.templateItem.fdName}</td>
                </j:then>
                <j:else>
                  <td>${bean.templateItem.fdName}</td>
                </j:else>
              </j:ifelse>
              <td>
              	<a href="${ctx}/group/report/contentList/${bean.project.fdId}/${bean.fdId}/${bean.newteachId}/${bean.fdIndex}">查看</a>
              	<j:if test="${bean.through eq true && (bean.fdIndex eq 2 || bean.fdIndex eq 4 || bean.fdIndex eq 6)}">
              		&nbsp;&nbsp;<a href="${ctx}/group/report/dataExp/${bean.fdId}/${bean.newteachId}/${bean.fdIndex}">下载</a>
              	</j:if>
              </td>
            </tr>
          </j:iter>
          </tbody>
        </table>
        <tags:pagination page="${page}" paginationSize="5" />
      </form>
    </div>
  </section>
</body>
</html>