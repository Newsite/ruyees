<%@page pageEncoding="UTF-8"%>
<div id="view-category${vstatus.index+1}" class="modal hide fade" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">x</button>
    <h5 id="update-user-label">基本信息</h5>
  </div>
  <div class="modal-body">
    <table class="table ">
      <tbody>
        <tr>
          <td style="width: 50px;">类型</td>
          <j:switch value="${bean.fdType}">
            <j:case value="1">
              <td colspan="3">项目</td>
            </j:case>
            <j:case value="2">
              <td colspan="3">课程</td>
            </j:case>
            <j:case value="3">
              <td colspan="3">分项</td>
            </j:case>
            <j:default>
              <td colspan="3">阶段</td>
            </j:default>
          </j:switch>
        </tr>
        <tr>
          <td style="width: 50px;">名称</td>
          <td>${bean.fdName}</td>
          <td style="width: 50px;">排序</td>
          <td>${bean.fdSeqNum}</td>
        </tr>
        <tr>
          <td style="width: 50px;">创建时间</td>
          <td colspan="3">${bean.fdDateCreated}</td>
        </tr>
        <tr>
          <td style="width: 50px;">备注</td>
          <td colspan="3">${bean.fdDescription}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal">取消</button>
  </div>
</div>