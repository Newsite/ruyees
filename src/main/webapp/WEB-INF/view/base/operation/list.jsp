<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<j:set name="fdPackageId" value="${fdPackageId}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript">
	// jquery获取复选框值  
	function delSel() {
		if (!confirm('您确定要批量删除吗？')) {
			return false;
		}
		var chk_value = [];
		$('input[name="ids"]:checked').each(function() {
			chk_value.push($(this).val());
		});
		if (chk_value.length == 0) {
			alert('请选择需要批量删除的内容。');
			return false;
		}
		var fdPackageId = $('#fdPackageId').val();
		document.form.method = "post";
		document.form.action = '${ctx}/group/operation/delete?fdPackageId='
				+ fdPackageId + '';
		document.form.submit();
		return;
	}

	$(document).ready(function() {
		var flag = $("#flag").val();
		if (flag == 'no') {
			$.fn.jalert2("此信息已在备课阶段引用，不能删除！");
		}
	});

	function delById(fdId, fdPackageId) {
		if (!confirm('您确定要删除？')) {
			return false;
		}
		document.form.method = "post";
		document.form.action = '${ctx }/group/operation/delete/' + fdId + '/'
				+ fdPackageId + '';
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <input type="hidden" id="flag" name="flag" value="${flag}" />
  <input type="hidden" id="fdPackageId" name="fdPackageId" value="${fdPackageId}" />
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/group/operPackage/list" class="tile-return">返回上页</a>
      <h4>学术作业包</h4>
      <p>在本模块中，您可以配置平台所有课程及分项的学术作业包信息。</p>
      <p>您当前配置学术作业包为&nbsp;<span class="text-info">${packageName}</span></p>
    </div>
    <p>
      <div class="btn-group">
        <a class="btn btn-info" href="${ctx}/group/operation/add?fdPackageId=${fdPackageId}"><i class="icon-plus-sign icon-white"></i>&nbsp;添加</a>
        <a class="btn btn-info" id="delAll" onclick="delSel()"><i class="icon-trash icon-white"></i>&nbsp;批量删除</a>
      </div>
    </p>
    <form class="form-horizontal" id="inputForm" action="" name="form">
      <table class="table">
        <thead>
          <tr>
            <th width="5px"><input type="checkbox" name="select" id="selectAll" /></th>
            <th width="10px">#</th>
            <th>教研步骤</th>
            <th width="10%">作业分数</th>
            <th width="20%">创建日期</th>
            <th width="15%">操作</th>
          </tr>
        </thead>
        <tbody>
          <j:iter items="${page.list}" var="bean" status="vstatus">
              <j:set name="value" value="0" />
              <j:iter items="${bean.indexs}" var="index" status="vs">
              	<j:set name="value" value="${value+index.fdValue}" />
              </j:iter>
            <tr>
              <td style="width: 10px;"><label> <input type="checkbox" name="ids" class="check"
                  value="${bean.fdId}" />
              </label></td>
              <td>${bean.fdOrder}</td>
              <td>${bean.fdName}</td>
              <td>${value}</td>
              <td>${fn:substring(bean.fdCreateTime, 0, 19)}</td>
              <td><a href="${ctx }/group/operation/edit?fdId=${bean.fdId}&fdPackageId=${fdPackageId}">编辑</a>&nbsp;&nbsp;
                <a href="#" onclick="delById('${bean.fdId}','${fdPackageId}')">删除</a></td>
            </tr>
          </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" paginationSize="5" />
    </form>
    <script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
  </div>
</body>
</html>