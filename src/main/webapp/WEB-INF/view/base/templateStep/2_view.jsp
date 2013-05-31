<%@page pageEncoding="UTF-8"%>
<div>
	<table class="table ">
		<tr class="vmid">
			<td width="13%">第二关名称</td>
			<td width="30%">${bean.templateItem[1].fdName}</td>
			<td>阶段时长</td>
			<td>${bean.templateItem[1].fdDayCount}&nbsp;天</td>
			<td>允许超时</td>
			<td>${bean.templateItem[1].fdDelayCount}&nbsp;天</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">${bean.templateItem[1].fdRemarke}</td>
		</tr>
		<jsp:include page="../templateStep/ext_view.jsp" flush="true">
			<jsp:param name="stageIndex" value="1" />
		</jsp:include>
	</table>
</div>

<div>
	<h5 class="pr">作业包</h5>
	<table class="table ">
	   <tr>
		   <th width="15%">#</th>
		   <th width="30%">作业包名称</th>
	   </tr>
		   <tr>
			   <th><span class="num">1</span></th>
			   <td>${bean.templateItem[1].templateContents[0].operPackage.fdName}</td>
		   </tr>
	</table>
	<table class="table ">
		<tr>
			<td width="13%">导师寄语</td>
			<td colspan="3">${bean.templateItem[1].templateContents[0].fdRemarke}</td>
		</tr>
	</table>
</div>