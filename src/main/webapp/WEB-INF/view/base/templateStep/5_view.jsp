<%@page pageEncoding="UTF-8"%>
<div>
	<table class="table">
		<tr class="vmid">
			<td width="13%">第五关名称</td>
			<td width="30%">${bean.templateItem[4].fdName}</td>
			<td>阶段时长</td>
			<td>${bean.templateItem[4].fdDayCount}&nbsp;天</td>
			<td>允许超时</td>
			<td>${bean.templateItem[4].fdDelayCount}&nbsp;天</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">${bean.templateItem[4].fdRemarke}</td>
		</tr>
		<jsp:include page="../templateStep/ext_view.jsp" flush="true">
			<jsp:param name="stageIndex" value="4" />
		</jsp:include>
	</table>
</div>
<div>
	<h5>新教师评分项</h5>
	<table class="table">
		   <tr>
			   <th width="10%">#</th>
	  		   <th>分项名称</th>
	  		   <th>序号</th>
	  		   <th>分项说明</th>
		   </tr>
		   <j:iter items="${bean.templateItem[4].templateContents[0].newItemConfs}" var="n" status="vs1">
			   <tr>
				   <th><span class="num">${vs1.index+1}</span></th>
				   <td>${n.fdName}</td>
				   <td>${n.fdIndex}</td>
				   <td>${n.fdRemarker}</td>
			   </tr>
	 	   </j:iter>  
	</table>
</div>
<div>
	<h5>批课教师评分项</h5>
	<table class="table">
		   <tr>
			   <th width="10%">#</th>
	  		   <th>分项名称</th>
	  		   <th>序号</th>
	  		   <th>分项说明</th>
		   </tr>
		   <j:iter items="${bean.templateItem[4].templateContents[0].evalItems}" var="check" status="vs1">
			   <tr>
				   <th><span class="num">${vs1.index+1}</span></th>
				   <td>${check.fdName}</td>
				   <td>${check.fdIndex}</td>
				   <td>${check.fdExplain}</td>
			   </tr>
	 	   </j:iter>  
	</table>
	<table class="table">
		<tr>
			<td width="13%">导师寄语</td>
			<td colspan="3">${bean.templateItem[4].templateContents[0].fdRemarke}</td>
		</tr>
	</table>
</div>