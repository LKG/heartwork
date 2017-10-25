<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>添加角色</title>
</head> 
<body class="page-header-fixed">
	<div class="clearfix" ></div>
	<!------页面header信息 end----->
	<div class="full-container" id="main-container" style="height: 531px;padding-right: 10px;">
	 	
		 <form class="form-horizontal" id="J_AuthRolForm" action="${contextPath}/admin/user/${result.userId}/auth" method="post" >
			  <input type="hidden" class="form-control" id="userId" name="userId" value="${result.userId}" >
			 <div class="input-group">
			      <div class="input-group-addon"><i class="fa fa-user"></i></div>
			      <p class="form-control form-control-static" >
			     	 ${result.userName}
			      <p>
			  </div>
			  <div class="form-group"  >
				<label class="col-xs-2 control-label" for="formGroupInputLarge">权限：</label>
				<div class="col-xs-10"  >
				 	<#compress>
					<#list roleCodes as model>
 				  		<label  class="checkbox-inline" style="<#if model_index==0>padding-left: 30px;</#if>" > 
						  	<input type="checkbox" name="roleCode"  data-id="${model.roleId}"
						  		<#if model.hasRole>checked</#if>
						  	 value="${model.roleCode}"/>${model.roleName}
						  </label>
			   		  </#list>
			   		</#compress>
				</div>
			  </div>
			  <div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
				
				  <button id="J_Submit" type="button" class="btn btn-primary"> <@spring.message  code="label.default.button.save" /></button>
				</div>
			  </div>
			</form>
	</div>

  	<!------seajs.config 引用信息 begin----->
	<#include "/includes/seajs.config.ftl" />
	<!------seajs.config   引用信息 end----->
	<script>
  	seajs.use(["js/admin/usercore/user_auth.js?v="+Math.random()]);
	</script>
</body>
</html>