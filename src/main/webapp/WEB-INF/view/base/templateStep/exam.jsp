<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<h5 class="pr">
  试卷<a class="btn pa-right" href="javascript:open('${param.itemindex }','${param.contentId }','${param.method}');"><i
    class=icon-plus-sign></i>&nbsp;添加</a>
</h5>
<table id="${param.method}${param.itemindex}${param.contentId}" class="table ">
  <tbody>
    <tr>
      <th width="75%">试卷名称</th>
      <th>操作</th>
    </tr>
    <j:iter items="${bean.templateItem[param.itemindex].templateContents[param.contentId].examCategories}"
      var="examcate" status="vs1">
      <tr id="${examcate.fdId }">
        <input type="hidden" value="${examcate.fdId }"
          name="templateItem[${param.itemindex }].templateContents[${param.contentId }].viewExamCategories[${vs1.index}].fdId" />
        <td>${examcate.fdName }</td>
        <th><a href="##"><i class="icon-trash"></i></a></th>
      </tr>
    </j:iter>
  </tbody>
</table>