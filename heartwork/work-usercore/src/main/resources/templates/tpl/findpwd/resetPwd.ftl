<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>找回密码</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<style>
 #forgot_process span.active{
  	  background: #fda6a3;
	  color: #fff;
  }
   .main-container{
   	padding:0px 100px;
   	margin-top: 40px;
   	height:583px;
   }
   #forgot_process{
   	width: 100%  !important;
   	margin-bottom: 50px;
   }
  #forgot_process span{
  	width: 25%  !important;
  	border:1px solid transparent;
  	border-color: #ddd;
  }
</style>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
	<div class="container main-container" id="register-container">
		<div class="panel panel-default">
			<div class="panel-heading">找回密码</div>
	  		<div class="panel-body">
	  			<div class="btn-group" id="forgot_process">
					 <span class="btn " ><i class="fa fa-pencil-square-o"></i>填写账户名</span>
					 <span class="btn " ><i class="fa fa-anchor"></i>验证身份</span>
					 <span class="btn active" ><i class="fa fa-paypal"></i>设置新密码</span>
					 <span class="btn " ><i class="fa fa-check"></i>完成</span>
				</div>
		  		<div class="row ">
				  <div class="col-sm-12 col-md-12 col-xs-12">
					<#include "/pages/forgotpasswd-3.ftl" />
				 </div>
				</div>
	  		</div>
  		</div>
	</div>
   	<!------footer信息 begin----->
  	<#include "/includes/footer.ftl" />
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
 </body>
</html>