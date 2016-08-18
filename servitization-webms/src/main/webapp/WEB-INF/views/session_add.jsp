<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:choose>
	<c:when test="${fn:length(metadataProxyList) <= 0}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span>添加/修改</span>
				</h3>
			</div>
			<div class="panel-body">
				<h2>请先在请求转发配置模块中配置请求地址</h2>
			</div>
		</div>
	</c:when>
	<c:otherwise>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span>添加/修改</span>
				</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal">
					<c:choose>
						<c:when test="${metadataSession != null}">
							<input type="hidden" id="sessionId"
								value="<c:out value="${metadataSession.id}"/>">
						</c:when>
						<c:otherwise>
							<input type="hidden" id="sessionId" value="">
						</c:otherwise>
					</c:choose>
					<div class="form-group">
						<label for="strategy" class="col-xs-2 control-label">策&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;略</label>
						<div class="col-xs-7">
							<select class="form-control" id="strategy">
								<option value="HttpCheckStrategy"
									<c:if test="${metadataSession != null and metadataSession.strategy == 'HttpCheckStrategy'}">selected</c:if>>HttpCheckStrategy</option>
								<option value="ForceCheckStrategy"
									<c:if test="${metadataSession != null and metadataSession.strategy == 'ForceCheckStrategy'}">selected</c:if>>ForceCheckStrategy</option>
								<option value="ForceUnCheckStrategy"
									<c:if test="${metadataSession != null and metadataSession.strategy == 'ForceUnCheckStrategy'}">selected</c:if>>ForceUnCheckStrategy</option>
								<option value="ForceCheckWithVersionStrategy"
									<c:if test="${metadataSession != null and metadataSession.strategy == 'ForceCheckWithVersionStrategy'}">selected</c:if>>ForceCheckWithVersionStrategy</option>
								<option value="ForceCheckWriteCardNoStrategy"
									<c:if test="${metadataSession != null and metadataSession.strategy == 'ForceCheckWriteCardNoStrategy'}">selected</c:if>>ForceCheckWriteCardNoStrategy</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="proxyId" class="col-xs-2 control-label">请求地址</label>
						<div class="col-xs-7">
							<select class="form-control" id="proxyId">
								<c:forEach var="p" items="${metadataProxyList}">
									<option value="<c:out value="${p.id}"/>"
										<c:if test="${metadataSession != null and metadataSession.proxyId == p.id}">selected</c:if>>
										<c:out value="${p.sourceUrl}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="proxyId" class="col-xs-2 control-label">验证类型</label>
						<div class="col-xs-7">
							<select class="form-control" id="reqType">
									<option value="Http">HTTP</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="proxyId" class="col-xs-2 control-label">验证地址</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="validateurl" value="<c:out value="${metadataSession.validateurl}"/>" placeholder="请输入验证地址">
						</div>
					</div>
					<div class="form-group">
						<label for="proxyId" class="col-xs-2 control-label">验证方法</label>
						<div class="col-xs-7">
							<select class="form-control" id="validateMethod">
								<c:if test="${metadataSession != null and metadataSession.validateMethod == 'POST'}">selected</c:if>><c:out
											value="${p.validateMethod}" /></option>
								<option value="POST" 
								 <c:if test="${metadataSession != null and metadataSession.validateMethod == 'POST'}">selected</c:if>
								>POST</option>
								<option value="GET" 
									<c:if test="${metadataSession != null and metadataSession.validateMethod == 'GET'}">selected</c:if>
								>GET</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-offset-2 col-xs-3">
							<button type="button" class="btn btn-default btn-primary"
								onclick="vertifySession();">保存</button>
						</div>
						<div class="col-xs-3">
							<button type="button" class="btn btn-default"
								onclick="showSessionPage();">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/session.js"></script>