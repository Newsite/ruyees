<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<j:set name="fdPackageId" value="${fdPackageId}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <input type="hidden" id="flag" name="flag" value="${flag}" />
  <section id="search-query"></section>
  <section id="exam-list">
    <div class="well">
      <div style="margin-bottom: 10px;">
        <a href="${ctx}/campus/flow/list" class="tile-return">返回上页</a>
        <h4></h4>
        <p></p>
      </div>
      <form class="form-horizontal" id="inputForm" action="" name="form">
        <table class="table ">
          <thead>
            <tr>
              <th width="7%">#</th>
              <th width="48%">教研步骤</th>
              <th width="7%">序号</th>
              <th width="30%">创建日期</th>
              <th width="15%">操作</th>
            </tr>
          </thead>
          <tbody>
            <j:iter items="${bean}" var="bean" status="vstatus">
              <tr>
                <td>${vstatus.index+1}</td>
                <td>${bean.fdName}</td>
                <td>${bean.fdOrder}</td>
                <td>${bean.fdCreateTime}</td>
                <td><a href="${ctx}/campus/flow/edit/bamOperation/edit/${bean.fdId}/2/1">编辑</a></td>
              </tr>
            </j:iter>
          </tbody>
        </table>
      </form>
      <script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
    </div>
  </section>
</body>
</html>