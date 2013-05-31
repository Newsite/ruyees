<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx }/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/resources/js/lhgdialog.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=22"></script>
<style type="text/css">
body {
  text-align: center;
  font-size: 12px;
}

a:hover {
  text-decoration: none;
}

.uploadify {
  margin: 40px auto 12px;
}

input {
  font-size: 12px;
  margin: 0;
  padding: 2px;
}
</style>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;

	api.button({
		id : 'valueOk',
		name : '确定',
		callback : ok
	});

	function ok() {
	};

	var count = 0;
	$(function() {
		$("#file_upload")
				.uploadify(
						{
							'height' : 26,
							'width' : 60,
							'buttonText' : '浏 览',
							'swf' : '${ctx}/resources/uploadify/uploadify.swf',
							'uploader' : '${ctx}/common/o_upload',
							'auto' : false,
							'fileTypeExts' : '*',
							'onUploadStart' : function(file) {
								$("#file_upload").uploadify("settings",
										"formData", {
											'folder' : 'Folder'
										});
								//$("#file_upload").uploadify("settings");
							},
							'onUploadSuccess' : function(file, data, Response) {
								if (Response) {
									var objvalue = eval("(" + data + ")");
									if (W.document
											.getElementById('${fileName}')) {
										W.document
												.getElementById('${fileName}').value = file.name;
									}
									//W.document.getElementById('${fullName}').value = objvalue;
									W.document.getElementById('${fullName}').value = objvalue.filePath;
									count++;
								}
							}
						});
	});
</script>
</head>
<body>
  <input type="file" name="file_upload" id="file_upload" />
  <a href="javascript:$('#file_upload').uploadify('upload','*')">开始上传</a>&nbsp;
  <a href="javascript:$('#file_upload').uploadify('cancel', '*')">取消所有上传</a>
</body>
</html>