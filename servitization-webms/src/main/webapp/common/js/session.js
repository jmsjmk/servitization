/**
 * Created by demon on 15/7/7.
 */
var resource = new Array();
var flag =1;
var oldValue;
$(document).ready(function() {
	oldValue = jQuery("#proxyId  option:selected").text();
	var table = document.getElementById("sessionTable");
	if(table!=null&&table.rows!=null&&table.rows.length>1){
		table = document.getElementById("sessionTable").rows;
		for (var index = 0; index < table.length; index++) {
			resource[index] = table[index].cells[2].innerHTML;
		}
	
	$("#sourceUrlParam").autocomplete({
		source : resource
	});
	}
});
function deleteManyMetadataSession(event){
    var tableId = "MetadataSessions";
    var ids = collectChosenId(tableId);
    if(!ids){
        alert('请选择要删除的记录');
        return;
    }
    var params = {
        ids: ids
    };
    confirm(null,'确认删除?',function(){
        doDeleteMetadataSession(params);
    });
    prevent(event);
}

function deleteOneMetadataSession(field, event) {
    var ids = [];
    var id = $(field).parent().parent().attr('value');
    ids.push(id);
    var arg = {
        ids: JSON.stringify(ids)
    };
    confirm(null,'确认删除?',function(){
        doDeleteMetadataSession(arg);
    });
    prevent(event);
}

function doDeleteMetadataSession(params){
    var url = getRootPath() + "/webms/session/deleteMetadataSessions";
    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
        	if(obj.status==202){
        		alert("没有权限");
        		return ;
        	}
        	
            alert(data);
            showSessionPage();
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'TEXT'
    });
}

/**
 * 添加防攻击页面
 */
function showAddSessionPage(field, event){
    var url = getRootPath() + "/webms/session/getAddSessionPage";
    var params = {
        metadataId: METADATAOBJECT.id
    };
    if(field != null) {
        var proxyId = 0;
        proxyId = $(field).parent().parent().attr('value');
        params['proxyId'] = proxyId;
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

/**
 *
 */
function addSession() {
    var url = getRootPath() + "/webms/session/addOrUpdateSession";
    var proxyId = $("#proxyId").val();
    var strategy = $("#strategy").val();
    // 系统扩充字段
	var reqType = $("#reqType").val();
	var validateurl = $("#validateurl").val();
	var validateMethod = $("#validateMethod").val()

    var params = {
        metadataId : METADATAOBJECT.id,
        proxyId : proxyId,
        strategy : strategy,
        reqType : reqType,
        validateurl: validateurl,
        validateMethod: validateMethod
        
    };
    var sessionId = $("#sessionId").val();
    if(vf(sessionId)){
        params.sessionId = sessionId;
    }
   var newValue = jQuery("#proxyId option:selected").text();
    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
            alert(data);
            if(data.indexOf("成功")>0) {
                showSessionPage();
            }
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'text'
    });
}
function vertifySession(){
	 var url = getRootPath() + "/webms/session/vertifySession";
	 
	 var proxyId = $("#proxyId").val();
	 var reqType = $("#reqType").val();
	 var validateurl = $("#validateurl").val();
	 var validateMethod = $("#validateMethod").val();
	 var params = {
         metadataId : METADATAOBJECT.id,
         proxyId : proxyId
     };
	 var newValue = jQuery("#proxyId option:selected").text();
	 var sessionId = $("#sessionId").val();
	 if(sessionId&&oldValue!=newValue){
		 $.ajax({
		        url: url,
		        data: params,
		        type: 'GET',
		        success: function (data, status, obj) {
		             if(parseInt(data)>=1){
		            	alert("请求地址已经存在");
		            	return ;
		             }
		             addSession();
		        },
		        error: function (obj, status, e) {
		            alert('发生错误: ' + JSON.stringify(e));
		        },
		        dataType: 'json'
		    });
	   }
	 if(sessionId&&oldValue==newValue){
		 addSession();
		 return ;
	 }
	 if(sessionId==""){
		 $.ajax({
             url: url,
             data: params,
             type: 'GET',
             success: function (data, status, obj) {
                 if(parseInt(data)>=1){
                    alert("请求地址已经存在");
                    return ;
                 }
                 addSession();
             },
            error: function (obj, status, e) {
                alert('发生错误: ' + JSON.stringify(e));
            },
            dataType: 'json'
         });
     }
}
