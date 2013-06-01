<%@page import="ruyees.otp.utils.PhaseUtils"%>
<%@page pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/resources/js/courseware.js"></script>
<div>
  <table class="table ">
    <tr class="vmid">
      <input type="hidden" name="templateItem[3].fdId" />
      <input type="hidden" name="templateItem[3].fdIndex" value="4" />
      <td width="16%">第四关名称</td>
      <td><input type="text" class="input-large" name="templateItem[3].fdName" value="课件编写"/></td>
      <td></td>
      <td><input type="hidden" class="input-xmini" name="templateItem[3].fdDayCount"
        <j:if test="${empty bean}">value="0"</j:if> />&nbsp;</td>
      <td></td>
      <td><input type="hidden" class="input-xmini" name="templateItem[3].fdDelayCount"
        <j:if test="${empty bean}">value="0"</j:if> />&nbsp;</td>
    </tr>
    <tr>
      <td>闯关说明</td>
      <td colspan="5">
      <j:ifelse test="${method eq 'add'}">
            	<j:then>
      <tags:edit editid="fdRemarkeforuth" value="<%=PhaseUtils.getPhaseContext(4) %>" name="templateItem[3].fdRemarke" width="100%"
          height="100px;"></tags:edit>
        </j:then>
        <j:else>
         <tags:edit editid="fdRemarkeforuth" name="templateItem[3].fdRemarke" width="100%"
          height="100px;"></tags:edit>
        </j:else>
        </j:ifelse> 
         </td>
    </tr>
    <jsp:include page="../templateStep/ext_edit.jsp" flush="true">
      <jsp:param name="stageIndex" value="3" />
    </jsp:include>
  </table>
</div>
<div class="">
  <h5>提交作业</h5>
  <table class="table ">
    <input type="hidden" name="templateItem[3].templateContents[0].fdId" />
    <input type="hidden" name="templateItem[3].templateContents[0].fdIndex" value="1" />
    <tr>
      <td width="16%">提醒</td>
      <td colspan="3">
       <j:ifelse test="${method eq 'add'}">
            	<j:then>
	      <tags:edit value="<%=PhaseUtils.getBuzhouContext(4, 1) %>" editid="fdRemarke30" name="templateItem[3].templateContents[0].fdRemarke"
	          width="100%" height="100px;"></tags:edit>
         </j:then>
         <j:else>
          <tags:edit editid="fdRemarke30" name="templateItem[3].templateContents[0].fdRemarke"
	          width="100%" height="100px;"></tags:edit>
         </j:else>
         </j:ifelse>
         </td>
    </tr>
  </table>
</div>
<div>
  <h5>课件要求</h5>
  <table class="table ">
    <!-- <input type="hidden" name="templateItem[3].templateContents[1].fdId" />
	<input type="hidden" name="templateItem[3].templateContents[1].fdIndex" value="2"/> -->
    <input type="hidden" name="templateItem[3].templateContents[0].courseware.fdId" />
    <tr>
      <td colspan="4">
       <j:ifelse test="${method eq 'add'}">
            	<j:then>
      <tags:edit value="<%=PhaseUtils.getBuzhouContext(4, 2) %>" editid="couReq" name="templateItem[3].templateContents[0].courseware.couReq"
          width="100%" height="100px;"></tags:edit>
         </j:then>
         <j:else>
         <tags:edit editid="couReq" name="templateItem[3].templateContents[0].courseware.couReq"
          width="100%" height="100px;"></tags:edit>
         </j:else>
         </j:ifelse>
         </td>
    </tr>
  </table>
</div>
<div>
  <h5>课件资料</h5>
  <table class="table ">
    <tr>
      <td colspan="4">
       <j:ifelse test="${method eq 'add'}">
            	<j:then>
      <tags:edit value="<%=PhaseUtils.getBuzhouContext(4, 3) %>" editid="couNeedInfo"
          name="templateItem[3].templateContents[0].courseware.couNeedInfo" width="100%" height="100px;"></tags:edit>
         </j:then>
         <j:else>
         <tags:edit editid="couNeedInfo"
          name="templateItem[3].templateContents[0].courseware.couNeedInfo" width="100%" height="100px;"></tags:edit>
         </j:else>
         </j:ifelse>
       </td>
    </tr>
  </table>
</div>
<div>
  <h5>提交课件分项</h5>
  <table class="table setItem">
    <thead>
      <tr>
        <th width="9%">NO<a href="#" class="addItem"><i class="icon-plus-sign"></i></a></th>
        <th width="25%">分项名称</th>
      </tr>
    </thead>
    <tbody>
      <j:iter items="${bean.templateItem[3].templateContents[0].courseware.items}" var="item" status="i">
        <tr>
          <input type="hidden" name="templateItem[3].templateContents[0].courseware.items[${i.index}].fdId"
            value="${item.fdId }" />
          <th><span class="num">${i.index+1 }</span><a href="##" class="delItem"><i class="icon-minus"></i></a></th>
          <td><input type="text" name="templateItem[3].templateContents[0].courseware.items[${i.index}].name"
            value="${item.name}" class="input-medium span5" /></td>
        </tr>
      </j:iter>
    </tbody>
  </table>
</div>