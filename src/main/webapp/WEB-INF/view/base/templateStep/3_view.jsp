<%@page pageEncoding="UTF-8"%>
<div>
	<table class="table">
		<tr class="vmid">
			<td width="13%">第三关名称</td>
			<td width="30%">${bean.templateItem[2].fdName}</td>
			<td>阶段时长</td>
			<td>${bean.templateItem[2].fdDayCount}&nbsp;天</td>
			<td>允许超时</td>
			<td>${bean.templateItem[2].fdDelayCount}&nbsp;天</td>
		</tr>
		<tr>
			<td>闯关说明</td>
			<td colspan="5">${bean.templateItem[2].fdRemarke}</td>
		</tr>
		<jsp:include page="../templateStep/ext_view.jsp" flush="true">
			<jsp:param name="stageIndex" value="2" />
		</jsp:include>
	</table>
</div>
<div>
	<h5 class="pr">视频</h5>
	<table class="table">
	   <tr>
		   <th width="15%">#</th>
		   <th width="30%">视频名称</th>
	   </tr>
	   <j:iter items="${bean.templateItem[2].templateContents[0].contentMovies}" var="movie" status="vs1">
		   <tr>
			   <th><span class="num">${vs1.index+1}</span></th>
			   <td>${movie.fdName}</td>
		   </tr>
 	   </j:iter>  
	</table>
	<table class="table">
		<tr>
			<td width="13%">导师寄语</td>
			<td colspan="3">${bean.templateItem[2].templateContents[0].fdRemarke}</td>
		</tr>
	</table>
</div>
<div>
	<h5 class="pr">试卷</h5>
	<table class="table">
	   <tr>
		   <th width="15%">#</th>
		   <th width="30%">试卷名称</th>
	   </tr>
	   <j:iter items="${bean.templateItem[2].templateContents[1].examCategories}" var="exam" status="vs1">
		   <tr>
			   <th><span class="num">${vs1.index+1}</span></th>
			   <td>${exam.fdName}</td>
		   </tr>
 	   </j:iter>  
	</table>
	<table class="table">
		<tr>
			<td width="13%">导师寄语</td>
			<td colspan="3">${bean.templateItem[2].templateContents[1].fdRemarke}</td>
		</tr>
	</table>
</div>
<div>
	<h5 class="pr">文档</h5>
	<table class="table ">
	   <tr>
		   <th width="15%">#</th>
		   <th width="30%">文档名称</th>
	   </tr>
	   <j:iter items="${bean.templateItem[2].templateContents[2].contentMovies}" var="content" status="vs1">
		   <tr>
			   <th><span class="num">${vs1.index+1}</span></th>
			   <td>${content.fdName}</td>
		   </tr>
 	   </j:iter>  
	</table>
	<table class="table ">
		<tr>
			<td width="13%">导师寄语</td>
			<td colspan="3">${bean.templateItem[2].templateContents[2].fdRemarke}</td>
		</tr>
	</table>
</div>
<div>
	<h5 class="pr">DEMO</h5>
	<table class="table ">
	   <tr>
		   <th width="15%">#</th>
		   <th width="30%">DEMO名称</th>
	   </tr>
	   <j:iter items="${bean.templateItem[2].templateContents[3].contentMovies}" var="movie" status="vs1">
		   <tr>
			   <th><span class="num">${vs1.index+1}</span></th>
			   <td>${movie.fdName}</td>
		   </tr>
 	   </j:iter>  
	</table>
	<table class="table">
		<tr>
			<td width="13%">导师寄语</td>
			<td colspan="3">${bean.templateItem[2].templateContents[3].fdRemarke}</td>
		</tr>
	</table>
</div>