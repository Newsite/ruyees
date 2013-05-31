<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="flowId" value="${bean.bamPackage.phase.stage.project.fdId}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/uploadify/uploadify.css" type="text/css"></link>
<style>
#imgshow {
  width: 120px;
  height: 80px;
}
</style>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=22"></script>
<script type="text/javascript" src="${ctx}/resources/js/lhgdialog.min.js"></script>
<script charset="utf-8" src="${ctx}/resources/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/resources/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		// 聚焦第一个输入框
		$("#fdName").focus();
		// 为inputForm注册validate函数
		$("#inputForm").validate({
			onsubmit : true,// 是否在提交是验证
			onfocusout : false,// 是否在获取焦点时验证
			onkeyup : false,// 是否在敲击键盘时验证 
			submitHandler : function(form) { // 通过之后回调
				// 提交数据
				document.form.method = "post";
				document.form.action = '${ctx}/campus/flow/edit/bamOperation/save';
				document.form.submit();
				return;
			},
			invalidHandler : function(form, validator) { // 不通过回调
				return false;
			}
		});
		return;
	});

	$(document).ready(function() {
		var flag = $("#flag").val();
		if (flag == 'has') {
			$.fn.jalert2("此信息已存在，请重新增加！");
		}
	});
</script>

</head>
<body>
  <form class="form-horizontal" method="" id="inputForm" action="" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" name="flowId" value="${flowId}" /> <input
      type="hidden" id="flag" name="flag" value="${flag}" /> <input type="hidden" name="method" value="${method}" /> <input
      type="hidden" name="stage" value="2" /> <input type="hidden" name="step" value="1" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/campus/flow/edit/bamOperation/list/${flowId}/2/1" class="tile-return">返回上页</a>
          <h4></h4>
          <p></p>
        </div>
        <table class="table">
          <tr>
            <th width="17%">教研步骤</th>
            <td>${bean.fdName}</td>
            <th>序号</th>
            <td>${bean.fdOrder}</td>
          </tr>
          <tr>
            <th>教研材料</th>
            <td colspan="3"><tags:edit editid="fdReferBook" name="fdReferBook" value="${bean.fdReferBook}"
                width="540px;" height="200px;"></tags:edit></td>
          </tr>
          <tr>
            <th>教研成果</th>
            <td colspan="3"><tags:edit editid="fdRequest" name="fdRequest" value="${bean.fdRequest}" width="540px;"
                height="200px;"></tags:edit></td>
          </tr>
          <tr>
            <td colspan="4">
              <center>
                <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
              </center>
            </td>
          </tr>
        </table>
      </div>
    </section>
  </form>
  <script src="${ctx}/resources/js/index.js" type="text/javascript"></script>
</body>
</html>