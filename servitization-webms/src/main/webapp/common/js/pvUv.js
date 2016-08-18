$(document).ready(function() {
		$(".pv li").bind('click', function() {
			var parent_id = $(this).parent().attr("id");
			var proxyId = $(this).attr("proxyId");
			var sources = $(this).attr("sources");
			//此时是添加操作 
			if (parent_id == "box") {
				vertifyPvUv(proxyId,this,"#box2");
			//删除操作 
			} else {
				del(proxyId,this,"#box");
			}
		})
	});

/**
 * 批量删除
 */
function deleteManyMetadataPvUv(event) {
	var tableId = "pvUvTable";
	var ids = collectChosenId(tableId);
	if (!ids) {
		alert('请选择要删除的记录');
		return;
	}
	var params = {
		ids : ids
	};
	confirm(null, '确认删除?', function() {
		doManyDelete(params);
	});
	prevent(event);
}
function doManyDelete(params) {
	var url = getRootPath() + "/webms/pvUv/deleteMany";
	$.ajax({
		url : url,
		data : params,
		type : 'post',
		success : function(data, status, obj) {
			if (obj.status == 202) {
				alert("没有权限");
				return;
			}
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
			alert("系统错误");
		},
		dataType : 'html'
	});
}
/**
 * 
 * 删除操作、不过得先看看有没有权限
 */
function del(proxyId,current,id) {
	var url = getRootPath() + "/webms/pvUv/delete";
	$(id+" li").removeClass("changeStyle");
	var params = {
		metadataId : METADATAOBJECT.id,
		proxyId : proxyId
	};
	confirm(null, '确认删除?', function() {
		$.ajax({
			url : url,
			data : params,
			type : "post",
			success : function(data, status, obj) {
				if (obj.status == 202) {
					alert("没有权限");
					return;
				}
				var selector = $(id+" li");
				if(data>=1){
					if(selector.length==0){
						$(id).append(current);
					}else{
						$(id+" li:first").before(current);
					}
					$(current).addClass("changeStyle");
				}else{
					alert("删除失败");
				}
			},
			error : function(obj, status, e) {
				alert("系统出现错误");
			},
			dataType : 'json'
		});
	});
}

/**
 * 添加pv、uv
 */
function showAddPvUvPage() {
	var url = getRootPath() + "/webms/pvUv/showProxyList";
	var params = {
		metadataId : METADATAOBJECT.id
	};

	$.ajax({
		url : url,
		data : params,
		type : "GET",
		success : function(data, status, obj) {
			$("#dataConfigArea").html(data);
		},
		error : function(obj, status, e) {
		}

	});
}
/**
 * 保存pv、uv,首先得检查是否有权限，没有权限的话，是不能添加的
 */
function vertifyPvUv(proxyId,currentobj,id) {
	var url = getRootPath() + "/webms/pvUv/addPvUv";
	var params = {
		metadataId : METADATAOBJECT.id,
		proxyId : proxyId
	};
	$(id+" li").removeClass("changeStyle");
	$.ajax({
		url : url,
		data : params,
		type : 'post',
		success : function(data, status, obj) {
			if (obj.status == 202) {
				alert("没有权限");
				return;
			}
			if (data == -1) {
				alert("传入参数metadataId为空");
				return;
			}

			if (data == -2) {
				alert("不能添加相同打得数据");
				return;
			}
			var selector = $(id+" li");
			if(selector.length==0){
				$(id).append(currentobj);
			}else{
				$(id+" li:first").before(currentobj);
			}
			$(currentobj).addClass("changeStyle");
		},
		error : function(obj, status, e) {
			alert("系统出现异常");
			console.log(obj);
			console.log(status);
			console.log(e);
		},
		dataType : 'text'
	});
}
