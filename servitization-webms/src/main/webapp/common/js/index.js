/**
 * Created by demon on 15/7/2.
 */

function test(metaKey) {
    console.info(metaKey);
}

function prevent(event){
    var e = event || window.event;
    if(e){
        e.preventDefault();
        e.returnValue = false;
    }
}

/**
 * 获取根目录
 */
function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.lastIndexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPaht;
}

/**
 * 校验字段函数
 * @param value
 * @param isNum
 * @returns {boolean}
 */
function vf(value, isNum){
    if(!value || value.trim() == ''){
        return false;
    }
    if(isNum && !/[0-9\\-]+/.test(value)){
        return false;
    }
    return true;
}

/**
 * 勾选全部
 * @param tableId
 */
function checkAll(tableId){
    var y = document.getElementById(tableId+'AllCheckbox').checked;
    var cbs = document.getElementsByName(tableId+'Checkbox');
    for(var i = 0; i < cbs.length; i++){
        cbs[i].checked = y;
    }
}

/**
 * 获取需要删除记录的id列表
 * @param tableId
 * @returns {boolean}
 */
function collectChosenId(tableId){
    var cbs = document.getElementsByName(tableId+'Checkbox');
    var ids = [];
    for(var i = 0; i < cbs.length; i++){
        if(cbs[i].checked){
            ids.push($(cbs[i]).parent().parent().attr('value'));
        }
    }
    if(ids.length == 0){
        return false;
    }
    return JSON.stringify(ids);
}

/**
 * 目录树公共beforeClick处理函数
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function beforeClick(treeId, treeNode, clickFlag) {
    METADATAOBJECT = treeNode.metadata;
    //console.info("[beforeClick ]&nbsp;&nbsp;" + treeNode.name + "," + JSON.stringify(treeNode));
}

/**
 * 目录树公共onClick处理函数
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function onClick(event, treeId, treeNode, clickFlag) {
    //console.info("[onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag === 1 ? "普通选中" : (clickFlag === 0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
}

/**
 * 获取左侧目录树数据
 */
function structureTree() {
    var url = getRootPath() + "/webms/getMetadataList";
    var params = {};
    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            structureTreeHtml(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'json'
    });
}

/**
 * 构造并显示左侧目录树
 * @param data
 */
function structureTreeHtml(data) {
    var setting = {
        callback: {
            beforeClick: beforeClick,
            onClick: onClick
        }
    };

    var metadatas = data;

    var children = [];
    for (var i = 0; i < metadatas.length; i++) {
        var meta = {
            name: metadatas[i]['description'],
            children: [
                {name: "元数据定义", click: "showDataConfigPage()", metadata:(metadatas[i])},
                {name: "版本发布", click: "showVersionAndMachinePage()", metadata:(metadatas[i])}
            ]
        };
        children.push(meta);
    }
    var zNodes = [
        {
            name: "全局配置", open: true,
            children: [
                {name: "元数据管理", click: "showMetadataPage()"},
                {name: "模块管理", click: "showModulePage()"},
                {name: "ZK管理", click: "showZkPage()"}
            ]
        },
        {name: "元数据配置", children: children, open: true}
    ];
    $.fn.zTree.init($("#tree"), setting, zNodes);
}

/**
 * 显示模块管理页面
 */
function showModulePage() {
    var url = getRootPath() + "/webms/module/getModulePage";
    var params = {};
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
/**
 * 显示项目zk的配信信息
 */
function showZkPage() {
    var url = getRootPath() + "/webms/zk/getZkList";
    var params = {};
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

/**
 * 全局配置-元素管理页
 * 
 */
function showMetadataPage(pageIndex, pageSize) {
    var url = getRootPath() + "/webms/metadata/getMetadataPage";

    var pageIndex = pageIndex || 0;
    var pageSize = pageSize || PAGESIZE;

    var params = {
        pageIndex: pageIndex,
        pageSize: pageSize
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

/**
 * 显示数据配置管理页面
 */
function showDataConfigPage() {
    var url = getRootPath() + "/webms/getDataConfigPage";
    var params = {};
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

/**
 * 显示版本机器管理页面
 */
function showVersionAndMachinePage() {
    var url = getRootPath() + "/webms/getVersionAndMachinePage";
    var params = {};
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
