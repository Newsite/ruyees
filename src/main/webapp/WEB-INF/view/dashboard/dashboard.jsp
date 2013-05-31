<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<link rel="stylesheet" href="${ctx}/resources/css/metro.css?id=25" />
</head>
<body>
  <div class="container">
    <div class="titles">
      <tags:metro text="集团备课跟踪" imgUrl="${ctx}/resources/img/metro/dashboard-01.png" roleNames="admin,group"
        href="${ctx}/group/report/statList" />
      <tags:metro text="集团绩效分析" imgUrl="${ctx}/resources/img/metro/dashboard-02.png" roleNames="admin,group"
        href="${ctx}/group/report/guidList" />
      <tags:metro text="用户管理" imgUrl="${ctx}/resources/img/metro/dashboard-03.png" roleNames="admin,group"
        href="${ctx}/admin/role/list" />
      <tags:metro text="新教师注册" imgUrl="${ctx}/resources/img/metro/dashboard-04.png" roleNames="admin,campus"
        href="${ctx}/register/list" />
        
      <tags:metro text="个人信息修改" imgUrl="${ctx}/resources/img/metro/dashboard-03.png" roleNames="trainee"
        href="${ctx}/register2/edit" />
      
      <div class="tile-main">
        <h3>国外考试新教师在线备课平台</h3>
        <p class="title-en">Overseas Testing Teacher Training Platform (OTP)</p>
      </div>
      <tags:metro text="意见反馈" imgUrl="${ctx}/resources/img/metro/dashboard-05.png" href="mailto:yangyifeng@xdf.cn?subject=[OTP]问题描述" />
      <tags:metro text="我的消息" imgUrl="${ctx}/resources/img/metro/dashboard-06.png" href="${ctx}/notify/list/1/ALL" />
      <tags:metro text="组织备课" imgUrl="${ctx}/resources/img/metro/dashboard-07.png" roleNames="campus"
        href="${ctx}/campus/flow/list" />
      <tags:metro text="集团模板配置" imgUrl="${ctx}/resources/img/metro/dashboard-08.png" roleNames="admin,group"
        href="${ctx}/group/template/list" />
      <tags:metro text="学校备课跟踪" imgUrl="${ctx}/resources/img/metro/dashboard-09.png" roleNames="campus"
        href="${ctx}/campus/progress/list" />
      <tags:metro text="学校绩效分析" imgUrl="${ctx}/resources/img/metro/dashboard-10.png" roleNames="campus"
        href="${ctx}/campus/progress/guidList" />
      <tags:metro text="开始备课吧" imgUrl="${ctx}/resources/img/metro/dashboard-11.png" roleNames="trainee"
        href="${ctx}/trainee/welcome" />
        
      <a href="##" class="tile empty"></a>
      <tags:metro text="学员备课跟踪" imgUrl="${ctx}/resources/img/metro/dashboard-12.png" roleNames="coach"
        href="${ctx}/coach/progress/list" />
      <tags:metro text="学术作业审核" imgUrl="${ctx}/resources/img/metro/dashboard-13.png" roleNames="coach"
        href="${ctx}/coach/package/list" />
      <tags:metro text="课件审核" imgUrl="${ctx}/resources/img/metro/dashboard-14.png" roleNames="coach"
        href="${ctx}/coach/courseware/list/4" />
      <tags:metro text="批课安排" imgUrl="${ctx}/resources/img/metro/dashboard-15.png" roleNames="coach"
        href="${ctx}/coach/grade/list" />
      <tags:metro text="课件确认" imgUrl="${ctx}/resources/img/metro/dashboard-16.png" roleNames="coach"
        href="${ctx}/coach/courseware/list/6" cssClass="outline-color-blue" />
    </div>
  </div>
</body>
</html>