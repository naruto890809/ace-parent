<style>
    .dataTables_wrapper{
        max-width: 4000px;overflow-x: auto;
        background-color: #EFF3F8;
    }.dataTables_wrapper th{
         white-space:nowrap;
     }
</style>
<title>返利设置</title>
<div id="mainPageDiv">
    <div class="page-header">
    <@check module="rebate" privilege="import">
        <button type="button" class="btn btn-pink" id="importAccountBtn">
            <i class="ace-icon fa fa-cloud-upload"></i> 导入
        </button>
    </@check>

    <@check module="rebate" privilege="export">
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
        </button>
    </@check>

    <@check module="rebate" privilege="add">
        <button type="button" class="btn btn-info" id="addBtn">
            <i class="ace-icon fa fa-plus"></i> 添加
        </button>
    </@check>

    <@check module="rebate" privilege="approve">
        <button type="button" class="btn btn-success" id="approve">
            <i class="ace-icon fa fa-undo"></i>审批
        </button>
    </@check>
            <@check module="rebate" privilege="remove">
        <button type="button" class="btn btn-danger ace-batch-del">
            <i class="ace-icon fa fa-trash-o"></i>删除
        </button>
            </@check>

    </div>

    <form id="submitForm"></form>
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <div class="row">
                    <div class="row">
                        <form id="extend-form" class="form-inline">
                            <div class="form-group">
                                <select name="rebate.companyId"  class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择商业公司&emsp;&emsp;&emsp;</option>
                                <#list companyIdNameMap?keys as key>
                                    <option value="${key}" >${companyIdNameMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="rebate.hospitalId"  style="width: 208px" class="chosen-select">
                                    <option value="">请选择医院&emsp;&emsp;&emsp;&emsp;&emsp;</option>
                                <#list hospitals as key>
                                    <option value="${key.id}">${key.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="rebate.specDrugName"  class="chosen-select">
                                    <option value="">请选择品规名称&emsp;&emsp;</option>
                                <#list drugSpecIdsNamesMap?keys as key>
                                    <option value="${drugSpecIdsNamesMap[key]}">
                                    ${drugSpecIdsNamesMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="rebate.priceTopic" class="chosen-select">
                                    <option value="">请选择议价主体&emsp;</option>
                                <#list priceTopics as type>
                                    <option value="${type.id}">${type.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="rebate.rebateStyle" class="chosen-select">
                                    <option value="">请选择返利形式</option>
                                <#list rebateStyles as type>
                                    <option value="${type.id}">${type.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;审核状态：</label>
                                <select name="rebate.approve">
                                    <option value="">全部</option>
                                    <option value="PASSED">通过</option>
                                    <option value="APPROVING">审核中</option>
                                    <option value="FREEZED">冻结</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;补差性质：</label>
                                <select name="rebate.difType">
                                    <option value="">全部</option>
                                    <option value="明">明</option>
                                    <option value="暗">暗</option>
                                    <option value="明+暗">明+暗</option>
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
                        <th>商业公司&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>末级商业公司&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>医院&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>医院区域&emsp;&emsp;&emsp;&emsp;</th>
                    <th>医院所在部门&emsp;&emsp;</th>
                    <th>品规名称&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>补差性质</th>
                    <th>议价主体&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>明返单价</th>
                    <th>明返日期</th>
                    <th>暗返单价</th>
                    <th>暗返日期</th>
                    <th>返利单价</th>
                        <th>中标价</th>
                        <th>执行价</th>
                    <th>返利点数（%）</th>
                    <th>返利形式</th>
                        <th>审核状态</th>
                        <th>创建人&emsp;&emsp;</th>
                        <th>创建时间&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>操作&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
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
                        <td>%{companyName}</td>
                        <td>%{companyLast}</td>
                        <td>%{hospitalName}</td>
                        <td>%{hospitalArea}</td>
                        <td>%{hospitalDeptName}</td>
                        <td>%{specDrugName}</td>
                        <td>%{difType}</td>
                        <td>%{priceTopicName}</td>

                        <td>%{brightPrice}</td>
                        <td>%{brightExecuteDateText}</td>
                        <td>%{darkPrice}</td>
                        <td>%{darkExecuteDateText}</td>
                        <td>%{rebatePrice}</td>
                        <td>%{biddingPrice}</td>
                        <td>%{executePrice}</td>
                        <td>%{rebateRate}</td>
                        <td>%{rebateStyleName}</td>
                        <td>%{approveText}</td>
                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                            <div class="btn-group">
                            <@check module="rebate" privilege="edit">
                                <button class="btn  btn-xs btn-info ace-tr-edit" data-id="%{id}">
                                    <i class="ace-icon fa fa-pencil"></i>编辑
                                </button>
                            </@check>
                            <@check module="rebate" privilege="remove">
                                <button class="btn btn-xs btn-danger ace-tr-delete" data-id="%{id}">
                                    <i class="ace-icon fa fa-trash-o"></i>删除
                                </button>
                            </@check>
                            <button class="btn btn-xs btn-success ace-tr-freeze" data-id="%{id}">
                                <i class="ace-icon fa fa-lock"></i>冻结
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
        delBatchUrl: "rebate.removeBatch.do",
        deleteUrl: "rebate.remove.do",
        listUrl: "rebate.search.do",
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        //新增|编辑
        $('#mainPageDiv').delegate('#addBtn,.ace-tr-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var title="编辑返利设置";
            if(typeof(id) == "undefined"){
                title = "新增返利设置";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "rebate.addOrEditIndex.do?id=" + id;
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
        //冻结
        $('#mainPageDiv').delegate('.ace-tr-freeze', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            myConfirm("确认冻结？冻结之后返利信息不可用",function () {
                ace.post('${ctx!}/html/rebate.freeze.do?id='+id, function (data) {
                    if (data.status == "ERROR") {
                        alertErr("哇哦，操作失败咯！");
                    }else if(data.status == "WARNING"){
                        alertErr(data.message);
                    }else if(data.status == "SUCCESS"){
                        alertSuc("操作成功！");
                        table.refresh();
                    }
                });
            })
        });

        //审批
        $("#approve").click(function(){
            var checkedIds = table.getCheckedIds();
            if (checkedIds.length > 0) {
                myConfirm("确认数据无误，审批通过？",function(){
                    ace.post('/html/rebate.approve.do?',{idsStr:checkedIds.join()}, function (data) {
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
            window.open('${ctx}/html/common.export.do?fileName=返利设置&exportClass=com.ace.app.cms.drug.domain.Rebate&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.Rebate";
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


