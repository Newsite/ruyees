<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/uploadify/uploadify.css"></link>
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/otp_iframe.css">
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/iframe_inner.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=1111"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".btn-submit").click(function() {
			var itemId = $(this).closest("li").find(".coursewareItemId").val();
			var status = $(this).parent().prev(".kj-bg").children('.status');
			var $this = $(this);
			var fileNameId = '#fileName' + itemId;
			var fullNameId = '#fdUrl' + itemId;
			var fileName = $(fileNameId).val();
			var fullName = $(fullNameId).val();
			$.ajax({
				type : "post",
				dataType : "json",
				url : "${ctx}/ajax/courseware/setData",
				data : {
					"itemId" : itemId,
					"fileName" : fileName,
					"fullName" : fullName
				},
				success : function(msg) {
					if (msg != "-1") {
						// window.parent.ifraAlert2("提交成功!");
						status.html('<span class="text-info">已提交</span>');
						$this.attr("disabled", "disabled");
						$this.prevAll(".uploadify").children("object").detach();
						$this.prevAll(".uploadify").children(".btn").attr("disabled", "disabled");
					} else {
						window.parent.ifraAlert2("提交失败!");
					}
				}
			});
		});
	});
</script>
<j:ifelse test="${empty index}">
  <j:then>
    <j:set name="index" value="0" />
  </j:then>
  <j:else>
    <j:set name="index" value="${index}" />
  </j:else>
</j:ifelse>
</head>
<j:set name="allUploaded" value="true" />
<j:iter items="${content.coursewares}" var="courseware" status="s">
  <j:iter items="${courseware.items}" var="coursewareItem" status="s">
    <j:if test="${coursewareItem.status == '0' || coursewareItem.status == '1'}">
      <j:set name="allUploaded" value="false" />
    </j:if>
  </j:iter>
</j:iter>
<body oncontextmenu="return false" onselectstart="return false" ondragstart="return false" onbeforecopy="return false" onmouseup="document.selection.empty()" oncopy="document.selection.empty()" onselect="document.selection.empty()">
  <div class="iframe-container">
    <div class="stepsTab-cont-top">
      <p class="location">
        <a href="${ctx}/trainee/step/${name}/4/1"><b class="sjl"></b><b class="sjl2"></b>4.1 第四关：提交课件<b class="sjr"></b><b
          class="sjr2"></b></a> <a class="active" href="${ctx}/trainee/step/${name}/4/2"><b class="sjl"></b><b
          class="sjl2"></b>4.2 审批课件<b class="sjr"></b><b class="sjr2"></b></a>
      </p>
      <p>
        <label class="label">闯关说明</label>&nbsp;${content.content.item.fdRemarke}
      </p>
    </div>
    <div class="media toMe">
      <a class="pull-left" href="#"> <tags:image href="${member.guid.poto}" clas="media-object img-polaroid" />
      </a>
      <div class="media-body">
        <div class="icon-quote pull-left"></div>
        <p>
          ${content.content.fdRemarke}<i class="icon-quote"></i>
        </p>
      </div>
    </div>
    <j:if test="${allUploaded eq true && throughStatus[4][1] != 1}">
      <div class="success_box well">
        <div>你已经完成了全部课件的提交任务。<br />请耐心等待指导教师&nbsp;<span class="text-info">${member.guid.realName}</span>&nbsp;的审批。如果久未批复，请及时提醒TA。<a
          href="#" onclick="top.location='${ctx}/notify/toAdd/${member.guid.fdId}'" rel="tooltip" title="站内消息" class="icon-online"></a><a
          href="mailto:${member.guid.fdEmail}?subject=请多关照" rel="tooltip" title="发送邮件" class="icon-mail"></a>
        </div>
      </div>
    </j:if>
    <j:if test="${throughStatus[4][1] == 1}">
      <div class="success_box well">
        <div>指导教师&nbsp;<span class="text-info">${member.guid.realName}</span>&nbsp;说，“${content.coursewares[0].fdComment}”<br />恭喜你完成&nbsp;<span
          class="text-info">课件编写</span>&nbsp;关卡！请进入下一关&nbsp;<span class="text-info"><a href="#" rel="tooltip" onclick="goTop(function(){window.parent.activeStep(5);});">批课</a></span>&nbsp;<a
          href="#" rel="tooltip" title="点击进入" class="icon-nextStep" onclick="goTop(function(){window.parent.activeStep(5);});"></a>
        </div>
      </div>
    </j:if>
    <div class="content-box clearfix">
      <div class="fs14">
        <strong>课件管理</strong>
      </div>
      <ul class="thumbnails kj-ul clearfix">
        <j:iter items="${content.coursewares[0].items}" var="coursewareItem" status="s">
          <li class="kj-li">
            <div class="thumbnail">
              <div class="kj-bg">
                <i class="icon-ppt"></i>
                <h6>${coursewareItem.name}</h6>
                <div class="status">
                  <j:switch value="${coursewareItem.status}">
                    <j:case value="0">
                      <span class="text-warning">未上传</span>
                    </j:case>
                    <j:case value="1">
                      <span class="text-warning">已上传</span>
                    </j:case>
                    <j:case value="2">
                      <span class="text-info">已提交</span>
                    </j:case>
                    <j:case value="3">
                      <span class="text-error">被驳回</span>
                    </j:case>
                    <j:case value="4">
                      <span class="text-success">通过</span>
                    </j:case>
                  </j:switch>
                </div>
              </div>
              <div class="kj-btns">
                <input type="hidden" class="coursewareItemId" name="coursewares[0].items[${s.index}].fdId"
                  value="${coursewareItem.fdId}"> <input type="hidden" id="fileName${coursewareItem.fdId}"
                  name="coursewares[0].items[${s.index}].name" value="${coursewareItem.name}">
                <!-- 已提交或通过不显示上传 -->
                <j:ifelse test="${coursewareItem.status == 2 || coursewareItem.status == 4}">
                  <j:then>
                    <button type="button" class="btn btn-info btn-small" disabled>上传</button>
                  </j:then>
                  <j:else>
                    <tags:courseware itemId="${coursewareItem.fdId}" fullnameid="fdUrl${coursewareItem.fdId}"
                      fullname="coursewares[0].items[${s.index}].path" filename="${coursewareItem.name}" filevalue=""
                      fullvalue="${coursewareItem.path}" id="uploadify${s.index + 1}" folder="System"></tags:courseware>
                  </j:else>
                </j:ifelse>
                <button type="button" class="last btn btn-info btn-small btn-submit" ${coursewareItem.submitButtonClass}>提&nbsp;交</button>
              </div>
              <div class="kj-btns">
                <j:ifelse test="${coursewareItem.status>=3}">
                  <j:then>
                    <j:switch value="${s.index mod 4}">
                      <j:case value="0">
                        <j:set name="placement" value="right" />
                      </j:case>
                      <j:case value="3">
                        <j:set name="placement" value="left" />
                      </j:case>
                      <j:default>
                        <j:set name="placement" value="top" />
                      </j:default>
                    </j:switch>
                    <a href="#" rel="popover" data-placement="${placement}" data-content='<a class="close" onClick="disPop(this);" href="#">&times;</a> ${coursewareItem.remark}' class="btn btn-link btn-small">评语</a>
                    <%-- <a href="#" rel="popover" data-placement="${placement}" data-content="${coursewareItem.remark}" class="btn btn-link btn-small">评语</a> --%>
                  </j:then>
                  <j:else>
                    <a href="#" class="btn btn-link btn-small" disabled>评&nbsp;语</a>
                  </j:else>
                </j:ifelse>
                <a href="${ctx}/common/courseware/${coursewareItem.fdId}" class="btn btn-link btn-small last" ${coursewareItem.seeButtonClass}>查&nbsp;看</a>
                
              </div>
            </div>
          </li>
        </j:iter>
      </ul>
    </div>
  </div>
</body>
</html>