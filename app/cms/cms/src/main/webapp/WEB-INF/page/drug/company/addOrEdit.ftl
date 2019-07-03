<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="company.id" value="${company.id!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">客户名称</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="company.name" value="${company.name!}"
                               class="required" maxlength="50"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">客户代码</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="company.code" value="${company.code!}"
                               class="" maxlength="50"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">别名</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <textarea  name="company.alias"
                                   class="" maxlength="200">${company.alias!}</textarea>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">销售渠道</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                         <select name="company.channel" class="required">
                             <option value="">请选择销售渠道</option>
                         <#list channels as channel>
                             <option value="${channel.id}" <#if company.channel?? && company.channel == channel.id>selected="selected"</#if>>${channel.name}</option>
                         </#list>
                         </select>

					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">所在省</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="company.sellAreaName" class="required" ></select>
					</span>
                    </div>
                </div>
            <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">所在市</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="company.sellAreaId" class="required" ></select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">详细地址</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="company.address" value="${company.address!}"
                               class="" maxlength="100"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">联系人</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="company.person" value="${company.person!}"
                               class="" maxlength="20"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">联系方式</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" name="company.contacts" value="${company.contacts!}"
                               class="" maxlength="100"/>
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
        corpCode: "",
        submitUrl: "${ctx}/html/company.addOrEdit.do",
        success:function(){
            alertSuc("操作成功");
            $("#divDialog").dialog("close");
            $("#divDialog").dialog("destroy");
            $("#searchBtn").click();
        }
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var form = new ace(option).form().init();
        new PCAS("company.sellAreaName","company.sellAreaId","${company.sellAreaName!}","${company.sellAreaId!}");
    });

</script>
