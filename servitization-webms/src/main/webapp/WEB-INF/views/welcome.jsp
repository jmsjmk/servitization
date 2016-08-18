<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<c:choose>
		<c:when test="${metadata.deployModel == null or metadata.deployModel == ''}">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<h2>ServiceDefine配置</h2>
					</h3>
				</div>
				<div class="panel-body">
					<h2>请配置具体的处理链条</h2>
				</div>
		</c:when>
		<c:otherwise>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<h2>${metadata.description}:ServiceDefine配置</h2>
					</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-2 col-lg-offset-1">部署策略:</div>
						<div class="col-lg-7">
							<ul>
								<li><c:out value="${metadata.deployModel}" /></li>
							</ul>
						</div>
					</div>

					<div class="row" style="margin-top: 2%;">
						<div class="col-lg-2 col-lg-offset-1">上行处理链:</div>
						<div class="col-lg-7">
							<ul>
								<c:forEach var="chain" items="${upChain}">
									<c:choose>
										<c:when test="${chain.type == 0}">
											<li><c:out value="${chain.metadataModule.name}" /></li>
										</c:when>
										<c:otherwise>
											<li><c:out value="${chain.metadataGroup.name}" /></li>
											<ul class="list-inline">
												(
												<c:forEach var="module"
													items="${chain.metadataGroup.metadataModules}">
													<li><c:out value="${module.name}" /></li>
												</c:forEach>
												)
											</ul>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="row" style="margin-top: 2%;">
						<div class="col-lg-2 col-lg-offset-1">下行处理链:</div>
						<div class="col-lg-7">
							<ul>
								<c:forEach var="chain" items="${downChain}">
									<c:choose>
										<c:when test="${chain.type == 0}">
											<li><c:out value="${chain.metadataModule.name}" /></li>
										</c:when>
										<c:otherwise>
											<li><c:out value="${chain.metadataGroup.name}" /></li>
											<ul class="list-inline">
												(
												<c:forEach var="module"
													items="${chain.metadataGroup.metadataModules}">
													<li><c:out value="${module.name}" /></li>
												</c:forEach>
												)
											</ul>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
						</div>
					</div>
		</c:otherwise>
	</c:choose>
</div>
</div>
</div>
<script src="${path}/common/js/welcome.js"></script>