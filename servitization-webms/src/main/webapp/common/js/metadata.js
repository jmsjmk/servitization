/**
 * Created by demon on 15/7/3.
 */

function deleteOneMetadata(field, event) {
    var ids = [];
    var id = $(field).parent().parent().attr('value');
    ids.push(id);
    var arg = {
        ids: JSON.stringify(ids)
    };
    confirm("温馨提示", "确认删除?", function(){
    	 doDeleteMetadata(arg);
    });
    prevent(event);
}

function doDeleteMetadata(params){
    var url = getRootPath() + "/webms/metadata/deleteMetadatas";
    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
        	if(obj.status==202){
        		alert("没有权限");
        		return ;
        	}
        	alert( data);
            structureTree();
            showMetadataPage();
        },
        error: function (obj, status, e) {
        	alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'TEXT'
    });
}

function addMetadata() {
    var metaKey = $("#metaKey").val();
    var description = $("#description").val();

    if(!vf(metaKey)){
        alert("元数据唯一id不能为空");
        $("#metaKey").focus();
        return false;
    }

    if(!vf(description)){
        alert("元数据名称不能为空");
        $("#description").focus();
        return false;
    }
    confirm("温馨提示", "确认添加?", function(){
		var params = {
			metaKey : metaKey,
			description : description
		};

		var url = getRootPath() + "/webms/metadata/addMetadata";
		$.ajax({
			url : url,
			data : params,
			type : 'POST',
			success : function(data, status, obj) {
				if(obj.status==202){
					alert("没有权限");
					return ;
				}
				alert( data);
				structureTree();
				showMetadataPage();
			},
			error : function(obj, status, e) {
				alert('发生错误: ' + JSON.stringify(e));
			},
			dataType : 'TEXT'
		});
    });
}

function queryMetadataMachine(field, event) {
	var metadataId = $(field).parent().parent().attr('value');

	var url = getRootPath() + "/webms/machine/getMachineListByMtdtId";
	var params = {
		metadataId:metadataId
	};
	$.ajax({
		url: url,
		data: params,
		type: 'GET',
		success: function (data, status, obj) {
			$("#mainRight").html(data);
		},
		error: function (obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType: 'html'
	});
}


function addMetadataByCopy(field, event) {
    var metaKey = $("#metaKey").val();
    var description = $("#description").val();
    var copyMetaId = $(field).parent().parent().attr('value');

    if(!vf(metaKey)){
        alert("元数据唯一id不能为空");
        $("#metaKey").focus();
        return false;
    }

    if(!vf(description)){
    	alert("元数据名称不能为空");
        $("#description").focus();
        return false;
    }
    confirm("温馨提示", "确认复制添加?", function(){
		var params = {
			metaKey     : metaKey,
			description : description,
			copyFrom    : copyMetaId
		};
		doAddMetadataByCopy(params);
    });
    prevent(event);
}

function doAddMetadataByCopy(params){
	var url = getRootPath() + "/webms/metadata/addMetadataByCopy";
	$.ajax({
		url : url,
		data : params,
		type : 'POST',
		success : function(data, status, obj) {
			if(obj.status==202){
				alert("没有权限");
				return ;
			}
			alert(data);
			structureTree();
			showMetadataPage();
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'TEXT'
	});
}