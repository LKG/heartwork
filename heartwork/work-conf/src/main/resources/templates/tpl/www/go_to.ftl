<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
<!------head 引用信息 begin----->
<#include "/includes/head.ftl" />
<!------head   引用信息 end----->
<head>
<title>页面跳转提示</title>
</head> 
<body class="page-header-fixed">
	<#include "/pages/header-navbar-top.ftl" />
<div class="container-fluid" id="main-add-container" style="padding:0 0px">
	<div class="panel panel-default">
	  <div class="panel-body center-block">
		<i class="fa fa-check-circle"></i>页面正在跳转中.. <a  id="goto-url"  href="${url}">${url}</a>,<i class="fa fa-time"></i><code id="goto-time" >5</code>秒后自动跳转
	  </div>
	</div>
</div>
 <!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<script>
 seajs.use(['$', 'artDialog'], function($,artDialog){
 	var i=5;
	var interval=setInterval(function(){
		i--;
		if(i<=0){
			var url=$("#goto-url").attr("href");
			if(url.indexOf("http")<=0){
				url="http://"+url;
			}
			clearInterval(interval); 
			window.location.href = url	;
	 	}
	 	$("#goto-time").html(i);
	},1000);
  });
</script>
<!------seajs.config   引用信息 end----->
</html>