/**
 * Created by Administrator on 2016/2/26 0026.
 */
;(function(){
    var map = {};//存储表单id
    var mapAmount = {};//存储表单id


    var w = this;
    w.step = {

        //SKU信息组合
        Creat_Table: function (json) {
            if(json){
                $.each(json,function(name,value) {
                    map[name]=value;
                });
            }
            /////


            w.step.hebingFunction();
            var SKUObj = $('.tdy-table .alldis-option');

            //var skuCount = SKUObj.length;//
            var arrayTile = new Array();//标题组数
            var arrayInfor = new Array();//盛放每组选中的CheckBox值的对象
            var arrayColumn = new Array();//指定列，用来合并哪些列
            var bCheck = true;//是否全选
            var columnIndex = 0;
            $.each(SKUObj, function (i, item){
                arrayColumn.push(columnIndex);
                columnIndex++;
//                arrayTile.push($(this).find('option:selected').text());
                arrayTile.push($(this).find('.chosen-single').text()); //任意输入值

                var itemName = "Father_Item" + i;
                //选中的CHeckBox取值
                var order = new Array();
                $(this).find('.tag').each(function(j){
//                    var v=$(this).text().replace('×','');
                    var v=$(this).find(".tagName").text().trim();;
                    order.push(v);
//                    console.log(v);
                });
                arrayInfor.push(order);
                if (order.join() == ""){
                    bCheck = false;
                }
                //var skuValue = SKUObj.find("li").eq(index).html();
            });
            //开始创建Table表
            if (bCheck == true) {
                var RowsCount = 0;
                $(".showRegion").html("");
                var table = $('<table id="simple-table" class="table table-striped table-bordered table-hover"></table>');
                table.appendTo($(".showRegion"));
                var thead = $("<thead></thead>");
                thead.appendTo(table);
                var trHead = $("<tr></tr>");
                trHead.appendTo(thead);
                //创建表头
                $.each(arrayTile, function (index, item) {
                    var td = $('<th class="center"><span class="bigger-120">' + item + '</span></th>');
                    td.appendTo(trHead);
                });
                var itemColumHead = $('<th class="center"><span class="bigger-120">原价</span></th><th class="center"><span class="bigger-120">售价</span></th><th class="center"><span class="bigger-120">库存</span></th><th class="center"><span class="bigger-120">商家编码</span></th><th class="center"><span class="bigger-120">条形编码</span></th>');
                itemColumHead.appendTo(trHead);
                //var itemColumHead2 = $("<td >商家编码</td><td >商品条形码</td>");
                //itemColumHead2.appendTo(trHead);
                var tbody = $("<tbody></tbody>");
                tbody.appendTo(table);
                ////生成组合
                var zuheDate = w.step.doExchange(arrayInfor);
                try {
                    zuheDate.length
                } catch (e) {
                    return ;
                }
                if (zuheDate.length > 0) {
                    //创建行
                    $.each(zuheDate, function (index, item) {
                        var td_array = item.split(",");
                        var tr = $('<tr class="center"></tr>');
                        tr.appendTo(tbody);
                        var name="";
                        $.each(td_array, function (i, values) {
                            var strs= new Array()
                            strs=values.split("#"); //字符分割
                            var td = $("<td ><span class='bigger-120'>" + values + "</span></td>");
                            td.appendTo(tr);
                            name=name+'_'+values;
                        });
                        var J_SkuField_bprice=map["J_SkuField_bprice-"+name];
                        if(J_SkuField_bprice==undefined){
                            J_SkuField_bprice="";
                        }
                        var J_SkuField_price=map["J_SkuField_price-"+name];
                        if(J_SkuField_price==undefined){
                            J_SkuField_price="";
                        }
                        var J_SkuField_quantity=map["J_SkuField_quantity-"+name];
                        if(J_SkuField_quantity==undefined){
                            J_SkuField_quantity="";
                        }
                        var J_MapProductid=map["J_SkuField_mcode-"+name];
                        if(J_MapProductid==undefined){
                            J_MapProductid="";
                        }
                        var J_BarProductid=map["J_SkuField_bcode-"+name];
                        if(J_BarProductid==undefined){
                            J_BarProductid="";
                        }
                        var J_SkuField_skuId=map["J_SkuField_skuId-"+name];
                        if(J_SkuField_skuId==undefined){
                            J_SkuField_skuId="";
                        }

                        var td0 = $("<td ><input maxlength='14' dataId=\"J_SkuField_bprice\" datename="+"J_SkuField_bprice-"+name+" name=\"Txt_PriceSon"+name+"\" class=\"l-text bigger-120 required-input price J_SkuField_bprice\" type=\"text\" value=\""+J_SkuField_bprice+"\" br></td>");
                        td0.appendTo(tr);
                        var td1 = $("<td ><input maxlength='14' dataId=\"J_SkuField_price\" datename="+"J_SkuField_price-"+name+" name=\"Txt_PriceSon"+name+"\" class=\"l-text bigger-120 required-input price J_SkuField_price\" type=\"text\" value=\""+J_SkuField_price+"\" br><span id=\"dis\" style='width: 30px; display: inline-block'><!--10</span>折--></td>");
                        td1.appendTo(tr);
                        var td2 = $("<td ><input maxlength='10' dataId=\"J_SkuField_quantity\" datename="+"J_SkuField_quantity-"+name+" name=\"Txt_CountSon\" class=\"l-text bigger-120 required-input quantity J_SkuField_quantity\" type=\"text\" value=\""+J_SkuField_quantity+"\"></td>");
                        td2.appendTo(tr);
                        var td3 = $("<td ><input maxlength='16' dataId=\"J_SkuField_mcode\" datename="+"J_SkuField_mcode-"+name+" name=\"Txt_CountSon\" class=\"l-text bigger-120\" type=\"text\" value=\""+J_MapProductid+"\"></td>");
                        td3.appendTo(tr);
                        var td4 = $("<td ><input maxlength='16' dataId=\"J_SkuField_bcode\" datename="+"J_SkuField_bcode-"+name+" name=\"Txt_CountSon\" class=\"l-text bigger-120\" type=\"text\" value=\""+J_BarProductid+"\"></td>");
                        td4.appendTo(tr);
                        var td5 = $("<input maxlength='16' dataId=\"J_SkuField_skuId\" datename="+"J_SkuField_skuId-"+name+" name=\"Txt_CountSon\" class=\"l-text bigger-120\" type=\"hidden\" value=\""+J_SkuField_skuId+"\">");
                        td5.appendTo(tr);
                        $("#simple-table :input").unbind().on({
                            'blur': function () {
                                map[$(this).attr("datename")]=$(this).val(); //缓存

                                if($(this).hasClass('required-input')){ //非空校验
                                    if($(this).val()==""){
                                        $(this).css({
                                            border:'1px solid #f2a696',
                                            background:'#FEF9F8'
                                        });
                                        $(this).attr('placeholder','不能为空！');
                                    }
                                }
                                if($(this).attr('dataId')=='J_SkuField_quantity'){ //非空校验

                                    //计算库存总数
                                    var count=0;
                                    for (var key in map) {
                                        if(key.startWith('J_SkuField_quantity')){
                                            count+=+map[key];
                                        }
                                    }
                                    $("#amount").val( count );
                                }
                                if($(this).attr('dataId')=='J_SkuField_bprice'){ //
                                    $(this).parent().next().find('input').val($(this).val());
                                }
                                if($(this).attr('dataId')=='J_SkuField_price'){ //
                                    var curV=$(this).val();

                                    var prev=$(this).parent().prev().find('input').val();
//                                    console.log("curV+" +parseInt(curV));
//                                    console.log("prev+" +parseInt(prev));
                                    if(prev==''||prev==null){
                                        $(this).parent().prev().find('input').val(curV);
                                    }else{
                                        if(parseInt(curV)>parseInt(prev)){
                                            $(this).val('');
                                            $(this).attr('placeholder','不能高出底价！');
                                        }else{
                                            /*var dis=((curV/prev)*10).toFixed(1);
                                            if(dis==10.0){
                                                //$(this).next().text('不打');
                                            }else{
                                                $(this).next().text(dis);
                                            }*/
                                        }

                                    }
                                }
                            },
                            "keyup":function(){
                                $(this).css({
                                    'border':"1px solid #D5D5D5",
                                    'background':'#fff'
                                });
                            }
                        })

                        td4.appendTo(tr);
                        $('.price').decimalinput(2);
                        $('.quantity').decimalinput(0);
                    });
                }

                //结束创建Table表
                arrayColumn.pop();//删除数组中最后一项
                //合并单元格
                $(table).mergeCell({
                    // 目前只有cols这么一个配置项, 用数组表示列的索引,从0开始
                    cols: arrayColumn
                });
            } else{
                //未全选中,清除表格
                //document.getElementById('.showRegion').innerHTML="";
                var _tableAll=$('.showRegion').children().remove();
            }
        },//合并行
        hebingFunction: function () {
            $.fn.mergeCell = function (options) {
                return this.each(function () {
                    var cols = options.cols;
                    for (var i = cols.length - 1; cols[i] != undefined; i--) {
                        // fixbug console调试
                        // console.debug(cols[i]);
                        mergeCell($(this), cols[i]);
                    }
                    dispose($(this));
                });
            };
            function mergeCell($table, colIndex) {
                $table.data('col-content', ''); // 存放单元格内容
                $table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
                $table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的), 默认一个"空"的jquery对象
                $table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数, 用于最后一行做特殊处理时进行判断之用
                // 进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
                $('tbody tr', $table).each(function (index) {
                    // td:eq中的colIndex即列索引
                    var $td = $('td:eq(' + colIndex + ')', this);
                    // 取出单元格的当前内容
                    var currentContent = $td.html();
                    // 第一次时走此分支
                    if ($table.data('col-content') == '') {
                        $table.data('col-content', currentContent);
                        $table.data('col-td', $td);
                    } else {
                        // 上一行与当前行内容相同
                        if ($table.data('col-content') == currentContent) {
                            // 上一行与当前行内容相同则col-rowspan累加, 保存新值
                            var rowspan = $table.data('col-rowspan') + 1;
                            $table.data('col-rowspan', rowspan);
                            // 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响
                            $td.hide();
                            // 最后一行的情况比较特殊一点
                            // 比如最后2行 td中的内容是一样的, 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
                            if (++index == $table.data('trNum'))
                                $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                        } else { // 上一行与当前行内容不同
                            // col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理
                            if ($table.data('col-rowspan') != 1) {
                                $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                            }
                            // 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan
                            $table.data('col-td', $td);
                            $table.data('col-content', $td.html());
                            $table.data('col-rowspan', 1);
                        }
                    }
                });
            }
            // 同样是个private函数 清理内存之用
            function dispose($table) {
                $table.removeData();
            }
        },
        //组合数组
        doExchange: function (doubleArrays) {
            var len = doubleArrays.length;
            if (len >= 2) {
                var arr1 = doubleArrays[0];
                var arr2 = doubleArrays[1];
                var len1 = doubleArrays[0].length;
                var len2 = doubleArrays[1].length;
                var newlen = len1 * len2;
                var temp = new Array(newlen);
                var index = 0;
                for (var i = 0; i < len1; i++) {
                    for (var j = 0; j < len2; j++) {
                        temp[index] = arr1[i] + "," + arr2[j];
                        index++;
                    }
                }
                var newArray = new Array(len - 1);
                newArray[0] = temp;
                if (len > 2) {
                    var _count = 1;
                    for (var i = 2; i < len; i++) {
                        newArray[_count] = doubleArrays[i];
                        _count++;
                    }
                }

                return w.step.doExchange(newArray);
            }
            else {
                return doubleArrays[0];
            }
        }
    }

    String.prototype.startWith=function(str){
        if(str==null||str==""||this.length==0||str.length>this.length)
            return false;
        if(this.substr(0,str.length)==str)
            return true;
        else
            return false;
        return true;
    },
    String.prototype.endWith=function(str){
            if(str==null||str==""||this.length==0||str.length>this.length)
                return false;
            if(this.substring(this.length-str.length)==str)
                return true;
            else
                return false;
            return true;
    }
})(jQuery);