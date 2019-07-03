
<title>500 Error Page</title>

<!-- ajax layout which only needs content area -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->

		<!-- #section:pages/error -->
		<div class="error-container">
			<div class="well">
				<h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="ace-icon fa fa-random"></i>
						错误
					</span>
					出现了一点问题
				</h1>

				<hr />
				<h3 class="lighter smaller">
					我们正在修复
					<i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
					它!
				</h3>

				<div class="space"></div>

				<div>
					<h4 class="lighter smaller">同时, 你可以这样做：</h4>

					<ul class="list-unstyled spaced inline bigger-110 margin-15">
						<li>
							<i class="ace-icon fa fa-hand-o-right blue"></i>
                            阅读帮助中心
						</li>

						<li>
							<i class="ace-icon fa fa-hand-o-right blue"></i>
                            给我们更多的信息关于这个具体的错误是如何发生的!
						</li>
					</ul>
				</div>

				<hr />
				<div class="space"></div>

				<div class="center">
					<a href="javascript:history.back()" class="btn btn-grey">
						<i class="ace-icon fa fa-arrow-left"></i>
						返回
					</a>

					<a href="${ctx}/html/login.manageIndex.do#report.UI.do" class="btn btn-primary">
						<i class="ace-icon fa fa-tachometer"></i>
                        回到首页
					</a>
				</div>
			</div>
		</div>

		<!-- /section:pages/error -->

		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
	var scripts = [null, null]
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	  //inline scripts related to this page
	});
</script>
