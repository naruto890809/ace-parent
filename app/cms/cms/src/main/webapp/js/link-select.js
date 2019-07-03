
$(function(){
	$(window).resize(function(){
		$("#link-selector").css({marginLeft:-$("#link-selector").width()/2});
	});
	selectTooltips();
	$(".dialog-mask,#dialogTab .close-dialog").click(function(){
		hideDialog($("#link-selector"));
	});
	//链接选择窗搜索框功能
	$(".link-search .nav-search-input").on("input",function(){
		var $search = $(this).closest(".link-search");
		if($(this).val().length){
			$search.find(".clear-btn").fadeIn(200);
			$search.find(".btn-info").css({display:"inline-block"});
		}else{
			$search.find(".clear-btn").fadeOut(200);
			$search.find(".btn-info").css({display:"none"});
		}
	});

	//链接选择窗搜索框清除功能
	$(".link-search .clear-btn").click(function(){
		clearSearch($(this).closest(".link-search"));
	});

	$("#link-selector").delegate(".link-ok-btn","click",function(){
		$(".link-input-cur").val($(this).attr("data-link")).trigger("change");
		hideDialog($("#link-selector"));
	});

	$("#dialogTab li").click(function(){
		if($(this).attr("data-init")!="true"){
			var type = $(this).attr("data-type");
			if(type){
				try{
					var fun = eval(type+"_init");
					if(typeof fun == 'function'){
						fun($(this));
					}
				}catch(e){
				}
			}
		}
	});

});

function clearSearch($search) {
	$search.find(".nav-search-input").val('');
	$search.find(".clear-btn").fadeOut(200);
	$search.find(".btn-info").css({display: "none"});
}

function creatDialog($obj){
	var sdwth = $(".sidebar").width();
	var ctnHt = $(window).height();
	var dlgHt = $("#link-selector").height();
	$(".dialog-mask").fadeIn();
	$obj.css({marginLeft:-$obj.width()/2});
	$obj.show(1).animate({opacity:1,top:(ctnHt-dlgHt)/2},250);
}
function hideDialog($obj){
	$(".dialog-mask").fadeOut(250);
	$obj.animate({opacity:0,top:0},250,function(){$obj.hide(1)}); 
}

function selectTooltips(){
	$('.tab-content select').change(function(){
		var sltCtn = $(this).find("option:selected").text();
		$(this).attr("title",sltCtn);
//		$(this).tooltip({ //弹窗分类选择器提示气泡
// 			'container': '.tab-content',
// 			'template': '<div class="tooltip tooltip-default"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
//		})
	});

}
/*------------商品选择---------------------------*/
function products_init($li){
	$li.attr("data-init","true");
	loadProducts(1);
	$("#products-link .search-btn,.clear-btn").click(function(){
		$("#products-link .category-selector:first").val(0).nextAll(".category-selector").remove();
		loadProducts(1);
	});

	ace.post("goodsType.children.do", $.param({'typePid':0,'typeVisible':1}),function(res){
		var $select = $("#products-link .category-selector").change(products_type_change);
		$.each(res.rows,function(){
			$("<option value='"+this.typeId+"' data-leaf='"+this.typeLeaf+"'>"+this.typeName+"</option>").appendTo($select);
		});
	},function(res){
		if(res.status!=ace.status.SUCCESS){
			$li.removeAttr("data-init");
		}
	});
}

function products_type_change(){
	$(this).nextAll(".category-selector").remove();

	if(this.value==""){
		return;
	}
	var $o = $(this);
	if(this.value=="0"||$o.find("option:selected").attr("data-leaf")=="1"){
		clearSearch($("#products-link .link-search"));
		loadProducts(1);
		return;
	}
	ace.post("goodsType.children.do",$.param({'typePid':this.value,'typeVisible':1}),function(res){
		var $select = $("<select class='category-selector'></select>").change(products_type_change);
		$("<option value=''>未选择</option>").appendTo($select);
		$o.after($select);
		$.each(res.rows,function(){
			$("<option value='"+this.typeId+"' data-leaf='"+this.typeLeaf+"'>"+this.typeName+"</option>").appendTo($select);
		});
	});
}

function loadProducts(page){
	var searchVal = $.trim($("#products-link").find(".nav-search-input").val());
	var typeId = $("#products-link .category-selector:last").val();
	if(typeId=="0"){
		typeId = ""
	}
	ace.post("goods.search.do", $.param({'goodsSpu.typeId':typeId,search:searchVal,'Page.pageNo':page,'Page.pageSize':5}) ,function(res){
		fillProductsTable(res);
	},function(res){
		if(res.status!=ace.status.SUCCESS){
			$("#dialogTab li[data-type='products']").removeAttr("data-init");
		}
	})
}
function fillProductsTable(res){
	var $ul = $("#products-list").empty();
	$.each(res.rows, function (index) {
		var $li = $('<li><div class="item-box"> <img src="'+this.viewImg+'"/><div class="item-name" title="'+this.title+'">'+this.title+'</div><div class="item-num" title="编号:'+this.barCode+'">编号:'+this.barCode+'</div> <div class="item-price" title="￥'+this.sellprice+'">￥'+this.sellprice+'</div> <div class="item-choose btn btn-sm btn-primary link-ok-btn" data-link="{tile:\''+this.title+'\',type:\'GOODS\',id:\''+this.spuId+'\'}"> 选择 </div></div> </li>');
		$ul.append($li);
	})
	createPagination($("#products-link"),res,loadProducts);
}
/*------------项目选择---------------------------*/
function service_init($li){
	$li.attr("data-init","true");
	loadServices(1);
	$("#service-link .search-btn,.clear-btn").click(function(){
		$("#service-link .category-selector:first").val(0).nextAll(".category-selector").remove()
		loadServices(1);
	});

	ace.post("type.children.do", $.param({'typePid':0,'typeVisible':1}),function(res){
		var $select = $("#service-link .category-selector").change(service_type_change);
		$.each(res.rows,function(){
			$("<option value='"+this.typeId+"' data-leaf='"+this.typeLeaf+"'>"+this.typeName+"</option>").appendTo($select);
		});
	},function(res){
		if(res.status!=ace.status.SUCCESS){
			$("#dialogTab li[data-type='service']").removeAttr("data-init");
		}
	});
}
function service_type_change(){
	$(this).nextAll(".category-selector").remove();

	if(this.value==""){
		return;
	}
	var $o = $(this);
	if(this.value=="0"||$o.find("option:selected").attr("data-leaf")=="1"){
		clearSearch($("#service-link .link-search"));
		loadServices(1);
		return;
	}
	ace.post("type.children.do",$.param({'typePid':this.value,'typeVisible':1}),function(res){
		var $select = $("<select class='category-selector'></select>").change(service_type_change);
		$("<option value=''>未选择</option>").appendTo($select);
		$o.after($select);
		$.each(res.rows,function(){
			$("<option value='"+this.typeId+"' data-leaf='"+this.typeLeaf+"'>"+this.typeName+"</option>").appendTo($select);
		});
	});
}
function loadServices(page){
	var searchVal = $.trim($("#service-link").find(".nav-search-input").val());
	var typeId = $("#service-link .category-selector:last").val();
	if(typeId=="0"){
		typeId = "";
	}
	ace.post("pro.search.do", $.param({'pro.typeId':typeId,search:searchVal,'Page.pageNo':page,'Page.pageSize':5}) ,function(res){
		fillServices(res);
	},function(res){
		if(res.status!=ace.status.SUCCESS){
			$("#dialogTab li[data-type='service']").removeAttr("data-init");
		}
	})
}
function fillServices(res){
	var $ul = $("#service-list").empty();
	$.each(res.rows, function (index) {
		var $li = $('<li><div class="item-box"> <img src="'+this.viewImg+'"/><div class="item-name" title="'+this.proName+'">'+this.proName+'</div><div class="item-num" title="编号:'+this.proCode+'">编号:'+this.proCode+'</div> <div class="item-price" title="￥'+this.sellprice+'">￥'+this.sellprice+'</div> <div class="item-choose btn btn-sm btn-primary link-ok-btn" data-link="{title:\''+this.proName+'\',type:\'PRO\',id:\''+this.proId+'\'}"> 选择 </div></div> </li>');
		$ul.append($li);
	})
	createPagination($("#service-link"),res,loadServices);
}
/*---------------活动选择------------------*/

function activity_init($li){
	$li.attr("data-init","true");
	loadActivity(1);
	$("#activity-link .search-btn,.clear-btn").click(function(){
		loadActivity(1);
	});
}
function loadActivity(page){
	var searchVal = $.trim($("#activity-link").find(".nav-search-input").val());
	ace.post("activity.search.do", $.param({search:searchVal,'Page.pageNo':page,'Page.pageSize':5}) ,function(res){
		fillActivity(res);
	},function(res){
		if(res.status!=ace.status.SUCCESS){
			$("#dialogTab li[data-type='activity']").removeAttr("data-init");
		}
	})
}
function fillActivity(res){
	var $ul = $("#activity-list").empty();
	$.each(res.rows, function (index) {
		var $li = $('<li><div class="item-box"><i class="fa fa-file-text-o"></i><div class="item-name" title="'+this.activityTitle+'">'+this.activityTitle+'</div> <div class="upd-time">'+this.createTimeText+'</div> <div class="item-choose btn btn-sm btn-primary link-ok-btn" data-link="{title:\''+this.activityTitle+'\',type:\''+this.activityType+'\',id:\''+this.activityId+'\'}"> 选择 </div> </div></li>');
		$ul.append($li);
	})
	createPagination($("#activity-link"),res,loadActivity);
}

/*----------我的页面--------*/
function customPage_init($li){
	$li.attr("data-init","true");
	var $ul = $("#customPage-list").empty();
	ace.post("page.listPages.do",null,function(res){
		$.each(res.data, function (index) {
			var $li = $('<li><div class="item-box-sq link-ok-btn" data-link="{title:\''+this.pageName+'\',type:\''+this.pageType+'\',id:\''+this.customPageId+'\'}"><div class="content" title="'+this.pageName+'">'+this.pageName+'</div></div></li>');
			$ul.append($li);
		})
	},function(res){
		if(res.status!=ace.status.SUCCESS){
			$li.removeAttr("data-init");
		}
	});
}



/*-------------分页创建-------------------*/
function createPagination($parent,res,callback){
	var $pagination = $parent.find(".pagination").empty();
	var $prev = $('<li class="prev" data-page="1"><a href="javascript:void(0);"><i class="ace-icon fa fa-angle-double-left"></i></a></li>').appendTo($pagination);
	if (res.pageNo == 1) $prev.addClass("disabled");
	$.each(createPageArr(res.pageNo, res.totalPages), function () {
		var $pitem = $('<li data-page="' + this + '"><a href="javascript:void(0);">' + this + '</a></li>').appendTo($pagination);
		if (this == res.pageNo) $pitem.addClass("disabled");
	});
	var $next = $('<li class="next" data-page="' + res.totalPages + '"><a href="javascript:void(0);"><i class="ace-icon fa fa-angle-double-right"></i></a></li>').appendTo($pagination);
	if (res.pageNo == res.totalPages) $next.addClass("disabled");
	$pagination.find("li").not(".disabled").unbind("click").bind("click", function () {
		callback($(this).attr("data-page"));
	});
}

createPageArr = function (page, pageCount) {
	var startPage = 1;
	var endPage = pageCount?pageCount:startPage;
	var o = this;
	var maxPageNum = 5;
    if (pageCount > maxPageNum) {
		startPage = page - maxPageNum / 2 + 1;
		endPage = page + maxPageNum / 2;
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












