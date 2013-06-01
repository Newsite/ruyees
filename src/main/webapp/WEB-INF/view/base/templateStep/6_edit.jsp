<%@page import="ruyees.otp.utils.PhaseUtils"%>
<%@page pageEncoding="UTF-8"%>

<j:ifelse test="${empty bean}">
	<j:then>
		<j:set name="daycount" value="0"/>
		<j:set name="delaydaycount" value="0"/>
	</j:then>
	<j:else>
		<j:set name="daycount" value="${bean.templateItem[5].fdDayCount }"/>
		<j:set name="delaydaycount" value="${bean.templateItem[5].fdDelayCount }"/>
	</j:else>
</j:ifelse>

	<table class="table ">
		<tr class="vmid">
			<input type="hidden" name="templateItem[5].fdId" value="${bean.templateItem[5].fdId }"/>
			<input type="hidden" name="templateItem[5].fdIndex" value="6"/>
			<input type="hidden" name="templateItem[5].templateContents[0].fdIndex" value="1"/>
			<input type="hidden" name="templateItem[5].templateContents[0].fdId" value="${bean.templateItem[5].templateContents[0].fdId }"/>
			<td width="16%">第六关名称</td>
			<td><input type="text" class="input-large" name="templateItem[5].fdName" value="课件确认"/></td>
			<td></td>
			<td><input type="hidden" class="input-xmini" name="templateItem[5].fdDayCount"  value="${daycount }" />&nbsp;</td>
			<td></td>
			<td><input type="hidden" class="input-xmini" name="templateItem[5].fdDelayCount" value="${delaydaycount }"/>&nbsp;</td>
		</tr>

		<tr>
			<td><span class="form-txt">闯关说明</span></td>
			<td colspan="5">
			 <j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit editid="fdRemarkesixth" value="<%=PhaseUtils.getPhaseContext(6) %>" name="templateItem[5].fdRemarke" width="100%" height="100px;"></tags:edit>
				</j:then>
				<j:else>
				<tags:edit editid="fdRemarkesixth" name="templateItem[5].fdRemarke" width="100%" height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
		<jsp:include page="../templateStep/ext_edit.jsp" flush="true">
			<jsp:param name="stageIndex" value="5" />
		</jsp:include>
	</table>

	<h5>提交作业</h5>
	<table class="table ">
		<tr>
			<td width="16%">提醒</td>
			<td colspan="3"><tags:edit
					editid="fdRemarke50"
					name="templateItem[5].templateContents[0].fdRemarke"
					width="100%" height="100px;"></tags:edit></td>
		</tr>
	</table>



