<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css"></link>
<style type="text/css">
#imgshow {
  width: 120px;
  height: 80px;
}

.form-horizontal .control-group {
  margin-bottom: 12px
}
</style>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=1211"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#fdName").focus();
		$("#inputForm").validate({
			onsubmit : true, // 是否在提交是验证
			onfocusout : false, // 是否在获取焦点时验证
			onkeyup : false, // 是否在敲击键盘时验证 
			submitHandler : function(form) { // 通过之后回调
				var fileType = $("#fileType").val();
				var fdName = $("#fdName").val();
				$.ajax({
					url : "${ctx}/ajax/foundation/checkCM",
					type : "POST",
					dataType : "json",
					data : {
						fileType : fileType,
						fdName : fdName
					},
					success : function(result) {
						if (result == true) {
							$("#errorInfo").text("资源名称已存在，请重新输入。");
							return;
						} else {
							document.form.method = "post";
							document.form.action = '${ctx}/group/contentMovie/save';
							document.form.submit();
							return;
						}
					}
				});
			},
			invalidHandler : function(form, validator) { // 不通过回调
				return false;
			}
		});
		return;
	});

	$(document).ready(function() {
		$("#fileType").bind("change", function() {
			if ($(this).val() == '1') {
				$('#form-addCont').toggleClass("hide");
				$('#form-addMovie').toggleClass("hide");
			} else if ($(this).val() == '2') {
				$('#form-addCont').toggleClass("hide");
				$('#form-addMovie').toggleClass("hide");
			}
		});
	});
</script>
</head>
<body>
  <div class="well">
    <form class="form-horizontal" method="post" id="inputForm" action="${ctx}/group/contentMovie/save" name="form">
      <div class="page-header">
        <a href="${ctx}/group/contentMovie/list" class="tile-return">返回上页</a>
        <h4>文档视频</h4>
        <p>在本模块中，您可以配置平台的所有文档、视频资源。</p>
      </div>
      <div class="control-group">
        <label class="control-label">类型</label>
        <div class="controls">
          <select id="fileType" name="fileType" class="required">
            <option value="1" <j:if test="${bean.fileType==1}"> selected="selected"</j:if>>文档</option>
            <option value="2" <j:if test="${bean.fileType==2}"> selected="selected"</j:if>>视频</option>
          </select>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label">名称</label>
        <div class="controls">
          <input type="text" name="fdName" id="fdName" value="${bean.fdName}" class="inpTd input-xlarge required" /> <font
            color="red"><label id="errorInfo"></label></font>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label">缩略图</label>
        <div class="controls">
          <img id="imgshow" src="${ctx}/${bean.backgroundUrl}" class="pull-right">
          <tags:simpleupload folder="Folder" fullname="backgroundUrl" imgshow="imgshow" fullnameid="backgroundUrl"
            filename="backgroundUrl" filevalue="${bean.backgroundUrl}" fullvalue="${bean.backgroundUrl}" id="img"></tags:simpleupload>
        </div>
      </div>
      <div id="form-addCont">
        <div class="control-group">
          <label class="control-label">内容</label>
          <div class="controls">
            <tags:multiupload folder="Folder" filename="fdName" filevalue="${bean.fdName}" fullvalue="" id="upContent">
            </tags:multiupload>
          </div>
          <div class="controls">
	          <div id="addMulImg"></div>
          </div>
        </div>
      </div>
      <div id="form-addMovie" class="hide">
        <div class="control-group">
          <label class="control-label">内容</label>
          <div class="controls">
            <tags:simpleupload fullname="attMains[0].filePath" fullnameid="fdUrl" filename="fdName"
              filevalue="${bean.fdName}" fullvalue="${bean.attMains[0].filePath}" id="upMovie">
            </tags:simpleupload>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">视频地址</label>
          <div class="controls">
            <input type="text" name="attMains[0].fileUrl" value="${bean.attMains[0].fileUrl}" class="inpTd input-xlarge" />
            <label class="text-warning">如果您可以准确填写该视频的网络地址，则无需上传本地视频。</label>
          </div>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label">备注</label>
        <div class="controls">
          <textarea name="fdDescribe" rows="6" cols="80" style="width: 85%">${bean.fdDescribe}</textarea>
        </div>
      </div>
      <div class="control-group">
        <div class="controls">
          <button class="btn btn-info" type="submit" value="提交" id="saveBtn">确定</button>
        </div>
      </div>
    </form>
  </div>
</body>
</html>