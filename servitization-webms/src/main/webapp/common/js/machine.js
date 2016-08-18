/**
 * Created by demon on 15/7/3.
 */
function deleteMetadataMachine(field, event) {
    var id = $(field).parent().parent().attr('value');
	var metadataDescription = $("#metadataDescription").val();
    confirm("温馨提示", "确认删除?", function(){
		var url = getRootPath() + "/webms/machine/deleteMetadataMachine";
		$.ajax({
			url: url,
			data: {metadataId:id, metadataDescription:metadataDescription},
			type: 'POST',
			success: function (data, status, obj) {
				if(obj.status==202){
					alert("没有权限");
					return ;
				}
				alert( data);
				structureTree();
				showMetadataPage();
				//queryMetadataMachine(metadataId);
			},
			error: function (obj, status, e) {
				alert('发生错误: ' + JSON.stringify(e));
			},
			dataType: 'TEXT'
		});
    });
}

function addMachine() {
    var metadataId = $("#metadataId").val();
	var ip = $("#machineIp").val();
	var status = $("#status").val();
	var metadataDescription = $("#metadataDescription").val();

    if(!vf(metadataId)){
        alert("元数据唯一id不能为空");
        $("#metadataId").focus();
        return false;
    }

    if(!vf(ip)){
        alert("原数据的ip不能为空");
        $("#machineIp").focus();
        return false;
    }
    confirm("温馨提示", "确认添加?", function(){
		var params = {
			metadataId : metadataId,
			ip : ip,
			status:status,
			metadataDescription:metadataDescription
		};

		var url = getRootPath() + "/webms/machine/addMetadataMachine";
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

function queryMetadataMachine(metadataId) {
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
