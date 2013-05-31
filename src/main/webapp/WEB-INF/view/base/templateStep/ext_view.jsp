<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>

		<tr>
			<td>是否需要审核</td>
			<td>
				<j:ifelse test="${bean.templateItem[param.stageIndex].assessment eq true}">
					<j:then>验证</j:then>
					<j:else>不验证</j:else>
				</j:ifelse>
			</td>
			<td>默认评语</td>
			<td colspan="3">
				${bean.templateItem[param.stageIndex].defaultComment}
			</td>
		</tr>