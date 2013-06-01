<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript">
	function submitCourseware() {
		document.form.method = "post";
		document.form.action = '${ctx}/coach/courseware/save/4';
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <form class="form-horizontal" method="post" action="" name="form">
    <input type="hidden" name="fdId" value="${bamCourseware.fdId}" /> <input type="hidden" name="newTeachId"
      value="${person.fdId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/courseware/list/4" class="tile-return">返回上页</a>
          <h4>课件审批</h4>
          <p>在本模块中，您可以审批新教师提交的课件。</p>
        </div>
        <j:set name="show" value="true" />
        <j:iter items="${bamCourseware.items}" var="bean2">
          <j:if test="${bean2.through eq false}">
            <j:set name="show" value="false" />
          </j:if>
        </j:iter>
        <table class="table ">
          <tr>
            <th width="20%">新教师</th>
            <td colspan="3">${person.realName}</td>
          </tr>
          <j:if test="${show eq true}">
            <j:ifelse test="${bamCourseware.through eq true}">
              <j:then>
                <tr>
                  <th>状态</th>
                  <td colspan="3"><j:ifelse test="${bamCourseware.through eq true}">
                      <j:then>通过</j:then>
                      <j:else>驳回</j:else>
                    </j:ifelse></td>
                </tr>
                 <tr>
                  <input type="hidden" name="through" value="true"/>
                  <th>评语</th>
                  <td colspan="3"><textarea name="fdComment" id="fdComment" rows="6" cols="80" class="span7">${bamCourseware.fdComment}</textarea>
                  <input type="hidden" name="through" value="1"/>
                  </td>
                </tr>
                <tr>
                  <td colspan="4"><a class="btn btn-info" value="提交" id="saveBtn" onclick="submitCourseware()">确定</a></td>
                </tr>
              </j:then>
              <j:else>
                <tr>
                  <input type="hidden" name="through" value="true"/>
                  <th>评语</th>
                  <td colspan="3"><textarea name="fdComment" id="fdComment" rows="6" cols="80" class="span7">${bamCourseware.fdComment}</textarea>
                  <input type="hidden" name="through" value="1"/>
                  </td>
                </tr>
                <tr>
                  <td colspan="4"><a class="btn btn-info" value="提交" id="saveBtn" onclick="submitCourseware()">确定</a></td>
                </tr>
              </j:else>
            </j:ifelse>
          </j:if>
        </table>
        <table class="table">
          <thead>
            <tr>
              <th width="10px">#</th>
              <th>课件</th>
              <th width="10%">状态</th>
              <th width="15%">操作</th>
            </tr>
          </thead>
          <tbody>
            <j:iter items="${bamCoursewareItemList}" var="bean" status="vstatus">
              <tr>
                <td>${vstatus.index+1}</td>
                <td>${bean.name}</td>
                <td><j:switch value="${bean.status}">
                    <j:case value="0">未上传</j:case>
                    <j:case value="1">已上传</j:case>
                    <j:case value="2">已提交</j:case>
                    <j:case value="3">被驳回</j:case>
                    <j:case value="4">通过</j:case>
                  </j:switch></td>
                <td>
                <j:switch value="${bean.status}">
                    <j:case value="0"></j:case>
                    <j:case value="1"></j:case>
                    <j:case value="2">
                      <a href="${ctx}/coach/courItem/edit/4/${bean.fdId}">审批</a>
                    </j:case>
                    <j:case value="3">
                      <a href="${ctx}/coach/courItem/view/4/${bean.fdId}">查看</a>
                      <a href="${ctx}/coach/courItem/edit/4/${bean.fdId}">修改评语</a>
                    </j:case>
                    <j:case value="4">
                      <a href="${ctx}/coach/courItem/view/4/${bean.fdId}">查看</a>
                      <a href="${ctx}/coach/courItem/edit/4/${bean.fdId}">修改评语</a>
                    </j:case>
                  </j:switch></td>
              </tr>
            </j:iter>
          </tbody>
        </table>
      </div>
    </section>
  </form>
</body>
</html>