<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:ifelse
	test="${param.stageIndex==1||param.stageIndex==3||param.stageIndex==5 }">
	<j:then>
		<tr>
			<td>是否需要审核</td>
			<td colspan="5"><label class="radio inline"> 验证<input
					type="radio" name="templateItem[${param.stageIndex }].assessment"
					value="true" checked
					<j:if test="${templateItem[param.stageIndex].assessment eq true}">checked</j:if> />
			</label> <label class="radio inline"> 不验证<input type="radio"
					name="templateItem[${param.stageIndex }].assessment" value="false"
					<j:if test="${templateItem[param.stageIndex].assessment eq false}">checked</j:if> />
			</label></td>
		</tr>
		<tr>

			<td>默认评语</td>
			<td colspan="5"><textarea
					name="templateItem[${param.stageIndex }].defaultComment"
					id="defaultComment" rows="3" cols="40" class="input-block-level">${templateItem[param.stageIndex].defaultComment}</textarea>
			</td>
		</tr>
	</j:then>
	<j:else>
<input type="hidden" name="templateItem[${param.stageIndex }].assessment" value="true"/>
	</j:else>
</j:ifelse>
