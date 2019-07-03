KindEditor.plugin('myImage', function(K) {
    var editor = this, name = 'myImage';
    // 点击图标时执行
    editor.clickToolbar(name, function() {
        $.colorbox(editor.imageSetting);
        window.editorImage = function(imgs){
            $.each(imgs,function(){
                editor.insertHtml('<img src="'+this.url+'" title="'+this.title+'" />');
            })
            //
            $.fn.colorbox.close();

        }
    });
});