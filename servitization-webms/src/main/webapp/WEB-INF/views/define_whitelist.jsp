<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<span>添加/修改</span>
		</h3>
	</div>
	<div class="panel-body">
		<form class="form-horizontal">
			<div class="form-group">
				<label for="ips" class="col-xs-2 control-label">白名单&nbsp;</label>
				<div class="col-xs-7">
					<textarea id="ips" cols=40 rows=10 class="form-control">${ips}</textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-offset-2 col-xs-3">
					<button type="button" class="btn btn-default btn-primary"
						onclick="vertifyWhitelist();">保存</button>
				</div>
				<div class="col-xs-3">
					<button type="button" class="btn btn-default"
						onclick="showDefinePage();">取消</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script src="${path}/common/js/define.js"></script>