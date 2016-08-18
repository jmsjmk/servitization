<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<h1 style="text-align: left">模块列表</h1>
		</h3>
	</div>
	<div class="panel-body">
	<form class="form-inline">
		<button type="button" class="btn btn-default" onclick="showModuleAddPage()">添加模块</button>
			</form>
				<font color="red">${msg}</font>
				<hr>
			<table class="table table-bordered table-hover  table-striped">
				<thead>
					<tr>
						<th>模块名称</th>
						<th>处理模块名</th>
						<th>处理模块类路径</th>
						<th>所属链条</th>
						<th>操作</th>
					</tr>
				</thead>
			<tbody>
				<c:set value="1" var="num" scope="page" />
				<c:forEach var="m" items="${modules}">
					<tr>
						<td><c:out value="${m.name}" /></td>
						<td><c:out value="${m.handlerName}" /></td>
						<td><c:out value="${m.handlerClazz}" /></td>
						<td>
							<c:choose>
								<c:when test="${m.chain==0}">上行
                        	</c:when>
								<c:otherwise>下行
                        	</c:otherwise>
							</c:choose>
						</td>
						<td>
							<a href="#"
							class="btn btn-default btn-sm  btn btn-danger"
							onclick="deleteOneMoudle('<c:out value="${m.id }"></c:out>')">删除</a>

							<a href="#"
							   class="btn btn-default btn-sm  btn btn-info"
							   onclick="preUpdateOneMoudle('<c:out value="${m.id }"></c:out>')">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/module.js"></script>