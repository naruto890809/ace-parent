<!DOCTYPE html>
<html lang="zh">

<head>
    <title>中美华东销售管理系统</title>
    <link rel="shortcut icon" href="${ctx!}/img/ace_logo.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="User login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <script type="text/javascript" src="${ctx!}/js/jquery-2.1.3.min.js"></script>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${ctx!}/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctx!}/css/font-awesome.css" />

    <!-- text fonts -->
    <link rel="stylesheet" href="${ctx!}/css/ace-fonts.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx!}/css/ace.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx!}/css/ace-part2.css" />
    <![endif]-->
    <link rel="stylesheet" href="${ctx!}/css/ace-rtl.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx!}/css/ace-ie.css" />
    <![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="${ctx!}/js/html5shiv.js"></script>
    <script src="${ctx!}/js/respond.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/css/jquery-ui.css" />
    <script src="${ctx}/js/jquery-ui.js"></script>
    <script src="${ctx}/js/jquery.ui.touch-punch.js"></script>
</head>
<style>
    .login-layout {
        background: url('${ctx!}/img/backgrounds/bg_6.jpg');
        background-repeat: no-repeat;
        background-size: cover;
    }
    #id-company-text{
        position: absolute;
        bottom: 0px;
        text-align: center;
        width: 100%;
        background: #444;
        background: rgba(0,0,0,0.3);
        padding: 10px;
        margin-bottom: 0px;
        font-size: 14px;
        font-weight: 600;
    }
</style>
<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <#--<div class="center">
                        <h1>
                            <img src="${ctx}/img/ace_logo.ico.png" style="width: 100%;">
                        </h1>
                    </div>-->

                    <div class="space-6"></div>

                    <div class="position-relative" style="padding-top: 25%">
                        <div id="login-box" class="login-box visible widget-box no-border" style="box-shadow: 0px 10px 60px rgba(0,0,0,0.4);padding:0px;">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        请输入您的登录信息(管理员)
                                    </h4>

                                    <div class="space-6"></div>

                                    <form>
                                        <fieldset>

                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="手机号" name="account.phone" id="phone" />
															<i class="ace-icon fa fa-user"></i>
														</span>
                                            </label>

                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" name="account.loginPsd" id="loginPsd" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                            </label>

                                            <#--<label class="block clearfix">
														<span class="block input-icon input-icon-right">
                                                            <input type="text" class="form-control" name="account.securityCode" id="securityCode" placeholder="验证码"
                                                                   style="width: 57%;float: left">&nbsp;
                                                            <img src="login.genSecurityCode.do" id="imgSecurityCode" border="0" onclick="newSecurityCode()"
                                                                 style="cursor:pointer;"/>
														</span>
                                            </label>-->

                                            <div class="space"></div>


                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" class="ace" id="rememberPsd">
                                                    <span class="lbl"> 记住密码</span>
                                                </label>
                                                <#--<span class="bigger-110" style="color: #2679b5;cursor: pointer" id="forgetPsdSpan">忘记密码？</span>-->

                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="login">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">登&nbsp;录</span>
                                                </button>
                                            </div>

                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>

                                    <div class="social-or-login center">
                                        <span class="bigger-110" id="errorMsg"></span>
                                    </div>

                                    <div class="space-6"></div>

                                </div><!-- /.widget-main -->
                            </div><!-- /.widget-body -->
                        </div><!-- /.login-box -->



                    </div><!-- /.position-relative -->

                </div>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.main-content -->
</div><!-- /.main-container -->
<h4 class="white" id="id-company-text">Copyright &copy;华东中美 | 2018 </h4>

<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function($) {
        $(document).on('click', '.toolbar a[data-target]', function(e) {
            e.preventDefault();
            var target = $(this).data('target');
            $('.widget-box.visible').removeClass('visible');//hide others
            $(target).addClass('visible');//show target
        });
    });

    <#--$(".login-layout").css("background-image","url(${ctx!}/img/backgrounds/bg_"+parseInt(1+6*Math.random())+".jpg)");-->
    $(".login-layout").css("background-image","url(${ctx!}/img/backgrounds/bg_"+parseInt(5+2*Math.random())+".jpg)");

    function newSecurityCode() {
        document.getElementById("imgSecurityCode").src = "login.genSecurityCode.do?randm=" + new Date();
    }

    function checkLogin() {
        var phone = $.trim($("#phone").val());
        var loginPsd = $.trim($("#loginPsd").val());
        var securityCode = $.trim($("#securityCode").val());

        if (phone == '') {
            $('#errorMsg').html("手机号码不能为空！");
            return false;
        }

        if (loginPsd == '') {
            $('#errorMsg').html("密码不能为空！");
            return false;
        }

        /*if (securityCode == '') {
            $('#errorMsg').html("验证码不能为空！");
            return false;
        }*/

        return true;
    }

    $(function () {
        $('#phone').focus();

        var AceUserName =  CookieUtil.getCookie("AceUserName");
        if(AceUserName){
            $("#phone").val(AceUserName);
            $("#rememberPsd").attr("checked",'true');
        }
        var AcePsd =  CookieUtil.getCookie("AcePsd");
        if(AcePsd){
            $("#loginPsd").val(AcePsd);
        }
        $('#loginPsd,#phone,#securityCode ').change(function () {
            $("#errorMsg").html("");
        });

        $("#login").click(function () {
            if (!checkLogin()) {
                return;
            }
            var params = {
                "account.phone": $.trim($("#phone").val()),
                "account.securityCode": $.trim($("#securityCode").val()),
                "account.loginPsd": $("#loginPsd").val()
            };
            $.post("${ctx!}/html/login.login.do", params, function (data) {
                if (data.status == "ERROR") {
                    /*if(data.message === "SECURITY_CODE_ERROR"){
                        data.message = "验证码错误";
                        document.getElementById("imgSecurityCode").src = "login.genSecurityCode.do?randm=" + new Date();
                    }*/

                    $("#errorMsg").html(data.message);
                } else {
                    rememberPsd();
                    window.location.href = "${ctx!}/html/login.manageIndex.do#report.UI.do";
                }
            });
        });

        document.onkeydown = function (e) {
            var ev = document.all ? window.event : e;
            if (ev.keyCode == 13) {
                if (!checkLogin()) {
                    return;
                }
                var params = {
                    "account.phone": $.trim($("#phone").val()),
                    "account.securityCode": $.trim($("#securityCode").val()),
                    "account.loginPsd": $("#loginPsd").val()
                };
                $.post("${ctx!}/html/login.login.do", params, function (data) {
                    if (data.status == "ERROR") {
                       /* if(data.message === "SECURITY_CODE_ERROR"){
                            data.message = "验证码错误";
                            document.getElementById("imgSecurityCode").src = "login.genSecurityCode.do?randm=" + new Date();
                        }*/

                        $("#errorMsg").html(data.message);
                    } else {
                        rememberPsd();
                        window.location.href = "${ctx!}/html/login.manageIndex.do#report.UI.do";
                    }
                });
            }
        };

    });


    function rememberPsd(){
        if($("#rememberPsd").get(0).checked){
            CookieUtil.setCookie("AceUserName", $.trim($("#phone").val()));
            CookieUtil.setCookie("AcePsd", $("#loginPsd").val());
        }else{
            CookieUtil.delCookie("AceUserName");
            CookieUtil.delCookie("AcePsd");
        }
    }
    CookieUtil = {
        setCookie: function (name, value) {
            var Days = 30;
            var exp = new Date();
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        },
        getCookie: function (name) {
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        },
        delCookie: function (name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = CookieUtil.getCookie(name);
            if (cval != null)
                document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
        }
    };

</script>

</body>
</html>

