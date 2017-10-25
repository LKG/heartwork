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
   <#assign template="changePwd"/>
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
			  	 <form class="form-horizontal" id="J_changePwdForm"  action="${contextPath}/userinfo/changePwd.json" method="post" >
			  	  <div class="form-group">
				    <label for="oldPassWord" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.password" /></label>
				     <div class="col-sm-6 col-xs-6">
					    <div class="input-group">
					      <div class="input-group-addon"><i class="fa fa-key"></i></div>
					     <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="<@spring.message  code="label.login.password" />" title="<@spring.message  code="label.login.password" />" >
					    </div>
				    </div>
				  </div>
			 	  <div class="form-group">
				    <label for="passWord" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span>新密码：</label>
				     <div class="col-sm-6 col-xs-6">
					    <div class="input-group">
					      <div class="input-group-addon"><i class="fa fa-key"></i></div>
					     <input type="password" class="form-control" id="passWord" name="passWord" placeholder="新密码" title="新密码" />
					    </div>
					    <div class="progress" style="margin-bottom:-10px;margin-top:2px;">
				 			<div id="pass_progress" class="progress-bar  progress-bar-striped active" role="progressbar" 
								aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
				 				<span class=""  id="pass_progress_text" ></span>
				 			</div>
				 		</div>
				    </div>
				  </div>
			  	   <div class="form-group">
				    <label for="retryPassWord" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.user.password.confirm" /></label>
				     <div class="col-sm-6 col-xs-6">
					    <div class="input-group">
					      <div class="input-group-addon"><i class="fa fa-key"></i></div>
					        <input type="password" class="form-control" id="retryPassWord" name="retryPassWord" placeholder="<@spring.message  code="label.user.password.confirm" />" >
					    </div>
				    </div>
				  </div>
				  <div class="form-group">
				 	 <label for="J_changePwdSubmit" class="col-sm-2 col-xs-2 control-label"></label>
				   	  <div class="col-sm-6 col-xs-6">
				   	     <div class="input-group col-xs-12" >
					   	   <input type="hidden" name="format" value="json" />
						   	 <#if sub_token??>
						   	 <input type="hidden" name="sub_token" value="${sub_token}" />
						   	 </#if>
						  <button id="J_changePwdSubmit" class="btn btn-danger btn-lg btn-block" data-loading-text="提交中..." tabindex="5">修改 </button>
						 </div>
					 </div>
			   	   </div>
			  </form>
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
  	seajs.use(["js/userinfo/changePwd.js?v=${ver!'1'}","js/userinfo/user-sign.js?v="+Math.random(),"js/common/passwordCheck.js?v=${ver!'1'}"]);
</script>
 </body>
</html>