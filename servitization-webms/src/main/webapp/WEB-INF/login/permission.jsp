<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
*{padding:0; margin:0;}
body {background:#F5FAFD}
.mvBox{height:15px; background:#F5FAFD url(/common/permission/images/line.jpg) no-repeat left center; width:471px; position:relative; padding:0 30px; margin:0 auto;}
.mvBtn{position:absolute; left:50px; top:0;}
.mvTxt{height:50px; line-height:50px; width:531px; text-align:center; font-size:30px; color:#29B6FF; font-family:Arial; margin:0 auto;}
</style>

</head>

<body>
<div style="background:#F5FAFD; padding:50px;">
<div class="mvTxt">Loading<span class="mvSq">.</span><span class="mvSq">.</span><span class="mvSq">.</span></div>
<div class="mvTxt">正在跳转到登录界面</div>
<div class="mvBox">
 <img class="mvBtn" src="/common/permission/images/move.jpg" />
</div>
<script src="${path}/common/js/jquery.min.js"></script>
<script type="text/javascript">
var delVal=50;
var line =8;
var move = 1500;

var id_1 = setInterval(autoTsq,move);
var id_2 = setInterval(autoMove,line);
 function autoMove(){
	 delVal++;
	 if(delVal>460){
		clearInterval(id_1);
		clearInterval(id_2);
	    return ;
	 }
	 $(".mvBtn").css("left",delVal);
 }
 function autoTsq(){
	$(".mvSq").css("color","#F5FAFD");
	setTimeout(function(){$(".mvSq").eq(0).css("color","#29B6FF")},0);
	setTimeout(function(){$(".mvSq").eq(1).css("color","#29B6FF")},500);
	setTimeout(function(){$(".mvSq").eq(2).css("color","#29B6FF")},1000);
 }
</script>
</body>
</html>