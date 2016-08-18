<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row" style="margin-top: 2%;">
    <div class="col-lg-12">
        <table class="table table-bordered table-hover  table-striped">
            <thead>
                <tr>
                    <th style="text-align: center;">#</th>
                    <th style="text-align: center;">发布版本号</th>
                    <th style="text-align: center;">发布版描述</th>
                    <th style="text-align: center;">发布时间</th>
                    <th style="text-align: center;">发布状态</th>
                    <th style="text-align: center;">详细</th>
                </tr>
            </thead>
            <tbody>
            <c:set value="1" var="num" scope="page"/>
            <c:forEach var="publish" items="${publishHisyory}">
                <tr value="${publish.id}" align="center">
                    <td scope="row">
                        <c:out value="${num}"/>
                        <c:set value="${num + 1}" var="num"/>
                    </td>
                    <td><c:out value="${publish.versionId}"/></td>
                    <td><c:out value="${publish.versionDesc}"/></td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${publish.createTime}"/></td>
					<td>
						<c:choose>
							<c:when test="${publish.status==0}">
								正在发布
							</c:when>
							<c:when test="${publish.status==1}">
								发布成功
							</c:when>
							<c:otherwise>
								发布失败
							</c:otherwise>
						</c:choose>
					</td>
                    <td>
                        <a href="javascript:void(0);" onclick="getPublishDetail('${publish.id}','${publish.nodeRelationId}')" class="btn btn-default btn-sm">详情</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="row">
    <nav>
        <ul class="pager">
            <c:choose>
                <c:when test="${pageIndex == 0}">
                    <li class="disabled"><a href="javascript:void(0);"><img src="${path}/common/img/prev.png"></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:showHistoryPage(${pageIndex - 1})"><img src="${path}/common/img/prev.png"></a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(pageIndex == (pageCount - 1)) or (pageCount == 0)}">
                    <li class="disabled"><a href="javascript:void(0);"><img src="${path}/common/img/next.png"></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:showHistoryPage(${pageIndex + 1})"><img src="${path}/common/img/next.png"></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</div>
<script src="${path}/common/js/node.js"></script>