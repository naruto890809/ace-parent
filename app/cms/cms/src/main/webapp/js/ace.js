const BASE_URL = "http://127.0.0.1";
//const BASE_URL = "116.62.148.11";
//const BASE_URL = "47.111.93.165";
const HOME_PAGE = "/html/login.manageIndex.do";
const DEFAULT_CHECK_ALL_CLASS = "ace-check-all";
const DEFAULT_CHECK_ITEM_CLASS = "ace-check-item";
const DEFAULT_TABLE_ID = "ace-table";
const DEFAULT_TABLE_TEMP_CLASS = "ace-tr-temp";
const DEFAULT_SELECT_PAGESIZE_CLASS = "ace-select-pagesize";
const DEFAULT_TR_DELETE_BTN_CLASS = "ace-tr-delete";
const DEFAULT_TR_NAME_CLASS = "ace-tr-name";
const DEFAULT_TR_EDIT_CLASS = "ace-tr-edit";
const DEFAULT_FORM_ID = "ace-form";
const DEFAULT_FORM_SUNMIT_CLASS = "ace-form-submit";
const DEFAULT_REQUIRED_CLASS = "ace-required";
const DEFAULT_TABLE_SEARCH_CLASS = "ace-table-search";
const BATCH_DEL_CLASS = "ace-batch-del";
const DEFAULT_EXTEND_FORM_ID = "extend-form";
const DEFAULT_EDITOR_CLASS = "ace-editor";
const DEFAULT_LINK_BTN_CLASS = "ace-link-btn";
const DEFAULT_LINK_INPUT_CLASS = "ace-link-input";
const DEFAULT_MAX_PAGE_NUM = 10;

const OSS_DOMAIN = "127.0.0.1";
//const OSS_DOMAIN = "116.62.148.11";
//const OSS_DOMAIN = "47.111.93.165";
const COLOR_BOX_JS_URL = "../plugs/colorbox/jquery.colorbox-min.js";
const COLOR_BOX_CSS_URL = "../plugs/colorbox/colorbox.css";
const CHOOSE_IMAGE_URL = "http://127.0.0.1/oss/gallery.UI.do";
const KINDEDITOR_CSS_SIMPLE_URL = BASE_URL+"/js/kindeditor-4.1.10/themes/simple/simple.css";
const KINDEDITOR_CSS_DEFAULT_URL = BASE_URL+"/js/kindeditor-4.1.10/themes/default/default.css";
const KINDEDITOR_JS_URL =BASE_URL+ "/js/kindeditor-4.1.10/kindeditor-min.js";
const KINDEDITOR_LANGUAGE_URL = BASE_URL+ "/js/kindeditor-4.1.10/zh_CN.js";

const UEDITOR_JS_URL =BASE_URL+ "/js/ueditor/ueditor.all.min.js";
const UEDITOR_CONFIG_JS_URL =BASE_URL+ "/js/ueditor/ueditor.config.js";
const UEDITOR_LANGUAGE_URL = BASE_URL+ "/js/ueditor/lang/zh-cn/zh-cn.js";
const UEDITOR_PLUGIN_URL = BASE_URL+ "/js/ueditor/plugin.js";
function ace(option) {
    this.option = {
        baseUrl: BASE_URL,
        homePage: HOME_PAGE,
        checkAllClass: DEFAULT_CHECK_ALL_CLASS,
        checkItemClass: DEFAULT_CHECK_ITEM_CLASS,
        tableId: DEFAULT_TABLE_ID,
        tableTempClass: DEFAULT_TABLE_TEMP_CLASS,
        selectPageSizeClass: DEFAULT_SELECT_PAGESIZE_CLASS,
        trDeleteBtnClass: DEFAULT_TR_DELETE_BTN_CLASS,
        trEditBtnClass: DEFAULT_TR_EDIT_CLASS,
        trNameClass: DEFAULT_TR_NAME_CLASS,
        tableSearchClass: DEFAULT_TABLE_SEARCH_CLASS,
        batchDelClass: BATCH_DEL_CLASS,
        extendFormId: DEFAULT_EXTEND_FORM_ID,
        formId: DEFAULT_FORM_ID,
        submitClass: DEFAULT_FORM_SUNMIT_CLASS,
        requiredClass: DEFAULT_REQUIRED_CLASS,
        editorClass: DEFAULT_EDITOR_CLASS,
        linkBtnClass:DEFAULT_LINK_BTN_CLASS,
        linkInputClass:DEFAULT_LINK_INPUT_CLASS,
        maxPageNum: DEFAULT_MAX_PAGE_NUM,
        chooseImageUrl:CHOOSE_IMAGE_URL,
        parentId: '',
        pageSize: 10,
        id: 'id',
        appCode: "",
        corpCode: "",
        initImageBtn:false
    };
    this.data = {};
    $.extend(this.option, option);
    this.$parent = this.option.parentId ? $("#" + this.option.parentId) : $("body");
    this.$table = this.$parent.find("#" + this.option.tableId);
    this.$form = this.$parent.find("#" + this.option.formId);
    //window.defaultDomain = document.domain;
    document.domain = OSS_DOMAIN;
};
ace.status = {SUCCESS: 'SUCCESS', ERROR: 'ERROR', RELOGIN: 'RELOGIN'};
ace.post = function (url, param, success, callback) {
    if(typeof param == "function"){
        success = param;
        param = null;
    }

    $.ace_ajax({
        type: "POST",
        url: url,
        data: param,
        dataType: "json",
        success: function (data) {
            if (data.status == ace.status.SUCCESS) {
                if(success){
                    success(data);
                }
            } else if (data.status == ace.status.ERROR) {
                if (data.message) {
                    alertErr(data.message);
                } else {
                    alertErr("服务器忙");
                }
            } else if (data.status == ace.status.RELOGIN) {
                if (data.message) {
                    alertErr(data.message);
                } else {
                    alertErr("登录超时，请重新登录");
                }
                setTimeout(function () {
                    location.reload();
                }, 1500);
            } else {
                alertErr("出现未知错误");
            }
            if (callback) {
                callback(data);
            }
        },
        error: function (data) {
            alertErr("服务器忙");
            if (callback) {
                callback(data);
            }
        }
    });
};
ace.util = {
    changeURLArg: function (url, arg, arg_val) {
        var pattern = arg + '=([^&]*)';
        var replaceText = arg + '=' + arg_val;
        if (url.match(pattern)) {
            var tmp = '/(' + arg + '=)([^&]*)/gi';
            tmp = url.replace(eval(tmp), replaceText);
            return tmp;
        } else {
            if (url.match('[\?]')) {
                return url + '&' + replaceText;
            } else {
                return url + '?' + replaceText;
            }
        }
        return url + '\n' + arg + '\n' + arg_val;
    },
    jumpTo: function (url) {
        location.hash = ace.util.getUrl(url);
    },
    getUrl: function (url) {
        if (url.indexOf("/") != 0) {
            // 非"/"开头
            var addr = location.href;
            var start = addr.lastIndexOf(HOME_PAGE + "#") + HOME_PAGE.length + 1;
            var end = addr.lastIndexOf("/");
            if (end < start) {
                return url;
            }
            return addr.substring(start, end) + "/" + url;
        }
        return url;
    }
};
ace.prototype.table = function () {
    /**
     * 全选按钮事件绑定
     */
    this.bindCheckAll = function () {
        var $checkAllBtn = this.$parent.find('.' + this.option.checkAllClass);
        var checkItemClass = this.option.checkItemClass;
        var o = this;
        $checkAllBtn.unbind("click").bind("click", function () {
            var checked = this.checked;
            o.$table.find("." + checkItemClass).each(function () {
                this.checked = checked;
            });
        });
        return o;
    };
    /**
     * 填充表格
     * @param url
     */
    this.fillTable = function (url,pageChangeLimit) {
        if (!url && this.data.curUrl) {
            url = this.data.curUrl;
        }
        this.data.curUrl = url;
        var page = CookieUtil.getCookie(this.data.curUrl + ":page");
        if (!page) {
            page = 1;
        }
        var pageSize = CookieUtil.getCookie(this.data.curUrl + ":pageSize");
        if (!pageSize) {
            pageSize = this.$parent.find('.' + this.option.selectPageSizeClass).val();
        } else {
            this.$parent.find('.' + this.option.selectPageSizeClass).val(pageSize);
        }

        pageSize = pageSize ? pageSize : 10;
        if(pageChangeLimit){
            page = pageChangeLimit;
        }else{
            if (this.option.page) {
                var p = null;
                if (typeof this.option.page == 'function') {
                    p = parseInt(this.option.page());
                } else {
                    p = parseInt(this.option.page);
                }
                if (!isNaN(p)) page = p;
            }
        }

        if (this.option.pageSize) {
            var ps = null;
            if (typeof this.option.pageSize == 'function') {
                ps = parseInt(this.option.pageSize());
            } else {
                ps = parseInt(this.option.pageSize);
            }
            if (!isNaN(ps)) pageSize = ps;
        }

        CookieUtil.setCookie(this.data.curUrl + ":page", page);
        CookieUtil.setCookie(this.data.curUrl + ":pageSize", pageSize);
        var param = {'Page.pageNo': page, 'Page.pageSize': pageSize};
        if (this.option.searchParam) {
            param = $.param(param) + "&" + this.option.searchParam;
        }
        var o = this;
        var $table = o.$table;
        var $tbody = $table.find("tbody").empty();
        var $loading = $('<tr style="text-align: center;"><td colspan=' + $table.find("th").length + '><i class="fa fa-spinner fa-spin orange2 bigger-225"></i></td></tr>').appendTo($tbody);
        ace.post(url, param, function (res) {
            $(".totalCnt").html("共"+res.total+"条");
            o.data.rows = res.rows;
            o.createTr(res);
            o.createPagination(res);
            if (pageSize) {
                o.$parent.find("." + o.option.selectPageSizeClass).val(pageSize);
            }
            if (o.option.fillTableCallBack) {
                o.option.fillTableCallBack(res,o.option.searchParam);
            }
        }, function () {
            $loading.remove();
        });
        return o;
    };
    this.createTr = function (res) {
        var temp = this.$parent.find("." + this.option.tableTempClass + " tbody").html();
        var $tbody = this.$table.find("tbody").empty();
        var o = this;
        var page= res.pageNo;
        var pageSize=res.pageSize;
        var sequence=(page-1)*pageSize;
        $.each(res.rows, function (index) {
            sequence++;
            var tempHtml = temp;
            for (var key in this) {
                var value = this[key];

                if (o.option.formatter && o.option.formatter[key]) {
                    value = o.option.formatter[key](value, index,sequence);
                }

                if(key ==="index"){
                    value = index + 1;
                }

                if (value == null) {
                    value = "";
                }else if(value.length > 100 && key!="id" && key.indexOf("Id")<0 && value.indexOf("span") < 0){
                    value = "<span title='"+value+"'>"+value.substring(0,100)+"...</span>";
                }
                tempHtml = tempHtml.replace(new RegExp("%{" + key + "}", "g"), value);
            }
            tempHtml = tempHtml.replace(new RegExp("%{.*}", "g"), "");
            var $tr = $(tempHtml).appendTo($tbody);
            o.bindBtn($tr, this);
            if (o.option.createTrCallback) {
                o.option.createTrCallback(this, $tr , sequence);
            }
        });
        //ace.table.bindBtn();
        //取消全选
        var $btn = o.$parent.find("." + o.option.checkAllClass);
        if ($btn.length) {
            $btn[0].checked = false;
        }
        return o;
    };
    this.createPagination = function (res) {
        var $pagination = this.$parent.find(".pagination_parent .pagination");
        var $table = this.$table;
        var o = this;
        res.totalPages = res.totalPages?res.totalPages:1;
        if ($pagination.length == 0) {
            var $div = $('<div class="modal-footer no-margin-top"></div>');
            $('<div class="row"><div class="col-xs-6"><div class="dataTables_length pull-left" id="dynamic-table_length"><label>每页 <select name="dynamic-table_length" class="form-control input-sm ' + DEFAULT_SELECT_PAGESIZE_CLASS + '"> <option value="10">10</option> <option value="20">20</option> <option value="30">30</option><option value="50">50</option><option value="100">100</option></select>条</label><label class="totalCnt">  共'+res.total+'条</label></div></div><div class="col-xs-6 pagination_parent"></div></div>').appendTo($div);
            $pagination = $('<ul class="pagination pull-right no-margin"></ul>').appendTo($div.find(".pagination_parent"));
            $table.after($div);
            $div.find("." + o.option.selectPageSizeClass).change(function () {
                o.option.pageSize = this.value;
                o.fillTable("",1);
            });
        } else $pagination.empty();
        var $prev = $('<li class="prev" data-page="1"><a href="javascript:void(0);"><i class="ace-icon fa fa-angle-double-left"></i></a></li>').appendTo($pagination);
        if (res.pageNo == 1) $prev.addClass("disabled");
        $.each(o.createPageArr(res.pageNo, res.totalPages), function () {
            var $pitem = $('<li data-page="' + this + '"><a href="javascript:void(0);">' + this + '</a></li>').appendTo($pagination);
            if (this == res.pageNo) $pitem.addClass("disabled");
        });
        var $next = $('<li class="next" data-page="' + res.totalPages + '"><a href="javascript:void(0);"><i class="ace-icon fa fa-angle-double-right"></i></a></li>').appendTo($pagination);
        if (res.pageNo == res.totalPages) $next.addClass("disabled");
        $pagination.find("li").not(".disabled").unbind("click").bind("click", function () {
            o.option.page = $(this).attr("data-page");
            o.fillTable();
        });
        return o;
    };
    this.createPageArr = function (page, pageCount) {
        var startPage = 1;
        var endPage = pageCount?pageCount:startPage;
        var o = this;
        if (pageCount > o.option.maxPageNum) {
            startPage = page - o.option.maxPageNum / 2 + 1;
            endPage = page + o.option.maxPageNum / 2;
            //如果结束页>总页数,则
            if (endPage > pageCount) {
                //startPage = startPage-(endPage-pageCount)
                startPage -= endPage - pageCount;
                endPage = pageCount;
            }
            //如果开始页<=0,则
            if (startPage <= 0) {
                //endPage = endPage+(1-startPage)
                endPage += 1 - startPage;
                startPage = 1;
            }
        }
        var pageArr = new Array();
        for (var i = startPage; i <= endPage; i++) {
            pageArr.push(i);
        }
        return pageArr;
    };
    this.bindBtn = function ($tr, json) {
        var o = this;
        //删除按钮
        $tr.find(" ." + o.option.trDeleteBtnClass).unbind("click").bind("click", function () {
            var deleteUrl = "";
            if (o.option.deleteUrl instanceof Function) {
                deleteUrl = o.option.deleteUrl(json, $tr);
            } else {
                deleteUrl = o.option.deleteUrl;
            }
            var name = "该行数据";
            //var $tr = $(this).parents("tr");
            var $table = o.$table;
            var index = $table.find("th").index($table.find("." + o.option.trNameClass));
            if (index > -1) {
                name = "'" + $tr.find("td").eq(index).text().trim() + "'";
            }
            var id = o.option.id;
            var param = {};
            param[id] = $tr.attr("data-id");
            myConfirm("删除可能会影响其他业务数据！确定要删除" + name + "吗？", function () {
                ace.post(deleteUrl, param, function (data) {
                        alertSuc("删除成功");
                        $tr.remove();
                });
            });
        });
        //编辑按钮
        $tr.find(" ." + o.option.trEditBtnClass).unbind("click").bind("click", function () {
            if (!o.option.updateUrl) {
                return o;
            }
            var updateUrl = "";
            if (o.option.updateUrl instanceof Function) {
                updateUrl = o.option.updateUrl(json, $tr);
            } else {
                var id = o.option.id;
                updateUrl = ace.util.changeURLArg(o.option.updateUrl, id, $(this).parents("tr").attr("data-id"));
            }
            ace.util.jumpTo(updateUrl);
        });
        return o;
    };
    this.delBatch = function () {
        var o = this;
        this.$parent.find(".ace-batch-del" ).unbind("click").bind("click", function () {
            var url = o.option.delBatchUrl;
            var $col = $("#ace-table").find(".ace-check-item:checked");

            if ($col.length < 1) {
                alertErr("请选中要删除的行");
                return o;
            }
            myConfirm("确定要删除这" + $col.length + "条数据吗?", function () {
                var ids = new Array();
                $col.each(function () {
                    ids.push($(this).parents("tr").attr("data-id"));
                });
                ace.post(url, {ids: ids}, function (data) {
                    var count = data.data;
                    if (!count) {
                        count = ids.length;
                    }
                    alertSuc("成功删除" + count + "条数据");
                    setTimeout(o.fillTable(), 1500);

                });
            });
            return o;
        });
    };
    this.update = function (url,title) {
        var $col = this.$table.find("." + this.option.checkItemClass + ":checked");
        if ($col.length < 1) {
            alertErr("请选中要"+title+"的行");
            return o;
        }
        var o = this;
        myConfirm("确定要"+title+"这" + $col.length + "条数据吗?", function () {
            var ids = new Array();
            $col.each(function () {
                ids.push($(this).parents("tr").attr("data-id"));
            });
            ace.post(url, {ids: ids}, function (data) {
                var count = data.data;
                if (!count) {
                    count = ids.length;
                }
                alertSuc("成功"+title+"" + count + "条数据");
                setTimeout(o.fillTable(), 1500);
            });
        });
        return o;
    };
    this.search = function () {
        var o = this;
        this.$parent.find("." + this.option.tableSearchClass).unbind("click").bind("click", function () {
            o.doSearch();
        });
        return o;
    };
    this.doSearch = function(){
        var o = this;
        o.option.page = 1;
        var $extendForm = o.$parent.find("#" + o.option.extendFormId);
        if ($extendForm) {
            $.each($extendForm.find("input"),function(){
                var val = $(this).val();
                if(val){
                    $(this).val($.trim(val));
                }
            });

            o.option.searchParam = $extendForm.serialize();
        }
        o.fillTable(o.option.searchUrl);
    };
    this.getCheckedIds = function () {
        var $col = this.$table.find(" ." + this.option.checkItemClass + ":checked");
        var ids = new Array();
        $col.each(function () {
            ids.push($(this).parents("tr").attr("data-id"));
        });
        return ids;
    },
        this.getChecked = function () {
            var idsArr = this.getCheckedIds();
            var jsonArr = new Array();
            var o = this;
            if (o.data.rows) {
                $.each(o.data.rows, function (index) {
                    for (var key in this) {
                        var value = this[key];
                        if ($.inArray(value, idsArr) > -1) {
                            jsonArr.push(this);
                            break;
                        }
                    }
                });
            }
            return jsonArr;
        };
    this.refresh = function () {
        return this.fillTable();
    };
    this.init = function () {
        $('.chosen-select').unbind("change").change(function () {
            var tag_input = $(this).closest('.alldis-option').find('.form-field-tags');
            try {
                var $tag_obj = tag_input.data('tag');
                $tag_obj.element.siblings('.tag').remove()
            }
            catch (e) {
            }
        });
        $('.chosen-select').chosen({
            allow_single_deselect: true,
            search_contains: true,
            width: "100%",
            no_results_input: '<ul class="chosen-results"><li class="active-result" data-option-array-index="1"><span></span></li></ul>'
        });

        this.bindCheckAll();
        this.fillTable(this.option.listUrl);
        this.search();
        this.delBatch();
        return this;
    };
    return this;
};
ace.prototype.form = function () {
    this.init = function () {
        var o = this;
        o.$form.find(" .required").each(function () {
            $(this).parents(".form-group").find("label").append($("<span class='" + o.option.requiredClass + "'>*</span>"));
        });

        o.$form.find(" ." + o.option.submitClass).unbind("click").bind("click", function () {
            if (o.option.beforeSubmit) {
                if (!o.option.beforeSubmit()) {
                    return;
                }
            }

            o.submit(this, o.option.submitUrl, o.option.formId, o.option.success);

        });
        o.data.validator = o.$form.validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: false,
            ignore: "",
            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                    var controls = element.closest('div[class*="col-"]');
                    if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                }
                else if (element.is('.select2')) {
                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                }
                else if (element.is('.chosen-select')) {
                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                }
                else error.insertAfter(element.parent());
            },
            submitHandler: function (form) {
            },
            invalidHandler: function (form) {
            }
        });
        o.imageBtn_init();
        o.editor_init();
        o.link_init();
        $('.chosen-select').unbind("change").change(function () {
            var tag_input = $(this).closest('.alldis-option').find('.form-field-tags');
            try {
                var $tag_obj = tag_input.data('tag');
                $tag_obj.element.siblings('.tag').remove()
            }
            catch (e) {
            }
        });
        $('.chosen-select').chosen({
            allow_single_deselect: true,
            width: "100%",
            no_results_input: '<ul class="chosen-results"><li class="active-result" data-option-array-index="1"><span>A</span></li></ul>'
        });
    };

    this.submit = function (btn, url, formId, success, callBack) {
        var o = this;
        if (btn) {
            btn.disabled = true;
        }

        if (o.data.validator) {
            if (!o.data.validator.form()) {
                alertErr("请认真填写表单必填项");
                btn.disabled = false;
                return;
            }
        }

        var param = o.$form.serialize();
        success = success ? success : function (res) {
            if (res.location) {
                alertSuc("操作成功,跳转中");
                setTimeout(function () {
                    ace.util.jumpTo(res.location);
                }, 1500);
            }
        };
        callBack = callBack ? callBack : function (data) {
            if (data.status == ace.status.SUCCESS) {
                return;
            }
            btn.disabled = false;
        };
        ace.post(url, param, success, callBack);
    };
    this.imageBtn_init = function () {
        var o = this;
        if (o.$parent.find('.cho-img').length == 0&&!o.option.initImageBtn) {
            return;
        }
        $("<link>").attr({rel: "stylesheet", type: "text/css", href: COLOR_BOX_CSS_URL}).appendTo("head");
        loadScript(COLOR_BOX_JS_URL, function () {
            var count = 0;
            $.each(o.$parent.find('.hid-img'), function () {
                var $parent = $(this).parent();
                if ($(this).val()) {
                    var $img = $parent.find(".form-img");
                    if ($img.length == 0) {
                        createImg($parent.find(".cho-img"), $(this).val());
                    } else {
                        $img.attr("src", $(this).val());
                    }
                } else {
                    $parent.find(".form-img").remove();
                    $parent.find(".del-img").remove();
                }
            });
            var setting = {
                href: ace.util.changeURLArg(ace.util.changeURLArg(o.option.chooseImageUrl, "corpCode", o.option.corpCode), "appCode", o.option.appCode),
                transition: "elastic",
                iframe: true,
                width: "90%",
                height: "90%",
                innerWidth: 800,
                opacity: 0.5,
                overlayClose: true,
                title: " ",
                scrolling: false,
                onClosed: function () {
                }
            };

            o.$parent.find(".cho-img").not(".multi-img").colorbox(setting);
           /* o.$parent.undelegate(".cho-img","click").delegate(".cho-img","click", function () {
                if(!$(this).hasClass("multi-img")){
                    $.colorbox(setting);
                }
            });*/
            setting.href = ace.util.changeURLArg(setting.href,"type","multi");
            o.$parent.find(".multi-img").colorbox(setting);
            /*o.$parent.undelegate(".multi-img","click").delegate(".multi-img","click", function () {
                $.colorbox(setting);
            });*/

            o.$parent.undelegate(".cho-img","click").delegate(".cho-img","click", function () {
                o.$parent.find(".cho-img-cur").removeClass("cho-img-cur");
                $(this).addClass("cho-img-cur");
            });

            o.$parent.find(".pic-atm-area").undelegate(".del-img-btn","click").delegate(".del-img-btn","click",function(){
                $(this).closest(".pic-mod").remove();
            });

        });

        window.createImg = function ($btn, url) {
            var $parent = $btn.parent();
            var $img = $parent.find(".form-img");
            if ($img.length > 0) {
                $img.attr("src", url);
            } else {
                $img = $("<img class='form-img'>").css("background-color", "#eee");
                if ($btn.attr("data-height")) {
                    $img.css("height", $btn.attr("data-height"));
                } else {
                    $img.css("height", "100px");
                }
                if ($btn.attr("data-mwidth")) {
                    $img.css("max-width", $btn.attr("data-mwidth"));
                }

                $btn.before($img.attr("src", url));
            }
            if ($parent.find(".hid-img").length == 0) {
                $parent.find("input[type='hidden']").addClass("hid-img");
            }
            //移除按钮
            if($btn.attr("data-required")!="true"){
                ace.imageBtn_remove($parent);
            }
        };

        window.chooseImage = function (img) {
            var url = img.url;
            if (url.indexOf("http://") < 0) {
                url = "http://" + window.location.host + url;
            }
            var $parent = $(".cho-img-cur").parent();

            createImg($(".cho-img-cur"), url);
            if($parent.find(".hid-img").val()!=url){
                $parent.find(".hid-img").val(url).trigger("change");
            }


            var mh = $('.cho-img-cur').attr("data-call");
            if (mh) {
                eval(mh + "(img)");
            }
            $('.cho-img-cur').removeClass("cho-img-cur");
            $.fn.colorbox.close();
        };

        window.multiImage = function(imgs){
            var $content = $(".cho-img-cur").closest(".form-group").find(".pic-atm-area");
            var name = $(".cho-img-cur").attr("data-name");
            var max = $(".cho-img-cur").attr("data-max");
            if(max){
                max = parseInt(max);
            }
            $.each(imgs,function(){
                if(!isNaN(max)&&$content.find(".pic-mod").length>=max){
                    return false;
                }
                $('<span class="pic-mod"> <input value="'+this.url+'" name="'+name+'" class="item-pic hid-img" type="hidden"><img src="'+this.url+'"  class="atm-img" title="'+this.title+'"> <div class="del-img-btn"></div></span>').appendTo($content);

            });
            $.fn.colorbox.close();
        }

        ace.imageBtn_remove = function ($obj) {
            var $delBtn = $obj.find(".del-img");
            if ($delBtn.length == 0) {
                $delBtn = $('<button type="button" class="btn  btn-sm btn-danger  del-img">移除</button>');
                $obj.find(".cho-img").after($delBtn);
                $delBtn.bind("click", function () {
                    $(this).parent().find(".form-img").remove();
                    $(this).parent().find(".hid-img").val("");
                    $(this).remove();
                });
            }
        }
    }
    this.editor_init = function () {
        var o = this;
       /* var setting = {
            //useContextmenu:false,
            themeType: "simple",
            resizeType: 1,
            syncType: "",
            allowPreviewEmoticons: false,
            allowFileManager: false,
            allowImageUpload:false,
            showLocal:false,
            minWidth: 500,
            width: 670,
            filePostName:'img',
            afterCreate: function () {
                this.sync();
            },
            afterBlur: function () {
                this.sync();
            }
        };
        setting.imageSetting = {
            href: ace.util.changeURLArg(ace.util.changeURLArg(ace.util.changeURLArg(o.option.chooseImageUrl, "corpCode", o.option.corpCode), "appCode", o.option.appCode),"type","editor"),
            transition: "elastic",
            iframe: true,
            width: "90%",
            height: "90%",
            innerWidth: 800,
            opacity: 0.5,
            overlayClose: true,
            title: " ",
            scrolling: false,
            onClosed: function () {
            }
        };
        var $editor = o.$parent.find("." + o.option.editorClass);
        if (!$editor || $editor.length == 0) {
            return;
        }
        $("<link>").attr({rel: "stylesheet", type: "text/css", href: COLOR_BOX_CSS_URL}).appendTo("head");
        loadScript(COLOR_BOX_JS_URL, function () {});

        $("<link>").attr({
            rel: "stylesheet",
            type: "text/css",
            href: KINDEDITOR_CSS_DEFAULT_URL
        }).appendTo("head");
        $("<link>").attr({
            rel: "stylesheet",
            type: "text/css",
            href: KINDEDITOR_CSS_SIMPLE_URL
        }).appendTo("head");
        loadScript(KINDEDITOR_JS_URL, function () {
            loadScript(KINDEDITOR_LANGUAGE_URL, function () {
                $.each($editor, function (index) {
                    if (!this.id) {
                        this.id = "editor_" + index;
                    }
                    KindEditor.create("#" + this.id, setting);
                });
            });
        });*/
        var $editor = o.$parent.find("." + o.option.editorClass);
        if (!$editor || $editor.length == 0) {
            return;
        }
        window.editorImgSetting = {
            href: ace.util.changeURLArg(ace.util.changeURLArg(ace.util.changeURLArg(o.option.chooseImageUrl, "corpCode", o.option.corpCode), "appCode", o.option.appCode),"type","editor"),
            transition: "elastic",
            iframe: true,
            width: "90%",
            height: "90%",
            innerWidth: 800,
            opacity: 0.5,
            overlayClose: true,
            title: " ",
            scrolling: false,
            onClosed: function () {
            }
        };
        window.onerror = function(sMsg,sUrl,sLine){
            try{
                if(sUrl.indexOf("ueditor")>=0){
                    return true;
                }
            }catch(e){
            }

        }
        loadScript(COLOR_BOX_JS_URL, function () {});
        $("<link>").attr({rel: "stylesheet", type: "text/css", href: COLOR_BOX_CSS_URL}).appendTo("head");
        loadScript(UEDITOR_CONFIG_JS_URL, function () {
            loadScript(UEDITOR_JS_URL, function () {
                loadScript(UEDITOR_LANGUAGE_URL, function () {
                    loadScript(UEDITOR_PLUGIN_URL, function () {
                        $.each($editor, function (index) {
                            if (!this.id) {
                                this.id = "editor_" + index;
                            }
                            UE.getEditor(this.id,ueditorSetting.simple);
                        });
                    });
                });
           });
        });
    }

    this.link_init = function(){
        var o = this;
        /*
        var $btns = o.$parent.find("."+ o.option.linkBtnClass);
        if(!$btns.length){
            return;
        }*/
        o.$parent.undelegate("."+ o.option.linkBtnClass,"click").delegate("."+ o.option.linkBtnClass,"click",function(){
            $(".link-input-cur").removeClass("link-input-cur");
            $(this).parent().find("."+ o.option.linkInputClass).addClass("link-input-cur");
            creatDialog($("#link-selector"));
            var type = $(this).attr("data-type");
            $(".type_show").removeClass("type_show");
            if(type){
                $("#dialogTab li").not(".close-dialog").hide();
                $.each(type.split(","),function(){
                    $("#dialogTab li[data-type='"+this+"'] ").show().addClass("type_show").appendTo($("#dialogTab"));
                });
                if(!$("#dialogTab .active").hasClass("type_show")){
                    $("#dialogTab .type_show:first a").trigger("click");
                }
            }else{
                $("#dialogTab li").show();
            }
        });
    }
    return this;
}
;
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

window.alertErr = function (text, time) {
    time = time ? time : 1500;
    var $frame = $(".default-frame-err");
    if ($frame.length == 0) {
        $frame = $('<div class="alert-frame default-frame-err"><div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"></button><span class="frame-msg"></span></div></div>').hide().appendTo('body');
    }
    $frame.find(".frame-msg").text(text);
    $frame.fadeIn(function () {
        setTimeout(function () {
            $frame.fadeOut();
        }, time);
    });
};
window.alertSuc = function (text, time) {
    time = time ? time : 1500;
    var $frame = $(".default-frame-suc");
    if ($frame.length == 0) {
        $frame = $('<div class="alert-frame default-frame-suc"><div class="alert alert-success"><button type="button" class="close" data-dismiss="alert"></button><span class="frame-msg"></span></div></div>').hide().appendTo('body');
    }
    $frame.find(".frame-msg").text(text);
    $frame.fadeIn(function () {
        setTimeout(function () {
            $frame.fadeOut();
        }, time);
    });
};
window.myConfirm = function (text, callback) {
    bootbox.confirm({
            message: text,
            buttons: {
                confirm: {
                    label: "确定",
                    className: "btn-primary btn-sm"
                },
                cancel: {
                    label: "取消",
                    className: "btn-sm"
                }
            },
            callback: function (result) {
                if (result && callback) callback(result);
            }
        }
    );
};
function loadScript(url, callback) {
    var script = document.createElement("script")
    script.type = "text/javascript";
    if (script.readyState) {  //IE
        script.onreadystatechange = function () {
            if (script.readyState == "loaded" ||
                script.readyState == "complete") {
                script.onreadystatechange = null;
                callback();
            }
        };
    } else {  //Others
        script.onload = function () {
            callback();
        };
    }
    script.src = url;
    document.body.appendChild(script);
}


Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
window.myConfirmWithBtn = function (text, sureText,cancelText,callback) {
    bootbox.confirm({
            message: text,
            buttons: {
                confirm: {
                    label: sureText,
                    className: "btn-primary btn-sm"
                },
                cancel: {
                    label: cancelText,
                    className: "btn-sm"
                }
            },
            callback: function (result) {
                if (result && callback) callback(result);
            }
        }
    );
};

function IsURL (str_url) {
    var strRegex = '^((https|http)?://)'
        + '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@
        + '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184
        + '|' // 允许IP和DOMAIN（域名）
        + '([0-9a-z_!~*\'()-]+.)*' // 域名- www.
        + '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名
        + '[a-z]{2,6})' // first level domain- .com or .museum
        + '(:[0-9]{1,4})?' // 端口- :80
        + '((/?)|' // a slash isn't required if there is no file name
        + '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
    var re=new RegExp(strRegex);
//re.test()
    if (re.test(str_url)) {
        return (true);
    } else {
        return (false);
    }
};

jQuery(function($){
    var _ajax= $.ajax;
    $.ace_ajax=function(opt){
        var _success = opt && opt.success || function(a, b){};
        var _opt = $.extend(opt, {
            success:function(data, textStatus){
                var loginUrl = new RegExp("login.index.do");
                try{
                    if(data.location&&loginUrl.test(data.location)){
                        window.location.href= data.location;
                        return;
                    }
                }catch(e){

                }

                _success(data, textStatus);
            }
        });
        _ajax(_opt);
    };
});

(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

function openDialogByUrl(url,title,openCallback,closeCallback,width){
    var div=$("<div id='divDialog' title='"+title+"'></div>").appendTo($("BODY"));
    var content=div.load(url,{});
    $("#divDialog").data("url",url);
    if(width == null){
        width =600;
    }
    div.dialog({
        autoOpen:true,
        width:width,
        modal:true,
        bgiframe:true,
        close:function(evt,ui)
        {
            if (closeCallback) {
                closeCallback();
            }
            div.dialog("destroy");
            div.html("").remove();
        },
        open:function(evt,ui){
            if (openCallback) {
                openCallback();
            }
        }
    });
}

function fillQuestion(obj,jsonQ){
    if(jsonQ){
        var length = jsonQ.length;
        var hasDel = false;
        if(length >= 1){
            hasDel = true;
            for(var o in jsonQ){
                obj.append(getQuestionHtml(hasDel,jsonQ[o].faqQuestion,jsonQ[o].faqAnswer));
            }
        }else{
            obj.html(getQuestionHtml());
        }
    }else{
        obj.html(getQuestionHtml());
    }
    obj.delegate('.add-pro-question', 'click', function (e)  {
        $(this).closest(".span-pro-question").after(getQuestionHtml(true));

    });
}

function getQuestionHtml(hasDel,question,answer){
    if(typeof(hasDel) == "undefined"){
        hasDel = "";
    }
    if(typeof(question) == "undefined"){
        question = "";
    }
    if(typeof(answer) == "undefined"){
        answer = "";
    }


    if(hasDel){
        hasDel =  '<a href="#" data-action="close" class="del-pro-question">'+
            '<i class="ace-icon fa fa-times"></i>'+
            '</a>';
    }
    return '<span class="block input-icon input-icon-right span-pro-question">'+
        '<div class="widget-box">'+
        '<div class="widget-header" style="height: 50px;">'+
        '<input type="text" class="col-xs-10 col-sm-9 proQuestion" value="'+question+'" style="margin-top: 7px;" placeholder="Q：请输入问题">'+
        '<div class="widget-toolbar">'+
        '<a href="#" data-action="collapse" >'+
        '<i class="ace-icon fa fa-chevron-up"></i>'+
        '</a>'+
        '<a href="#" data-action="add" class="orange2 add-pro-question" class="fillQuestionAdd">'+
        '<i class="ace-icon fa fa-plus"></i>'+
        '</a>'+

         hasDel+

        '</div>'+
        '</div>'+
        '<div class="widget-body">'+
        '<div class="widget-main">'+
        '<textarea class="form-control limited proQuestionAnswer" style="width: 76%;height: 55px;"  maxlength="200" placeholder="A：请输入答案">'+answer+'</textarea>'+
        '</div>'+
        '</div>'+
        '</div>'+
        ' </span>';
}