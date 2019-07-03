<title>销售概况</title>
<div id="mainPageDiv">

    <form id="submitForm">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_wrapper form-inline ">
                <div class="row">
                    <div class="row">
                        <form id="extend-form" class="form-inline">
                            &nbsp;&nbsp;&nbsp;

                            <div class="form-group" name="order.hospitalCity">
                                <label for="area">&nbsp;&nbsp;&nbsp;医院所属市：</label>
                                <input type="text" name="order.hospitalCity" class="input-medium" id="hospitalCity"/>
                            </div>

                            <div class="form-group" name="order.hospitalArea">
                                <label for="area">&nbsp;&nbsp;&nbsp;医院行政区域：</label>
                                <input type="text" name="order.hospitalArea" class="input-medium" id="hospitalArea"/>
                            </div>

                            <div class="form-group">
                                <select name="order.deptmentId" class="chosen-select" style="max-width: 308px">
                                    <option value="">请选择部门</option>
                                <#list departments as department>
                                    <option value="${department.id}">
                                        ${department.name}</option>
                                </#list>
                                </select>
                            </div>



                         <!--   <div class="form-group" >
                                <label for="area">&nbsp;&nbsp;&nbsp;请选择部门：</label>

                                <input type="text" value="${account.email}" name="order.departmentName" class="" style="width: 90px"/>
                            </div>-->


                            <div class="form-group">
                                <label >类型</label>
                                <select name="order.summaryType">
                                    <option value="">整体</option>
                                    <option value="cx">纯销</option>
                                    <option value="ls">零售</option>
                                </select>
                            </div>


                            <div class="form-group">
                                <label >计量方式</label>
                                <select name="order.reportType">
                                    <option value="coefficient">数量</option>
                                    <option value="total_money">金额</option>
                                </select>
                            </div>


                            <div class="form-group">
                                <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                                <input id ="qssj" type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                                <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                                <!--<input type="text" value="${account.accountEmailList}" name="order.endTime" class="date" style="width: 90px"/>
                            --></div>


                            <div class="form-group">
                                <label >排序</label>
                                <select name="order.orderBy">
                                    <option value="">整体</option>
                                    <option value="deptment_id">推广部</option>
                                    <option value="drug_id">产品</option>
                                </select>
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
                        <th>维度</th>
                        <th>年</th>
                        <th>月</th>
                        <th>品名</th>
                        <th>类型</th>
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
                        <td>


                                浙江省


                                %{hospitalCity}%{hospitalArea}%{departmentName}


                        </td>

                        <td>%{year}</td>
                        <td>%{month}</td>
                        <td>%{drugName}</td>
                        <td>%{summaryType}</td>
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
        listUrl: "report.summaryPage.do"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();

        $('.date').datepicker({
        });

        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/report.exportSummary.do?'+json);
        });
        $('#cleanBtn').click(function () {
            $("#mainPageDiv form input").each(function(){
                $(this).val('');
            });
            $("#screen-window form select").each(function(){
                $(this).val('');
            });
            window.location.reload();
        });

    });

</script>


