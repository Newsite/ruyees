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
  <section id="exam-list">
    <div class="well">
      <div style="padding-bottom: 10px;"></div>
      <form class="form-horizontal" id="inputForm" action="" name="form">
        <table class="table ">
          <thead>
            <tr>
              <th width="10px">#</th>
              <th width="10%">新教师</th>
              <th width="30%">作业包</th>
              <th>作业步骤</th>
              <th width="10%">审批状态</th>
              <th width="15%">操作</th>
            </tr>
          </thead>
          <j:iter items="${page.list}" var="bean" status="vstatus">
            <tr>
              <td>${vstatus.index+1}</td>
              <td>${bean.bamPackage.phase.newTeach.realName}</td>
              <td>${bean.bamPackage.fdName}</td>
              <td>${bean.fdName}</td>
              <td><j:ifelse test="${bean.through eq true}">
                  <j:then>通过</j:then>
                  <j:else>待审核</j:else>
                </j:ifelse></td>
              <td><j:set name="show" value="true" /> <j:iter items="${bean.bamIndexs}" var="bean2">
                  <j:ifelse test="${bean2.status eq 4}">
                    <j:else>
                      <j:set name="show" value="false" />
                    </j:else>
                  </j:ifelse>
                </j:iter> <j:ifelse test="${show eq true}">
                  <j:then>
                    <j:ifelse test="${bean.through eq true}">
                      <j:then>
                        <a href="${ctx}/coach/operation/view/${bean.fdId}">查看</a>
                      </j:then>
                      <j:else>
                        <a href="${ctx}/coach/operation/edit/${bean.fdId}">审核</a>
                      </j:else>
                    </j:ifelse>
                  </j:then>
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