<%@page import="org.apache.commons.lang3.math.NumberUtils"%><%@page import="org.apache.commons.lang3.StringUtils"%><%@ page contentType="text/html;charset=UTF-8"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%><j:set name="ctx" value="${pageContext.request.contextPath}" /><!DOCTYPE html><html lang="zh_CN"><head><script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script></head><body oncontextmenu="return false" onselectstart="return false" ondragstart="return false" onbeforecopy="return false" onmouseup="document.selection.empty()" oncopy="document.selection.empty()" onselect="document.selection.empty()">  <div class="container">    <div class="steps-tabs tabbable tabs-left">      <ul class="nav nav-tabs">        <j:iter items="${statges }" var="stage" status="s">          <j:ifelse test="${s.index+1 == stageIndex }">            <j:then>              <li class="active">            </j:then>            <j:else>              <j:ifelse test="${stage.enable==false }">                <j:then>                  <li class="disabled">                </j:then>                <j:else>                  <li>                </j:else>              </j:ifelse>            </j:else>          </j:ifelse>          <a href="${ctx}/trainee/step/${name}/${s.index+1 }/${stage.goTo}" target="iframe_1"><strong>第 <i              class="icon-Num${s.index+1 }">一</i> 关</strong><i class="icon-lock"></i>            <div class="stepTit">${stage.templateItem.fdName }</div>          </a>          </li>        </j:iter>      </ul>      <div class="tab-content">        <div class="topbar">          <span>${bean.course.fdName}${bean.stage.fdName}${bean.item.fdName}备课闯关</span><span>指导教师&nbsp;${member.guid.realName}          <!-- <a href="#" rel="tooltip" title="聊天" class="icon-online"></a> -->            <a href="mailto:${member.guid.fdEmail}?subject=请多关照" rel="tooltip" title="发送邮件" class="icon-mail"></a>             <a href="${ctx}/notify/toAdd/${member.guid.fdId}" rel="tooltip" title="站内消息" class="icon-online"></a>          </span><span> <!-- <a href="#"><i              class="icon-help"></i>系统帮助</a> --> <a href="${ctx}/trainee/to/${name}"><i class="icon-home"></i>返回</a>          </span>        </div>        <iframe id="iframe_1" name="iframe_1" src="${ctx}/trainee/step/${name}/${stageIndex }/${step}" frameborder="0"          scrolling="no" width="100%"></iframe>      </div>    </div>  </div></body></html>