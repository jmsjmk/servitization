<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="${path}/common/css/pvUv.css"
	type="text/css">
<div class="container">
	左侧：待选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;右侧：已选
	<hr>
	<div id="pv-check" class="row">
		<div id="box" class="pv">
			<c:forEach var="p" items="${data.proxyList}">
				<li proxyId="${p.id}" sources="${p.sourceUrl}"><c:out
						value="${p.sourceUrl}" /></li>
			</c:forEach>
		</div>
		<div id="box2" class="pv">
			<c:forEach var="m" items="${data.metadataPvUv}">
				<li proxyId="${m.proxyId}" sources="${m.sourceUrl}"><c:out
						value="${m.sourceUrl}" /></li>
			</c:forEach>
		</div>
	</div>
</div>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/pvUv.js"></script>