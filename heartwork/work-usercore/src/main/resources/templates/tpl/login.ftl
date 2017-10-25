<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
 <head> 
 <title><@spring.message  code="label.login.title" /></title>
<!------head 引用信息 begin----->
  	<#include "/includes/head.ftl" />
<!------head   引用信息 end----->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/login.css?v=${ver!'1'}" />
 </head> 
 <body id="login_body"> 
  <!--背景图片begin--> 
  <div class="lg_bg"> 
   <div class="lg_bg_in" id="lg_bg"  > 
    <img class="on" style="display:none;"  src="http://old.bz55.com/uploads/allimg/150714/139-150G4120254.jpg" /> 
   </div> 
  </div> 
  <!--背景图片end-->
  <div class="header" id="header">
	<div id="header-inner">
	  <div class="logo">
	  </div>
	</div>
  </div> 
  <div class="wrap-login clearfix" id="wrap-login" >
   <!-- 宣传图片区 begin-->
   <div class="login-banner" >
		<a href="javascript:void(0);"  target="_blank">
		<img id='j_mediaImg' style="display:none;"  src="#" >
		</a>
		<div class="themeCtrl">
			<a id="prevTheme" href="javascript:void(0);" onclick="javascript:void(0);" title="上一张"><i class="fa fa-chevron-left"></i></a>
			<a id="nextTheme" href="javascript:void(0);" onclick="javascript:void(0);" title="下一张"><i class="fa fa-chevron-right"></i></a>
		</div>
   </div>
    <!--宣传图片end -->
	 <!-- 登录 begin-->
   <div class="lg_bd">
	<div class="lg_bd_in">
		<div class="lg_bd_c clearfix">
			<div class="lg_bd_c1 clearfix">
				<div class="loginBox">
					 <iframe allowtransparency="true" style="border: 1px solid #ccc; border-top:0px;" scrolling="no" frameborder="0" width="317" height="317" id="login_box"></iframe>
				</div >
			</div >
		</div >
	</div >
   </div >
  <!-- 登录 end-->
  </div>
  <!--版权信息begin--> 
  <#include "/includes/copyright.ftl" />
  <!--版权信息begin-->
  	<!------seajs.config 引用信息 begin----->
  	<#include "/includes/seajs.config.ftl" />
  	<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use("${contextPath}/js/login/init.js?v=${ver!'1'}");
</script>
 </body>
 	 <#include "/includes/jquery.ftl" />
 <script type="text/javascript">
  var loginUrl="${contextPath}/login-in.jhtml?logout=${_error!''}";
 	document.getElementById('login_box').src = loginUrl;
</script >

</html>
    