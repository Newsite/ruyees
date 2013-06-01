<%@page import="ruyees.otp.utils.PhaseUtils"%>
<%@page pageEncoding="UTF-8"%>
<script src="${ctx}/resources/js/grade.js" type="text/javascript"></script>
<table class="table ">
  <tr class="vmid">
    <input type="hidden" name="templateItem[4].fdId" />
    <input type="hidden" name="templateItem[4].fdIndex" value="5" />
    <input type="hidden" name="templateItem[4].templateContents[0].fdIndex" value="1" />
    <input type="hidden" name="templateItem[4].templateContents[0].fdId"
      value="${bean.templateItem[4].templateContents[0].fdId }" />
    <td width="16%">第五关名称</td>
    <td><input type="text" class="input-large" name="templateItem[4].fdName" value="批课"/></td>
    <td></td>
    <td><input type="hidden" class="input-xmini" name="templateItem[4].fdDayCount"
      <j:if test="${empty bean}">value="0"</j:if> />&nbsp;</td>
    <td></td>
    <td><input type="hidden" class="input-xmini" name="templateItem[4].fdDelayCount"
      <j:if test="${empty bean}">value="0"</j:if> />&nbsp;</td>
  </tr>
  <tr>
    <td><span class="form-txt">闯关说明</span></td>
    <td colspan="5">
     <j:ifelse test="${method eq 'add'}">
            	<j:then>
    <tags:edit value="<%=PhaseUtils.getPhaseContext(5) %>" editid="fdRemarkefifth" name="templateItem[4].fdRemarke" width="100%"
        height="100px;"></tags:edit>
        </j:then>
        <j:else>
        <tags:edit editid="fdRemarkefifth" name="templateItem[4].fdRemarke" width="100%"
        height="100px;"></tags:edit>
        </j:else>
        </j:ifelse>
    </td>
  </tr>
  <jsp:include page="../templateStep/ext_edit.jsp" flush="true">
    <jsp:param name="stageIndex" value="4" />
  </jsp:include>
</table>
<h5>新教师评分项</h5>
<table class="table setNew">
  <thead>
    <tr>
      <th width="9%">NO<a href="#" class="addNew"><i class="icon-plus-sign"></i></a></th>
      <th width="25%">分项名称</th>
      <th width="9%">序号</th>
      <th>分项说明</th>
    </tr>
  </thead>
  <tbody>
    <j:iter items="${bean.templateItem[4].templateContents[0].newItemConfs}" var="newItemConf" status="s">
      <tr>
        <input type="hidden" name="templateItem[4].templateContents[0].newItemConfs[${s.index }].fdId"
          value="${newItemConf.fdId }" />
        <th><span class="num">${s.index+1 }</span><a href="##" class="delNew"><i class="icon-minus"></i></a></th>
        <td><input type="text" name="templateItem[4].templateContents[0].newItemConfs[${s.index }].fdName"
          value="${newItemConf.fdName }" class="input-medium" /></td>
        <td><input type="text" name="templateItem[4].templateContents[0].newItemConfs[${s.index }].fdIndex"
          value="${newItemConf.fdIndex }" class="input-xmini" /></td>
        <td><input type="text" name="templateItem[4].templateContents[0].newItemConfs[${s.index }].fdRemarker"
          class="input-block" value="${newItemConf.fdRemarker }" /></td>
      </tr>
    </j:iter>
  </tbody>
</table>
<h5>批课教师评分项</h5>
<table class="table bordered setCheck">
  <thead>
    <tr>
      <th width="9%">NO<a href="#" class="addCheck"><i class="icon-plus-sign"></i></a></th>
      <th width="25%">分项名称</th>
      <th width="9%">序号</th>
      <th>分项说明</th>
    </tr>
  </thead>
  <tbody>
    <j:iter items="${bean.templateItem[4].templateContents[0].evalItems}" var="evalItem" status="s">
      <tr>
        <input type="hidden" name="templateItem[4].templateContents[0].evalItems[${s.index }].fdId"
          value="${evalItem.fdId }" />
        <th><span class="num">${s.index+1 }</span><a href="##" class="delCheck"><i class="icon-minus"></i></a></th>
        <td><input type="text" name="templateItem[4].templateContents[0].evalItems[${s.index }].fdName"
          value="${evalItem.fdName }" class="input-medium" /></td>
        <td><input type="text" name="templateItem[4].templateContents[0].evalItems[${s.index }].fdIndex"
          value="${evalItem.fdIndex }" class="input-xmini" /></td>
        <td><input type="text" name="templateItem[4].templateContents[0].evalItems[${s.index }].fdExplain"
          class="input-block" value="${evalItem.fdExplain }" /></td>
      </tr>
    </j:iter>
  </tbody>
</table>
<table class="table">
  <tr>
    <td width="16%">提醒</td>
    <td colspan="3"><tags:edit editid="fdRemarke40" name="templateItem[4].templateContents[0].fdRemarke"
        width="100%" height="100px;"></tags:edit></td>
  </tr>
</table>