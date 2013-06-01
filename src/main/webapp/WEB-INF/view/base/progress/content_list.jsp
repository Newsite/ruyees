<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%>
<j:set name="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
</head>
<body>
  <section id="exam-list">
    <div class="well">
      <div class="page-header">
        <a href="${ctx}/coach/progress/stageList/${projectId}/${newTeachId}" class="tile-return">返回上页</a>
        <h4>学员备课跟踪</h4>
        <p>
          在本模块中，您可以跟踪新教师的详细备课资源学习情况。
        </p>
      </div>
      <form class="form-horizontal" action="" name="form">
        <table class="table ">
          <thead>
            <tr>
              <th width="10px">#</th>
              <j:if test="${stageNo eq 1}">
                <th>第一关 业务学习</th>
              </j:if>
              <j:if test="${stageNo eq 2}">
                <th>第二关 学术准备</th>
              </j:if>
              <j:if test="${stageNo eq 3}">
                <th>第三关 标准化教案学习</th>
              </j:if>
              <j:if test="${stageNo eq 4}">
                <th>第四关 课件编写</th>
              </j:if>
              <j:if test="${stageNo eq 5}">
                <th>第五关 批课</th>
              </j:if>
              <j:if test="${stageNo eq 6}">
                <th>第六关 课件确认</th>
              </j:if>
            </tr>
          </thead>
          <j:iter items="${page.list}" var="bean" status="vs">
            <!-- step1 -->
            <j:if test="${stageNo eq 1}">
              <tr>
                <td>${stageNo}.${vs.index + 1}</td>
                <td>
                  <j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.cws}" var="movie" status="vs1">
                      <i class="icon-film"></i>
                      <j:ifelse test="${movie.through eq true}">
                        <j:then>
                          <span class="text-success">${movie.fdName}</span><br />
                        </j:then>
                        <j:else>${movie.fdName}<br /></j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if> <j:if test="${bean.fdIndex eq 2}">
                    <j:iter items="${bean.examCategories}" var="cate" status="vs2">
                      <i class="icon-flag"></i>
                      <j:ifelse test="${cate.through eq true}">
                        <j:then>
                          <span class="text-success">${cate.fdName}</span><br />
                        </j:then>
                        <j:else>${cate.fdName}<br /></j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                </td>
              </tr>
            </j:if>
            <!-- step2 -->
            <j:if test="${stageNo eq 2}">
              <tr>
                <td>${vs.index + 1}</td>
                <td>
                  <j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.bamPackage.bamOperation}" var="operation" status="vs1">
                      <j:iter items="${operation.bamIndexs}" var="bamIndex" status="vs2">
                        <i class="icon-upload"></i>
                        <j:ifelse test="${bamIndex.status >= 2}">
                          <j:then>
                            <span class="text-success">${bamIndex.fdIndexName}</span>
                            <span class="text-error"><j:if test="${not empty bamIndex.fdToValue}">${bamIndex.fdToValue}分</j:if></span>
                            <br />
                          </j:then>
                          <j:else>${bamIndex.fdIndexName}<br /></j:else>
                        </j:ifelse>
                      </j:iter>
                    </j:iter>
                  </j:if>
                </td>
              </tr>
            </j:if>
            <!-- step3 -->
            <j:if test="${stageNo eq 3}">
              <tr>
                <td>${vs.index + 1}</td>
                <td>
                  <j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.cws}" var="movie" status="vs1">
                      <i class="icon-film"></i>
                      <j:ifelse test="${movie.through eq true}">
                        <j:then>
                          <span class="text-success">${movie.fdName}</span><br />
                        </j:then>
                        <j:else>${movie.fdName}<br /></j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                  <j:if test="${bean.fdIndex eq 2}">
                    <j:iter items="${bean.examCategories}" var="cate" status="vs2">
                      <i class="icon-flag"></i>
                      <j:ifelse test="${cate.through eq true}">
                        <j:then>
                          <span class="text-success">${cate.fdName}</span><br />
                        </j:then>
                        <j:else>${cate.fdName}<br /></j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                  <j:if test="${bean.fdIndex eq 3}">
                    <j:iter items="${bean.cws}" var="content" status="vs3">
                      <i class="icon-book"></i>
                      <j:ifelse test="${content.through eq true}">
                        <j:then>
                          <span class="text-success">${content.fdName}</span><br />
                        </j:then>
                        <j:else>${content.fdName}<br /></j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                  <j:if test="${bean.fdIndex eq 4}">
                    <j:iter items="${bean.cws}" var="movie" status="vs4">
                      <i class="icon-film"></i>
                      <j:ifelse test="${movie.through eq true}">
                        <j:then>
                          <span class="text-success">${movie.fdName}</span><br />
                        </j:then>
                        <j:else>${movie.fdName}<br /></j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                </td>
              </tr>
            </j:if>
            <!-- step4 -->
            <j:if test="${stageNo eq 4}">
              <tr>
                <td>${vs.index + 1}</td>
                <td>
                  <j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.coursewares[0].items}" var="item" status="vs1">
                      <i class="icon-upload"></i>
                      <j:ifelse test="${item.through eq true}">
                        <j:then>
                          <span class="text-success">${item.name}</span>
                          <span class="text-error">${item.remark}</span>
                          <br />
                        </j:then>
                        <j:else>
                          <span>${item.name}</span>
                          <span>${item.remark}</span>
                          <br />
                        </j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                </td>
              </tr>
            </j:if>
            <!-- step5 -->
            <j:if test="${stageNo eq 5}">
              <tr>
                <td>1</td>
                <td>
                  <j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.grades[0].gradeItems}" var="item" status="vs1">
	      		 	  <i class="icon-calenadr"></i>
                      <span>${item.advier.realName}</span>
                      <span>${item.value}分</span>
                      <span>${item.remark}</span>
                      <br />
                    </j:iter>
                  </j:if>
                </td>
              </tr>
              <tr>
                <td>2</td>
                <td>
                  <j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.grades[0].ticklings}" var="tick" status="vs2">
                      <i class="icon-calendar"></i>
                      <j:if test="${tick.guidId == bean.grades[0].guidId}">
		      		 	<span>${tick.advier.realName}</span>
                        <span>${tick.totalvalue}分</span>
                        <span>${tick.appraise}</span>
                        <br />
                      </j:if>
                    </j:iter>
                  </j:if>
                </td>
              </tr>
            </j:if>
            <!-- step6 -->
            <j:if test="${stageNo eq 6}">
              <tr>
                <td>${vs.index + 1}</td>
                <td><j:if test="${bean.fdIndex eq 1}">
                    <j:iter items="${bean.coursewares[0].items}" var="item" status="vs1">
                      <i class="icon-upload"></i>
                      <j:ifelse test="${item.through eq true}">
                        <j:then>
                          <span class="text-success">${item.name}</span>
                          <span>${item.remark}</span>
                          <br />
                        </j:then>
                        <j:else>
                          <span>${item.name}</span>
                          <span>${item.remark}</span>
                          <br />
                        </j:else>
                      </j:ifelse>
                    </j:iter>
                  </j:if>
                </td>
              </tr>
            </j:if>
          </j:iter>
          </tbody>
        </table>
        <tags:pagination page="${page}" paginationSize="5" />
      </form>
    </div>
  </section>
</body>
</html>