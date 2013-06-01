<%@page pageEncoding="UTF-8"%><%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%><%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%><j:set name="ctx" value="${pageContext.request.contextPath}" /><!DOCTYPE html><html lang="zh_CN"><head><link rel="stylesheet" href="${ctx}/resources/css/jquery.autocomplete.css" /><link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css"></link><script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=22"></script><script type="text/javascript" src="${ctx }/resources/js/lib/plugin/addUser/jquery.autocomplete.pack.js"></script><script type="text/javascript" src="${ctx }/resources/js/jquery.jalert.js"></script><script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script><script type="text/javascript" src="${ctx}/resources/js/validate2.js"></script><style type="text/css">#adduser {  background-color: #FFF;  padding: 10px 20px;}#adduser .u_list>tbody>tr a {  cursor: pointer;  text-decoration: none}#inpUser {  width: 400px;}</style><script type="text/javascript">	jQuery(document).ready(function() {		jQuery("#inputForm").validate();		return;	});</script><script type="text/javascript">	$().ready(function() {		$("#inpOrg").autocomplete("${ctx}/ajax/org/findByOrgIdAndDeptName", {			matchContains : true,			max : 100,			width : 410,			dataType : 'json',			extraParams : {				q : function() {return encodeURI($("#inpOrg").val());},				orgId : function() { return $('#schId').val();}			},			//加入对返回的json对象进行解析的函数，函数返回一个数组			parse : function(data) {				var rows = [];				for ( var i = 0; i < data.length; i++) {					rows[rows.length] = {						data : data[i],						value : data[i].fdName,						result : data[i].fdName					//显示在输入文本框里的内容 ,					};				}				return rows;			},			formatItem : function(item) {				return item.fdName;			}		}).result(function(event, item) {			name = item.fdName;			id = item.fdId;			$("#inpOrg").val("");			$("#deptId").val(id);			$("#deptName").val(name);			$("#deptName").trigger("blur");		});		$("#removeOrg").click(function() {			$("#inpOrg").val("");			$("#deptId").val("");			$("#deptName").val("");		});		$("#fdIdentityCard").blur(function() {			checkIdentityCard($("#fdIdentityCard").val());		});	});	function checkIdentityCard(str) {		if (str == null || str == "") {			$("#fdIdentityCardLabel").hide();			return false;		}		$.ajax({			type : "post",			dataType : "json",			url : "${ctx}/ajax/register/checkIdentityCard",			data : {				str : str			},			success : function(data) {				if (data > 0) {					$("#fdIdentityCardLabel").html(							"This Identity Card number has been registered.").show();					$("#fdIdentityCard").val("");				} else {					$("#fdIdentityCardLabel").hide();				}			}		});	}</script></head><body><form class="form-horizontal" method="post" id="inputForm" action="${ctx}/register/save" name="form"><input name="fdIsEmp" type="hidden" value="0" /> <input name="isRegistered" type="hidden" value="0" /> <input id="deptId" name="deptId" type="hidden"  value="" /><input type="hidden" id="schId" value="${orgId}" />  <section id="set-exam">    <div class="well">      <div class="page-header">        <a href="${ctx}/register/list" class="tile-return">返回上页</a>        <h4>新教师注册</h4>        <p>在本模块中，您可以为新教师创建账号。</p>      </div>      <table class="table">        <thead>        </thead>        <tbody>        <tr>          <th width="15%">姓名 *</th>          <td><input name="notifyEntity.realName" type="text" class="span3 required" value=""  maxlength="20" /></td>        </tr>        <tr>          <th>身份证号码 *</th>          <td><input id="fdIdentityCard" name="fdIdentityCard" type="text"            class="span3 required isIdCardNo" value="" maxlength="18" /> <label id="fdIdentityCardLabel"            class="text-error" style="display: none;"></label></td>        </tr>        <tr>          <th>密码 *</th>          <td><input name="password" type="password" class="span3 required password" value=""            maxlength="15" /></td>        </tr>        <tr>          <th>性别 *</th>          <td><label class="radio inline"> 男<input type="radio" checked name="fdSex"              value="F" <j:if test="${bean.fdSex=='F'}">checked</j:if> />          </label> <label class="radio inline"> 女<input type="radio" name="fdSex" value="M"              <j:if test="${bean.fdSex=='M'}">checked</j:if> />          </label></td>        </tr>        <tr>          <th>部门 *</th>          <td><input id="inpOrg" type="text" class="span3" />            <button id="removeOrg" type="button" class="btn"><i class="icon-repeat"></i>&nbsp;重置</button> <br> <br> <input id="deptName"            name="deptName" type="text" class="span3 required" value="" readOnly /></td>        </tr>        <tr>          <th>电子邮箱 *</th>          <td><input name="notifyEntity.fdEmail" type="text" class="span3 required email" value="" />          </td>        </tr><!--        <tr>          <td>手机</td>          <td><input id="fdMobileNo" name="notifyEntity.fdMobileNo" type="text"            class="span3 isMobile" value="" maxlength="11" /></td>        </tr>        <tr>          <td>QQ</td>          <td><input name="fdQq" type="text" class="span3 qq" value="" maxlength="12" /></td>        </tr>        <tr>          <td>MSN</td>          <td><input name="fdMsn" type="text" class="span3 email" value="" /></td>        </tr>-->        <tr>          <th>上传头像</th>          <td>            <tags:icoupload fullname="fdIcoUrl" fullnameid="fdIcoUrl" folder="Folder" imgshow="imgshow"              filename="fdPhotoUrl" filevalue="" fullvalue="${bean.backgroundUrl}" id="upIcon">            </tags:icoupload>          </td>        </tr>        <tr>          <th>显示头像</th>          <td><img id="imgshow" style="width: 80px; height: 80px;" src="${ctx}/resources/images/default-avatar.jpg" /></td>        </tr>        </tbody>      </table>      <button class="btn btn-info" type="submit" value="提交" id="saveBtn"><i class="icon-ok-circle icon-white"></i>&nbsp;确定</button>    </div>  </section></form></body></html>