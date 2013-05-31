<%@page import="jodd.servlet.JspResolver"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lhgdialog.min.js"></script>
<script charset="utf-8" src="${ctx }/resources/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx }/resources/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	jQuery(document)
			.ready(
					function() {
						//聚焦第一个输入框
						$("#fdName").focus();

						$("#inputForm")
								.validate(
										{
											onsubmit : true,// 是否在提交是验证
											onfocusout : false,// 是否在获取焦点时验证
											onkeyup : false,// 是否在敲击键盘时验证 
											submitHandler : function(form) { //通过之后回调
												var method = $("#method").val();
												if (method == "add") {
													var name = $("#fdName")
															.val();
													$
															.ajax({
																url : "${ctx}/ajax/foundation/checkTemplate",
																type : "POST",
																dataType : "json",
																data : {
																	name : name
																},
																success : function(
																		result) {
																	if (result == true) {
																		$(
																				"#errorInfo")
																				.text(
																						"此模板名称已存在");
																		return;
																	} else {
																		document.form.method = "post";
																		document.form.action = '${ctx}/group/template/save';
																		document.form
																				.submit();
																		return;
																	}
																}
															});
												}
												//编辑不用校验模板名唯一性
												if (method == "edit") {
													document.form.method = "post";
													document.form.action = '${ctx}/group/template/save';
													document.form.submit();
													return;
												}
											},
											invalidHandler : function(form,
													validator) { //不通过回调
												return false;
											}

										});
						return;
					});

	$(document).ready(function() {
		//输入作业分值后
		$("#fdOperationWeight").blur(function() {
			var work = $("#fdOperationWeight").val();
			if (work != null || work != "") {
				var check = 100 - parseFloat(work);
				$("#fdAppofClass").val(check);
			}
			if (work == null || work == "") {
				$("#fdAppofClass").val(0);
				return $("#fdOperationWeight").focus();
			}
		});
		//输入批课分值后
		$("#fdAppofClass").blur(function() {
			var work = $("#fdOperationWeight").val();
			var check = $("#fdAppofClass").val();
			if (work == null || work == "") {
				alert('请输入作业分值权重！');
				return $("#fdOperationWeight").focus();
			}
			if (work != "" && check != "") {
				if (parseFloat(work) + parseFloat(check) != 100) {
					alert('作业分值+批课分值=100!');
					return $("#fdOperationWeight").focus();
				}
				if (parseFloat(work) <= 0 || parseFloat(check) <= 0) {
					alert('分值权重为正数！');
					return false;
				}
			}
		});
		//删除已增加过的资源
		$(".tab-pane .table tbody tr").find("a").click(function() {
			$(this).closest("tr").remove();
		});
	});

	function open(itemindex, contentId, method) {
		var a = jQuery.dialog({
			id : 'msg',
			title : '资源选择',
			content : 'url:${ctx}/common/' + method + '?itemindex=' + itemindex
					+ "&contentId=" + contentId,
			width : 600,
			height : 400,
			left : "100%",
			fixed : true,
			drag : true,
			resize : true
		});
	}
	
	function open2(itemindex, contentId,cid ,method) {
		var a = jQuery.dialog({
			id : 'msg',
			title : '资源选择',
			content : 'url:${ctx}/common/' + method + '?itemindex=' + itemindex
					+ "&contentId=" + contentId+"&cid="+cid,
			width : 600,
			height : 400,
			left : "100%",
			fixed : true,
			drag : true,
			resize : true
		});
	}

	function add(id, name, itemindex, contentId, method) {
		var flag = true;
		var rows = $("#" + method + itemindex + contentId + " tbody").children(
				"tr").length - 1;
		$("#" + method + itemindex + contentId + " tbody tr").each(function() {
			if ($(this).attr('id') == id) {
				alert("已添加，请勿重复");
				flag = false;
			}
		});

		//判断资源内容
		var contents = "";
		if (method == "video" || method == "content") {
			contents = "viewContentMovies";
		} else if (method == "exam") {
			contents = "viewExamCategories";
		} else {
			contents = "operPackage";
		}

		if (flag) {
			//method=operation  只加一条，若加第二条就替换第一条
			if (method == "operation") {
				$("#" + method + itemindex + contentId)
						.find("tbody")
						.html(
								'<tr id="'+id+'"><td>'
										+ name
										+ '<input type="hidden" value="'+id+'" name="templateItem['+itemindex+'].templateContents['+contentId+'].operPackage.fdId"/></td><td><a href="##"><i class="icon-trash"></i></a></td></tr>')
						.find("a").click(function() {
							$(this).closest("tr").remove();
						});
			} else {
				$("#" + method + itemindex + contentId)
						.find("tbody")
						.append(
								'<tr id="'+id+'"><td>'
										+ name
										+ '<input type="hidden" value="'+id+'" name="templateItem['+itemindex+'].templateContents['+contentId+'].'+contents+'['+rows+'].fdId"/>  </td><td><a href="##"><i class="icon-trash"></i></a></td></tr>')
						.find("a").click(function() {
							$(this).closest("tr").remove();
						});
			}
		}
	}
</script>
</head>
<body>
  <j:form2 bean="bean">
    <div class="well">
      <form class="form-horizontal" method="post" id="inputForm" action="" name="form">
        <input type="hidden" name="fdId" /> <input type="hidden" id="method" name="method" value="${method}" />
        <div class="page-header">
          <a href="${ctx}/group/template/list" class="tile-return">返回上页</a>
          <h4>备课模板配置</h4>
          <p>在本模块中，您可以配置集团所有国外考试项目课程及分项的备课模板。</p>
        </div>
        <div class="control-group">
          <label class="control-label" for="fdName">备课模板</label>
          <j:ifelse test="${method eq 'add'}">
            <j:then>
              <div class="controls">
                <input type="text" id="fdName" name="fdName" class="input-xlarge required" /> <label id="errorInfo"
                  class="text-error"></label>
              </div>
            </j:then>
            <j:else>
              <div class="controls">
                <input type="text" name="fdName" class="input-xlarge required"/>
              </div>
            </j:else>
          </j:ifelse>
        </div>
        <div class="control-group">
          <label class="control-label" for="program">项目类型</label>
          <div class="controls">
            <select name="program" id="program" class="required input-xlarge">
              <option value="">选择项目</option>
              <j:iter items="${pList}" var="program" status="vstatus">
                <option value="${program.fdId}" <j:if test="${program.fdId eq bean.program.fdId}">selected</j:if>>${program.fdName}</option>
              </j:iter>
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="course">课程类型</label>
          <div class="controls">
            <select name="course" id="course" class="required input-xlarge">
              <option value="">选择课程</option>
              <j:iter items="${cList}" var="course" status="vstatus">
                <option value="${course.fdId}" <j:if test="${course.fdId eq bean.course.fdId}">selected</j:if>>${course.fdName}</option>
              </j:iter>
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="item">分项类型</label>
          <div class="controls">
            <select name="item" id="item" class="required input-xlarge">
              <option value="">选择分项</option>
              <j:iter items="${iList}" var="item" status="vstatus">
                <option value="${item.fdId}" <j:if test="${item.fdId eq bean.item.fdId}">selected</j:if>>${item.fdName}</option>
              </j:iter>
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="stage">阶段类型</label>
          <div class="controls">
            <select name="stage" id="stage" class="required input-xlarge">
              <option value="">选择阶段</option>
              <j:iter items="${sList}" var="stage" status="vstatus">
                <option value="${stage.fdId}" <j:if test="${stage.fdId eq bean.stage.fdId}">selected</j:if>>${stage.fdName}</option>
              </j:iter>
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="fdOperationWeight">学术作业成绩</label>
          <div class="controls">
            <div class="input-append">
              <input class="input-large required number" id="fdOperationWeight" type="text" name="fdOperationWeight">
              <span class="add-on">.00</span><span class="add-on">%</span>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="fdAppofClass">批课成绩</label>
          <div class="controls">
            <div class="input-append">
              <input class="input-large required number" id="fdAppofClass" type="text" name="fdAppofClass"> <span
                class="add-on">.00</span><span class="add-on">%</span>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="description">备注</label>
          <div class="controls">
            <textarea name="description" maxlength="1500" rows="6" class="span5"></textarea>
          </div>
        </div>
        <div id="mytab" class="tabbable">
          <ul class="nav nav-tabs">
            <li class="active"><a href="#tab1" data-toggle="tab">第一关</a></li>
            <li><a href="#tab2" data-toggle="tab">第二关</a></li>
            <li><a href="#tab3" data-toggle="tab">第三关</a></li>
            <li><a href="#tab4" data-toggle="tab">第四关</a></li>
            <li><a href="#tab5" data-toggle="tab">第五关</a></li>
            <li><a href="#tab6" data-toggle="tab">第六关</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane active" id="tab1">
              <%@include file="../templateStep/1_edit.jsp"%>
            </div>
            <div class="tab-pane" id="tab2">
              <%@include file="../templateStep/2_edit.jsp"%>
            </div>
            <div class="tab-pane" id="tab3">
              <%@include file="../templateStep/3_edit.jsp"%>
            </div>
            <div class="tab-pane" id="tab4">
              <%@include file="../templateStep/4_edit.jsp"%>
            </div>
            <div class="tab-pane" id="tab5">
              <%@include file="../templateStep/5_edit.jsp"%>
            </div>
            <div class="tab-pane" id="tab6">
              <%@include file="../templateStep/6_edit.jsp"%>
            </div>
          </div>
        </div>
        <div align="center">
          <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
        </div>
      </form>
    </div>
  </j:form2>
  <script src="${ctx}/resources/js/index.js" type="text/javascript"></script>
</body>
</html>