<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row" style="margin-top: 2%;">
    <div class="col-lg-12">
        <table class="table table-bordered table-hover  table-striped">
            <thead>
                <tr>
                    <th style="text-align: center;">机器名</th>
                    <th style="text-align: center;">发布时间</th>
                    <th style="text-align: center;">最后更新时间</th>
                    <th style="text-align: center;">发布状态</th>
                </tr>
            </thead>
            <tbody>
            <c:set value="1" var="num" scope="page"/>
            <c:forEach var="publishIp" items="${publishDetail}">
                <tr value="${publishIp.id}" align="center">
                    <td><c:out value="${publishIp.ip}"/></td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${publishIp.createTime}"/></td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${publishIp.updateTime}"/></td>
					<td>
						<c:choose>
							<c:when test="${publishIp.status==0}">
								等待同步
							</c:when>
							<c:when test="${publishIp.status==1}">
								正在同步
							</c:when>
							<c:when test="${publishIp.status==2}">
								加载成功
							</c:when>
							<c:when test="${publishIp.status==3}">
								加载失败
							</c:when>
							<c:when test="${publishIp.status==4}">
								更新状态超时
							</c:when>
							<c:when test="${publishIp.status==5}">
								ZK节点不存在
							</c:when>
							<c:otherwise>
								未知发布失败
							</c:otherwise>
						</c:choose>
					</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="row">
     <div class="col-xs-3">
                                <button type="button" class="btn btn-default" onclick="showHistoryPage('${nodeRelationId}');">返回</button>
                            </div>
</div>

<script src="${path}/common/js/node.js"></script>