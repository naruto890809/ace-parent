<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="code" value="${code!}">

            <div class="form-group">
                <label for="province" class="col-sm-3 control-label no-padding-right">字典类型</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<select class="required" disabled="disabled">
                            <option value="">请选择类型</option>
                            <option value="COMPANY_CHANNEL" <#if "COMPANY_CHANNEL" = code>selected="selected"</#if>>销售渠道</option>
                            <option value="HOSPITAL_LEVEL" <#if "HOSPITAL_LEVEL" = code>selected="selected"</#if>>医院等级</option>
                            <option value="HOSPITAL_TYPE" <#if "HOSPITAL_TYPE" = code>selected="selected"</#if>>医院性质</option>
                            <option value="REBATE_DIF_TYPE" <#if "REBATE_DIF_TYPE" = code>selected="selected"</#if>>补差性质</option>
                            <option value="REBATE_PRICE_TOPIC" <#if "REBATE_PRICE_TOPIC" = code>selected="selected"</#if>>议价主体</option>
                            <option value="REBATE_STYLE" <#if "REBATE_STYLE" = code>selected="selected"</#if>>返利形式</option>
                        </select>
					</span>
                </div>
            </div>

            <#list dics as dic>
                <div class="form-group <#if dic_index != 0>minusDiv</#if>"  <#if dic_index == 0>id="dicNameDiv"</#if>>
                    <label for="province" class="col-sm-3 control-label no-padding-right">名称</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="name" value="${dic.name!}"
                               class="required" maxlength="50"/>

                        <#if dic_index == 0>
                            <i class="addHtml ace-icon fa fa-plus bigger-120" style="top: 3px;margin-right: -10px;cursor: pointer"></i>
                        <#else >
                            <i class="minusHtml ace-icon fa fa-minus bigger-120" style="top: 3px;margin-right: -10px;cursor: pointer"></i>
                        </#if>
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

<div id="dicNameHiddenHtml" style="display: none">
    <div class="form-group minusDiv">
        <label for="province" class="col-sm-3 control-label no-padding-right">名称</label>

        <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="name" value=""
                               class="required" maxlength="50"/>
                        <i class="minusHtml ace-icon fa fa-minus bigger-120" style="top: 3px;margin-right: -10px;cursor: pointer"></i>
					</span>
        </div>
    </div>

</div>

<script type="text/javascript">
    var scripts = [null];
    var option = {
        appCode: "cms",
        corpCode: "",
        submitUrl: "${ctx}/html/dic.addOrEdit.do",
        success: function () {
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init();

        $(".addHtml").click(function(){
            $("#dicNameDiv").after($("#dicNameHiddenHtml").html());
        });

        $("#ace-form").delegate(".minusHtml","click",function(){
            $(this).parents(".minusDiv").remove();
        });
    });

</script>
