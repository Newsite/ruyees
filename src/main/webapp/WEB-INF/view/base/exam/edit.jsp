<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#inputForm").validate({
			onsubmit : true,// 是否在提交是验证
			onfocusout : false,// 是否在获取焦点时验证
			onkeyup : false,// 是否在敲击键盘时验证 
			submitHandler : function(form) { //通过之后回调
				//答题选项必有
				var fdOption_value = [];
				$('input[name="fdOption"]').each(function() {
					var fdOption = $(this).val();
					if (fdOption != '') {
						fdOption_value.push(fdOption);
					}
				});
				if (fdOption_value.length == 0) {
					alert('答案选项不能为空！');
					return false;
				}
				//答题必有
				var fdAnswer_value = [];
				$('input[name="fdAnswer"]:checked').each(function() {
					fdAnswer_value.push($(this).val());
				});
				if (fdAnswer_value.length == 0) {
					alert('答案不能为空！');
					return false;
				}
				//提交数据
				document.form.method = "post";
				document.form.action = '${ctx}/group/exam/save';
				document.form.submit();
				return;
			},
			invalidHandler : function(form, validator) { //不通过回调
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
    <input type="hidden" name="fdId" value="${bean.fdId}" />
    <input type="hidden" id="flag" name="flag" value="${flag}" />
    <input type="hidden" name="fdCategoryId" value="${fdCategoryId}" />
    <input type="hidden" name="method" value="${method}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/group/exam/list?fdCategoryId=${fdCategoryId}" class="tile-return">返回上页</a>
          <h4>试卷试题</h4>
          <p>在本模块中，您可以配置平台的所有试卷与试题信息。</p>
        </div>
        <table class="table">
          <tr>
            <th width="15%">试题</th>
            <td><input type="text" id="fdSubject" name="fdSubject" value="${bean.fdSubject}" class="inpTd input-xlarge required" /></td>
          </tr>
          <tr>
            <th>排序</th>
            <td><input type="text" name="fdOrder" value="${bean.fdOrder}" class="inpTd input-xmini required" /></td>
          </tr>
          <tr>
            <th>题型</th>
            <td>
              <label class="radio inline">单选题<input type="radio" checked name="fdType" value="0"
                <j:if test="${bean.fdType==0}">checked</j:if> />
              </label>
              <label class="radio inline">多选题<input type="radio" name="fdType" value="1"
                <j:if test="${bean.fdType==1}">checked</j:if> />
              </label>
            </td>
          </tr>
          <tr>
            <th>答案选项</th>
            <td class="tableTd">
              <table class="table setTest">
                <thead>
                  <tr>
                    <th width="10%">#<a href="#" class="addRow"><i class="icon-plus"></i></a></th>
                    <th>答案选项</th>
                    <th width="15%">是否答案</th>
                  </tr>
                </thead>
                <tbody>
                  <c:set var="fdAnswers" value="${bean.fdAnswer}" />
                  <j:ifelse test="${bean.fdType eq 0}">
                    <j:then>
                      <j:set name="fdType" value="radio" />
                    </j:then>
                    <j:else>
                      <j:set name="fdType" value="checkbox" />
                    </j:else>
                  </j:ifelse>
                  <c:forTokens items="${bean.fdOption}" delims="," var="item1" varStatus="vs1">
                    <tr>
                      <c:set var="index1" value="${vs1.index + 1}" />
                      <td><span class="num">${index1}</span>&nbsp;<a href="#" class="delRow"><i class="icon-remove"></i></a></td>
                      <td><input type="text" name="fdOption" value="${item1}" style="width: 350px" class="required" /></td>
                      <td><input class="ck required" type="${fdType}" name="fdAnswer" value="${index1}"
                        <c:if test="${fn:contains(fdAnswers, index1)}">checked</c:if> /></td>
                    </tr>
                  </c:forTokens>
                </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <th>备注</th>
            <td><textarea name="fdDescription" id="fdDescription" rows="6" cols="80" style="width: 85%">${bean.fdDescription}</textarea></td>
          </tr>
          <tr>
          </tr>
        </table>
        <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
      </div>
    </section>
  </form>
  <script src="${ctx}/resources/js/addExam.js" type="text/javascript"></script>
</body>
</html>