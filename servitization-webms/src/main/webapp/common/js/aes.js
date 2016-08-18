/**
 * 保存白名单
 */
function vertifyWhitelist() {
	var url = getRootPath() + "/webms/aes/addOrUpdateWhitelist";
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
			if (obj.status == 202) {
				alert("没有权限");
				return;
			}
			alert(data);
		},
		error : function(obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType : 'text'
	});
}

