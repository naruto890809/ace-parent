<style>
    .errorMsg {
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="hospital.id" value="${hospital.id!}">

                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院名称</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="hospital.name" value="${hospital.name!}"
                               class="required" maxlength="50"/>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院别名</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <textarea  name="hospital.alias"
                                   class="" maxlength="1000">${hospital.alias!}</textarea>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院等级</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="hospital.level" class="required chosen-select1">
                            <option value="">请选择医院等级</option>
                        <#list levels as level>
                            <option value="${level.id}" <#if hospital.level?? && hospital.level == level.id>selected="selected"</#if>>${level.name}</option>
                        </#list>
                        </select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">医院性质</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						 <select name="hospital.type" class="required chosen-select1">
                             <option value="">请选择医院性质</option>
                         <#list types as type>
                             <option value="${type.id}" <#if hospital.type?? && hospital.type == type.id>selected="selected"</#if>>${type.name}</option>
                         </#list>
                         </select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">部门</label>
                    <div class="col-xs-12 col-sm-5">
                        <span class="block input-icon input-icon-right">
                            <select name="hospital.departmentId" class="chosen-select1" style="max-width: 208px">
                                <option value="">请选择部门</option>
                            <#list departments as department>
                                <option value="${department.id}" <#if hospital.departmentId?? && hospital.departmentId == department.id>selected="selected"</#if>>${department.name}</option>
                            </#list>
                            </select>
                        </span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">省</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="hospital.province" ></select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">市</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                        <select name="hospital.city" ></select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">区</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
                         <select name="hospital.area" class="required chosen-select1" style="max-width: 208px">
                             <option value="">请选择区</option>
                         <#list areas as area>
                             <option value="${area}" <#if hospital.area?? && hospital.area == area>selected="selected"</#if>>${area}</option>
                         </#list>
                         </select>
					</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="province" class="col-sm-3 control-label no-padding-right">详细地址</label>
                    <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text"  name="hospital.address" value="${hospital.address!}"
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
        submitUrl: "${ctx}/html/hospital.addOrEdit.do",
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


        new PCAS("hospital.province","hospital.city","${hospital.province!}","${hospital.city!}");


    });

</script>
