<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
#footer {
	height: 34px;
	vertical-align: middle;
	text-align: right;
	clear: both;
	padding-right: 3px;
	background-color: #D4D4D4;
	margin-top: 2px;
	width: 790px;
}

#footer form {
	margin: 0px;
	margin-top: 2px;
}

#dhtmlgoodies_dragDropContainer { /* Main container for this script */
	width: 790px;
	height: auto;
	border: 15px solid #D4D4D4;
	background-color: #FFF;
	-moz-user-select: none;
}

#dhtmlgoodies_dragDropContainer ul { /* General rules for all <ul> */
	margin-top: 0px;
	margin-left: 0px;
	margin-bottom: 0px;
	padding: 2px;
}

#dhtmlgoodies_dragDropContainer li, #dragContent li, li#indicateDestination
	{ /* Movable items, i.e. <LI> */
	list-style-type: none;
	height: 20px;
	background-color: #EEE;
	border: 1px solid #000;
	padding: 2px;
	margin-bottom: 2px;
	cursor: pointer;
	font-size: 0.9em;
}

li#indicateDestination {
	/* Box indicating where content will be dropped - i.e. the one you use if you don't use arrow */
	border: 1px solid #317082;
	background-color: #FFF;
}

/* LEFT COLUMN CSS */
div#dhtmlgoodies_listOfItems { /* Left column "Available students" */
	float: left;
	padding-left: 10px;
	padding-right: 10px;
	/* CSS HACK */
	width: 180px; /* IE 5.x */
	width /* */: /**/ 160px; /* Other browsers */
	width: /**/ 160px;
}

#dhtmlgoodies_listOfItems ul { /* Left(Sources) column <ul> */
	height: 140px;
}

div#dhtmlgoodies_listOfItems div {
	border: 1px solid #999;
}

div#dhtmlgoodies_listOfItems div ul { /* Left column <ul> */
	margin-left: 10px;
	/* Space at the left of list - the arrow will be positioned there */
}

#dhtmlgoodies_listOfItems div p, .p { /* Heading above left column */
	margin: 0px;
	font-weight: bold;
	padding-left: 12px;
	background-color: #317082;
	color: #FFF;
	margin-bottom: 5px;
}
/* END LEFT COLUMN CSS */
#dhtmlgoodies_dragDropContainer .mouseover {
	/* Mouse over effect DIV box in right column */
	background-color: #E2EBED;
	border: 1px solid #317082;
}

/* Start main container CSS */
div#dhtmlgoodies_mainContainer { /* Right column DIV */
	width: 590px;
	float: left;
}

#dhtmlgoodies_mainContainer div { /* Parent <div> of small boxes */
	float: left;
	margin-right: 10px;
	margin-bottom: 10px;
	margin-top: 0px;
	border: 1px solid #999;
	/* CSS HACK */
	width: 172px; /* IE 5.x */
	width /* */: /**/ 170px; /* Other browsers */
	width: /**/ 170px;
}
#allItems,#allItems2,#box1,#box2{
overflow:auto;
}
#dhtmlgoodies_mainContainer div ul {
	margin-left: 10px;
}

#dhtmlgoodies_mainContainer div p { /* Heading above small boxes */
	margin: 0px;
	padding: 0px;
	padding-left: 12px;
	font-weight: bold;
	background-color: #317082;
	color: #FFF;
	margin-bottom: 5px;
}

#dhtmlgoodies_mainContainer ul {
	/* Small box in right column ,i.e <ul> */
	width: 152px;
	height: 80px;
	border: 0px;
	margin-bottom: 0px;
	overflow: hidden;
}

#dragContent { /* Drag container */
	position: absolute;
	width: 150px;
	height: 20px;
	display: none;
	margin: 0px;
	padding: 0px;
	z-index: 2000;
	board: sold
}

#dragDropIndicator { /* DIV for the small arrow */
	position: absolute;
	width: 7px;
	height: 10px;
	display: none;
	z-index: 1000;
	margin: 0px;
	padding: 0px;
}
</style>
<script type="text/javascript">
	/* VARIABLES YOU COULD MODIFY */
	var boxSizeArray = [ 13, 13, 13, 13 ]; // Array indicating how many items there is rooom for in the right column ULs
	var arrow_offsetX = -5; // Offset X - position of small arrow
	var arrow_offsetY = 0; // Offset Y - position of small arrow
	var arrow_offsetX_firefox = -6; // Firefox - offset X small arrow
	var arrow_offsetY_firefox = -13; // Firefox - offset Y small arrow
	var verticalSpaceBetweenListItems = 3; // Pixels space between one <li> and next	
	// Same value or higher as margin bottom in CSS for #dhtmlgoodies_dragDropContainer ul li,#dragContent li
	var indicateDestionationByUseOfArrow = true; // Display arrow to indicate where object will be dropped(false = use rectangle)
	var cloneSourceItems = false; // Items picked from main container will be cloned(i.e. "copy" instead of "cut").	
	var cloneAllowDuplicates = false; // Allow multiple instances of an item inside a small box(example: drag Student 1 to team A twice
	/* END VARIABLES YOU COULD MODIFY */
	var dragDropTopContainer = false;
	var dragTimer = -1;
	var dragContentObj = false;
	var contentToBeDragged = false; // Reference to dragged <li>
	var contentToBeDragged_src = false; // Reference to parent of <li> before drag started
	var contentToBeDragged_next = false; // Reference to next sibling of <li> to be dragged
	var destinationObj = false; // Reference to <UL> or <LI> where element is dropped.
	var dragDropIndicator = false; // Reference to small arrow indicating where items will be dropped
	var ulPositionArray = new Array();
	var mouseoverObj = false; // Reference to highlighted DIV
	var MSIE = navigator.userAgent.indexOf('MSIE') >= 0 ? true : false;
	var navigatorVersion = navigator.appVersion.replace(/.*?MSIE (\d\.\d).*/g,
			'$1') / 1;
	var oldIdentify; //用来防止不同链条的数据放在一起，下行的不能拖动到上行，上行不能到下行。
	var oldNodeId;
	var indicateDestinationBox = false;
    var arr=[] ;
	function getTopPos(inputObj) {
		var returnValue = inputObj.offsetTop;
		while ((inputObj = inputObj.offsetParent) != null) {
			if (inputObj.tagName != 'HTML')
				returnValue += inputObj.offsetTop;
		}
		return returnValue;
	}

	function getLeftPos(inputObj) {
		var returnValue = inputObj.offsetLeft;
		while ((inputObj = inputObj.offsetParent) != null) {
			if (inputObj.tagName != 'HTML')
				returnValue += inputObj.offsetLeft;
		}
		return returnValue;
	}

	function cancelEvent() {
		return false;
	}

	function initDrag(e) // Mouse button is pressed down on a LI
	{
		// alert("initDrag.");
		if (document.all)
			e = event;
		var st = Math.max(document.body.scrollTop,
				document.documentElement.scrollTop);
		var sl = Math.max(document.body.scrollLeft,
				document.documentElement.scrollLeft);
		dragTimer = 0;
		//鼠标点击时的坐标 
		dragContentObj.style.left = e.clientX + sl - 210 + 'px';
		dragContentObj.style.top = e.clientY + st-4 + 'px';
		contentToBeDragged = this;
		//this 对象为当前选中的对象;
		contentToBeDragged_src = this.parentNode;
		contentToBeDragged_next = false;
		//根据该属性来判断是在同一个div中拖动，还是拖曳到其他的div中。该值只是为box1或者box2
		oldNodeId = contentToBeDragged_src.getAttribute("id");
		///用来防止不同链条的数据放在一起，下行的不能拖动到上行，上行不能到下行。值为up或者down
		oldIdentify = contentToBeDragged_src.parentNode.getAttribute("tag");
		var children ;
		if(oldNodeId=="box1"){
			children = $("#box1").children("li");
		}
		if(oldNodeId=="box2"){
			children = $("#box2").children("li");
		}
		
		if(oldNodeId=="allItems2"){
			children = $("#allItems2").children("li");
		}
		if(oldNodeId=="allItems"){
			children = $("#allItems").children("li");
		}
		if(children!=null&&children.length>0){
			var len = children.length;
			for(var index=0;index<len;index++){
				arr[index] = children[index];
			}
		}
		
		if (this.nextSibling) {
			contentToBeDragged_next = this.nextSibling;
			if (!this.tagName && contentToBeDragged_next.nextSibling)
				contentToBeDragged_next = contentToBeDragged_next.nextSibling;
		}
		timerDrag();
		return false;
	}

	function timerDrag() {
		if (dragTimer >= 0 && dragTimer < 10) {
			dragTimer++;
			setTimeout('timerDrag()', 10);
			return;
		}
		//在鼠标选中时发生，而且只执行一次，直到鼠标放下在选中时在执行
		if (dragTimer == 10) {
			if (cloneSourceItems
					&& contentToBeDragged.parentNode.id == 'allItems') {
				newItem = contentToBeDragged.cloneNode(true);
				newItem.onmousedown = contentToBeDragged.onmousedown;
				contentToBeDragged = newItem;
			}
			if (cloneSourceItems
					&& contentToBeDragged.parentNode.id == 'allItems2') {
				newItem = contentToBeDragged.cloneNode(true);
				newItem.onmousedown = contentToBeDragged.onmousedown;
				contentToBeDragged = newItem;
			}
			dragContentObj.style.display = 'block';
			dragContentObj.appendChild(contentToBeDragged);
		}
	}

	function moveDragContent(e) {
		if (dragTimer < 10) {
			if (contentToBeDragged) {
				if (contentToBeDragged_next) {
					contentToBeDragged_src.insertBefore(contentToBeDragged,
							contentToBeDragged_next);
				} else {
					contentToBeDragged_src.appendChild(contentToBeDragged);
				}
			}
			return;
		}
		if (document.all)
			e = event;
		var st = Math.max(document.body.scrollTop,
				document.documentElement.scrollTop);
		var sl = Math.max(document.body.scrollLeft,
				document.documentElement.scrollLeft);
		//被选中的div的位置
		dragContentObj.style.left = e.clientX + sl - 210 + 'px';
		dragContentObj.style.top = e.clientY + st + 'px';
		if (mouseoverObj)
			mouseoverObj.className = '';
		destinationObj = false;
		dragDropIndicator.style.display = 'none';
		if (indicateDestinationBox)
			indicateDestinationBox.style.display = 'none';
		var x = e.clientX + sl;
		var y = e.clientY + st;
		var width = dragContentObj.offsetWidth;
		var height = dragContentObj.offsetHeight;
		var tmpOffsetX = arrow_offsetX;
		var tmpOffsetY = arrow_offsetY;
		if (!document.all) {
			tmpOffsetX = arrow_offsetX_firefox;
			tmpOffsetY = arrow_offsetY_firefox;
		}
		for (var no = 0; no < ulPositionArray.length; no++) {
			var ul_leftPos = ulPositionArray[no]['left'];
			var ul_topPos = ulPositionArray[no]['top'];
			var ul_height = ulPositionArray[no]['height'];
			var ul_width = ulPositionArray[no]['width'];
			if ((x + width) > ul_leftPos && x < (ul_leftPos + ul_width)
					&& (y + height) > ul_topPos && y < (ul_topPos + ul_height)) {
				var noExisting = ulPositionArray[no]['obj']
						.getElementsByTagName('LI').length;
				if (indicateDestinationBox
						&& indicateDestinationBox.parentNode == ulPositionArray[no]['obj'])
					noExisting--;
				if (noExisting < boxSizeArray[no - 1] || no == 0) {
					dragDropIndicator.style.left = ul_leftPos + tmpOffsetX
							- 220 + 'px';
					var subLi = ulPositionArray[no]['obj']
							.getElementsByTagName('LI');
					var clonedItemAllreadyAdded = false;
					if (cloneSourceItems && !cloneAllowDuplicates) {
						for (var liIndex = 0; liIndex < subLi.length; liIndex++) {
							if (contentToBeDragged.id == subLi[liIndex].id)
								clonedItemAllreadyAdded = true;
						}
						if (clonedItemAllreadyAdded)
							continue;
					}
					for (var liIndex = 0; liIndex < subLi.length; liIndex++) {
						var tmpTop = getTopPos(subLi[liIndex]);
						if (!indicateDestionationByUseOfArrow) {
							if (y < tmpTop) {
								destinationObj = subLi[liIndex];
								indicateDestinationBox.style.display = 'block';
								subLi[liIndex].parentNode.insertBefore(
										indicateDestinationBox, subLi[liIndex]);
								break;
							}
						} else {
							if (y < tmpTop) {
								destinationObj = subLi[liIndex];
								dragDropIndicator.style.top = tmpTop
										+ tmpOffsetY
										- Math
												.round(dragDropIndicator.clientHeight / 2)+2
										+ 'px';
								dragDropIndicator.style.display = 'block';
								break;
							}
						}
					}
					if (!indicateDestionationByUseOfArrow) {
						if (indicateDestinationBox.style.display == 'none') {
							indicateDestinationBox.style.display = 'block';
							ulPositionArray[no]['obj']
									.appendChild(indicateDestinationBox);
						}
					} else {
						if (subLi.length > 0
								&& dragDropIndicator.style.display == 'none') {
							dragDropIndicator.style.top = getTopPos(subLi[subLi.length - 1])
									+ subLi[subLi.length - 1].offsetHeight
									+ tmpOffsetY + 'px';
							dragDropIndicator.style.display = 'block';
						}
						if (subLi.length == 0) {
							dragDropIndicator.style.top = ul_topPos
									+ arrow_offsetY + 'px'
							dragDropIndicator.style.display = 'block';
						}
					}
					if (!destinationObj)
						destinationObj = ulPositionArray[no]['obj'];
					mouseoverObj = ulPositionArray[no]['obj'].parentNode;
					mouseoverObj.className = 'mouseover';
					return;
				}
			}
		}
	}

	function dragDropEnd(e) {
		if (dragTimer == -1)
			return;
		if (dragTimer < 10) {
			dragTimer = -1;
			return;
		}
		dragTimer = -1;
		if (document.all)
			e = event;
		if (cloneSourceItems
				&& (!destinationObj || (destinationObj && (destinationObj.id == 'allItems'
						|| destinationObj.id == 'allItems2'
						|| destinationObj.parentNode.id == 'allItems2' || destinationObj.parentNode.id == 'allItems')))) {
			contentToBeDragged.parentNode.removeChild(contentToBeDragged);
		} else {
			//鼠标放下才执行
			if (destinationObj) {
				//该属性为拖放到那个div中
				var nowNodeId = destinationObj.getAttribute("id");
				//防止不同链条的值混乱，也就是上行不能到下行，下行不能到上行
				var nowIdentify = destinationObj.parentNode.getAttribute("tag");

				var tempId = destinationObj.parentNode.parentNode
						.getAttribute("tag");
				var nowNodeId2 = destinationObj.parentNode.getAttribute("id");
				//成立说明不能放到不同的链条中
				if (nowIdentify != oldIdentify
						&& oldIdentify != destinationObj.parentNode.parentNode
								.getAttribute("tag")) {
					$("#dragContent").empty();
					if (oldIdentify == "up") {
						if(oldNodeId=="allItems"){
							$("#allItems").append(contentToBeDragged);
							
						}else{
							$("#box1").append(contentToBeDragged.children);
							if(arr!=null&&arr.length>0){
								$("#box1").remove("li");
								for(var index=0;index<arr.length;index++){
									$("#box1").append(arr[index]);
								}
							}
							addChainData("box1");
						}
						
						alert("上行链条不能加到下行链条");
					}
					if (oldIdentify == "down") {
						
						if(oldNodeId=="allItems2"){
							$("#allItems2").append(contentToBeDragged);
						}else{
							$("#box2").append(contentToBeDragged.children);
							if(arr!=null&&arr.length>0){
								$("#box2").remove("li");
								for(var index=0;index<arr.length;index++){
									$("#box2").append(arr[index]);
								}
							}
							addChainData("box2");
						}
						
						alert("下行链条不能加到上行链条");
					}
					arr =[];
					contentToBeDragged = false;
					dragDropIndicator.style.display = 'none';
					if (indicateDestinationBox) {
						indicateDestinationBox.style.display = 'none';
						document.body.appendChild(indicateDestinationBox);
					}
					indicateDestinationBox.style.display = 'none';
					mouseoverObj = false;
					destinationObj =false;
					return;
				}
				arr =[];
				if (destinationObj.tagName == 'UL') {
					destinationObj.appendChild(contentToBeDragged);
				} else {
					destinationObj.parentNode.insertBefore(contentToBeDragged,
							destinationObj);
				}
				mouseoverObj.className = '';
				destinationObj = false;
				dragDropIndicator.style.display = 'none';
				if (indicateDestinationBox) {
					indicateDestinationBox.style.display = 'none';
					document.body.appendChild(indicateDestinationBox);
				}
				contentToBeDragged = false;
				//在同一个div中拖放，可以理解为为链条重新排序
				if ((oldNodeId == "box1" || oldNodeId == "box2")
						&& (nowNodeId == "box1" || nowNodeId == "box2")
						|| tempId == "up" || tempId == "down") {
					addChainData(oldNodeId);
				}

				//当从右侧添加数据到右侧的数据框时
				if ((oldNodeId == "allItems2" || oldNodeId == "allItems")
						&& ((nowNodeId == "box1" || nowNodeId == "box2"
								|| tempId == "up" || tempId == "down"))) {
					if (nowNodeId == "box1" || nowNodeId == "box2") {
						addChainData(nowNodeId);
					}
					if (tempId == "up" || tempId == "down") {
						addChainData(tempId, tempId);
					}
				}

				if ((oldNodeId == "box1" || oldNodeId == "box2")
						&& (nowNodeId == "allItems" || nowNodeId == "allItems2")) {
					addChainData(oldNodeId);
				}
				return;
			}
			if (contentToBeDragged_next) {
				contentToBeDragged_src.insertBefore(contentToBeDragged,
						contentToBeDragged_next);
			} else {
				contentToBeDragged_src.appendChild(contentToBeDragged);
			}
		}
		contentToBeDragged = false;
		dragDropIndicator.style.display = 'none';
		if (indicateDestinationBox) {
			indicateDestinationBox.style.display = 'none';
			document.body.appendChild(indicateDestinationBox);
		}
		mouseoverObj = false;
		//在同一个div中拖放，可以理解为为链条重新排序
		if ((oldNodeId == "box1" || oldNodeId == "box2")
				&& (nowNodeId == "box1" || nowNodeId == "box2")
				|| destinationObj.parentNode.parentNode.getAttribute("tag") == "up"
				|| destinationObj.parentNode.parentNode.getAttribute("tag") == "down") {
			addChainData(oldNodeId);
		}
	}
	/**
	 * 初始化拖拽组件 <br/>
	 *
	 *
	 */
	function initDragDropScript() {
		addChainData("up");
		addChainData("down");
		dragContentObj = document.getElementById('dragContent');
		dragDropIndicator = document.getElementById('dragDropIndicator');
		dragDropTopContainer = document
				.getElementById('dhtmlgoodies_dragDropContainer');
		document.documentElement.onselectstart = cancelEvent;
		;
		var listItems = dragDropTopContainer.getElementsByTagName('LI'); // Get array containing all <LI>
		var itemHeight = false;
		for (var no = 0; no < listItems.length; no++) {
			listItems[no].onmousedown = initDrag;
			listItems[no].onselectstart = cancelEvent;
			if (!itemHeight)
				itemHeight = listItems[no].offsetHeight;
			if (MSIE && navigatorVersion / 1 < 6) {
				listItems[no].style.cursor = 'hand';
			}
		}
		var mainContainer = document
				.getElementById('dhtmlgoodies_mainContainer');
		var uls = mainContainer.getElementsByTagName('UL');
		itemHeight = itemHeight + verticalSpaceBetweenListItems;
		for (var no = 0; no < uls.length; no++) {
			uls[no].style.height = itemHeight * boxSizeArray[no] + 'px';
		}
		var leftContainer = document.getElementById('dhtmlgoodies_listOfItems');
		var itemBox = leftContainer.getElementsByTagName('UL')[0];
		document.documentElement.onmousemove = moveDragContent; // Mouse move event - moving draggable div
		document.documentElement.onmouseup = dragDropEnd; // Mouse move event - moving draggable div
		var ulArray = dragDropTopContainer.getElementsByTagName('UL');
		for (var no = 0; no < ulArray.length; no++) {
			ulPositionArray[no] = new Array();
			ulPositionArray[no]['left'] = getLeftPos(ulArray[no]);
			ulPositionArray[no]['top'] = getTopPos(ulArray[no]);
			ulPositionArray[no]['width'] = ulArray[no].offsetWidth;
			ulPositionArray[no]['height'] = ulArray[no].clientHeight;
			ulPositionArray[no]['obj'] = ulArray[no];
		}
		if (!indicateDestionationByUseOfArrow) {
			indicateDestinationBox = document.createElement('LI');
			indicateDestinationBox.id = 'indicateDestination';
			indicateDestinationBox.style.display = 'none';
			document.body.appendChild(indicateDestinationBox);
		}
	}
	window.onload = initDragDropScript();
	//对右侧链条数据的操作
	function addChainData(chain) {
		var value = "";
		var chainOrder = "";
		var flag = true;
		if (chain == "up" || chain == "box1") {
			var ulElement = $("#box1")[0];
			var li = ulElement.childNodes;
			$("#upChainValue").val("");
			for (var i = 0; i < li.length; i++) {
				if (undefined != li[i].innerText) {
					value += li[i].getAttribute("value") + ",";
					if (flag) {
						chainOrder += li[i].innerText;
					} else {
						chainOrder += "<span class='glyphicon glyphicon-arrow-right' aria-hidden='true'>"
								+ li[i].innerText + "</span>";
					}
					flag = false;
				}
			}
			$("#upChainValue").val(value);
			$("#upChainOrder").empty();
			$("#upChainOrder").html(chainOrder);
		}
		flag = true;
		if (chain == "down" || chain == "box2") {
			var ulElement = $("#box2")[0];
			var li = ulElement.childNodes;
			$("#downChainValue").val("");
			for (var i = 0; i < li.length; i++) {
				if (undefined != li[i].innerText) {
					value += li[i].getAttribute("value") + ",";
					if (flag) {
						chainOrder += li[i].innerText;
					} else {
						chainOrder += "<span class='glyphicon glyphicon-arrow-right' aria-hidden='true'>"
								+ li[i].innerText + "</span>";
					}
					flag = false;
				}
			}
			$("#downChainValue").val(value);
			$("#downChainOrder").empty();
			$("#downChainOrder").html(chainOrder);
		}
	}
</script>
<link rel="stylesheet" type="text/css"
	href="${path}/common/css/bootstrap-duallistbox.css"></link>
<body>
	<div class="row">
		<div class="col-lg-12">
			<form class="form-horizontal">
				<div class="form-group"
					style="margin-right: 15px; margin-left: -112px;">
					<label for="deployModel" class="col-xs-2 control-label">部署策略</label>
					<div class="col-xs-2">
						<select class="form-control" id="deployModel">
							<option value="STANDALONE"
								<c:if test="${'STANDALONE' == metadata.deployModel}">selected</c:if>>STANDALONE</option>
							<option value="PLUGIN"
								<c:if test="${'PLUGIN' == metadata.deployModel}">selected</c:if>>PLUGIN</option>
						</select>
					</div>
				</div>
			</form>
		</div>
	</div>
	上行链条的顺序:
	<label id="upChainOrder"></label>
	<br /> 
	下行链条的顺序:
	<label id="downChainOrder"></label>

	<c:choose>
		<c:when test="${metadata.upChain == '' or metadata.upChain == null}">
			<input type="hidden" id="upChainValue"
				value="<c:out value="${metadata.upChain}"/>">
		</c:when>
		<c:otherwise>
			<input type="hidden" id="upChainValue"
				value="<c:out value="${metadata.upChain}"/>,">
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when
			test="${metadata.downChain == '' or metadata.downChain == null}">
			<input type="hidden" id="downChainValue"
				value="<c:out value="${metadata.downChain}"/>">
		</c:when>
		<c:otherwise>
			<input type="hidden" id="downChainValue"
				value="<c:out value="${metadata.downChain}"/>,">
		</c:otherwise>
	</c:choose>
	<div id="dhtmlgoodies_dragDropContainer">
		<!-- left  begin            -->
		<div id="dhtmlgoodies_listOfItems" style="margin-top: 3%;">
			<div tag="up">
				<p class="p">上行链条(待选)</p>
				<ul id="allItems">
					<c:forEach var="m" items="${modules}" varStatus="status">
						<c:if test="${m.chain == 0}">
							<c:set value="m${m.id}" var="mId" />
							<c:set value="0" var="temp" />
							<c:forTokens items="${metadata.upChain}" delims="," var="up">
								<c:if test="${mId == up}">
									<c:set value="1" var="temp" />
								</c:if>
							</c:forTokens>
							<c:if test="${temp == 0}">
								<li id="${mId}" value="${mId}"><c:out value="${m.name}" /></li>
							</c:if>
						</c:if>
					</c:forEach>

					<c:forEach var="g" items="${groups}" varStatus="status">
						<c:set var="gid">
							<c:out value="${g.id}" />
						</c:set>
						<c:if test="${g.chain == 0}">
							<c:set value="0" var="temp" />
							<c:forTokens items="${metadata.upChain}" delims="," var="up">
								<c:if test="${up == gid}">
									<c:set value="1" var="temp" />
								</c:if>
							</c:forTokens>
							<c:if test="${temp==0 }">
								<li id="g${gid}" value="${gid}"><c:out value="${g.name}" /></li>
							</c:if>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div tag="down">
				<p class="p">下行链条(待选)</p>
				<ul id="allItems2">
					<c:forEach var="m" items="${modules}" varStatus="status">
						<c:if test="${m.chain == 1}">
							<c:set value="m${m.id}" var="mId" />
							<c:set value="0" var="temp" />
							<c:forTokens items="${metadata.downChain}" delims="," var="down">
								<c:if test="${mId == down}">
									<c:set value="1" var="temp" />
								</c:if>
							</c:forTokens>
							<c:if test="${temp==0 }">
								<li id="${mId}" value="${mId}"><c:out value="${m.name}" /></li>
							</c:if>
						</c:if>
					</c:forEach>

					<c:forEach var="g" items="${groups}" varStatus="status">
						<c:set var="gid">
							<c:out value="${g.id}" />
						</c:set>
						<c:set value="0" var="temp" />
						<c:if test="${g.chain == 1}">
							<c:forTokens items="${metadata.downChain}" delims="," var="down">
								<c:if test="${down == gid}">
									<c:set value="1" var="temp" />
								</c:if>
							</c:forTokens>
							<c:if test="${temp==0 }">
								<li id="g${gid}" value="${gid }"><c:out value="${g.name}" /></li>
							</c:if>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- left  end            -->
		<div id="dhtmlgoodies_mainContainer" style="margin-top: 3%;">
			<!-- ONE <UL> for each "room" -->
			<div tag="up">
				<p class="p">上行链条(已选)</p>
				<ul id="box1">
					<c:forTokens items="${metadata.upChain}" delims="," var="up">
						<c:choose>
							<c:when test="${fn:startsWith(up, 'm')}">
								<c:forEach var="m" items="${modules}">
									<c:set value="m${m.id}" var="mId" />
									<c:if test="${mId == up}">
										<li id="m${m.id}" value="m${m.id}">
											<c:out value="${m.name}" />
										</li>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var="g" items="${groups}">
									<c:if test="${g.id == up}">
										<li id="g${g.id}" value="${g.id}">
											<c:out value="${g.name}" />
										</li>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:forTokens>
				</ul>
			</div>
			<div tag="down">
				<p class="p">下行链条(已选)</p>
				<ul id="box2">
					<c:forTokens items="${metadata.downChain}" delims="," var="down">
						<c:choose>
							<c:when test="${fn:startsWith(down, 'm')}">
								<c:forEach var="m" items="${modules}">
									<c:set value="m${m.id}" var="mId" />
									<c:if test="${mId == down}">
										<li id="m${m.id}" value="m${m.id}">
											<c:out value="${m.name}" />
										</li>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var="g" items="${groups}">
									<c:if test="${g.id == down}">
										<li id="g${g.id}" value="${g.id}">
											<c:out value="${g.name}" />
										</li>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:forTokens>
				</ul>
			</div>
		</div>
	</div>
	<div id="footer">
		<button type="button" class="btn btn-default btn-primary"
			onclick="updateMetadataChain();">保存</button>
		<button type="button" class="btn btn-default"
			onclick="$('#welcomePageTag').click();">取消</button>
	</div>
	<ul id="dragContent"></ul>
	<div id="dragDropIndicator">
		<img src="${path}/common/img/insert.gif">
	</div>
	</div>
</body>
</html>
<script src="${path}/common/js/jquery.bootstrap-duallistbox.min.js"></script>
<script src="${path}/common/js/jquery-2.1.4.min.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/chain.js"></script>