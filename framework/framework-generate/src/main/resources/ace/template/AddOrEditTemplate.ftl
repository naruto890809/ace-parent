<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="${lowerClazzName}.approve" value="APPROVING">
            <input type="hidden" name="${lowerClazzName}.id" value="${'$'}{${lowerClazzName}.id!}">

            <#list beanDates as beanData>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">${beanData.columnComment!"实体注释为空"}</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="province" name="${lowerClazzName}.${beanData.proColumnName!}" value="${'$'}{${lowerClazzName}.${beanData.proColumnName!}!}"
                               class="${beanData.optionType!}" maxlength="${beanData.charmaxLength!}"/>
					</span>
                    </div>
                </div>
            </#list>




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
        corpCode: "${corpCode!}",
        submitUrl: "${r'${ctx}'}/html/${lowerClazzName}.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            setTimeout(function () {
                $("#divDialog").dialog("close");
                $("#divDialog").dialog("destroy");
                $("#searchBtn").click();
            }, 1000);

        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init()
    });

</script>
