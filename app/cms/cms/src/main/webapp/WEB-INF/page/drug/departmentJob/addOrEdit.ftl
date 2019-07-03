<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="departmentJob.id" value="${departmentJob.id!}">
            <input type="hidden" name="departmentJob.departmentId" value="${departmentId!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">药品名称</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<select name="departmentJob.drugId" class="required chosen-select" >
                            <option value="">请选择药品</option>
                        <#list drugs  as drug>
                            <option value="${drug.id}" <#if drug.id==departmentJob.drugId!>selected="selected" </#if>>
                            ${drug.name}</option>
                        </#list>
                        </select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">年度</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <input type="text" name="departmentJob.year" value="${departmentJob.year!}" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="4" class="required date" placeholder="请填写年份"/>

					</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">任务量</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <input type="text" name="departmentJob.jobAmount" value="${departmentJob.jobAmount!}" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="9" class="required" placeholder="请填写正整数"/>

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
        submitUrl: "${ctx}/html/departmentJob.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init();
        /*$('.date').datepicker({
            format:'yyyy'
        });*/
    });

</script>
