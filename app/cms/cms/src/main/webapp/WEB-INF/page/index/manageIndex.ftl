<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>中美华东销售管理系统</title>
    <link rel="shortcut icon" href="${ctx!}/img/ace_logo.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <script type="text/javascript" src="${ctx}/js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ace.js"></script>
    <link rel="stylesheet" href="${ctx!}/css/cms.css">
    <link rel="stylesheet" href="${ctx!}/css/multiimage.css">

    <!--[if !IE]> -->
    <link rel="stylesheet" href="${ctx}/css/pace.css"/>

    <script data-pace-options='{ "ajax": true, "document": true, "eventLag": false, "elements": false }'
            src="${ctx}/js/pace.js"></script>

    <!-- <![endif]-->

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${ctx}/css/font-awesome.css"/>

    <!-- text fonts -->
    <link rel="stylesheet" href="${ctx}/css/ace-fonts.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx}/css/jquery-ui.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx}/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <#--<link rel="stylesheet" href="${ctx}/css/common.css"/>-->
    <link rel="stylesheet" href="${ctx}/css/datepicker.css" />
    <link rel="stylesheet" href="${ctx}/css/bootstrap-timepicker.css" />
    <link rel="stylesheet" href="${ctx}/css/daterangepicker.css" />
    <link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css" />


    <link rel="stylesheet" href="${ctx}/css/ztree/ztree.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx}/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${ctx}/css/ace-ie.css"/>
    <![endif]-->

    <!-- ace settings handler -->
    <script src="${ctx}/js/ace-extra.js"></script>

    <link rel="stylesheet" href="${ctx}/css/chosen.css"/>
    <script src="${ctx}/js/chosen.jquery.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="${ctx}/js/html5shiv.js"></script>
    <script src="${ctx}/js/respond.js"></script>
    <![endif]-->
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <div class="navbar-header pull-left">
            <a href="#report.UI.do">
                <img src="${ctx}/img/logo1.png" <#--style="height:45px;"-->>
            </a>
        </div>

        <!-- #section:basics/navbar.dropdown -->
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <div class="hide" id="mp3Message"></div>
            <ul class="nav ace-nav">
                <!-- #section:basics/navbar.user_menu -->
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <#--<img class="nav-user-photo" src="<#if account.faceUrl??&&account.faceUrl!="">${account.faceUrl!}<#else >${ctx}/img/user.jpg</#if>"/>-->
								<span class="user-info">
									<small>欢迎</small>
                                ${account.accountName!}
								</span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                        <li>
                            <a  href="#account.editPersonal.do?accountId=${account.accountId!}" id="accountInfo">
                                <i class="ace-icon fa fa-user"></i>
                                个人信息
                            </a>
                        </li>

                        <li>
                            <a href="${ctx!}/html/login.loginOut.do?accountId=${account.accountId!}" id="loginOut">
                                <i class="ace-icon fa fa-power-off"></i>
                                注销
                            </a>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>

    </div>
    <!-- /.navbar-container -->
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
<script type="text/javascript">
    try {
        ace.settings.check('main-container', 'fixed')
    } catch (e) {
    }
</script>

<!-- #section:basics/sidebar -->
<div id="sidebar" class="sidebar                  responsive">
<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'fixed')
    } catch (e) {
    }
</script>

<#--<div class="sidebar-shortcuts" id="sidebar-shortcuts">
    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
        <a class="btn btn-success" href="#report.UI.do">
            <i class="ace-icon fa fa-bar-chart-o"></i>
        </a>

        <a class="btn btn-info" href="#companyDrug.index.do">
            <i class="ace-icon fa fa-coffee"></i>
        </a>

        <!-- #section:basics/sidebar.layout.shortcuts &ndash;&gt;
        <a class="btn btn-warning" href="#salesman.index.do">
            <i class="ace-icon fa fa-users"></i>
        </a>
        <a class="btn btn-danger" href="#order.index.do">
            <i class="ace-icon fa fa-bell"></i>
        </a>

        <!-- /section:basics/sidebar.layout.shortcuts &ndash;&gt;
    </div>

    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
        <span class="btn btn-success"></span>

        <span class="btn btn-info"></span>

        <span class="btn btn-warning"></span>

        <span class="btn btn-danger"></span>
    </div>
</div>-->
<!-- /.sidebar-shortcuts -->
<#macro menu menuList>

    <#if menuList ??>
        <#list menuList as item>
            <li class="">
            <a <#if item.url??> data-url="${item.url!}" href="#${item.url!}"<#else>href="#"</#if>  <#if item.subMenuList?? && item.subMenuList?size gt 0> class="dropdown-toggle" </#if>  >
                <i class="${item.icon!}"></i>
							<span class="menu-text">
								${item.title}
							</span>
            </a>
            <b class="arrow fa fa-angle-down"></b>

                <#if item.subMenuList?? && item.subMenuList?size gt 0>
                    <ul class="submenu">
                        <@menu menuList=item.subMenuList  />
                    </ul>
                </#if>
            </li>
        </#list>
    </#if>
</#macro>
<ul class="nav nav-list">

<#if menuTree?? && (menuTree?size>0)>
<@menu menuList=menuTree />
<#else>
您没有权限请联系管理员！！！
</#if>



<ul>



<!-- #section:basics/sidebar.layout.minimize -->
<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
       data-icon2="ace-icon fa fa-angle-double-right"></i>
</div>

<!-- /section:basics/sidebar.layout.minimize -->
<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'collapsed')
    } catch (e) {
    }
</script>
</div>

<!-- /section:basics/sidebar -->
<div class="main-content">
    <div class="main-content-inner">
        <!-- #section:basics/content.breadcrumbs -->
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#report.UI.do">首页</a>
                </li>
            </ul>
            <!-- /.breadcrumb -->

            <!-- #section:basics/content.searchbox -->
            <#--<div class="nav-search" id="nav-search">
                <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
                </form>
            </div>-->
            <!-- /.nav-search -->

            <!-- /section:basics/content.searchbox -->
        </div>

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content">
            <!-- #section:settings.box -->
           <#-- <div class="ace-settings-container" id="ace-settings-container">
                <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                    <i class="ace-icon fa fa-cog bigger-130"></i>
                </div>

                <div class="ace-settings-box clearfix" id="ace-settings-box">
                    <div class="pull-left width-50">
                        <!-- #section:settings.skins &ndash;&gt;
                        <div class="ace-settings-item">
                            <div class="pull-left">
                                <select id="skin-colorpicker" class="hide">
                                    <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                    <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                    <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                    <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                </select>
                            </div>
                            <span>&nbsp; 选择皮肤</span>
                        </div>

                        <!-- /section:settings.skins &ndash;&gt;

                        <!-- #section:settings.navbar &ndash;&gt;
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar"/>
                            <label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
                        </div>

                        <!-- /section:settings.navbar &ndash;&gt;

                        <!-- #section:settings.sidebar &ndash;&gt;
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar"/>
                            <label class="lbl" for="ace-settings-sidebar"> 固定工具栏</label>
                        </div>

                        <!-- /section:settings.sidebar &ndash;&gt;

                        <!-- #section:settings.breadcrumbs &ndash;&gt;
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs"/>
                            <label class="lbl" for="ace-settings-breadcrumbs"> 固定顶部条</label>
                        </div>

                        <!-- /section:settings.breadcrumbs &ndash;&gt;

                        <!-- #section:settings.rtl &ndash;&gt;
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"/>
                            <label class="lbl" for="ace-settings-rtl"> 切换左右</label>
                        </div>

                        <!-- /section:settings.rtl &ndash;&gt;

                        <!-- #section:settings.container &ndash;&gt;
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>
                            <label class="lbl" for="ace-settings-add-container">
                                全屏缩放
                            </label>
                        </div>

                        <!-- /section:settings.container &ndash;&gt;
                    </div>
                    <!-- /.pull-left &ndash;&gt;

                    <div class="pull-left width-50">
                        <!-- #section:basics/sidebar.options &ndash;&gt;
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover"/>
                            <label class="lbl" for="ace-settings-hover"> 子菜单悬停</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact"/>
                            <label class="lbl" for="ace-settings-compact"> 简化工具栏</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight"/>
                            <label class="lbl" for="ace-settings-highlight"> 选中栏样式</label>
                        </div>

                        <!-- /section:basics/sidebar.options &ndash;&gt;
                    </div>
                    <!-- /.pull-left &ndash;&gt;
                </div>
                <!-- /.ace-settings-box &ndash;&gt;
            </div>-->
            <!-- /.ace-settings-container -->

            <!-- /section:settings.box -->
            <div class="page-content-area" data-ajax-content="true" id="page-content-area">
                <!-- ajax content goes here -->
            </div>
            <!-- /.page-content-area -->
        </div>
        <!-- /.page-content -->
    </div>
</div>
<!-- /.main-content -->

<#--<div class="footer">
    <div class="footer-inner">
        <!-- #section:basics/footer &ndash;&gt;
        <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">店务管理</span>
                            &copy; 2015 美云间
						</span>

            &nbsp; &nbsp;
        </div>

        <!-- /section:basics/footer &ndash;&gt;
    </div>
</div>-->

<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>
</div>
<!-- /.main-container -->

<!-- basic scripts -->

<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/js/jquery.mobile.custom.js'>" + "<" + "/script>");
</script>
<script src="${ctx}/js/bootstrap.js"></script>

<!-- ace scripts -->
<script src="${ctx}/js/ace/elements.scroller.js"></script>
<script src="${ctx}/js/ace/elements.colorpicker.js"></script>
<script src="${ctx}/js/ace/elements.fileinput.js"></script>
<script src="${ctx}/js/ace/elements.typeahead.js"></script>
<script src="${ctx}/js/ace/elements.wysiwyg.js"></script>
<script src="${ctx}/js/ace/elements.spinner.js"></script>
<script src="${ctx}/js/ace/elements.treeview.js"></script>
<script src="${ctx}/js/ace/elements.wizard.js"></script>
<script src="${ctx}/js/ace/elements.aside.js"></script>
<script src="${ctx}/js/ace/ace.js"></script>
<script src="${ctx}/js/ace/ace.ajax-content.js"></script>
<script src="${ctx}/js/ace/ace.touch-drag.js"></script>
<script src="${ctx}/js/ace/ace.sidebar.js"></script>
<script src="${ctx}/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="${ctx}/js/ace/ace.submenu-hover.js"></script>
<script src="${ctx}/js/ace/ace.widget-box.js"></script>
<script src="${ctx}/js/ace/ace.settings.js"></script>
<script src="${ctx}/js/ace/ace.settings-rtl.js"></script>
<script src="${ctx}/js/ace/ace.settings-skin.js"></script>
<script src="${ctx}/js/ace/ace.widget-on-reload.js"></script>
<script src="${ctx}/js/ace/ace.searchbox-autocomplete.js"></script>
<script src="${ctx}/js/bootbox.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.validate.message.cn.js"></script>
<script src="${ctx}/js/ace/ace-extra.js"></script>

<#--dialog-->
<script src="${ctx}/js/jquery-ui.js"></script>
<script src="${ctx}/js/jquery.ui.touch-punch.js"></script>

<#--时间-->
<script src="${ctx}/js/date-time/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/date-time/bootstrap-timepicker.js"></script>
<script src="${ctx}/js/date-time/moment.js"></script>
<script src="${ctx}/js/date-time/local-zh-cn.js"></script>
<script src="${ctx}/js/date-time/daterangepicker.js"></script>
<script src="${ctx}/js/date-time/bootstrap-datetimepicker.js"></script>


<#--上传-->
<link rel="stylesheet" href="${ctx}/css/html5uploader.css"/>
<link rel="stylesheet" href="${ctx}/css/uploadifive.css"/>
<script src="${ctx}/js/jquery.html5uploader.js"></script>
<script src="${ctx}/js/jquery.uploadifive.min.js"></script>

<#--富文本编辑-->
<link rel="stylesheet" href="${ctx}/js/kindeditor-4.1.10/themes/default/default.css" />
<script src="${ctx}/js/kindeditor-4.1.10/kindeditor-min.js"></script>
<script src="${ctx}/js/kindeditor-4.1.10/zh_CN.js"></script>
<script src="${ctx!}/js/jquery.easypiechart.js"></script>
<script src="${ctx!}/js/jquery.sparkline.js"></script>

<#--轮询图-->
<link rel="stylesheet" href="${ctx}/css/swiper.min.css"/>

</body>
<script type="text/javascript">
    $('#page-content-area').ace_ajax({
        content_url: function (hash) {
            return ace.util.changeURLArg(hash, "ace", "ajax");
        },
        default_url: '',
        max_load_wait: 3000
    });

    $('#page-content-area').on('ajaxloaderror ', function () {
        alertErr("服务器忙，请稍后再试");
    });


</script>
<input type="hidden" id="ctx" value="${ctx}" />
</html>
