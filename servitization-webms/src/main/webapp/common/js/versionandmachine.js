/**
 * Created by demon on 15/7/3.
 */

$("#dataConfigUl li a").click(function () {
    $(this).parent().siblings().removeClass("active");
    $(this).parent().addClass("active");
});

function home() {
    console.info("123");
}

/**
 * 显示版本管理页面
 */
function showVersionPage(pageIndex, pageSize){
    var url = getRootPath() + "/webms/version/getVersionPage";

    var pageIndex = pageIndex || 0;
    var pageSize = pageSize || PAGESIZE;

    var params = {
        metadataId: METADATAOBJECT.id,
        pageIndex: pageIndex,
        pageSize: pageSize
    };
    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            $("#dataConfigArea").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}

/**
 * 显示AOS节点管理页面
 * 
 * add by jiamingku 修改 原始  var url = getRootPath() + "/webms/node/getNodePage";
 * 
 */
function showNodePage(pageIndex, pageSize){
    var url = getRootPath() + "/webms/node/getNodePageNew";

    var pageIndex = pageIndex || 0;
    var pageSize = pageSize || PAGESIZE;

    var params = {
        metadataId: METADATAOBJECT.id,
        pageIndex: pageIndex,
        pageSize: pageSize
    };
    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            $("#dataConfigArea").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}

/**
 * 展示生效机器列表页面
 */
function showEffectiveMachinePage(){
    var url = getRootPath() + "/webms/machine/getEffectiveMachinePage";
    var params = {
        metadataId: METADATAOBJECT.id
    };
    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            $("#dataConfigArea").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}

/**
 * 展示生效机器列表页面
 */
function showHistoryVersion(){
    var url = getRootPath() + "/webms/publish/getPublishHistory";
    var params = {
        metadataId: METADATAOBJECT.id
    };
    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            $("#dataConfigArea").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });
}

