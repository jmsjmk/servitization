<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="row">
    <ul class="nav nav-tabs" id="dataConfigUl">
        <li role="presentation" class="active"><a href="#" onclick="showVersionPage();">版本管理</a></li>
        <!--  原始版本的 暂实留着处理
        <li role="presentation"><a href="#" onclick="showNodePage();">发布版本信息</a></li>
        <li role="presentation"><a href="#" onclick="showEffectiveMachinePage();">生效机器列表</a></li>
        -->
        <li role="presentation"><a href="#" onclick="showHistoryVersion();">历史版本与生效机器列表</a></li>
        <li role="presentation"><a href="#" onclick="showEffectiveMachinePage();">生效机器列表</a></li>
    </ul>
</div>
<div id="dataConfigArea" class="row">
    hello 版本机器配置
</div>
<script src="${path}/common/js/versionandmachine.js"></script>
<script>
    showVersionPage();
</script>