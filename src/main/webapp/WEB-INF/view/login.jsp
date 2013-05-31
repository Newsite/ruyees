<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Cache-Control" content="no-cache,no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="author" content="" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="viewpoint" content="width=device-width,initial-scale=1.0" />
<title>OTP - Overseas Testing Teacher Training Platform</title>
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/login.css" />
<script src="${ctx}/resources/js/lib/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/placeholder.js" type="text/javascript"></script>
</head>
<body>
  <div class="wel-main">
    <form action="" method="post">
      <div class="login-box">
        <div class="form-inline">
          <input type="text" id="usr-input" name="username" placeholder="您的身份证号码或者集团邮箱用户名" />
        </div>
        <div class="form-inline">
          <input type="password" id="pwd-input" name="password" placeholder="请输入您的密码" />
          <button type="submit" id="loginSubmit" class="btn btn-info btn-login">Sign in</button>
          <a href="${ctx}/self_register">register</a>
        </div>
      </div>
    </form>
  </div>
  <script type="text/javascript">
  $(function(){
    if($.browser.msie && ($.browser.version == 6.0) && !$.support.style) {
      $(".login-box").html('国外考试新教师在线备课平台不支持采用微软IE 6内核的浏览器，<br />请您点击<a href="http://download.microsoft.com/download/4/C/A/4CA9248C-C09D-43D3-B627-76B0F6EBCD5E/IE9-Windows7-x86-chs.exe" class="text-info">这里</a>下载安装新版浏览器。');
    }
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    if (window.ActiveXObject){
    	Sys.ie = ua.match(/msie ([\d.]+)/)[1];
    	if (Sys.ie){
    		var sysIe =Sys.ie.substring(0, 1); 
    		if(sysIe == "6"||sysIe == "7"){
    			jQuery("#loginSubmit").attr("disabled", true);
    			$(".login-box").html('国外考试新教师在线备课平台不支持采用微软IE 8以下内核的浏览器，<br />请您点击<a href="http://download.microsoft.com/download/4/C/A/4CA9248C-C09D-43D3-B627-76B0F6EBCD5E/IE9-Windows7-x86-chs.exe" class="text-info">这里</a>下载安装新版浏览器。');
    		}
    	}	
    }else if (document.getBoxObjectFor){
    	Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1];	
    }
    else if (window.MessageEvent && !document.getBoxObjectFor){
    	Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1];	
    }
    else if (window.opera){
    	Sys.opera = ua.match(/opera.([\d.]+)/)[1];	
    }
    else if (window.openDatabase){
    	Sys.safari = ua.match(/version\/([\d.]+)/)[1];	
    }
    else{
    	$(".login-box").html('国外考试新教师在线备课平台只支持采用(微软IE8以上,Chrome,opera,safari,firefox)，<br />我们强烈推荐你下载<a href="http://www.google.cn/intl/zh-CN/chrome/browser/" class="text-info">Chrome</a>浏览器。');
    }
  });
  </script>
</body>
</html>