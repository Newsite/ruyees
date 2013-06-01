<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		// 聚焦第一个输入框
		jQuery("#updateform${vstatus.index}").validate();
		return;
	});
</script>
<div id="update-diction${vstatus.index+1}" class="modal hide fade" tabindex="${vstatus.index+1}" style="display: none;">
  <j:autoform>
    <form id="updateform${vstatus.index}" method="post" action="${ctx}/group/diction/save">
      <input type="hidden" name="bean.fdId" value="${bean.fdId}" /> <input type="hidden" name="bean.fdType"
        value="${bean.fdType}" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">x</button>
        <h5 id="add-user-label">基本信息</h5>
      </div>
      <div class="modal-body">
        <table class="table ">
          <tr>
            <th width="20%">类型</th>
            <td><j:switch value="${bean.fdType}">
                <j:case value="1">项目</j:case>
                <j:case value="2">课程</j:case>
                <j:case value="3">分项</j:case>
                <j:case value="4">阶段</j:case>
              </j:switch></td>
          </tr>
          <tr>
            <th>名称</th>
            <td><input type="text" name="bean.fdName" class="required" /><font color="red"><label
                id="errorInfo"></label></font></td>
          </tr>
          <tr>
            <th>排序</th>
            <td><input type="text" id="pwd-input" name="bean.fdSeqNum" class="required number" maxlength="3" /></td>
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
  </j:autoform>
</div>