/**
 * Created by demon on 15/7/14.
 */

function addVersion() {
    var url = getRootPath() + "/webms/version/addVersion";

    var description = $("#description").val();
    if(!vf(description)){
    	alert("请仔细填写描述信息!");
        $("#description").focus();
        return;
    }

    var params = {
        metadataId : METADATAOBJECT.id,
        description : description
    };

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
            if(data.indexOf("成功") != -1) {
                showVersionPage();
            }
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'text'
    });
}

function publicNew(versionId, metadataId) {
	confirm("系统提示", '确认发布版本信息么?小心操作!', function() {
		confirmPubliVersion(versionId, metadataId);
	});
    prevent(event);
}

function confirmPubliVersion(versionId, metadataId) {
    var url = getRootPath() + "/webms/publish/publishnew?versionId="+versionId+"&metadataId="+metadataId;
    $.ajax({
        url : url,
        type : 'GET',
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
        dataType : 'TEXT'
    });
}


function previewVersion(field, event){
    var url = getRootPath() + "/webms/version/previewVersion?id=3";

    $('#myModal').modal({
        remote: url
    });

    //var id = $(field).parent().parent().attr('value');
    //var params = {
    //    id: id
    //};
    //
    //$.ajax({
    //    url: url,
    //    data: params,
    //    type: 'GET',
    //    success: function (data, status, obj) {
    //        console.info(data);
    //        $("#modalBody").html(data);
    //        $('#myModal').modal({});
    //    },
    //    error: function (obj, status, e) {
    //        alert('发生错误: ' + JSON.stringify(e));
    //    },
    //    dataType: 'text'
    //});

    //prevent(event);
}