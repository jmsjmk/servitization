/**group添加修改界面 */
function showAddGroupPage(groupId){
	var url = getRootPath() + "/webms/group/getAddGroupPage";
	var params = {
		metadataId: METADATAOBJECT.id
	};
	if(groupId != null) {
		params['groupId'] = groupId;
	}
	$.ajax({
		url: url,
		data: params,
		type: 'GET',
		success: function (data, status, obj) {
			if(obj.status==202){
				alert("没有权限");
				return ;
			}
			$("#dataConfigArea").html(data);
		},
		error: function (obj, status, e) {
			alert('发生错误: ' + JSON.stringify(e));
		},
		dataType: 'html'
	});
}

function addGroup(){
	var url = getRootPath() + "/webms/group/addOrUpdateGroup";
    var name = $("#name").val();
    var processTimeOut = $("#processTimeOut").val();
    var size = $("#size").val();
    var policy = $("#policy").val();
    var upModules=""; 
    $('input[name="upModule"]:checked').each(function(){ 
    	upModules+=$(this).val()+","; 
    }); 
    var downModules=""; 
    $('input[name="downModule"]:checked').each(function(){ 
    	downModules+=$(this).val()+","; 
    }); 
    if(upModules.length>0){
    	upModules = upModules.substring(0, upModules.length-1);
    }
    if(downModules.length>0){
    	downModules = downModules.substring(0, downModules.length-1);
    }
    var params = {
        metadataId : METADATAOBJECT.id,
        upModules : upModules,
        downModules : downModules,
        name : name,
        processTimeOut : processTimeOut,
        size : size,
        policy : policy
    };

    var groupId = $("#groupId").val();
    if(vf(groupId)){
        params.groupId = groupId;
    }
    
    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
        	alert(data);
            if(data.indexOf("成功")>0) {
                showGroupPage();
            }
        },
        error: function (obj, status, e) {
        	alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'text'
    });
}

function deleteGroup(groupId){
	var url = getRootPath() + "/webms/group/delGroup";
	var params = {
	        metadataId: METADATAOBJECT.id
	    };
	    if(groupId != null) {
	        params['groupId'] = groupId;
	    }
	    confirm("温馨提示", "确认删除?", function(){
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
		            showGroupPage();
		        },
		        error: function (obj, status, e) {
		        	alert('发生错误: ' + JSON.stringify(e));
		        },
		        dataType: 'text'
		    });
	    });
}