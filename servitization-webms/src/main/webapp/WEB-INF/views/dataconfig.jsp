<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="row">
    <ul class="nav nav-tabs" id="dataConfigUl">
        <li role="presentation" class="active">
            <a href="#" onclick="showWelcomePage()" id="welcomePageTag">欢迎页</a>
        </li>
        <li role="presentation"><a href="#" onclick="showProxyPage()">转发配置</a></li>
        <li role="presentation"><a href="#" onclick="showAesPage()">加解密配置</a></li>
        <li role="presentation"><a href="#" onclick="showDefinePage()">防刷配置</a></li>
        <li role="presentation"><a href="#" onclick="showSessionPage()">session配置</a></li>
        <li role="presentation"><a href="#" onclick="showGroupPage()">group配置</a></li>
        <!--  
        	暂时处理掉 回头添加
        	<li role="presentation"><a href="#" onclick="showPvUvPage()">PvUv配置</a></li>
        -->
        <li role="presentation"><a href="#" onclick="showChainPage()">配置上下行链条</a></li>
    </ul>
</div>
<div class="row" id="dataConfigArea" style="margin-top: 10px;margin-bottom: 10px;">
</div>
<script src="${path}/common/js/dataconfig.js"></script>
<script>
    showWelcomePage();
</script>