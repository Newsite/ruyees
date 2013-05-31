<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
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
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=12"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript">
jQuery(document).ready(
function() {
	$("#fdName").focus();
	$("#inputForm").validate({
		onsubmit : true, // 是否在提交是验证
		onfocusout : false, // 是否在获取焦点时验证
		onkeyup : false, // 是否在敲击键盘时验证 
		submitHandler : function(form) { //通过之后回调
			var fdId = $("#fdId").val();
			var fileType = $("#fileType")
					.val();
			var fdName = $("#fdName").val();
			if (fdName == null
					|| fdName == "") {
				alert('您必须填写资源名称，请重新输入。');
				return $("#fdName").focus();
			}
			$.ajax({
					url : "${ctx}/ajax/foundation/checkEditCM",
					type : "POST",
					dataType : "json",
					data : {
						fdId : fdId,
						fileType : fileType,
						fdName : fdName
					},
					success : function(result) {
						if (result == true) {
							$("#errorInfo").text("该名称已存在，请重新输入。");
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
		invalidHandler : function(form,
				validator) { // 不通过回调
			return false;
		}
	});
return;
});
</script>
<script type="text/javascript">
function delAtt(fdId,attId) {
	/* $.ajax({
		type : "post",
		dataType : "json",
		url : "${ctx}/ajax/cm/delAtt",
		data : {
			fdId : fdId
		},
		success : function(data) {
		}
	}); */
	document.form.method = "get";
	document.form.action = '${ctx}/group/contentMovie/delAtt/'+fdId+'/'+attId;
	document.form.submit();
	return;
}
</script>
</head>
<body>
  <div class="well">
    <form class="form-horizontal" id="inputForm" action="" name="form">
      <input type="hidden" id="fdId" name="fdId" value="${bean.fdId}" />
      <input type="hidden" id="fileType" name="fileType" value="${bean.fileType}" />
      <div class="page-header">
        <a href="${ctx}/group/contentMovie/list" class="tile-return">返回上页</a>
        <h4>文档视频</h4>
        <p>在本模块中，您可以配置平台的所有文档、视频资源。</p>
      </div>
      <div class="control-group">
        <label class="control-label">类型</label>
        <j:ifelse test="${bean.fileType==1}">
          <div class="controls">
            <j:then>
              <span>文档</span>
            </j:then>
            <j:else>
              <span>视频</span>
            </j:else>
          </div>
        </j:ifelse>
      </div>
      <div class="control-group">
        <label class="control-label">名称</label>
        <div class="controls">
          <input type="text" name="fdName" id="fdName" value="${bean.fdName}" class="inpTd input-xlarge" />
          <font color="red"><label id="errorInfo"></label></font>
        </div>
      </div>

      <div class="control-group">
        <label class="control-label">缩略图</label>
        <div class="controls">
          <img id="imgshow" src="${ctx}/${bean.backgroundUrl}" class="pull-right">
          <tags:simpleupload folder="Folder" fullname="backgroundUrl" imgshow="imgshow" fullnameid="backgroundUrl"
            filename="backgroundUrl" filevalue="${bean.backgroundUrl}" fullvalue="${bean.backgroundUrl}" id="img">
          </tags:simpleupload>
        </div>
      </div>
      <j:if test="${bean.fileType==1 }">
        <div id="form-addCont">
          <div class="control-group">
            <label class="control-label">内容</label>
            <div class="controls">
              <tags:multiupload folder="Folder" filename="fdName" filevalue="${bean.fdName}" fullvalue="" id="upContent">
              </tags:multiupload>
            </div>
            <div class="controls">
            <div id="addMulImg"></div>
            <table id="attAllList">
             <%--  <tr><td colspan="5"><a href="${ctx}/group/contentMovie/delAllAtt/${bean.fdId}">删除全部内容</a></td></tr> --%>
              <j:iter items="${bean.attMains}" var="attMain" status="s1">
                <tr>
                	<td width="5%">序号</td>
                	<td><input type="text" name="attMains[${s1.index}].fdIndex" value="${attMain.fdIndex}" class="inpTd input-xmini" /></td>
                	<td width="40%"><img id="imgshow${s1.index}" src="${ctx}/${attMain.filePath}" style="width: 112px;height: 64px"></td>
                	<j:if test="${isView!=1}">
                	<td width="40%">
	                	<tags:simpleupload folder="Folder" fullname="attMains[${s1.index}].filePath" fullnameid="fdUrl${s1.index}"
	            						   imgshow="imgshow${s1.index}" filename="attMains[${s1.index}].fileName" filevalue="${attMain.fileName}" fullvalue="${attMain.filePath}" id="upCon${s1.index}">
	          			</tags:simpleupload> 
              		</td>
                	</j:if>
                	<td><a href="#" onclick="delAtt('${bean.fdId}','${attMain.fdId}')">删除</a></td>
                	
                </tr>
	            <input type="hidden" name="attMains[${s1.index}].fdId" value="${attMain.fdId}" />
	            <input type="hidden" name="attMains[${s1.index}].fileName" value="${attMain.fileName}" />
	            <input type="hidden" name="attMains[${s1.index}].filePath" value="${attMain.filePath}" />
              </j:iter>
              </table>
            </div>
            <tr>
          </div>
        </div>
      </j:if>
      <j:if test="${bean.fileType==2}">
        <div id="form-addMovie">
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
              <input type="text" id="attMainFileUrl" name="attMains[0].fileUrl" value="${bean.attMains[0].fileUrl}" class="inpTd input-xlarge" />
              <label class="text-warning">如果您可以准确填写该视频的网络地址，则无需上传本地视频。</label>
            </div>
          </div>
        </div>
      </j:if>
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