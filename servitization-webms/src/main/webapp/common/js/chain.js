/**
 * Created by demon on 15/7/9.
 */

/**
 * 处理上行链条模块勾选
 * @param field
 * @param event
 */
function upChainCheckBoxClick(field, event){
    //console.info($(field).val());
    //console.info($(field).parent().text().trim());

    if($(field).is(':checked')){
        handUpchainLabel($(field).val(), $(field).parent().text().trim(), true);
    }else{
        handUpchainLabel($(field).val(), $(field).parent().text().trim(), false);
    }
    prevent(event);
}

function handUpchainLabel(value, text, flag){
    if(flag) {
        var html = '<label class="col-xs-2 control-label">' + text + '</label>';
        $("#upChainLabel").append(html);
        var upChainValue = $("#upChainValue").val();
        upChainValue += value + ",";
        $("#upChainValue").val(upChainValue);
    }else{
        $("#upChainLabel").children("label").each(function(){
            if($(this).text() == text) {
                $(this).remove();
            }
        });
        var upChainValue = $("#upChainValue").val();
        upChainValue = upChainValue.replace(value + ",", "");
        $("#upChainValue").val(upChainValue);
    }
}

/**
 * 处理下行链条模块勾选
 * @param field
 * @param event
 */
function downChainCheckBoxClick(field, event){

    if($(field).is(':checked')){
        handDownchainLabel($(field).val(), $(field).parent().text().trim(), true);
    }else{
        handDownchainLabel($(field).val(), $(field).parent().text().trim(), false);
    }
    prevent(event);
}

function handDownchainLabel(value, text, flag){
    if(flag) {
        var html = '<label class="col-xs-2 control-label">' + text + '</label>';
        $("#downChainLabel").append(html);
        var downChainValue = $("#downChainValue").val();
        downChainValue += value + ",";
        $("#downChainValue").val(downChainValue);
    }else{
        $("#downChainLabel").children("label").each(function(){
            if($(this).text() == text) {
                $(this).remove();
            }
        });
        var downChainValue = $("#downChainValue").val();
        downChainValue = downChainValue.replace(value + ",", "");
        $("#downChainValue").val(downChainValue);
    }
}

function updateMetadataChain() {
    var url = getRootPath() + "/webms/metadata/updateMetadataChain";
    var deployModel = $("#deployModel").val();
    var upChainValue = $("#upChainValue").val();
    var downChainValue = $("#downChainValue").val();
    if(!vf(upChainValue)){
        alert("请选择上行处理链模块");
        return;
    }
    upChainValue = upChainValue.substr(0, upChainValue.length - 1);

    if(!vf(downChainValue)){
        alert("请选择下行处理链模块");
        return;
    }
    downChainValue = downChainValue.substr(0, downChainValue.length - 1);

    var params = {
        metadataId: METADATAOBJECT.id,
        deployModel: deployModel,
        upChain: upChainValue,
        downChain: downChainValue
    };

    $.ajax({
        url: url,
        data: params,
        type: 'POST',
        success: function (data, status, obj) {
        	if(obj.status==202){
        		alert( "没有权限");
        		return ;
        	}
            alert(data);
            if(data.indexOf("成功") != -1) {
                $("#welcomePageTag").click();
            }
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'text'
    });
}