<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<h5 class="pr">
  文档<A class="btn pa-right" title=增加
    href="javascript:open('${param.itemindex}','${param.contentId}','${param.method}');"><I class=icon-plus-sign></I>&nbsp;添加</A>
</h5>
<table id="${param.method}${param.itemindex}${param.contentId}" class="table ">
  <tbody>
    <tr>
      <th width="75%">文档名称</th>
      <th>操作</th>
    </tr>
    <j:iter items="${bean.templateItem[param.itemindex].templateContents[param.contentId].contentMovies}"
      var="viewcontent" status="vs1">
      <tr>
        <input type="hidden" value="${viewcontent.fdId }"
          name="templateItem[${param.itemindex }].templateContents[${param.contentId }].viewContentMovies[${vs1.index}].fdId" />
        <td>${viewcontent.fdName }</td>
        <th><a href="##"><i class="icon-trash"></i></a></th>
      </tr>
    </j:iter>
  </tbody>
</table>
