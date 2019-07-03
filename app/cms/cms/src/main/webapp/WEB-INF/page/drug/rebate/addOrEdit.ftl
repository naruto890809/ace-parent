<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="rebate.id" value="${rebate.id!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">商业公司</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="rebate.companyId" class="required chosen-select1"  style="max-width: 208px">
                            <option value="">请选择商业公司</option>
                        <#list companyIdNameMap?keys as key>
                            <option value="${key}" <#if rebate.companyId?? && key==rebate.companyId>selected="selected" </#if>>${companyIdNameMap[key]}</option>
                        </#list>
                        </select>
                        
					</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">末级商业公司</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="rebate.companyLast" class="chosen-select1"  style="max-width: 208px">
                            <option value="">请选择商业公司</option>
                        <#list companyIdNameMap?keys as key>
                            <option value="${companyIdNameMap[key]}" <#if rebate.companyLast?? && companyIdNameMap[key]==rebate.companyLast>selected="selected" </#if>>${companyIdNameMap[key]}</option>
                        </#list>
                        </select>

					</span>
                    </div>
                </div>


                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="rebate.hospitalId" class="required chosen-select1" style="max-width: 208px">
                            <option value="">请选择医院</option>
                        <#list hospitals as key>
                            <option <#if rebate.hospitalId?? && key.id==rebate.hospitalId>selected="selected" </#if> value="${key.id}">${key.name}</option>
                        </#list>
                        </select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">品规</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="rebate.specDrugName" class="required chosen-select1" >
                            <option value="">请选择品规</option>
                        <#list drugSpecIdsNamesMap?keys as key>
                            <option value="${drugSpecIdsNamesMap[key]}" <#if rebate.specDrugName ?? && drugSpecIdsNamesMap[key]==rebate.specDrugName!>selected="selected" </#if>>
                            ${drugSpecIdsNamesMap[key]}</option>
                        </#list>
                        </select>

					</span>
                    </div>
                </div>
                
                
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">议价主体</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        
                        <select name="rebate.priceTopic" class="chosen-select1 required">
                            <option value="">请选择议价主体</option>
                        <#list priceTopics as type>
                            <option value="${type.id}" <#if rebate.priceTopic?? && rebate.priceTopic == type.id>selected="selected"</#if>>${type.name}</option>
                        </#list>
                        </select>
                        
					</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">执行价</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="rebate.executePrice" value="${rebate.executePrice!}"
                               class="required" />
					</span>
                    </div>
                </div>
               <#-- <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">明返单价</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="rebate.brightPrice" value="${rebate.brightPrice!}"
                               />
					</span>
                    </div>
                </div>-->
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">明返执行日期</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="rebate.brightExecuteDate" value="${rebate.brightExecuteDateText!}"
                               class="date"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">暗返单价</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="rebate.darkPrice" value="${rebate.darkPrice!}"
                              />
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">暗返执行日期</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="rebate.darkExecuteDate" value="${rebate.darkExecuteDateText!}"
                               class="date"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">返利形式</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="rebate.rebateStyle" class="required">
                            <option value="">请选择返利形式</option>
                        <#list rebateStyles as type>
                            <option value="${type.id}" <#if rebate.rebateStyle?? && rebate.rebateStyle == type.id>selected="selected"</#if>>${type.name}</option>
                        </#list>
                        </select>
                        
					</span>
                    </div>
                </div>


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
        submitUrl: "${ctx}/html/rebate.addOrEdit.do",
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
