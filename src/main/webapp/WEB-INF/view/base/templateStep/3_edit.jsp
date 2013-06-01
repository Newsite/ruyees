<%@page import="ruyees.otp.utils.PhaseUtils"%>
<%@page pageEncoding="UTF-8"%>
<div class="">
	<table class="table ">
		<tr class="vmid">
			<input type="hidden" name="templateItem[2].fdId" />
			<input type="hidden" name="templateItem[2].fdIndex" value="3"/>
			<td width="16%">第三关名称</td>
			<td><input type="text" class="input-large"
				name="templateItem[2].fdName" value="标准化教案学习"/></td>
			<td></td>
			<td><input type="hidden" class="input-xmini"
				name="templateItem[2].fdDayCount" <j:if test="${empty bean}">value="0"</j:if>/>&nbsp;</td>
			<td></td>
			<td><input type="hidden" class="input-xmini"
				name="templateItem[2].fdDelayCount" <j:if test="${empty bean}">value="0"</j:if>/>&nbsp;</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit value="<%=PhaseUtils.getPhaseContext(3) %>" editid="fdRemarkethird"
					name="templateItem[2].fdRemarke" width="100%" height="100px;"></tags:edit>
				</j:then>
				<j:else>
				<tags:edit editid="fdRemarkethird"
					name="templateItem[2].fdRemarke" width="100%" height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
		<jsp:include page="../templateStep/ext_edit.jsp" flush="true">
			<jsp:param name="stageIndex" value="2" />
		</jsp:include>
	</table>
</div>
<div class="">
	<jsp:include page="../templateStep/video.jsp" flush="true">
		<jsp:param name="method" value="video" />
		<jsp:param name="itemindex" value="2" />
		<jsp:param name="contentId" value="0" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[2].templateContents[0].fdId" />
	<input type="hidden" name="templateItem[2].templateContents[0].fdIndex" value="1"/>
		<tr>
			<td width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit value="<%=PhaseUtils.getBuzhouContext(3, 1) %>" editid="fdRemarke20"
					name="templateItem[2].templateContents[0].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:then>
				<j:else>
				<tags:edit editid="fdRemarke20"
					name="templateItem[2].templateContents[0].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
	</table>
</div>
<div class="">
	<jsp:include page="../templateStep/exam.jsp" flush="true">
		<jsp:param name="method" value="exam" />
		<jsp:param name="itemindex" value="2" />
		<jsp:param name="contentId" value="1" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[2].templateContents[1].fdId" />
	<input type="hidden" name="templateItem[2].templateContents[1].fdIndex" value="2"/>
		<tr>
			<td width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit value="<%=PhaseUtils.getBuzhouContext(3, 2) %>" editid="fdRemarke21"
					name="templateItem[2].templateContents[1].fdRemarke" width="100%"
					height="100px;"></tags:edit>
			</j:then>
			<j:else>
			<tags:edit editid="fdRemarke21"
					name="templateItem[2].templateContents[1].fdRemarke" width="100%"
					height="100px;"></tags:edit>
			</j:else>
			</j:ifelse>
			</td>
		</tr>
	</table>
</div>
<div class="">
	<jsp:include page="../templateStep/content.jsp" flush="true">
		<jsp:param name="method" value="content" />
		<jsp:param name="itemindex" value="2" />
		<jsp:param name="contentId" value="2" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[2].templateContents[2].fdId" />
	<input type="hidden" name="templateItem[2].templateContents[2].fdIndex" value="3"/>
		<tr>
			<td width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit value="<%=PhaseUtils.getBuzhouContext(3, 3) %>" editid="fdRemarke22"
					name="templateItem[2].templateContents[2].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:then>
				<j:else>
				<tags:edit editid="fdRemarke22"
					name="templateItem[2].templateContents[2].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
	</table>
</div>
<div class="">
	<jsp:include page="../templateStep/video.jsp" flush="true">
		<jsp:param name="method" value="video" />
		<jsp:param name="itemindex" value="2" />
		<jsp:param name="contentId" value="3" />
	</jsp:include>
	<table class="table ">
	<input type="hidden" name="templateItem[2].templateContents[3].fdId" />
	<input type="hidden" name="templateItem[2].templateContents[3].fdIndex" value="4"/>
		<tr>
			<td width="16%">导师寄语</td>
			<td colspan="3">
			<j:ifelse test="${method eq 'add'}">
            	<j:then>
			<tags:edit value="<%=PhaseUtils.getBuzhouContext(3, 4) %>" editid="fdRemarke23"
					name="templateItem[2].templateContents[3].fdRemarke" width="100%"
					height="100px;"></tags:edit>
				</j:then>
				<j:else>
				<tags:edit editid="fdRemarke23" name="templateItem[2].templateContents[3].fdRemarke" width="100%" height="100px;"></tags:edit>
				</j:else>
				</j:ifelse>
			</td>
		</tr>
	</table>
</div>