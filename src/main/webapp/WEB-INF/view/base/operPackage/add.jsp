<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="add-category" class="modal hide fade" tabindex="-1" style="display: none;">
  <form class="form-horizontal" method="post" name="addform" id="addinputForm" action="${ctx}/group/operPackage/save">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">x</button>
      <h5 id="add-user-label">学术作业包</h5>
    </div>
    <div class="modal-body">
      <table class="table">
        <tr>
          <th width="30px">名称</th>
          <td>
            <input type="text" id="fdName" name="fdName" value="${bean.fdName}" style="width:80%;" class="inpTd input-xlarge required" />
            <font color="red"><label id="errorInfo"></label></font>
          </td>
        </tr>
        <tr>
          <th width="30px">备注</th>
          <td><textarea name="fdDescription" id="fdDescription" rows="6" cols="80" style="width:80%;">${bean.fdDescription}</textarea></td>
        </tr>
      </table>
    </div>
    <div class="modal-footer">
      <button class="btn btn-info">确定</button>
      <button class="btn" data-dismiss="modal">取消</button>
    </div>
  </form>
</div>