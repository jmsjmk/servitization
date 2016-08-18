/**
 * Created by demon on 15/7/3.
 */

/**
 * 显示模块添加页面
 */
function showModuleAddPage() {
    var url = getRootPath() + "/webms/module/getModuleAddPage";
    var params = {};
    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
        	if(obj.status==202){
        		alert("没有权限");
        		return ;
        	}
            $("#mainRight").html(data);
        },
        error: function (obj, status, e) {
        	alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}

function addModule() {
    var moduleName = $("#moduleName").val();
    var handlerName = $("#handlerName").val();
    var handlerClazz = $("#handlerClazz").val();
    var chain = $("#chain").val();

    if(!vf(moduleName)){
    	alert( "模块名称不能为空");
        $("#moduleName").focus();
        return false;
    }
    
    if(!vf(handlerName)){
    	alert("处理类名称不能为空");
        $("#handlerName").focus();
        return false;
    }
    
    if(!vf(handlerClazz)){
    	alert("处理类全路径不能为空");
        $("#handlerClazz").focus();
        return false;
    }

    var params = {
        moduleName:moduleName,
    	handlerName:handlerName,
    	handlerClazz:handlerClazz,
    	chain: chain
    };
    var url2 = getRootPath() + "/webms/module/vertifyModule";
    $.ajax({
        url: url2,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            if(data>=1){
                alert("处理模块名和处理模块类路径不能有相同");
        		return ;
            }
            add(url,params);
        },
        error: function (obj, status, e) {
        	alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'TEXT'
    });
    var url = getRootPath() + "/webms/module/addModule";
}
function add(url ,params){
	$.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
        	alert( data);
            structureTree();
            showModulePage();
        },
        error: function (obj, status, e) {
        	alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'TEXT'
    });
}
function deleteOneMoudle(id){
    	var params = {
            id:id
        };
        var url = getRootPath() + "/webms/module/delModule";
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
                     structureTree();
                     showModulePage();
                 },
                 error: function (obj, status, e) {
                	 alert('发生错误: ' + JSON.stringify(e));
                 },
                 dataType: 'TEXT'
             });
        });
}

function updateOneMoudle(id){
    var moduleName = $("#moduleName").val();
    var handlerName =$("#handlerName").val();
    var handlerClazz =$("#handlerClazz").val();
    var chain =$("#chain").val();
    var id = $("#moduleId").val();
    
    var params = {
        id:id,
        handlerName:handlerName,
        moduleName: moduleName,
        handlerClazz:handlerClazz,
        chain:chain
    }; 
    var url = getRootPath() + "/webms/module/updateModule";
    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
            if(obj.status==202){
                alert("没有权限");
                return ;
            }
            $("#mainRight").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}


function preUpdateOneMoudle(id){
    var params = {
        id:id
    };
    var url = getRootPath() + "/webms/module/preUpdateModule";
    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
            if(obj.status==202){
                alert("没有权限");
                return ;
            }
            $("#mainRight").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}