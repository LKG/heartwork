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
     <#assign submenu="role"/> 
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
				<li>角色管理</li>
				</ul><!-- .breadcrumb -->
			 </div>
			<!-- .breadcrumb  end -->
			<div class="container-fluid" >
					<!-----panel--->
				<div class="panel panel-info main-panel">
			 	<div class="panel-heading" style="padding: 0px 15px;">
			 		<a href="${contextPath}/admin/roles.jhtml" class="btn btn-danger" role="button"><i class="fa fa-reply"></i>返回</a>
			 		 <button id="heading-btn-add" class="btn btn-danger" data="0">
				 		<i class="fa fa-save"></i><@spring.message code="label.default.button.save" />
		 		 </button>
			 	</div>
				<div class="panel-body" style="min-height:500px;" id="roleResource-body" >
					<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
					<div class="alert alert-info" role="alert">
						<input type="hidden" name="roleId" id="hideRoleId" value="${result.roleId}" />
						 <p><span>(<code >${result.roleCode}</code>)${result.roleName}</span>
							<i class="fa fa-check-square fa-lg"></i>
							<code >${result.roleDesc}</code>
						 </p>
					</div>
					<div class="row" >
						 <#if (resources.content?size>0) >
							<#list resources.content as model>
								 <#if (model.parentId==0) >
								<div class="col-xs-6" >
									<div class="panel panel-default">
									  <div class="panel-heading">
									  	<label  class="checkbox-inline" > 
											<input type="checkbox" name="resourceId"  data-id="${model.resourceId}" data-code="${model.resourceCode}"
												  	  	 <#if model.ischecked > checked</#if> 
												  	 value="${model.resourceId}"/>${model.resourceName!''}(<code>${model.resourceCode!''}</code>)
										</label>
										 <#list model.permissions as permission> 
										      <label  <#if permission_index==0 > style="padding-left: 30px;"</#if> class="checkbox-inline" > 
											  	<input type="checkbox" name="permissionIds_${model.resourceId}"  data-id="${permission.permissionId}"
											  	  	 <#if permission.isChecked > checked</#if> 
											  	 value="${permission.permissionId}"/>${permission.permissionName}
											  </label>
										 </#list >
									  </div>
									   <table class="table table-bordered table-condensed table-hover">
						        		<tbody  id="table-tbody" >
											<#list resources.content as child>
											 <#if (child.parentId==model.resourceId) >	
												  <tr data-row="${model_index/3}" > 
												   <td style="padding-left: 15px;width:300px">
												 	<label  class="checkbox-inline" > 
												        	<input type="checkbox" name="resourceId"  data-id="${child.resourceId}"
												        	data-code="${child.resourceCode}"
													  	  	 <#if child.ischecked > checked</#if> 
													  	 value="${child.resourceId}"/>${child.resourceName!''}(<code>${child.resourceCode!''}</code>)
													</label>
												  </td>
												   <td>
													 <#list child.permissions as childpermission> 
													      <label  class="checkbox-inline" > 
														  	<input type="checkbox" name="permissionIds_${child.resourceId}" data-id="${childpermission.permissionId}"
														  	  	 <#if childpermission.isChecked > checked</#if> 
														  	 value="${childpermission.permissionId}"/>${childpermission.permissionName}
														  </label>
													 </#list >
													 </td>
												  </tr > 
												</#if>
											</#list>
										  </tbody>
						   			   </table>  	
									</div>
								</div>
								</#if>
							</#list>
						<#else>
						</#if>
					</div>
					
				</div>
				<div class="panel-footer" style="padding: 0px 15px;">
			 		<a href="${contextPath}/admin/roles.jhtml" class="btn btn-danger" role="button"><i class="fa fa-reply"></i>返回</a>
			 		 <button id="footer-btn-add" type="button" class="btn btn-danger" data="0">
				 		<i class="fa fa-save"></i><@spring.message code="label.default.button.save" />
		 		 </button>
			 	</div>
			 	</form>	
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
   	seajs.use(["js/left-menu.js?v=${ver!'1'}","js/admin/usercore/role_res_details.js?v="+Math.random(),"/js/app.js?v=${ver!'1'}"]);
</script>
 </body>
</html>