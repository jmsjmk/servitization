/**
 * Created by demon on 15/7/7.
 */

var dualBox = null;
var poolName = null;
var isUpdate = false;
var resource = new Array();
var destination = new Array();
var originalSource = '';
$(document).ready(function() {
	$('#serviceType').change(function() {
		var type = $("#serviceType  option:selected").val();
		if (type == '0') {
			$('#httpSpecificPoolForm').css('display', 'block');
			$('#emcfSpecificPoolForm').css('display', 'none');
		} else if (type == '1') {
			$('#emcfSpecificPoolForm').css('display', 'block');
			$('#httpSpecificPoolForm').css('display', 'none');
		}
		assemableServicePoolName();
	});
	$('#getCb').removeAttr('checked');
	$('#postCb').removeAttr('checked');
	$('#putCb').removeAttr('checked');
	$('#deleteCb').removeAttr('checked');
	var v = $('#sourceMethodValue').val();
	if (v) {
		var arr = v.split(',');
		for (var i = 0; i < arr.length; i++) {
			$('#' + arr[i].toLowerCase() + 'Cb').prop('checked', 'true');
		}
	}
	$('#servicePoolSelect').change(onServicePoolSelectChange);

	$('#poolList').click(function(e) {
		e = e || window.event;
		var src = $(e.srcElement || e.target);
		if (src.is('a') && src.attr('id') != 'active') {
			$.each($('#poolList').children(), function(i, item) {
				if (i != 0) {
					$(item).css('background', '#FFFFFF');
				}
			});
			src.css('background', '#D3D3D3');
		}
	});
	var table = document.getElementById("orderTable");
	if (table != null && table.rows != null && table.rows.length > 1) {
		table = document.getElementById("orderTable").rows;

		for (var index = 0; index < table.length; index++) {
			resource[index] = table[index].cells[2].innerHTML;
			destination[index] = table[index].cells[5].innerHTML;
		}

		$("#sourceUrlParam").autocomplete({
			source : resource
		});
		$("#targetUrlParam").autocomplete({
			source : destination
		});
	}
});

function deleteManyProxy(event) {
	var tableId = parseInt($('input:radio[name="tableSelectMode"]:checked')
			.val()) == 1 ? "emcfTable" : "MetadataProxys";
	var ids = collectChosenId(tableId);
	if (!ids) {
		alert('请选择要删除的记录');
		return;
	}
	var params = {
		ids : ids
	};
	confirm("温馨提示", "确认删除?", function() {
		doDeleteMetadataProxy(params);
	});
	prevent(event);
}

function deleteOneMetadataProxy(field, event) {
	var ids = [];
	var id = $(field).parent().parent().attr('value');
	ids.push(id);
	var arg = {
		ids : JSON.stringify(ids)
	};
	confirm("温馨提示", "确认删除元数据么?", function() {
		doDeleteMetadataProxy(arg);
	});
	prevent(event);
}

function doDeleteMetadataProxy(params) {
	var url = getRootPath() + "/webms/proxy/deleteMetadataProxys";
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
			var n = parseInt($('input:radio[name="tableSelectMode"]:checked')
					.val());
			showProxyPage(null, null, n);
		},
		error : function(obj, status, e) {
			alert("系统提示", '发生错误: ' + JSON.stringify(e));
		},
		dataType : 'TEXT'
	});
}

/**
 * 添加请求代理页面
 */
function showAddProxyPage(field, event) {
	// alert("add proxyPage");
	var url = getRootPath() + "/webms/proxy/getAddProxyPage";

	var params = {
		metadataId : METADATAOBJECT.id
	};

	var proxyId = 0;
	var poolName = null;
	if (field != null) {
		var arr = $(field).parent().parent().attr('value').split('__');
		proxyId = arr[0];
		poolName = arr[1];
		params['proxyId'] = proxyId;
	}
	var n = parseInt($('input:radio[name="tableSelectMode"]:checked').val());
	// alert("n:" + n);
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
			loadPoolSelect(poolName);
			onServicePoolSelectChange();
			if (proxyId && n) {
				$('#emcfMode').attr('checked', 'true');
				$('#emcf').css('display', 'block');
				$('#ordinary').css('display', 'none');
			}
			if (proxyId) {// 修改
				originalSource = $("#sourceUrl").val();
				$.each($('input:radio[name="selectMode"]'), function(i, item) {
					$(item).attr('disabled', 'true');
				});
			} else {// 新增
				originalSource = '';
				$.each($('input:radio[name="selectMode"]'), function(i, item) {
					$(item).removeAttr('disabled');
				});
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

/**
 * 首先验证是否已经含有了sourceUrl
 */
function addProxy() {
	var path = getRootPath() + "/webms/proxy/isAgain";
	var proxyId = $("#proxyId").val();
	var sourceUrl = $("#sourceUrl").val();
	sourceUrl = sourceUrl.replace(/(^\s*)|(\s*$)/g, "");
	if (!sourceUrl) {
		alert('请填写源路径');
		return;
	}
	if (sourceUrl.substring(0, 1) != '/') {
		sourceUrl = '/' + sourceUrl;
	}

	var sourceMethod = assemableSourceMethod();
	if (!sourceMethod) {
		alert('请选择源请求方法');
		return;
	}

	var proxyId = $("#proxyId").val();
	var update = 0;
	if (vf(proxyId)) {
		proxyId = proxyId;
		update = 1;
	}
	if (update == 0) {
		$.ajax({
			url : path,
			data : {
				metadataId : METADATAOBJECT.id,
				sourceUrl : sourceUrl
			},
			type : 'get',
			success : function(data, status, obj) {
				if (data >= 1) {
					alert("已经存在相同的源路径");
					return;
				}
				add(proxyId, sourceUrl, sourceMethod);
			}
		});
	} else if (originalSource != sourceUrl) {
		$.ajax({
			url : path,
			data : {
				metadataId : METADATAOBJECT.id,
				sourceUrl : sourceUrl
			},
			type : 'get',
			success : function(data, status, obj) {
				if (data >= 1) {
					alert("已经存在相同的源路径");
				} else {
					add(proxyId, sourceUrl, sourceMethod);
				}
			}
		});
	} else{
		add(proxyId, sourceUrl, sourceMethod);
	}
}
function add(proxyId, sourceUrl, sourceMethod) {
	var url = getRootPath() + "/webms/proxy/addOrUpdateProxy";
	// var targetUrl = $("#targetUrl").val();
	var targetMethod = $("#targetMethod").val();
	var connectTimeout = $("#connectTimeout").val();
	var socketTimeout = $("#socketTimeout").val();
	var threshold = $("#threshold").val();
	var serviceName = $('#serviceName').val();
	var thresholdType = $("#thresholdType  option:selected").val();
	var serviceVersion = $('#serviceVersion').val();
	var servicePoolName = $("#servicePoolSelect option:selected").val();
	var serviceTypeStr = servicePoolName.substring(servicePoolName.length - 4,
			servicePoolName.length);
	var serviceType = serviceTypeStr == 'HTTP' ? '0' : '1';
	var isConvert = $("#isConvert").val();
	if (!serviceName) {
		alert('请填写服务名');
		return;
	}
	if (serviceType == '1' && serviceName.substring(0, 1) == '/') {
		serviceName = serviceName.substring(1, serviceName.length);
	}

	var params = {
		metadataId : METADATAOBJECT.id,
		proxyId : proxyId,
		sourceUrl : sourceUrl,
		sourceMethod : sourceMethod,
		targetMethod : targetMethod,
		connectTimeout : connectTimeout,
		socketTimeout : socketTimeout,
		threshold : threshold,
		thresholdType : thresholdType,
		serviceName : serviceName,
		serviceVersion : serviceVersion,
		servicePoolName : servicePoolName,
		isConvert : isConvert,
		serviceType : serviceType
	};

	$.ajax({
		url : url,
		data : params,
		type : 'POST',
		success : function(data, status, obj) {
			alert(data);
			if (data.indexOf("成功")) {
				showProxyPage();
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});
}
function onPlItemClick(field) {
	var str = $(field).attr('value');
	arr = str.split(',');
	$('#poolId').val(arr[0]);
	$('#servicePoolName').val(arr[1]);
	$('#oldServicePoolName').val(arr[1]);
	$('#poolUrl').val(arr[2]);
	var children = $('#serviceType').children();
	if (arr[3] == '0') {
		$(children[0]).attr('selected', 'selected');
		$('#httpSpecificPoolForm').css('display', 'block');
		$('#emcfSpecificPoolForm').css('display', 'none');
	} else if (arr[3] == '1') {
		$(children[1]).attr('selected', 'selected');
		$('#emcfSpecificPoolForm').css('display', 'block');
		$('#httpSpecificPoolForm').css('display', 'none');
	}
	$('#coefficient').val(arr[4]);
	var children = $('#forceCloseChannel').children();
	if (arr[5] == '0') {
		$(children[0]).attr('checked', 'checked');
		$(children[1]).removeAttr('checked');
	} else {
		$(children[1]).attr('checked', 'checked');
		$(children[0]).removeAttr('checked');
	}
	$('#forceCloseTimeMillis').val(arr[6]);
	$('#poolConnectTimeout').val(arr[7]);
	isUpdate = true;
	// alert("insert isUpa:" + isUpdate);
}

function selectPools() {
	$.get(getRootPath() + "/webms/servicePool/selectPools",
					{'metadataId' : METADATAOBJECT.id},
					function(data, status) {
						if (data) {
							l = eval(data);
							var pl = $('#poolList');
							pl.html('');
							pl.append('<a href="#" id="active" class="list-group-item active">连接池列表</a>');
							for (var i = 0; i < l.length; i++) {
								var item = $('<a href="#" class="list-group-item" onclick="onPlItemClick(this)" value="'
										+ l[i].id
										+ ','
										+ l[i].servicePoolName
										+ ','
										+ l[i].url
										+ ','
										+ l[i].serviceType
										+ ','
										+ l[i].coefficient
										+ ','
										+ l[i].forceCloseChannel
										+ ','
										+ l[i].forceCloseTimeMillis
										+ ','
										+ l[i].connectTimeout
										+ '">'
										+ l[i].servicePoolName + '</a>');
								pl.append(item);
								if (i == 0) {
									item.click();
								}
							}
						}
					});
}

function insertOrUpdatePoolById() {
	if (isUpdate) {
		updatePoolById();
	} else {
		insertPool();
	}
}

function onInsertPoolBtnClick() {
	$('#servicePoolName').val('');
	$('#poolUrl').val('');
	$('#coefficient').val('');
	$('#forceCloseChannel').val('');
	$('#forceCloseTimeMillis').val('');
	$('#poolConnectTimeout').val('');
	$($('#serviceType').children()[0]).attr('checked', 'checked');
	isUpdate = false;
}

function insertPool() {
	var servicePoolName = $('#servicePoolName').val();
	var url = $('#poolUrl').val();
	var serviceType = $("#serviceType  option:selected").val();
	var coefficient = $('#coefficient').val();
	var forceCloseChannel = $('#forceCloseChannel').val();
	var forceCloseTimeMillis = $('#forceCloseTimeMillis').val();
	var connectTimeout = $('#poolConnectTimeout').val();
	if (!servicePoolName || !url) {
		alert('连接池名和url不能为空');
		return;
	}
	if (!IsURL(url)) {
		alert('url不合法，请重新输入');
		return;
	}
	$.post(getRootPath() + "/webms/servicePool/insertPool", {
		metadataId : METADATAOBJECT.id,
		servicePoolName : servicePoolName,
		url : url,
		serviceType : serviceType,
		coefficient : coefficient,
		forceCloseChannel : forceCloseChannel,
		forceCloseTimeMillis : forceCloseTimeMillis,
		connectTimeout : connectTimeout
	}, function(data, status) {
		alert(data);
		selectPools();
		loadPoolSelect(poolName);
	});
}

function updatePoolById() {
	var servicePoolName = $('#servicePoolName').val();
	var oldServicePoolName = $('#oldServicePoolName').val();
	var url = $('#poolUrl').val();
	var serviceType = $("#serviceType  option:selected").val();
	var coefficient = $('#coefficient').val();
	var forceCloseChannel = $('#forceCloseChannel').val();
	var forceCloseTimeMillis = $('#forceCloseTimeMillis').val();
	var connectTimeout = $('#poolConnectTimeout').val();
	if (!servicePoolName || !url) {
		alert('连接池名和url不能为空');
		return;
	}
	if (!IsURL(url)) {
		alert('url不合法，请重新输入');
		return;
	}
	$.post(getRootPath() + "/webms/servicePool/updatePoolByName", {
		metadataId : METADATAOBJECT.id,
		servicePoolName : servicePoolName,
		oldServicePoolName : oldServicePoolName,
		url : url,
		serviceType : serviceType,
		coefficient : coefficient,
		forceCloseChannel : forceCloseChannel,
		forceCloseTimeMillis : forceCloseTimeMillis,
		connectTimeout : connectTimeout
	}, function(data, status) {
		alert(data);
		selectPools();
		loadPoolSelect(poolName);
		if (data.indexOf('succeed') != -1) {
			$('#oldServicePoolName').val(servicePoolName);
		}
	});
}

function deletePoolByName() {
	var name = $('#servicePoolName').val();
	$.get(getRootPath() + "/webms/proxy/selectServiceNameByServicePoolName", {
		metadataId : METADATAOBJECT.id,
		servicePoolName : name
	}, function(data, status) {
		if (data && data != "success") {
			alert(data);
			return;
		}
		$.post(getRootPath() + "/webms/servicePool/deletePoolByName", {
			metadataId : METADATAOBJECT.id,
			servicePoolName : name
		}, function(data, status) {
			alert(data);
			selectPools();
			loadPoolSelect(poolName);
		});
	});
}

/**
 * 根据poolName load池信息
 * @param name
 */
function loadPoolSelect(name) {
	$.get(getRootPath() + "/webms/servicePool/selectPools", {
		'metadataId' : METADATAOBJECT.id
	}, function(data, status) {
		if (data) {
			var html = '';
			l = eval(data);
			poolName = name;
			for (var i = 0; i < l.length; i++) {
				if (name == l[i].servicePoolName) {
					html += '<option value="' + name + '" type="'
							+ l[i].serviceType + '" selected>' + name
							+ '</option>';
				} else {
					html += '<option value="' + l[i].servicePoolName
							+ '" type="' + l[i].serviceType + '">'
							+ l[i].servicePoolName + '</option>';
				}
			}
			$('#servicePoolSelect').html(html);
			onServicePoolSelectChange();
		}
	});
}

function assemableServicePoolName() {
	var url = $('#poolUrl').val();
	var serviceType = $("#serviceType  option:selected").val();
	if (!url || !serviceType) {
		$('#servicePoolName').val('');
		return;
	}
	if (serviceType == '0') {
		serviceType = 'HTTP';
	} else if (serviceType == '1') {
		serviceType = 'EMCF';
	}
	$('#servicePoolName').val(url + '_' + serviceType);
}

function assemableSourceMethod() {
	var result = '';
	if ($('#getCb').prop('checked')) {
		result += 'GET,';
	}
	if ($('#postCb').prop('checked')) {
		result += 'POST,';
	}
	if ($('#putCb').prop('checked')) {
		result += 'PUT,';
	}
	if ($('#deleteCb').prop('checked')) {
		result += 'DELETE,';
	}
	if (result.length > 0) {
		result = result.substring(0, result.length - 1);
	}
	return result;
}

function IsURL(url) {
	var regexp = new RegExp(".+?:[0-9]{1,5}$", "gi");
	if (regexp.test(url)) {
		return (true);
	} else {
		return (false);
	}
}

function onServicePoolSelectChange() {
	var type = $("#servicePoolSelect option:selected").attr('type');
	if (type == '0') {
		$('#httpFormDiv').css('display', 'block');
		$('#emcfFormDiv').css('display', 'none');
	} else if (type == '1') {
		$('#emcfFormDiv').css('display', 'block');
		$('#httpFormDiv').css('display', 'none');
	}
}
