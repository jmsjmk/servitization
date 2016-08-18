/**
 * Created by demon on 15/7/20.
 */

function addNodes() {
	var url = getRootPath() + "/webms/node/addNodes";

	var ids = $("#nodes").val();

	if (!vf(ids) || !vf(ids.trim())) {
		alert("请输入需要关联的aos节点id!");
		$("#nodes").focus();
		return;
	}

	var params = {
		metadataId : METADATAOBJECT.id,
		nodeIds : ids.trim()
	};

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
			if (data.indexOf("成功") != -1) {
				showNodePage();
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});

}

function delNode(field, event) {
	var url = getRootPath() + "/webms/node/delNode";

	var params = {
		metadataId : METADATAOBJECT.id
	};

	if (field != null) {
		var id = $(field).parent().parent().attr('value');
		params['id'] = id;
	}

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
			if (data.indexOf("成功") != -1) {
				showNodePage();
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});

	prevent(event);
}

function getPublishVersion(aosRelationId, nodeId) {
	var url = getRootPath() + "/webms/publish/publishMetadataSelectVersion";

	var params = {
		metadataId : METADATAOBJECT.id,
		aosRelationId : aosRelationId,
		aosNodeId : nodeId
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
			$('#myModal').modal({
				keyboard : true
			});
			$("#myModalDialog").html(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

function publishMetadata() {
	var url = getRootPath() + "/webms/publish/publish";
	var nodeRelationId = $('input[name="aosRelationId"]').val();
	var nodeId = $('input[name="aosNodeId"]').val();
	var versionId = $('input[name="versionSelectRadio"]:checked').val()+"";
	versionId = versionId.replace(/(^\s*)|(\s*$)/g, "");
	if(versionId == '' || versionId == null || versionId == 'undefined'){
		alert("请选择版本");
		return ;
	}
	
	var params = {
		metadataId : METADATAOBJECT.id,
		nodeRelationId : nodeRelationId,
		versionId : versionId,
		nodeId : nodeId
	};

	$.ajax({
		url : url,
		data : params,
		type : 'GET',
		success : function(data, status, obj) {
			alert(data);
			if (data.indexOf("成功") != -1) {
				showNodePage();
			}
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});
}

function showHistoryPage(nodeRelationId, pageIndex, pageSize) {
	var url = getRootPath() + "/webms/publish/getPublishHistory";
	var pageIndex = pageIndex || 0;
	var pageSize = pageSize || PAGESIZE;
	var params = {
		metadataId : METADATAOBJECT.id,
		nodeRelationId : nodeRelationId,
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
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'html'
	});
}

function getPublishDetail(publishId, nodeRelationId) {
	var url = getRootPath() + "/webms/publish/getPublishDetail";
	var params = {
		publishId : publishId,
		nodeRelationId : nodeRelationId
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

t = null;

function getTree() {
	var setting = {
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				if (!treeNode.children || treeNode.children.length == 0) {
					var nodes = $('#selectedItems').val();
					if (nodes) {
						$('#selectedItems').val(nodes + ',' + treeNode.id);
					} else {
						$('#selectedItems').val(treeNode.id);
					}
				}
			}
		}
	};
	if (!t) {
		var tree = assembleTree(eval('(' + $('#treeStr').text() + ')'));
		t = $.fn.zTree.init($("#treeUl"), setting, tree);
	}
	$('#treeModal').modal('show');
}

function assembleTree(obj) {
	if (!obj) {
		return;
	}
	var map = {};
	for (var i = 0; i < obj.data.length; i++) {
		map[obj.data[i].id] = obj.data[i];
	}
	for (var i = 0; i < obj.data.length; i++) {
		var pObj = map[obj.data[i].pid];
		if (!pObj) {
			continue;
		}
		if (!pObj.children) {
			pObj.children = [];
		}
		pObj.children.push(obj.data[i]);
	}
	var root = map['43536'];
	root.open = true;
	return [ root ];
}

function eliminateNotes(root, text) {
	if (!root.children || root.children.length == 0) {
		if (root.name.toLowerCase().indexOf(text) == -1) {
			return false;
		}
		return true;
	}
	var isContain = false;
	for (var i = 0; i < root.children.length; i++) {
		var c = root.children[i].name.toLowerCase().indexOf(text) >= 0
				|| eliminateNotes(root.children[i], text);
		if (!c) {
			root.children.splice(i--, 1);
		}
		isContain = c || isContain;
	}
	return isContain;
}

function onTreeQueryChange() {
	var tree = assembleTree(eval('(' + $('#treeStr').text() + ')'));
	var text = $('#treeQuery').val();
	if (!eliminateNotes(tree[0], text.toLowerCase())) {
		tree = [];
	}
	var setting = {
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				if (!treeNode.children || treeNode.children.length == 0) {
					var nodes = $('#selectedItems').val();
					if (nodes) {
						$('#selectedItems').val(nodes + ',' + treeNode.id);
					} else {
						$('#selectedItems').val(treeNode.id);
					}
				}
			}
		}
	};
	t = $.fn.zTree.init($("#treeUl"), setting, tree);
}
