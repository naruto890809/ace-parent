jsCube = function (content, option) {
    if (content && !option && !content.table) {
        option = content;
        content = null;
    }
    var o = this;
    o.option = {
        row: 4,
        col: 4,
        parentSelector: ".cube-controls",
        onClick:function(){
        },
        onEmptyClick:function(){
            $('.cube-block-selector .selected').removeClass("selected");
            $('.cube-block-selector .modal-body li').addClass("used-block");
            var col = parseInt($(this).attr("data-col"));
            var row =  parseInt($(this).attr("data-row"));
            o.curCol = col;
            o.curRow = row;
            var cnt = 0
            var maxRow = o.option.row;
            var maxCol = o.option.col;
            for(var i=row;i< maxRow;i++){
                for(var j=col;j< maxCol;j++){
                    if(o.$table.find("td[data-row='"+i+"'][data-col='"+j+"']").hasClass("cube_empty")){
                        $(".used-block[data-rows='"+i+"'][data-cols='"+j+"']").removeClass("used-block");
                        cnt++;
                    }else{
                        maxCol = j;
                        break;
                    }
                }
                if(!o.$table.find("td[data-row='"+i+"'][data-col='"+col+"']").hasClass("cube_empty")){
                    break;
                }
            }
            if(cnt==1){
                o.block(row,col,1,1);
                return;
            }
            $('.cube-block-selector').show();
            return false;
        },
        onRemove:function(){
        }
    }
    $.extend(o.option, option);
    if(content){
        if(content.row){
            o.option.row = content.row;
        }
        if(content.col){
            o.option.col = content.col;
        }
    }

    o.$parent = $(o.option.parentSelector).empty();
    o.$table = $("<table><tbody></tbody></table>");
    o.$parent.append(o.$table);
    for(var i=0;i<o.option.row;i++){
        var $tr = $("<tr data-row='"+i+"'></tr>").appendTo(o.$table);
        for(var j=0;j<o.option.col;j++){
            $tr.append($('<td data-row="'+i+'" data-col="'+j+'" class="cube_empty"></td>'));
        }
    }
    o.$table.after($('<p class="help-desc">点击 + 号添加内容</p>'));
    o.blockCount = 0;
    o.$table.width(o.option.col*82);
    o.$table.height(o.option.row*82);
    o.$table.delegate(".cube_empty","click", o.option.onEmptyClick);
    o.data = {};

    if(content){
        o.createTable(content);
    }
    return o;
}
jsCube.prototype.block = function(row,col,width,height){
    var o = this;
    o.blockCount++;
    o.setDate(row,col,{row:row,col:col,width:width,height:height});
    var $td = o.$table.find("td[data-row='"+row+"'][data-col='"+col+"']").removeClass("cube_empty").addClass("cube-block").append($("<span>"+width+"X"+height+"</span>"));
    $td.attr("colspan",width);
    $td.attr("rowspan",height);
    for(var i=0;i<width;i++){
        for(var j=0;j<height;j++){
            if(i!=0||j!=0){
                var $td = o.$table.find(".cube_empty[data-row='"+(row+j)+"'][data-col='"+(col+i)+"']");
                $td.remove();
            }
        }
    }
    o.recolor();
}
jsCube.prototype.removeBlock = function(row,col){
    if(typeof row  == "string"){
        row = parseInt(row);
    }
    if(typeof col  == "string"){
        col = parseInt(col);
    }

    var o = this;
    o.removeDate(row,col);

    var $td = o.$table.find("td[data-row='"+row+"'][data-col='"+col+"']");
    var width = parseInt($td.attr("colspan"));
    var height = parseInt($td.attr("rowspan"));

    $td.attr("colspan",1);
    $td.attr("rowspan",1);
    $td.attr("class","cube_empty").empty();
    o.blockCount = $(".cube-block").length;


    var $curTr = $td.parent();
    var $curTd = $td;
    for(var i=0;i<height;i++){
        //var $curTds = $("tr[data-row='"+(row+i)+"']");
        var $tr = o.$table.find("tr[data-row='"+(row+i)+"']");
        $curTr = $tr;
        var curCol = col;
        do{
            $curTd = $tr.find("td[data-col='"+(--curCol)+"']");
        }while(curCol>0&&$curTd.length==0);
        for(var j=0;j<width;j++){
            if(i!=0||j!=0){
                var $tempTd = $('<td data-row="'+(row+i)+'" data-col="'+(col+j)+'" class="cube_empty"></td>');
                if($curTd.length>0){
                    $curTd.after($tempTd);
                }else{
                    $curTr.prepend($tempTd);
                }
                $curTd = $tempTd;
            }else{
                $curTd = $td;
            }
        }
    }
    o.recolor();
}
jsCube.prototype.createTable = function(content){
    var o = this;
    $.each(content.table,function(){
        o.block(this.row,this.col,this.width,this.height);
        o.setDate(this.row,this.col,this);
        if(this.pic){
            o.$table.find((".cube-block[data-row='"+this.row+"'][data-col='"+this.col+"']")).css("background-image","url("+this.pic+")");
        }else{
            o.$table.find((".cube-block[data-row='"+this.row+"'][data-col='"+this.col+"']")).removeAttr("style");
        }

    });
}
jsCube.prototype.recolor = function(){
    $(".cube-block").each(function(index){
        $(this).attr("class","cube-block cube-block-index-"+(index+1));
    });
}
jsCube.prototype.getDate = function(row,col){
    return this.data[row+"-"+col];
}
jsCube.prototype.setDate = function(row,col,val){
    return this.data[row+"-"+col] = val;
}
jsCube.prototype.removeDate = function(row,col){
     delete this.data[row+"-"+col];
}