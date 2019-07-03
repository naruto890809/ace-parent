<div class="row">
    <p>
        <button class="btn">Default </button>
        <button class="btn btn-primary">Primary</button>
        <button class="btn btn-info">Info</button>
        <button class="btn btn-success">Success</button>
        <button class="btn btn-warning">Warning</button>
        <button class="btn btn-danger">Danger</button>
        <button class="btn btn-inverse">Inverse</button>
        <button class="btn btn-pink">Pink</button>
    </p>
</div>


<div class="row" style="margin-top: 20px;">
    <div class="row">
        <div class="col-sm-12">
            <form id="departmentForm">
                <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                <input type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                &nbsp;&nbsp;
                <select name="order.deptmentId" class="chosen-select" style="max-width: 308px">
                    <option value="">请选择部门</option>
                                <#list departments as department>
                                    <option value="${department.id}">
                                        ${department.name}</option>
                                </#list>
                </select>
                &nbsp;&nbsp;
                <button type="button" class="btn btn-info btn-sm  ace-table-search" id="departmentSearch">
                    <i class="ace-icon fa fa-search"></i>搜索
                </button>
                <button type="button" class="btn btn-grey btn-info btn-sm " id="cleanBtn1" onclick="window.location.reload()">
                    <i class="ace-icon fa fa-repeat"></i>清空
                </button>
            </form>
        </div>
    </div>
    <br/>
    <br/>
    <div class="col-sm-6">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
                <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-star orange"></i>
                    推广部销售额
                </h4>


            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main no-padding">
                    <table class="table table-bordered table-striped">
                        <thead class="thin-border-bottom">
                        <tr>
                            <th>
                                <i class="ace-icon fa fa-caret-right blue"></i>name
                            </th>

                            <th>
                                <i class="ace-icon fa fa-caret-right blue"></i>price
                            </th>

                            <th class="hidden-480">
                                <i class="ace-icon fa fa-caret-right blue"></i>status
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>internet.com</td>

                            <td>
                                <small>
                                    <s class="red">$29.99</s>
                                </small>
                                <b class="green">$19.99</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-info arrowed-right arrowed-in">on sale</span>
                            </td>
                        </tr>
                        <tr>
                            <td>online.com</td>

                            <td>
                                <b class="blue">$16.45</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-success arrowed-in arrowed-in-right">approved</span>
                            </td>
                        </tr>
                        <tr>
                            <td>newnet.com</td>

                            <td>
                                <b class="blue">$15.00</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-danger arrowed">pending</span>
                            </td>
                        </tr>
                        <tr>
                            <td>web.com</td>

                            <td>
                                <small>
                                    <s class="red">$24.99</s>
                                </small>
                                <b class="green">$19.95</b>
                            </td>

                            <td class="hidden-480">
																	<span class="label arrowed">
																		<s>out of stock</s>
																	</span>
                            </td>
                        </tr>
                        <tr>
                            <td>domain.com</td>

                            <td>
                                <b class="blue">$12.00</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-warning arrowed arrowed-right">SOLD</span>
                            </td>
                        </tr>
                        <tr>
                            <td>domain.com</td>

                            <td>
                                <b class="blue">$12.00</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-warning arrowed arrowed-right">SOLD</span>
                            </td>
                        </tr>
                        <tr>
                            <td>domain.com</td>

                            <td>
                                <b class="blue">$12.00</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-warning arrowed arrowed-right">SOLD</span>
                            </td>
                        </tr>
                        <tr>
                            <td>domain.com</td>

                            <td>
                                <b class="blue">$12.00</b>
                            </td>

                            <td class="hidden-480">
                                <span class="label label-warning arrowed arrowed-right">SOLD</span>
                            </td>
                        </tr>
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
            ace.post('/html/report.departmentReport.do?',$("#departmentForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var departmentChar = echarts.init(document.getElementById('department'));
                    var option = {
                        title: {
                            text: '推广部销售额',
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


    });

</script>
