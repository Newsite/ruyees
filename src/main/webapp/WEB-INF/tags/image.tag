<%@tag import="ruyees.otp.utils.ComUtils"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="clas" type="java.lang.String" required="true"%>
<%@ attribute name="href" type="java.lang.String" required="true"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%
	String w = width;
	String h = height;
	if(StringUtils.isBlank(w)){
		w = "48px";
		request.setAttribute("w", w);
	}
	if(StringUtils.isBlank(h)){
		h = "48px";
		request.setAttribute("h", h);
	}
	boolean ishttp = false;
	if (href.contains("http")) {
		ishttp = true;
	}
	
%>
<%
if(StringUtils.isBlank(href)){
	%>
	<img width="<%=w%>" height="<%=h%>" class="<%=clas%>" src="${ctx}/<%=ComUtils.getDefaultPoto()%>" onerror=""/>	
	<%
}
else if (ishttp) {
%>
<img class="<%=clas%>" src="<%=href%>" width="<%=w%>" height="<%=h%>"/>
<%
	} else {
%>
<img class="<%=clas%>" src="${ctx}/<%=href%>" width="<%=w%>" height="<%=h%>"/>
<%
	}
%>
