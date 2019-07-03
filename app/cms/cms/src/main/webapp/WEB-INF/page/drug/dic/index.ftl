<title>数据字典</title>
<div id="mainPageDiv">
    <div class="page-header">
   <#-- <@check module="dic" privilege="add">
        <button type="button" class="btn btn-info" id="addBtn">
            <i class="ace-icon fa fa-plus"></i> 添加
        </button>
    </@check>-->
    </div>

    <form id="submitForm"></form>
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <table id="ace-table" class="table table-striped table-bordered table-hover  dataTable">
                    <thead>
                    <tr>
                        <th class="center ace-tr-sm">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace ace-check-all"/>
                                <span class="lbl"></span>
                            </label>
                        </th>
                        <th>序号</th>
                        <th>字典类型</th>
                    <th>描述</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="ace-tr-temp">
                    <tbody>
                    <tr data-id="%{code}">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace ace-check-item"/>
                                <span class="lbl"></span>
                            </label>
                        </td>
                        <td>%{index}</td>
                        <td>%{name}</td>
                        <td>%{des}</td>
                        <td>
                            <div class="btn-group">
                            <@check module="dic" privilege="edit">
                                <button class="btn  btn-xs btn-info ace-tr-edit" data-id="%{code}">
                                    <i class="ace-icon fa fa-pencil"></i>编辑
                                </button>
                            </@check>
                            <@check module="dic" privilege="remove">
                                <button class="btn btn-xs btn-danger ace-tr-delete" data-id="%{id}">
                                    <i class="ace-icon fa fa-trash-o"></i>删除
                                </button>
                            </@check>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var scripts = [];
    var option = {
        appCode: "cms",
        corpCode: "",
        deleteUrl: "dic.remove.do",
        listUrl: "dic.search.do",
        id: "code"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        //新增|编辑
        $('#mainPageDiv').delegate('#addBtn,.ace-tr-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var title="编辑数据字典";
            if(typeof(id) == "undefined"){
                title = "新增数据字典";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "dic.addOrEditIndex.do?code=" + id;
            var content = div.load(url, {});
            $("#divDialog").data("url", url);
            div.dialog({
                autoOpen: true,
                width: 600,
                modal: true,
                bgiframe: true,
                close: function (evt, ui) {
                    div.dialog("destroy");
                    div.html("").remove();
                },
                open: function (evt, ui) {
                    $(".ui-dialog-titlebar-close .ui-button-text").remove();
                },
                buttons: [
                    {
                        text: "取消",
                        "class": "btn btn-minier",
                        click: function () {
                            div.dialog("close");
                        }
                    },
                    {
                        text: "确认",
                        "class": "btn btn-primary btn-minier",
                        click: function () {
                            $("#saveBtn").click();
                        }
                    }
                ]
            });

        });

        //审批
        $("#approve").click(function(){
            var checkedIds = table.getCheckedIds();
            if (checkedIds.length > 0) {
                myConfirm("确认数据无误，审批通过？",function(){
                    ace.post('/html/dic.approve.do?',{idsStr:checkedIds.join()}, function (data) {
                        if (data.status == "ERROR") {
                            alertErr("网络异常，请稍后重试！");
                        }else if(data.status == "WARNING"){
                            alertErr(data.message);
                        }else if(data.status == "SUCCESS"){
                            alertSuc("操作成功！");
                            table.refresh();
                        }
                    });
                });
            } else {
                alertErr("请选择操作的列！");
            }
        });

        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/common.export.do?fileName=数据字典&exportClass=com.ace.app.cms.drug.domain.Dic&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.Dic";
            var content = div.load(url, {});
            $("#divDialog").data("url", url);
            div.dialog({
                autoOpen: true,
                width: 600,
                modal: true,
                bgiframe: true,
                close: function (evt, ui) {
                    div.dialog("destroy");
                    div.html("").remove();
                },
                open: function (evt, ui) {
                    $(".ui-dialog-titlebar-close .ui-button-text").remove();
                },
                buttons: [
                    {
                        text: "取消",
                        "class": "btn btn-minier",
                        click: function () {
                            div.dialog("close");
                        }
                    },
                    {
                        text: "确认",
                        "class": "btn btn-primary btn-minier",
                        click: function () {
                            $("#importCustomerBtn").click();
                        }
                    }
                ]
            });

        });

    });

</script>


