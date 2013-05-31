<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/otp_iframe.css">
<link rel="stylesheet" href="${ctx}/resources/css/video.css">
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/flash/jquery.jcarousel.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/flash/SWFobject.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/flash/video.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/iframe_inner.js"></script>
<script type="text/javascript">
	// videoPlayer
	var currentMovieId = "";
	function setMovieId(movieId) {
		currentMovieId = movieId;
		setComment(movieId,'');
	}

	function videoPlayComplete() {
		// videoPlayComplete
		setMovie(currentMovieId);
	}
	function gopage(pageNo){
		setComment(currentMovieId,pageNo);
	}

	function setRemark(remark) {
		$(".videoIntro").text(remark);
	}
	function deleteCommon(fdid){
		$.ajax({
            type: "get",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${ctx}/ajax/comment/deleteCommentByCwId",//要访问的后台地址
            data: "fdid=" + fdid,//要发送的数据
            
            success: function(msg){//msg为返回的数据，在这里做数据绑定
            	setComment(currentMovieId,'');
            }
        });	
	}
	function setComment(id,pageNo){
		//ajax
		var newId = '${member.newteachId}';
		var userId = '${user.id}';
		var commHtml = '<div class="com_list"><h3>老师们说，</h3>';
		  $.ajax({
            type: "get",//使用get方法访问后台
            dataType: "json",//返回json格式的数据
            url: "${ctx}/ajax/comment/findCommentByCwId",//要访问的后台地址
            data: "cwId=" + id+"&page="+pageNo,//要发送的数据
            
            success: function(msg){//msg为返回的数据，在这里做数据绑定
            	var data = msg.list;
            	var pageStr = msg.pageStr;
                $.each(data, function(i, n){
                	var value = n.poto;
                	if(value.indexOf("http")<0){
                		value = '${ctx}/'+value;
                	}
                	commHtml += '<div class="media"><a class="pull-left" href="#"><img class="media-object img-circle" src="';
        			commHtml += value + '"></a><div class="media-body"><p class="media-heading">';
        			commHtml += n.userName + '&nbsp;<strong>';
        			commHtml += n.departName + '</strong><br />';
        			commHtml += n.createdtime + '</p><p>';
        			commHtml += n.fdContent + '</p>';
        			if(newId==userId){
        				commHtml += '<a href="javascript:deleteCommon(\''+n.fdId+'\')">删除</a>';	
        			}
        			commHtml += '</div></div>';
                });
                commHtml += pageStr;
                commHtml += ' </div>\
		            <div class="com_pub">\
		              <h3>我想说，</h3>\
		              <textarea id="fdConent" placeholder="随便说些什么吧" class="input-block-level" rows="6">';
		              // 区分测试
		              commHtml += '</textarea>\
		              <div class="com_submit">\
		              	<button onClick="pushComment()" type="button" class="btn btn-info">说完了</button>\
		              </div>\
		            </div>';
		              $("#videoComment").html(commHtml);
            }
            
        });	
	}

	
	function pushComment(){
		var conent = $('#fdConent').val();
		 $.ajax({
             type: "post",
             url: "${ctx}/ajax/comment/pushComment",
             data : {
					"cwId" : currentMovieId,
					"fdConent" : conent
				},
             success: function(msg){setComment(currentMovieId,''); }   //操作成功后的操作！msg是后台传过来的值
            });
	}
</script>
<script type="text/javascript">
	function setMovie(mvieId) {
		var v = $("#" + mvieId).parent().next();
		var nextId = v.find("a").attr("id");
		$.ajax({
			type : "post",
			dataType : "json",
			url : "${ctx}/ajax/cm/setThrough",
			data : {
				mvieId : mvieId,
				nextId : nextId
			},
			success : function(data) {
				var status = data[0].status;
				var remark = data[0].remark;
				if (status != '-1') {
					$("#h" + mvieId).removeClass("text-error").addClass(
					"text-success").html("已完成").before('<i class="ico-ok"></i>');
					var url = v.find("a").attr("href");
					setMovieId(nextId);
					setRemark(remark);
					v.addClass("active").prev().removeClass("active");
					playVideo(url);
					$("#goD").trigger("click");
					
					if (status == '1') {
						$(".success_box").removeClass("hide");
						$(".btn-next").removeAttr("disabled");
						var h = $(document).height();  //刷窗口高度
						window.parent.setIframeH(h);
					}
				}
			}
		});
	}
</script>
</head>
<body>
  <div class="iframe-container">
    <div class="stepsTab-cont-top">
      <p class="location">
        <a href="${ctx}/trainee/step/${name}/1/1" class="active"><b class="sjl"></b><b class="sjl2"></b>1.1 学习入门视频<b
          class="sjr"></b><b class="sjr2"></b></a>
        <j:ifelse test="${throughStatus[1][1] == 1}">
          <j:then>
            <a href="${ctx}/trainee/step/${name}/1/2"><b class="sjl"></b><b class="sjl2"></b>1.2 参加在线测试<b
              class="sjr"></b><b class="sjr2"></b></a>
          </j:then>
          <j:else>
            <a href="#" id="nextStepNav"><b class="sjl"></b><b class="sjl2"></b>1.2 参加在线测试<b class="sjr"></b><b
              class="sjr2"></b></a>
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
	<j:ifelse test="${throughStatus[1][1] == 1}">
      <j:then>
        <div class="success_box well">
          <div>
                                   你已经完成了&nbsp;<span class="text-info">学习入门视频</span>&nbsp;请进入下一步&nbsp;
             <span class="text-info"><a href="${ctx}/trainee/step/${name}/1/2" rel="tooltip">参加在线测试</a></span>&nbsp;
             <a href="${ctx}/trainee/step/${name}/1/2" rel="tooltip" title="点击进入" class="icon-nextStep"></a>
          </div>
        </div>
      </j:then>
      <j:else>
        <div class="success_box well hide">
          <div>
           	 你已经完成了&nbsp;<span class="text-info">学习入门视频</span>&nbsp;请进入下一步&nbsp;
             <span class="text-info"><a href="${ctx}/trainee/step/${name}/1/2" rel="tooltip">参加在线测试</a></span>&nbsp;
             <a href="${ctx}/trainee/step/${name}/1/2" rel="tooltip" title="点击进入" class="icon-nextStep"></a>
          </div>
        </div>
      </j:else>
    </j:ifelse>
    <div class="row-container clearfix">
      <div id="videoWrap" class="pull-left">
        <div id="video"></div>
        <div class="videoIntro">${content.cws[0].fdDescribe}</div>
        <!-- 资源评论 -->
        <div class="Comment" id="videoComment">
          	<div class="com_list">
              <h3>老师们说，</h3>
              <div class="media">
                <a class="pull-left" href="#"><tags:image href="${member.guid.poto}" clas="media-object img-circle" /></a>
                <div class="media-body">
                  <p class="media-heading">
                  	张三 <strong>武汉学校英联邦部</strong><br />
                    2013年4月28日
                  </p>
                  <p>这个视频很棒。。。。。</p>
                </div>
              </div>
              <div class="media">
                <a class="pull-left" href="#"><tags:image href="${member.guid.poto}" clas="media-object img-circle" /></a>
                <div class="media-body">
                  <p class="media-heading">
                  	张三 <strong>武汉学校英联邦部</strong><br />
                    2013年4月28日
                  </p>
                  <p>这个视频很棒。。。。。</p>
                </div>
              </div>
              <div class="pagination pagination-centered pagination-small">
              	<ul>
                	<li class="disabled"><a href="#">«</a></li>
                    <li class="active"><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">»</a></li>
                </ul>
              </div>
            </div>
            <div class="com_pub">
              <h3>我想说，</h3>
              <textarea placeholder="随便说些什么吧" class="input-block-level" rows="6"></textarea>
              <div class="com_submit">
              	<button type="submit" class="btn btn-info">说完了</button>
              </div>
            </div>
          </div>
          <!-- end资源评论 -->
      </div>
      <div id="videoListWrap" class="pull-right">
        <ul id="videoList" class="unstyled">
          <j:iter items="${content.cws}" var="movie" status="vs">
            <li><j:set name="end" value="${fn:length(movie.bamAttMain.fileUrl)}" /> <a
              href="${fn:substring(movie.bamAttMain.fileUrl, 23, end-4)}"
              onclick="setMovieId('${movie.fdId}');setRemark('${movie.fdDescribe}')" id="${movie.fdId}"> <img
                class="img-polaroid" src="${ctx}/${movie.backgroundUrl}" width="133" height="72" /> <j:ifelse
                  test="${movie.through eq false}">
                  <j:then>
                    <span id="h${movie.fdId}" class="text-error">未完成</span>
                  </j:then>
                  <j:else>
                    <span id="h${movie.fdId}" class="text-success">已完成</span>
                  </j:else>
                </j:ifelse>
            </a>
              <h5 style="overflow: hidden;" title="${movie.fdName}">${movie.fdName}</h5></li>
          </j:iter>
        </ul>
        <j:ifelse test="${throughStatus[1][1] == 1}">
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
			window.location.href = '${ctx}/trainee/step/${name}/1/2';
		});
	});
	setComment('${content.cws[0].fdId}','');
  </script>
</body>
</html>