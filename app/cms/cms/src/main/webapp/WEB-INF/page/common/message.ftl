
<script type="text/javascript">
    var scripts = [null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function() {
        alertErr('${message}',1000*60);
    });
</script>