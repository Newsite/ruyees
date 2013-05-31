<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="fdPackageId" value="${fdPackageId}" />
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
				// 作业图片背景必有
				var backgroundUrl = $("#backgroundUrl").val();
				if (backgroundUrl == '') {
					alert('作业背景图片不能为空！');
					return false;
				}
				// 至少有一个指标
				var index_value = [];
				$('input[name^="indexs"][name$=".fdIndexName"]').each(function() {
					var fdIndex = $(this).val();
					if (fdIndex != '') {
						index_value.push(fdIndex);
					}
				});
				if (index_value.length == 0) {
					alert('作业指标不能为空！');
					return false;
				}
				// 提交数据
				document.form.method = "post";
				document.form.action = '${ctx}/group/operation/save';
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
<script type="text/javascript">
	var demoDG1;
	function openss(fname, fullname) {
		demoDG1 = $.dialog({
			id : 'demo_ZB',
			content : 'url:${ctx}/common/o_open?fileName=' + fname
					+ '&fullName=' + fullname + '',
			width : 400,
			height : 200
		});
		return false;
	}
</script>
</head>
<body>
  <div class="well">
    <form class="form-horizontal" method="" id="inputForm" action="" name="form">
      <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" name="fdPackageId"
        value="${fdPackageId}" /> <input type="hidden" id="flag" name="flag" value="${flag}" /> <input type="hidden"
        name="method" value="${method}" />
      <div class="page-header">
        <a href="${ctx}/group/operation/list?fdPackageId=${fdPackageId}" class="tile-return">返回上页</a>
        <h4>学术作业包</h4>
        <p>在本模块中，您可以配置平台所有课程及分项的学术作业包信息。</p>
      </div>
      <table class="table">
        <tr>
          <td width="17%">教研步骤</td>
          <td colspan="3"><input type="text" id="fdName" name="fdName" value="${bean.fdName}"
            class="span4 required" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span>序号</span>&nbsp;&nbsp; <input type="text"
            name="fdOrder" value="${bean.fdOrder}" class="span1 required number" maxlength="3" /></td>
        </tr>
        <tr>
          <td>作业图片</td>
          <td colspan="3"><img id="imgshow" src="${ctx}/${bean.backgroundUrl}" class="pull-right"> <tags:simpleupload
              folder="Folder" fullname="backgroundUrl" imgshow="imgshow" fullnameid="backgroundUrl"
              filename="backgroundUrl" filevalue="${bean.backgroundUrl}" fullvalue="${bean.backgroundUrl}" id="img"></tags:simpleupload>
          </td>
        </tr>
        <tr>
          <td>教研材料</td>
          <td colspan="3"><tags:edit editid="fdReferBook" name="fdReferBook" value="${bean.fdReferBook}"
              height="200px;"></tags:edit></td>
        </tr>
        <tr>
          <td>教研成果</td>
          <td colspan="3">
            <tags:edit editid="fdRequest" name="fdRequest" value="${bean.fdRequest}" height="200px;"></tags:edit>
          </td>
        </tr>
        <tr>
          <td>成果要求</td>
          <td class="tableTd" colspan="3">
            <table class="table setTest">
              <thead>
                <tr>
                  <th width="6%">#<a href="##" class="addRow"><i class="icon-plus-sign"></i></a></th>
                  <th width="10%">顺序</th>
                  <th>成果指标</th>
                  <th  width="10%">分数</th>
                  <th>对应模板</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${bean.indexs}" var="index" varStatus="vs1">
                  <tr>
                    <input type="hidden" name="indexs[${vs1.index}].fdId" value="${index.fdId}" />
                    <input type="hidden" name="indexs[${vs1.index}].attMain.fdId" value="${index.attMain.fdId}" />
                    <input type="hidden" id="fileName${vs1.index}" name="indexs[${vs1.index}].fdFileName"
                      value="${index.fdFileName}">
                    <th><span class="num">${vs1.index+1}</span><a href="##" class="delRow"><i class="icon-remove-sign"></i></a></th>
                    <td><input type="text" name="indexs[${vs1.index}].fdOrder" value="${index.fdOrder}" class="input-xmini"/></td>
                    <td><input type="text" name="indexs[${vs1.index}].fdIndexName" value="${index.fdIndexName}" class="required" /></td>
                    <td><input type="text" name="indexs[${vs1.index}].fdValue" value="${index.fdValue}" class="span1 required number" /></td>
                    <td>
                    <tags:simpleupload fullnameid="fdUrl${vs1.index}"
                        fullname="indexs[${vs1.index}].attMain.filePath" filename="fileName${vs1.index}" filevalue=""
                        fullvalue="${index.attMain.filePath}" id="uploadify${vs1.index+1}" folder="Folder"></tags:simpleupload>
                     </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3"><textarea name="fdDescription" id="fdDescription" rows="6" cols="80" class="span7">${bean.fdDescription}</textarea></td>
        </tr>
        <tr>
          <td colspan="4">
            <center>
              <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
            </center>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <script src="${ctx}/resources/js/index.js" type="text/javascript"></script>
</body>
</html>