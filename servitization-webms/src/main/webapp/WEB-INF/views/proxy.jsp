<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" rel="stylesheet" href="${path}/common/css/jquery-ui.min.css" />
<div class="row">
	<div class="col-md-2">
		<button type="button" class="btn btn-default btn-sm btn-primary"
			onclick="showAddProxyPage();">添加</button>
		<button type="button" class="btn btn-default btn-sm btn-primary"
			onclick="deleteManyProxy(event)">批量删除</button>
	</div>
	<div class="col-md-3">
		<input type="text" class="form-control" id="sourceUrlParam"
			placeholder="请求地址">
	</div>
	<div class="col-md-3">
		<input type="text" class="form-control" id="targetUrlParam"
			placeholder="服务名">
	</div>
	<div class="col-md-2">
		<button class="btn btn-default" type="button"
			onclick="showProxyPage();">筛选</button>
	</div>
</div>
<hr>
<div class="row" style="margin-top: 2%;">
	<div id="ordinaryTable" class="col-lg-12">
		<table class="table table-bordered table-hover  table-striped" id="orderTable">
			<thead>
				<tr>
					<th style="text-align: center;">
						<input type="checkbox" id="MetadataProxysAllCheckbox" onclick="checkAll('MetadataProxys');">
					</th>
					<th style="text-align: center;">#</th>
					<th style="text-align: center;">源请求地址</th>
					<th style="text-align: center;">服务类型</th>
					<th style="text-align: center;">连接池</th>
					<th style="text-align: center;">服务名</th>
					<th style="text-align: center;">返回超时时间</th>
					<th style="text-align: center;">流量阀值</th>
					<th style="text-align: center;">阀值类型</th>
					<th style="text-align: center;">创建时间</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:set value="1" var="num" scope="page" />
				<c:forEach var="m" items="${metadataProxys}">
					<tr value="${m.id}__${m.servicePoolName}" align="center">
						<td><input type="checkbox" name="MetadataProxysCheckbox">
						</td>
						<td scope="row">
							<c:out value="${num}" />
							<c:set value="${num + 1}" var="num" />
						</td>
						<td><c:out value="${m.sourceUrl}"/></td>
						<td>
							<c:if test="${m.seviceType == 0}">
								<c:out value="HTTP" />
							</c:if>
							<c:if test="${m.seviceType == 1}">
								<c:out value="EMCF" />
							</c:if>
						</td>
						<td><c:out value="${m.servicePoolName}"/></td>
						<td><c:out value="${m.serviceName}"/></td>
						<td><c:out value="${m.socketTimeout}"/></td>
						<td><c:out value="${m.threshold}"/></td>
						<td>
							<c:if test="${m.thresholdType == 0}">
								<c:out value="随机" />
							</c:if> <c:if test="${m.thresholdType == 1}">
								<c:out value="按用户区分" />
							</c:if>
						</td>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
								value="${m.createTime}" />
						</td>
						<td>
							<a href="#" class="btn btn-sm btn-info"
							onclick="showAddProxyPage(this, event)">修改</a>
							<a href="#" class="btn btn-sm btn-danger"
							onclick="deleteOneMetadataProxy(this, event)">删除</a>
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
					<li class="disabled"><a href="javascript:void(0);">Previous</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showProxyPage(${pageIndex - 1})">Previous</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(pageIndex == (pageCount - 1)) or (pageCount == 0)}">
					<li class="disabled"><a href="javascript:void(0);">Next</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showProxyPage(${pageIndex + 1})">Next</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</div>
<script src="${path}/common/js/jquery-2.1.4.min.js"></script>
<script src="${path}/common/js/jquery-ui.min.js"></script>
<script src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/proxy.js"></script>