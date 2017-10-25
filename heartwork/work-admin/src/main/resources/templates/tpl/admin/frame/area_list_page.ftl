 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="name" >名称:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="name" name="name_LIKES" placeholder="名称">
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
	           <th  class="text-center bs-checkbox" style="width: 30px;" >
			       <input type="checkbox" id="btSelectAll" class="text-center bs-checkbox-selectAll"  />
			   </th>
			   <th style="width:200px;" class="page-sort-btn" data-sort="code" >代号
	           	 <i class="fa "  ></i>
	            </th>
	            <th class="page-sort-btn" data-sort="name" >名称<i class="fa " ></i></th>
	            <th style="width:150px;" >状态</th>
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
			    			<#if (model.status=='pending')&&(!model.hasChildren) >
			    			 <input type="checkbox" id="check_${model.code}"  name="code" value="${model.code}" />
			    			</#if>
					    </td>
					     <td><code>${model.code!'无'}</code></td>
					     <td>${model.name!'未知'}</td>
					     <td>
					     <#if (model.status=='pending') >未使用</#if>
					     <#if (model.status=='enabled') >可用</#if>
					     <#if (model.status=='disabled') >禁用</#if>
					     </td>
	 					<td class="operate">
							<i id="edit-${model.code}"  title="修改" data="${model.code}" class="btn-view fa fa-pencil">修改</i>
							<i id="child-${model.code}"  title="添加子节点" data="${model.code}" class="btn-add-child fa fa-plus">添加子节点</i>
							<#if (model.status=='pending')&&(!model.hasChildren) >
							    <i id="del-${model.code}"  title="删除" data="${model.code}" class="btn-del fa fa-trash-o">删除</i>
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
			{{if (model.status=='pending')&&(!model.hasChildren) }}
			    <input type="checkbox" id="check_{{model.code}}" name="code" value="{{model.code}}" />
			{{/if}} 
		</td>
		<td><code>{{model.code}}</code></td>
		 <td>{{model.name}}</td> 
		  <td>
		   {{if model.status=='pending'}}未使用{{/if}} 
		   {{if model.status=='enabled'}}可用{{/if}} 
		    {{if model.status=='disabled'}}禁用{{/if}} 
		 </td>
		<td class="operate">
			<i id="edit-{{model.code}}" data="{{model.code}}"  title="修改"  class="btn-view fa fa-pencil">修改</i>
			<i id="child-{{model.code}}" data="{{model.code}}"  title="添加子节点"  class="btn-add-child fa fa-plus">添加子节点</i>
			{{if (model.status=='pending')&&(!model.hasChildren) }}
			    <i id="del-{{model.code}}" data="{{model.code}}"  title="删除"  class="btn-del fa fa-trash-o">删除</i>
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