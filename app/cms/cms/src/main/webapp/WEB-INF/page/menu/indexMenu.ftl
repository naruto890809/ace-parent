<title>菜单管理</title>
    <button type="button" class="btn btn-info" onclick="ace.util.jumpTo('role.addIndex.do')">
        <i class="ace-icon fa fa-plus"></i> 添加
    </button>
<div class="page-header">

</div>

<form id="submitForm"></form>
<div class="row">
    <div class="col-xs-12">
        <div class="dataTables_wrapper form-inline ">
            <div class="row">



            </div>
            <table id="ace-table" class="table table-striped table-bordered table-hover  dataTable">
                <thead>
                <tr>
                    <th>序号</th>
                <#--<th>角色编号</th>-->
                    <th>角色名称</th>
                    <#--<th>状态</th>-->
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <table class="ace-tr-temp">
                <tbody>
                <tr data-id="%{roleId}">
                    <td>%{index}</td>
                    <#--<td>%{roleCode}</td>-->
                    <td>%{roleName}</td>
                    <#--<td>%{visible}</td>-->
                    <td>
                        <div></div>
                        <div class="btn-group">

                            <button class="btn btn-success btn-xs ace-menu-set" data-id="%{roleId}">
                                设置权限
                            </button>
                            <button class="btn btn-xs btn-info ace-tr-edit">
                                <i class="ace-icon fa fa-pencil bigger-120">编辑</i>
                            </button>
                            <#--<button class="btn btn-success btn-xs btn-info ace-tr-visible " visible="1" >
                                <i class="visible-btn">禁用</i>
                            </button>-->

                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="setMenu" class="hide">
    <div id="setMenuContent"></div>
</div>

<script type="text/javascript">
    var scripts = ['${ctx}/js/ztree.js'];
    var option = {
        appCode:"cms",
        corpCode:"${corpCode!}",
        listUrl: "menu.searchRole.do",
        updateUrl: function (json, $tr) {
            return '${ctx}/html/role.editRoleIndex.do?role.roleId=' + json.roleId;
        },
        fillTableCallBack:function(){
            $('.ace-tr-visible').on('click',visible);
        },
        createTrCallback:function(json,$tr){
            if(json.roleCode =="president" || json.roleCode =="shop_manager" || json.roleCode =="beautician" || json.roleCode =="reception"){
                $tr.find(".ace-tr-visible").hide();
            }
            if(json.visible==1){
                $tr.find('.ace-tr-visible').text("禁用").attr("visible",0).removeClass("btn-success").addClass("btn-warning");
            }else{
                $tr.find('.ace-tr-visible').text("启用").attr("visible",1).removeClass("btn-warning").addClass("btn-success");
            }
        },
        /*formatter: {

            visible: function (value, index) {
                if(value==0){
                    return '<span class="label label-warning arrowed arrowed-right visible-text">禁用</span>'
                }else{
                    return '<span class="label label-success arrowed arrowed-right visible-text">启用</span>'
                }
            }
        },*/
        id:"menu.menuId"
    };

    visible=function(){
        $obj=$(this).parent().parent().parent();
        var visiblev=$(this).attr("visible");
        if(visiblev==1){
            $(this).text("禁用").removeClass("btn-success").addClass("btn-warning");
            $obj.find(".visible-text").removeClass("label-warning").addClass("label-success").text("启用");
            $(this).attr("visible",0);
        }else{
            $(this).attr("visible",1);
            $(this).removeClass("btn-warning").addClass("btn-success").text("启用");
            $obj.find(".visible-text").removeClass("label-success").addClass("label-warning").text("禁用");
        }
        var roleId=$obj.attr("data-id");

        $.ajax({
            type : "post",
            url : "${ctx}/html/role.commend.do",
            data:{
                "role.roleId":roleId,
                "role.visible":visiblev
            },
            async : true,
            success : function(data){
                if (data.status == "SUCCESS") {

                }

            }
        });
    };

    var table;
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {

        table = new ace(option).table().init();

        $('#ace-table').delegate('.ace-menu-set', 'click', function (e)  {
            e.preventDefault();
            var roleId=$(this).attr("data-id");
            var div=$("<div id='divDialog' title='权限设置'></div>").appendTo($("BODY"));
            var url ="menu.setMenuIndex.do?roleId="+roleId;
            var content=div.load(url,{});
            $("#divDialog").data("url",url);
            div.dialog({
                autoOpen:true,
                width:600,
                modal:true,
                bgiframe:true,
                close:function(evt,ui)
                {
                    div.dialog("destroy");
                    div.html("").remove();
                },
                open:function(evt,ui){
                    $(".ui-dialog-titlebar-close .ui-button-text").remove();
                },
                buttons: [
                {
                    text: "取消",
                    "class" : "btn btn-minier",
                    click: function() {
                        div.dialog( "close" );
                    }
                },
                {
                    text: "确认",
                    "class" : "btn btn-primary btn-minier",
                    click: function() {
                        var zTree = $.fn.zTree.getZTreeObj("treePermission");
                        var checkNodes = zTree.getNodesByFilter(nodeFilter);
                        if (checkNodes.length > 0) {
                            var ids = new Array();
                            var idsMenu = new Array();
                            var menuList = new Array();
                            for(var i=0;i<checkNodes.length;i++){
                                ids.push(checkNodes[i].id );
                                idsMenu.push(checkNodes[i].other);

                                var menu={}//
                                menu.id =checkNodes[i].id;
                                menu.other =checkNodes[i].other;
                                menuList.push(menu);
                            }

                            ace.post("${ctx}/html/menu.setMenu.do",{ ids: ids,roleId:roleId,idsMenu:idsMenu,menuList:JSON.stringify(menuList)},function(data){
                                if (data.status == "ERROR") {
                                    alertErr("哇哦，操作失败咯！");
                                }else if(data.status == "SUCCESS"){
                                    div.dialog("close");
                                    div.dialog("destroy");
                                }
                            });
                        }else{
                            alertErr("请选择后在提交！");
                        }
                    }
                }
            ]
            });

           /* $("#setMenu" ).removeClass('hide').dialog({
                modal: true,
                title: '设置权限',
                width:600,
                open:function(){
                    $(".ui-dialog-titlebar-close .ui-button-text").remove();
                    $("#setMenuContent").html('').load("menu.setMenuIndex.do?roleId="+roleId);
                },
                close:function(){
                    $("#setMenuContent").html('');
                },buttons: [
                    {
                        text: "取消",
                        "class" : "btn btn-minier",
                        click: function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    {
                        text: "确认",
                        "class" : "btn btn-primary btn-minier",
                        click: function() {
                            var zTree = $.fn.zTree.getZTreeObj("treePermission");
                            var checkNodes = zTree.getNodesByFilter(nodeFilter);
                            if (checkNodes.length > 0) {
                                var ids = new Array();
                                var idsMenu = new Array();
                                var menuList = new Array();
                                for(var i=0;i<checkNodes.length;i++){
                                    ids.push(checkNodes[i].id );
                                    idsMenu.push(checkNodes[i].other);

                                    var menu={}//
                                    menu.id =checkNodes[i].id;
                                    menu.other =checkNodes[i].other;
                                    menuList.push(menu);
                                }

                                ace.post("${ctx}/html/menu.setMenu.do",{ ids: ids,roleId:roleId,idsMenu:idsMenu,menuList:JSON.stringify(menuList)},function(data){
                                        if (data.status == "ERROR") {
                                            alertErr("哇哦，操作失败咯！");
                                        }else if(data.status == "SUCCESS"){
                                            $("#setMenu" ).dialog("close");
                                        }
                                });
                            }else{
                                alertErr("请选择后在提交！");
                            }
                        }
                    }
                ]
            });*/
        });

    });

</script>


