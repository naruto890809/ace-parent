<style>
    .errorMsg{
        color: red;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal" role="form" id="ace-form">
            <input type="hidden" name="role.roleId" value="${role.roleId!}"/>

            <#--<div class="form-group">
                <label for="employeeCode" class="col-sm-3 control-label no-padding-right">角色编号</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="roleCode" name="role.roleCode" value="${role.roleCode!}" class="required" maxlength="50"/>
					</span>
                </div>
            </div>-->

            <div class="form-group">
                <label for="employeeCode" class="col-sm-3 control-label no-padding-right">角色名称</label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="roleName" name="role.roleName" value="${role.roleName!}" class="required" maxlength="50"/>
					</span>
                </div>
            </div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">


                    <button class="btn btn-info ace-form-submit" type="button">
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
        appCode:"cms",
        corpCode:"${corpCode!}",
        submitUrl: "role.editRole.do"
    };

    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        if($("#roleCode").val() =="president" || $("#roleCode").val() =="shop_manager" || $("#roleCode").val() =="beautician" || $("#roleCode").val() =="reception" ){
            $("#roleCode").attr("readonly","readonly");
        }
        var form = new ace(option).form().init()

    });

    $(document).ready(function(){
        　
    });

</script>
