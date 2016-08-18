function selectVersion() {
    var url = getRootPath() + "/webms/machine/getEffectiveMachineList";
    var versionId = $("#version").val();
    console.info(versionId);

    var params = {
        metadataId: METADATAOBJECT.id,
        versionId: versionId
    };

    $.ajax({
        url: url,
        data: params,
        type: 'GET',
        success: function (data, status, obj) {
            $("#effectiveMachineList").html(data);
        },
        error: function (obj, status, e) {
            alert('发生错误: ' + JSON.stringify(e));
        },
        dataType: 'html'
    });


}