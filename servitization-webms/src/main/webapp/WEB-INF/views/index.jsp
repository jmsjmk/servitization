<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/common/img/lightning.png">
    <title>servitization-webms</title>
    <link href="${path}/common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/common/css/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/common/zTree/css/demo.css" type="text/css">
    <link rel="stylesheet" href="${path}/common/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script src="${path}/common/js/jquery.min.js"></script>
    <script src="${path}/common/zTree/js/jquery.ztree.core-3.5.js"></script>
    <script src="${path}/common/js/params.js"></script>
    <script src="${path}/common/js/index.js"></script>
    <SCRIPT type="text/javascript">
        structureTree();
    </SCRIPT>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">MAPI接口管理平台</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
            <!--  监控页面暂时屏幕后续扩展开发
            <a class="navbar-brand" href="http://172.21.12.143:8080/monitor/getAppname?productline=servitization">接口监控-></a>
            -->
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul id="tree" class="ztree"></ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Dashboard</h1>
            <div class="container-fluid" id="mainRight">
            </div>
        </div>
    </div>
</div>
<script src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${path}/common/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

