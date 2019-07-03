<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="salesman.id" value="${salesman.id!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">姓名</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="salesman.name" value="${salesman.name!}"
                               class="required" maxlength="20"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">部门</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="salesman.departmentId" class="chosen-select1 required" style="max-width: 208px">
                                <option value="">请选择部门</option>
                            <#list departments as department>
                                <option value="${department.id}" <#if salesman.departmentId?? && salesman.departmentId == department.id>selected="selected"</#if>>${department.name}</option>
                            </#list>
                        </select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">联系方式</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="salesman.contact" value="${salesman.contact!}"
                               class="" maxlength="50"/>
					</span>
                    </div>
                </div>

        <@check module="salesman" privilege="bank">
            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">银行账号</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="salesman.bankNo" value="${salesman.bankNo!}"
                               class="" maxlength="50"/>
					</span>
                </div>
            </div>

            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">开户行</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="salesman.bankName" value="${salesman.bankName!}"
                               class="" maxlength="50"/>
					</span>
                </div>
            </div>
        </@check>





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
        submitUrl: "${ctx}/html/salesman.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();

        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init();
        $('.chosen-select1').chosen({
            allow_single_deselect: true,
            search_contains: true,
            width: "100%",
            no_results_input: '<ul class="chosen-results"><li class="active-result" data-option-array-index="1"><span></span></li></ul>'
        });
    });

</script>
