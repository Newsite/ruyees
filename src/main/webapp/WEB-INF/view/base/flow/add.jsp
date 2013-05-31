<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/jquery.autocomplete.css" />
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/addUser/jquery.autocomplete.pack.js"></script>
<style type="text/css">
#adduser {
  background-color: #FFF;
  padding: 10px 20px;
}

#adduser .u_list>tbody>tr a {
  cursor: pointer;
  text-decoration: none
}

#inpUser {
  width: 400px;
}
</style>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//为inputForm注册validate函数
	$("#inputForm").validate({
		onsubmit : true,// 是否在提交是验证
		onfocusout : false,// 是否在获取焦点时验证
		onkeyup : false,// 是否在敲击键盘时验证 
		submitHandler : function(form) { //通过之后回调
			var name = $("#inpName").val();
			if (name == "" || name == null) {
				alert("流程名称不能为空!");
				return $("#inpName").focus();
			}

			//至少一位新教师
			var teacher_value = [];
			$('input[name^="bamProjectMembers"]').each(function() { //bamProjectMembers[*].newteachId
				var teachId = $(this).val();
				if (teachId != '') {
					teacher_value.push(teachId);
				}
			});
			if (teacher_value.length == 0) {
				alert('请添加新教师及指导教师！');
				return false;
			}

			$.ajax({
				url : "${ctx}/ajax/flow/findName",
				type : "GET",
				dataType : "json",
				data : {
					key : name
				},
				success : function(result) {
					if (result == true) {
						alert("此流程名称已存在！");
						return $("#inpName").focus();
					} else {
						document.form.method = "post";
						document.form.action = '${ctx}/campus/flow/save';
						document.form.submit();
						return;
					}
				}
			});
		},
		invalidHandler : function(form, validator) { //不通过回调
			return false;
		}

	});
	return;
});
</script>
<script type="text/javascript">
var id = '';
$(document).ready(
	function() {
		var name1 = "";
		var name2 = "";
		var id1 = "";
		var id2 = "";
		//选择人之前必择校
		$("#inpUser1").click(function() {
			var deptId = id;
			if (deptId == null || deptId == "") {
				alert('请先选择机构！');
				return $("#inpOrg").focus();
			}
		});
		//择学校之后再择学校内人员
		$("#inpUser1").autocomplete(
				"${ctx}/ajax/user/findByOrg", {
					matchContains : true,
					max : 100,
					width : 140,
					dataType : 'json',
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					extraParams : {
						q : function() {
							return $('#inpUser1').val();
						},
						deptId : function() {
							return $('#schId').val();
						}
					},

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
			name1 = item.name;
			id1 = item.id;
		});

		$("#inpUser2").autocomplete(
				"${ctx}/ajax/user/findByOrg", {
					matchContains : true,
					max : 100,
					width : 140,
					dataType : 'json',
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					extraParams : {
						q : function() {
							return $('#inpUser2').val();
						},
						deptId : function() {
							return $('#schId').val();
						}
					},
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
			name2 = item.name;
			id2 = item.id;
		});

		$("#u_list tbody a").click(function() {
			$(this).closest("tr").remove();
		});

		$("#addUser").click(function() {
			var repeat = false;
			$("#inpUser1").val("");
			$("#inpUser2").val("");
			$("#u_list tbody tr").each(
					function() {
						var id = $(this).find(
								"input")
								.first().val();
						if (id == id1)
							repeat = true;
					});
			if (repeat) {
				$.fn.jalert2("新教师不能重复添加！");
			} else {
				jQuery.ajax({
					type : "post",
					dataType : "json",
					url : "${ctx}/ajax/flow/checkFlowUser",
					data : {
						flowId : '${bean.fdId}',
						uid : id1,
						schId : $('#schId').val()
					},
					success : function(data) {
						if(data){
							alert(name1+'该新教师已经开始备课活动，请您联系本校其他教研主管进行确认。');
							return false;
						}else{
							var rows = $("#u_list tbody")
							.children("tr").length;
					if (rows == 0) {
						$("#u_list").removeClass(
								"hide");
					}
					$("#u_list tbody").append(
									'<tr><td>'
											+ name1
											+ '<input type="hidden" name="bamProjectMembers['+rows+'].newteachId" value="'+id1+'" /></td><td>'
											+ name2
											+ '<input type="hidden" name="bamProjectMembers['+rows+'].guidId" value="'+id2+'" /></td><td><a href="#" class="text-error">移除</a></td></tr>')
							.find("a").click(
									function() {
										$(this).closest("tr").remove();
									});
						}
					}
				});
			}
		});

	});
</script>

<script type="text/javascript">
$().ready(function() {
	$("#inpOrg").autocomplete("${ctx}/ajax/org/findByOrgName", {
		matchContains : true,
		max : 100,
		width : 368,
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
		name = item.fdName;
		id = item.fdId;
		$("#schId").val(id);
	});
})
</script>
</head>
<body>
  <form class="form-horizontal" id="inputForm" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" name="template.fdId"
      value="${bean.template.fdId}" /> <input type="hidden" name="user.fdId" value="${bean.user.fdId}" /> <input
      type="hidden" id="schId" name="schId" value="" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/campus/flow/list" class="tile-return">返回上页</a>
          <h4>组织备课</h4>
          <p>在本模块中，您可以组织新教师与指导教师开展备课活动。</p>
        </div>
        <table class="table">
          <tr>
            <th>备课活动</th>
            <td colspan="3"><input type="text" id="inpName" name="name" value="" class="span4 required" /></td>
          </tr>
          <tr>
            <th>添加学校</th>
            <td colspan="3"><input id="inpOrg" type="text" class="span4" /></td>
          </tr>
          <tr>
            <th class="span2">模板</th>
            <td class="span4">${bean.template.fdName}</td>
            <th class="span2">状态</th>
            <td class="span4"><j:ifelse test="${bean.template.fdStatus==true}">
                <j:then>已启用</j:then>
                <j:else>未启用</j:else>
              </j:ifelse></td>
          </tr>
          <tr>
            <th>项目</th>
            <td>${bean.template.program.fdName}</td>
            <th>课程</th>
            <td>${bean.template.course.fdName}</td>
          </tr>
          <tr>
            <th>分项</th>
            <td>${bean.template.item.fdName}</td>
            <th>阶段</th>
            <td>${bean.template.stage.fdName}</td>
          </tr>
          <tr>
            <th>学术作业成绩</th>
            <td>${bean.template.fdOperationWeight}<span class="add-on">(%)</span></td>
            <th>批课成绩</th>
            <td>${bean.template.fdAppofClass}<span class="add-on">(%)</span></td>
          </tr>
          <tr>
            <th>备注</th>
            <td colspan="3">${bean.template.description}</td>
          </tr>
          </tbody>
        </table>

        <table class="table">
          <tr>
            <th>新教师</th>
            <td><input id="inpUser1" type="text" class="span3" /></td>
            <th>指导教师</th>
            <td><input id="inpUser2" type="text" class="span3" /></td>
            <td><button id="addUser" type="button" class="btn">
                <i class="icon-plus-sign"></i>&nbsp;添加
              </button></td>
          </tr>
        </table>
        <table class="table hide" id="u_list">
          <thead>
            <tr>
              <th>新教师</th>
              <th>指导教师</th>
              <th>移除</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
        <p>
          <button class="btn btn-info" type="submit" value="确定" id="saveBtn">确定</button>
        </p>

      </div>
    </section>
  </form>
</body>
</html>