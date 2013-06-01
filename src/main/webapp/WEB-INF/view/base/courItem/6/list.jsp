<%@page import="org.apache.commons.lang3.ArrayUtils"%>
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
      <form class="form-horizontal" id="inputForm" action="" name="form">
        <table class="table">
          <thead>
            <tr>
              <th width="10px">#</th>
              <th width="10%">新教师</th>
              <th>课件</th>
              <th width="10%">状态</th>
              <th width="15%">操作</th>
            </tr>
          </thead>
          <j:iter items="${page.list}" var="bean" status="vstatus">
            <tr>
              <td>${vstatus.index+1}</td>
              <td>${bean.bamCourseware.phase.newTeach.realName}</td>
              <td>${bean.name}</td>
              <td><j:ifelse test="${bean.status==4}">
                  <j:then>通过</j:then>
                  <j:else>待审核</j:else>
                </j:ifelse></td>
              <td><j:ifelse test="${bean.through eq true}">
                  <j:then>
                    <a href="${ctx}/coach/courItem/view/6/${bean.fdId}">查看</a>
                  </j:then>
                  <j:else>
                    <a href="${ctx}/coach/courItem/edit/6/${bean.fdId}">审核</a>
                  </j:else>
                </j:ifelse></td>
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