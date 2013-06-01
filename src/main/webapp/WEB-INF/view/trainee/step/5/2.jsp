<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tlds" %>
<j:set name="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批课</title>
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/otp_iframe.css">
<link rel="stylesheet" href="${ctx}/resources/css/jquery-ui.css" >
<link rel="stylesheet" href="${ctx}/resources/css/calendar.css" >
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/iframe_inner.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-form/form.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var events=${gradeList};
	//默认加载第一个备课
	var pkclass="point";
	if(events[0].pass){pkclass="point pass";}
	var remark0=events[0].remark==null?"":events[0].remark;
	$("#eventCont").html('<b>备课：</b>'+events[0].title+'<br />\
						<b>批课：</b><b class="'+pkclass+'">'+1+'<i class="text-dack"> / '+events.length+'</i></b><br />\
						<b>时间：</b>'+events[0].date +'&nbsp;'+ events[0].time+'<br />\
						<b>地点：</b>'+events[0].place+'<br />\
						<b>批课教师：</b>'+events[0].teach+'<br />\
						<b>新教师：</b>'+events[0].newteach+'<br />\
						<b>备注：</b>'+remark0+'<br />\
					');
					
	//测试当天是否有事件
    function hasEvents(date){
		var da = date.getFullYear()+"-"+fill0(date.getMonth()+1)+"-"+fill0(date.getDate());
		for(var i in events){
			if(events[i].date == da ){  
				return events[i];
			}
		}
		return false;
	}
	
	//不足10的补0
	function fill0(n){
		if(n<10){
			return "0"+n;
		}
		else{
			return n;
		}
	}
			
	$("#calendar").datepicker({
		dateFormat: "yy-mm-dd",
		beforeShowDay: function(date) { 
			var css = "";
			var alt = "";
			if(hasEvents(date)){
				if(hasEvents(date).pass){
					css = "day-isPass";				
					alt = "已批";
				}else{
					css = "day-notPass";				
					alt = "未批";
				}
			}
			return [true,css,alt];
		},
		onSelect: function(dateText,inst){
			var pkclass="point";
			for(var x in events){
				if(events[x].date == dateText)
				{
					if(events[x].pass){
						pkclass="point pass";
					}
					var remarkx=events[x].remark==null?'':events[x].remark;
					$("#eventCont").html('<b>备课：</b>'+events[x].title+'<br />\
						<b>批课：</b><b class="'+pkclass+'">'+(parseInt(x)+1)+'<i class="text-dack"> / '+events.length+'</i></b><br />\
						<b>时间：</b>'+events[x].date +'&nbsp;'+ events[x].time+'<br />\
						<b>地点：</b>'+events[x].place+'<br />\
						<b>批课教师：</b>'+events[x].teach+'<br />\
						<b>新教师：</b>'+events[x].newteach+'<br />\
						<b>备注：</b>'+remarkx+'<br />\
					');
				}
			}
		}
	});
});

$(document).ready(function(){
	//$(".rating-star-bar a").tooltip({placement:"bottom"});
	$(".btn-submit").click(function(){
	var count=$("#count").val();
	var values =[];    
	$('input[name^="totalvalue"]').each(function(){ 
		var value=$(this).val();
		if(value!='') {
			values.push(value);    
		};
	});    
	if(values.length==0){
		window.parent.ifraAlert2("请给您的批课老师打分!");
		return false;
	}
	if(values.length!=count){
		window.parent.ifraAlert2("请确认您的每位老师都打过分!");
		return false;
	}
	
	var appraises =[];    
	$('input[name^="appraise"]').each(function(){ 
		var appraise=$(this).val();
		if(appraise!=''){
			appraises.push(appraise);    
		}
	});    
	if(appraises.length==0 ){
		window.parent.ifraAlert2("请给您的批课老师一点评语！");
		return false;
	}
	if(appraises.length!=count){
		window.parent.ifraAlert2("请确认您的每位老师都有评语！");
		return false;
	}
	
	var data = $("#ajaxform").serializeArray();
	jQuery.post("${ctx}/ajax/grade/pushGrade", data,RateArticleSuccess, "json");
	return false; 
	 
});
});
//回传信息
function RateArticleSuccess(data){
	if(data==true){
		goTop(function(){
			$(".success_box .temp").html('恭喜你完成&nbsp;<span class="text-info">批课</span>&nbsp;关卡！请进入下一关&nbsp;<span class="text-info"><a href="#" rel="tooltip" onclick="window.parent.activeStep(6)">课件确认</a></span>&nbsp;<a href="#" rel="tooltip" title="点击进入下一关" class="icon-nextStep" onclick="window.parent.activeStep(6)"></a>');
		});	
	}
}
</script>
</head>
<body oncontextmenu="return false" onselectstart="return false" ondragstart="return false" onbeforecopy="return false" oncopy="document.selection.empty()" onselect="document.selection.empty()">
  <form class="form-horizontal" method="post" id="ajaxform" action="#" name="form">
    <input type="hidden" name="gradeId" value="${grade.fdId}">
    <div class="iframe-container">
      <div class="stepsTab-cont-top">
        <p class="location">
          <a href="${ctx}/trainee/step/${name}/5/1"><b class="sjl"></b><b class="sjl2"></b>5.1 第五关：参加批课<b
            class="sjr"></b><b class="sjr2"></b></a> <a class="active" href="${ctx}/trainee/step/${name}/5/2"><b
            class="sjl"></b><b class="sjl2"></b>5.2 反馈批课结果<b class="sjr"></b><b class="sjr2"></b></a>
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
      <j:if test="${throughStatus[5][1] != 1}">
        <div class="success_box well">
          批课老师对你的批课评价是："${content.grades[0].remark}" <br /> 批课平均成绩是<b class="point">${content.grades[0].value}</b>分<br />
          <div class="temp">请反馈批课结果，给指导教师打分，帮助我们改进工作。</div>
        </div>
      </j:if>
      <j:if test="${throughStatus[5][1] == 1}">
        <div class="success_box well">
          	批课老师对你的批课评价是："${content.grades[0].remark}" <br /> 批课平均成绩是<b class="point">${content.grades[0].value}</b>分<br />
         	 恭喜你完成<span class="text-info">批课</span>关卡！ 请进入下一关 
         	 <span class="text-info"><a href="#" rel="tooltip" onclick="goTop(function(){window.parent.activeStep(6);});">确认课件</a></span> 
         	 <a href="#" rel="tooltip" title="点击进入下一关" class="icon-nextStep" onclick="goTop(function(){window.parent.activeStep(6);});"></a>
        </div>
      </j:if>
      <div class="content-box clearfix" id="rating-wrap">
        <div id="rating-titbar">
          <strong class="fs14">给批课老师打分</strong>
          	<button type="submit" class="btn btn-info btn-submit">完成</button>
        </div>
        <div class="tabbable tabs-left" id="rating-tabs">
          <ul class="nav nav-tabs">
            <!-- 取批课老师个数 -->
            <j:set name="count" value="0" />
            <%-- <j:iter items="${grade.ticklings}" var="tick" status="vs1"> --%>
            <j:ifelse test="${flag eq 1}">
            	<j:then>
		            <j:iter items="${ticks}" var="tick" status="vs1">
		              <li <j:if test="${vs1.index eq 0}">class="active"</j:if>><a href="#teacher${vs1.index}"
		                data-toggle="tab">${tick.advier.realName}</a></li>
		              <j:set name="count" value="${count+1}" />
		            </j:iter>
            	</j:then>
            	<j:else>
            		<j:iter items="${gradeItems}" var="tick" status="vs1">
		              <li <j:if test="${vs1.index eq 0}">class="active"</j:if>><a href="#teacher${vs1.index}"
		                data-toggle="tab">${tick.advier.realName}</a></li>
		              <j:set name="count" value="${count+1}" />
		            </j:iter>
            	</j:else>
            </j:ifelse>
            <input type="hidden" id="count" value="${count}" />
          </ul>
          <div class="tab-content">

           <%--  <j:iter items="${grade.ticklings}" var="tick" status="vs2"> --%>
            <j:ifelse test="${flag eq 1}">
            	<j:then>
		            <j:iter items="${ticks}" var="tick" status="vs2">
		              <div class="tab-pane <j:if test="${vs2.index eq 0}">active</j:if>" id="teacher${vs2.index}">
		                <dl class="dl-horizontal dl-rating">
		                  <dt>分项打分</dt>
		                  <dd>
		                    <ol>
		                      <j:iter items="${evals}" var="eval" status="vs3">
		                        <j:if test="${vs3.index==0}">
		                          <j:set name="valuei" value="${tick.value1}" />
		                        </j:if>
		                        <j:if test="${vs3.index==1}">
		                          <j:set name="valuei" value="${tick.value2}" />
		                        </j:if>
		                        <j:if test="${vs3.index==2}">
		                          <j:set name="valuei" value="${tick.value3}" />
		                        </j:if>
		                        <j:if test="${vs3.index==3}">
		                          <j:set name="valuei" value="${tick.value4}" />
		                        </j:if>
		                        <j:if test="${vs3.index==4}">
		                          <j:set name="valuei" value="${tick.value5}" />
		                        </j:if>
		                        <li>${eval.fdName} <span class="rating-star-bar"> <input type="hidden"
		                            name="value${vs3.index+1}${tick.fdId}" value="${valuei}" /> <j:if
		                              test="${empty valuei || valuei eq 0}">
		                              <a href="#" title="不满意 "></a>
		                              <a href="#" title="还好吧 "></a>
		                              <a href="#" title="不置可否 "></a>
		                              <a href="#" title="是的 "></a>
		                              <a href="#" title="非常给力 "></a>
		                            </j:if> <j:if test="${valuei eq 1}">
		                              <a href="#" title="不满意 " class="rated"></a>
		                              <a href="#" title="还好吧 "></a>
		                              <a href="#" title="不置可否 "></a>
		                              <a href="#" title="是的 "></a>
		                              <a href="#" title="非常给力 "></a>
		                            </j:if> <j:if test="${valuei eq 2}">
		                              <a href="#" title="不满意 " class="rated"></a>
		                              <a href="#" title="还好吧 " class="rated"></a>
		                              <a href="#" title="不置可否 "></a>
		                              <a href="#" title="是的 "></a>
		                              <a href="#" title="非常给力 "></a>
		                            </j:if> <j:if test="${valuei eq 3}">
		                              <a href="#" title="不满意 " class="rated"></a>
		                              <a href="#" title="还好吧 " class="rated"></a>
		                              <a href="#" title="不置可否 " class="rated"></a>
		                              <a href="#" title="是的 "></a>
		                              <a href="#" title="非常给力 "></a>
		                            </j:if> <j:if test="${valuei eq 4}">
		                              <a href="#" title="不满意 " class="rated"></a>
		                              <a href="#" title="还好吧 " class="rated"></a>
		                              <a href="#" title="不置可否 " class="rated"></a>
		                              <a href="#" title="是的 " class="rated"></a>
		                              <a href="#" title="非常给力 "></a>
		                            </j:if> <j:if test="${valuei eq 5}">
		                              <a href="#" title="不满意 " class="rated"></a>
		                              <a href="#" title="还好吧 " class="rated"></a>
		                              <a href="#" title="不置可否 " class="rated"></a>
		                              <a href="#" title="是的 " class="rated"></a>
		                              <a href="#" title="非常给力 " class="rated"></a>
		                            </j:if>
		                        </span>
		                        </li>
		                      </j:iter>
		                    </ol>
		                  </dd>
		                  <dt>综合评价</dt>
		                  <dd>
		                    <input type="hidden" name="tickId" value="${tick.fdId}" /> <input type="hidden" name="grade.fdId"
		                      value="${tick.grade.fdId}" /> <input type="text" name="appraise${tick.fdId}"
		                      value="${tick.appraise}" maxlength="500" class="input-xxlarge inp-valuation" />
		                  </dd>
		                </dl>
		                <div class="pos-point">
		                  <input type="hidden" name="totalvalue${tick.fdId}" value="${tick.totalvalue}" /> <b
		                    class="icon-point-bg point"><j:if test="${empty tick.totalvalue}">0</j:if>${tick.totalvalue}</b>分
		                </div>
		              </div>
		            </j:iter>
            	</j:then>
            	<j:else>
            		 <j:iter items="${gradeItems}" var="tick" status="vs2">
		              <div class="tab-pane <j:if test="${vs2.index eq 0}">active</j:if>" id="teacher${vs2.index}">
		                <dl class="dl-horizontal dl-rating">
		                  <dt>分项打分</dt>
		                  <dd>
		                    <ol>
		                      <j:iter items="${evals}" var="eval" status="vs3">
		                        <li>${eval.fdName} 
		                        <span class="rating-star-bar"> 
		                        	  <input type="hidden" name="value${vs3.index+1}${tick.fdId}" value=""/>
		                              <a href="#" title="不满意 "></a>
		                              <a href="#" title="还好吧 "></a>
		                              <a href="#" title="不置可否 "></a>
		                              <a href="#" title="是的 "></a>
		                              <a href="#" title="非常给力 "></a>
		                        </span>
		                        </li>
		                      </j:iter>
		                    </ol>
		                  </dd>
		                  <dt>综合评价</dt>
		                  <dd>
		                    <input type="hidden" name="tickId" value="${tick.fdId}" /> 
		                    <input type="hidden" name="grade.fdId" value="${tick.grade.fdId}" /> 
		                    <input type="text" name="appraise${tick.fdId}" value="" maxlength="500" class="input-xxlarge inp-valuation" />
		                  </dd>
		                </dl>
		                <div class="pos-point">
		                  <input type="hidden" name="totalvalue${tick.fdId}" value="" /> <b class="icon-point-bg point">0</b>分
		                </div>
		              </div>
		            </j:iter>
            	</j:else>
            </j:ifelse>
          </div>
        </div>
      </div>
      <div class="content-box clearfix">
        <div class="fs14">
          <strong>批课安排</strong>
        </div>
        <div class="clearfix mt10">
          <div class="pull-left">
            <div id="calendar"></div>
          </div>
          <div class="pull-right">
            <div id="currEvent" class="ui-corner-all blue_box">
              <div class="sj"></div>
              <h5>当前日程</h5>
              <div id="eventCont"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="content-box clearfix ">
        <div class="">
          <strong class="fs14">批课纪录</strong>
        </div>
        <div id="pk-list">
          <j:iter items="${items}" var="item" status="vs">
            <dl class="dl-horizontal  pass">
              <dt class="title">
                <b>已批&nbsp;</b>${item.grade.title}
              </dt>
              <dd>
                <b>批课&nbsp;</b><b class="point">${vs.index+1}<i class="text-dack"> /${itemSize}</i></b>
              </dd>
              <dt>
                <b>时间&nbsp;</b><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;-&nbsp;
                <fmt:formatDate value="${item.endTime}" pattern="HH:mm"/> 
              </dt>
              <dd>
                <b>地点&nbsp;</b>${item.address}
              </dd>
              <dt>
                <b>批课教师&nbsp;</b><b>${item.advier.realName}</b>
              </dt>
              <dd>
                <b>新教师&nbsp;</b>${item.newTeach.realName}
              </dd>
              <dt>
                <b>备注&nbsp;</b>${item.remark}
              </dt>
              <dd></dd>
              <div class="pos-point">
                <b class="icon-point-bg point"><j:if test="${empty item.value}">0</j:if>${item.value}</b>分
              </div>
            </dl>
          </j:iter>
        </div>
      </div>
    </div>
  </form>
</body>
</html>