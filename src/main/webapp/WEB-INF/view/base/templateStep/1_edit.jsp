<%@page import="cn.xdf.me.otp.utils.PhaseUtils"%>
<%@page pageEncoding="UTF-8"%>
<div>
	<table class="table ">
		<tr class="vmid">
			<input type="hidden" name="templateItem[0].fdId" />
			<input type="hidden" name="templateItem[0].fdIndex" value="1"/>
		<td width="16%">第一关名称</td>
			<td><input type="text" class="input-large"
				name="templateItem[0].fdName" value="业务学习"/></td>
			<td></td>
			<td><input type="hidden" class="input-xmini"
				name="templateItem[0].fdDayCount" <j:if test="${empty bean}">value="0"</j:if>/>&nbsp;</td>
			<td></td>
			<td><input type="hidden" class="input-xmini"
				name="templateItem[0].fdDelayCount" <j:if test="${empty bean}">value="0"</j:if>/>&nbsp;</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">
			 <j:ifelse test="${method eq 'add'}">
            	<j:then>
				<tags:edit editid="fdRemarkeFirst" value="<%=PhaseUtils.getPhaseContext(1) %>" name="templateItem[0].fdRemarke" width="100%" height="100px"></tags:edit>
				</j:then>
				<j:else>
				<tags:edit editid="fdRemarkeFirst" name="templateItem[0].fdRemarke" width="100%" height="100px"></tags:edit>
				</j:else>
			</j:ifelse>
			</td>
		</tr>
		<jsp:include page="../templateStep/ext_edit.jsp" flush="true">
			<jsp:param name="stageIndex" value="0" />
		</jsp:include>
	</table>
</div>
<div>
	<jsp:include page="../templateStep/video.jsp" flush="true">
		<jsp:param name="method" value="video" />
		<jsp:param name="itemindex" value="0" />
		<jsp:param name="contentId" value="0" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[0].templateContents[0].fdId" />
	<input type="hidden" name="templateItem[0].templateContents[0].fdIndex" value="1"/>
		<tr>
			<td  width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit editid="fdRemarke00"
					name="templateItem[0].templateContents[0].fdRemarke" value="<%=PhaseUtils.getBuzhouContext(1, 1) %>" width="100%"
					height="100px;"></tags:edit>
				</j:then>
				<j:else>
			<tags:edit editid="fdRemarke00"
					name="templateItem[0].templateContents[0].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
	</table>
</div>
<div>
	<jsp:include page="../templateStep/exam.jsp" flush="true">
		<jsp:param name="method" value="exam" />
		<jsp:param name="itemindex" value="0" />
		<jsp:param name="contentId" value="1" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[0].templateContents[1].fdId" />
	<input type="hidden" name="templateItem[0].templateContents[1].fdIndex" value="2"/>
		<tr>
			<td width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit value="<%=PhaseUtils.getBuzhouContext(1, 2) %>" editid="fdRemarke01"
					name="templateItem[0].templateContents[1].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:then>
			<j:else>
			<tags:edit editid="fdRemarke01"
					name="templateItem[0].templateContents[1].fdRemarke" width="100%"
					height="100px;"></tags:edit>
			</j:else>
			</j:ifelse>
			</td>
		</tr>
	</table>
</div>