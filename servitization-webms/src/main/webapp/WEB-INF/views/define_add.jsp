<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:choose>
	<c:when test="${fn:length(metadataProxyList) <= 0}">
		<div class="panel panel-default">
  		<div class="panel-heading">
      		<h3 class="panel-title"><span>添加/修改</span></h3>
        </div>
        	<div class="panel-body"><h2>请先在请求转发配置模块中配置请求地址</h2></div>
	    </div>
	</c:when>
	<c:otherwise>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title"><span>添加/修改</span></h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal">
					<c:choose>
						<c:when test="${metadataDefine != null}">
							<input type="hidden" id="defineId"
								value="<c:out value="${metadataDefine.id}"/>">
						</c:when>
						<c:otherwise>
							<input type="hidden" id="defineId" value="">
						</c:otherwise>
					</c:choose>
					<div class="form-group">
						<label for="proxyId" class="col-xs-2 control-label">请求地址</label>
						<div class="col-xs-7">
							<select class="form-control" id="proxyId">
								<c:forEach var="p" items="${metadataProxyList}">
									<option value="<c:out value="${p.id}"/>"
										<c:if test="${metadataDefine != null and metadataDefine.proxyId == p.id}">selected</c:if>>
										<c:out value="${p.sourceUrl}" />
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="timeUnit" class="col-xs-2 control-label">时间(秒)&nbsp;</label>
						<div class="col-xs-7">
							<c:choose>
								<c:when test="${metadataDefine != null}">
									<input type="text" class="form-control" id="timeUnit"
										placeholder="timeUnit"
										value="<c:out value="${metadataDefine.timeUnit}"/>"
										onkeyup="this.value=this.value.replace(/\D/g,'')"
										onafterpaste="this.value=this.value.replace(/\D/g,'')">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="timeUnit"
										placeholder="timeUnit" value="60"
										onkeyup="this.value=this.value.replace(/\D/g,'')"
										onafterpaste="this.value=this.value.replace(/\D/g,'')">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="form-group">
						<label for="times" class="col-xs-2 control-label">次&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数</label>
						<div class="col-xs-7">
							<c:choose>
								<c:when test="${metadataDefine != null}">
									<input type="text" class="form-control" id="times"
										placeholder="times"
										value="<c:out value="${metadataDefine.times}"/>"
										onkeyup="this.value=this.value.replace(/\D/g,'')"
										onafterpaste="this.value=this.value.replace(/\D/g,'')">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="times"
										placeholder="times" value="1000"
										onkeyup="this.value=this.value.replace(/\D/g,'')"
										onafterpaste="this.value=this.value.replace(/\D/g,'')">
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="form-group">
						<div class="col-xs-offset-2 col-xs-3">
							<button type="button" class="btn btn-default btn-primary"
								onclick="vertifyDefine();">保存</button>
						</div>
						<div class="col-xs-3">
							<button type="button" class="btn btn-default"
								onclick="showDefinePage();">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<script src="${path}/common/js/define.js"></script>