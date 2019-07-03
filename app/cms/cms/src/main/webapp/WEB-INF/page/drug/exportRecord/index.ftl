<title>导入记录</title>
<div id="mainPageDiv">
    <div class="page-header">
        <button type="button" class="btn btn-pink" onclick="history.go(-1)">
            <i class="ace-icon fa fa-reply icon-only"></i> 返回
        </button>
    </div>

    <form id="submitForm"></form>
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <div class="row">
                    <div class="row">
                        <#--<form id="extend-form" class="form-inline">
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;区域：</label>
                                <input type="text" class="input-medium" name="exportRecord.area"/>
                            </div>

                            <div class="form-group">&nbsp;&nbsp;&nbsp;
                                <button type="button" class="btn btn-info btn-sm ace-table-search" id="searchBtn">
                                    <i class="ace-icon fa fa-search"></i>搜索
                                </button>
                            </div>
                        </form>-->
                    </div>

                </div>
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
                        <th>文件名</th>
                        <th>导入条数</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <table class="ace-tr-temp">
                    <tbody>
                    <tr data-id="%{id}">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace ace-check-item"/>
                                <span class="lbl"></span>
                            </label>
                        </td>
                        <td>%{index}</td>
                        <td>%{fileName}</td>
                        <td>%{exportCnt}</td>
                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                            <div class="btn-group">
                                <button class="btn btn-xs btn-danger ace-tr-delete" data-id="%{id}">
                                    <i class="ace-icon fa fa-trash-o"></i>删除
                                </button>
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
        deleteUrl: "exportRecord.remove.do",
        listUrl: "exportRecord.search.do",
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        //新增|编辑
        $('#mainPageDiv').delegate('#addBtn,.ace-tr-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var title="编辑导入记录";
            if(typeof(id) == "undefined"){
                title = "新增导入记录";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "exportRecord.addOrEditIndex.do?id=" + id;
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
                    ace.post('/html/exportRecord.approve.do?',{idsStr:checkedIds.join()}, function (data) {
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
            window.open('${ctx}/html/common.export.do?fileName=导入记录&exportClass=com.ace.app.cms.drug.domain.ExportRecord&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.ExportRecord";
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


