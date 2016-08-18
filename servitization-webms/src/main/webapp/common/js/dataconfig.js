/**
 * Created by demon on 15/7/3.
 */

$("#dataConfigUl li a").click(function() {
	$(this).parent().siblings().removeClass("active");
	$(this).parent().addClass("active");
});

function home() {
	console.info("123");
}

/**
 * 获取数据配置欢迎页面
 */
function showWelcomePage() {
	var url = getRootPath() + "/webms/metadata/showWelcomePage";
	var params = {
		metadataId : METADATAOBJECT.id
	};

	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 获取放攻击配置页面
 */
function showDefinePage(pageIndex, pageSize) {
	var url = getRootPath() + "/webms/define/getDefinePage";

	var pageIndex = pageIndex || 0;
	var pageSize = pageSize || PAGESIZE;

	var sourceUrl = $("#sourceUrlParam").val();

	var params = {
		metadataId : METADATAOBJECT.id,
		sourceUrl : sourceUrl,
		pageIndex : pageIndex,
		pageSize : pageSize
	};

	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
			$("#sourceUrlParam").val(sourceUrl);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 获取上下链条配置页面
 */
function showChainPage() {
	var url = getRootPath() + "/webms/chain/getChainPage";
	var params = {
		metadataId : METADATAOBJECT.id
	};
	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 获取转发页面
 */
function showProxyPage(pageIndex, pageSize, isEmcf) {
	var url = getRootPath() + "/webms/proxy/getProxyPage";

	var pageIndex = pageIndex || 0;
	var pageSize = pageSize || PAGESIZE;

	var sourceUrl = $("#sourceUrlParam").val();
	var targetUrl = $("#targetUrlParam").val();

	var params = {
		metadataId : METADATAOBJECT.id,
		sourceUrl : sourceUrl,
		targetUrl : targetUrl,
		pageIndex : pageIndex,
		pageSize : pageSize
	};

	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
			$("#sourceUrlParam").val(sourceUrl);
			$("#targetUrlParam").val(targetUrl);
			if (isEmcf) {
				$('#emcfTableRadio').attr('checked', 'true');
				$('#emcfTable').css('display', 'block');
				$('#ordinaryTable').css('display', 'none');
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 获取session页面
 */
function showSessionPage(pageIndex, pageSize) {
	var url = getRootPath() + "/webms/session/getSessionPage";

	var pageIndex = pageIndex || 0;
	var pageSize = pageSize || PAGESIZE;

	var sourceUrl = $("#sourceUrlParam").val();

	var params = {
		metadataId : METADATAOBJECT.id,
		sourceUrl : sourceUrl,
		pageIndex : pageIndex,
		pageSize : pageSize
	};

	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
			$("#sourceUrlParam").val(sourceUrl);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}
/**
 * 获取group页面
 */
function showGroupPage() {
	var url = getRootPath() + "/webms/group/getGroupPage";
	var params = {
		metadataId : METADATAOBJECT.id
	};
	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}
/**
 * 得到pv、uv的页面
 */
function showPvUvPage(pageIndex, pageSize) {
	var url = getRootPath() + "/webms/pvUv/getPvUvPage";
	var pageIndex = pageIndex || 0;
	var pageSize = pageSize || PAGESIZE;

	var sourceUrl = $("#sourceUrlParam").val();

	var params = {
		metadataId : METADATAOBJECT.id,
		sourceUrl : sourceUrl,
		pageIndex : pageIndex,
		pageSize : pageSize
	};
	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, obj) {
			alert("系统出现异常");
		},
		dataType : 'html'
	});
}


/**
 * 白名单页面
 */
function showAesPage() {
	var url = getRootPath() + "/webms/aes/getWhitelistPage";
	var params = {
		metadataId : METADATAOBJECT.id
	};
	
	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
	
}