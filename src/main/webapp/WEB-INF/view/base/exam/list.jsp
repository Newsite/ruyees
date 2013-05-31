<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<j:set name="fdCategoryId" value="${fdCategoryId}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
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
		var fdCategoryId = $('#fdCategoryId').val();

		document.form.method = "post";
		document.form.action = '${ctx}/group/exam/delete?fdCategoryId='
				+ fdCategoryId + '';
		document.form.submit();
		return;
	}

	$(document).ready(function() {
		var flag = $("#flag").val();
		if (flag == 'no') {
			$.fn.jalert2("此信息已在备课阶段引用，不能删除！");
		}
	});

	function delById(fdId, fdCategoryId) {
		if (!confirm('您确定要删除吗？')) {
			return false;
		}
		document.form.method = "post";
		document.form.action = '${ctx}/group/exam/delete/' + fdId + '/'
				+ fdCategoryId + '';
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <input type="hidden" id="flag" name="flag" value="${flag}" />
  <input type="hidden" id="fdCategoryId" name="fdCategoryId" value="${fdCategoryId}" />
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/group/examCategory/list" class="tile-return">返回上页</a>
      <h4>试卷试题</h4>
      <p>在本模块中，您可以配置平台的所有试卷与试题信息。</p>
      <p>您当前配置试卷为&nbsp;<span class="text-info">${fdCategoryName}</span></p>
    </div>
    <p>
      <div class="btn-group">
        <a class="btn btn-info" href="${ctx}/group/exam/add?fdCategoryId=${fdCategoryId}"><i class="icon-plus-sign icon-white"></i>&nbsp;添加</a>
        <a class="btn btn-info" id="delAll" onclick="delSel()"><i class="icon-trash icon-white"></i>&nbsp;批量删除</a>
      </div>
    <p>
    <form class="form-horizontal" id="inputForm" action="" name="form">
      <table class="table">
        <thead>
          <tr>
            <th width="5px"><input type="checkbox" name="select" id="selectAll" /></th>
            <th width="15px">#</th>
            <th>试题</th>
            <th width="10%">题型</th>
            <th width="20%">创建时间</th>
            <th width="15%">操作</th>
          </tr>
        </thead>
        <j:iter items="${page.list}" var="bean" status="vstatus">
          <tr>
            <td>
              <label><input type="checkbox" name="ids" class="check" value="${bean.fdId}" />
              </label>
            </td>
            <td>${bean.fdOrder}</td>
            <td>${bean.fdSubject}</td>
            <td><j:ifelse test="${bean.fdType == 0}">
                <j:then>单选题</j:then>
                <j:else>多选题</j:else>
              </j:ifelse></td>
            <td>${fn:substring(bean.fdCreateTime, 0, 19)}</td>
            <td>
            	<a href="${ctx }/group/exam/edit?fdId=${bean.fdId}&fdCategoryId=${fdCategoryId}">编辑</a>
            	<a onclick="delById('${bean.fdId}','${fdCategoryId}')">删除</a>
            </td>
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