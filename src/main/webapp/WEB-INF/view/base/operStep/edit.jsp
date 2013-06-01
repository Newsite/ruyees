<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript">
	function submitOper() {
		var status = $('input:radio[name="through"]:checked').val();
		if (status == "" || status == null) {
			alert("审批状态必选！");
			return false;
		}

		document.form.method = "post";
		document.form.action = '${ctx}/coach/operation/save';
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <form class="form-horizontal" method="post" action="" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          审批作业步骤&nbsp;&nbsp; <a href="${ctx }/coach/operation/list">返回</a>
        </div>
        <table class="table ">
          <tr>
            <th width="20%">新教师</th>
            <td colspan="3">${person.realName}</td>
          </tr>
          <tr>
            <th width="20%">作业步骤</th>
            <td colspan="3">${bean.fdName}</td>
          </tr>
          <tr>
            <th>审批</th>
            <td colspan="3"><label class="radio inline"> 驳回<input type="radio" name="through"
                value="false" />
            </label> <label class="radio inline"> 通过<input type="radio" name="through" value="true" checked />
            </label></td>
          </tr>
          <tr>
            <td colspan="4"><a class="btn btn-info" value="提交" id="saveBtn" onclick="submitOper()">确定</a></td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>