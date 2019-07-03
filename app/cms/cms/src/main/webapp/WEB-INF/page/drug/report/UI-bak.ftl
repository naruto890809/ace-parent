<div class="row">
    <#--推广部销售额-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
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

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="department" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#--推广部产品架构-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="departmentDrugForm">
                    <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                    <input type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                    <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                    &nbsp;&nbsp;
                    <select name="order.deptmentId" class="chosen-select" style="max-width: 308px">
                                <#list departments as department>
                                    <option value="${department.id}">
                                        ${department.name}</option>
                                </#list>
                    </select>
                    &nbsp;&nbsp;

                    <button type="button" class="btn btn-info btn-sm  ace-table-search" id="departmentDrugSearch">
                        <i class="ace-icon fa fa-search"></i>搜索
                    </button>
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="departmentDrug" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="row">
    <#--产品销量部门分布饼图-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="drugDepartmentForm">
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
                    <button type="button" class="btn btn-info btn-sm  ace-table-search" id="drugDepartmentSearch">
                        <i class="ace-icon fa fa-search"></i>搜索
                    </button>
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="drugDepartment" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#--产品销售额部门分布柱状图-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="departmentDrugYZForm">
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
                    <button type="button" class="btn btn-info btn-sm  ace-table-search" id="departmentDrugYZSearch">
                        <i class="ace-icon fa fa-search"></i>搜索
                    </button>
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="departmentDrugYZ" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="row">
    <#--产品区域销量-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="drugAreaForm">
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
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="drugArea" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#--产品区域销售额柱状图-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="drugAreaYZForm">
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
                    <button type="button" class="btn btn-info btn-sm  ace-table-search" id="drugAreaYZSearch">
                        <i class="ace-icon fa fa-search"></i>搜索
                    </button>
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="drugAreaYZ" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="row">
    <#--某医院药品销售额排行柱状图-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="hospitalDrugForm">
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
                    <button type="button" class="btn btn-info btn-sm  ace-table-search" id="hospitalDrugSearch">
                        <i class="ace-icon fa fa-search"></i>搜索
                    </button>
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="hospitalDrug" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#--某药品医院销售额排行柱状图-->
    <div class="col-xs-12 col-sm-6 widget-container-col ui-sortable" style="min-height: 310px;">
        <div class="widget-box ui-sortable-handle" style="opacity: 1;">
            <div class="widget-header">
                <form id="drugHospitalForm">
                    <label for="area">&nbsp;&nbsp;&nbsp;时间段：</label>
                    <input type="text" value="${startDateText}" name="order.startTime" class="date" style="width: 90px"/>一
                    <input type="text" value="${endDateText}" name="order.endTime" class="date" style="width: 90px"/>
                    &nbsp;&nbsp;
                    <select name="order.hospitalId" class="chosen-select" style="max-width: 308px">
                    <#list hospitals as hospital>
                        <option value="${hospital.id}">
                        ${hospital.name}</option>
                    </#list>
                    </select>
                    &nbsp;&nbsp;
                    <button type="button" class="btn btn-info btn-sm  ace-table-search" id="drugHospitalSearch">
                        <i class="ace-icon fa fa-search"></i>搜索
                    </button>
                </form>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main ace-scroll scroll-disabled" style="position: relative;">
                    <div class="scroll-track" style="display: none;">
                        <div class="scroll-bar" style="top: 0px;"></div>
                    </div>
                    <div class="scroll-content" style="">
                        <div id="drugHospital" style="height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
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


        <#--推广部产品架构-->
        $("#departmentDrugSearch").click(function () {
            ace.post('/html/report.departmentDrugReport.do?',$("#departmentDrugForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var departmentDrugChar = echarts.init(document.getElementById('departmentDrug'));
                    var option = {
                        title: {
                            text: data.data.deptName+'药品销售额分布图',
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
                    departmentDrugChar.setOption(option);
                }
            });
        });

        $("#departmentDrugSearch").trigger("click");


        <#--推广部产品架构-->
        $("#drugDepartmentSearch").click(function () {
            ace.post('/html/report.drugDepartmentReport.do?',$("#drugDepartmentForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var drugDepartmentChar = echarts.init(document.getElementById('drugDepartment'));
                    var option = {
                        title: {
                            text: data.data.drugName+'部门销量分布图',
                            subtext: '',
                            left: 'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "销量 <br/>{b} : {c} ({d}%)"
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
                    drugDepartmentChar.setOption(option);
                }
            });
        });

        $("#drugDepartmentSearch").trigger("click");



        <#--推广部产品架构 柱状图-->
        $("#departmentDrugYZSearch").click(function () {
            ace.post('/html/report.drugDepartmentReport.do?order.reportType=money&',$("#departmentDrugYZForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var departmentDrugYZChar = echarts.init(document.getElementById('departmentDrugYZ'));
                    var departmentDrugYZOption = {
                        title: {
                            text: data.data.drugName+'部门销售额柱状图'
                        },
                        tooltip: {},
                        legend: {
                            data:['销售额']
                        },
                        xAxis: {
                            data: data.data.names
                        },
                        yAxis: {},
                        series: [{
                            name: '销售额',
                            type: 'bar',
                            data: data.data.values
                        }]
                    };
                    departmentDrugYZChar.setOption(departmentDrugYZOption);
                }
            });
        });

        $("#departmentDrugYZSearch").trigger("click");




    <#--产品区域销量-->
        $("#drugAreaSearch").click(function () {
            ace.post('/html/report.drugAreaReport.do?',$("#drugAreaForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var drugAreaChar = echarts.init(document.getElementById('drugArea'));
                    var option = {
                        title: {
                            text: data.data.drugName+'区域销量分布图',
                            subtext: '',
                            left: 'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "销量 <br/>{b} : {c} ({d}%)"
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
                    drugAreaChar.setOption(option);
                }
            });
        });

        $("#drugAreaSearch").trigger("click");



    <#--产品区域销量柱状图-->
        $("#drugAreaYZSearch").click(function () {
            ace.post('/html/report.drugAreaReport.do?order.reportType=money&',$("#drugAreaYZForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var drugAreaYZChar = echarts.init(document.getElementById('drugAreaYZ'));
                    var drugAreaYZOption = {
                        title: {
                            text: data.data.drugName+'区域销售额柱状图'
                        },
                        tooltip: {},
                        legend: {
                            data:['销售额']
                        },
                        xAxis: {
                            data: data.data.names
                        },
                        yAxis: {},
                        series: [{
                            name: '销售额',
                            type: 'bar',
                            data: data.data.values
                        }]
                    };
                    drugAreaYZChar.setOption(drugAreaYZOption);
                }
            });
        });

        $("#drugAreaYZSearch").trigger("click");



        <#--某医院药品销售额排行柱状图-->
        $("#hospitalDrugSearch").click(function () {
            ace.post('/html/report.hospitalDrugReport.do?',$("#hospitalDrugForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var hospitalDrugChar = echarts.init(document.getElementById('hospitalDrug'));
                    var hospitalDrugOption = {
                        title: {
                            text: data.data.drugName+'医院销售额排名'
                        },
                        tooltip: {},
                        legend: {
                            data:['销售额']
                        },
                        xAxis: {
                            data: data.data.names
                        },
                        yAxis: {},
                        series: [{
                            name: '销售额',
                            type: 'bar',
                            data: data.data.values
                        }]
                    };
                    hospitalDrugChar.setOption(hospitalDrugOption);
                }
            });
        });

        $("#hospitalDrugSearch").trigger("click");



    <#--某药品医院销售额排行柱状图-->
        $("#drugHospitalSearch").click(function () {
            ace.post('/html/report.drugHospitalReport.do',$("#drugHospitalForm").serializeJson(), function (data) {
                if(data.status == "SUCCESS"){
                    var drugHospitalChar = echarts.init(document.getElementById('drugHospital'));
                    var drugHospitalOption = {
                        title: {
                            text: data.data.drugName+'药品销售额排名'
                        },
                        tooltip: {},
                        legend: {
                            data:['销售额']
                        },
                        xAxis: {
                            data: data.data.names
                        },
                        yAxis: {},
                        series: [{
                            name: '销售额',
                            type: 'bar',
                            data: data.data.values
                        }]
                    };
                    drugHospitalChar.setOption(drugHospitalOption);
                }
            });
        });

        $("#drugHospitalSearch").trigger("click");

    });

</script>
