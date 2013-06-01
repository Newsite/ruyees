<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/datepicker.css"></link>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/addUser/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap-datepicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var name = [], id = [];
		$("#inpOrg").autocomplete("${ctx}/ajax/org/findByOrgNames", {
			multiple : true,
			matchContains : true,
			max : 100,
			width : 410,
			dataType : 'json',
			extraParams : {
				q : function() {
					return encodeURI($("#inpOrg").val());
				}
			},
			//加入对返回的json对象进行解析的函数，函数返回一个数组
			parse : function(data) {
				var rows = [];
				for ( var i = 0; i < data.length; i++) {
					rows[rows.length] = {
						data : data[i],
						value : data[i].fdName,
						result : data[i].fdName
					//显示在输入文本框里的内容 ,
					};
				}
				return rows;
			},
			formatItem : function(item) {
				return item.fdName;
			}
		}).result(function(event, item) {
			name.push(item.fdName);
			id.push(item.fdId);
			$("#schIds").val(id);
		});
	});

	$(document).ready(function() {
		var name1 = [];
		var id1 = [];
		$("#inpGuid").autocomplete("${ctx}/ajax/user/findByOrgs", {
			multiple : true,
			matchContains : true,
			max : 100,
			width : 140,
			dataType : 'json',
			extraParams : {
				schIds : function() {
					return $('#schIds').val();
				}
			},
			//extraParams: {schIds: function() { return $('#schIds').val(); } },
			//加入对返回的json对象进行解析的函数，函数返回一个数组
			parse : function(data) {
				var rows = [];
				for ( var i = 0; i < data.length; i++) {
					rows[rows.length] = {
						data : data[i],
						value : data[i].name,
						result : data[i].name
					//显示在输入文本框里的内容 ,
					};
				}
				return rows;
			},
			formatItem : function(item) {
				return item.name;
			}
		}).result(function(event, item) {
			name1.push(item.name);
			id1.push(item.id);
			$("#guidIds").val(id1);
		});
	});
</script>
<script>
	$(document).ready(function() {
		window.prettyPrint && prettyPrint();
		$('#startDate').datepicker({
			format : 'yyyy-mm-dd'
		}).datepicker("setValue", $('#startDate').val() == null ? new Date() : $('#begin').val());
		$('#endDate').datepicker({
			format : 'yyyy-mm-dd'
		}).datepicker("setValue", $('#endDate').val() == null ? new Date() : $('#end').val());
	});
</script>
<script type="text/javascript">
	function query() {
		document.form1.method = "get";
		document.form1.action = '${ctx}/group/report/statList';
		document.form1.submit();
		return;
	}
	
	function excelExp() {
		document.form1.expExcel.value="1";
		document.form1.method = "get";
		document.form1.action = '${ctx}/group/report/excelList';
		document.form1.submit();
		return;
	}
	$(document).ready(function() {
		$("#clear").click(function() {
			$("#inpOrg").val("");
			$("#schIds").val("");
			$("#inpGuid").val("");
			$("#guidIds").val("");
		});
	});
</script>
</head>
<body>
  <div class="well">
    <div class="page-header">
      <a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
      <h4>集团备课跟踪</h4>
      <p>在本模块中，您可以跟踪集团所有国外考试项目新教师的备课进度。</p>
    </div>

    <form class="form-horizontal" action="" name="form1">
      <input type="hidden" name="expExcel" value="" /> 
      <input type="hidden" id="begin" value="${startDate}" /> 
      <input type="hidden" id="end" value="${endDate}" />
      <table class="table">
        <tbody>
        <tr>
          <th>选择学校</th>
          <td><input id="inpOrg" type="text" name="orgNames" value="${orgNames}" class="span6" /> <input
            id="schIds" type="hidden" name="schIds" value="${schIds}" /></td>
        </tr>
        <tr>
          <th>选择导师</th>
          <td><input id="inpGuid" type="text" name="guidNames" value="${guidNames}" class="span6" />
            <input id="guidIds" type="hidden" name="guidIds" value="${guidIds}" /></td>
        </tr>
        <tr>
          <th>时间范围 </th>
          <td><span class="text">开始时间</span>&nbsp;<input id="startDate" name="startDate" type="text" value="${startDate}"
            class="input-small datepicker" />&nbsp;&nbsp;&nbsp;&nbsp;<span class="text">结束时间</span>&nbsp;<input id="endDate" name="endDate" type="text" value="${endDate}" class="input-small datepicker" /></td>
        </tr>
        <tr>
          <td colspan="2">
            <a type="button" class="btn btn-info" onclick="query()"><i class="icon-search icon-white"></i>&nbsp;查询</a>
            <a type="button" class="btn" id="clear"><i class="icon-repeat"></i>&nbsp;重置</a>
            <a type="button" class="btn" onclick="excelExp()"><i class="icon-list-alt"></i>&nbsp;EXCEL导出</a>
            </td>
        </tr>
        </tbody>
      </table>
    </form>
    <form class="form-horizontal" action="" name="form">
      <table class="table">
        <thead>
          <tr>
            <th width="10px">#</th>
            <th width="10%">新教师</th>
            <th width="10%">指导教师</th>
            <th width="15%">开始时间</th>
            <th>备课活动</th>
            <th width="10%">最终得分</th>
            <th width="15%">详情</th>
          </tr>
        </thead>
        <j:iter items="${page.list}" var="bean" status="vs">
          <tr>
            <td>${vs.index+1}</td>
            <td>${bean.newTeach.realName}</td>
            <td>${bean.guid.realName}</td>
            <td>${fn:substring(bean.project.createTime, 0, 10)}</td>
            <td>${bean.project.name}&nbsp;/&nbsp;${bean.project.template.fdName}</td>
            <td <j:if test="${not empty bean.finalScore}">class="text-success"</j:if>>${bean.finalScore}</td>
            <td><a href="${ctx}/group/report/stageList/${bean.project.fdId}/${bean.newteachId}">查看</a></td>
          </tr>
        </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" searchParams="schIds=${schIds}&guidIds=${guidIds}&startDate=${startDate}&endDate=${endDate}" paginationSize="5" />
    </form>
  </div>
</body>
</html>