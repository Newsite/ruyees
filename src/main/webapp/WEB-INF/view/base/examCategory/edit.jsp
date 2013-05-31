<%@page pageEncoding="UTF-8"%>
<div id="update-category${vstatus.index+1}" class="modal hide fade" tabindex="${vstatus.index+1}" style="display: none;">
  <form action="${ctx}/group/examCategory/save" method="post">
    <input type="hidden" id="fdId" name="fdId" value="${bean.fdId}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">x</button>
      <h5 id="update-user-label">试卷试题</h5>
    </div>
    <div class="modal-body">
      <table class="table ">
        <tr>
          <th width="20%">试卷名称</th>
          <td><input type="text" id="fdName" name="fdName" value="${bean.fdName}"
            class="inpTd input-xlarge required" /></td>
        </tr>
        <tr>
          <th>备注</th>
          <td><textarea name="fdDescription" id="fdDescription" rows="6" cols="80" style="width: 85%">${bean.fdDescription}</textarea></td>
        </tr>
      </table>
    </div>
    <div class="modal-footer">
      <button class="btn btn-info">确定</button>
      <button class="btn" data-dismiss="modal">取消</button>
    </div>
  </form>
</div>