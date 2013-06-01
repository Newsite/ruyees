<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
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
<title>OTP - Overseas Testing Teacher Training Platform</title>
<link rel="shortcut icon" href="${ctx}/resources/img/favicon.ico" />
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/resources/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/navbar.css?id=22" />
<link rel="stylesheet" href="${ctx}/resources/css/admin-default.css?id=12" />
<script src="${ctx}/resources/js/lib/jquery/jquery.min.js" type="text/javascript"></script>
<sitemesh:head />
</head>
<body>
  <%@ include file="/WEB-INF/layouts/header.jsp"%>
  <div class="container">
    <div class="row">
      <div class="span3">
        <ul class="nav nav-list well">
          <!-- 集团主管 -->
          <shiro:hasAnyRoles name="admin,group">
            <li class="nav-header">集团教研</li>
            <tags:shirourl url="${ctx}/group/report/statList" active="lesson" text="集团备课跟踪" iconName="dashboard-01_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/group/report/guidList" active="groupguidList" text="集团绩效分析" iconName="dashboard-02_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/group/template/list" active="template" text="备课模板配置" iconName="dashboard-08_16.png" para="${active}"></tags:shirourl>
            <li class="divider"></li>
          </shiro:hasAnyRoles>
          <!-- 学校主管 -->
          <shiro:hasAnyRoles name="admin,group,campus">
            <li class="nav-header">学校教研</li>
            <tags:shirourl url="${ctx}/register/list" active="register" text="新教师注册" iconName="dashboard-04_16.png" para="${active}"></tags:shirourl>
          </shiro:hasAnyRoles>
          <shiro:hasAnyRoles name="campus">
            <tags:shirourl url="${ctx}/campus/flow/list" active="flow" text="组织备课" iconName="dashboard-07_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/campus/progress/list" active="campusprogress" text="学校备课跟踪" iconName="dashboard-09_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/campus/progress/guidList" active="campusguidList" text="学校绩效分析" iconName="dashboard-10_16.png" para="${active}"></tags:shirourl>
          </shiro:hasAnyRoles>
          <shiro:hasAnyRoles name="admin,group,campus">
            <li class="divider"></li>
          </shiro:hasAnyRoles>
          <!-- 指导教师 -->
          <shiro:hasAnyRoles name="coach">
            <li class="nav-header">备课指导</li>
            <tags:shirourl url="${ctx}/coach/progress/list" active="coachprogress" text="学员备课跟踪" iconName="dashboard-12_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/coach/package/list" active="package" text="学术作业审核" iconName="dashboard-13_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/coach/courseware/list/4" active="4" text="课件审批" iconName="dashboard-14_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/coach/grade/list" active="grade" text="批课安排" iconName="dashboard-15_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/coach/courseware/list/6" active="6" text="课件确认" iconName="dashboard-16_16.png" para="${active}"></tags:shirourl>
            <li class="divider"></li>
          </shiro:hasAnyRoles>
          <shiro:hasAnyRoles name="admin">
            <li class="nav-header">基础配置</li>
            <tags:shirourl url="${ctx}/group/diction/list" active="diction" text="基本信息" iconName="dashboard-08_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/group/contentMovie/list" active="contentMovie" text="文档视频" iconName="dashboard-08_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/group/examCategory/list" active="examCategory" text="试卷试题" iconName="dashboard-08_16.png" para="${active}"></tags:shirourl>
            <tags:shirourl url="${ctx}/group/operPackage/list" active="operPackage" text="学术作业包" iconName="dashboard-08_16.png" para="${active}"></tags:shirourl>
            <li class="divider"></li>
          </shiro:hasAnyRoles>
          <shiro:hasAnyRoles name="admin,group">
            <li class="nav-header">系统管理</li>
            <tags:shirourl url="${ctx}/admin/role/list" active="role" text="用户管理" iconName="dashboard-03_16.png" para="${active}"></tags:shirourl>
            <li class="divider"></li>
          </shiro:hasAnyRoles>
          <li class="nav-header">消息中心</li>
          <tags:shirourl url="${ctx}/notify/list/1/ALL" active="notify" text="我的消息" iconName="dashboard-06_16.png" para="${active}"></tags:shirourl>
        </ul>
      </div>
      <div class="span9">
        <sitemesh:body />
      </div>
    </div>
  </div>
  <%@ include file="/WEB-INF/layouts/footer.jsp"%>
  <script src="${ctx}/resources/js/lib/bootstrap/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>