<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<h5 class="pr">
  作业包<A class="btn pa-right" title=增加
    href="javascript:open2('${param.itemindex }','${param.contentId }','${bean.templateItem[1].templateContents[0].fdId }','${param.method}');"><I class=icon-plus-sign></I>&nbsp;添加</A>
</h5>
<table id="${param.method}${param.itemindex}${param.contentId}" class="table ">
  <tbody>
    <tr>
      <th width="75%">作业包名称</th>
      <th>操作</th>
    </tr>
    <j:if test="${not empty bean.templateItem[param.itemindex].templateContents[param.contentId].operPackage}">
      <tr id="${bean.templateItem[param.itemindex].templateContents[param.contentId].operPackage.fdId }">
        <input type="hidden"
          value="${bean.templateItem[param.itemindex].templateContents[param.contentId].operPackage.fdId }"
          name="templateItem[${param.itemindex }].templateContents[${param.contentId }].operPackage.fdId" />
        <td>${bean.templateItem[param.itemindex].templateContents[param.contentId].operPackage.fdName}</td>
        <th><a href="##"><i class="icon-trash"></i></a></th>
      </tr>
    </j:if>
  </tbody>
</table>
