<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	jQuery(document)
			.ready(
					function() {
						$("#inputForm")
								.validate(
										{
											onsubmit : true,// 是否在提交是验证
											onfocusout : false,// 是否在获取焦点时验证
											onkeyup : false,// 是否在敲击键盘时验证 
											submitHandler : function(form) { //通过之后回调
												//至少一位批课老师
												var advier_value = [];
												$('input[name^="gradeItems["][name$=".advierId"]')
														.each(function() {
																	var advierId = $(this).val();
																	if (advierId != '') {
																		advier_value.push(advierId);
														}
												});

												if (advier_value.length == 0) {
													alert('请添加批课老师|开始时间|结束时间！');
													//$.fn.jalert2('请添加批课老师|开始时间|结束时间！');
													return false;
												}

												document.form.method = "post";
												document.form.action = '${ctx}/coach/grade/save';
												document.form.submit();
												return;
											},
											invalidHandler : function(form,
													validator) { //不通过回调
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
	$(document)
			.ready(
					function() {
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

						$("#addUser").click(
										function() {
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
											$(".form-addPlan :text")
													.each(function() {
														if ($(this).val() == "") {
															$(this).after('&nbsp;&nbsp;<span class="text-error">不能为空！</span>');
															$(this).bind("focus",function() {
																$(this).nextAll().remove();
															})
															empty = true;
															return false;
														} else if ($(this).attr("id") == "teachers"&& $(this).val() != name) {
															$(this).after('&nbsp;&nbsp;<span class="text-error">请选择老师！</span>');
															teach = true;
															return false;
														}
													});
											if (repeat) {
												$.fn.jalert2("批课老师不能在同一时间重复添加！");
											} else if (!empty && !teach) {
												var rows = $("#pk_list").children(".alert").length;
												var startDate = $("#startDate").val();
												var endDate = $("#endDate").val();
												var startTime = $("#startTime").val();
												var endTime = $("#endTime").val();
												var startDateTime = startDate+ " " + startTime+ ":00";
												var endDataTime = endDate + " "+ endTime + ":00";
												//时间比较 2012-12-19 10:02:00
												if(!isTime(startTime+":00")){
													$.fn.jalert2("开始时间格式不正确！");
													return false;
												}
												if(!isTime(endTime+":00")){
													$.fn.jalert2("结束时间格式不正确！");
													return false;
												}
												var date1 = new Date(Date.parse(startDateTime.replace("-","/")));
												var date2 = new Date(Date.parse(endDataTime.replace("-","/")));
												if (date1 > date2) {
													$.fn.jalert2("开始时间不能大于结束时间！");
													return false;
												}
												var addr = $("#addr").val();
												var remark = $("#remark").val();
												$("#pk_list")
														.append(
																'<div class="alert alert-success" ><button type="button" class="close" data-dismiss="alert">×</button><dl class="dl-horizontal"><dt>批课教师：'
																		+ name
																		+ '<input type="hidden" name="gradeItems['+rows+'].advierId" class="pkteach" value="'
																		+ id +'"/></dt><dd>批课时间： '
																		+ startDate
																		+ " "
																		+ startTime
																		+ ' ~ '
																		+ endDate
																		+ " "
																		+ endTime
																		+ '<input type="hidden" name="gradeItems['+rows+'].startTime" class="pkTime" value="'
				+startDateTime+'"/><input type="hidden" name="gradeItems['+rows+'].endTime" value="'
				+endDataTime+'"/></dd><dt>批课地点：'
																		+ addr
																		+ '<input type="hidden" name="gradeItems['+rows+'].address" class="pkteach" value="'
				+ addr +'"/></dt><dd>备注：'
																		+ remark
																		+ '<input type="hidden" name="gradeItems['+rows+'].remark" class="pkteach" value="'
				+ remark +'"/></dd></dl></div>')
														.find(".close")
														.click(
																function() {
																	$(this)
																			.closest(
																					".alert")
																			.alert(
																					"close");
																});
												$(
														".form-addPlan :text,.form-addPlan textArea")
														.not(
																".datepicker,.timePicker")
														.val("");

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
    <input type="hidden" name="fdId" value="${bean.fdId}" /> 
    <input type="hidden" name="projectId" value="${bamProject.fdId}" /> 
    <input type="hidden" name="uid" value="${bean.uid}" /> 
    <input type="hidden" id="schId" name="schId" value="${bamProject.schId}" /> 
    <input type="hidden" name="title" value="${bean.title}" />
    
     <j:iter items="${bean.gradeItems}" var="it" status="v1">
     <input type="hidden" name="gradeItems[${v1.index}].advise" value="${it.advise}" />
     <input type="hidden" name="gradeItems[${v1.index}].merit" value="${it.merit}" />
     <input type="hidden" name="gradeItems[${v1.index}].overall" value="${it.overall}" />
     <input type="hidden" name="gradeItems[${v1.index}].problem" value="${it.problem}" />
     <input type="hidden" name="gradeItems[${v1.index}].record" value="${it.record}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark1" value="${it.remark1}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark2" value="${it.remark2}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark3" value="${it.remark3}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark4" value="${it.remark4}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark5" value="${it.remark5}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark6" value="${it.remark6}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark7" value="${it.remark7}" />
     <input type="hidden" name="gradeItems[${v1.index}].remark8" value="${it.remark8}" />
     <input type="hidden" name="gradeItems[${v1.index}].value" value="${it.value}" />
     <input type="hidden" name="gradeItems[${v1.index}].value1" value="${it.value1}" />
     <input type="hidden" name="gradeItems[${v1.index}].value2" value="${it.value2}" />
     <input type="hidden" name="gradeItems[${v1.index}].value3" value="${it.value3}" />
     <input type="hidden" name="gradeItems[${v1.index}].value4" value="${it.value4}" />
     <input type="hidden" name="gradeItems[${v1.index}].value5" value="${it.value5}" />
     <input type="hidden" name="gradeItems[${v1.index}].value6" value="${it.value6}" />
     <input type="hidden" name="gradeItems[${v1.index}].value7" value="${it.value7}" />
     <input type="hidden" name="gradeItems[${v1.index}].value8" value="${it.value8}" />
     </j:iter>
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/grade/list" class="tile-return">返回上页</a>
          <h4>批课安排</h4>
          <p>在本模块中，您可以安排新教师参加批课活动。</p>
        </div>
        <table class="table">
          <tr>
            <td>
              <div class="control-group">
                <label for="title" class="control-label">备课模板</label>
                <div class="controls">
                  <span>${bamProject.template.fdName}&nbsp;/&nbsp;${bamProject.name}</span>
                </div>
              </div>
              <div class="control-group">
                <label for="title" class="control-label">批课主题</label>
                <div class="controls">
                  <span>${bean.title}</span>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">新教师</label>
                <div class="controls">
                  <span>${bean.newTeach.realName}</span>
                </div>
              </div>
              <div class="control-group">
                <label for="title" class="control-label">学校名称</label>
                <div class="controls">
                  <span>${deptName}</span>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div id="pk_list">
                <j:iter items="${bean.gradeItems}" var="item" status="vs1">
                  <div class="alert alert-success">
                   <!--  <button type="button" class="close" id="deleteMsg" data-dismiss="alert">×</button> -->
                    <dl class="dl-horizontal">
                      <dt>
                        批课教师&nbsp;${item.advier.realName } <input type="hidden" name="gradeItems[${vs1.index}].fdId"
                          value="${item.fdId}" /> <input type="hidden" name="gradeItems[${vs1.index}].advierId"
                          value="${item.advierId}" />
                      </dt>
                      <dd>
                        批课时间&nbsp;${fn:substring(item.startTime, 0, 16)} ~ ${fn:substring(item.endTime, 0, 16)} <input
                          type="hidden" name="gradeItems[${vs1.index}].startTime" value="${item.startTime}" /> <input
                          type="hidden" name="gradeItems[${vs1.index}].endTime" value="${item.endTime}">
                      </dd>
                      <dt>
                        批课地点&nbsp;${item.address}<input type="hidden" name="gradeItems[${vs1.index}].address"
                          value="${item.address}">
                      </dt>
                      <dd>
                        备注&nbsp;${item.remark}<input type="hidden" name="gradeItems[${vs1.index}].remark"
                          value="${item.remark}">
                      </dd>
                    </dl>
                  </div>
                </j:iter>
              </div>
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
                    <textArea id="remark" rows="4" class="span5"></textArea>
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