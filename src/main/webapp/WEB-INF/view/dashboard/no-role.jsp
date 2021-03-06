<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/metro.css" />
<script type="text/javascript" src="${ctx}/resources/js/lib/plugin/metro/tile-slider.js"></script>
</head>
<body>
  <div class="container">
    <div class="tiles">
      <a href="${ctx}/trainee/welcome" class="tile">
        <div class="tile-content">
          <p>非常抱歉，您并未被授予本平台的任何使用权限。</p>
          <img src="${ctx}/resources/img/metro/dashboard-17.png" />
        </div>
      </a> <a href="mailto:yangyifeng@xdf.cn?subject=[OTP]问题描述">
        <div class="tile">
          <div class="tile-content">
            <p>在使用本平台的过程中无论遇到任何问题，请您随时拨打电话或者发送电子邮件进行求助</p>
            <img src="${ctx}/resources/img/metro/dashboard-08.png" />
          </div>
          <div class="brand">
            <img class="icon" src="${ctx}/resources/img/metro/phone_32x32.png">
            <div class="count">010 6260 5566</div>
          </div>
        </div>
      </a>
    </div>
  </div>
</body>
</html>