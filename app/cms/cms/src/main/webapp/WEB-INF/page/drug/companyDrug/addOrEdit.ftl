<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="companyDrug.id" value="${companyDrug.id!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">商业公司</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="companyDrug.companyId" class="required chosen-select1" style="max-width: 208px">
                            <option value="">请选择商业公司</option>
                        <#list companyIdNameMap?keys as key>
                            <option value="${key}" <#if companyDrug.companyId?? && key==companyDrug.companyId>selected="selected" </#if>>${companyIdNameMap[key]}</option>
                        </#list>
                        </select>
					</span>
                    </div>
                </div>

            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">品规名称</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">

                        <select name="companyDrug.specDrugName" class="required chosen-select1" >
                            <option value="">请选择品规</option>
                        <#list drugSpecIdsNamesMap?keys as key>
                            <option value="${drugSpecIdsNamesMap[key]}" <#if companyDrug.specDrugName?? && drugSpecIdsNamesMap[key]==companyDrug.specDrugName!>selected="selected" </#if>>
                            ${drugSpecIdsNamesMap[key]}</option>
                        </#list>
                        </select>

					</span>
                </div>
            </div>
               
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">开始时间</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="companyDrug.startTime" value="${companyDrug.startTimeText!}"
                               class="required date" />
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">结束时间</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="companyDrug.endTime" value="${companyDrug.endTimeText!}"
                               class="required date" />
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">中标价</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="companyDrug.biddingPrice" value="${companyDrug.biddingPrice!}"
                               class="required"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">扣率(1-100)</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="companyDrug.deductionRate" value="${companyDrug.deductionRate!}" placeholder="请填写1-100的数字"
                               class="required" />
					</span>
                    </div>
                </div>
                <#--<div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">开票价</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="companyDrug.billingPrice" value="${companyDrug.billingPrice!}"
                               class="required"/>
					</span>
                    </div>
                </div>-->




            <div class="clearfix form-actions" style="display: none">
                <div class="col-md-offset-3 col-md-9">

                    <button class="btn btn-info ace-form-submit" type="button" id="saveBtn">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>

                    &nbsp; &nbsp; &nbsp;
                    <button class="btn zc-form-cancel " type="button" onclick="history.go(-1);">
                        <i class="ace-icon fa fa-times bigger-110"></i>
                        取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var scripts = [null];
    var option = {
        appCode: "cms",
        corpCode: "",
        submitUrl: "${ctx}/html/companyDrug.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init();
        $('.date').datepicker({
        });
        $('.chosen-select1').chosen({
            allow_single_deselect: true,
            search_contains: true,
            width: "100%",
            no_results_input: '<ul class="chosen-results"><li class="active-result" data-option-array-index="1"><span></span></li></ul>'
        });
    });

</script>
