<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#inputForm").validate({
			onsubmit : true,// 是否在提交是验证
			onfocusout : false,// 是否在获取焦点时验证
			onkeyup : false,// 是否在敲击键盘时验证 
			submitHandler : function(form) { //通过之后回调
				var title = $("#title").val();
				if (title == null || title == "") {
					alert("请填写消息标题！");
					return $("#title").focus();
				}
				var body = $("#body").val();
				if (body == null || body == "") {
					alert("请填写消息内容！");
					return $("#body").focus();
				}
				document.form.method = "post";
				document.form.action = '${ctx}/notify/toSave';
				document.form.submit();
				return;
			},
			invalidHandler : function(form, validator) { //不通过回调
				return false;
			}

		});
		return;
	});
</script>
</head>
<body>
  <form class="form-horizontal" id="inputForm" method="" action="" name="form">
    <input type="hidden" name="acceptUser[0].person.fdId" value="${guidId}" />
    <div class="well">
      <div class="page-header">
        <a href="${ctx}/coach/progress/list" class="tile-return">返回上页</a>
        <h4>提醒&nbsp;TA</h4>
        <p>在本模块中，您可以给新教师发送消息，提醒TA尽快提交学术作业或者课件。</p>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>发送消息</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>标题</td>
            <td><input type="text" id="title" name="title" value="" /></td>
          </tr>
          <tr>
            <td>内容</td>
            <td><textarea id="body" name="body" rows="3" cols="120" class="span6"></textarea></td>
          </tr>
        </tbody>
      </table>
      <span> <button class="btn btn-info" type="submit" value="提交" id="saveBtn"><i class="icon-envelope icon-white"></i>&nbsp;发送</button></span>
     
    </div>
  </form>
</body>
</html>