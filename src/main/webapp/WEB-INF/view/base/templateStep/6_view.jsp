<%@page pageEncoding="UTF-8"%>
<div>
	<table class="table ">
		<tr class="vmid">
			<td width="13%">第六关名称</td>
			<td width="30%">${bean.templateItem[5].fdName}</td>
			<td>阶段时长</td>
			<td>${bean.templateItem[5].fdDayCount}&nbsp;天</td>
			<td>允许超时</td>
			<td>${bean.templateItem[5].fdDelayCount}&nbsp;天</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">${bean.templateItem[5].fdRemarke}</td>
		</tr>
		<jsp:include page="../templateStep/ext_view.jsp" flush="true">
			<jsp:param name="stageIndex" value="5" />
		</jsp:include>
	</table>
</div>
<div>
	<h5>作业</h5>
	<table class="table">
		<tr>
			<td width="13%">提醒</td>
			<td colspan="3">${bean.templateItem[5].templateContents[0].fdRemarke}</td>
		</tr>
	</table>
</div>