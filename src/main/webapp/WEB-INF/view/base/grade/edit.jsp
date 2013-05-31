<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
	jQuery(document)
			.ready(
					function() {
						$("#title").focus();

						$("#inputForm")
								.validate(
										{
											onsubmit : true,// 是否在提交是验证
											onfocusout : false,// 是否在获取焦点时验证
											onkeyup : false,// 是否在敲击键盘时验证 
											submitHandler : function(form) { //通过之后回调
												//至少一位批课老师
												var advier_value = [];
												$(
														'input[name^="gradeItems["][name$=".advierId"]')
														.each(
																function() {
																	var advierId = $(
																			this)
																			.val();
																	if (advierId != '') {
																		advier_value
																				.push(advierId);
																	}
																});
												if (advier_value.length == 0) {
													alert('请添加加批课老师|开始时间|结束时间！');
													return false;
												}

												document.form.method = "post";
												document.form.action = '${ctx}/coach/grade/update';
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
</head>
<body>
  <form class="form-horizontal" method="" id="inputForm" action="" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" name="projectId" value="${projectId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/grade/list" class="tile-return">返回上页</a>
          <h4>批课安排</h4>
          <p>在本模块中，您可以安排新教师参加批课活动。</p>
        </div>
        <table class="table ">
          <tr>
            <th width="20%">主题</th>
            <td colspan="3"><input type="text" name="title" value="${bean.title}" class="inpTd input-xlarge"
              readonly /></td>
          </tr>
          <tr>
            <th>新教师</th>
            <td colspan="3">${bean.newTeach.realName}</td>
          </tr>
          <tr>
          	<th>显示头像</th>
            <td colspan="3"><tags:image href="${bean.newTeach.poto}" clas="img-polaroid img-face" /></td>
          </tr>
          <tr>
            <th>批课总分</th>
            <td colspan="3">${bean.totalValue} &nbsp;分</td>
          </tr>
          <tr>
            <td colspan="4">
              <table class="table" id="u_list">
                <thead>
                  <tr>
                    <th>批课老师</th>
                    <th>批课结束</th>
                    <th>批课打分</th>
                    <th>分项打分</th>
                  </tr>
                </thead>
                <tbody>
                  <j:iter items="${bean.gradeItems}" var="item" status="vs1">
                    <tr>
                      <td><input type="hidden" name="gradeItems[${vs1.index}].fdId" value="${item.fdId}" />
                        ${item.advier.realName}<input type="hidden" name="gradeItems[${vs1.index}].advierId"
                        value="${item.advierId}" /></td>
                      <td><input type="hidden" name="gradeItems[${vs1.index}].endTime" value="${item.endTime}" />
                        ${fn:substring(item.endTime, 0, 16)}</td>
                      <td><input type="text" name="gradeItems[${vs1.index}].value" value="${item.value}"
                        class="required number" readonly /></td>
                      <td><a href="${ctx}/coach/grade/item/${bean.fdId}/${item.fdId}">分项打分</a></td>
                    </tr>
                    </tr>
                  </j:iter>
                </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <th>导师评语</th>
            <td colspan="3"><input type="text" name="remark" value="${bean.remark}" class="span7 required" /></td>
          </tr>
          </tr>
        </table>
        <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
      </div>
    </section>
  </form>
</body>
</html>