<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/datepicker.css"></link>
<link rel="stylesheet" href="${ctx}/resources/css/timepicker.css" />
<style type="text/css">
.form-horizontal .control-group {
  margin-bottom: 12px
}

#pk_list .dl-horizontal {
  margin-bottom: 10px;
}

#pk_list dt {
  width: 310px;
  font-weight: normal;
  text-align: left;
}

#pk_list dd {
  margin-left: 320px;
}

.timePicker {
  margin-left: 15px
}
</style>

<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/addUser/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap-timepicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>

<script type="text/javascript">
jQuery(document).ready(function() {
	$("#title").focus();

	$("#inputForm").validate({
		onsubmit : true,// 是否在提交是验证
		onfocusout : false,// 是否在获取焦点时验证
		onkeyup : false,// 是否在敲击键盘时验证 
		submitHandler : function(form) { //通过之后回调
			//新教师必须存在
			var uid = $('input:radio[name="uid"]:checked').val();
			if (uid == "" || uid == null) {
				$.fn.jalert2("新教师不存在！");
				return false;
			}
			//至少一位批课老师
			var advier_value = [];
			$('input[name^="gradeItems["][name$=".advierId"]').each(
				function() {
					var advierId = $(this).val();
					if (advierId != '') {
						advier_value.push(advierId);
					}
				});
			if (advier_value.length == 0) {
				$.fn.jalert2('请添加批课老师|开始时间|结束时间！');
				return false;
			}

			//主题唯一
			var name = $("#title").val();
			$.ajax({
					url : "${ctx}/ajax/foundation/checkGrade",
					type : "POST",
					dataType : "json",
					data : {name : name},
					success : function(result) {
						if (result == true) {
							$("#errorInfo").text("此主题已存在");
							return;
						} else {
							document.form.method = "post";
							document.form.action = '${ctx}/coach/grade/save';
							document.form.submit();
							return;
						}
					}
				});
		},
		invalidHandler : function(form,validator) { //不通过回调
			return false;
		}

	});
	return;
});
</script>
<script type="text/javascript">
function isTime(str)
{
  var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
  if (a == null) {return false;}
  if (a[1]>24 || a[3]>60 || a[4]>60)
  {
    return false;
  }
  return true;
}
$(document).ready(function() {
	var name = "";
	var id = "";
	$("#teachers").autocomplete(
		"${ctx}/ajax/user/findByOrg", {
			matchContains : true,
			max : 100,
			width : 220,
			dataType : 'json',
			extraParams : {
				q : function() {
					return $('#inpUser').val();
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
		name = item.name;
		id = item.id;
	});

	$("#addUser").click(function() {
		var repeat = false;
		var empty = false;
		var teach = false;
		$("#pk_list .alert").each(
				function() {
					var id0 = $(this).find(".pkteach").val();
					var yjtime = $(this).find(".pkTime").val();
					var startDate = $("#startDate").val();
					var startTime = $("#startTime").val();
					var dqtime = startDate+ " " + startTime+ ":00";
					if (id0==id && dqtime == yjtime)
						repeat = true;
				});
		$(".form-addPlan :text").each(
						function() {
							if ($(this).val() == "") {
								$(this).after('&nbsp;&nbsp;<span class="text-error">不能为空！</span>');
								$(this).bind("focus",
										function() {
											$(this).nextAll().remove();
										})
								empty = true;
								return false;
							} else if ($(this).attr("id") == "teachers"
									&& $(this).val() != name) {
								$(this).after('&nbsp;&nbsp;<span class="text-error">请选择老师！</span>');
								teach = true;
								return false;
							}
						});
		if (repeat) {
			$.fn.jalert2("批课老师同一时间不能重复添加！");
		} else if (!empty && !teach) {
			var rows = $("#pk_list").children(".alert").length;
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var startDateTime = startDate+ " " + startTime+ ":00";
			var endDataTime = endDate + " "+ endTime + ":00";
			if(!isTime(startTime+":00")){
				$.fn.jalert2("开始时间格式不正确！");
				return false;
			}
			if(!isTime(endTime+":00")){
				$.fn.jalert2("结束时间格式不正确！");
				return false;
			}
			//时间比较 2012-12-19 10:02:00
			var date1 = new Date(Date.parse(startDateTime.replace("-","/")));
			var date2 = new Date(Date.parse(endDataTime.replace("-","/")));
			if (date1 > date2) {
				$.fn.jalert2("开始时间不能大于结束时间！");
				return false;
			}
			var addr = $("#addr").val();
			var remark = $("#remark").val();
			$("#pk_list").append(
				'<div class="alert alert-success" ><button type="button" class="close" data-dismiss="alert">×</button><dl class="dl-horizontal"><dt>批课教师：'
				 + name+ '<input type="hidden" name="gradeItems['+rows+'].advierId" class="pkteach" value="'
				 + id +'"/></dt><dd>批课时间： '+ startDate+ " " + startTime+ ' ~ '+ endDate+ " "+ endTime
				 + '<input type="hidden" name="gradeItems['+rows+'].startTime" class="pkTime" value="'
				 +startDateTime+'"/><input type="hidden" name="gradeItems['+rows+'].endTime" value="'
				 +endDataTime+'"/></dd><dt>批课地点：'+ addr
				 + '<input type="hidden" name="gradeItems['+rows+'].address" class="pkteach" value="'+ addr +'"/></dt><dd>备注：'
				 + remark+ '<input type="hidden" name="gradeItems['+rows+'].remark" class="pkteach" value="'+ remark +'"/></dd></dl></div>')
				 .find(".close").click(
							function() {
								$(this).closest(".alert").alert("close");
							});
			$(".form-addPlan :text,.form-addPlan textArea")
					.not(".datepicker,.timePicker").val("");

		}

	});

});
</script>
<script>
$(function() {
	window.prettyPrint && prettyPrint();
	$('#startDate').datepicker({
		format : 'yyyy-mm-dd'
	}).datepicker("setValue", new Date());
	$('#endDate').datepicker({
		format : 'yyyy-mm-dd'
	}).datepicker("setValue", new Date());
	$('#startTime').timepicker({
		minuteStep : 1,
		showInputs : false,
		showMeridian : false,
		disableFocus : true
	});
	$('#endTime').timepicker({
		minuteStep : 1,
		showInputs : false,
		showMeridian : false,
		disableFocus : true
	});
});
</script>
</head>
<body>
  <form class="form-horizontal" method="" id="inputForm" action="" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" name="projectId" value="${projectId}" />
    <input type="hidden" id="schId" name="schId" value="${bamProject.schId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/grade/list" class="tile-return">返回上页</a>
          <h4>批课安排</h4>
          <p>在本模块中，您可以安排新教师参加批课活动。</p>
        </div>
        <table class="table ">
          <tr>
            <td>
              <div class="control-group">
                <label for="title" class="control-label">备课活动</label>
               	<div class="controls"><span>${bamProject.template.fdName}&nbsp;/&nbsp;${bamProject.name}</span></div>
              </div>
              <div class="control-group">
                <label for="title" class="control-label">批课主题</label>
                <div class="controls">
                  <input type="text" id="title" name="title" value="${bean.title}" class="span5 required" /><span
                    id="errorInfo" class="text-error"></span>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">选择新教师</label>
                <div class="controls">
                  <j:iter items="${members}" var="member" status="vs">
                    <label class="radio inline">${member.newTeach.realName}<input type="radio" name="uid" value="${member.newteachId}"
                      <j:if test="${vs.index==0}">checked</j:if> />
                    </label>
                  </j:iter>
                </div>
              </div>
              <div class="control-group">
                <label for="title" class="control-label">学校名称</label>
                <div class="controls"><span>${deptName}</span></div>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div id="pk_list"></div>
              <div class="form-addPlan">
                <div class="control-group">
                  <label class="control-label" for="teachers">批课教师</label>
                  <div class="controls">
                    <input id="teachers" type="text" class="input-large" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="startTime">开始时间</label>
                  <div class="controls">
 <input id="startDate" type="text" class="input-small datepicker" />
                    <input id="startTime" type="text" value="" class="input-small timePicker" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="endTime">结束时间</label>
                  <div class="controls">
 <input id="endDate" type="text" class="input-small datepicker" />
                    <input id="endTime" type="text" class="input-small timePicker" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="addr">批课地点</label>
                  <div class="controls">
                    <input id="addr" type="text" class="input-large" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="remark">备注</label>
                  <div class="controls">
                    <textArea id="remark" maxlength="1000" rows="4" class="span5"></textArea>
                  </div>
                </div>
                <div class="control-group">
                  <div class="controls">
                    <button id="addUser" type="button" class="btn">
                      <i class="icon-plus-sign"></i>&nbsp;添加
                    </button>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div align="center">
                <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>