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
<link rel="stylesheet" href="${ctx}/resources/css/carousel.css">
<style type="text/css">
#carouselWrap .jcarousel-clip {
  height: 660px;
}

#goD {
  top: 676px;
}
</style>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/flash/jquery.jcarousel.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/flash/carousel.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/iframe_inner.js?id=12"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify.js?id=111"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".btn-submit").click(function() {
			var indexId = $(this).closest("li").find(".bamIndexId").val();
			var status = $(this).parent().next('.status').find("span").eq(0);
			var _this = $(this);
			var fileNameId = '#fileName' + indexId;
			var fullNameId = '#fdUrl' + indexId;
			var fileName = $(fileNameId).val();
			var fullName = $(fullNameId).val();
			$.ajax({
				type : "post",
					dataType : "json",
					url : "${ctx}/ajax/index/setData",
					data : {
						"indexId" : indexId,
						"fileName" : fileName,
						"fullName" : fullName
					},
				success : function(msg) {
					if (msg != "-1") {
						if (msg == "1") {
							goTop(function() {
								$(".btn-next").removeAttr("disabled");
								$(".success_box").removeClass("hide");
							});
							window.location.href = '${ctx}/trainee/step/${name}/2/2';
						}
						status.removeClass("text-warning").addClass('text-info').text("已提交");
						_this.attr("disabled", "disabled");
						
						_this.prevAll(".uploadify").find("object").detach();
						_this.prevAll(".uploadify").find(".btn").attr("disabled","disabled");
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
<j:iter items="${content.bamPackage.bamOperation}" var="bamOperation" status="s">
  <j:iter items="${bamOperation.bamIndexs}" var="bamIndex" status="s">
    <j:if test="${bamIndex.status == '0' || bamIndex.status == '1'}">
      <j:set name="allUploaded" value="false" />
    </j:if>
  </j:iter>
</j:iter>
<body   >
  <div class="iframe-container">
    <div class="stepsTab-cont-top">
      <p class="location">
        <a class="active" href="${ctx}/trainee/step/${name}/2/1"><b class="sjl"></b><b class="sjl2"></b>2.1 完成基础学术作业<b
          class="sjr"></b><b class="sjr2"></b></a>
        <j:ifelse test="${allUploaded eq true}">
          <j:then>
            <a href="${ctx}/trainee/step/${name}/2/2"><b class="sjl"></b><b class="sjl2"></b>2.2 批改作业<b class="sjr"></b><b
              class="sjr2"></b></a>
          </j:then>
          <j:else>
            <a href="#"><b class="sjl"></b><b class="sjl2"></b>2.2 批改作业<b class="sjr"></b><b class="sjr2"></b></a>
          </j:else>
        </j:ifelse>
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
    <j:ifelse test="${allUploaded eq true}">
      <j:then>
        <div class="success_box well">
          <div>你已经完成了基础学术作业中所有指标的提交任务。<br />请耐心等待指导教师&nbsp;<span class="text-info">${member.guid.realName}</span>&nbsp;的审批。如果久未批复，请及时提醒TA。<a
            href="#" onclick="top.location='${ctx}/notify/toAdd/${member.guid.fdId}'" rel="tooltip" title="站内消息" class="icon-online"></a><a
            href="mailto:${member.guid.fdEmail}?subject=请多关照" rel="tooltip" title="发送邮件" class="icon-mail"></a>
          </div>
        </div>
      </j:then>
      <j:else>
        <div class="success_box well hide">
          <div>你已经完成了基础学术作业中所有指标的提交任务。<br />请耐心等待指导教师&nbsp;<span class="text-info">${member.guid.realName}</span>&nbsp;的审批。如果久未批复，请及时提醒TA。<a
            href="#" onclick="top.location='${ctx}/notify/toAdd/${member.guid.fdId}'" rel="tooltip" title="站内消息" class="icon-online"></a><a
            href="mailto:${member.guid.fdEmail}?subject=请多关照" rel="tooltip" title="发送邮件" class="icon-mail"></a>
          </div>
        </div>
      </j:else>
    </j:ifelse>
    <div class="row-container clearfix">
      <div class="pull-left">
        <div class="content-box showMoreWrap clearfix">
          <p class="fs14">
            <strong>教研材料</strong>
          </p>
          <div class="bookList">${content.bamPackage.bamOperation[index].fdReferBook}</div>
          <a href="#" class="showMore"><span>查看全部</span><i class="icon-chevron-down"></i></a>
        </div>
        <div class="content-box showMoreWrap clearfix">
          <p class="fs14">
            <strong>成果要求</strong>
          </p>
          <ul class="unstyled askList">${content.bamPackage.bamOperation[index].fdRequest}
          </ul>
          <a href="#" class="showMore"><span>查看全部</span><i class="icon-chevron-down"></i></a>
        </div>
        <div class="content-box clearfix">
          <div class="fs14">
            <strong>成果指标</strong>
          </div>
          <ul class="unstyled uploadList">
            <j:iter items="${content.bamPackage.bamOperation[index].bamIndexs}" var="bamIndex" status="s">
              <li><b class="num-bg text-info">${s.index+1}</b>
                <div class="rbox">
                  <div style="width: 200px" title="${bamIndex.fdIndexName}">${bamIndex.fdIndexName}</div>
                  <div class="posAb-bar">
                    <j:ifelse test="${not empty bamIndex.attMain.filePath}">
                      <j:then>
                        <a href="${ctx}/common/download/${bamIndex.attMain.fdId}" class="btn btn-link">下载模板</a>
                      </j:then>
                      <j:else>
                        <a href="#" class="btn btn-link" disabled>下载模板</a>
                      </j:else>
                    </j:ifelse>
                    <input type="hidden" class="bamIndexId"
                      name="bamPackage.bamOperation[${index}].bamIndexs[${s.index}].bamAttMain.fdId"
                      value="${bamIndex.fdId}"> <input type="hidden" id="fileName${bamIndex.fdId}"
                      name="bamPackage.bamOperation[${index}].bamIndexs[${s.index}].bamAttMain.fileName"
                      value="${bamIndex.bamAttMain.fileName}">
                    <!-- 已提交或通过不显示上传 -->
                    <j:ifelse test="${bamIndex.status==2||bamIndex.status==4}">
                      <j:then>
                        <button type="button" class="btn btn-info" disabled>上传</button>
                      </j:then>
                      <j:else>
                        <tags:receptionupload indexId="${bamIndex.fdId}" fullnameid="fdUrl${bamIndex.fdId}"
                          fullname="bamPackage.bamOperation[${index}].bamIndexs[${s.index}].bamAttMain.filePath"
                          filename="fileName${bamIndex.fdId}" filevalue="" fullvalue=""
                          id="uploadify${s.index+1}" folder="System">${bamIndex.uploadButtonClass}</tags:receptionupload>
                      </j:else>
                    </j:ifelse>
                    <button type="button" class="btn btn-info btn-submit" ${bamIndex.submitButtonClass}>提交</button>
                    <div class="progress progress-info progress-striped active hide">
                      <div class="bar" style="width: 0%"></div>
                    </div>
                    <!-- 已上传显示查看 -->
                    <a id="upload${bamIndex.fdId}" href="${ctx}/common/download/${bamIndex.bamAttMain.fdId}" class="btn btn-link" ${bamIndex.seeButtonClass}>查看</a>
                   
                    <!-- 通过驳回显示评语 -->
                    <j:ifelse test="${bamIndex.status>=3}">
                      <j:then>
                        <a href="#" rel="popover" data-placement="top" data-content='<a class="close" onClick="disPop(this);" href="#">&times;</a> ${bamIndex.remark}' class="btn btn-link">评语</a>
                      </j:then>
                      <j:else>
                        <a href="#" class="btn btn-link" disabled>评语</a>
                      </j:else>
                    </j:ifelse>
                  </div>
                  <div class="status">
                    <j:switch value="${bamIndex.status}">
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
                    <span><b class="point"><j:if test="${empty bamIndex.fdToValue}">0</j:if>${bamIndex.fdToValue}/${bamIndex.fdValue}</b>分</span>
                  </div>
                </div></li>
            </j:iter>
          </ul>
        </div>
      </div>
      <div id="carouselWrap" class="pull-right">
        <ul id="carousel" class="unstyled">
          <j:set name="operId" value="${content.bamPackage.bamOperation[index].fdId}" />
          <j:iter items="${content.bamPackage.bamOperation}" var="operation" status="s">
            <j:ifelse test="${operId== operation.fdId}">
              <j:then>
                <li class="active"><a href="${ctx}/trainee/step/${name}/2/1/${operation.fdId}"> <img
                    class="img-polaroid" src="${ctx}/${operation.backgroundUrl}" width="133" height="72"> <j:ifelse
                      test="${operation.through==false}">
                      <j:then>
                        <span class="text-error">未通过</span>
                      </j:then>
                      <j:else>
                        <span class="text-success">通过</span>
                      </j:else>
                    </j:ifelse>
                </a>
                  <h5 style="overflow: hidden;" title="${operation.fdName}">${operation.fdName}</h5></li>
              </j:then>
              <j:else>
                <li><a href="${ctx}/trainee/step/${name}/2/1/${operation.fdId}"> <img class="img-polaroid"
                    src="${ctx}/${operation.backgroundUrl}" width="133" height="72"> <j:ifelse
                      test="${operation.through==false}">
                      <j:then>
                        <span class="text-error">未通过</span>
                      </j:then>
                      <j:else>
                        <span class="text-success">通过</span>
                      </j:else>
                    </j:ifelse>
                </a>
                  <h5 style="overflow: hidden;" title="${operation.fdName}">${operation.fdName}</h5></li>
              </j:else>
            </j:ifelse>
          </j:iter>
        </ul>
        <j:ifelse test="${allUploaded eq true}">
          <j:then>
            <button class="btn btn-block btn-info btn-next">
              下一步<i class="icon-nextStep"></i>
            </button>
          </j:then>
          <j:else>
            <button class="btn btn-block btn-info btn-next" disabled>
              下一步<i class="icon-nextStep"></i>
            </button>
          </j:else>
        </j:ifelse>
      </div>
    </div>
  </div>
  <script type="text/javascript">
	$(".btn-next").click(function() {
		goTop(function() {
			window.location.href = '${ctx}/trainee/step/${name}/2/2';
		});
	});
  </script>
</body>
</html>