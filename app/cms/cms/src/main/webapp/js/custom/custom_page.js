//全局变量
dpTopSw = 0;//顶部下拉菜单开关（默认为关）

initWindow = function(){
	var tpBox = $('#dropdown-top .dropdown-box .tp-box');
	var ctntWth = $(".main-content").width();
	var lftWth = $(".coreL").width();
	var rghtWth = $(".coreR").width();
	$('#dropdown-top .dropdown-box ul').css({width:tpBox.length*300});//下拉模板box宽度
	$('#preview-section .custom-pages ul').css({width:$('#preview-section .custom-pages li').length*90+90});//页面栏box宽度
	$(".coreC").css({width:ctntWth - $(".coreL").width() - rghtWth});
	$("#preview-section .custom-pages").css({width:ctntWth - $(".coreL").width() - rghtWth});
	$(window).resize(function(){
		var ctntWth = $(".main-content").width();
		$("#link-selector").css({marginLeft:-$("#link-selector").width()/2});
		$(".coreC").css({width:ctntWth - $(".coreL").width() - rghtWth});
		$("#preview-section .custom-pages").css({width:ctntWth - $(".coreL").width() - rghtWth});
//		$('.main-content').height($(window).height()-46);
		$('.core').height($(window).height()-86);
	});


	$('#preview-section .phone-frame li,#preview-section .info-help').tooltip({ //预览区工具按钮提示气泡 
		'container': 'body',
		'template': '<div class="tooltip tooltip-default"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
	});


	//下拉窗口功能
	creatScrollX($('#dropdown-top .dropdown-box'));		//1号滚动条-下拉栏
	creatScrollY($('.coreL .widget-body'));		   		//2号滚动条-左栏
	creatScrollX($('#preview-section .custom-pages'));	//3号滚动条-底栏
	creatScrollY($('.coreR .widget-body'));				//4号滚动条-右栏
	creatScrollY($('#preview-section .phone-frame .screen'));//5号滚动条-手机
	$('#dropdown-top .dropdown-btn').click(function(){
		if (!dpTopSw) {
			dpBoxOn();
		} else{
			dpBoxOff();
		}
	});
	$('.tp-box .tp-options div').tooltip({ //下拉菜单按钮提示气泡  后期删除
		'container': 'body',
		'template': '<div class="tooltip tooltip-default"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
	});
	selectTooltips();
	$("#llooll-pop-test").click(function(){
		creatDialog($("#link-selector"));
	});
	$(".comp-box .input-group button").click(function(){
		creatDialog($("#link-selector"));
	});
	$(".dialog-mask,#dialogTab .close-dialog").click(function(){
		hideDialog($("#link-selector"));
	});

	//链接选择窗搜索框功能
	$(".link-search .nav-search-input").on("input",function(){
		if($(this).val().length){
			$(".link-search .clear-btn").fadeIn(200);
			$(".link-search .btn-info").css({display:"inline-block"});
		}else{
			$(".link-search .clear-btn").fadeOut(200);
			$(".link-search .btn-info").css({display:"none"});
		}
	});

	//链接选择窗搜索框清除功能
	$(".link-search .clear-btn").click(function(){
		$(".link-search .nav-search-input").val('');
		$(".link-search .clear-btn").fadeOut(200);
		$(".link-search .btn-info").css({display:"none"});
	});

	//保存按钮 触发加载动画
	//$('.comp-box.submit-btn').click(function(){pageLoading();});

	//预览页面-二维码
	$("#preview-section .phone-frame ul .fa-qrcode").closest("li").click(function(){
		showQRcode('extra plugins/img/qr-sample.png');
	});

	//删除页面-（工具栏）
	$("#preview-section .phone-frame ul .fa-trash-o").closest("li").click(function(){
		$(".custom-pages .active .del-page").trigger("click");
	});

	//页面-设置
	$("#preview-section .phone-frame ul .fa-cog").closest("li").click(function(){
		$(".page-config").show();
		$(".item-config").hide();
	});

	//清空页面-（工具栏）
	$("#preview-section .phone-frame ul .fa-eraser").closest("li").click(function(){
		var $li = $(".custom-pages .active");
		myConfirm("确定要清空‘"+$li.find("p").text()+"'吗？",function(){
			pageLoadingShow();
			ace.post("page.clearItem.do",{id:$li.attr("data-id")},function(){
				$(".custom_page_item_content").empty();
				pageLoadingHide();
			});
		});
	});

	//复制页面-（工具栏）
	$("#preview-section .phone-frame ul .fa-copy").closest("li").click(function(){
		var $li = $(".custom-pages .active");
		ace.post("page.copyPage.do",{id:$li.attr("data-id")},function(res){
			ace.util.jumpTo("page.index.do?id="+res.data);
		});
	});

	/*//删除页面
	$(".custom-pages .page-wrapper li .fa-times").closest("span").click(function(){
		confirmDialog($(this));
	});*/

	//页面切换
	//pageSwitch();
	/*$(".custom-pages .page-wrapper ul .add-btn").click(function(){
		var pageInfo = {"pageId":"3321","pageName":"某页面" }
		newPage(pageInfo);
	})*/


	//clearImg();//清除照片，调试用
	toolsFunc("extra plugins/img/qr-sample.png");//下拉栏按钮功能，调试用
};

function pageLoadingShow(){
	$(".core .loading-mask").removeClass('hide').fadeIn(150);
}

function pageLoadingHide(){
	$(".core .loading-mask").fadeOut(200);
}



function creatScrollX($obj){
	$obj.mCustomScrollbar({
    	axis:"x", // horizontal scrollbar
    	scrollInertia:250,
    	scrollButtons:{
			enable:true,
			scrollType:"continuous",
			scrollSpeed:20,
			scrollAmount:40
		},
	});
}
function creatScrollY($obj){
	$obj.mCustomScrollbar({
    	axis:"y", // horizontal scrollbar
    	scrollInertia:250,
    	scrollButtons:{
			enable:true,
			scrollType:"continuous",
			scrollSpeed:20,
			scrollAmount:40
		},
	});
}
function destroyScroll($obj){
	$obj.mCustomScrollbar("destroy");
}

function dpBoxOn() {
	var $dpBox = $('#dropdown-top .dropdown-box');
	$('#dropdown-top .dropdown-btn i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
	$('#dropdown-top .dropdown-btn').animate({top: 300}, 250);
	$('.dp-mask').fadeIn(250);
	$(".alert-dialog").fadeOut(250);
	$dpBox.animate({
		top: "0px"
	}, 250, function() {
		dpTopSw = 1;
		$(this).mCustomScrollbar("update");
	});
}

function dpBoxOff() {
	var $dpBox = $('#dropdown-top .dropdown-box');
	$('#dropdown-top .dropdown-btn i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
	$('#dropdown-top .dropdown-btn').animate({top: 0}, 250);
	$('.dp-mask').fadeOut(250);
	$('#dropdown-top .dropdown-box').animate({
		top: "-300px"
	}, 250, function() {
		dpTopSw = 0;
	})
}




/*function creatDialog($obj){
	var sdwth = $(".sidebar").width();
	var ctnHt = $(".core").height();
	var dlgHt = $("#link-selector").height();
	$(".dialog-mask").fadeIn();
	$obj.css({marginLeft:-$obj.width()/2});
	$obj.show(1).animate({opacity:1,top:(ctnHt-dlgHt)/2},250);
}*/
function hideDialog($obj){
	$(".dialog-mask").fadeOut(250);
	$obj.animate({opacity:0,top:0},250,function(){$obj.hide(1)});
}
function creatTemlate() {
	$("#dropdown-top .dropdown-box ul").prepend('<li data-id="1123334456" ><div class="tp-box"><p>2016新春</p><img src="extra plugins/img/01.jpg" alt="dasdasdsda" /><div class="tp-options"><div class="tp-use" data-original-title="Default" data-rel="tooltip" data-placement="bottom" title="应用"><i class="fa fa-check"></i></div><div class="tp-preview" data-original-title="Default"  data-rel="tooltip" data-placement="bottom" title="预览"><i class="fa fa-search-plus"></i></div></div></div></li>')
	$('.tp-box .tp-options div').tooltip({ //下拉菜单按钮提示气泡
		'container': 'body',
		'template': '<div class="tooltip tooltip-default"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
	});
}

function toolsFunc(imgUrl) {
	$('.tp-box .tp-options .tp-use').unbind('click').click(function(){
		alert('haha!')
		dpBoxOff();
	});
	$('.tp-box .tp-options .tp-preview').unbind('click').click(function(){
		dpBoxOff();
		setTimeout(function(){showQRcode(imgUrl)},300)
	});

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


function showQRcode(imgUrl){
	$(".alert-dialog .qrcode-box img").remove();
	$(".alert-dialog").fadeIn(250);
	$(".alert-dialog .close-btn").unbind("click").click(function(){
		$(".alert-dialog").fadeOut(250);
	});
}


/*function confirmDialog($obj) {
	if(confirm('删除后不可恢复，是否确认？')){
		pageDelete($obj);
	};
}*/

/*function pageDelete($obj){
	var actPage = $obj.closest('li').hasClass('active');
	if(actPage){
		$('.custom-pages .page-wrapper li:first-child').addClass('active');
		$('#preview-section .custom-pages').mCustomScrollbar("scrollTo","left");
	}
	$obj.closest('li').remove();
}*/
/*function pageDeleteTool(){
	$('.custom-pages .page-wrapper ul .active').remove();
	$('.custom-pages .page-wrapper li:first-child').addClass('active');
}*/

/*function newPage(pageInfo){
	$('.custom-pages .page-wrapper ul li:last-child').before('<li data-id="'+pageInfo.pageId+'"><p>'+pageInfo.pageName+'</p><div><span> <i class="fa fa-wrench"></i> </span><i class="fa fa-file-text-o fa-2x"></i><span> <i class="fa fa-times"></i> </span></div></li>');
	$('#preview-section .custom-pages ul').css({width:$('#preview-section .custom-pages ul').width()+90});//页面栏box宽度
	$('#preview-section .custom-pages').mCustomScrollbar("update");
	$(".custom-pages .page-wrapper li .fa-times").closest("span").unbind('click').click(function(){
		confirmDialog($(this));
	});
	pageSwitch();
}*/

/*function pageSwitch(){
	$('.custom-pages .page-wrapper li div span:last-child').click(function(){
		return false;
	});
	$('.custom-pages .page-wrapper li').not('.add-btn').unbind('click').click(function() {
		$('.custom-pages .page-wrapper li').removeClass('active');
		$(this).addClass('active');
	});
}*/

/*
function clearImg(){
	$('.comp-box.single-pic .img-frame .mask .clear-btn').unbind('click').click(function(){
		$(this).closest('.comp-box').addClass('empty');
		$(this).closest('.img-frame').empty().append('<p>点击选择图片</p><i class="fa ace-icon fa-picture-o fa-4x"></i>');
	});
}*/




