<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">
            <h1 style="text-align:left">机器列表(${metadata.description})</h1>
            <input type="hidden" id="metadataDescription" value="${metadata.description})" />
        </h3>
    </div>
    <div class="panel-body">
        <form class="form-inline">
            <div class="form-group">
                <input type="hidden" name="metadataId" id="metadataId" value="${metadataId}"/>
                <label for="metaKey">机器ip：</label>
                <input type="text" class="form-control" id="machineIp" placeholder="127.0.0.1">
            </div>
            <div class="form-group">
                <label for="description">机器状态：</label>
                <select class="form-control" id="status">
                    <option value="0">未启用</option>
                    <option value="1">启用</option>
                </select>
            </div>
            <button type="button" class="btn btn-default" onclick="addMachine()">添加</button>
        </form>
        <hr>
        <table class="table table-bordered table-hover  table-striped">
            <thead>
            <tr>
                <th style="text-align: center;">#</th>
                <th style="text-align: center;">元数据id</th>
                <th style="text-align: center;">机器ip</th>
                <th style="text-align: center;">状态</th>
                <th style="text-align: center;">添加时间</th>
                <th style="text-align: center;">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:set value="1" var="num" scope="page"/>
            <c:forEach var="m" items="${metadataMachines}">
                <tr value="${m.id}" align="center">
                    <td scope="row">
                        <c:out value="${num}"/>
                        <c:set value="${num + 1}" var="num"/>
                    </td>
                    <td><c:out value="${m.metadataId}"/></td>
                    <td><c:out value="${m.ip}"/></td>
                    <td>
                        <c:if test="${m.status == '0'}">
                            未启用
                        </c:if>
                        <c:if test="${m.status == '1'}">
                            启用
                        </c:if>
                    </td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${m.createTime}"/></td>
                    <td>
                        <a href="#" class="btn btn-default btn-sm btn btn-danger" onclick="deleteMetadataMachine(this, event)">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/metadata.js"></script>
<script src="${path}/common/js/machine.js"></script>
<script src="${path}/common/zTree/js/jquery.ztree.core-3.5.js"></script>