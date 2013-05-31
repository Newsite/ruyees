<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="fdPackageId" value="${fdPackageId}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <section id="set-exam">
    <div class="well">
      <div class="page-header">
        <a href="${ctx}/group/operation/list?fdPackageId=${fdPackageId}" class="tile-return">返回上页</a>
        <h4>学术作业包</h4>
        <p>在本模块中，您可以配置平台所有课程及分项的学术作业包信息。</p>
      </div>
      <table class="table">
        <tr>
          <th width="15%">教研步骤</th>
          <td width="35%">${bean.fdName}</td>
          <th width="15%">序号</th>
          <td width="35%">${bean.fdOrder}</td>
        </tr>
        <tr>
          <th>作业图片</th>
          <td colspan="3"><img id="imgshow" src="${ctx}/${bean.backgroundUrl}" width="80" class="pull-right"
            height="120"></td>
        </tr>
        <tr>
          <th width="15%">教研材料</th>
          <td colspan="3">${bean.fdReferBook}</td>
        </tr>
        <tr>
          <th width="15%">教研成果</th>
          <td colspan="3">${bean.fdRequest}</td>
        </tr>
        <tr>
          <th>成果要求</th>
          <td class="tableTd" colspan="3">
            <table class="table setTest">
              <thead>
                <tr>
                  <th width="15%">#</th>
                  <th width="30%">成果指标</th>
                  <th width="15%">分数</th>
                  <th width="30%">对应模板</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${bean.indexs}" var="index" varStatus="vs1">
                  <tr>
                    <th><span class="num">${vs1.index+1}</span></th>
                    <td>${index.fdIndexName}</td>
                    <td>${index.fdValue}</td>
                    <td>${index.attMain.filePath}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3">${bean.fdDescription}</td>
        </tr>
      </table>
    </div>
  </section>
</body>
</html>