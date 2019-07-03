$(function(){
    var tp;
    $(".batch-opts .js-batch-type a").click(function(){
		tp = $(this).text();
		$(".batch-opts .editor input").attr("placeholder","批量设置" + tp);
		$(".batch-opts .batch-title").text("设置" + tp + "：");
        if(tp=="原价" || tp=="售价"){
            $(".batch-opts .editor .setting").decimalinput(2);
        }else{
            $(".batch-opts .editor .setting").decimalinput(0);
        }

		$(".batch-opts .editor").fadeIn(200);
	});
	$(".batch-opts .editor .ok").click(function(){
		$(".batch-opts .editor input").attr("placeholder","");
		$(".batch-opts .batch-title").text("批量设置：");
		$(".batch-opts .editor").fadeOut(200);
        if(tp=="原价"){
            var setting=$(".batch-opts .editor .setting").val();
            $('.J_SkuField_bprice').val(setting);
            $('.batch-opts .editor .setting-bprice').val(setting); //设置备份

            $(".batch-opts .editor .setting").val("");
        }else if(tp=="售价"){
            var settiing=$(".batch-opts .editor .setting").val();
            var settiingb=$(".batch-opts .editor .setting-bprice").val();
            if(settiing>settiingb){
                alertErr("售价不能高于原价！")
                return ;
            }
            $('.J_SkuField_price').val(settiing);

            $(".batch-opts .editor .setting").val("");
        }else if(tp=="库存"){


            var text= $(".batch-opts .editor .setting").val();
            var total=parseInt(text)*$('.J_SkuField_quantity').length;
            $('.amount').val(total); //设置库存

            $('.J_SkuField_quantity').val(text);
            $(".batch-opts .editor .setting").val("");
        }
		 //*************设置的js放在这，替换掉alert*****************//
	});
	$(".batch-opts .editor .cancel").click(function(){
		$(".batch-opts .editor input").attr("placeholder","");
		$(".batch-opts .batch-title").text("批量设置：");
		$(".batch-opts .editor").fadeOut(200);
	});
});
