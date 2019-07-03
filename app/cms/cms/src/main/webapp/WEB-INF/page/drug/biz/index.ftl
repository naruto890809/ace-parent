<style>
    .dataTables_wrapper{
        max-width: 4000px;overflow-x: auto;
        background-color: #EFF3F8;
    }.dataTables_wrapper th{
         white-space:nowrap;
     }
</style>
<title>业务管理</title>
<div id="mainPageDiv">
    <div class="page-header">
    <@check module="biz" privilege="import">
        <button type="button" class="btn btn-pink" id="importAccountBtn">
            <i class="ace-icon fa fa-cloud-upload"></i> 导入
        </button>
    </@check>

    <@check module="biz" privilege="export">
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
        </button>
    </@check>

    <@check module="biz" privilege="add">
        <button type="button" class="btn btn-info" id="addBtn">
            <i class="ace-icon fa fa-plus"></i> 添加
        </button>
    </@check>

    <@check module="biz" privilege="approve">
        <button type="button" class="btn btn-success" id="approve">
            <i class="ace-icon fa fa-undo"></i>审批
        </button>
    </@check>

                    <@check module="biz" privilege="remove">
        <button type="button" class="btn btn-danger ace-batch-del">
            <i class="ace-icon fa fa-trash-o"></i>删除
        </button>
                    </@check>

        <button type="button" class="btn btn-pink " id="updateSalesman">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改业务员
        </button>
    </div>

    <form id="submitForm"></form>
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <div class="row">
                    <div class="row">
                        <form id="extend-form" class="form-inline">
                            &nbsp;&nbsp;
                            <div class="form-group">
                                <select name="biz.departmentId" style="max-width: 208px" class="chosen-select">
                                    <option value="">请选择部门&emsp;&emsp;</option>
                                <#list departments as department>
                                    <option value="${department.id}">${department.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="biz.salesmanId" class="chosen-select">
                                    <option value="">请选择业务员</option>
                                <#list salesmanIdNameMap?keys as key>
                                    <option value="${key}">${salesmanIdNameMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <#--<div class="form-group">-->
                                <#--<select  name="biz.hospitalId"  style="max-width: 208px" class="chosen-select" >-->
                                    <#--<option value="">请选择医院&emsp;&emsp;&emsp;&emsp;</option>-->
                                <#--<#list hospitals as key>-->
                                    <#--<option value="${key.id}">${key.name}</option>-->
                                <#--</#list>-->
                                <#--</select>-->
                            <#--</div>-->
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;医院名称：</label>
                                <input type="text" class="input-medium" name="biz.note"/>
                            </div>

                            <div class="form-group">
                                <select name="biz.specDrugName" class="chosen-select">
                                    <option value="">请选择品规&emsp;&emsp;&emsp;&emsp;</option>
                                <#list drugSpecIdsNamesMap?keys as key>
                                    <option value="${drugSpecIdsNamesMap[key]}">
                                    ${drugSpecIdsNamesMap[key]}</option>
                                </#list>
                                </select>
                            </div>
                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <label for="province" >销售时间段</label>
                                <input type="text"  style="width: 100px;" name="biz.saleDate" class="date" />
                            </div>
                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <label for="province" >跟踪时间</label>
                                <input type="text"  style="width: 100px;" name="biz.traceStartDate" class="date" />-
                                <input type="text"  style="width: 100px;" name="biz.traceEndDate" class="date" />
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;审核状态：</label>
                                <select name="biz.approve">
                                    <option value="">全部</option>
                                    <option value="PASSED">通过</option>
                                    <option value="APPROVING">审核中</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;类型：</label>
                                <select name="biz.type">
                                    <option value="">全部</option>
                                    <option value="申请">申请</option>
                                    <option value="交接">交接</option>
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
                    <th>业务员&emsp;&emsp;&emsp;</th>
                    <th>医院名称&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>品规名称&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>类型&emsp;</th>
                    <th>考核量</th>
                    <th>销售时间段&emsp;&emsp;&emsp;</th>
                    <th>跟踪时间</th>
                    <#--<th>备注&emsp;&emsp;&emsp;&emsp;</th>-->
                        <th>审核状态</th>
                        <th>创建人</th>
                        <th>创建时间&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>操作&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
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
                        <td>%{salesmanName}</td>
                        <td>%{hospitalName}</td>
                        <td>%{specDrugName}</td>
                        <td>%{type}</td>
                        <td>%{amount}</td>
                        <td>%{saleDateText}</td>
                        <td>%{traceStartDateText}~%{traceEndDateText}</td>
                       <#-- <td>%{note}</td>-->
                        <td>%{approveText}</td>
                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                            <div class="btn-group">
                            <@check module="biz" privilege="edit">
                                <button class="btn  btn-xs btn-info ace-tr-edit" data-id="%{id}">
                                    <i class="ace-icon fa fa-pencil"></i>编辑
                                </button>
                            </@check>
                            <@check module="biz" privilege="remove">
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
        delBatchUrl: "biz.removeBatch.do",
        deleteUrl: "biz.remove.do",
        listUrl: "biz.search.do",
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        $('.date').datepicker({
        });
        //新增|编辑
        $('#mainPageDiv').delegate('#addBtn,.ace-tr-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var title="编辑业务管理";
            if(typeof(id) == "undefined"){
                title = "新增业务管理";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "biz.addOrEditIndex.do?id=" + id;
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
                    ace.post('/html/biz.approve.do?',{idsStr:checkedIds.join()}, function (data) {
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

            var title="编辑业务员";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "biz.editSalesmanIndex.do?id=" + ids.join();
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

        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/common.export.do?fileName=业务管理&exportClass=com.ace.app.cms.drug.domain.Biz&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.Biz";
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


