<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>机构管理</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/navbar-left-menu.css?v=${ver!'1'}" />
  <#include "/includes/laypage-css.ftl" />
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
  </style>
     <#assign template="sys"/>
     <#assign submenu="org"/> 
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
				 <#include "/admin/usercore/org_list_page.ftl" />
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
<#include "/includes/vue-js.ftl" />
<!------seajs.config   引用信息 end----->
<script>
   	seajs.use(["js/left-menu.js?v=${ver!'1'}","/js/admin/usercore/org_list.js?v="+Math.random(),"/js/app.js?v=${ver!'1'}"]);
</script>
 </body>
</html>