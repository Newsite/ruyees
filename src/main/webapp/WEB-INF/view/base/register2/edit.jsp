<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="shortcut icon" href="${ctx}/resources/img/favicon.ico" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/register.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css"></link>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/uploadify/jquery.uploadify-3.1.min.js?id=22"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/bootstrap/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.jalert.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/validate2.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#birth-input').datepicker({
		format : 'yyyy-mm-dd'
	});
});
</script>
<script type="text/javascript">
jQuery(document).ready(function() {
	jQuery("#inputForm").validate();
	return;
});
</script>
</head>
<body>
<div class="container">
<div class="row">
<div class="span8 offset2">
<div class="head">
<a herf="#" class="logo"></a>
</div>
<div class="well reg-pane">
<div class="page-header">
	<a href="${ctx}/dashboard" class="tile-return">返回桌面</a>
	<h3 style="font-weight:normal;">新教师注册</h3>
</div>
<form class="form-horizontal" method="post" id="inputForm" action="${ctx}/register2/save" name="form">
<input type="hidden" name="fdId" value="${bean.fdId}"/>
<input type="hidden" name="fdPassword" value="${bean.fdPassword}" />
<c:if test="${not empty bean }">
<fieldset>
	<div class="control-group">
	  <p>请认真填写以下资料，方便指导教师、学校主管和集团主管老师们更加了解您。</p>
	  <p style="height:20px;"></p>
	  <legend>基本信息 </legend>
	</div>
	<div class="control-group face-row">
		<label class="control-label"><span class="text-error">*</span> 头像</label>
		<div class="controls">
			<img id="imgshow" src="${bean.fdIcoUrl }" class="img-polaroid img-face"/>
			 <tags:icoupload fullname="fdIcoUrl" fullnameid="fdIcoUrl" folder="Folder" imgshow="imgshow"
              	filename="fdIcoUrl" filevalue="" fullvalue="${bean.fdIcoUrl }" id="upIcon">
            </tags:icoupload>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="usr-input"><span class="text-error">*</span> 姓名</label>
		<div class="controls">
			<input type="text" name="notifyEntity.realName" value="${bean.notifyEntity.realName}" id="usr-input" class="input-xlarge required" maxlength="20" placeholder="请输入您的姓名" />
		</div>
	</div>
	<div class="control-group">
	  <label class="control-label" for="ID-input"><span class="text-error">*</span> 身份证号码</label>
	  <div class="controls">
	    <input type="text" name="fdIdentityCard" value="${bean.fdIdentityCard}" id="ID-input" class="input-xlarge required isIdCardNo" maxlength="18" placeholder="请输入您的身份证号码" />
	  </div>
	</div>
	<!-- <div class="control-group">
	  <label class="control-label" for="pwd-input">密码</label>
	  <div class="controls">
	    <input type="password" id="plainPassword" name="fdPassword" class="input-xlarge" placeholder="请输入您的密码" />
	  </div>
	</div>
	<div class="control-group">
	  <label class="control-label" for="pwd-input">确认密码</label>
	  <div class="controls">
	    <input type="password" id="confirmPassword" name="confirmPassword" equalTo="#plainPassword" class="input-xlarge" placeholder="请确认您的密码" />
	  </div>
	</div> -->
	<div class="control-group">
	  <label class="control-label" for="pwd-input"><span class="text-error">*</span>您在新东方那个机构？</label>
	  <div class="controls">
	     <select name="depatId" class="required stime input-small">
	     	<option value="">请选择</option>	
	     <c:forEach items="${elements }" var="e">
	     	<c:if test="${e.fdId eq bean.depatId }">
	     		<option value="${e.fdId }" selected="selected">${e.fdName }</option>
	     	</c:if>
	     	<c:if test="${e.fdId ne bean.depatId }">
	     	<option value="${e.fdId }">${e.fdName }</option>
	     	</c:if>
	     </c:forEach>
      	</select>
	  </div>
	</div>
	<div class="control-group">
	  <label class="control-label"><span class="text-error">*</span>性别</label>
	  <div class="controls">
	    <labal class="radio inline">
	    <input type="radio" id="male" name="fdSex" value="M" <j:if test="${bean.fdSex=='M'}">checked</j:if> /> 男</labal>
	    <labal class="radio inline">
	    <input type="radio" id="female" name="fdSex" value="F" <j:if test="${bean.fdSex=='F'}">checked</j:if> /> 女</labal>
	  </div>
	</div>
	<div class="control-group">
	  <label class="control-label" for="birth-input"><span class="text-error">*</span> 生日</label>
	  <div class="controls">
	    <input type="text" name="fdBirthDay" value="${bean.fdBirthDay}" id="birth-input" class="input-xlarge required" placeholder="请输入您的出生日期" />
	  </div>
	</div>
  <div class="control-group">
    <label class="control-label"><span class="text-error">*</span> 血型</label>
    <div class="controls">
      <labal class="radio inline">A<input type="radio" id="A" name="fdBloodType" value="A" <j:if test="${bean.fdBloodType =='A'}">checked</j:if> /></labal>
      <labal class="radio inline">B<input type="radio" id="B" name="fdBloodType" value="B" <j:if test="${bean.fdBloodType =='B'}">checked</j:if> /></labal>
      <labal class="radio inline">AB<input type="radio" id="AB" name="fdBloodType" value="AB" <j:if test="${bean.fdBloodType =='AB'}">checked</j:if> /></labal>
      <labal class="radio inline">O<input type="radio" id="O" name="fdBloodType" value="O" <j:if test="${bean.fdBloodType =='O'}">checked</j:if> /></labal>
      <labal class="radio inline">不详<input type="radio" id="OTHER" name="fdBloodType" value="OTHER" <j:if test="${bean.fdBloodType == 'OTHER'}">checked</j:if> /></labal>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="wbid-input">微博ID</label>
    <div class="controls">
      <input type="text" name="fdMicroBlog" value="${bean.fdMicroBlog}" id="wbid-input" class="input-xlarge" maxlength="20" placeholder="请输入您的微博ID" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mail-input"><span class="text-error">*</span> 个人邮箱</label>
    <div class="controls">
      <input type="text" name="notifyEntity.fdEmail" value="${bean.notifyEntity.fdEmail}" id="mail-input" class="input-xlarge required email" placeholder="请输入您的个人邮箱" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mobl-input"><span class="text-error">*</span> 手机</label>
    <div class="controls">
      <input type="text" name="notifyEntity.fdMobileNo" value="${bean.notifyEntity.fdMobileNo}" id="mobl-input" class="input-xlarge required isMobile" maxlength="11" placeholder="请输入您的手机号码" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="award-input">所获奖项/所持证书</label>
    <div class="controls">
      <input type="text" name="fdCertificate" value="${bean.fdCertificate}" id="award-input" class="input-xlarge" maxlength="50" placeholder="请输入您所获奖项或所持证书" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label"><span class="text-error">*</span> 爱好/特长</label>
    <div class="controls">
      	我擅长/喜欢&nbsp;<input type="text" name="fdHobby" value="${bean.fdHobby}" class="span2 required input-underline" maxlength="20"/>， 我曾经&nbsp;
      	<input type="text" name="fdOldHobby" value="${bean.fdOldHobby}" class="span2 required input-underline" maxlength="50"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="pride-input"><span class="text-error">*</span> 最自豪的事情</label>
    <div class="controls">
      <input type="text" name="fdPride" value="${bean.fdPride}"  id="pride-input" class="required input-xlarge" maxlength="100" placeholder="请输入您最自豪的事情" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="regret-input"><span class="text-error">*</span> 最遗憾的事情</label>
    <div class="controls">
      <input type="text" name="fdPity" value="${bean.fdPity}" id="regret-input" class="required input-xlarge" maxlength="100" placeholder="请输入您最遗憾的事情" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="dream-input"><span class="text-error">*</span> 最梦想实现的事情</label>
    <div class="controls">
      <input type="text" name="fdDram" value="${bean.fdDram}" id="dream-input" class="required input-xlarge" maxlength="100" placeholder="请输入您最梦想实现的事情" />
    </div>
  </div>
</fieldset>

<fieldset id="jobHy">
  <legend>工作经历 </legend>
  <div id="jobHy-list" class="list-alert">
  <%--工作经历列表 --%>
   <j:iter items="${bean.workHistory}" var="work" status="vs1">
	  <div class="alert alert-info fade in">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  <dl class="dl-horizontal">
		    <input type="hidden" name="workHistory[${vs1.index}].fdId" value="${work.fdId}"/>
		    <input type="hidden" name="workHistory[${vs1.index}].orgName" value="${work.orgName}"/>
		    <input type="hidden" name="workHistory[${vs1.index}].deptName" value="${work.deptName}"/>
		    <input type="hidden" name="workHistory[${vs1.index}].beginYear" value="${work.beginYear}"/>
		    <input type="hidden" name="workHistory[${vs1.index}].endYear" value="${work.endYear}"/>
		    <input type="hidden" name="workHistory[${vs1.index}].product" value="${work.product}"/>
		    <dt>机构名称</dt>
		    <dd>${work.orgName}</dd>
		    <dt>部门/职位</dt>
		    <dd>${work.deptName}</dd>
		    <dt>工作时间</dt>
		    <dd>${work.beginYear}&nbsp;-&nbsp;${work.endYear}</dd>
		    <dt>工作成果</dt>
		    <dd>${work.product}</dd>
		  </dl>
	  </div>
   </j:iter>
  </div>
  <div class="control-group">
    <label class="control-label" for="org-input">机构名称 </label>
    <div class="controls">
      <input type="text" name="workHistory.orgName" id="org-input" class="input-xlarge" maxlength="50" placeholder="请输入您的机构名称 " />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="pos-input">部门/职位 </label>
    <div class="controls">
      <input type="text" name="workHistory.deptName" id="pos-input" class="input-xlarge" maxlength="50" placeholder="请输入您的部门或职位 " />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">工作时间 </label>
    <div class="controls">
      <span class="rsp"> 
      <select name="workHistory.beginYear" class="stime input-small">
          <option value="">请选择</option>
          <option value="2013">2013</option>
          <option value="2012">2012</option>
          <option value="2011">2011</option>
          <option value="2010">2010</option>
          <option value="2009">2009</option>
          <option value="2008">2008</option>
          <option value="2007">2007</option>
          <option value="2006">2006</option>
          <option value="2005">2005</option>
          <option value="2004">2004</option>
          <option value="2003">2003</option>
          <option value="2002">2002</option>
          <option value="2001">2001</option>
          <option value="2000">2000</option>
          <option value="1999">1999</option>
          <option value="1998">1998</option>
          <option value="1997">1997</option>
          <option value="1996">1996</option>
          <option value="1995">1995</option>
          <option value="1994">1994</option>
          <option value="1993">1993</option>
          <option value="1992">1992</option>
          <option value="1991">1991</option>
          <option value="1990">1990</option>
          <option value="1989">1989</option>
          <option value="1988">1988</option>
          <option value="1987">1987</option>
          <option value="1986">1986</option>
          <option value="1985">1985</option>
          <option value="1984">1984</option>
          <option value="1983">1983</option>
          <option value="1982">1982</option>
          <option value="1981">1981</option>
          <option value="1980">1980</option>
          <option value="1979">1979</option>
          <option value="1978">1978</option>
          <option value="1977">1977</option>
          <option value="1976">1976</option>
          <option value="1975">1975</option>
          <option value="1974">1974</option>
          <option value="1973">1973</option>
          <option value="1972">1972</option>
          <option value="1971">1971</option>
          <option value="1970">1970</option>
          <option value="1969">1969</option>
          <option value="1968">1968</option>
          <option value="1967">1967</option>
          <option value="1966">1966</option>
          <option value="1965">1965</option>
          <option value="1964">1964</option>
          <option value="1963">1963</option>
          <option value="1962">1962</option>
          <option value="1961">1961</option>
          <option value="1960">1960</option>
          <option value="1959">1959</option>
          <option value="1958">1958</option>
          <option value="1957">1957</option>
          <option value="1956">1956</option>
          <option value="1955">1955</option>
          <option value="1954">1954</option>
          <option value="1953">1953</option>
          <option value="1952">1952</option>
          <option value="1951">1951</option>
          <option value="1950">1950</option>
          <option value="1949">1949</option>
          <option value="1948">1948</option>
          <option value="1947">1947</option>
          <option value="1946">1946</option>
          <option value="1945">1945</option>
          <option value="1944">1944</option>
          <option value="1943">1943</option>
          <option value="1942">1942</option>
          <option value="1941">1941</option>
          <option value="1940">1940</option>
          <option value="1939">1939</option>
          <option value="1938">1938</option>
          <option value="1937">1937</option>
          <option value="1936">1936</option>
          <option value="1935">1935</option>
          <option value="1934">1934</option>
          <option value="1933">1933</option>
          <option value="1932">1932</option>
          <option value="1931">1931</option>
          <option value="1930">1930</option>
          <option value="1929">1929</option>
          <option value="1928">1928</option>
          <option value="1927">1927</option>
          <option value="1926">1926</option>
          <option value="1925">1925</option>
          <option value="1924">1924</option>
          <option value="1923">1923</option>
          <option value="1922">1922</option>
          <option value="1921">1921</option>
          <option value="1920">1920</option>
          <option value="1919">1919</option>
          <option value="1918">1918</option>
          <option value="1917">1917</option>
          <option value="1916">1916</option>
          <option value="1915">1915</option>
          <option value="1914">1914</option>
          <option value="1913">1913</option>
          <option value="1912">1912</option>
          <option value="1911">1911</option>
          <option value="1910">1910</option>
          <option value="1909">1909</option>
          <option value="1908">1908</option>
          <option value="1907">1907</option>
          <option value="1906">1906</option>
          <option value="1905">1905</option>
          <option value="1904">1904</option>
          <option value="1903">1903</option>
          <option value="1902">1902</option>
          <option value="1901">1901</option>
          <option value="1900">1900</option>
      </select> &nbsp;至
      </span> <span class="rsp"> 
      <select name="workHistory.endYear" class="etime  input-small">
          <option value="">请选择</option>
          <option value="2013">2013</option>
          <option value="2012">2012</option>
          <option value="2011">2011</option>
          <option value="2010">2010</option>
          <option value="2009">2009</option>
          <option value="2008">2008</option>
          <option value="2007">2007</option>
          <option value="2006">2006</option>
          <option value="2005">2005</option>
          <option value="2004">2004</option>
          <option value="2003">2003</option>
          <option value="2002">2002</option>
          <option value="2001">2001</option>
          <option value="2000">2000</option>
          <option value="1999">1999</option>
          <option value="1998">1998</option>
          <option value="1997">1997</option>
          <option value="1996">1996</option>
          <option value="1995">1995</option>
          <option value="1994">1994</option>
          <option value="1993">1993</option>
          <option value="1992">1992</option>
          <option value="1991">1991</option>
          <option value="1990">1990</option>
          <option value="1989">1989</option>
          <option value="1988">1988</option>
          <option value="1987">1987</option>
          <option value="1986">1986</option>
          <option value="1985">1985</option>
          <option value="1984">1984</option>
          <option value="1983">1983</option>
          <option value="1982">1982</option>
          <option value="1981">1981</option>
          <option value="1980">1980</option>
          <option value="1979">1979</option>
          <option value="1978">1978</option>
          <option value="1977">1977</option>
          <option value="1976">1976</option>
          <option value="1975">1975</option>
          <option value="1974">1974</option>
          <option value="1973">1973</option>
          <option value="1972">1972</option>
          <option value="1971">1971</option>
          <option value="1970">1970</option>
          <option value="1969">1969</option>
          <option value="1968">1968</option>
          <option value="1967">1967</option>
          <option value="1966">1966</option>
          <option value="1965">1965</option>
          <option value="1964">1964</option>
          <option value="1963">1963</option>
          <option value="1962">1962</option>
          <option value="1961">1961</option>
          <option value="1960">1960</option>
          <option value="1959">1959</option>
          <option value="1958">1958</option>
          <option value="1957">1957</option>
          <option value="1956">1956</option>
          <option value="1955">1955</option>
          <option value="1954">1954</option>
          <option value="1953">1953</option>
          <option value="1952">1952</option>
          <option value="1951">1951</option>
          <option value="1950">1950</option>
          <option value="1949">1949</option>
          <option value="1948">1948</option>
          <option value="1947">1947</option>
          <option value="1946">1946</option>
          <option value="1945">1945</option>
          <option value="1944">1944</option>
          <option value="1943">1943</option>
          <option value="1942">1942</option>
          <option value="1941">1941</option>
          <option value="1940">1940</option>
          <option value="1939">1939</option>
          <option value="1938">1938</option>
          <option value="1937">1937</option>
          <option value="1936">1936</option>
          <option value="1935">1935</option>
          <option value="1934">1934</option>
          <option value="1933">1933</option>
          <option value="1932">1932</option>
          <option value="1931">1931</option>
          <option value="1930">1930</option>
          <option value="1929">1929</option>
          <option value="1928">1928</option>
          <option value="1927">1927</option>
          <option value="1926">1926</option>
          <option value="1925">1925</option>
          <option value="1924">1924</option>
          <option value="1923">1923</option>
          <option value="1922">1922</option>
          <option value="1921">1921</option>
          <option value="1920">1920</option>
          <option value="1919">1919</option>
          <option value="1918">1918</option>
          <option value="1917">1917</option>
          <option value="1916">1916</option>
          <option value="1915">1915</option>
          <option value="1914">1914</option>
          <option value="1913">1913</option>
          <option value="1912">1912</option>
          <option value="1911">1911</option>
          <option value="1910">1910</option>
          <option value="1909">1909</option>
          <option value="1908">1908</option>
          <option value="1907">1907</option>
          <option value="1906">1906</option>
          <option value="1905">1905</option>
          <option value="1904">1904</option>
          <option value="1903">1903</option>
          <option value="1902">1902</option>
          <option value="1901">1901</option>
          <option value="1900">1900</option>
      </select></span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="result-input">工作成果</label>
    <div class="controls">
      <input type="text" name="product" id="result-input" class="input-xlarge" maxlength="100" placeholder="请输入您的工作成果" />
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button class="btn btn-small btn-submit" type="button"><i class="icon-plus-sign"></i>&nbsp;添加</button>
    </div>
  </div>
</fieldset>

<fieldset id="eduBg">
  <legend>教育背景 </legend>
  <div id="eduBg-list" class="list-alert">
  <%--教育背景列表 --%>
  <j:iter items="${bean.education}" var="edu" status="vs2">
	  <div class="alert alert-info fade in">
	  <button type="button" class="close" data-dismiss="alert">×</button>
	  <dl class="dl-horizontal">
	    <input type="hidden" name="education[${vs2.index}].fdId" value="${edu.fdId}"/>
	    <input type="hidden" name="education[${vs2.index}].schoolName" value="${edu.schoolName}"/>
	    <input type="hidden" name="education[${vs2.index}].beginYear" value="${edu.beginYear}"/>
	    <input type="hidden" name="education[${vs2.index}].majorName" value="${edu.majorName}"/>
	    <dt>学校名称</dt>
	    <dd>${edu.schoolName}</dd>
	    <dt>入学时间</dt>
	    <dd>${edu.beginYear}</dd>
	    <dt>专业名称</dt>
	    <dd>${edu.majorName}</dd>
	  </dl>
	  </div>
  </j:iter>
  </div>
  <div class="control-group">
    <label class="control-label" for="sch-input">学校名称</label>
    <div class="controls">
      <input type="text" name="education.schoolName" id="sch-input" class="input-xlarge" maxlength="50" placeholder="请输入您的学校名称 " />
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">入学时间</label>
    <div class="controls">
      <span class="rsp"> 
      <select name="education.beginYear" class="stime input-small">
          <option value="">请选择</option>
          <option value="2013">2013</option>
          <option value="2012">2012</option>
          <option value="2011">2011</option>
          <option value="2010">2010</option>
          <option value="2009">2009</option>
          <option value="2008">2008</option>
          <option value="2007">2007</option>
          <option value="2006">2006</option>
          <option value="2005">2005</option>
          <option value="2004">2004</option>
          <option value="2003">2003</option>
          <option value="2002">2002</option>
          <option value="2001">2001</option>
          <option value="2000">2000</option>
          <option value="1999">1999</option>
          <option value="1998">1998</option>
          <option value="1997">1997</option>
          <option value="1996">1996</option>
          <option value="1995">1995</option>
          <option value="1994">1994</option>
          <option value="1993">1993</option>
          <option value="1992">1992</option>
          <option value="1991">1991</option>
          <option value="1990">1990</option>
          <option value="1989">1989</option>
          <option value="1988">1988</option>
          <option value="1987">1987</option>
          <option value="1986">1986</option>
          <option value="1985">1985</option>
          <option value="1984">1984</option>
          <option value="1983">1983</option>
          <option value="1982">1982</option>
          <option value="1981">1981</option>
          <option value="1980">1980</option>
          <option value="1979">1979</option>
          <option value="1978">1978</option>
          <option value="1977">1977</option>
      </select>
      </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="major-input">专业名称</label>
    <div class="controls">
      <input type="text" name="education.majorName" id="major-input" class="input-xlarge" maxlength="50" placeholder="请输入您的专业名称" />
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button class="btn btn-small btn-submit" type="button"><i class="icon-plus-sign"></i>&nbsp;添加</button>
    </div>
  </div>
</fieldset>
<div class="page-footer">
  <button type="submit" class="btn btn-info btn-sign-up"><i class="icon-ok-circle icon-white"></i>&nbsp;确定</button>
</div>
</c:if>
<c:if test="${empty bean }">
	<a target="_blank" href="http://me.xdf.cn/iportal/ixdf/auth/ixdf_auth_person/ixdfAuthPerson.do?method=editProfile&s_css=default">请点击链接进入ixdf平台修改</a>
</c:if>
</form>
</div>
</div>
</div>
</div>
<script type="text/javascript">
//jQuery(':input:visible:enabled:first').focus();
$(function(){
	var $obj = $("#eduBg");
	var $sch_inp = $("#sch-input");
	var $stime = $obj.find(".stime");
	var $major_inp = $("#major-input");	
	var $submit = $obj.find(".btn-submit");
	var $list = $obj.find(".list-alert");
	var $item;
	
	$submit.bind("click",function(){
		var rows = $("#eduBg-list").children(".alert").length;
		
		$item = $('<div class="alert alert-info fade in">\
           		<button type="button" class="close" data-dismiss="alert">×</button>\
           		<dl class="dl-horizontal">\
           			<dt>学校名称</dt>\
           			<dd>'+ $sch_inp.val() +'<input type="hidden" name="education['+rows+'].schoolName" value="'+ $sch_inp.val()+'"/></dd>\
           			<dt>入学时间</dt>\
           			<dd>'+ $stime.val() +'<input type="hidden" name="education['+rows+'].beginYear" value="'+ $stime.val()+'"/></dd>\
           			<dt>专业名称</dt>\
           			<dd>'+ $major_inp.val() +'<input type="hidden" name="education['+rows+'].majorName" value="'+ $major_inp.val()+'"/></dd>\
           		</dl>\
           	</div>');
		$list.append($item);
		//提交后置空
		$sch_inp.val("");
		$stime.val("");
		$major_inp.val("");
	});
});

$(function(){
	var $obj = $("#jobHy");
	var $org_inp = $("#org-input");
	var $pos_inp = $("#pos-input");
	var $stime = $obj.find(".stime");
	var $etime = $obj.find(".etime");
	var $result_inp = $("#result-input");	
	var $submit = $obj.find(".btn-submit");
	var $list = $obj.find(".list-alert");
	
	var $item;
	
	$submit.bind("click",function(){
		//时间比较 2013>2012
		if ($stime.val() > $etime.val()) {
			$.fn.jalert2("开始时间不能大于结束时间！");
			return false;
		}
		var rows = $("#jobHy-list").children(".alert").length;
		
		$item = $('<div class="alert alert-info fade in">\
           		<button type="button" class="close" data-dismiss="alert">×</button>\
           		<dl class="dl-horizontal">\
           			<dt>机构名称</dt>\
           			<dd>'+ $org_inp.val() +'<input type="hidden" name="workHistory['+rows+'].orgName" value="'+ $org_inp.val()+'"/></dd>\
           			<dt>部门/职位</dt>\
           			<dd>'+ $pos_inp.val() +'<input type="hidden" name="workHistory['+rows+'].deptName" value="'+ $pos_inp.val()+'"/></dd>\
           			<dt>工作时间</dt>\
           			<dd>'+ $stime.val() +' - '+ $etime.val() +'<input type="hidden" name="workHistory['+rows+'].beginYear" value="'+ $stime.val()+'"/><input type="hidden" name="workHistory['+rows+'].endYear" value="'+ $etime.val()+'"/></dd>\
           			<dt>工作成果</dt>\
           			<dd>'+ $result_inp.val() +'<input type="hidden" name="workHistory['+rows+'].product" value="'+ $result_inp.val()+'"/></dd>\
           		</dl>\
           	</div>');
		$list.append($item);
		//提交后置空
		$org_inp.val("");
		$pos_inp.val("");
		$stime.val("");
		$result_inp.val("");
	});
});
</script>
</body>
</html>