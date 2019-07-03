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
        <button type="button" class="btn btn-purple" id="exportAccountBtn">
            <i class="ace-icon fa fa-cloud-download"></i> 导出
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
                                <select name="order.companyId" class="required" style="max-width: 208px">
                                    <option value="">请选择商业公司</option>
                                <#list companyIdNameMap?keys as key>
                                    <option value="${key}" >${companyIdNameMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="order.hospitalId" class="required" style="max-width: 208px">
                                    <option value="">请选择医院</option>
                                <#list hospitalIdNameMap?keys as key>
                                    <option value="${key}">${hospitalIdNameMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="order.drugSpecName" class="required" >
                                    <option value="">请选择品规名称</option>
                                <#list drugSpecIdsNamesMap?keys as key>
                                    <option value="${drugSpecIdsNamesMap[key]}">
                                        ${drugSpecIdsNamesMap[key]}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">&nbsp;&nbsp;&nbsp;
                                <button type="button" class="btn btn-info btn-sm ace-table-search" id="searchBtn">
                                    <i class="ace-icon fa fa-search"></i>搜索
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
                    <th>药品规格</th>
                    <th>批号</th>
                    <th>数量</th>
                    <th>医院</th>
                    <th>日期</th>
                    <th>单价</th>
                    <th>商业公司</th>
                    <th>实际结算价</th>
                    <th>销售金额</th>
                    <th>商业公司所属销售区域</th>
                    <th>医院所属部门</th>
                    <th>医院所在行政区域</th>
                    <th>医院等级</th>
                    <th>医院性质</th>
                    <th>业务员姓名</th>
                    <th>扣率</th>
                    <th>中标价</th>
                    <th>开票价</th>
                    <th>返利点数</th>
                    <th>明返单价</th>
                    <th>暗返单价</th>
                    <th>返利单价</th>
                    <th>状态</th>
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
                        <td>%{drugSpecName}</td>
                        <td>%{drugNum}</td>
                        <td>%{drugCnt}</td>
                        <td>%{hospitalName}</td>
                        <td>%{orderDateText}</td>
                        <td>%{price}</td>
                        <td>%{companyName}</td>
                        <td>%{actualPrice}</td>
                        <td>%{totalMoney}</td>
                        <td>%{companyArea}</td>
                        <td>%{departmentName}</td>
                        <td>%{hospitalArea}</td>
                        <td>%{hospitalLavel}</td>
                        <td>%{hospitalType}</td>
                        <td>%{salesmanName}</td>
                        <td>%{deductionRate}</td>
                        <td>%{biddingPrice}</td>
                        <td>%{billingPrice}</td>
                        <td>%{rebateRate}</td>
                        <td>%{brightPrice}</td>
                        <td>%{darkPrice}</td>
                        <td>%{rebatePrice}</td>
                        <td>%{statusText}</td>

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
        deleteUrl: "order.remove.do",
        listUrl: "order.search.do",
        id: "id"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();

        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/common.export.do?fileName=销售总表&exportClass=com.ace.app.cms.drug.domain.OrderSummary&'+json);
        });


    });

</script>


