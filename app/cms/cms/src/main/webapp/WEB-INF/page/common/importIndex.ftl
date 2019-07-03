<form>
    <div class="layui-form" style="padding: 20px 0 0 0;">
        <dl style="padding-left: 35px;">
            <dt>请按模板进行数据导入 <span id="downTemplate" style="color: #01AAED;cursor: pointer">下载导入模板</span></dt>
            <dd style="margin-top: 15px;">
                <input type="file" id="id-input-file-4"/>

                <div style="display: inline-block;margin-left: 15px;" id="show-file-name"></div>
                <input type="hidden" value="" name="importEntity.url" id="importFileUrl">
                <input type="hidden" value="" name="importEntity.fileName" id="importFileName">
            </dd>

            <dd id="importBefore" style="display: none">
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <table class="table table-striped table-bordered table-hover">
                            <thead class="thin-border-bottom">
                            <tr>
                                <th>上传文件表头</th>
                                <th>数据范例</th>
                                <th>对应字段设置</th>
                            </tr>
                            </thead>

                            <tbody id="importFieldDiv">
                            </tbody>
                        </table>
                    </div>
                </div>
            </dd>
            <dd style="display: none">
                <input type="button"  value="确定导入" disabled="disabled" id="importCustomerBtn">
            </dd>
            <br/>
            <br/>
        </dl>
    </div>
</form>


<script type="text/javascript">

    $("#downTemplate").on("click", function () {
        window.open('${ctx}/html/common.exportTemplate.do?exportClass=${exportClass}');
    });


    $('#id-input-file-4').uploadifive({
        //上传处理程序
        uploadScript: "${ctx}/html/fileUpload.do",
        //是否自动上传
        fileType: '.xlsx',
        auto: true,
        fileObjName: 'excel',
        buttonText: '<span id="upbtn">选择文件</span>',//浏览按钮
        queueSizeLimit: 1,//上传数量
        fileSizeLimit: '5MB',
        buttonClass: 'upload-btn-adj',
        multi: false,
        //选择上传文件后调用
        onUpload: function (file) {
            $("#uploadifive-id-input-file-4").addClass("hide");
        },
        //上传到服务器，服务器返回相应信息到data里
        onUploadComplete: function (file, data, response) {
            if (data != null && data.indexOf("ERROR") >= 0) {
                alert("上传文件失败，请重试！");
            } else {
                var result = data.split(",");
                $("#importFileName").val($("#uploadifive-id-input-file-4-file-0").find(".filename").html());
                var fileUrl = result[1];
                $("#importFileUrl").val(fileUrl);
                ace.post('${ctx!}/html/common.returnImportHeads.do?', {fileUrl: fileUrl, exportClass:"${exportClass}"}, function (data) {
                            if (data.status == "ERROR") {
                                alertErr(data.message);
                                $("#importFieldDiv").html("");
                                $("#importCustomerBtn").attr("disabled",true);
                            } else if (data.status == "SUCCESS") {
                                $("#importCustomerBtn").attr("disabled",false);
                                $("#importBefore").show();
                                $("#importFieldDiv").html("");
                                var data = data.data;
                                var importEntities = data.importEntities;
                                var heads = data.heads;
                                var html = "";

                                for (var i = 0; i < heads.length; i++) {
                                    var head = heads[i];
                                    var headName = head.headName;
                                    var selectHtml = "<select name='importEntity.child[" + i + "].field'>";
                                    for (var j = 0; j < importEntities.length; j++) {
                                        var entity = importEntities[j];
                                        var select = "";
                                        var fieldName = entity.fieldName;
                                        if (headName === fieldName) {
                                            select = "selected='selected'";
                                        }
                                        selectHtml += "<option " + select + "value='" + entity.field + "'>" + fieldName + "</option>";
                                    }
                                    selectHtml += "</select>";
                                    var demo = head.demo;
                                    if (demo == "") {
                                        demo = "&nbsp;&nbsp;&nbsp;&nbsp;"
                                    }

                                    html += "<tr>"
                                    +"       <td><input type='hidden' name='importEntity.child[" + i + "].headName' value='" + headName + "'>" + headName + "</td>"
                                    +"        <td>" + demo + "</td>"
                                    +"        <td>"+ selectHtml+"</td>"
                                    +"</tr>";
                                }

                                $("#importFieldDiv").html(html);
                            }
                        }
                )

            }
        },
        onError: function (errorType, file) {
            $("#upbtn").text("选择文件");
            switch (errorType) {
                case "QUEUE_LIMIT_EXCEEDED":
                    alert("最多只能同时上传" + $('#uploadify').data('uploadifive').settings.queueSizeLimit + "个文件");
                    break;
                case "FILE_SIZE_LIMIT_EXCEEDED":
                    alert("文件 [" + file.name + "] 大小超出限制");
                    break;
                case "FORBIDDEN_FILE_TYPE":
                    alert("文件 [" + file.name + "] 不是excel文件！");
                    break;
                case "UPLOAD_LIMIT_EXCEEDED":
                    alert("最多只能上传" + $('#uploadify').data('uploadifive').settings.queueSizeLimit + "个文件");
                    break;
            }
        },
        onCancel: function () {
            $("#uploadifive-id-input-file-4").removeClass("hide");
            $("#importFileName").val("");
            $("#importFileUrl").val("");
        }
    });

    $("#importCustomerBtn").click(function(){

        ace.post('${ctx!}/html/common.importExcel.do?exportClass=${exportClass}',$("form").serializeJson(), function (data) {
            if(data.status == "SUCCESS"){
                var message = data.data;
                if(message){
                    myConfirm(message);
                }else{
                    alertSuc("操作成功！");
                    $("#searchBtn").click();
                }

                $("#divDialog").dialog("close");
                $("#divDialog").dialog("destroy");
            }
        });
    });

</script>


