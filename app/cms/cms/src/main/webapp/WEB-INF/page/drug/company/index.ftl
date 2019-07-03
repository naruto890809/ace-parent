<title>商业公司</title>
<div id="mainPageDiv">
    <div class="page-header">
    <@check module="company" privilege="import">
        <button type="button" class="btn btn-pink" id="importAccountBtn">
            <i class="ace-icon fa fa-cloud-upload"></i> 导入
        </button>
    </@check>

    <@check module="company" privilege="export">
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
        </button>
    </@check>

    <@check module="company" privilege="add">
        <button type="button" class="btn btn-info" id="addBtn">
            <i class="ace-icon fa fa-plus"></i> 添加
        </button>
    </@check>

    <@check module="company" privilege="approve">
        <button type="button" class="btn btn-success" id="approve">
            <i class="ace-icon fa fa-undo"></i>审批
        </button>
    </@check>

                    <@check module="company" privilege="remove">
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
                                <label for="area">&nbsp;&nbsp;&nbsp;客户名称：</label>
                                <input type="text" class="input-medium" name="company.name"/>
                            </div>
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;别名：</label>
                                <input type="text" class="input-medium" name="company.alias"/>
                            </div>
                            <div class="form-group">
                                <select name="company.channel" class="required">
                                    <option value="">请选择销售渠道</option>
                                <#list channels as channel>
                                    <option value="${channel.id}" >${channel.name}</option>
                                </#list>
                                </select>
                            </div>
                            <div class="form-group">
                                <select name="company.provinceSearch" id="province"></select>
                                <select name="company.citySearch" id="city"></select>
                            </div>
                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;审核状态：</label>
                                <select name="company.approve">
                                    <option value="">全部</option>
                                    <option value="PASSED">通过</option>
                                    <option value="APPROVING">审核中</option>
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
                        <th>客户名称</th>
                    <th>客户代码</th>
                    <th>别名</th>
                    <th>销售渠道</th>
                    <th>所在省</th>
                    <th>所在市</th>
                    <th>详细地址</th>
                    <th>联系人</th>
                    <th>联系方式</th>
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
                        <td>%{code}</td>
                        <td>%{alias}</td>
                        <td>%{channelName}</td>
                        <td>%{sellAreaName}</td>
                        <td>%{sellAreaId}</td>
                        <td>%{address}</td>
                        <td>%{person}</td>
                        <td>%{contacts}</td>
                        <td>%{approveText}</td>
                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                            <div class="btn-group">
                            <@check module="company" privilege="edit">
                                <button class="btn  btn-xs btn-info ace-tr-edit" data-id="%{id}">
                                    <i class="ace-icon fa fa-pencil"></i>编辑
                                </button>
                            </@check>
                            <@check module="company" privilege="remove">
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
    var scripts = ["${ctx!}/js/PCASClass.js"];
    var option = {
        appCode: "cms",
        corpCode: "",
        delBatchUrl: "company.removeBatch.do",
        deleteUrl: "company.remove.do",
        listUrl: "company.search.do",
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        //新增|编辑
        $('#mainPageDiv').delegate('#addBtn,.ace-tr-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr("data-id");
            var title="编辑商业公司";
            if(typeof(id) == "undefined"){
                title = "新增商业公司";
                id = "";
            }
            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "company.addOrEditIndex.do?id=" + id;
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
                    ace.post('/html/company.approve.do?',{idsStr:checkedIds.join()}, function (data) {
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
            window.open('${ctx}/html/common.export.do?fileName=商业公司&exportClass=com.ace.app.cms.drug.domain.Company&'+json);
        });

        //导入
        $('#mainPageDiv').delegate('#importAccountBtn', 'click', function (e) {
            e.preventDefault();

            var div = $("<div id='divDialog' title='导入'></div>").appendTo($("BODY"));
            var url = "common.importIndex.do?exportClass=com.ace.app.cms.drug.domain.Company";
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

        new PCAS("company.provinceSearch","company.citySearch");

    });

</script>


