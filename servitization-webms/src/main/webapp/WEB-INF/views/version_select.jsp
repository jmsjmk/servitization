<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
      	<label><c:out value="${errorMsg }"></c:out> </label>
      	<input type="hidden"  name="aosNodeId" value="${aosNodeId }"/>
      	<input type="hidden"  name="aosRelationId" value="${aosRelationId }"/>
      	<input type="hidden"  name="selectedId" value="${selectedId }"/>
		<div class="col-lg-12">
			<table class="table table-bordered table-hover  table-striped">
				<thead>
					<tr>
						<th style="text-align: center;">选择</th>
						<th style="text-align: center;">ID</th>
						<th style="text-align: center;">生成日期</th>
						<th style="text-align: center;">描述</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="v" items="${metadataVersions}">
						<tr align="center">
							<td>
								<label class="radio">
								<c:choose>
									<c:when test="${v.id==selectedId}">
										<input type="radio" name="versionSelectRadio"  value="${v.id}" checked="checked">
									</c:when>
									<c:otherwise>
										<input type="radio" name="versionSelectRadio"  value="${v.id}" >
									</c:otherwise>
								</c:choose>
								
								<span class="metro-radio"></span>
								</label>
							</td>
							<td scope="row"><c:out value="${v.id}" /> </td>
							<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
									value="${v.createTime}" /></td>
							<td><c:out value="${v.description}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="publishMetadata()">发布</button>
      </div>
</div>
