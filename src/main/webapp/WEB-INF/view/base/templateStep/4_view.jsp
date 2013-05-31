<%@page pageEncoding="UTF-8"%>
<div>
	<table class="table">
		<tr class="vmid">
			<td width="13%">第四关名称</td>
			<td width="30%">${bean.templateItem[3].fdName}</td>
			<td>阶段时长</td>
			<td>${bean.templateItem[3].fdDayCount}&nbsp;天</td>
			<td>允许超时</td>
			<td>${bean.templateItem[3].fdDelayCount}&nbsp;天</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">${bean.templateItem[3].fdRemarke}</td>
		</tr>
		<jsp:include page="../templateStep/ext_view.jsp" flush="true">
			<jsp:param name="stageIndex" value="3" />
		</jsp:include>
	</table>
</div>
<div>
	<h5>作业</h5>
	<table class="table">
		<tr>
			<td width="13%">提醒</td>
			<td colspan="3">${bean.templateItem[3].templateContents[0].fdRemarke}</td>
		</tr>
	</table>
</div>
<div>
	<h5>课件要求</h5>
	<table class="table">
		<tr>
			<td colspan="4">${bean.templateItem[3].templateContents[0].courseware.couReq}</td>
		</tr>
	</table>
</div>
<div>
	<h5>课件资料</h5>
	<table class="table">
		<tr>
			<td colspan="4">${bean.templateItem[3].templateContents[0].courseware.couNeedInfo}</td>
		</tr>
	</table>
</div>
<div>
	<h5>课件分项</h5>
	<table class="table">
	   <tr>
		   <th width="10%">#</th>
  		   <th colspan="4">分项名称</th>
	   </tr>
	   <j:iter items="${bean.templateItem[3].templateContents[0].courseware.items}" var="i" status="vs1">
		   <tr>
			   <th><span class="num">${vs1.index+1}</span></th>
			   <td colspan="4">${i.name}</td>
		   </tr>
 	   </j:iter>  
	</table>
</div>