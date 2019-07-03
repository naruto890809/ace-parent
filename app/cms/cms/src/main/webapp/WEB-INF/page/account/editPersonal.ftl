<style>
    .errorMsg{
        color: red;
    }
</style>
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form" id="accountAddForm">
            <input type="hidden" name="account.accountId" value="${account.accountId!}"/>

            <div class="form-group">
                <label for="accountName" class="col-sm-3 control-label no-padding-right">用户名 </label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="accountName" value="${account.accountName!}"
                               name="account.accountName" class="required" maxlength="50"
                               onblur='checkParam($("#accountName"), true, "accountName")'/>
                        <span class="errorMsg"></span>
					</span>
                </div>
            </div>


            <div class="form-group">
                <label for="loginPsd" class="col-sm-3 control-label no-padding-right">登录密码 </label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="password" id="loginPsd" value="${account.loginPsd!}"
                               name="account.loginPsd" class="required" maxlength="64" value="000000"
                               onblur='checkParam($("#loginPsd"), false, "loginPsd")'/><span>（默认密码为六个零）</span>
                        <span class="errorMsg"></span>

					</span>
                </div>
            </div>

            <div class="form-group">
                <label for="phone" class="col-sm-3 control-label no-padding-right">手机号码 </label>
                <div class="col-xs-12 col-sm-5">
					<span class="block input-icon input-icon-right">
						<input type="text" id="phone"  value="${account.phone!}"
                               name="account.phone" class="required" maxlength="20"
                               onblur='checkParam($("#phone"), true, "phone")'/>
                        <span class="errorMsg"></span>

					</span>
                </div>
            </div>


			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info ace-form-submit" type="button" id="saveAccountInfoBtn">
						<i class="ace-icon fa fa-check bigger-110"></i>
						保存
					</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	var scripts = ["${ctx!}/js/fuelux/fuelux.spinner.js"];
    var option = {
        appCode:"cms",
        corpCode:"${corpCode!}"
    };
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        var form = new ace(option).form().init();
        $("#accountAddForm  .required").each(function () {
            $(this).parents(".form-group").find("label").append($("<span style='color: red'>*</span>"));
        });
	});

    function checkParam(obj,checkunique,checkType){
        var val = obj.val();
        if(val == null || val == ""){
            obj.parents(".form-group").find(".errorMsg").html("必填项！");
            $("#saveAccountInfoBtn").attr("disabled","disabled");
            return false;
        }
        if(checkunique) {
            var url ="${ctx!}/html/account.checkUnique.do?checkType="+checkType+"&checkValue="+val+"&accountId=${account.accountId!}";
            var subCorpCode = $("#subCorpCode").val();
            if(typeof(subCorpCode) != "undefined" && subCorpCode != null){
                url = url +"&subCorpCode="+subCorpCode;
            }

            $.ajax({
                type : "post",
                url : url,
                async : false,
                success : function(data){
                    if (data.status == "ERROR") {
                        obj.parents(".form-group").find(".errorMsg").html("已存在，请重新填写！");
                        $("#saveAccountInfoBtn").attr("disabled","disabled");
                        return false;
                    }
                    $("#saveAccountInfoBtn").removeAttr("disabled");
                    return true;
                }
            });
        }else{
            $("#saveAccountInfoBtn").removeAttr("disabled");
            return true;
        }
    }

    $(function () {
        $("#accountAddForm  .required").keyup(function () {
            $(this).parents(".form-group").find(".errorMsg").html("");
        });


        $("#saveAccountInfoBtn").click(function(){
            var flag = false;
            $("#accountAddForm  .errorMsg").each(function () {
                var val = $(this).html();
                if(val != null && val != ""){
                    flag = true;
                }
            });

            $("#accountAddForm  .required").each(function () {
                var val = $(this).val();
                if(val === null || val === ""){
                    flag = true;
                }
            });

            if(flag){
                alertErr("请认真填写表单！");
                $("#saveAccountInfoBtn").attr("disabled","disabled");
                return;
            }

            var json = $("#accountAddForm").serializeJson();
            ace.post('${ctx!}/html/account.update.do',json, function (data) {
                if (data.status == "ERROR") {
                    alertErr("哇哦，操作失败咯！");
                }else if(data.status == "SUCCESS"){
                    alertSuc("操作成功");
                }
            });
        });
    });


</script>