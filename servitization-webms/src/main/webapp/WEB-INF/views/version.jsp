<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${path}/common/js/version.js"></script>
<div class="panel panel-default">
   <div class="panel-heading">
      <h3 class="panel-title">
         <form class="form-inline">
          	生成新版本元数据 <span style="color: #990000;">关键操作,请谨慎操作</span>
            <div class="form-group">
                <div class="input-group">
                    <input type="text" class="form-control" id="description" placeholder="版本描述">
                </div>
            </div>
            <button type="button" class="btn" onclick="addVersion();">创建</button>
        </form>
      </h3>
   </div>
   <div class="panel-body">
      <table class="table table-bordered table-hover  table-striped">
            <thead>
                <tr>
                    <th style="text-align: center;">#</th>
                    <th style="text-align: center;">版本号</th>
                    <th style="text-align: center;">生成日期</th>
                    <th style="text-align: center;">描述</th>
                    <th style="text-align: center;">操作</th>
                </tr>
            </thead>
            <tbody>
            <c:set value="1" var="num" scope="page"/>
            <c:forEach var="v" items="${metadataVersions}">
                <tr value="${v.id}" align="center">
                    <td scope="row">
                        <c:out value="${num}"/>
                        <c:set value="${num + 1}" var="num"/>
                    </td>
                    <td>${v.id}</td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${v.createTime}"/></td>
                    <td><c:out value="${v.description}"/></td>
                    <td>
                        <a href="${path}/webms/version/previewVersion?id=${v.id}" target="_blank" class="btn btn-default btn-sm">预览</a>
                        <a href="#" onclick="publicNew(${v.id}, ${v.metadataId})" class="btn btn-default btn-sm btn btn-info">发布版本</a>
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
                    <li><a href="javascript:showVersionPage(${pageIndex - 1})"><img src="${path}/common/img/prev.png"></a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(pageIndex == (pageCount - 1)) or (pageCount == 0)}">
                    <li class="disabled"><a href="javascript:void(0);"><img src="${path}/common/img/next.png"></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:showVersionPage(${pageIndex + 1})"><img src="${path}/common/img/next.png"></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</div>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/version.js"></script>
<script>
</script>