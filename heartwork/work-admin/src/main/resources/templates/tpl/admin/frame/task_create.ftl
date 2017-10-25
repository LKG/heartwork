<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>任务添加</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/navbar-left-menu.css?v=${ver!'1'}" />  
   <style>
   .main-panel .panel-heading{
      padding: 0px 15px;
   }
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
	.operate i{
		cursor:pointer;
		font-size: 12px;
	}
	.editormd-fullscreen{
		z-index: 9999;
	}
  </style>
     <#assign template="ext"/>
     <#assign submenu="task"/> 
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
				<li>控制台</li>
				</ul><!-- .breadcrumb -->
			 </div>
			<!-- .breadcrumb  end -->
			<div class="container-fluid" >
				<!-- .panel  begin -->
				<div class="panel panel-primary">
					<div class="panel-heading" style="padding: 0px 15px;"  >
					  <a class="btn btn-danger" id="btn-reply" href="${contextPath}/admin/tasks.jhtml"> 
				  	  	<i class="fa fa-mail-reply"></i>返回
				  	   </a>
				  	    <button type="button" id="heading-btn-save" class="btn btn-danger"><@spring.message  code="label.default.button.save" /></button>
	  	  			</div>
					<div class="panel-body">
						<form class="form-horizontal " action="" id="J_articleForm"  method="post"  >
							 <input type="hidden" class="form-control" id="id" value="${result.id}" name="id" />
						 <div class="form-group" >
						 	<div class="col-xs-5">
						 		<div class="input-group">
							 		<span class="input-group-addon">名称</span>
							 		<input type="text" class="form-control"  name="taskName" placeholder="名称" value="${result.taskName}" /> 
								</div>
							</div>
							<div class="col-xs-5">
								<div class="input-group">
							  		<span class="input-group-addon">代号</span>
							  		<input type="text" class="form-control"  name="taskCode" placeholder="代号" value="${result.taskCode}" /> 
						        </div>
							</div>
						 </div>
						 <div class="form-group" >
							<div class="col-xs-10">
								<textarea class="form-control" id="taskDesc" name="taskDesc" placeholder="任务描述" rows="2">${result.taskDesc}</textarea>
							</div>
						 </div>
						   <div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
							  <button type="button" id="footer-btn-save" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
							</div>
						  </div>
						</form>
					
					</div>
				</div>
				<!-- .panel  end -->
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
   	seajs.use(["js/left-menu.js?v=${ver!'1'}","/js/admin/frame/task_create.js?v="+Math.random(),"/js/app.js?v=${ver!'1'}"]);
</script>
 </body>
</html>