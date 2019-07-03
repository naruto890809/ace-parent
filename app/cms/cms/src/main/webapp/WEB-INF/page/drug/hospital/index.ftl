<title>医院</title>
<div id="mainPageDiv">
    <div class="page-header">
    <@check module="hospital" privilege="import">
        <button type="button" class="btn btn-pink" id="importAccountBtn">
            <i class="ace-icon fa fa-cloud-upload"></i> 导入
        </button>
    </@check>

    <@check module="hospital" privilege="export">
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
        </button>
    </@check>

    <@check module="hospital" privilege="add">
        <button type="button" class="btn btn-info" id="addBtn">
            <i class="ace-icon fa fa-plus"></i> 添加
        </button>
    </@check>

    <@check module="hospital" privilege="approve">
        <button type="button" class="btn btn-success" id="approve">
            <i class="ace-icon fa fa-undo"></i>审批
        </button>
    </@check>
                    <@check module="hospital" privilege="remove">
        <button type="button" class="btn btn-danger ace-batch-del">
            <i class="ace-icon fa fa-trash-o"></i>删除
        </button>
                    </@check>

        <button type="button" class="btn btn-pink " id="updateSalesman">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改部门
        </button>

    </div>

    <form id="submitForm"></form>
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <div class="row">
                    <div class="row">
                        <form id="extend-form" class="form-inline">
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;名称：</label>
                                <input type="text" class="input-medium" name="hospital.hospitalNameSplit" id="hospitalNameSplit"/>
                            </div>
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;别名：</label>
                                <input type="text" class="input-medium" name="hospital.hospitalAliasSplit" id="hospitalAliasSplit"/>
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;市：</label>
                                <input type="text" class="input-medium" name="hospital.citySearch"/>
                            </div>
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;区：</label>
                                <input type="text" class="input-medium" name="hospital.area"/>
                            </div>

                            <br>
                            <div class="form-group">
                                    <select name="hospital.approve">
                                    <option value="">审核状态</option>
                                    <option value="PASSED">通过</option>
                                    <option value="APPROVING">审核中</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="hospital.level">
                                    <option value="">请选择医院等级</option>
                                <#list levels as level>
                                    <option value="${level.id}">${level.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="hospital.type">
                                    <option value="">请选择医院性质</option>
                                <#list types as type>
                                    <option value="${type.id}">${type.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="hospital.departmentId" style="max-width: 208px">
                                    <option value="">请选择部门</option>
                                <#list departments as department>
                                    <option value="${department.id}">${department.name}</option>
                                </#list>
                                </select>
                            </div>


                            <div class="form-group">&nbsp;&nbsp;&nbsp;
                                <button type="button" class="btn btn-info btn-sm ace-table-search" id="searchBtn">
                                    <i class="ace-icon fa fa-search"></i>搜索
                                </button>
                                <button type="button" class="btn btn-grey btn-info btn-sm " id="cleanBtn1" onclick="window.location.reload()">
                                    <i class="ace-icon fa fa-repeat"></i>清空
                                </button>
                            </div>
                        </form>
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
                        <th>名称</th>
                    <th>别名</th>
                    <th>等级</th>
                    <th>医院性质</th>
                    <th>所属部门</th>
                    <th>地址</th>
                        <th>审核状态</th>
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
                        <td>%{name}</td>
                        <td>%{alias}</td>
                        <td>%{levelName}</td>
                        <td>%{typeName}</td>
                        <td>%{departmentName}</td>
                        <td>%{province}%{city}%{area}%{address}</td>
                        <td>%{approveText}</td>
                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                            <div class="btn-group">
                            <@check module="hospital" privilege="edit">
                                <button class="btn  btn-xs btn-info ace-tr-edit" data-id="%{id}">
                                    <i class="ace-icon fa fa-pencil"></i>编辑
                                </button>
                            </@check>
                            <@check module="hospital" privilege="remove">
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
        deleteUrl: "hospital.remove.do",
        delBatchUrl: "hospital.removeBatch.do",
        listUrl: "hospital.search.do",
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        //新增|编辑
        $('#mainPageDiv').delegate('#addBtn,.ace-tr-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var title="编辑医院";
            if(typeof(id) == "undefined"){
                title = "新增医院";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "hospital.addOrEditIndex.do?id=" + id;
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
                    ace.post('/html/hospital.approve.do?',{idsStr:checkedIds.join()}, function (data) {
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
            window.open('${ctx}/html/common.export.do?fileName=医院&exportClass=com.ace.app.cms.drug.domain.Hospital&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.Hospital";
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


        //批量修改业务员
        $('#updateSalesman').click(function () {
            var $col = $("#ace-table").find(".ace-check-item:checked");
            if ($col.length < 1) {
                alertErr("请选中要修改的行");
                return;
            }
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });

            var title="编辑部门";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "hospital.editDeptIndex.do?id=" + ids.join();
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
    });

</script>


