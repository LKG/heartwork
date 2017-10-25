<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>角色授权</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/navbar-left-menu.css?v=${ver!'1'}" />
   <style>
   .main-panel{
      margin-top: -20px;
   }
  .table td{white-space:nowrap;overflow:hidden; text-overflow:ellipsis; } 
  .table  {table-layout:fixed;}  
  .table th{vertical-align:middle !important;text-align: center; font-size:14px;}
  .Wdate{
	  height: 32px !important;
	  padding: 3px 1px !important; 
	  border: 1px solid #ccc !important; 
	}
  .form-search select{
   	  padding: 6px 3px  !important; 
   }
	.form-inline .form-group,.form-inline .form-control{
		width: 100px;
		display: inline;
	}
	@media (max-width: 900px){
		.panel-heading{
			padding: 10px 0px !important; 
		}
	}
	.laypage_main{
		clear: none !important;
	}
  </style>
     <#assign template="sys"/>
     <#assign submenu="user"/> 
</head> 
<body class="page-header-fixed">
    <#include "/admin/navbar-header.ftl" />
    <div class="container-fluid main-container" >
     <div  class="main-container-inner">
     	  <#include "/admin/navbar-left-menu.ftl" />
     	  <div class="main-content"> 
     	  	<!-- .breadcrumb  begin -->
     	 	 <div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
				<li ><i class="icon-home home-icon"></i><a href="#"><@spring.message  code="label.system.index" /></a></li>
				<li>用户管理</li>
				</ul><!-- .breadcrumb -->
			 </div>
			<!-- .breadcrumb  end -->
			<div class="container-fluid" >
					<!-----panel--->
				<div class="panel panel-info main-panel">
				 	<div class="panel-heading" style="padding: 0px 15px;">
					 	<form  class="form-search form-inline">
					 		<a href="${contextPath}/admin/users.jhtml" class="btn btn-danger" role="button"><i class="fa fa-reply"></i>返回</a>
					 		 <div class="form-group">
					 		 	<label  class="control-label"><@spring.message  code="label.login.username" /></label><span><code>${result.userName}</code></span>
					 		 </div>
					 		<div class="form-group">
					 			<label  class="control-label"><@spring.message  code="label.user.phone" /></label><span><code>${result.userPhone}</code></span>
					 		</div>
					 		<div class="form-group">
					 			<label  class="control-label"><@spring.message  code="label.user.nickName" /></label><span><code>${result.nickName}</code></span>
					 		</div>
					 		<div class="form-group">
					 			<label  class="control-label"><@spring.message  code="label.user.email" /></label><span><code>${result.userEmail}</code></span>
					 		</div>
					 		<div class="form-group">
					 			<label  class="control-label">身份证</label><span><code>${result.userIdcard}</code></span>
					 		</div>
					 		<div class="form-group">
					 			<label  class="control-label">真实姓名</label><span><code>${result.realName}</code></span>
					 		</div>
					 		<div class="form-group">
					 		<label  class="control-label"><@spring.message  code="label.user.qq" /></label><span><code>${result.userQq}</code></span>
					 		</div>
					 	</form>
				 	</div>
					<div class="panel-body" style="min-height:500px;" id="user-body" >
						<#include "/admin/usercore/user_form.ftl" />
					</div>
					<div class="panel-footer" style="padding: 0px 15px;">
				 		<a href="${contextPath}/admin/users.jhtml" class="btn btn-danger" role="button"><i class="fa fa-reply"></i>返回</a>
				 	</div>
				</div>
					<!-----panel--->
			</div>
     	  </div>
     </div>
    </div>
   	<!------footer信息 begin----->
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
  	<#include "/includes/datePicker.ftl" />
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
<script>
   	seajs.use(["js/left-menu.js?v=${ver!'1'}","js/admin/usercore/user_details.js?v="+Math.random(),"/js/app.js?v=${ver!'1'}"]);
</script>
 </body>
</html>