<style>
    .dataTables_wrapper{
        max-width: 4000px;overflow-x: auto;
        background-color: #EFF3F8;
    }.dataTables_wrapper th{
         white-space:nowrap;
     }
</style>
<title>销售总表</title>
<div id="mainPageDiv">
    <div class="page-header">
    <@check module="order" privilege="exportSummary">
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
        </button>
    </@check>


<@check module="order" privilege="editSummary">
        <button type="button" class="btn btn-default " id="updateSalesman">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改部门
        </button>

        <button type="button" class="btn btn-primary" id="updateSalesman1">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改业务员
        </button>

        <button type="button" class="btn btn-info" id="updateHosArea">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改行政区域
        </button>
<button type="button" class="btn btn-success" id="updatePriceTopicName">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改议价主体
        </button>
<button type="button" class="btn btn-danger" id="updateHospitalLavel">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改医院等级
        </button>
<button type="button" class="btn btn-pink" id="updateHospitalType">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改医院性质
        </button>
<button type="button" class="btn btn-inverse" id="updateDarkPrice">
            <i class="ace-icon fa fa-pencil-square-o"></i>修改暗返单价
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
                                <select name="orderSummary.companyId" class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择商业公司</option>
                                <#list companyIdNameMap?keys as key>
                                    <option value="${key}" >${companyIdNameMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <#--<div class="form-group">
                                <select name="order.hospitalId" class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择医院</option>
                                <#list hospitals as key>
                                    <option value="${key.id}">${key.name}</option>
                                </#list>
                                </select>
                            </div>
-->

                            <div class="form-group">
                                    <label for="area">&nbsp;&nbsp;&nbsp;业务员：</label>
                                    <input type="text" name="orderSummary.salesmanName" class="input-small" id="salesmanName"/>
                            </div>

                            <div class="form-group">
                                <select name="orderSummary.drugSpecName" class="chosen-select" >
                                    <option value="">请选择品规名称</option>
                                <#list drugSpecIdsNamesMap?keys as key>
                                    <option value="${drugSpecIdsNamesMap[key]}">
                                        ${drugSpecIdsNamesMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="orderSummary.departmentName" class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择推广部</option>
                                <#list departments as department>
                                    <option value="${department.name}">${department.name}</option>
                                </#list>
                                </select>
                            </div>

                             <div class="form-group">
                                <select name="orderSummary.bigArea" class="chosen-select" style="max-width: 208px">
                                    <option value="">请选择大区</option>
                                <#list bigAreas as bigArea>
                                    <option value="${bigArea}">${bigArea}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="orderSummary.priceTopicName" class="chosen-select">
                                    <option value="">请选择议价主体</option>
                                <#list priceTopics as type>
                                    <option value="${type.name}">${type.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="orderSummary.hospitalLavel">
                                    <option value="">请选择医院等级</option>
                                <#list levels as level>
                                    <option value="${level.name}">${level.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="orderSummary.hospitalType">
                                    <option value="">请选择医院性质</option>
                                <#list types as type>
                                    <option value="${type.name}">${type.name}</option>
                                </#list>
                                </select>
                            </div>



                            <br>
                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <label for="province" >日期</label>
                                <input type="text"  style="width: 100px;" name="orderSummary.startTime" class="date" />-
                                <input type="text"  style="width: 100px;" name="orderSummary.endTime" class="date" />
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;批号：</label>
                                <input type="text" name="orderSummary.drugNum" class="input-medium" id="drugNum"/>
                            </div>


                             <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;行政区域：</label>
                                <input type="text" name="orderSummary.hospitalArea" class="input-medium" id="hospitalArea"/>
                            </div>


                             <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;医院名称：</label>
                                <input type="text" name="orderSummary.hospitalNameSplit" class="input-medium" id="hospitalNameSplit"/>
                            </div>

                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;状态：</label>
                                <select name="orderSummary.status">
                                    <option value="">全部</option>
                                    <option value="formal">正常</option>
                                    <option value="123">异常</option>
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
                        <th>商业公司地区&emsp;&emsp;&emsp;&emsp;</th>
                        <th>年</th>
                        <th>月</th>
                        <th>日期</th>
                        <th>大区&emsp;&emsp;&emsp;</th>
                        <th>推广部名称&emsp;&emsp;</th>
                        <th>行政区域</th>
                        <th>业务员</th>
                        <th>议价主体</th>
                        <th>医院名称&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>医院原始名称 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>医院原始地址  &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>医院等级</th>
                        <th>医院性质</th>
                        <th>品名&emsp;&emsp;&emsp;&emsp;</th>
                        <th>品名规格&emsp;&emsp;&emsp;&emsp;</th>
                        <th>数量</th>
                        <th>医保支付价</th>
                        <th>终端开票价</th>
                        <th>开票价</th>
                        <th>返利点数</th>
                        <th>明返单价</th>
                        <th>暗返单价</th>
                        <th>返利单价</th>
                        <#--<th>实际结算价</th>-->
                        <th>销售金额</th>
                        <th>商业公司&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>商品代码&emsp;&emsp;</th>
                        <th>分公司&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</th>
                        <th>批号&emsp;&emsp;</th>

                        <th>状态</th>
                        <th>创建人</th>
                        <th>创建时间&emsp;&emsp;&emsp;&emsp;&emsp;</th>
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
                        <td>%{companyArea}%{companySellAreaId}</td>
                        <td>%{year}</td>
                        <td>%{month}</td>
                        <td>%{orderDateText}</td>
                        <td>%{bigArea}</td>
                        <td>%{departmentName}</td>
                        <td>%{hospitalArea}</td>
                        <td>%{salesmanName}</td>
                        <td>%{priceTopicName}</td>
                        <td>%{hospitalName}</td>
                        <td>%{hospitalPreName}</td>
                        <td>%{hospitalPreAddre}</td>
                        <td>%{hospitalLavel}</td>
                        <td>%{hospitalType}</td>
                        <td>%{drugName}</td>
                        <td>%{specName}</td>
                        <td>%{drugCnt}</td>
                        <td>%{biddingPrice}</td>
                        <td>%{price}</td>
                        <td>%{billingPrice}</td>
                        <td>%{rebateRate}</td>
                        <td>%{brightPrice}</td>
                        <td>%{darkPrice}</td>
                        <td>%{rebatePrice}</td>
                        <#--<td>%{actualPrice}</td>-->
                        <td>%{totalMoney}</td>
                        <td>%{companyName}</td>
                        <td>%{drugCode}</td>
                        <td>%{branchCompany}</td>
                        <td>%{drugNum}</td>

                        <td>%{statusText1}</td>
                        <td>%{createByName}</td>
                        <td>%{createTimeText}</td>
                        <td>
                        <@check module="order" privilege="delSummary">
                            <div class="btn-group">
                                <button class="btn btn-xs btn-danger ace-tr-delete" data-id="%{id}">
                                    <i class="ace-icon fa fa-trash-o"></i>删除
                                </button>
                            </div>
                        </@check>

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
        listUrl: "order.search.do",
        pageSize: 10,
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        $('.date').datepicker({
        });
        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/common.export.do?fileName=销售总表&exportClass=com.ace.app.cms.drug.domain.OrderSummary&'+json);
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
            var url = "order.editDeptIndex.do?id=" + ids.join();
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

        //批量修改业务员
        $('#updateSalesman1').click(function () {
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
            var url = "order.editSalesmanIndex.do?id=" + ids.join();
            var content = div.load(url, {});
            $("#divDialog").data("url", url);
            div.dialog({
                autoOpen: true,
                width: 300,
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


        $('#updateHosArea').click(function () {
            var $col = $("#ace-table").find(".ace-check-item:checked");
            if ($col.length < 1) {
                alertErr("请选中要修改的行");
                return;
            }
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });

            var title="编辑行政区域";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "order.editHosAreaIndex.do?id=" + ids.join();
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

        $('#updatePriceTopicName').click(function () {
            var $col = $("#ace-table").find(".ace-check-item:checked");
            if ($col.length < 1) {
                alertErr("请选中要修改的行");
                return;
            }
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });

            var title="编辑议价主体";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "order.editPriceTopicNameIndex.do?id=" + ids.join();
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

        $('#updateHospitalLavel').click(function () {
            var $col = $("#ace-table").find(".ace-check-item:checked");
            if ($col.length < 1) {
                alertErr("请选中要修改的行");
                return;
            }
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });

            var title="编辑医院等级";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "order.editHospitalLavelIndex.do?id=" + ids.join();
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

        $('#updateHospitalType').click(function () {
            var $col = $("#ace-table").find(".ace-check-item:checked");
            if ($col.length < 1) {
                alertErr("请选中要修改的行");
                return;
            }
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });

            var title="编辑医院性质";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "order.editHospitalTypeIndex.do?id=" + ids.join();
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


        $('#updateDarkPrice').click(function () {
            var $col = $("#ace-table").find(".ace-check-item:checked");
            if ($col.length < 1) {
                alertErr("请选中要修改的行");
                return;
            }
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });

            var title="编辑暗返单价";

            var div = $("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
            var url = "order.editDarkIndex.do?id=" + ids.join();
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


