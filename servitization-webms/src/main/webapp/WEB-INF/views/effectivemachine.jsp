<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<form class="form-inline">
				<div class="form-group">
					<label for="version" class="control-label">版本选择</label>
					<div class="input-group">
						<select class="form-control input-sm" id="version"
							onchange="selectVersion();">
							<option value="0">请选择</option>
							<c:forEach var="v" items="${versions}">
								<option value="<c:out value="${v.id}"/>"
									<c:if test="${v.isMachineExist == false}">disabled="" style="color:#DCDCDC"</c:if>>
									<c:out value="${v.description}" />
									(<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${v.createTime}" />)
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</form>
		</h3>
	</div>
	<div class="panel-body">
		<div class="col-lg-12" id="effectiveMachineList">
			<c:choose>
				<c:when test="${not empty machines}">
					<table class="table table-bordered table-hover  table-striped">
						<thead>
							<tr>
								<th style="text-align: center;">#</th>
								<th style="text-align: center;">机器IP</th>
								<th style="text-align: center;">机器名</th>
								<th style="text-align: center;">机器所属节点</th>
							</tr>
						</thead>
						<tbody>
							<c:set value="1" var="num" scope="page" />
							<c:forEach var="m" items="${machines}">
								<tr align="center">
									<td scope="row"><c:out value="${num}" /> <c:set
											value="${num + 1}" var="num" /></td>
									<td><c:out value="${m.machineIp}" /></td>
									<td><c:out value="${m.machineName}" /></td>
									<td><c:choose>
											<c:when test="${m.nodeName != null and m.nodeName != ''}">
												<c:out value="${m.nodeName}" />
											</c:when>
											<c:otherwise>
                                            -
                                        </c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<h3>没有生效机器</h3>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</div>
<script src="${path}/common/js/effectivemachine.js"></script>