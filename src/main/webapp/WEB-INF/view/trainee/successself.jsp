<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/otp.css?id=123">
<script src="${ctx}/resources/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
    <div class="finish-main">
      <div class="cie-outline">
        <div class="cie-inner">
          <div class="cie-tit">新东方认证教师</div>
          <div class="media">
            <a class="pull-left" href="#"><tags:image width="82px" height="82px" href="${successResult.poto}" clas="media-object img-polaroid" /></a>
            <div class="media-body">
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
    </div>
</body>
</html>