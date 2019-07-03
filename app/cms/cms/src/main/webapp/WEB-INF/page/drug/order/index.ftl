<style>
    .dataTables_wrapper{
        max-width: 4000px;overflow-x: auto;
        background-color: #EFF3F8;
    }.dataTables_wrapper th{
         white-space:nowrap;
     }
</style>
<title>流向清单</title>
<div id="mainPageDiv">
    <div class="page-header">
    <@check module="order" privilege="import">
        <button type="button" class="btn btn-pink" id="importAccountBtn">
            <i class="ace-icon fa fa-cloud-upload"></i> 导入
        </button>
    </@check>

    <@check module="order" privilege="export">
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
        </button>
    </@check>

    <@check module="order" privilege="add">
        <button type="button" class="btn btn-info" id="addBtn">
            <i class="ace-icon fa fa-plus"></i> 添加
        </button>
    </@check>

    <@check module="order" privilege="exportRecord">
        <button type="button" class="btn btn-yellow" id="exportRecord" onclick="ace.util.jumpTo('exportRecord.index.do')">
            <i class="ace-icon fa fa-book"></i>导入记录
        </button>
    </@check>
            <@check module="order" privilege="remove">
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
                                <select name="order.companyId" class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择商业公司&emsp;&emsp;&emsp;</option>
                                <#list companyIdNameMap?keys as key>
                                    <option value="${key}" >${companyIdNameMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="order.hospitalId" class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择医院&emsp;&emsp;&emsp;&emsp;</option>
                                <#list hospitals as key>
                                    <option value="${key.id}">${key.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="order.drugSpecName" class="chosen-select" >
                                    <option value="">请选择品规名称&emsp;&emsp;</option>
                                <#list drugSpecIdsNamesMap?keys as key>
                                    <option value="${drugSpecIdsNamesMap[key]}">
                                    ${drugSpecIdsNamesMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="order.creater" class="chosen-select" >
                                    <option value="">请选创建人</option>
                                <#list accounts as account>
                                    <option value="${account.accountId}">${account.accountName}</option>
                                </#list>
                                </select>
                            </div>

                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <label for="province" >订单时间</label>
                                <input type="text"  style="width: 100px;" name="order.startTime" class="date" />-
                                <input type="text"  style="width: 100px;" name="order.endTime" class="date" />
                            </div>
                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <label for="province" >创建时间</label>
                                <input type="text"  style="width: 100px;" name="order.createStart" class="date" />-
                                <input type="text"  style="width: 100px;" name="order.createEnd" class="date" />
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
                        <th>药品名称&emsp;&emsp;</th>
                        <th>规格&emsp;&emsp;</th>
                    <th>批号&emsp;</th>
                    <th>数量</th>
                    <th>医院名称&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>医院原始名称&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>医院原始地址 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>日期&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>单价</th>
                    <th>商业公司&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                    <th>分公司&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>创建人&emsp;</th>
                        <th>创建时间&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>操作&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
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
                        <td>%{drugName}</td>
                        <td>%{specName}</td>
                        <td>%{drugNum}</td>
                        <td>%{drugCnt}</td>
                        <td>%{hospitalName}</td>
                        <td>%{hospitalPreName}</td>
                        <td>%{hospitalPreAddre}</td>
                        <td>%{orderDateText}</td>
                        <td>%{priceText}</td>
                        <td>%{companyName}</td>
                        <td>%{branchCompany}</td>

                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                            <div class="btn-group">
                            <@check module="order" privilege="edit">
                                <button class="btn  btn-xs btn-info ace-tr-edit" data-id="%{id}">
                                    <i class="ace-icon fa fa-pencil"></i>编辑
                                </button>
                            </@check>
                            <@check module="order" privilege="remove">
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
        deleteUrl: "order.remove.do",
        delBatchUrl: "order.removeBatch.do",
        listUrl: "order.search.do?order.tb=1",
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
            var title="编辑流向清单";
            if(typeof(id) == "undefined"){
                title = "新增流向清单";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "order.addOrEditIndex.do?id=" + id;
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
            window.open('${ctx}/html/common.export.do?fileName=流向清单&exportClass=com.ace.app.cms.drug.domain.Order&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.Order";
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


