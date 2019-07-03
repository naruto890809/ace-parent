<div class="row">
    <p>
        <button class="btn btn-info" onclick="ace.util.jumpTo('report.UI.do')">销售概况</button>
        <button class="btn btn-success" onclick="ace.util.jumpTo('report.departmentDrugReportIndex.do')">产品架构</button>
        <button class="btn btn-warning" onclick="ace.util.jumpTo('report.drugDepartmentReportIndex.do')">部门销售排名</button>
        <button class="btn btn-danger" onclick="ace.util.jumpTo('report.drugAreaReportIndex.do')">地区销售排名</button>
        <button class="btn btn-inverse" onclick="ace.util.jumpTo('report.hospitalDrugReportIndex.do')">医院销售排名</button>
        <button class="btn btn-pink"  onclick="ace.util.jumpTo('report.drugHospitalReportIndex2.do')">药品医院排名</button>
    </p>
</div>
<br/>


<div class="row" style="margin-top: 20px;">
    <div class="row">
        <div class="col-sm-12">
            <form id="departmentForm">
                <input type="hidden" name="order.exportType" value="departmentDrugReport">
                &nbsp;&nbsp;&nbsp;
                <label >统计维度</label>
                <select name="order.reportDif">
                    <option value="department_name">推广部</option>
                    <option value="hospital_city">市</option>
                    <option value="hospital_area">区</option>
                </select>
                &nbsp;&nbsp;&nbsp;
                <input type="text" name="order.reportDifName" placeholder="请填写推广部|市|区名称">

                <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                <input type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                &nbsp;&nbsp;&nbsp;
                <label >计量方式</label>
                <select name="order.reportType">
                    <option value="coefficient">数量</option>
                    <option value="total_money">金额</option>
                </select>
                &nbsp;&nbsp;
                <button type="button" class="btn btn-info btn-sm  ace-table-search" id="departmentSearch">
                    <i class="ace-icon fa fa-search"></i>搜索
                </button>
                <button type="button" class="btn btn-grey btn-info btn-sm " id="cleanBtn1" onclick="window.location.reload()">
                    <i class="ace-icon fa fa-repeat"></i>清空
                </button>
                <button type="button" class="btn btn-purple  btn-sm" id="exportAccountBtn">
                    <i class="ace-icon fa fa-cloud-download"></i> 导出
                </button>
            </form>
        </div>
    </div>
    <br/>
    <div class="col-sm-6">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
                <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-star orange"></i>
                    产品架构
                </h4>
            </div>

            <div class="widget-body" style="display: block;height: 70%;overflow-y:auto">
                <div class="widget-main no-padding">
                    <table class="table table-bordered table-striped">
                        <thead class="thin-border-bottom">
                        <tr>
                            <th>
                                <i class="ace-icon fa fa-caret-right red"></i>排名
                            </th>
                            <th>
                                <i class="ace-icon fa fa-caret-right green"></i>维度
                            </th>
                            <th>
                                <i class="ace-icon fa fa-caret-right pink"></i>品名
                            </th>
                            <th class="hidden-480">
                                <i class="ace-icon fa fa-caret-right blue"></i>数量/金额
                            </th>
                        </tr>
                        </thead>

                        <tbody id="mainTable">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
<#--推广部销售额-->
    <div class="col-sm-6" style="min-height: 310px;margin-top: 67px;">
        <div id="department" style="height: 300px;"></div>
    </div>
</div>



<script type="text/javascript">
    var scripts = ["${ctx!}/js/echarts.common.min.js"];
    var option = {
        appCode: "cms",
        corpCode: "${corpCode!}"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        $('.date').datepicker({
        });
    <#--推广部销售额-->
        $("#departmentSearch").click(function () {
            ace.post('/html/report.departmentDrugReport.do?',$("#departmentForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var mainTable = ""
                    var orders = data.data.orders;
                    for(var i = 0 ; i<orders.length;i++){
                        var order = orders[i];
                        mainTable +='<tr>'+
                                '<td><b class="red">'+(i+1)+'</b></td>'+
                                '<td><b class="green">'+order.reportDifName+'</b></td>'+
                                '<td><b class="pink">'+order.drugName+'</b></td>'+
                                '<td class="hidden-480"><span class="label label-info arrowed-right arrowed-in">'+order.totalMoneyText+'</span></td>'+
                                '</tr>';
                    }

                    $("#mainTable").html(mainTable);

                    var departmentChar = echarts.init(document.getElementById('department'));
                    var option = {
                        title: {
                            text: data.data.reportDifName+'产品架构',
                            subtext: '',
                            left: 'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "销售额 <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            type: 'scroll',
                            orient: 'vertical',
                            right: 0,
                            data: data.data.names
                        },
                        series : [
                            {
                                type: 'pie',
                                radius : '65%',
                                center: ['50%', '50%'],
                                selectedMode: 'single',
                                data:data.data.nameValues,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    departmentChar.setOption(option);
                }
            });
        });

        $("#departmentSearch").trigger("click");

        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#departmentForm").serialize();
            window.open('${ctx}/html/common.exportReport.do?fileName=产品架构&exportClass=com.ace.app.cms.drug.domain.report.DepartmentDrugReport&'+json);
        });

    });

</script>
