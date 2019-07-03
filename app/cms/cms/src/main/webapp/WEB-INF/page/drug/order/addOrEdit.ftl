<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="order.id" value="${order.id!}">
            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">品规</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="order.drugSpecName" class="required chosen-select1" >
                            <option value="">请选择品规</option>
                        <#list drugSpecIdsNamesMap?keys as key>
                            <option value="${drugSpecIdsNamesMap[key]}" <#if order.drugSpecName ?? && drugSpecIdsNamesMap[key]==order.drugSpecName!>selected="selected" </#if>>
                            ${drugSpecIdsNamesMap[key]}</option>
                        </#list>
                        </select>
					</span>
                </div>
            </div>
                
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">批号</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="order.drugNum" value="${order.drugNum!}"
                               class="required" maxlength="50"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">数量</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="order.drugCnt" value="${order.drugCnt!}" maxlength="6"
                               class="required"/>
					</span>
                    </div>
                </div>
            

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院</label>
                    <div class="col-xs-12 col-sm-5">
                        <span class="block input-icon input-icon-right">
                            <select name="order.hospitalName" class="required chosen-select1" style="max-width: 208px">
                                <option value="">请选择医院</option>
                            <#list hospitals as key>
                                <option <#if order.hospitalName?? && key.name==order.hospitalName>selected="selected" </#if> value="${key.name}">${key.name}</option>
                            </#list>

                            </select>
                        </span>
                    </div>
                </div>

            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">医院地址</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="order.hospitalPreAddre" value="${order.hospitalPreAddre!}" maxlength="100"/>
					</span>
                </div>
            </div>


            <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">日期</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="order.orderDateText" value="${order.orderDateText!}"
                               class="required date"/>
					</span>
                    </div>
                </div>
            
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">单价</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="order.priceText" value="${order.priceText!}"
                               class="required"/>
					</span>
                    </div>
                </div>

            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">商业公司</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="order.companyName" class="required chosen-select1"  style="max-width: 208px">
                            <option value="">请选择商业公司</option>
                        <#list companyIdNameMap?keys as key>
                            <option value="${companyIdNameMap[key]}" <#if order.companyName?? && companyIdNameMap[key]==order.companyName>selected="selected" </#if>>${companyIdNameMap[key]}</option>
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
        submitUrl: "${ctx}/html/order.addOrEdit.do",
        success:function(data){
            console.log(data);
            if(data.data){
                myConfirm(data.data);
                return
            }
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
