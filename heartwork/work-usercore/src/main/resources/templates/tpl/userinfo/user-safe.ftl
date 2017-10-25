<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>用户资料</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
   <#assign template="safe"/>
    <style>
  .main-container{
   	padding:0px 100px;
   	margin-top: 40px;
   	height:583px;
   }

   .user-img ,.thumbnail{
  	width:100px;
  	height:100px;
  }
   .user-img .thumbnail img{
  	height:100%;
  }
  
   </style>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
	<div class="clearfix"></div>
    <div class="full-container main-container">
   	   	<div class="row">
		  <div class="col-xs-3">
		  <!--用户面板 begin--->
		  	<div class="panel panel-default user-panel">
			  <div class="panel-body">
			     <!--userInfo begin---->
	 	 		  <#include "/userinfo/user-media.ftl" />
	 	 		 <!--userInfo end---->
			  </div>
			  <!-- List group begin-->
			  <#include "/userinfo/user-menu.ftl" />
			  <!-- List group end-->
			  <div class="panel-footer">
			  	
			  </div>
			</div>
		  <!--用户面板 end--->
		  
		  </div>
		  <div class="col-xs-9">
			<div class="panel panel-info">
				<div class="panel-heading">
			        <h3 class="panel-title"><i class="fa fa-tag"></i></h3>
			    </div>
			  <div class="panel-body" style="min-height:500px;">
			  <div class="alert alert-info" role="alert">
			  			<div class="pull-left">您的安全级别：</div>
						<div class="progress pull-left" style="width:200px;">
							  <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 80%;">
							    <span class="sr-only">80%</span>
							  </div>
						</div>
						<code style="margin-left:50px;">建议您启动全部安全设置，以保障账户及资金安全。</code>
						<a class="pull-right" href="${contextPath}/userinfo/loginlog.jhtml">查看登录日志</a>	
				</div>
			  	<div class="alert alert-info" role="alert">
					 <p>
					 	<i class="fa fa-check-square fa-lg" ></i>
					 	<span><@spring.message  code="label.login.password" /></span>
					 	互联网账号存在被盗风险，建议您定期更改密码以保护账户安全。<a  href="${contextPath}/userinfo/changePwd.jhtml" >修改密码</a>
					 </p>
				</div>
				<div class="alert alert-warning" role="alert">
					 <p>
					 	<i class="fa fa-warning fa-lg" ></i>
					 	<span>邮箱验证：</span>
					 	您验证的邮箱： <@shiro.principal property='userEmail'/>
					 	验证后，可用于快速找回登录密码，接收账户余额变动提醒。。<a  >立即验证</a>
					 </p>
				</div>
				<div class="alert alert-success" role="alert">
					 <p>
					 	<i class="fa fa-check-square fa-lg" ></i>
					 	<span>手机验证：</span>
					    您验证的手机： <@shiro.principal property='userPhone'/>若已丢失或停用，请立即更换，避免账户被盗 <a>修改</a>
					 </p>
				</div>
				<div class="alert alert-warning" role="alert">
					<h6>安全服务提示</h6>
					 <p>
						<ol>
						  <li>注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</li>
						  <li>建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</li>
						</ol>
					 </p>
				</div>

				
			  </div>
			</div>
		  </div>
		</div>
    </div>
   	<!------footer信息 begin----->
  	<#include "/includes/footer.ftl" />
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
<script>
   	seajs.use(["js/userinfo/user-sign.js?v="+Math.random()]);
</script>
 </body>
</html>