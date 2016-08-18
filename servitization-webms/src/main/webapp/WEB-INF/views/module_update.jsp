<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div  class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">
            <span>修改配置模块</span>
        </h3>
    </div>
    <div class="panel-body">
        <div id="ordinary" class="row">
            <div class="col-lg-12">
                <form class="form-horizontal">
                    <input type="hidden" id="moduleId" value="<c:out value="${metadataModule.id}"/>">
                    <div class="form-group">
                        <label for="sourceUrl" class="col-xs-2 control-label">模块名称</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" id="moduleName" value="<c:out value="${metadataModule.name}"/>" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sourceMethod" class="col-xs-2 control-label">处理模块名</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" id="handlerName" value="<c:out value="${metadataModule.handlerName}"/>" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="servicePoolSelect" class="col-sm-2 control-label">处理模块类路径</label>
                        <div class="col-sm-8" >
                            <input type="text" class="form-control" id="handlerClazz" value="<c:out value="${metadataModule.handlerClazz}"/>" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="serviceName" class="col-sm-2 control-label">所属链条</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="chain">
                                <option value="0" <c:if test="${metadataModule != null and metadataModule.chain == '0'}">selected</c:if> >上行链条</option>
                                <option value="1" <c:if test="${metadataModule != null and metadataModule.chain == '1'}">selected</c:if> >下行链条</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-2">
                            <button type="button" class="btn btn-default btn-primary" onclick="updateOneMoudle();">保存</button>
                        </div>
                        <div class="col-xs-0">
                            <button type="button" class="btn btn-default" onclick="showModulePage()">取消</button>
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