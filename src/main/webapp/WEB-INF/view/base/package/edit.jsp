<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript">
	function submitPackage() {
		document.form.method = "post";
		document.form.action = '${ctx}/coach/package/save';
		document.form.submit();
		return;
	}
</script>
</head>
<body>
  <form class="form-horizontal" method="post" action="" name="form">
    <input type="hidden" name="fdId" value="${bamPackage.fdId}" /> <input type="hidden" name="phase.fdId"
      value="${bamPackage.phase.fdId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/coach/package/list" class="tile-return">返回上页</a>
          <h4>学术作业审核</h4>
          <p>在本模块中，您可以审核新教师提交的学术作业。</p>
        </div>
        <j:set name="show" value="true" />
        <j:iter items="${bamPackage.bamOperation}" var="bean2">
          <j:if test="${bean2.through eq false}">
            <j:set name="show" value="false" />
          </j:if>
        </j:iter>
        <table class="table">
          <tr>
            <th width="10%">新教师</th>
            <td colspan="3">${person.realName}</td>
          </tr>
          <tr>
            <th width="10%">作业包</th>
            <td colspan="3">${bamPackage.fdName}</td>
          </tr>
          <tr>
            <th>总分</th>
            <td colspan="3">${tValue}&nbsp;分</td>
          </tr>
          <j:if test="${show eq true}">
            <j:ifelse test="${bamPackage.through eq true}">
              <j:then>
                <tr>
                  <th>状态</th>
                  <td colspan="3"><j:ifelse test="${bamPackage.through eq true}">
                      <j:then>通过</j:then>
                      <j:else>驳回</j:else>
                    </j:ifelse></td>
                </tr>
               <tr>
                  <input type="hidden" name="through" value="true"/>
                  <th>评价</th>
                  <td colspan="3"><textarea maxlength="1000" name="fdComment" id="fdComment" rows="6" cols="80" class="span7">${bamPackage.fdComment}</textarea>
                  <input type="hidden" name="through" value="1"/>
                  </td>
                </tr>
                 <tr>
		            <th width="10%">打包下载</th>
		            <td colspan="3">
		            	<a href="${ctx }/coach/package/data/o_export?fdId=${bamPackage.fdId}">下载</a>
		            </td>
		          </tr>
		            <tr>
                  <td colspan="4"><a class="btn btn-info" value="提交" id="saveBtn" onclick="submitPackage()">确定</a></td>
                </tr>
              </j:then>
              <j:else>
                 <tr>
                  <input type="hidden" name="through" value="true"/>
                  <th>评价</th>
                  <td colspan="3"><textarea maxlength="1000" name="fdComment" id="fdComment" rows="6" cols="80" class="span7">${bamPackage.fdComment}</textarea>
                  <input type="hidden" name="through" value="1"/>
                  </td>
                </tr>
                <tr>
                  <td colspan="4"><a class="btn btn-info" value="提交" id="saveBtn" onclick="submitPackage()">确定</a></td>
                </tr>
              </j:else>
            </j:ifelse>
          </j:if>
        </table>
        <table class="table">
          <thead>
            <tr>
              <th width="10px">#</th>
              <th>成果指标</th>
              <th width="20%">教研步骤</th>
              <th width="10%">状态</th>
              <th width="10%">分数</th>
              <th width="15%">操作</th>
            </tr>
          </thead>
          <j:iter items="${bamIndexList}" var="bean" status="vstatus">
            <tr>
              <td>${vstatus.index + 1}</td>
              <td>${bean.fdIndexName}</td>
              <td>${bean.bamOperation.fdName}</td>
              <td><j:switch value="${bean.status}">
                  <j:case value="0"><span class="text-warning">未上传</span></j:case>
                  <j:case value="1"><span class="text-info">已上传</span></j:case>
                  <j:case value="2"><span class="text-info">已提交</span></j:case>
                  <j:case value="3"><span class="text-error">被驳回</span></j:case>
                  <j:case value="4"><span class="text-success">通过</span></j:case>
                </j:switch></td>
                <td>${bean.fdToValue}</td>
              <td><j:switch value="${bean.status}">
                  <j:case value="0"></j:case>
                  <j:case value="1"></j:case>
                  <j:case value="2">
                    <a href="${ctx}/coach/index/edit/${bean.fdId}">审核</a>
                  </j:case>
                  <j:case value="3">
                    <a href="${ctx}/coach/index/view/${bean.fdId}">查看</a>
                    <a href="${ctx}/coach/index/edit/${bean.fdId}">修改分数</a>
                  </j:case>
                  <j:case value="4">
                    <a href="${ctx}/coach/index/view/${bean.fdId}">查看</a>
                    <a href="${ctx}/coach/index/edit/${bean.fdId}">修改分数</a>
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