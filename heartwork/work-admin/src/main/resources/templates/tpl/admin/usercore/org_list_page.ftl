 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="10" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
		<div class="form-group">
			 	<select class="form-control" style="width: 100px;" id="provinceId" name="areaCode_LIKES">
				    	<option value="" >------</option>
				</select>
				<select class="form-control" style="width: 100px;" id="cityId" name="areaCode_LIKES">
				     	<option value="" >------</option>
				</select>
				<select class="form-control" style="width: 120px;" id="areaId" name="areaCode_LIKES">
				     	<option value="" >------</option>
				</select>
		</div>   
	     <div class="form-group">
			 <label for="name" >名称:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="name" name="name_LIKES" placeholder="名称">
		 </div>
		  <div class="form-group">
			 <label for="name" >类型:</label>
			 <select name="type" class="form-control" >
			 	<@custom.dict dictCode="org_type" >	
					<#list items as model>  
						<option value="${model.itemValue}" >${model.itemName}</option>
					</#list>
				</@custom.dict>
			</select>
		 </div>
		 <div class="form-group">
			 <label for="name" >状态:</label>
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
	            <th class="page-sort-btn" data-sort="name"  >名称</th>
	            <th style="width:150px;" >状态</th>
	            <th style="width:250px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='4' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.id}" name="id" value="${model.id}" />
					    </td>
					     <td>${model.name!'未知'}(<code>${model.orgCode!'无'}</code>)</td>
					     <td>
					     <#if (model.status=='pending') >未使用</#if>
					     <#if (model.status=='enabled') >可用</#if>
					     <#if (model.status=='disabled') >禁用</#if>
					     </td>
	 					<td class="operate text-center">
							<i id="edit-${model.id}" data="${model.id}" class="btn-view fa fa-pencil">修改</i>
							<i id="child-${model.id}" data="${model.id}" class="btn-add-child fa fa-plus">添加</i>
						    <i id="chain-${model.id}"　title="绑定机构"　data="${model.id}"  class="btn-chain fa fa-chain">
								绑定
							</i>
							<#if (model.status=='pending')&&(!model.hasChildren) >
							    <i id="del-${model.id}"  title="删除" data="${model.id}" class="btn-del fa fa-trash-o">删除</i>
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
	  <tr  data-num="{{$index}}" >
		<td scope="row" class="text-center" >
		  <input type="checkbox" id="check_{{model.id}}" name="id" value="{{model.id}}" />
		</td>
		 <td>{{model.name}}(<code>{{model.orgCode}}</code>)</td> 
		  <td>
		   {{if model.status=='pending'}}未使用{{/if}} 
		   {{if model.status=='enabled'}}可用{{/if}} 
		    {{if model.status=='disabled'}}禁用{{/if}} 
		 </td>
		<td class="operate text-center">
			<i id="edit-{{model.id}}" data="{{model.id}}" title="修改" class="btn-view fa fa-pencil">修改</i>
			<i id="child-{{model.id}}" data="{{model.id}}" title="添加子机构"  class="btn-add-child fa fa-plus">添加</i>
			<i id="chain-{{model.id}}" title="绑定机构" data="{{model.id}}" class="btn-chain fa fa-chain">
				绑定
			</i>
			{{if (model.status=='pending')&&(!model.hasChildren) }}
			    <i id="del-{{model.id}}" data="{{model.id}}"  title="删除"  class="btn-del fa fa-trash-o">删除</i>
			{{/if}} 
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='4'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>