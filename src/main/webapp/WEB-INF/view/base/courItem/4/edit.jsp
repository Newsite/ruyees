<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript">
	function submitCourItem() {
		var status = $('input:radio[name="status"]:checked').val();
		if (status == "" || status == null) {
			alert("请表明您的态度～");
			return false;
		}
		document.form.method = "post";
		document.form.action = '${ctx}/coach/courItem/save/4';
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
          <a href="${ctx}/coach/courseware/edit/4/${bean.bamCourseware.fdId}" class="tile-return">返回上页</a>
          <h4>课件审批</h4>
          <p>在本模块中，您可以审批新教师提交的课件。</p>
        </div>
        <table class="table ">
          <tr>
            <th width="20%">新教师</th>
            <td colspan="3">${person.realName}</td>
          </tr>
          <tr>
            <th width="20%">查看课件</th>
            <td colspan="3"><a href="${ctx}/common/courseware/${bean.fdId}">${bean.name}</a></td>
          </tr>
          <tr>
            <th>审批</th>
            <td>
            	<j:ifelse test="${bean.through }">
            		<j:then>
            		<label class="radio inline">通过<input type="radio" name="status" value="4" checked /></label>
            		</j:then>
            		<j:else>
            		<label class="radio inline">通过<input type="radio" name="status" value="4" checked /></label>
            	   <label class="radio inline">驳回<input type="radio" name="status" value="3" /></label>
            		</j:else>
            	</j:ifelse>
            </td>
          </tr>
          <tr>
            <th>评语</th>
            <td colspan="3"><textarea name="remark" rows="6" cols="120" maxlength="1000" class="span6">${bean.remark}</textarea></td>
          </tr>
          <tr>
            <td colspan="4"><a class="btn btn-info" value="提交" id="saveBtn" onclick="submitCourItem()">确定</a></td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>