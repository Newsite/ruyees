<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<script type="text/javascript"
	src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
</head>
<body>
	<div class="well">
		<div class="page-header">
			<a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
			<h4>我的消息</h4>
			<p>在本模块中，您可以查阅发出的消息内容。</p>
		</div>
		<p>
		<div class="form-inline">
			<label>消息类型</label>
			<div class="btn-group">
				<a href="${ctx}/notify/list/0/ALL" class="btn btn-info"> <i
					class="icon-circle-arrow-up icon-white"></i>&nbsp;已发送消息
				</a> <a href="${ctx}/notify/list/1/ALL" class="btn btn-info"> <i
					class="icon-circle-arrow-down icon-white"></i>&nbsp;已接收消息
				</a>
			</div>
		</div>
		</p>
		<div id="pk_list">
			<j:iter items="${page.list}" var="bean" status="vstatus">
				<div class="alert alert-success">
					<!-- <button type="button" class="close" data-dismiss="alert">×</button> -->
					<dl class="dl-horizontal">
						<dt>主题：</dt>
						<dd>${bean.title}</dd>
						<dt>时间： </dt>
						<dd>${bean.sendDate}</dd>
						<dt>接受者：</dt>
						<dd>
						<j:iter items="${bean.acceptUser}" var="bean2" status="s">
									${bean2.person.realName}
						</j:iter>
						</dd>
						<dt>内容：</dt>
						<dd>${bean.body}</dd>
					</dl>
				</div>
			</j:iter>
		</div>
		<tags:pagination page="${page}" paginationSize="5" />
	</div>
	<script src="${ctx}/resources/js/jquery.jalert.js"
		type="text/javascript"></script>
</body>
</html>