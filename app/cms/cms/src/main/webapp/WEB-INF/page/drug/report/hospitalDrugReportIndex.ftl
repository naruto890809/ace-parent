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
            <form id="drugAreaForm">
                <input type="hidden" name="order.exportType" value="hospitalDrugReport">
                <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                <input type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                &nbsp;&nbsp;
                <select name="order.drugId" class="chosen-select" style="max-width: 308px">
                    <#list drugs as drug>
                        <option value="${drug.id}">
                            ${drug.name}</option>
                    </#list>
                </select>

                &nbsp;&nbsp;
                <button type="button" class="btn btn-info btn-sm  ace-table-search" id="drugAreaSearch">
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
    <br/>
    <div class="row">
        <div class="col-sm-6">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-star orange"></i>
                        医院销售排名
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
                                    <i class="ace-icon fa fa-caret-right green"></i>品名
                                </th>
                                <th>
                                    <i class="ace-icon fa fa-caret-right pink"></i>医院
                                </th>
                                <th class="hidden-480">
                                    <i class="ace-icon fa fa-caret-right blue"></i>销售额
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

        <div class="col-sm-6" style="min-height: 310px;margin-top: 15px;">
            <div id="drugAreaYZ" style="height: 300px;"></div>
        </div>
    </div>
    <#--推广部销售额-->

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
    <#--产品区域销量-->
        $("#drugAreaSearch").click(function () {
            ace.post('/html/report.hospitalDrugReport.do?',$("#drugAreaForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var mainTable = ""
                    var orders = data.data.orders;
                    for(var i = 0 ; i<orders.length;i++){
                        var order = orders[i];
                        mainTable +='<tr>'+
                                '<td><b class="red">'+(i+1)+'</b></td>'+
                                '<td><b class="green">'+order.drugName+'</b></td>'+
                                '<td><b class="pink">'+order.hospitalName+'</b></td>'+
                                '<td class="hidden-480"><span class="label label-info arrowed-right arrowed-in">'+order.totalMoneyText+'</span></td>'+
                                '</tr>';
                    }

                    $("#mainTable").html(mainTable);


                    var drugAreaYZChar = echarts.init(document.getElementById('drugAreaYZ'));
                    var drugAreaYZOption = {
                        title: {
                            text: data.data.drugName+'医院销售排名'
                        },
                        tooltip: {},
                        legend: {
                            data:['销售额']
                        },
                        xAxis: {
                            data: data.data.names,
                            axisLabel: {
                                //这个是倾斜角度，也是考虑到文字过多的时候，方式覆盖采用倾斜
                                rotate: 40,
                                //这里是考虑到x轴文件过多的时候设置的，如果文字太多，默认是间隔显示，设置为0，标示全部显示，当然，如果x轴都不显示，那也就没有意义了
                                interval :0
                            }
                        },
                        yAxis: {},
                        series: [{
                            name: '销售额',
                            type: 'bar',
                            data: data.data.values,
                            itemStyle: {
                                normal: {
                                    //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                                    color: function(params) {
                                        // build a color map as your need.
                                        var colorList = [
                                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                            '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                        ];
                                        return colorList[params.dataIndex]
                                    }
                                }
                            }
                        }]
                    };
                    drugAreaYZChar.setOption(drugAreaYZOption);
                }
            });
        });

        $("#drugAreaSearch").trigger("click");

        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#drugAreaForm").serialize();
            window.open('${ctx}/html/common.exportReport.do?fileName=医院销售排名&exportClass=com.ace.app.cms.drug.domain.report.HospitalDrugReport&'+json);
        });

    });

</script>
