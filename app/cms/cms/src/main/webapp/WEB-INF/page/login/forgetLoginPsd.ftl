<div class="row" style="overflow: hidden;margin-left: auto;margin-right: auto;" >
    <div class="row">
        <div class="col-xs-12">
            <div class="col-md-offset-1">
                <form role="form" id="updateRechargePsdForm">
                    <div class="widget-body">
                        <div class="col-xs-12">
                            <label class="control-label no-padding-right">原始密码&nbsp;&nbsp;</label>
                            <input type="password" name="preRechargePsd" id="preRechargePsd" placeholder="原始充值密码"/>
                        </div>
                    </div>
                    <br/>
                    <br/>
                    <div class="widget-body">
                        <div class="col-xs-12">
                            <label class="control-label no-padding-right">新的密码&nbsp;&nbsp;</label>
                            <input type="password" name="rechargePsd" id="rechargePsdInput" placeholder="新密码" maxlength="64"/>
                        </div>
                    </div>
                    <br/>
                    <br/>

                    <div class="widget-body">
                        <div class="col-xs-12">
                            <label class="control-label no-padding-right">重新输入&nbsp;&nbsp;</label>
                            <input type="password" name="rechargePsd" id="rechargePsdAgain" placeholder="请再输入一次新密码"/>
                        </div>
                    </div>

                    <br/>
                    <br/>
                    <br/>
                    <div class="widget-body">
                        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info ace-form-submit" type="button" id="saveUpdateRechargeBtn">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            保存
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn zc-form-cancel " type="button" id="cancelUpdateRechargeBtn">
                            <i class="ace-icon fa fa-times bigger-110"></i>
                            取消
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $(function (){
        $("#saveUpdateRechargeBtn").click(function(){
            var preRechargePsd = $("#preRechargePsd").val();
            if(preRechargePsd == "" || preRechargePsd == null){
                alertErr("初始密码不能为空");
                return;
            }

            var rechargePsdInput = $("#rechargePsdInput").val();
            if(rechargePsdInput == "" || rechargePsdInput == null){
                alertErr("新密码不能为空");
                return;
            }

            var rechargePsdAgain = $("#rechargePsdAgain").val();
            if(rechargePsdAgain == "" || rechargePsdAgain == null){
                alertErr("第二次输入的新密码不能为空");
                return;
            }

            if(rechargePsdAgain != rechargePsdInput){
                alertErr("第一次输入的新密码与第二次输入的不相同");
                return;
            }

            var param = {
                "preRechargePsd":preRechargePsd,
                "newRechargePsd":rechargePsdInput,
                "rechargePsdAgain":rechargePsdAgain
            };
            ace.post('${ctx!}/html/account.updateRechargePsd.do',param, function (data) {
                if (data.status == "ERROR") {
                    alertErr(data.message);
                }else if(data.status == "SUCCESS"){
                    $("#divDialog").dialog("destroy");
                    $("#divDialog").html("").remove();
                    alertSuc("操作成功");
                }
            });
        });

        $("#cancelUpdateRechargeBtn").click(function(){
            $("#divDialog").dialog("destroy");
            $("#divDialog").html("").remove();
        });
    });

</script>