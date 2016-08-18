<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" rel="stylesheet" href="${path}/common/css/jquery-ui.min.css" />
<div class="row">
	<div class="col-md-2">
		<button type="button" class="btn btn-default btn-sm btn-primary"
			onclick="showAddSessionPage();">添加</button>
		<button type="button" class="btn btn-default btn-sm btn-primary"
			onclick="deleteManyMetadataSession(event)">批量删除</button>
	</div>
	<div class="col-md-4">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="请求地址"
				id="sourceUrlParam"> <span class="input-group-btn">
				<button class="btn btn-default" type="button"
					onclick="showSessionPage();">筛选</button>
			</span>
		</div>
	</div>
</div>
<hr>
<div class="row" style="margin-top: 2%;">
	<div class="col-lg-12">
		<table class="table table-bordered table-hover  table-striped" id="sessionTable">
			<thead>
				<tr>
					<th style="text-align: center;">
						<input type="checkbox"
						id="MetadataSessionsAllCheckbox"
						onclick="checkAll('MetadataSessions');">
					</th>
					<th style="text-align: center;">#</th>
					<th style="text-align: center;">请求地址</th>
					<th style="text-align: center;">策略</th>
					<th style="text-align: center;">创建时间</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:set value="1" var="num" scope="page" />
				<c:forEach var="m" items="${metadataSessions}">
					<tr value="${m.id}" align="center">
						<td>
							<input type="checkbox" name="MetadataSessionsCheckbox">
						</td>
						<td scope="row">
							<c:out value="${num}" />
							<c:set value="${num + 1}" var="num" />
						</td>
						<td><c:out value="${m.sourceUrl}" /></td>
						<td><c:out value="${m.strategy}" /></td>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
								value="${m.createTime}" />
						</td>
						<td>
							<a href="#" class="btn btn-sm btn-info"
							onclick="showAddSessionPage(this, event)">修改</a>
							<a href="#" class="btn btn-sm btn-danger"
							onclick="deleteOneMetadataSession(this, event)">删除</a>
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
					<li class="disabled"><a href="javascript:void(0);"><img
							src="${path}/common/img/prev.png"></a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showSessionPage(${pageIndex - 1})"><img
							src="${path}/common/img/prev.png"></a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(pageIndex == (pageCount - 1)) or (pageCount == 0)}">
					<li class="disabled"><a href="javascript:void(0);"><img
							src="${path}/common/img/next.png"></a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showSessionPage(${pageIndex + 1})"><img
							src="${path}/common/img/next.png"></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</div>
<script src="${path}/common/js/jquery-2.1.4.min.js"></script>
<script src="${path}/common/js/jquery-ui.min.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/session.js"></script>