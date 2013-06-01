<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/placeholder.js" type="text/javascript"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#inputForm").validate({
			onsubmit : true,// 是否在提交是验证
			onfocusout : false,// 是否在获取焦点时验证
			onkeyup : false,// 是否在敲击键盘时验证 
			submitHandler : function(form) { //通过之后回调
				var status = $('input:radio[name="status"]:checked').val();
				if (status == "" || status == null) {
					alert("审批状态必选！");
					return false;
				}
				
				var fdValue = $("#fdValue").val();
				var fdToValue = $("#fdToValue").val();
				if(status==4){
					if (fdToValue == null || fdToValue == "") {
						alert("请给新老师打分！");
						return $("#fdToValue").focus();
					}
					if(isNaN(fdToValue)){
						alert("打分应为数字！");
						return $("#fdToValue").focus();
					}
					if (fdToValue != null || fdToValue != "") {
						if(parseFloat(fdToValue)<0){
							alert("打分0~${bean.fdValue}！");
							return $("#fdToValue").focus();
						}
					}
					if (parseFloat(fdToValue) - parseFloat(fdValue) > 0) {
						alert("打分不能超过指标分值！");
						return $("#fdToValue").focus();
					}
				}else{
					if (fdToValue != null || fdToValue != "") {
						if(isNaN(fdToValue)){
							alert("打分应为数字！");
							return $("#fdToValue").focus();
						}
						if(parseFloat(fdToValue)<0){
							alert("打分0~${bean.fdValue}！");
							return $("#fdToValue").focus();
						}
					}
					if (parseFloat(fdToValue) - parseFloat(fdValue) > 0) {
						alert("打分不能超过指标分值！");
						return $("#fdToValue").focus();
					}
				}
				
				document.form.method = "post";
				document.form.action = '${ctx}/coach/index/save';
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
  <form class="form-horizontal" id="inputForm" method="post" action="" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" id="fdValue" name="fdValue"
      value="${bean.fdValue}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/package/edit/${bean.bamOperation.bamPackage.fdId}" class="tile-return">返回上页</a>
          <h4>学术作业审核</h4>
          <p>在本模块中，您可以审核新教师提交的学术作业。</p>
        </div>
        <table class="table">
          <thead>
            <tr><td>学术作业审核表</td><td></td></tr>
          </thead>
          <tbody>
          <tr>
            <td width="20%">新教师</td>
            <td colspan="3">${person.realName}</td>
          </tr>
          <tr>
           <td width="20%">显示头像</td>
            <td colspan="3"><tags:image href="${person.poto}" clas="img-polaroid img-face" /></td>
          </tr>
          <tr>
            <td>成果指标</td>
            <td>${bean.fdIndexName}</td>
          </tr>
          <tr>
            <td>查看内容</td>
            <td><a href="${ctx}/common/download/${bean.bamAttMain.fdId}">${bean.fdFileName}</a></td>
          </tr>
          <tr>
            <td>指标分值</td>
            <td>${bean.fdValue}&nbsp;分</td>
          </tr>
          <tr>
            <td>导师评分</td>
            <td><input type="text"
              id="fdToValue" max="${bean.fdValue}" name="fdToValue"
              value="${bean.fdToValue}" class="span1 {max:5}"
              placeholder="0 ~ ${bean.fdValue}" />&nbsp;分</td>
          </tr>
          <tr>
            <td>审核结果</td>
            <td colspan="3">
              <label class="radio inline">通过<input type="radio" name="status" value="4" checked /></label>
              <label class="radio inline">驳回<input type="radio" name="status" value="3" /></label>
            </td>
          </tr>
          <tr>
            <td>导师评语</td>
            <td colspan="3"><textarea name="remark" rows="6" cols="120" maxlength="1000" class="span6">${bean.remark}</textarea></td>
          </tr>
          <tr>
            <td colspan="4"><button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button></td>
            <td></td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>
  </form>
</body>
</html>