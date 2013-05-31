<%@page import="jodd.servlet.JspResolver"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tags/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <section id="set-exam">
    <div class="well">
      <div class="page-header">
        <a href="${ctx}/group/template/list">返回上页</a>
        <h4>备课模板配置</h4>
        <p>在本模块中，您可以配置集团所有国外考试项目课程及分项的备课模板。</p>
      </div>
      <table class="table">
        <tr>
          <th>备课模板</th>
          <td colspan="3">${bean.fdName}</td>
        </tr>
        <tr>
          <th>项目类型</th>
          <td>${bean.program.fdName}</td>
          <th>课程类型</th>
          <td>${bean.course.fdName}</td>
        </tr>
        <tr>
          <th>分项类型</th>
          <td>${bean.item.fdName}</td>
          <th>阶段类型</th>
          <td>${bean.stage.fdName}</td>
        </tr>
        <tr>
          <th>学术作业成绩</th>
          <td>${bean.fdOperationWeight}<span class="add-on">%</span></td>
          <th>批课成绩</th>
          <td>${bean.fdAppofClass}<span class="add-on">%</span></td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3">${bean.description}</td>
        </tr>
        <tr>
          <td colspan="4" style="background-color: #f8f8f8">
            <div class="tabbable">
              <ul id="mytab" class="nav nav-tabs">
                <li class="active"><a href="#tab1" data-toggle="tab">第一关</a></li>
                <li><a href="#tab2" data-toggle="tab">第二关</a></li>
                <li><a href="#tab3" data-toggle="tab">第三关</a></li>
                <li><a href="#tab4" data-toggle="tab">第四关</a></li>
                <li><a href="#tab5" data-toggle="tab">第五关</a></li>
                <li><a href="#tab6" data-toggle="tab">第六关</a></li>
              </ul>
              <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                  <%@include file="../templateStep/1_view.jsp"%>
                </div>
                <div class="tab-pane" id="tab2">
                  <%@include file="../templateStep/2_view.jsp"%>
                </div>
                <div class="tab-pane" id="tab3">
                  <%@include file="../templateStep/3_view.jsp"%>
                </div>
                <div class="tab-pane" id="tab4">
                  <%@include file="../templateStep/4_view.jsp"%>
                </div>
                <div class="tab-pane" id="tab5">
                  <%@include file="../templateStep/5_view.jsp"%>
                </div>
                <div class="tab-pane" id="tab6">
                  <%@include file="../templateStep/6_view.jsp"%>
                </div>
              </div>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </section>
</body>
</html>