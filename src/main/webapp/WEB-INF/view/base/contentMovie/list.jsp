<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
		document.form.method = "post";
		document.form.action = '${ctx}/group/contentMovie/delAll';
		document.form.submit();
		return;
	}
	// 查询分类
	$(function() {
		$('#fdTypeQuery').change(function() {
			var p1 = $(this).children('option:selected').val(); // 这就是selected的值
			document.form1.method = "get";
			document.form1.action = '${ctx}/group/contentMovie/list?fdType="' + p1 + '"';
			document.form1.submit();
			return;
		});
	});

	function query() {
		var fdType = $('#fdTypeQuery').val();
		var fdSubject = $('#fdNameQuery').val();

		document.form1.method = "get";
		document.form1.action = '${ctx}/group/contentMovie/list';
		document.form1.submit();
		return;
	}

	$(document).ready(function() {
		var flag = $("#flag").val();
		if (flag == 'no') {
			$.fn.jalert2("此信息已在备课阶段引用，不能删除！");
		}
	});

	function delById(fdId, fdType) {
		if (!confirm('您确定要删除吗？')) {
			return false;
		}
		document.form.method = "post";
		document.form.action = '${ctx}/group/contentMovie/delete?fdId=' + fdId + '&fdType=' + fdType + '';
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <h4>文档视频</h4>
      <p>在本模块中，您可以配置平台的所有文档、视频资源。</p>
    </div>
    <input type="hidden" id="flag" name="flag" value="${flag}" />
    <form class="form-search" name="form1">
      <label for="fdTypeQuery">筛选条件</label>&nbsp;<select name="fdType" id="fdTypeQuery">
        <option value="" <j:if test="${empty fdType}"> selected="selected"</j:if>>查看全部</option>
        <option value="1" <j:if test="${fdType==1}"> selected="selected"</j:if>>文档</option>
        <option value="2" <j:if test="${fdType==2}"> selected="selected"</j:if>>视频</option>
      </select>&nbsp;&nbsp;<label for="fdNameQuery">资源名称</label>&nbsp;<input type="text" id="fdNameQuery" name="fdName"
        value="${fdName}" />
      <button type="button" class="btn" onclick="query()"><i class="icon-search"></i>&nbsp;查询</button>
    </form>
    <p>
      <div class="btn-group">
        <a class="btn btn-info" href="${ctx}/group/contentMovie/add"><i class="icon-plus-sign icon-white"></i>&nbsp;添加</a>
        <a class="btn btn-info" id="delAll" onclick="delSel()"><i class="icon-trash icon-white"></i>&nbsp;批量删除</a>
      </div>
    </p>
    <form class="" id="inputForm" action="" name="form">
      <table class="table">
        <thead>
          <tr>
            <th width="5px"><input type="checkbox" name="select" id="selectAll" /></th>
            <th width="10px">#</th>
            <th>名称</th>
            <th width="10%">类型</th>
            <th width="20%">创建时间</th>
            <th width="15%">操作</th>
          </tr>
        </thead>
        <tbody>
          <j:iter items="${page.list}" var="bean" status="s">
            <tr>
              <td><label> <input type="checkbox" name="ids" class="check" value="${bean.fdId}" />
              </label></td>
              <td>${s.index+1}</td>
              <td>${bean.fdName}</td>
              <td>${bean.typeName}</td>
              <td>${fn:substring(bean.fdCreateTime, 0, 19)}</td>
              <td><a href="${ctx}/group/contentMovie/edit/${bean.fdId}">编辑</a>&nbsp;&nbsp;<a href="#"
                onclick="delById('${bean.fdId}','${fdType}')">删除</a>
                &nbsp;&nbsp;<a href="${ctx}/group/contentMovie/comment/${bean.fdId}">评论</a>
                </td>
            </tr>
          </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" searchParams="fdType=${fdType }&fdName=${fdName }" paginationSize="5" />
    </form>
    <script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
  </div>
</body>
</html>