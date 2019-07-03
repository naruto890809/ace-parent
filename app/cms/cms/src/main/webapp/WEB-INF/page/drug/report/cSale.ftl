<title>纯销数据</title>
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
                                <select name="order.deptmentId" class="chosen-select" style="max-width: 308px">
                                    <option value="">请选择部门</option>
                                <#list departments as department>
                                    <option value="${department.id}">
                                        ${department.name}</option>
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



                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;年月：</label>
                                <input type="text" value="${endDateText}" name="order.endTimeText" class="date" style="width: 90px"/>
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
                        <th>部门</th>
                        <th>产品</th>
                        <th>年度任务</th>
                        <th>当月销量</th>
                        <th>当月同比</th>
                        <th>当年累积销量</th>
                        <th>当年累积同比</th>
                        <th>完成率</th>
                        <th>差量</th>
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
                        <td>%{departmentName}</td>
                        <td>%{drugName}</td>
                        <td>%{taskCnt}</td>
                        <td>%{coefficient}</td>
                        <td>%{monthTb}</td>
                        <td>%{yearCnt}</td>
                        <td>%{yearTb}</td>
                        <td>%{completeRatio}%</td>
                        <td>%{dif}</td>
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
        listUrl: "report.cSalePage.do"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        $('.date').datepicker({
            format: 'yyyy-mm'
        });


        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/report.exportCSale.do?'+json);
        });
    });

</script>


