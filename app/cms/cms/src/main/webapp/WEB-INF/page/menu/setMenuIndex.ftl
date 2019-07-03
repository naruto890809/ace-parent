<div class="row">
    <div class="col-xs-12 col-xs-12">
        <div class="row">
            <div class="col-sm-12">
                <div class="widget-box">
                    <div style="background: #5090c1 none repeat scroll 0 0;border-color: #5090c1;color: #fff;font-size: 14px;font-weight: lighter;height: 38px;">
                        <h4 style="padding-top: 10px;margin-left: 20px;">权限列表</h4>
                    </div>
                    <div class="widget-body" style=" border: 1px solid #5090c1;box-shadow: none;">
                        <div class="widget-main padding-8">
                            <div class="zTreeDemoBackground left">
                                <ul class="ztree" id="treePermission" style="-moz-user-select: none;">

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var scripts = ['${ctx}/js/ztree.js'];
    var option = { };

    var setting = {
        check: {
            enable: true
        },
        view: {
            showIcon: false,
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            beforeAsync: zTreeBeforeAsync
        },
        async: {
            enable: true,
            type: "get",
            contentType: "application/json",
            url: "${ctx}/html/menu.menuList.do?roleId="+'${roleId}'
        }
    };
    function zTreeBeforeAsync(treeId, treeNode) {
        return treeNode == undefined;
    }
    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePermission");
        zTree.checkNode(treeNode, !treeNode.checked, true, true);
        return false;
    }

    function nodeFilter(node) {
        return node.checked == true;
    }



    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        $.fn.zTree.init($("#treePermission"), setting);
    });


</script>