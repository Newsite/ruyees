<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx }/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx }/resources/css/shtk.css" />
<script type="text/javascript" src="${ctx}/resources/js/lhgdialog.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;

	api.button({
		id : 'valueOk',
		name : '确定',
		callback : ok
	});

	function ok() {
	};

	function add(id, name) {
		jQuery.ajax({
			type : "post",
			dataType : "json",
			url : "${ctx}/ajax/operation/checkHasContent",
			data : {
				key : id,
				id  :'${cid}'
			},
			success : function(data) {
				if(data){
					alert('此作业包已经被引用');
				}else{
					W.add(id, name, '${itemindex}', '${contentId}', '${method}');		
				}
			}
		});
	}
</script>
<script type="text/javascript">
	function query() {
		// var fdSubject =$('#fdNameQuery').val();
		document.form1.method = "get";
		document.form1.action = '${ctx}/common/operation';
		document.form1.submit();
		return;
	}
</script>
</head>
<body>
  <section id="search-query">
  <div class="well">
    <div class="bk-pane">
      <form class="query" name="form1">
        <input type="hidden" name="method" value="${method}" /> <input type="hidden" name="itemindex"
          value="${itemindex}" /> <input type="hidden" name="contentId" value="${contentId}" />
        <table class="table ">
          <tr class="vmid">
            <th width="20%">作业包名称</th>
            <td><input type="text" id="fdNameQuery" name="fdName" value="${fdName}" /></td>
            <td><a type="button" class="btn" onclick="query()"><i class="icon-search"></i>&nbsp;查询</a></td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  </section>
  <section id="exam-list">
  <div class="well">
    <form class="form-horizontal" id="inputForm" action="" name="form">
      <table>
        <tr>
          <th><font color="red">作业包分值必须是100分！</font></th>
        </tr>
      </table>
      <table class="table">
        <thead>
          <tr>
            <th width="10px">#</th>
            <th>作业包名称</th>
            <th>创建日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <j:iter items="${page.list}" var="bean" status="vstatus">
            <tr>
              <td>${vstatus.index+1}</td>
              <td>${bean.FDNAME}</td>
              <td>${fn:substring(bean.FDCREATETIME, 0, 19)}</td>
              <td><a onclick="add('${bean.FDID}','${bean.FDNAME}')" style="cursor: pointer;">添加</a></td>
            </tr>
          </j:iter>
        </tbody>
      </table>
      <tags:pagination page="${page}" searchParams="fdName=${fdName }&contentId=${contentId}&itemindex=${itemindex}&cid=${cid }" paginationSize="5" />
    </form>
  </div>
  </section>
</body>
</html>