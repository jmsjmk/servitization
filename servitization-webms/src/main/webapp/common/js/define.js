/**
 * Created by demon on 15/7/7.
 */
var resource = new Array();
var globalProxyId;
var flag = 1;
$(document).ready(function() {

	globalProxyId = jQuery("#proxyId  option:selected").text();
	var table = document.getElementById("defineTable");
	if (table != null && table.rows != null && table.rows.length > 1) {
		table = table.rows;
		for (var index = 0; index < table.length; index++) {
			resource[index] = table[index].cells[2].innerHTML;
		}

		$("#sourceUrlParam").autocomplete({
			source : resource
		});
	}
});
function deleteManyMetadataDefine(event) {
	var tableId = "MetadataDefines";
	var ids = collectChosenId(tableId);
	if (!ids) {
		alert('请选择要删除的记录');
		return;
	}
	var params = {
		ids : ids
	};
	confirm(null, '确认删除?', function() {
		doDeleteMetadataDefine(params);
	});
	prevent(event);
}

function deleteOneMetadataDefine(field, event) {
	var ids = [];
	var id = $(field).parent().parent().attr('value');
	ids.push(id);
	var arg = {
		ids : JSON.stringify(ids)
	};
	confirm(null, '确认删除?', function() {
		doDeleteMetadataDefine(arg);
	});
	prevent(event);
}

function doDeleteMetadataDefine(params) {
	var url = getRootPath() + "/webms/define/deleteMetadataDefines";
	$.ajax({
		url : url,
		data : params,
		type : 'POST',
		success : function(data, status, obj) {
			if (obj.status == 202) {
				alert("没有权限");
				return;
			}
			alert(data);
			showDefinePage();
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'TEXT'
	});
}

/**
 * 添加防攻击页面
 */
function showAddDefinePage(field, event) {
	var url = getRootPath() + "/webms/define/getAddDefinePage";

	var params = {
		metadataId : METADATAOBJECT.id
	};

	if (field != null) {
		var proxyId = 0;
		proxyId = $(field).parent().parent().attr('value');
		params['proxyId'] = proxyId;
	}

	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			if (obj.status == 202) {
				alert("没有权限");
				return;
			}
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 
 */
function addDefine() {
	var url = getRootPath() + "/webms/define/addOrUpdateDefine";
	var proxyId = $("#proxyId").val();
	var timeUnit = $("#timeUnit").val();
	var times = $("#times").val();
	var defineId = $("#defineId").val();
	timeUnit = Number(timeUnit);
	if (timeUnit == 0) {
		alert("时间必须大于零!");
		$("#timeUnit").focus();
		return;
	}
	times = Number(times);
	if (times == 0) {
		alert("次数必须大于零!");
		$("#times").focus();
		return;
	}

	var params = {
		metadataId : METADATAOBJECT.id,
		proxyId : proxyId,
		timeUnit : timeUnit,
		times : times
	};

	if (vf(defineId)) {
		params.defineId = defineId;
	}
	$.ajax({
		url : url,
		data : params,
		type : 'POST',
		success : function(data, status, obj) {
			alert(data);
			if (data.indexOf("成功") != -1) {
				showDefinePage();
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});
}

function vertifyDefine() {
	var url = getRootPath() + "/webms/define/vertifyDefine";
	var proxyId = $("#proxyId").val();
	var defineId = $("#defineId").val();
	var params = {
		metadataId : METADATAOBJECT.id,
		proxyId : proxyId
	};
	var value = jQuery("#proxyId option:selected").text();
	// alert("defineId:" +defineId +"\t globalProxyId:" + globalProxyId +"\t value:" + value);
	if (defineId && value != globalProxyId) {
		$.ajax({
			url : url,
			data : params,
			type : 'get',
			success : function(data, status, obj) {
				if (parseInt(data) >= 1) {
					alert("请求地址已经存在");
					return;
				}
				addDefine();
			},
			error : function(obj, status, e) {
				alert('发生错误: ' + JSON.stringify(e));
			},
			dataType : 'json'
		});
	}
	if (defineId && value == globalProxyId) {
		addDefine();
		return;
	}
	if (defineId == "") {
		$.ajax({
			url : url,
			data : params,
			type : 'get',
			success : function(data, status, obj) {
				if (parseInt(data) >= 1) {
					alert("请求地址已经存在");
					return;
				}
				addDefine();
			},
			error : function(obj, status, e) {
				alert('发生错误: ' + JSON.stringify(e));
			},
			dataType : 'json'
		});
	}
}

/**
 * 白名单页面
 */
function showWhitelistDefinePage(field, event) {
	var url = getRootPath() + "/webms/define/getWhitelistPage";
	var params = {
		metadataId : METADATAOBJECT.id
	};
	
	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			if (obj.status == 202) {
				alert("没有权限");
				return;
			}
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 保存白名单
 */
function vertifyWhitelist() {
	var url = getRootPath() + "/webms/define/addOrUpdateWhitelist";
	var ips = $("#ips").val();
	var params = {
		metadataId : METADATAOBJECT.id,
		ips : ips
	};
	$.ajax({
		url : url,
		data : params,
		type : 'POST',
		success : function(data, status, obj) {
			alert(data);
			if (data.indexOf("成功") != -1) {
				showDefinePage();
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});
}

