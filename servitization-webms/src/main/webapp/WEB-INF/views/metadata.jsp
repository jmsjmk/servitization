<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
   <div class="panel-heading">
      <h3 class="panel-title">
        <h1 style="text-align:left">元数据标识列表</h1>
      </h3>
   </div>
   <div class="panel-body">
      <form class="form-inline">
        <div class="form-group">
            <label for="metaKey">元数据唯一id：</label>
            <input type="text" class="form-control" id="metaKey" placeholder="serviceId">
        </div>
        <div class="form-group">
            <label for="description">元数据名称：</label>
            <input type="email" class="form-control" id="description" placeholder="服务节点名称">
        </div>
        <button type="button" class="btn btn-default" onclick="addMetadata()">添加</button>
      </form>
        <hr>
        <table class="table table-bordered table-hover  table-striped">
            <thead>
            <tr>
                <th style="text-align: center;">#</th>
                <th style="text-align: center;">元数据唯一id</th>
                <th style="text-align: center;">元数据名称</th>
                <th style="text-align: center;">添加时间</th>
                <th style="text-align: center;">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:set value="1" var="num" scope="page"/>
            <c:forEach var="m" items="${metadatas}">
                <tr value="${m.id}" align="center">
                    <td scope="row">
                        <c:out value="${num}"/>
                        <c:set value="${num + 1}" var="num"/>
                    </td>
                    <td><c:out value="${m.metaKey}"/></td>
                    <td><c:out value="${m.description}"/></td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${m.createTime}"/></td>
                    <td>
                        <a href="#" class="btn btn-default btn-sm btn btn-danger" onclick="deleteOneMetadata(this, event)">删除</a>
                        <a href="#" class="btn btn-default btn-sm btn btn-info" onclick="addMetadataByCopy(this, event)">复制添加</a>
                        <a href="#" class="btn btn-default btn-sm btn btn-info" onclick="queryMetadataMachine(this, event)">机器列表</a>
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
                    <li class="disabled"><a href="javascript:void(0);"><img src="${path}/common/img/prev.png"></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:showMetadataPage(${pageIndex - 1})"><img src="${path}/common/img/prev.png"></a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(pageIndex == (pageCount - 1)) or (pageCount == 0)}">
                    <li class="disabled"><a href="javascript:void(0);"><img src="${path}/common/img/next.png"></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:showMetadataPage(${pageIndex + 1})"><img src="${path}/common/img/next.png"></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</div>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/metadata.js"></script>
<script src="${path}/common/zTree/js/jquery.ztree.core-3.5.js"></script>