<title>考核医院数量</title>
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
                                <select name="order.salesmanId" class="chosen-select" style="max-width: 308px">
                                    <option value="">请选择业务员</option>
                                <#list salesmen as salesman>
                                    <option value="${salesman.id}">
                                        ${salesman.name}</option>
                                </#list>
                                </select>
                            </div>

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
                                    <option value="">请选择品规</option>
                                <#list drugSpecs as drug>
                                    <option value="${drug.id}">
                                        ${drug.specName}</option>
                                </#list>
                                </select>
                            </div>



                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                                <input type="text" value="${startDateText}" name="order.startTimeText" class="date" style="width: 90px"/>一
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
                        <th>业务员所在部门</th>
                        <th>业务员</th>
                        <th>医院</th>
                        <th>品规</th>
                        <th>月均实际销量</th>
                        <th>考核周期</th>
                        <th>月均考核量</th>
                        <th>差值</th>
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
                        <td>%{salesmanName}</td>
                        <td>%{hospitalName}</td>
                        <td>%{drugSpecName}</td>
                        <td>%{drugCnt}</td>
                        <td>%{traceRange}</td>
                        <td>%{taskCnt}</td>
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
        listUrl: "report.hosCntPage.do"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();
        $('.date').datepicker({
            format: 'yyyy-mm'
        });

        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/report.exportHosCnt.do?'+json);
        });
    });

</script>


