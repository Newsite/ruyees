<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery.jalert.js" type="text/javascript"></script>
<script type="text/javascript">
	// 查询分类
	$(function() {
		$('#mySelect').change(function() {
			var p1 = $(this).children('option:selected').val(); // 这就是selected的值 
			document.form1.method = "get";
			document.form1.action = '${ctx}/group/diction/list?fdType="' + p1 + '"';
			document.form1.submit();
			return;
		});
	});

	jQuery(document).ready(function() {
		$("#inputForm").validate({
			onsubmit : true, // 是否在提交是验证
			onfocusout : false, // 是否在获取焦点时验证
			onkeyup : false, // 是否在敲击键盘时验证 
			submitHandler : function(form) { // 通过之后回调
				var fdType = $("#fdType").val();
				var name = $("#fdName").val();
				$.ajax({
					url : "${ctx}/ajax/foundation/checkDiction",
					type : "POST",
					dataType : "json",
					data : {
						fdType : fdType,
						name : name
					},
					success : function(result) {
						if (result == true) {
							$("#errorInfo").text("该名称已存在，请重新输入。");
							return;
						} else {
							document.addform.method = "post";
							document.addform.action = '${ctx}/group/diction/save';
							document.addform.submit();
							return;
						}
					}
				});
			},
			invalidHandler : function(form, validator) { // 不通过回调
				return false;
			}
		});
		return;
	});

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
      <h4>基本信息</h4>
      <p>在本模块中，您可以配置平台的各项基本信息。</p>
    </div>
    <input type="hidden" id="flag" name="flag" value="${flag}" />
    <input type="hidden" name="fdType" value="${fdType}" />
    <form class="form-inline" name="form1">
      <a href="#add-diction" class="btn btn-info" data-toggle="modal"><i class="icon-plus-sign icon-white"></i>&nbsp;添加
      </a>&nbsp;&nbsp;<label for="mySelect">筛选条件</label>&nbsp;<select name="fdType" id="mySelect">
        <option value="" <j:if test="${empty fdType}"> selected="selected"</j:if>>查看全部</option>
        <option value="1" <j:if test="${fdType==1}"> selected="selected"</j:if>>项目</option>
        <option value="2" <j:if test="${fdType==2}"> selected="selected"</j:if>>课程</option>
        <option value="3" <j:if test="${fdType==3}"> selected="selected"</j:if>>分项</option>
        <option value="4" <j:if test="${fdType==4}"> selected="selected"</j:if>>阶段</option>
      </select>
    </form>
    <%@ include file="/WEB-INF/view/base/diction/add.jsp"%>
    <table class="table">
      <thead>
        <tr>
          <th width="10px">#</th>
          <th>名称</th>
          <th width="10%">类型</th>
          <th width="20%">创建时间</th>
          <th width="15%">操作</th>
        </tr>
      </thead>
      <tbody>
        <j:iter items="${page.list}" var="bean" status="vstatus">
          <tr>
            <td>${bean.fdSeqNum}</td>
            <td>${bean.fdName}</td>
            <td><j:switch value="${bean.fdType}">
                <j:case value="1">项目</j:case>
                <j:case value="2">课程</j:case>
                <j:case value="3">分项</j:case>
                <j:default>阶段</j:default>
              </j:switch></td>
            <td>${fn:substring(bean.fdDateCreated, 0, 19)}</td>
            <td><a href="#update-diction${vstatus.index + 1}" data-toggle="modal">编辑</a>&nbsp;&nbsp;<a
              href="#delete-category${vstatus.index + 1}" data-toggle="modal">删除</a><tags:alert
              alertId="delete-category${vstatus.index + 1}" name="基本信息" tagIndex="${vstatus.index + 1}"
              href="${ctx}/group/diction/delete?fdId=${bean.fdId}&fdType=${fdType}"></tags:alert><%@ include
              file="/WEB-INF/view/base/diction/edit.jsp"%><%@ include
              file="/WEB-INF/view/base/diction/view.jsp"%></td>
          </tr>
        </j:iter>
      </tbody>
    </table>
    <tags:pagination page="${page}" paginationSize="5" />
  </div>
</body>
</html>