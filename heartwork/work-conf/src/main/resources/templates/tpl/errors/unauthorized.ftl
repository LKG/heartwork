<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>错误结果页面</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
<!------head 引用信息 begin----->
<#include "/includes/head.ftl" />
<!------head   引用信息 end----->
</head>
<body>
  <!--[if lte IE 8]>
  <p class="browsehappy">你正在使用<strong>过时</strong>的浏览器， 暂不支持。 请 <a
	  href="http://browsehappy.com/" target="_blank">升级浏览器</a>
	  以获得更好的体验！</p>
  <![endif]-->
<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<div class="clearfix"></div>
    <div class="container main-container" tyle="min-height:500px;" >
    	<div class="row">
    	<div class="col-xs-12">
		  <!--用户面板 begin--->
		  	<div class="panel panel-default user-panel">
			  <div class="panel-body">
			    <div class="alert alert-warning" role="alert">
			  		<h2 class="am-text-center am-text-xxxl am-margin-top-lg">无效访问</h2>
			        <p class="am-text-center">用户未登录或者无访问权限，请尝试重新登录</p>
				</div>
				 <pre class="page-unauthorized">
					          .----.
					       _.'__    `.
					   .--($)($$)---/#\
					 .' @          /###\
					 :         ,   #####
					  `-..__.-' _.-\###/
					        `;_:    `"'
					      .'"""""`.
					     /,  ya ,\\
					    //  401!  \\
					    `-._______.-'
					    ___`. | .'___
					   (______|______)
				</pre>
				
			  </div>
			  <div class="panel-footer">
			  	
			  </div>
			</div>
		  <!--用户面板 end--->
		  
		  </div>
    	</div>
    </div>
<!------页面header信息 end----->
<!------页面版权信息 begin----->
<!------页面版权信息 end----->
</body>
