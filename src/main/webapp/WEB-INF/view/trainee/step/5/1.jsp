<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tlds" %>
<j:set name="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/otp_iframe.css">
<link rel="stylesheet" href="${ctx}/resources/css/jquery-ui.css" >
<link rel="stylesheet" href="${ctx}/resources/css/calendar.css" >
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/iframe_inner.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var events=${gradeList};
	// 默认加载第一个备课
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
					
	// 测试当天是否有事件
    function hasEvents(date){
		var da = date.getFullYear()+"-"+fill0(date.getMonth()+1)+"-"+fill0(date.getDate());
		for(var i in events){
			if(events[i].date == da ){  
				return events[i];
			};
		}
		return false;
	}
	
	// 不足10的补0
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
						<b>时间：</b>'+events[x].date +'&nbsp'+ events[x].time +'<br />\
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
</script>
</head>
<body>
  <div class="iframe-container">
    <div class="stepsTab-cont-top">
      <p class="location">
        <a class="active" href="${ctx}/trainee/step/${name}/5/1"><b class="sjl"></b><b class="sjl2"></b>5.1 第五关：参加批课<b
          class="sjr"></b><b class="sjr2"></b></a>
        <j:ifelse test="${not empty content.grades[0].value}">
          <j:then>
            <a href="${ctx}/trainee/step/${name}/5/2"><b class="sjl"></b><b class="sjl2"></b>5.2 反馈批课结果<b
              class="sjr"></b><b class="sjr2"></b></a>
          </j:then>
          <j:else>
            <a href="#"><b class="sjl"></b><b class="sjl2"></b>5.2 反馈批课结果<b class="sjr"></b><b class="sjr2"></b></a>
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
    <j:if test="${empty items}">
      <div class="success_box well">
        <div>
          	请耐心等待指导教师&nbsp;<span class="text-info">${member.guid.realName}</span>&nbsp;
          </span>安排批课时间，请及时提醒TA<a href="#" rel="tooltip" title="发送邮件" class="icon-mail"></a>
            <a href="#" onclick="top.location='${ctx}/notify/toAdd/${member.guid.fdId}'" rel="tooltip" title="站内消息" class="icon-online"></a>。
        </div>
      </div>
    </j:if>
    <j:if test="${not empty items}">
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
        <div class="blue_box ui-corner-all title-box">
          <h5>全部日程</h5>
        </div>
        <div id="pk-list">
          <j:iter items="${items}" var="item" status="vs">
            <dl class="dl-horizontal pass">
              <dt class="title">
                <b><j:ifelse test="${empty item.value}"><j:then>未批</j:then><j:else>已批</j:else></j:ifelse>&nbsp;</b>${item.grade.title}
              </dt>
              <dd>
                <b>批课&nbsp;</b><b class="point">${vs.index+1}<i class="text-dack"> /${itemSize}</i></b>
              </dd>
              <dt>
                <b>时间&nbsp;</b>
                <fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;-&nbsp;<fmt:formatDate value="${item.endTime}" pattern="HH:mm"/>
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
    </j:if>
  </div>
</body>
</html>