jsCubeMob = function($parent,option){
    var o =  this;
    o.option = option;
    o.$table = $("<table><tbody></tbody></table>");
    o.$parent = $parent;
    o.$parent.append(o.$table);
    for(var i=0;i<o.option.realRow;i++){
        var $tr = $("<tr data-row='"+i+"'></tr>").appendTo(o.$table);
        for(var j=0;j<o.option.col;j++){
            $tr.append($('<td data-row="'+i+'" data-col="'+j+'" class="cube_empty_mobile"></td>'));
        }
    }
    o.createTable(option);
    var w = 280000/ o.option.col/1000;
    var $frame = $(".phone-frame .screen");
    if($frame.length==0){
        w = $(window).width()*1000/option.col/1000;
    }

    //减去间距
    try{
        if(o.option.pleft){
            w = w- parseInt(o.option.pleft);
            o.$parent.css("padding-left", o.option.pleft+"px");
        }
        if(o.option.pright){
            w = w- parseInt(o.option.pright);
            o.$parent.css("padding-right", o.option.pright+"px");
        }
        if(o.option.ptop){
            o.$parent.css("padding-top", o.option.ptop+"px");
        } if(o.option.pbottom){
            o.$parent.css("padding-bottom", o.option.pbottom+"px");
        }
    }catch(e){
        console.log(e);
    }

    o.$table.height(o.option.realRow*w);
    o.$table.width(o.option.col*w);
    o.$table.find("td").height(w).width(w).css("background-size","cover");
}

jsCubeMob.prototype.block = function(row,col,width,height){
    var o = this;
    var $td = o.$table.find("td[data-row='"+row+"'][data-col='"+col+"']").removeClass("cube_empty_mobile").addClass("cube-block-mob");
    $td.attr("colspan",width);
    $td.attr("rowspan",height);
    for(var i=0;i<width;i++){
        for(var j=0;j<height;j++){
            if(i!=0||j!=0){
                var $td = o.$table.find(".cube_empty_mobile[data-row='"+(row+j)+"'][data-col='"+(col+i)+"']");
                $td.remove();
            }
        }
    }
}
jsCubeMob.prototype.createTable = function(option){
    var o = this;
    $.each(option.table,function(){
        o.block(this.row,this.col,this.width,this.height);
        var $block = o.$table.find((".cube-block-mob[data-row='"+this.row+"'][data-col='"+this.col+"']")).css("background-image","url("+this.pic+")");
        if(this.link){
            $block.click(function(){
                try{
                    jumpToLink(this.link);
                }catch(e){

                }
            });
        }
    });
}
