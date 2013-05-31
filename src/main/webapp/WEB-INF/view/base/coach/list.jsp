<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="#" class="tile-return">返回上页</a>
      <h4>学术作业审核</h4>
      <p>在本模块中，您可以审核新教师提交的学术作业。</p>
    </div>
    <section id="exam-list">
      <div class="well">
        <form class="form-horizontal" id="inputForm" action="" name="form">
          <table class="table ">
            <thead>
              <tr>
                <th width="10px">#</th>
                <th width="10%">新教师</th>
                <th width="20%">作业包</th>
                <th>作业</th>
                <th width="10%">指标</th>
                <th width="10%">状态</th>
                <th width="15%">操作</th>
              </tr>
            </thead>
            <j:iter items="${page.list}" var="bean" status="vstatus">
              <tr>
                <td>${vstatus.index+1}</td>
                <td>${bean.bamOperation.bamPackage.phase.newTeach.realName}</td>
                <td>${bean.bamOperation.bamPackage.fdName}</td>
                <td>${bean.bamOperation.fdName}</td>
                <td>${bean.fdIndexName}</td>
                <td><j:ifelse test="${bean.status==4}">
                    <j:then>通过</j:then>
                    <j:else>待审核</j:else>
                  </j:ifelse></td>
                <td><j:ifelse test="${bean.status==4}">
                    <j:then>
                      <a href="${ctx}/coach/index/view/${bean.fdId}">查看</a>
                    </j:then>
                    <j:else>
                      <a href="${ctx}/coach/index/edit/${bean.fdId}">审核</a>
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
  </div>
</body>
</html>