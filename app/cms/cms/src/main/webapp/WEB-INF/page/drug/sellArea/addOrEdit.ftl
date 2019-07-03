<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="sellArea.id" value="${sellArea.id!}">
            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">省</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="sellArea.province" class="required" ></select>
					</span>
                </div>
            </div>

            <div class="form-group">
                <label for="city" class="col-sm-3 control-label no-padding-right">市</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="sellArea.city" class="required" ></select>
					</span>
                </div>
            </div>

            <div class="form-group">
                <label for="area" class="col-sm-3 control-label no-padding-right">区</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="area" name="sellArea.area" value="${sellArea.area!}" class="required" maxlength="20"/>
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
    var scripts = ["${ctx!}/js/PCASClass.js"];
    var option = {
        appCode: "cms",
        corpCode: "${corpCode!}",
        submitUrl: "${ctx}/html/sellArea.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init()
        new PCAS("sellArea.province","sellArea.city","${sellArea.province!}","${sellArea.city!}");
    });

</script>
