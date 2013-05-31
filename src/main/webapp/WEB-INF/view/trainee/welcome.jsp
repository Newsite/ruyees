<%@page import="cn.xdf.me.otp.model.flow.BamProject"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
<%
List<BamProject> projects = (List<BamProject>)request.getAttribute("beans");
%>
<style type="text/css">
.wel-main {
  <%if(projects!=null&&projects.size()>=5){
  %>
   margin: 47px auto 0;
  width: 956px;
  height: 510px;
  background: url(${ctx}/resources/images/wel-bg2.jpg) no-repeat 0 0;
   position: relative;
  -webkit-box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2)	
  <%
  }else{//小于4
  %>
   margin: 47px auto 0;
  width: 956px;
  height: 439px;
  background: url(${ctx}/resources/images/wel-bg.jpg) no-repeat 0 0;
   position: relative;
  -webkit-box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2)	
  <%
  }
  %>
}
</style>
</head>
<body>
  <div class="container">
    <div class="wel-main">
      <div class="pos-box">
        <h4>
          国外考试新教师在线备课平台
          <div class="en">Overseas Testing Teacher Training Platform (OTP)</div>
        </h4>
        <div class="avatar">
          <tags:image href="${person.poto}" clas="img-polaroid img-face" />
          &nbsp;亲爱的${person.realName}老师，
        </div>
        <div class="greeting">欢迎登录国外考试新教师在线备课平台。请点击下方你所要进行备课的课程按钮，马上开始充实的备课之旅吧！</div>
      </div>
      <div class="wel-btn-group">
        <ul class="nav nav-tabs">
          <j:iter items="${beans}" var="bean" status="vs">
            <j:if test="${vs.index > 0&&vs.index!=4}">
              <li>|</li>
            </j:if>
            <li class="active">
              <a href="${ctx}/trainee/to/${bean.fdId}">${bean.template.course.fdName}${bean.template.stage.fdName}${bean.template.item.fdName}&nbsp;<i class="icon-otp-wel"></i></a>
            </li>
          </j:iter>
          <%
          	/*
          	<j:iter items="${beans}" var="bean" status="vs">
          	  <j:if test="${vs.index > 0}">
          	    <li>|</li>
          	  </j:if>
          	  <j:ifelse test="${bean.enableByUser}">
          	    <j:then>
          	      <li class="active"><a href="${ctx}/trainee/to/${bean.fdId}">${bean.fdName}<i
          	          class="icon-otp-wel"></i></a></li>
          	    </j:then>
          	    <j:else>
          	      <li><a><span>${bean.fdName}</span><i class="icon-otp-wel"></i></a></li>
          	    </j:else>
          	  </j:ifelse>
          	</j:iter>
          	 */
          %>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>