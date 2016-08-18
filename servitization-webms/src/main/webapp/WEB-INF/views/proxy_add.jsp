<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div  class="panel panel-default">
   <div class="panel-heading">
      <h3 class="panel-title">
		  <span>添加/修改&nbsp;&nbsp;&nbsp;&nbsp;</span>
      </h3>
   </div>
   <div class="panel-body">
    <!--普通模式 -->
	<div id="ordinary" class="row">
		<div class="col-lg-12">
			<form class="form-horizontal">
				<c:choose>
					<c:when test="${metadataProxy != null}">
						<input type="hidden" id="proxyId" value="<c:out value="${metadataProxy.id}"/>">
					</c:when>
					<c:otherwise>
						<input type="hidden" id="proxyId" value="">
					</c:otherwise>
				</c:choose>
				<div class="form-group">
					<label for="sourceUrl" class="col-xs-2 control-label">源路径</label>
					<div class="col-xs-8">
						<c:choose>
							<c:when test="${metadataProxy != null}">
								<input type="text" class="form-control" id="sourceUrl" placeholder="sourceUrl" value="<c:out value="${metadataProxy.sourceUrl}"/>" />
							</c:when>
						  <c:otherwise>
							  <input type="text" class="form-control" id="sourceUrl" placeholder="sourceUrl" >
						  </c:otherwise>
						</c:choose>
				  	</div>
				</div>
				<div class="form-group">
					<label for="sourceMethod" class="col-xs-2 control-label">源请求方法</label>
					<div class="col-xs-8">
						<input type="hidden" id="sourceMethodValue" value="${metadataProxy.sourceMethod}" />
						<label class="checkbox-inline"><input type="checkbox" id="getCb" value="GET" checked="false" />GET</label>
						<label class="checkbox-inline"><input type="checkbox" id="postCb" value="POST" checked="false" />POST</label>
						<label class="checkbox-inline"><input type="checkbox" id="putCb" value="PUT" checked="false" />PUT</label>
						<label class="checkbox-inline"><input type="checkbox" id="deleteCb" value="DELETE" checked="false" />DELETE</label>
					</div>
				</div>
				<div class="form-group">
					<label for="servicePoolSelect" class="col-sm-2 control-label">连接池</label>
					<div class="col-sm-8" >
						<div class="input-group">
							<select id="servicePoolSelect" class="form-control">
							</select>
							<span class="input-group-btn">
								<button type="button" class="btn btn-success" id="inspectPools" data-toggle="modal" data-target="#poolModal" onclick="selectPools()">设置</button>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="serviceName" class="col-sm-2 control-label">服务名</label>
					<div class="col-sm-8">
						<c:choose>
							<c:when test="${metadataProxy.serviceName != null}">
								<input type="text" class="form-control" id="serviceName" value="<c:out value="${metadataProxy.serviceName}"/>" placeholder="请输入服务名">
							</c:when>
							<c:otherwise>
								<input type="text" class="form-control" id="serviceName" placeholder="请输入服务名">
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-group">
					<label for="times" class="col-xs-2 control-label">返回超时时间(ms)</label>
					<div class="col-xs-8">
						<c:choose>
							<c:when test="${metadataProxy != null}">
								<input type="text" class="form-control" id="socketTimeout" placeholder="socketTimeout" value="<c:out value="${metadataProxy.socketTimeout}"/>"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
							</c:when>
							<c:otherwise>
								<input type="text" class="form-control" id="socketTimeout" placeholder="socketTimeout" value="12000" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
							</c:otherwise>
						</c:choose>
                      </div>
				</div>
				<div id="httpFormDiv">
					<div class="form-group">
						<label for="targetMethod" class="col-xs-2 control-label">目标请求方法</label>
						<div class="col-xs-8">
							<select class="form-control" id="targetMethod">
								<option value="GET"  <c:if test="${metadataProxy != null and metadataProxy.targetMethod == 'GET'}">selected</c:if> >GET</option>
								<option value="POST" <c:if test="${metadataProxy != null and metadataProxy.targetMethod == 'POST'}">selected</c:if> >POST</option>
							</select>
	                      </div>
					</div>
				</div>
				<div id="httpFormDiv">
					<div class="form-group">
						<label for="targetMethod" class="col-xs-2 control-label">是否转换响应数据</label>
						<div class="col-xs-8">
							<select class="form-control" id="isConvert">
								<option value="no" <c:if test="${metadataProxy != null and metadataProxy.convert == 'no'}">selected</c:if> >不转换</option>
								<option value="yes"  <c:if test="${metadataProxy != null and metadataProxy.convert == 'yes'}">selected</c:if> >转换</option>
							</select>
						</div>
					</div>
				</div>
				<div id="emcfFormDiv" style="display:none">
					<div class="form-group">
						<label for="serviceVersion" class="col-sm-2 control-label">服务版本</label>
						<div class="col-sm-8">
							<c:choose>
								<c:when test="${metadataProxy.serviceVersion != null}">
									<input type="text" class="form-control" id="serviceVersion" value="<c:out value="${metadataProxy.serviceVersion}"/>" placeholder="请输入服务版本">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="serviceVersion" placeholder="请输入服务版本">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="thresholdType" class="col-xs-2 control-label">流量阀值类型&nbsp;&nbsp;<span title="“随机”是指对用户进行随机拦截。在这种策略下，每个用户每次访问都有一定概率被拦截。“按用户区分”是指按用户设备号进行拦截。在这种策略下，如果一个设备的一次请求被拦截，则无论访问多少次都会被拦截。"><img src="${path}/common/img/a.png" class="img-circle"></span></label>
					<div class="col-xs-8">
						<select class="form-control" id="thresholdType">
                        	<option value="0"  <c:if test="${metadataProxy != null and metadataProxy.thresholdType == '0'}">selected</c:if> >随机</option>
                        	<option value="1" <c:if test="${metadataProxy != null and metadataProxy.thresholdType == '1'}">selected</c:if> >按用户区分</option>
                      	</select>
					</div>
				</div>
				<div class="form-group">
					<label for="times" class="col-xs-2 control-label">流量阀值</label>
					<div id="slider-range-min" class="col-xs-6" style="margin-top: 1%;margin-left: 2.4%;"></div>
					<div class="col-xs-2" style="width: 120px;">
						<div class="input-group">
							<c:choose>
								<c:when test="${metadataProxy != null}">
									<input type="text" class="form-control" id="threshold" placeholder="threshold" value="<c:out value="${metadataProxy.threshold}"/>"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="threshold" placeholder="threshold" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								</c:otherwise>
							</c:choose>
							<span class="input-group-addon">%</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-2 col-xs-3">
						<button type="button" class="btn btn-default btn-primary" onclick="addProxy();">保存</button>
					</div>
					<div class="col-xs-3">
						<button type="button" class="btn btn-default" onclick="showProxyPage();">取消</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</div>
</div>
	

<!-- 连接池界面 -->
<div class="modal fade" id="poolModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">连接池</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-4">
						<div id="poolList">
							<a href="#" class="list-group-item active">连接池列表</a>
						</div>
						<div class="col-sm-offset-2 col-sm-10 btn-group">
							<button class="btn btn-success" onclick="onInsertPoolBtnClick()">新增</button>
							<button class="btn btn-default" onclick="deletePoolByName()">删除</button>
						</div>
					</div>
					<!-- 池的明细 -->
					<div class="col-sm-8" style="border:1px solid #CCCCCC; border-radius:3px; padding: 15px">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="servicePoolName" class="col-sm-4 control-label">连接池名</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="servicePoolName" placeholder="请输入连接池名" disabled="disabled" />
									<input type="hidden" class="form-control" id="oldServicePoolName"/>
								</div>
							</div>
							<div class="form-group">
								<label for="poolUrl" class="col-sm-4 control-label">url(包含端口)</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="poolUrl" placeholder="127.0.0.1:8087" onchange="assemableServicePoolName()">
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-4 control-label">选择列表</label> 
								<div class="col-sm-8">
									<select id="serviceType" class="form-control">
										<option value="0">HTTP</option>
										<option value="1">EMCF</option>
									</select>
								</div>
							</div>
							<div id="httpSpecificPoolForm">
								<div class="form-group">
									<label for="connectTimeout" class="col-sm-4 control-label">连接超时时间(ms)</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="poolConnectTimeout" placeholder="请输入连接超时时间" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
									</div>
								</div>
							</div>
							<div id="emcfSpecificPoolForm" style="display:none">
								<div class="form-group">
									<label for="coefficient" class="col-sm-4 control-label">coefficient</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="coefficient" placeholder="0-1之间的数字" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d\.]/g,'')">
									</div>
								</div>
								<div class="form-group">
									<label for="forceCloseChannel" class="col-sm-4 control-label">forceCloseChannel</label>
									<div class="col-sm-8">
										<select id="forceCloseChannel" class="form-control">
											<option value="0">FALSE</option>
											<option value="1">TRUE</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="forceCloseTimeMillis" class="col-sm-4 control-label">forceCloseTimeMillis(ms)</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="forceCloseTimeMillis" placeholder="请输入forceCloseTimeMillis" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-3">&nbsp;&nbsp;</div>
								<div class="col-sm-8">
									<div class="col-sm-offset-2 col-sm-10 btn-group">
							         	<a class="btn btn-warning" onclick="insertOrUpdatePoolById()">保存</a>
							      	</div>
								</div>
						   	</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
<link rel="stylesheet" href="${path}/common/css/jquery-ui.min.css">
<script src="${path}/common/js/jquery.min.js"></script>
<script src="${path}/common/js/jquery-ui.min.js"></script>
<script src="${path}/common/js/proxy.js"></script>
<script src="${path}/common/js/slider.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/jquery.bootstrap-duallistbox.min.js"></script>