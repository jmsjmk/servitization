<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<h3 class="page-header">
	<button type="button" class="btn btn-default btn-sm btn-primary"
		onclick="showAddGroupPage();">添加group配置</button>
</h3>
<table class="table table-bordered table-hover  table-striped" id="groupTable">
	<thead>
		<tr>
			<th style="text-align: center;">groupId</th>
			<th style="text-align: center;">group名称</th>
			<th style="text-align: center;">超时处理时间</th>
			<th style="text-align: center;">线程池大小</th>
			<th style="text-align: center;">策略</th>
			<th style="text-align: center;">包含模块</th>
			<th style="text-align: center;">所属上下行链条</th>
			<th style="text-align: center;">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:set value="1" var="num" scope="page" />
		<c:forEach var="m" items="${metadataGroups}">
			<tr value="${m.id}" align="center">

				<td><c:out value="${m.id}" /></td>
				<td><c:out value="${m.name}" /></td>
				<td><c:out value="${m.processTimeOut}" /></td>
				<td><c:out value="${m.size}" /></td>
				<td><c:out value="${m.policy}" /></td>
				<td>
					<c:forTokens items="${m.moduleIds}" delims="," var="mods">
						<c:choose>
							<c:when test="${fn:startsWith(mods, 'm')}">
								<c:forEach var="md" items="${modules}">
									<c:set value="m${md.id}" var="mId" />
									<c:if test="${mId == mods}">
										<c:out value="${md.name}" />&nbsp;&nbsp;
									</c:if>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:forTokens>
				</td>
				<td>
					<c:if test="${m.chain==0}">上行</c:if>
					<c:if test="${m.chain==1}">下行</c:if>
				</td>
				<td>
					<a href="#" class="btn btn-sm btn-info"
					onclick="showAddGroupPage('${m.id}')">修改</a>
					<a href="#"
					class="btn btn-sm btn-danger"
					   onclick="deleteGroup('${m.id}')">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/group.js"></script>