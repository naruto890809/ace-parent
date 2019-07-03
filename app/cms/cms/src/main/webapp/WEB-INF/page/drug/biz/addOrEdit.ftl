<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="biz.id" value="${biz.id!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">业务员</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="biz.salesmanId" class="chosen-select1 required" >
                            <option value="">请选择业务员</option>
                        <#list salesmanIdNameMap?keys as key>
                            <option value="${key}" <#if key==biz.salesmanId>selected="selected" </#if>>${salesmanIdNameMap[key]}</option>
                        </#list>
                        </select>
					</span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="biz.hospitalId" class="required chosen-select1 " style="max-width: 208px">
                            <option value="">请选择医院</option>
                        <#list hospitals as key>
                            <option  <#if key.id==biz.hospitalId>selected="selected" </#if> value="${key.id}">${key.name}</option>
                        </#list>
                        </select>

					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">品规名称</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">

                        <select name="biz.specDrugName" class="required chosen-select1 " >
                            <option value="">请选择品规</option>
                        <#list drugSpecIdsNamesMap?keys as key>
                            <option value="${drugSpecIdsNamesMap[key]}" <#if drugSpecIdsNamesMap[key]==biz.specDrugName!>selected="selected" </#if>>
                            ${drugSpecIdsNamesMap[key]}</option>
                        </#list>
                        </select>

					</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">销售时间段</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="biz.saleDate" value="${biz.saleDateText!}" class="required date" />
					</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">类型</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<select name="biz.type">
                            <option value="申请" <#if biz.type=="申请">selected="selected" </#if>>申请</option>
                            <option value="交接" <#if biz.type=="交接">selected="selected" </#if>>交接</option>
						</select>
					</span>
                    </div>
                </div>

            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">考核量</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="biz.amount" value="${biz.amount!}"  />
					</span>
                </div>
            </div>


            <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">跟踪开始时间</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="biz.traceStartDate" value="${biz.traceStartDateText!}"class=" date" />
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">跟踪结束时间</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="biz.traceEndDate" value="${biz.traceEndDateText!}"class=" date"/>
					</span>
                    </div>
                </div>
<#--                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">备注</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="biz.note" value="${biz.note!}"
                               class="" maxlength="100"/>
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
        submitUrl: "${ctx}/html/biz.addOrEdit.do",
        success:function(){
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
            alertSuc("操作成功");
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
