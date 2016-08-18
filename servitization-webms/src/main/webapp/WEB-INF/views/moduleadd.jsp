<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<h1 style="text-align: left">模块添加</h1>
		</h3>
	</div>
	<div class="panel-body">
		<form>
			<div class="form-group">
				<label for="moduleName">模块名称</label> <input type="text"
					class="form-control" id="moduleName" placeholder="汽车票">
			</div>
			<div class="form-group">
				<label for="handlerName">处理类名称</label> <input type="text"
					class="form-control" id="handlerName" placeholder="ReqServ">
			</div>
			<div class="form-group">
				<label for="handlerClazz">处理类全路径</label> <input type="text"
					class="form-control" id="handlerClazz"
					placeholder="com.elong.mb.ReqServ">
			</div>
			<div class="form-group">
				<label for="chain">上下行链条</label> <select class="form-control"
					id="chain">
					<option value="0">上行链条</option>
					<option value="1">下行链条</option>
				</select>
			</div>

			<button type="button" class="btn btn-default" onclick="addModule()">保存</button>
		</form>
	</div>
</div>
<script src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
<script src="${path}/common/js/module.js"></script>