<%@page import="cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser"%>
<%@page import="cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser"%>
<%@page import="cn.xdf.me.otp.model.sys.RoleEnum"%>
<%@page import="cn.xdf.me.otp.utils.ShiroUtils"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/jquery.autocomplete.css" />
<script type="text/javascript" src="${ctx }/resources/js/lib/plugin/addUser/jquery.autocomplete.pack.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/jquery.jalert.js"></script>
<style type="text/css">
#adduser {
  background-color: #FFF;
  padding: 10px 20px;
}

#adduser .u_list>tbody>tr a {
  cursor: pointer;
  text-decoration: none
}

#inpUser {
  width: 400px;
}
</style>
<%
request.setAttribute("isadmin", ShiroUtils.hasRole(RoleEnum.admin));
%>
<script type="text/javascript">
	var name = "";
	var id = "";
	$()
			.ready(
					function() {
						$("#inpUser").autocomplete(
								"${ctx}/ajax/user/findByNameAndRole", {
									matchContains : true,
									max : 100,
									width : 410,
									dataType : 'json',
									extraParams : {
										q : function() {
											return $("#inpUser").val();
										}
									},
									//加入对返回的json对象进行解析的函数，函数返回一个数组
									parse : function(data) {
										var rows = [];
										for ( var i = 0; i < data.length; i++) {
											rows[rows.length] = {
												data : data[i],
												value : data[i].name,
												result : data[i].name
											//显示在输入文本框里的内容 ,
											};
										}
										return rows;
									},
									formatItem : function(item) {
										return item.name;
									}
								}).result(function(event, item) {
							id = item.id;
							name = item.name;
						});

						$("#u_list li a").click(function() {
							var idstr = $(this).attr("id");
							var athis = $(this);
							var id = idstr.substr(1);
							var act = idstr.substr(0,1);
							if(act=='a'){//添加授权
								try{
									$.ajax({
										url : "${ctx}/ajax/flow/addAss",
										type : "GET",
										dataType : "json",
										data : {
											key : id
										},
										success : function(result) {
											athis.attr("id","r"+id);
											athis.html("移出授权");
										}
									});
								}catch(e){
									alert(e.Message);
								}
							}else if(act=='r'){//移出授权
								try{
									$.ajax({
										url : "${ctx}/ajax/flow/removeAss",
										type : "GET",
										dataType : "json",
										data : {
											key : id
										},
										success : function(result) {
											athis.attr("id","a"+id);
											athis.html("添加授权");
										}
									});
								}catch(e){
									alert(e.Message);
								}
							}else if(act=='d'){//删除操作
								try{
									//disabled
									$("#saveBtn").attr('disabled',"true");
									$.ajax({
										url : "${ctx}/ajax/flow/deleteAss",
										type : "GET",
										dataType : "json",
										data : {
											key : id
										},
										success : function(result) {
											if(result){
												$("#saveBtn").removeAttr("disabled");
											}
										}
									});
								}catch(e){
									$("#saveBtn").removeAttr("disabled");
									alert(e.Message);
								}
								$(this).closest("li").remove();
							}
						});

						$("#addUser").click(function() {
									if(id==''){
										$.fn.jalert2("请选择学校主管！");
										return false;
									}
											var repeat = false;
											$("#inpUser").val("");
											$("#u_list li").each(
													function() {
														var id1 = $(this).find("input").first().val();
														if (id == id1)
															repeat = true;
													});
											if (repeat) {
												$.fn.jalert2("学校主管不能重复添加！");
											} else {
												var rows = $("#u_list")
														.children("li").length;
												$("#u_list")
														.append(
																'<li>'
																		+ name
																		+ '<input type="hidden" name="bamProjects['+rows+'].user.fdId" value="'+id+'" /><input type="hidden" name="bamProjects['+rows+'].user.realName" value="'+name+'" /> <a href="#" id="" class="text-error">移除</a></li>')
														.find("a")
														.click(
																function() {
																	$(this)
																			.closest(
																					"li")
																			.remove();
																});
											}
										});

					});
</script>
</head>
<body>
  <form class="form-horizontal" method="post" id="inputForm" action="${ctx}/group/template/assignmentSave" name="form">
    <input type="hidden" name="fdId" value="${bean.fdId}" /> <input type="hidden" name="fdName" value="${bean.fdName}" />
    <input type="hidden" name="program" value="${bean.program.fdId}" /> <input type="hidden" name="course"
      value="${bean.course.fdId}" /> <input type="hidden" name="item" value="${bean.item.fdId}" /> <input
      type="hidden" name="stage" value="${bean.stage.fdId}" /> <input type="hidden" name="fdOperationWeight"
      value="${bean.fdOperationWeight}" /> <input type="hidden" name="fdAppofClass" value="${bean.fdAppofClass}" /> <input
      type="hidden" name="description" value="${bean.description}" /> <input type="hidden" name="fdCreateorId"
      value="${bean.fdCreateorId}" />
    <section id="set-exam">
      <div class="well">
        <div class="page-header">
          <a href="${ctx}/group/template/list" class="tile-return">返回上页</a>
          <h4>备课授权</h4>
          <p>
            您可以授权以下用户组织该学校的备课活动。
          </p>
        </div>
        <p>当前已授权用户列表如下</p>
        <ol id="u_list">
          <j:iter items="${bean.bamProjects}" var="bamProject" status="vs1">
            <li>
               <input type="hidden" name="bamProjects[${vs1.index}].user.fdId" value="${bamProject.user.fdId}" />
               <input type="hidden" name="bamProjects[${vs1.index}].fdId" value="${bamProject.fdId}" />
              ${bamProject.user.realName} 
               <input type="hidden" name="bamProjects[${vs1.index}].status" value="${bamProject.status}" /> 
               <input type="hidden" name="bamProjects[${vs1.index}].user.realName" value="${bamProject.user.realName}" />
               
               <j:ifelse test="${bamProject.invalid }">
               	<j:then>
               		<a href="#" class="text-error" id="a${bamProject.fdId }">添加授权</a>
               	</j:then>
               	<j:else>
               		<j:ifelse test="${bamProject.status==1 }">
               			<a href="#" class="text-error" id="r${bamProject.fdId }">移除授权</a>
               		</j:ifelse>
               	</j:else>
               </j:ifelse>
               <j:if test="${isadmin }">
               	<a href="#" class="text-error" id="d${bamProject.fdId }">删除</a>
               </j:if>
              </li>
          </j:iter>
        </ol>
        <br />
        <p>
          授权学校主管 <input id="inpUser" type="text" class="span3" />
          <button id="addUser" type="button" class="btn">
            <i class="icon-plus-sign"></i>&nbsp;添加
          </button>
          <button class="btn btn-info" type="submit" value="提交" id="saveBtn">
            <i class="icon-ok-sign icon-white"></i>&nbsp;确定
          </button>
        </p>
        </table>
      </div>
    </section>
  </form>
</body>
</html>