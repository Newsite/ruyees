<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("[href^='#subtr']").click(function(e) {
			e.preventDefault();
			var id = $(this).attr("href");
			$(id).slideToggle("fast");
		});
	});

	function delById(fdId) {
		if (!confirm('您确定要删除该备课模板？')) {
			return false;
		}
		document.form.method = "post";
		document.form.action = '${ctx}/group/template/delete/' + fdId + '';
		document.form.submit();
		return;
	}

	$(document).ready(function() {
		var flag = $("#flag").val();
		if (flag == 'no') {
			$.fn.jalert2("您无法删除该备课模板，请在系统管理员的协助下完成相关操作。");
		}
	});
</script>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
      <h4>备课模板配置</h4>
      <p>在本模块中，您可以配置集团所有国外考试项目课程及分项的备课模板。</p>
      <ul>
        <li><span class="text-info">授权</span>&nbsp;您最常使用的功能。作为集团主管，您可以授权各地学校主管根据下列模板组织备课活动。</li>
        <li><span class="text-info">编辑</span>&nbsp;您偶尔会使用的功能。您可以配置下列备课模板的详细信息，包括描述文字和各类资源。</li>
        <li><span class="muted">删除</span>&nbsp;不建议您使用的功能。请在系统管理员的协助下完成删除备课模板的相关操作。</li>
      </ul>
    </div>
    <input type="hidden" id="flag" name="flag" value="${flag}" />
    <p>
      <a class="btn btn-info" href="${ctx}/group/template/add"> <i class="icon-plus-sign icon-white"></i>&nbsp;添加
      </a>
    </p>
    <form class="form-horizontal" id="inputForm" action="" name="form">
      <table class="table">
        <thead>
          <tr>
            <th width="10px">#</th>
            <th>模板</th>
            <th width="15%">状态</th>
            <th width="15%">集团授权</th>
            <th width="15%">操作</th>
          </tr>
        </thead>
        <tbody>
          <j:iter items="${page.list}" var="bean" status="vstatus">
            <tr>
              <td>${vstatus.index + 1}</td>
              <td>${bean.fdName}</td>
              <td><j:ifelse test="${bean.fdStatus==false}">
                  <j:then>未启用</j:then>
                  <j:else>已启用</j:else>
                </j:ifelse></td>
              <td><a href="${ctx }/group/template/assignment/${bean.fdId}">授权</a>&nbsp;&nbsp;<a
                href="#subtr${vstatus.index + 1}">查看</a></a></td>
              <td><a href="${ctx}/group/template/edit/${bean.fdId}">编辑</a>&nbsp;&nbsp;<a class="muted" href="#"
                onclick="delById('${bean.fdId}')">删除</a></td>
            </tr>
            <tr id="subtr${vstatus.index+1}" style="display: none;">
              <td colspan="5" class="tableTd pl50">
                <table class="table">
                  <tbody>
                    <tr>
                      <th style="border-top: 0;">当前已授权用户列表</th>
                    </tr>
                    <j:iter items="${bean.bamProjects}" var="project" status="vs1">
                      <tr>
                        <td style="border-top: 1px solid #f5f5f5;">${project.user.realName}</td>
                        <%--  <td>${bean.fdDateCreated}</td> --%>
                      </tr>
                    </j:iter>
                  </tbody>
                </table>
              </td>
            </tr>
          </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" paginationSize="5" />
    </form>
  </div>
</body>
</html>