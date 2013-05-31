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
<link rel="stylesheet" href="${ctx}/resources/css/navbar.css" />
<script src="${ctx}/resources/js/lib/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/placeholder.js" type="text/javascript"></script>
<script src="${ctx}/resources/jwplayer/jwplayer.js" type="text/javascript"></script>
<style type="text/css">
body {
  padding-top: 40px;
  background: url("resources/images/bg.jpg");
}

#vwrap {
  background: url("resources/images/black-bg.png");
  padding: 60px 0;
}

#vplayer {
  float: left;
  margin-right: 60px;
  background: url("resources/images/bg.jpg");
}

#vplayer-info {
  color: #f8f8f8;
}

#vplayer-info p {
  padding-top: 40px;
  color: #ccc;
}

#vplayer-action {
  padding-top: 20px;
}

#vplayer-action button {
  margin-right: 10px;
}
</style>
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container pr">
        <div class="logo">
          <a href="#">XDF</a>
        </div>
        <div class="span4 top-nav">
          <ul class="nav">
            <li><a href="${ctx}/login">首页</a></li>
            <li><a>频道</a></li>
            <li><a>广场</a></li>
            <li><a>应用</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div id="vwrap">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div id="vplayer">视频加载中，请稍候...</div>
          <script type="text/javascript">
          jwplayer("vplayer").setup({
            file: "http://otp.xdf.cn/otp/resources/www/otp.mp4",
            image: "http://otp.xdf.cn/otp/resources/www/otp.png",
            modes: [
                     { type: 'html5' },
                     { type: 'flash', src: 'http://otp.xdf.cn/otp/resources/jwplayer/jwplayer.swf' }
                 ],
            // autostart: true,
            height: 450,
            width: 720
          });
          </script>
          <div id="vplayer-info">
            <h3>国外考试新教师在线备课平台</h3>
            <h5>Overseas Testing Teacher Training Platform (OTP)</h5>
            <p>欢迎登录国外考试新教师在线备课平台。本视频为您详细演示了平台涉及的集团主管、学校主管、指导教师、新教师用户的使用介绍，重点为您展现新教师如何通过6步备课闯关游戏，成为一名合格、优秀新东方国外考试教师的过程。</p>
            <div id="vplayer-action">
              <button type="button" class="btn btn-primary" data-toggle="button" onclick="jwplayer().play()"><i class="icon-film icon-white"></i>&nbsp;&nbsp;PLAY / PAUSE</button>
              <a class="btn btn-danger" href="${ctx}"><i class="icon-hand-left icon-white"></i>&nbsp;&nbsp;BACK TO OTP</a>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="footer">
    <p class="foot-txt">Copyright &copy; 2012 Knowledge Management Center, New Oriental</p>
  </div>
</body>
</html>