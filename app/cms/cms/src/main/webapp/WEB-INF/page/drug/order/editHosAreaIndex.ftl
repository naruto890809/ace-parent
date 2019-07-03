<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="order.id" value="${id!}">

                <div class="form-group" style="height: 200px;margin-top: 20px">
                    <label for="province" class="col-sm-3 control-label no-padding-right">行政区域</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <input type="text" name="order.hospitalArea">
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
        submitUrl: "${ctx}/html/order.editHosArea.do",
        success:function(){
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
            alertSuc("操作成功");
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
