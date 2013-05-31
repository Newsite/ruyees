<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<div id="add-diction" class="modal fade in" tabindex="111" style="display: none;">
  <form class="form-horizontal" method="post" id="inputForm" action="${ctx}/group/diction/save" name="addform">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">x</button>
      <h5 id="add-user-label">基本信息</h5>
    </div>
    <div class="modal-body">
      <table class="table">
        <tr>
          <th width="20%">类型</th>
          <td><select name="bean.fdType" id="fdType" class="required">
              <option value="">选择类型</option>
              <option value="1">项目</option>
              <option value="2">课程</option>
              <option value="3">分项</option>
              <option value="4">阶段</option>
          </select></td>
        </tr>
        <tr>
          <th>名称</th>
          <td><input type="text" id="fdName" name="bean.fdName" class="required" /> <font color="red"> <label
              id="errorInfo"></label>
          </font></td>
        </tr>
        <tr>
          <th>排序</th>
          <td><input type="text" name="bean.fdSeqNum" class="required number" maxlength="3" /></td>
        </tr>
        <tr>
          <th>备注</th>
          <td><textarea name="bean.fdDescription" id="fdDescription" rows="6" cols="60" style="width: 85%"></textarea></td>
        </tr>
      </table>
    </div>
    <div class="modal-footer">
      <button class="btn btn-info">确定</button>
      <button class="btn" data-dismiss="modal">取消</button>
    </div>
  </form>
</div>