<title>用户管理</title>

<div class="page-header">

<#--
<@check module="account" privilege="import">
    <button type="button" class="btn btn-info" id="importAccountBtn">
        <i class="ace-icon fa fa-cloud-upload"></i> 导入
    </button>
</@check>

<@check module="account" privilege="download">
    <button type="button" class="btn btn-info" id="exportAccountBtn">
        <i class="ace-icon fa fa-cloud-download"></i> 导出
    </button>
</@check>-->

<@check module="account" privilege="add">
    <button type="button" class="btn btn-primary" onclick="ace.util.jumpTo('account.addIndex.do')">
        <i class="ace-icon fa fa-plus"></i> 添加
    </button>
</@check>


<@check module="account" privilege="resetaccountpsd">
    <button type="button" class="btn btn-success" id="resetAccountPsdBtn">
        <i class="ace-icon fa fa-undo"></i> 重置密码
    </button>
</@check>

<@check module="account" privilege="freeze&activate">
    <button type="button" class="btn btn-warning" id="freezeMember">
        <i class="ace-icon fa fa-lock"></i> 冻结
    </button>

    <button type="button" class="btn btn-danger" id="activateMember">
        <i class="ace-icon fa fa-unlock-alt"></i> 激活
    </button>
</@check>
</div>
<!-- /.page-header -->

<form id="submitForm"></form>
<div class="row">
    <div class="col-xs-12">
        <div class="dataTables_wrapper form-inline ">
            <div class="row">

                <div class="row">
                    <form id="extend-form" class="form-inline">
                        <div class="form-group">
                            <label>角色：</label>
                            <select name="account.employeeCode" id="roleCode"  class="form-control">
                                <#list manageRoles as role>
                                    <option value="${role.roleId}">${role.roleName}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="accountName">&nbsp;&nbsp;&nbsp;用户名：</label>
                            <input type="text" class="input-medium" name="account.accountName" />
                        </div>

                        <div class="form-group">
                            <label>&nbsp;&nbsp;&nbsp;状态：</label>
                            <select name="account.accountStatus" class="form-control">
                                <option value="">全部</option>
                                <option value="ACTIVATED">激活</option>
                                <option value="FROZEN">冻结</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>&nbsp;&nbsp;&nbsp;手机号：</label>
                            <input type="text" class="input-medium" name="account.phone" />
                        </div>

                        <div class="form-group">&nbsp;&nbsp;&nbsp;

                            <@check module="account" privilege="view">
                            <button type="button" class="btn btn-info btn-sm ace-table-search" id="memberSearchBtn">
                                <i class="ace-icon fa fa-search"></i>搜索
                            </button>
                            </@check>
                        </div>
                    </form>
                </div>
            </div>
            <table id="ace-table" class="table table-striped table-bordered table-hover  dataTable">
                <thead>
                <tr>
                    <th class="center ace-tr-sm">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace ace-check-all"/>
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <th>序号</th>

                    <th>用户名</th>
                    <th>部门</th>
                    <th>角色</th>
                    <th>状态</th>
                    <th>手机号</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <table class="ace-tr-temp">
                <tbody>
                <tr data-id="%{accountId}">
                    <td class="center">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace ace-check-item"/>
                            <span class="lbl"></span>
                        </label>
                    </td>
                    <td>%{index}</td>
                    <td>%{accountName}</td>
                    <td>%{email}</td>
                    <td>%{roleName}</td>
                    <td>%{statusText}</td>
                    <td>%{phone}</td>
                    <td>
                        <div class="btn-group">
                            <span title="编辑">
                                <@check module="account" privilege="update">
                                    <button class="btn btn-xs btn-info ace-tr-edit">
                                        <i class="ace-icon fa fa-pencil"> 编辑</i>
                                    </button>
                                </@check>
                            </span>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>


<script type="text/javascript">
    var option = {
        listUrl: "${ctx}/html/account.search.do",
        updateUrl: function (json, $tr) {
            return 'account.edit.do?accountId=' + json.accountId + "&subCorpCode=" + json.corpCode;
        },
        searchUrl: "${ctx}/html/account.search.do"
    };
    var scripts = [];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        var table = new ace(option).table().init();

        //冻结  激活
        $("#freezeMember , #activateMember  ").click(function(){
            var checkedIds = table.getCheckedIds();
            if (checkedIds.length > 0) {
                var status = $(this).attr("id");
                myConfirm("确定此操作？",function(){
                    $('#submitForm').html("");
                    var param = "accountIds=";
                    var accountIds = "";
                    $.each(checkedIds, function (index, value) {
                        accountIds+=value+",";
                    });

                    param+=accountIds;
                    if(status === "freezeMember"){
                        param = param + "&accountStatus=FROZEN";
                    }else if (status === "activateMember"){
                        param = param + "&accountStatus=ACTIVATED";
                    }

                    var data = table.getChecked();
                    var subCorpCode = data[0].corpCode;
                    if(typeof(subCorpCode) != "undefined"){
                        param = param + "&subCorpCode="+subCorpCode;
                    }

                    ace.post('${ctx!}/html/account.updateStatus.do?'+param, function (data) {
                        if (data.status == "ERROR") {
                            alertErr("哇哦，操作失败咯！");
                        }else if(data.status == "WARNING"){
                            alertErr(data.message);
                        }else if(data.status == "SUCCESS"){
                            alertSuc("操作成功！")
                            table.refresh();
                        }
                    });
                });
            } else {
                alertErr("请选择操作的列！");
            }
        });

        //重置密码
        $('#resetAccountPsdBtn').click(function () {
            var checkedIds = table.getCheckedIds();
            if (checkedIds.length > 0) {
                myConfirm("确认重置密码？重置后的密码为6个0",function(){
                    $('#submitForm').html("");
                    var param = "accountIds=";
                    var accountIds = "";
                    $.each(checkedIds, function (index, value) {
                        accountIds+=value+",";
                    });
                    param+=accountIds;

                    var data = table.getChecked();
                    var subCorpCode = data[0].corpCode;
                    if(typeof(subCorpCode) != "undefined"){
                        param = param + "&subCorpCode="+subCorpCode;
                    }

                    ace.post('${ctx!}/html/account.resetAccountPsd.do?'+param, function (data) {
                        if (data.status == "ERROR") {
                            alertErr("哇哦，操作失败咯！");
                        }else if(data.status == "WARNING"){
                            alertErr(data.message);
                        }else if(data.status == "SUCCESS"){
                            alertSuc("操作成功！")
                            table.refresh();
                        }
                    });
                });
            } else {
                alertErr("请选择操作的列！");
            }
        });

        //导出
        $('#exportAccountBtn').click(function () {
            var json = $("#extend-form").serialize();
            window.open('${ctx}/html/account.exportAccount.do?'+json);
        });

        //导入
        $( "#importAccountBtn" ).on('click', function(e) {
            e.preventDefault();
            $( "#importDialogErrorInfo" ).addClass("hide");
            $( "#up_queue" ).addClass("hide");
            $( "#importDialogInfo" ).removeClass("hide");
            $(".upload-btn-adj input").removeAttr("disabled");
            $( "#importDialog" ).removeClass('hide').dialog({
                modal: true,
                title: "人员模版导入",
                width:400,
                open:function(){
                    $(".ui-dialog-titlebar-close .ui-button-text").remove();
                },
                close:function(){
                    $("#up_queue").find(".uploadifive-queue-item").find(".close").trigger("click");
                }
            });
        });

        $('#upload-btn').uploadifive({
            //是否自动上传
            auto:true,
            //超时时间
            successTimeou:99999,
            //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
            fileObjName:'excel',
            //上传处理程序
            uploadScript:"${ctx}/html/importAccount.do?corpCode=${corpCode!}&userId=${userId!}",
            //在浏览窗口底部的文件类型下拉菜单中显示的文本
            fileTypeDesc:'支持的格式：',
            //允许上传的文件后缀
            fileType     : '.xls',
            //上传文件的大小限制
            fileSizeLimit:'1MB',
            buttonText : '<span id="upbtn">上传</span>',//浏览按钮
            //上传数量
            queueSizeLimit : 1,
            width:370,
            //浏览按钮的高度
            height:40,
            queueID:'up_queue',
            buttonClass:'upload-btn-adj',
            multi:false,
            //选择上传文件后调用
            onUpload : function(file) {
                $(".upload-btn-adj input").attr("disabled","disabled");
            },
            //上传到服务器，服务器返回相应信息到data里
            onUploadComplete:function(file, data, response){
                if(data != null && data.contains("ERROR")){
                    $( "#importDialogInfo" ).addClass("hide");

                    $( "#importDialogErrorInfo" ).removeClass("hide");
                    $( "#up_queue" ).removeClass("hide");

                    var result =  data.split(",");
                    var url = "${ctx}/html/account.downloadImportAccountErrorInfo.do?excelName="+result[1];
                    $( "#importDialogErrorInfo" ).html('<p style="text-align: center;"><br/>人员导入失败。<a target="_blank" href="'+url+'">错误原因</a><br/></p>');
                }else {
                    $( "#importDialog" ).dialog('close');
                    alertSuc("导入成功！");
                    table.refresh();
                }

            },
            onError:function(errorType,file){
                $("#upbtn").text("上传");
                switch(errorType) {
                    case "QUEUE_LIMIT_EXCEEDED":
                        alert("最多只能同时上传"+$('#uploadify').data('uploadifive').settings.queueSizeLimit+"个文件");
                        break;
                    case "FILE_SIZE_LIMIT_EXCEEDED":
                        alert("文件 ["+file.name+"] 大小超出限制的1M");
                        break;
                    case "FORBIDDEN_FILE_TYPE":
                        alert("文件 ["+file.name+"] 不是excel文件！");
                        break;
                    case "UPLOAD_LIMIT_EXCEEDED":
                        alert("最多只能上传"+$('#uploadify').data('uploadifive').settings.queueSizeLimit+"个文件");
                        break;
                }
            }

        });

        $('#date-timepicker1 , #date-timepicker2').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            useCurrent:false,
            minuteStepping:5,
            minDate: new Date()
        }).next().on(ace.click_event, function(){
            $(this).prev().focus();
        });


        $('#ace-table').delegate('.account-work-time', 'click', function (e)  {
            e.preventDefault();
            var accountId = $(this).parent().parent().parent().parent().attr("data-id");
            ace.util.jumpTo('account.editWorkTimeIndex.do?accountId='+accountId + '&subCorpCode='+$(this).attr("corp-code")+'&roleCode='+$(this).attr("role-code"));
        });

        $('#ace-table').delegate('.account-project', 'click', function (e)  {
            e.preventDefault();
            var accountId = $(this).parent().parent().parent().parent().attr("data-id");
            ace.util.jumpTo('account.setProjectIndex.do?accountId='+accountId + "&subCorpCode="+$(this).attr("corp-code"));
        });

        $('#ace-table').delegate('.account-lock', 'click', function (e)  {
            e.preventDefault();
            var accountId = $(this).parent().parent().parent().parent().attr("data-id");
            ace.util.jumpTo('account.setLockIndex.do?accountId='+accountId + "&subCorpCode="+$(this).attr("corp-code"));
        });
    });


</script>


