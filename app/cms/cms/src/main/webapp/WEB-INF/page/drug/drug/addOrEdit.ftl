<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="drug.id" value="${drug.id!}">
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">药品名称</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="name" name="drug.name" value="${drug.name!}"
                               class="required" maxlength="50"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">药品别名</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="alias" name="drug.alias" value="${drug.alias!}"
                               class="" maxlength="5000"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">药品代码</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="code" name="drug.code" value="${drug.code!}"
                               class="" maxlength="5000"/>
					</span>
                    </div>
                </div>


            <#if drugSpecs??>
                <#list drugSpecs as dic>
                <div class="form-group specDiv <#if dic_index != 0>minusDiv</#if>"  <#if dic_index == 0>id="dicNameDiv"</#if>>
                    <label for="province" class="col-sm-3 control-label no-padding-right"><#if dic_index == 0>规格<span style="color: red">*</span></#if></label>
                    <div class="col-xs-12 col-sm-7">
					<span class="block input-icon input-icon-right">
						<input type="hidden" name="specId" value="${dic.id!}" class="specId"/>
                            <input style="width: 170px" placeholder="名称" type="text" name="specName" value="${dic.specName!}" class="specName" maxlength="50"/>
                            <input style="width: 126px" placeholder="系数（四位小数）"  type="text" name="coefficient" value="${dic.coefficient!}" class="coefficient" maxlength="10"/>
                        <br/>
                            <input style="width: 300px" placeholder="别名"  type="text" name="code" value="${dic.code!}" class="code" maxlength="5000"/>
                        <#if dic_index == 0>
                            <i class="addHtml ace-icon fa fa-plus bigger-120" style="top: 3px;margin-right: -10px;cursor: pointer"></i>
                        <#else >
                            <i class="minusHtml ace-icon fa fa-minus bigger-120" style="top: 3px;margin-right: -10px;cursor: pointer"></i>
                        </#if>
					</span>
                    </div>
                </div>
                </#list>
            <#else >
                <div class="form-group specDiv" id="dicNameDiv">
                    <label for="province" class="col-sm-3 control-label no-padding-right">规格<span style="color: red">*</span></label>
                    <div class="col-xs-12 col-sm-7">
                        <span class="block input-icon input-icon-right">
                            <input type="hidden" name="specId" value="" class="specId"/>
                            <input style="width: 170px" placeholder="名称" type="text" name="specName" value="" class="specName" maxlength="50"/>
                            <input style="width: 126px" placeholder="系数（四位小数）" type="text" name="coefficient" value="" class="coefficient" maxlength="10"/>
                            <br/>
                            <input style="width: 300px" placeholder="别名"  type="text" name="code" value="" class="code" maxlength="5000"/>
                            <i class="addHtml ace-icon fa fa-plus bigger-120" style="top: 3px;margin-right: -10px;cursor: pointer"></i>
                        </span>
                    </div>
                </div>

            </#if>






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
    <div class="form-group specDiv minusDiv">
        <label for="province" class="col-sm-3 control-label no-padding-right"></label>

        <div class="col-xs-12 col-sm-7">
					<span class="block input-icon input-icon-right">
						<input type="hidden" name="specId" value="" class="specId"/>
                        <input style="width: 170px" placeholder="名称" type="text" name="specName" value="" class="specName" maxlength="50"/>
                        <input style="width: 126px" placeholder="系数（四位小数）" type="text" name="coefficient" value="" class="coefficient" maxlength="10"/>
                        <br/>
                            <input style="width: 300px" placeholder="别名"  type="text" name="code" value="" class="code" maxlength="500"/>
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
        submitUrl: "${ctx}/html/drug.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
        },
        beforeSubmit:function(){
            $("#ace-form").find(".specDiv").each(function(index){
                var thisDiv = $(this);
                thisDiv.find(".specId").attr("name","drugSpecs["+index+"].id");
                thisDiv.find(".specName").attr("name","drugSpecs["+index+"].specName");
                thisDiv.find(".coefficient").attr("name","drugSpecs["+index+"].coefficient");
                thisDiv.find(".code").attr("name","drugSpecs["+index+"].code");
            });
            var notSubmit = false;
            $("#ace-form").find(".specDiv").each(function(index){
                var thisDiv = $(this);
                if (thisDiv.find(".specName").val() =='' || thisDiv.find(".coefficient").val()==''){
                    notSubmit = true;
                    return;
                }

            });

            if (notSubmit){
                alertErr("请填写规格名称和系数");
                return false;
            }

            return true;
        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init();
        $(".addHtml").click(function(){
            $("#dicNameDiv").after($("#dicNameHiddenHtml").html());
            resetNode();
        });

        $("#ace-form").delegate(".minusHtml","click",function(){
            $(this).parents(".minusDiv").remove();
            resetNode();
        });

        function resetNode() {
            $("#ace-form").find(".specDiv").each(function(index){
                var thisDiv = $(this);
                thisDiv.find(".specId").attr("name","drugSpecs["+index+"].id");
                thisDiv.find(".specName").attr("name","drugSpecs["+index+"].specName");
                thisDiv.find(".coefficient").attr("name","drugSpecs["+index+"].coefficient");
            });
        }
    });




</script>
