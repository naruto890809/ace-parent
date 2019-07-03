ueditorSetting = {
    simple:{
        elementPathEnabled:false,
        toolbars:[[
            'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'strikethrough', '|', 'superscript', 'subscript', '|','forecolor', 'backcolor', '|', 'removeformat', '|',
            'insertorderedlist', 'insertunorderedlist', '|','selectall', 'cleardoc', 'paragraph', '|', 'fontfamily','fontsize',
            '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify','|',
            'link', 'unlink', '|', 'emotion', 'insertvideo', '|','map',
            '|','horizontal', 'print', 'preview', 'drafts','|','fullscreen'
        ]],
        simple:true
    },
    full:{
        elementPathEnabled:false,
        toolbars: [[
            'fullscreen', 'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'emotion', 'insertvideo', 'music', 'map', 'insertframe', 'insertcode', 'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'drafts','|'
        ]]
    },
    type:[]
}

UE.registerUI("myImage",function(editor, uiName){
    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name: uiName,
        //提示
        title: "图片选择",
        //添加额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules: 'background-position: -380px 0;',
        //点击时执行的命令
        onclick: function() {
            $.colorbox(window.editorImgSetting);
            window.editorImage = function(imgs){
                $.each(imgs,function(){
                    editor.execCommand('insertHtml', '<img src="'+this.url+'" title="'+this.title+'" />')
                })
                $.fn.colorbox.close();

            }
        }
    });
    return btn;
});
UE.registerUI("exchange",function(editor, uiName){
    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name: uiName,
        //提示
        title: "切换编辑器",
        //添加额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules: 'background-position: -501px -20px;',
        //点击时执行的命令
        onclick: function() {
            var id = editor.key;
            var isFullScreen = editor.ui.isFullScreen();
            if(isFullScreen){
                editor.ui.setFullScreen(false);
            }
            editor.destroy();
            if(!ueditorSetting.type[id]){
                ueditorSetting.full.fullscreen = isFullScreen;
                UE.getEditor(id,ueditorSetting.full);
                ueditorSetting.type[id] = true;
            }else{
                ueditorSetting.simple.fullscreen = isFullScreen;
               UE.getEditor(id,ueditorSetting.simple);
                ueditorSetting.type[id] = false;
            }
        }
    });
    setTimeout(function(){
        $(".edui-default .edui-toolbar .edui-for-exchange").css("float","right");
    },300);



    return btn;
});
