 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="resourceCode" >代号:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="roleCode" name="resourceCode_LIKES" placeholder="代号">
		 </div>
		 <div class="form-group">
			 <label for="resourceName" >名称:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="resourceName" name="resourceName_LIKES" placeholder="名称">
		 </div>
		 <div class="form-group">
			 <label for="resourceType" >类型:</label>
			 <select name="resourceType" class="form-control" >
			 <option value="">---</option>
			  <option value ="menu">菜单</option>
			  <option value ="button">按钮</option>
			  <option value="url">url</option>
			</select>
		 </div>
 		<div class="form-group">
			 <label for="status" >状态:</label>
			 <select name="status" class="form-control" >
			 <option value="">---</option>
			  <option value ="pending">未使用</option>
			  <option value ="enabled">可用</option>
			  <option value="disabled">禁用</option>
			</select>
		 </div>
		  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
	   </form>	
   </div>
 	<div class="panel-body" > 
 	<!---toolbar begin-->
	 <div id="toolbar">
	  <div class="bars pull-left" >
		  <button id="btn-add" class="btn btn-danger" data="0">
				 <i class="fa fa-cloud-upload"></i><@spring.message code="label.default.button.create" />
		  </button>
	  </div>
		<#include "/includes/pagination-total.ftl" />
	 </div>
	<!---toolbar end-->
	 	<div class="table-responsive">
		 <table class="table table-bordered table-condensed table-hover">
	        <thead>
	          <tr>
	           <th  class="text-center bs-checkbox" style="width: 30px;"  >
			       <input type="checkbox"  class="text-center bs-checkbox-selectAll"  />
			   </th>
	            <th class="page-sort-btn" data-sort="resourceName">名称<i class="fa " ></i></th>
	            <th >描述</th>
	            <th style="width:150px;" >创建日期</th>
	            <th style="width:150px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='5' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.resourceId}" name="resourceId" value="${model.resourceId}" />
					    </td>
					     <td>${model.resourceName!''}(<code>${model.resourceCode!''}</code>)
					     	<span class="badge "><#if model.resourceType=='menu'>菜单</#if>
					     	<#if model.resourceType=='button'>按钮</#if>
					     	<#if model.resourceType=='url'>URL</#if>
					     	</span>
					     </td>
					     <td>${model.resourceDesc!''}</td>
					     <td>${model.modiTime}</td>
	 					<td class="operate text-center">
							<i id="edit-${model.resourceId}" data="${model.resourceId}" class="btn-view fa fa-pencil">修改</i>
							<i id="child-${model.resourceId}" data="${model.resourceId}" class="btn-add-child fa fa-plus">添加</i>
							<#if (model.status=='pending')&&(!model.hasChildren) >
							    <i id="del-${model.resourceId}"  title="删除" data="${model.resourceId}" class="btn-del fa fa-trash-o">删除</i>
							</#if>
						</td>
			    	</tr>
			    </#list >			       	 
			  </#if>
	        </tbody>
	      </table>  		
	  	</div>
	  	<!-----分页-begin---->
			<div  id="table-pagination" data-totalPages="${result.totalPages}" data-number="${result.number}" style="margin-top: -15px;"  class="clearfix"></div>
		<!-----分页-end---->
 	</div>
 </div>
<script id="tr-template-js"  type="text/html">
{{if (content.length>0) }}
   {{each content as model}}
	  <tr  data-num="{{$index}}" 
	  	class="">
		<td scope="row" class="text-center" >
		  <input type="checkbox" id="check_{{model.resourceId}}" name="resourceId" value="{{model.resourceId}}" />
		</td>
		 <td>{{model.resourceName}}(<code>{{model.resourceCode}}</code>)
		 <span class="badge">
		 	{{if model.resourceType=='menu'}}菜单{{/if}} 
			{{if model.resourceType=='button'}}按钮{{/if}} 
			{{if model.resourceType=='url'}}URL{{/if}} 
		</span>
		 </td>
		 <td>{{model.resourceDesc}}</td>
		  <td>{{model.createTime}}</td>
		<td class="operate text-center">
			<i id="edit-{{model.resourceId}}" data="{{model.resourceId}}" class="btn-view fa fa-pencil">修改</i>
			<i id="child-{{model.resourceId}}" data="{{model.resourceId}}" class="btn-add-child fa fa-plus">添加</i>
			{{if (model.status=='pending')&&(!model.hasChildren) }}
			    <i id="del-{{model.resourceId}}" data="{{model.resourceId}}"  title="删除"  class="btn-del fa fa-trash-o">删除</i>
			{{/if}} 
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='5'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>