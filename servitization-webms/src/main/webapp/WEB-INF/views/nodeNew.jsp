<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<div class="form-group">
				<div class="input-group">
					<input type="text" id="nodes" style="width: 500px; heigth: 260px;"
						placeholder="输入AOS节点ID绑定:（多个节点以逗号,分隔）"> <label
						for="queryBtn">&nbsp;&nbsp;</label>
					<button id="queryBtn" type="button" class="btn btn-success"
						onclick="getTree();">查看</button>
					<label for="addBtn"></label>
					<button id="addBtn" type="button" class="btn btn-warning"
						onclick="addNodes();">添加udddddddd</button>
				</div>
		</h3>
	</div>
	<div class="panel-body">
		<table class="table table-bordered table-hover  table-striped">
			<thead>
				<tr>
					<th style="text-align: center;">#</th>
					<th style="text-align: center;">节点ID</th>
					<th style="text-align: center;">节点名称</th>
					<th style="text-align: center;">添加时间</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:set value="1" var="num" scope="page" />
				<c:forEach var="node" items="${metadataNodes}">
					<tr value="${node.id}" align="center">
						<td scope="row"><c:out value="${num}" /> <c:set
								value="${num + 1}" var="num" /></td>
						<td><c:out value="${node.nodeId}" /></td>
						<td><c:out value="${node.nodeName}" /></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
								value="${node.createTime}" /></td>

						<td><a href="javascript:void(0);"
							class="btn btn-default btn-sm" onclick="delNode(this,event);">删除</a>
							<button onclick="getPublishVersion('${node.id}','${node.nodeId}')">
							发布
							</button>
							 <a href="javascript:void(0);"
							onclick="showHistoryPage('${node.id}')"
							class="btn btn-default btn-sm">发布状态</a></td>
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
					<li class="disabled"><a href="javascript:void(0);"><img
							src="${path}/common/img/prev.png"></a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showNodePage(${pageIndex - 1})"><img
							src="${path}/common/img/prev.png"></a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(pageIndex == (pageCount - 1)) or (pageCount == 0)}">
					<li class="disabled"><a href="javascript:void(0);"><img
							src="${path}/common/img/next.png"></a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showNodePage(${pageIndex + 1})"><img
							src="${path}/common/img/next.png"></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</div>
<div id="treeStr" style="display: none">${tree}</div>
<!-- <div id="mytree"> -->
<!-- 	<div style="width: 300px; height: 550px; overflow-y:auto; overflow-x:auto;"> -->
<!-- 		<ul id="treeUl" class="ztree"></ul> -->
<!-- 	</div> -->
<!-- 	<button class="btn btn-danger" onclick="closeTree()">关闭</button> -->
<!-- </div> -->

<div class="modal fade" id="treeModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel2">AOS Tree</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-5">
						<input type="text" id="selectedItems" class="form-control"  aria-describedby="helpBlock" placeholder="已选择的节点id" />
					</div>
					<div class="col-xs-5">
						<input type="text" id="treeQuery" class="form-control" aria-describedby="helpBlock" placeholder="请输入要搜索的节点名" onkeydown="onTreeQueryChange()" />
					</div>
					<div class="col-xs-2">
						<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="$('#nodes').val($('#selectedItems').val())">确定</button>
					</div>
				</div>
				<ul id="treeUl" class="ztree"></ul>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div id="myModalDialog" class="modal-dialog" role="document"></div>
</div>
<script src="${path}/common/js/jquery.min.js"></script>
<script src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/node.js"></script>
<script src="${path}/common/zTree/js/jquery.ztree.core-3.5.js"></script>