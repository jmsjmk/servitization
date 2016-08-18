<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">
            <h1 style="text-align: left">ZK列表</h1>
        </h3>
    </div>
    <div class="panel-body">
        <button type="button" class="btn btn-default" onclick="getZkInfo()">加载zk信息</button>
        <ul id="zookeeperTree" class="ztree"></ul>
    </div>
    <div>
        <span id="nodeInfo"></span>
    </div>
</div>
<script src="${path}/common/bootstrap/js/bootstrap.min.js"></script>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/module.js"></script>

<script type="text/javascript">
    function getZkInfo() {
        var url = getRootPath() + "/webms/zk/getZkInfo";
        var params = {};
        $.ajax({
            url: url,
            data: params,
            type: 'GET',
            success: function (data, status, obj) {
                // alert(data);
                showZkInfo(data);
            },
            error: function (obj, status, e) {
                alert('发生错误: ' + JSON.stringify(e));
            },
            dataType: 'json'
        });
    }

    function onClick(event, treeId, treeNode, clickFlag) {
        $("#nodeInfo").html(treeNode.info);
    }

    function showZkInfo(data) {
        var setting = {
            callback: {
                onClick: onClick
            }
        };
        var pushNode = data.push;
        var statusNode = data.status;
        var children = [];
        var statusChildren = [];

        var pushChildren = [];
        for (var i = 0; i < statusNode.length; i++) {
            var sip = statusNode[i].ip;
            var sinfo = statusNode[i].info;
            // alert("ip:" + ip + "\t info:" + info);
            schildren = {name:sip, info:sinfo};
            var a  = statusChildren.push(schildren);

            var pip = pushNode[i].ip;
            var pinfo = pushNode[i].info;
            pchildren = {name:pip, info:pinfo};
            var p = pushChildren.push(pchildren);
        }

        var bootChildren = [
        ];
        var zNodes = [
            {
                name: "ZK信息", open: true,
                children: [
                    {name: "boot", isParent:true,
                        children:bootChildren
                    },
                    {name: "status", isParent:true,
                        children:statusChildren
                    },
                    {name: "push", isParent:true,
                        children:pushChildren
                    }
                ]
            }
        ];
        $.fn.zTree.init($("#zookeeperTree"), setting, zNodes);
    }
</script>
