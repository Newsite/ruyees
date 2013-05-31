<%@page pageEncoding="UTF-8"%>
<div id="view-category${vstatus.index+1}" class="modal hide fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">x</button>
    <h5 id="update-user-label">试卷试题</h5>
  </div>
  <div class="modal-body">
    <table class="table ">
      <tr>
        <th width="20%">试卷名称</th>
        <td>${bean.fdName}</td>
      </tr>
      <tr>
        <th>备注</th>
        <td>${bean.fdDescription}</td>
      </tr>
    </table>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal">取消</button>
  </div>
</div>