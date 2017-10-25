<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
	 <input type="hidden" class="form-control" id="parentId" value="<#if result.parentId??>${result.parentId!'0'}</#if>" name="parentId" />
 <#if result.type??>
 	<div class="form-group">
  	<div class="col-xs-12">
		<div class="input-group">
		<span class="input-group-addon">上级</span>
		 <input type="hidden" class="form-control" id="type" value="<#if result.type??>${result.type}</#if>" name="type" />
         <p class="form-control form-control-static" id="parentName" ><code>${result.parentName!'无'}</code></p>
        </div>
	</div>
 </div>
</#if>
 <div class="form-group">
	<div class="col-xs-12">
		<#if result.id??>
			<div class="input-group">
			 <span class="input-group-addon">代号</span>
			 <input type="hidden" class="form-control" id="id"  value="${result.id!''}" name="id" placeholder="机构ID">
			 <input type="hidden" class="form-control" id="orgCode"  value="${result.orgCode!''}" name="orgCode" placeholder="机构代号">
			 <p class="form-control form-control-static"><code>${result.orgCode}</code></p>
 			</div>
		<#else>
			<div class="input-group">
			<span class="input-group-addon">机构号</span>
		 	<input type="text" class="form-control" id="orgCode"  value="${result.orgCode!''}" name="orgCode" placeholder="机构代号">
		 	 <#if result.type??>

		 	 <#else>
		 	 	<div class="input-group-btn"> 
			 	  <select name="type" style="width:100px" class="form-control" >
				 	<@custom.dict dictCode="org_type" >	
						<#list items as model>
							<option value="${model.itemValue}" >${model.itemName}</option>
						</#list>
					</@custom.dict>
				</select>
		 		 </div>
		 	 </#if>
		  </div>
		</#if>
	</div>
 </div>
  <div class="form-group" >
	<div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">名称</span>
         	<input type="text" class="form-control" id="areaName" value="<#if result.name??>${result.name}</#if>" name="name" placeholder="名称"/>
          <div class="input-group-btn">
           	<select name="status" id="status"  data="<#if result.status??>${result.status}</#if>" style="min-width:100px;"   class="form-control">
				<option value="pending">未使用</option>
				<option value="disabled">禁用</option>
				<option value="enabled">可用</option>
			</select>
          </div>
        </div>
	</div>
 </div>
   <div class="form-group" >
    <div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">联系人</span>
         	<input type="text" class="form-control" id="contacts" value="<#if result.contacts??>${result.contacts}</#if>" name="contacts" placeholder="联系人"/>
        </div>
	</div>
 </div>
   <div class="form-group" >
    <div class="col-xs-6">
		<div class="input-group">
	  	<span class="input-group-addon">手机</span>
         	<input type="text" class="form-control" id="mobilePhone" value="<#if result.mobilePhone??>${result.mobilePhone}</#if>" name="mobilePhone" placeholder="名称"/>
        </div>
	</div>
	<div class="col-xs-6">
		<div class="input-group">
	  	<span class="input-group-addon">固话</span>
         	<input type="text" class="form-control" id="telePhone" value="<#if result.telePhone??>${result.telePhone}</#if>" name="telePhone" placeholder="名称"/>
         
        </div>
	</div>
 </div>
 <div class="form-group">
	<div class="col-xs-12">
		<div class="input-group">
		<span class="input-group-addon">地区</span>
					 <input type="hidden" class="form-control" id="hideAreaCode" value="<#if result.areaCode??>${result.areaCode}</#if>" name="areaCode" />
		 	<select class="form-control" style="width: 100px;" data-pid='<#if result.areaCode?length gt 1>${result.areaCode?substring(0,2)}</#if>' id="provinceId" name="provinceId">
				    	<option value="" >------</option>
				</select>
				<select class="form-control" style="width: 100px;" data-pid='<#if result.areaCode?length gt 3>${result.areaCode?substring(0,4)}</#if>'  id="cityId" name="cityId">
				     	<option value="" >------</option>
				</select>
				<select class="form-control" style="width: 120px;" data-pid='<#if result.areaCode?length gt 5>${result.areaCode?substring(0,6)}</#if>' id="areaId" name="areaId">
				     	<option value="" >------</option>
				</select>
        </div>

	</div>
 </div>
   <div class="form-group">
	<div class="col-xs-12">
		<div class="input-group">
		<span class="input-group-addon">街道</span>
			<select class="form-control" id="orgAddress"  data-pid='<#if result.areaCode?length gt 7>${result.areaCode?substring(0,9)}</#if>'  name="orgAddress">
				 <option value="" >------</option>
			</select>
        </div>
	</div>
 </div>
  <div class="form-group">
	<div class="col-xs-12">
		<div class="input-group">
		<span class="input-group-addon">具体地址</span>
         	<input type="text" class="form-control" id="orgAddress"  value="${result.address!''}" name="address" placeholder="具体地址">
        </div>

	</div>
 </div>
 <div class="form-group">
	<label for="category" class="col-xs-3 control-label" >经营范围</label>
	<div class="col-xs-9">	<#-- 后面考虑从数据库查询获取分类信息 -->
	 <@custom.dict dictCode="org_cate" >	
		<#list items as model>  
		<label class="checkbox-inline">
	    <input type="checkbox" id="category_${model.id}"  <#list result.category?split(",") as category><#if category==model.itemValue>checked<#break></#if></#list> name="category"  value="${model.itemValue}">${model.itemName}
	  	</label>
		</#list>
	</@custom.dict>
	</div>
  </div>
  <div class="form-group">
	<label for="remark" class="col-xs-2 control-label" >描述</label>
	<div class="col-xs-10">
	<textarea class="form-control" id="remark" name="remark" placeholder="描述" rows="3"><#if result.remark??>${result.remark}</#if></textarea>
	</div>
  </div>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>