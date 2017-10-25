<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>极致保障</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<#assign template="helps"/>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
	<div class="container" id="main-container">
		<div class="row">
			<div class="col-xs-3">
				<div class="panel panel-default" id="user-info-panel" >
			  <div class="panel-heading" id="user_<@shiro.principal property='userId' />">
			  	
			  </div>
			  <div class="panel-body">
			   	
			  </div>
			  <div class="list-group" id="user-info-setting" >
			     <a href="${contextPath}/index/agreements.html" class="list-group-item active">服务协议</a>
				 <a href="${contextPath}/index/questions.html" class="list-group-item">常见问题</a>
				 <!--
				  <a href="${contextPath}/index/partners.html" class="list-group-item">合作伙伴</a>
				  --->
				 <a href="${contextPath}/index/aboutus.html" class="list-group-item">联系我们</a>
				 <a href="${contextPath}/index/aboutus.html" class="list-group-item">关于我们</a>
			  </div>
			</div>
			</div>
			<div class="col-xs-9" ></div>
		</div>
		
	
	</div>
   	<!------footer信息 begin----->
  	<#include "/includes/footer.ftl" />
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->

</body>
</html>