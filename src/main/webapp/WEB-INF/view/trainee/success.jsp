<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
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
<title>OTP - Overseas Test Teacher Training Platform</title>
<link rel="shortcut icon" href="${ctx}/resources/img/favicon.ico" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/navbar.css">
<link rel="stylesheet" href="${ctx}/resources/css/otp.css?id=123">
<script src="${ctx}/resources/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
  <div class="container">
    <div class="finish-main">
      <div class="cie-outline">
        <div class="cie-inner">
          <div class="cie-tit">新东方认证教师</div>
          <div class="media">
            <%--  <a class="pull-left" href="#"><img class="media-object img-polaroid" src="${successResult.poto}"></a> --%>
            <a class="pull-left" href="#"><tags:image width="82px" height="82px" href="${successResult.poto}" clas="media-object img-polaroid" /></a>
            <div class="media-body">
              <!-- <a href="#">${successResult.realname }</a> 老师 <br /> -->
              <!-- <a href="#" class="xunzhg">查看证书<i class="icon-xunzhg"></i></a> -->
            </div>
          </div>
          <div class="txtbox">
            <p>
              <span class="fill">${successResult.realname }</span>老师，
            </p>
            <p>
              已于&nbsp;<span class="fill">${successResult.endtime }</span>&nbsp;完成新东方国外考试雅思${model.template.stage.fdName }${model.template.item.fdName }备课任务，成绩&nbsp;<span class="fill">${successResult.successScore}</span>&nbsp;。特此认证为新东方雅思教师。
            </p>
            <div>
              This is to certify MR/MS&nbsp;<span class="fill">${successResult.englishname}</span>&nbsp;'s successful completion
              of the New Oriental Overseas Test Teacher Online Training.
            </div>
          </div>
          <div class="inscribe">
            ${successResult.realname}老师，恭祝备课通关，祝你工作愉快！<i class="icon-name"></i>
          </div>
        </div>
      </div>
      <div class="wel-btn-group">
        <ul class="nav nav-tabs">
          <li><a href="${ctx}/trainee/to/${name}">return <i class="icon-otp-wel"></i></a></li>
          <!-- <li><a href="${ctx}/common/download/pdf/${name}">下载 <i class="icon-otp-wel"></i></a></li> -->
        </ul>
      </div>
    </div>
  </div>
</body>
</html>