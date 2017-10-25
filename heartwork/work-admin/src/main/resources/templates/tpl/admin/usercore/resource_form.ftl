<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
  <div class="form-group">
	<div class="col-xs-6">
		<div class="input-group">
		<span class="input-group-addon">父节点</span>
		 <input type="hidden" class="form-control" id="parentId" value="<#if result.parentId??>${result.parentId!'0'}</#if>" name="parentId" />
         <p class="form-control form-control-static" id="parentName" ><code>${result.parentName!'无'}</code></p>
        </div>
	</div>
	<div class="col-xs-6">
		<div class="input-group">
		<span class="input-group-addon">类型</span>
		<#if result.resourceCode??>
		 	<input type="hidden" class="form-control" id="resourceType" value="<#if result.resourceType??>${result.resourceType!'0'}</#if>" name="resourceType" />
		 	  <p class="form-control form-control-static" id="parentName" ><code>
		 	   <#if result.resourceType=='menu'>菜单</#if>
				<#if result.resourceType=='button'>按钮</#if>
				<#if result.resourceType=='url'>URL</#if>
		 	  </code></p>
		<#else>
			<select name="resourceType" id="resourceType"  data="<#if result.resourceType??>${result.resourceType}</#if>" style="min-width:105px;"   class="form-control">
			  <option value ="menu">菜单</option>
			  <option value ="button">按钮</option>
			  <option value="url">url</option>
			</select>
		</#if>
        </div>

	</div>
 </div>
 <div class="form-group">
	<div class="col-sm-6 col-xs-6">
		<#if result.resourceCode??>
			<div class="input-group">
			 <span class="input-group-addon">代号</span>
			 <p class="form-control form-control-static"><code>${result.resourceCode}</code></p>
			 <input type="hidden" class="form-control" id="resourceId"  value="${result.resourceId}" name="resourceId" >
 			<input type="hidden" class="form-control" id="resourceCode"  value="${result.resourceCode}" name="resourceCode" >
 			<input type="hidden" class="form-control" id="level"  value="${result.level}" name="level" >
 			</div>
		<#else>
			<div class="input-group">
			<span class="input-group-addon">代号</span>
		 	<input type="text" class="form-control" id="resourceCode"  maxlength="32" value="${result.resourceCode!''}" name="resourceCode" placeholder="">
		  </div>
		</#if>
	</div>
	<div class="col-sm-6 col-xs-6">
		<div class="input-group">
		 	<span class="input-group-addon">排序</span>
	 		<input type="text" class="form-control" id="resourceSort" value="<#if result.resourceSort??>${result.resourceSort}</#if>" name="resourceSort" placeholder="模块排序号">
		</div>
	</div>
 </div>
  <div class="form-group" >
	<div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">名称</span>
         	<input type="text" class="form-control" id="resourceName" value="<#if result.resourceName??>${result.resourceName}</#if>" name="resourceName" placeholder="名称"/>
          <div class="input-group-btn">
           	<select name="status" id="status"  data="<#if result.status??>${result.status}</#if>" style="min-width:105px;"   class="form-control">
				<option value="pending">未使用</option>
				<option value="disabled">禁用</option>
				<option value="enabled">可用</option>
			</select>
          </div>
        </div>
	</div>
 </div>
 <div class="form-group">
	<div class="col-sm-12 col-xs-12">
		<div class="input-group">
		 	<span class="input-group-addon">图标</span>
	 	 <input type="text" class="form-control" id="resourceIcon" value="<#if result.icon??>${result.icon}</#if>" name="icon" placeholder="图标">
		</div>
		<p class="help-block">填写<a target="_blank" href="http://fontawesome.io/icons/">Font Awesome</a>图标样式；如果不清楚图标样式，请为空！</p>
	</div>
 </div>
 <div class="form-group">
	<div class="col-sm-12 col-xs-12">
	  <div class="input-group">
	  	<span class="input-group-addon">链接</span>
         <input type="text" class="form-control" id="resourceUrl" value="<#if result.resourceUrl??>${result.resourceUrl}</#if>" name="resourceUrl" placeholder="模块Url"/>
          <div class="input-group-btn">
           	<select name="resourceTarget" id="resourceTarget"  data="<#if result.resourceTarget??>${result.resourceTarget}</#if>" style="min-width:105px;"   class="form-control">
				<option value="_self">当前页面</option>
				<option value="_parent">父窗口</option>
				<option value="_blank">新窗口</option>
				<option value="_top">框架页</option>
			</select>
          </div>
        </div>
	</div>
  </div>
  <div class="form-group">
	<label for="remark" class="col-xs-2 control-label" >描述</label>
	<div class="col-xs-10">
	<textarea class="form-control" id="resourceDesc" name="resourceDesc" placeholder="描述" rows="3"><#if result.resourceDesc??>${result.resourceDesc}</#if></textarea>
	</div>
  </div>
  <fieldset >
 	<legend>操作权限列表</legend>
   <div class="form-group">
	<div class="col-xs-10"  id="permiss-list"  >
		<@user.permission>
	   	     <#list permissions as permission>
	   	      <label  <#if permission_index==0 > style="padding-left: 30px; "</#if> class="checkbox-inline" > 
			  	<input type="checkbox" name="permissionIds"  data-id="${permission.permissionId}"
			  	<#if result.permissionIds??>
			  		<#list result.permissionIds?split(",") as permissionId>
 	  					<#if  permissionId==permission.permissionId >checked<#break></#if>
			   		</#list>
			  	</#if>
			  	 value="${permission.permissionId}"/>${permission.permissionName}
			  </label>
			 </#list>
		</@user.permission>
	</div>
  </div>
   </fieldset>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>