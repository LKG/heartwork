<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
 <div class="form-group">
	<div class="col-xs-11">
		<input type="hidden" class="form-control" id="roleId"  value="${result.roleId!""}" name="roleId" >
		<#if result.roleCode??>
			<div class="input-group">
			 <span class="input-group-addon">代号</span>
			 <p class="form-control form-control-static"><code>${result.roleCode}</code></p>
 			<input type="hidden" class="form-control" id="roleCode"  value="${result.roleCode}" name="roleCode" >
 			<input type="hidden" class="form-control" id="level"  value="${result.level}" name="level" >
 			</div>
		<#else>
			<div class="input-group">
			<span class="input-group-addon">代号</span>
		 	<input type="text" class="form-control" id="roleCode"  value="${result.roleCode!''}" name="roleCode" placeholder="">
		  </div>
		</#if>
	</div>
 </div>
  <div class="form-group" >
	<div class="col-xs-11">
		<div class="input-group">
	  	<span class="input-group-addon">名称</span>
         	<input type="text" class="form-control" id="roleName" value="<#if result.roleName??>${result.roleName}</#if>" name="roleName" placeholder="名称"/>
        </div>
	</div>
 </div>
  <div class="form-group">
	<label for="remark" class="col-xs-2 control-label" >描述</label>
	<div class="col-xs-9">
	<textarea class="form-control" id="roleDesc" name="roleDesc" placeholder="描述" rows="3"><#if result.roleDesc??>${result.roleDesc}</#if></textarea>
	</div>
  </div>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>