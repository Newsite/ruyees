<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<h5 class="pr">
  视频
  &nbsp;&nbsp;&nbsp;
  <a class="btn pa-right" title=增加
    href="javascript:open('${param.itemindex }','${param.contentId }','${param.method}');"><i class=icon-plus-sign></i>&nbsp;添加</a>
</h5>
<table id="${param.method}${param.itemindex}${param.contentId}" class="table ">
  <tbody>
    <tr>
      <th width="75%">视频名称</th>
      <th>操作</th>
    </tr>
    <j:iter items="${bean.templateItem[param.itemindex].templateContents[param.contentId].contentMovies}" var="video"
      status="vs1">
      <tr id="${video.fdId }">
        <input type="hidden" value="${video.fdId }"
          name="templateItem[${param.itemindex }].templateContents[${param.contentId }].viewContentMovies[${vs1.index}].fdId" />
        <td>${video.fdName}</td>
        <th><a href="##"><i class="icon-trash"></i></a></th>
      </tr>
    </j:iter>
  </tbody>
</table>