<title>产品医院</title>
<div id="mainPageDiv">

    <form id="submitForm"></form>
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <div class="row">
                    <div class="row">
                        <form id="extend-form" class="form-inline">
                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <select name="order.hospitalId" class="chosen-select" style="max-width: 308px">
                                    <option value="">请选择医院</option>
                                <#list hospitals as hospital>
                                    <option value="${hospital.id}">${hospital.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <select name="order.drugId" class="chosen-select" style="max-width: 308px">
                                    <option value="">请选择产品</option>
                                <#list drugs as drug>
                                    <option value="${drug.id}">
                                        ${drug.name}</option>
                                </#list>
                                </select>
                            </div>
                            &nbsp;&nbsp;&nbsp;
                            <div class="form-group">
                                <label >计量方式</label>
                                <select name="order.reportType">
                                    <option value="coefficient">数量</option>
                                    <option value="total_money">金额</option>
                                </select>
                            </div>


                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                                <input type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                                <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                            </div>


                            <div class="form-group">
                                <button type="button" class="btn btn-info btn-sm  ace-table-search" id="searchBtn">
                                    <i class="ace-icon fa fa-search"></i>搜索
                                </button>
                                <button type="button" class="btn btn-grey btn-info btn-sm " id="cleanBtn1" onclick="window.location.reload()">
                                    <i class="ace-icon fa fa-repeat"></i>清空
                                </button>
                                <button type="button" class="btn btn-purple  btn-sm " id="exportAccountBtn">
                                    <i class="ace-icon fa fa-cloud-download"></i> 导出
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
                        <th>品名</th>
                        <th>医院</th>
                        <th>部门</th>
                        <!--<th>员工</th>-->
                        <th>数量|金额</th>
                        <th>同比</th>
                        <th>环比</th>
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
                        <td>%{hospitalName}</td>
                        <td>%{departmentName}</td>
                      <!--  <td>%{salesmanName}</td>-->
                        <td>%{amountOrMoney}</td>
                        <td>%{tb}</td>
                        <td>%{hb}</td>
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
        corpCode: "${corpCode!}",
        listUrl: "report.drugHosPage.do"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        $('.date').datepicker({
        });


        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/report.exportDrugHos.do?'+json);
        });
    });

</script>


