<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#record").focus();

		$("#inputForm").validate({
			onsubmit : true,// 是否在提交是验证
			onfocusout : false,// 是否在获取焦点时验证
			onkeyup : false,// 是否在敲击键盘时验证 
			submitHandler : function(form) { //通过之后回调
				//校验每个分项
				var count = $("#count").val();
				var values = [];
				$('input[name^="value"]').each(function() {
					var valuei = $(this).val();
					var value = parseFloat(valuei);
					if (value<1 || value>5) {
						$.fn.jalert2("请查看打分内容提示！");
						return false;
					}
					/* if (value % 0.5 != 0) {
						$.fn.jalert2("请查看打分提示内容,打分以 0.5 为间隔！");
						return false;
					} */

					values.push(valuei);
				});

				if (values.length != count) {
					$.fn.jalert2("请再次查看打分内容提示！");
					return false;
				}

				document.form.method = "post";
				document.form.action = '${ctx}/coach/grade/upItem';
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
  <form class="form-horizontal" method="" id="inputForm" action="" name="form">
    <input type="hidden" name="fdId" value="${item.fdId}" /> 
    <input type="hidden" id="count" value="${count}" /> 
    <input type="hidden" name="gradeId" value="${grade.fdId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/grade/edit/${grade.fdId}" class="tile-return">返回上页</a>
          <h4>批课安排</h4>
          <p>在本模块中，您可以安排新教师参加批课活动。</p>
          <p>打分提示：5分制，精确到小数点后1位,打分值大于等于1.0,小于等于5.0</p>
        </div>
        <!-- <span class="text-error">打分提示：5分制，精确到小数点后1位,打分值大于等于1.0,小于等于5.0,以 0.5 分为间隔 </span> -->
        <table class="table table-bordered">
          <caption>
            <b>[${grade.title}]新教师综合评测表<br>&nbsp;
            </b>
          </caption>
          <tr>
            <td width="10%">新教师姓名</td>
            <td width="10%">${item.newTeach.realName}</td>
            <td width="10%">所在机构</td>
            <td width="10%">${sys.fdName}</td>
            <td width="10%">所在项目</td>
            <td width="10%"></td>
            <td width="10%">主授课程</td>
            <td width="10%"></td>
            <td width="10%">承担分项</td>
            <td width="10%"></td>
          </tr>
          <tr>
            <td width="10%">培训师姓名</td>
            <td colspan="9">${guid.realName}</td>
          </tr>
          <tr>
            <th width="100%" colspan="10"><center>说课记录</center></th>
          </tr>
          <tr>
            <td width="10%">日期：</td>
            <td colspan="9">${fn:substring(item.startTime, 0, 16)}~${fn:substring(item.endTime, 0, 16)}</td>
          </tr>
          <tr>
            <td width="100%" colspan="10">
            <textarea maxlength="1000"  id="record" name="record" rows="6" cols="200"
                class="input-block-level required">${item.record}</textarea></td>
          </tr>
          <tr>
            <th width="100%" colspan="10"><center>综合评价</center></th>
          </tr>
          <tr>
            <td width="10%" style="vertical-align: middle;">课程优点</td>
            <td colspan="9"><textarea name="merit" maxlength="1000" rows="3" cols="200" class="input-block-level required">${item.merit}</textarea>
            </td>
          </tr>
          <tr>
            <td style="vertical-align: middle;">分项分析<br>（5分制，精确到小数点后1位）
            </td>
            <td colspan="9">
              <table class="table table-bordered">
                <j:iter items="${news}" var="newItem" status="vs">

                  <j:if test="${vs.index==0}">
                    <j:set name="valuei" value="${item.value1}" />
                    <j:set name="remarki" value="${item.remark1}" />
                  </j:if>
                  <j:if test="${vs.index==1}">
                    <j:set name="valuei" value="${item.value2}" />
                    <j:set name="remarki" value="${item.remark2}" />
                  </j:if>
                  <j:if test="${vs.index==2}">
                    <j:set name="valuei" value="${item.value3}" />
                    <j:set name="remarki" value="${item.remark3}" />
                  </j:if>
                  <j:if test="${vs.index==3}">
                    <j:set name="valuei" value="${item.value4}" />
                    <j:set name="remarki" value="${item.remark4}" />
                  </j:if>
                  <j:if test="${vs.index==4}">
                    <j:set name="valuei" value="${item.value5}" />
                    <j:set name="remarki" value="${item.remark5}" />
                  </j:if>
                  <j:if test="${vs.index==5}">
                    <j:set name="valuei" value="${item.value6}" />
                    <j:set name="remarki" value="${item.remark6}" />
                  </j:if>
                  <j:if test="${vs.index==6}">
                    <j:set name="valuei" value="${item.value7}" />
                    <j:set name="remarki" value="${item.remark7}" />
                  </j:if>
                  <j:if test="${vs.index==7}">
                    <j:set name="valuei" value="${item.value8}" />
                    <j:set name="remarki" value="${item.remark8}" />
                  </j:if>
                  <tr>
                    <td>${newItem.fdName}</td>
                    <td>打分</td>
                    <td><input type="text" name="value${vs.index+1}" value="${valuei}"
                      class="inpTd input-small required number {max:5}" /></td>
                    <td>评语</td>
                    <td clospan="5"><input type="text" maxlength="1000" name="remark${vs.index+1}" value="${remarki}"
                      class="span4 required" /></td>
                  </tr>
                </j:iter>
              </table>
            </td>
          </tr>
          <tr>
            <td width="10%" style="vertical-align: middle;">主要问题</td>
            <td colspan="9"><textarea name="problem" rows="3" cols="200" maxlength="1000" class="input-block-level required">${item.problem}</textarea>
            </td>
          </tr>
          <tr>
            <td width="10%" style="vertical-align: middle;">修改建议</td>
            <td colspan="9"><textarea name="advise" rows="3" cols="200" maxlength="1000" class="input-block-level required">${item.advise}</textarea>
            </td>
          </tr>
          <tr>
            <td width="10%" style="vertical-align: middle;">综合评价</td>
            <td colspan="9"><textarea name="overall" rows="3"  cols="200" maxlength="1000" class="input-block-level required">${item.overall}</textarea>
            </td>
          </tr>

          <tr>
            <td colspan="10">填表说明：<br>1、请参加评课的培训师详细填写此表所有内容；<br>2、在修改建议栏请针对说课新教师给出实用的可操作的修改建议；<br>3、请参加评课的培训师完整填写完该表后每天及时交给会务组。
            </td>
          </tr>
          <tr>
            <td colspan="10">
              <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
            </td>
          </tr>
        </table>
      </div>
    </section>
  </form>
</body>
</html>