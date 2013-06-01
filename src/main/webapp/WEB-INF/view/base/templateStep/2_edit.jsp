<%@page import="ruyees.otp.utils.PhaseUtils"%>
<%@page pageEncoding="UTF-8"%>
<div class="">
	<table class="table ">
		<tr class="vmid">
			<input type="hidden" name="templateItem[1].fdId" />
			<input type="hidden" name="templateItem[1].fdIndex" value="2"/>
			<td width="16%">第二关名称</td>
			<td><input type="text" class="input-large"
				name="templateItem[1].fdName" value="学术准备"/></td>
			<td></td>
			<td><input type="hidden" class="input-xmini"
				name="templateItem[1].fdDayCount" <j:if test="${empty bean}">value="0"</j:if>/>&nbsp;</td>
			<td></td>
			<td><input type="hidden" class="input-xmini"
				name="templateItem[1].fdDelayCount" <j:if test="${empty bean}">value="0"</j:if>/>&nbsp;</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
				<tags:edit editid="fdRemarkeSecond" value="<%=PhaseUtils.getPhaseContext(2) %>"
						name="templateItem[1].fdRemarke" width="100%" height="100px;"></tags:edit>
				</j:then>
			<j:else>
				<tags:edit editid="fdRemarkeSecond"
						name="templateItem[1].fdRemarke" width="100%" height="100px;"></tags:edit>
			</j:else>
			</j:ifelse>
			</td>
		</tr>
		<jsp:include page="../templateStep/ext_edit.jsp" flush="true">
			<jsp:param name="stageIndex" value="1" />
		</jsp:include>
	</table>
</div>
<div class="">
	<jsp:include page="../templateStep/operation.jsp" flush="true">
		<jsp:param name="method" value="operation" />
		<jsp:param name="itemindex" value="1" />
		<jsp:param name="contentId" value="0" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[1].templateContents[0].fdId" />
	<input type="hidden" name="templateItem[1].templateContents[0].fdIndex" value="1"/>
		<tr>
			<td width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
					<tags:edit value="<%=PhaseUtils.getBuzhouContext(2, 1) %>" editid="fdRemarke10"
						name="templateItem[1].templateContents[0].fdRemarke" width="100%"
						height="100px;"></tags:edit>
				</j:then>
				<j:else>
					<tags:edit editid="fdRemarke10"
						name="templateItem[1].templateContents[0].fdRemarke" width="100%"
						height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
	</table>
</div>