<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#addinputForm").validate({
			onsubmit : true,// 是否在提交是验证
			onfocusout : false,// 是否在获取焦点时验证
			onkeyup : false,// 是否在敲击键盘时验证 
			submitHandler : function(form) {// 通过之后回调
				var name = $("#fdName").val();
				$.ajax({
					url : "${ctx}/ajax/foundation/checkExam",
					type : "POST",
					dataType : "json",
					data : {
						name : name
					},
					success : function(result) {
						if (result == true) {
							$("#errorInfo").text("该名称已存在，请重新输入。");
							return;
						} else {
							document.addform.method = "post";
							document.addform.action = '${ctx}/group/examCategory/save';
							document.addform.submit();
							return;
						}
					}
				});
			},
			invalidHandler : function(form, validator) {// 不通过回调
				return false;
			}
		});
		return;
	});

	function query() {
		var fdName = $('#fdNameQuery').val();
		document.form1.method = "get";
		document.form1.action = '${ctx}/group/examCategory/list';
		document.form1.submit();
		return;
	}

	$(document).ready(function() {
		var flag = $("#flag").val();
		if (flag == 'no') {
			$.fn.jalert2("该条目已经在某个备课模板中被使用，请先将其从该备课模板中移除。");
		}
	});
</script>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <h4>试卷试题</h4>
      <p>在本模块中，您可以配置平台的所有试卷与试题信息。</p>
    </div>
    <input type="hidden" id="flag" name="flag" value="${flag}" />
    <%@ include file="/WEB-INF/view/base/examCategory/add.jsp"%>
    <form class="form-search" name="form1">
      <a class="btn btn-info" href="#add-category" data-toggle="modal"><i class="icon-plus-sign icon-white"></i>&nbsp;添加
      </a>&nbsp;&nbsp;<label for="fdNameQuery">试卷名称</label>&nbsp;<input type="text" id="fdNameQuery" name="fdName"
        value="${fdName}" class="span2">
      <button type="button" onclick="query()" class="btn"><i class="icon-search"></i>&nbsp;查询</button>
    </form>
    <table class="table">
      <thead>
        <tr>
          <th width="10px">#</th>
          <th>试卷</th>
          <th width="10%">查看试题</th>
          <th width="20%">创建日期</th>
          <th width="15%">操作</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${page.list}" var="bean" varStatus="vstatus">
          <tr>
            <td>${vstatus.index+1}</td>
            <td>${bean.fdName}</td>
            <td><a href="${ctx}/group/exam/list?fdCategoryId=${bean.fdId}">查看试题</a></td>
            <td>${fn:substring(bean.fdCreateTime, 0, 19)}</td>
            <td><a href="#update-category${vstatus.index + 1}" data-toggle="modal">编辑</a>&nbsp;&nbsp;<a
              href="#delete-category${vstatus.index + 1}" data-toggle="modal">删除</a>&nbsp;<tags:alert
                alertId="delete-category${vstatus.index + 1}" name="试卷" tagIndex="${vstatus.index + 1}"
                href="${ctx}/group/examCategory/delete/${bean.fdId }"></tags:alert> <%@ include
                file="/WEB-INF/view/base/examCategory/edit.jsp"%> <%@ include
                file="/WEB-INF/view/base/examCategory/view.jsp"%></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>