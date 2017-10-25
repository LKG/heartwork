 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="10" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="title" >友链名称:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="name" name="name_LIKES" placeholder="友链名称">
		 </div>
		  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
	   </form>	
   </div>
 	<div class="panel-body" > 
 	<!---toolbar begin-->
	 <div id="toolbar">
	  <div class="bars pull-left" >
 		  <button id="btn-add" class="btn btn-danger" >
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
	            <th class="page-sort-btn">友链名称</th>
	            <th >友链地址</th>
	            <th style="width:150px;" >状态/审核</th>
	            <th style="width:150px;" >操作</th>
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
					     <td class="text-center" title="${model.name}">
					     	<a href="${model.url}" target="_blank" >${model.name}</a>
					     </td>
					     <td class="text-center" >
					     	<a href="${model.url}" target="_blank" >${model.url}</a>
					     </td class="text-center" >
					  	 <td  class="text-center" >
						     <#if (model.status=='pending') >未使用</#if>
						     <#if (model.status=='enabled') >可用</#if>
						     <#if (model.status=='disabled') >禁用</#if>
						     /	<#if (model.checkStatus=='pending') >待审核</#if>
						     <#if (model.checkStatus=='waiting') >审核中</#if>
						     <#if (model.checkStatus=='fail') >审核不通过</#if>
					         <#if (model.checkStatus=='success') >审核通过</#if>
					     </td>
	 					<td class="operate text-center">
	 						<i id="edit-${model.id}"  title="修改" data="${model.id}" class="btn-view fa fa-pencil">修改</i>
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
		 <td  class="text-center" title="{{model.name}}">
		 <a target="_blank" href="{{model.url}}" >{{model.name}}</a>
		 </td> 
		 <td class="text-center" >
			<a target="_blank" href="{{model.url}}" >{{model.url}}</a>
		 </td>
		   <td  class="text-center" >
		   {{if model.status=='pending'}}未使用{{/if}} 
		   {{if model.status=='enabled'}}可用{{/if}} 
		    {{if model.status=='disabled'}}禁用{{/if}} 
		    /		   {{if model.checkStatus=='pending'}}待审核{{/if}} 
		   {{if model.checkStatus=='waiting'}}审核中{{/if}} 
		    {{if model.checkStatus=='fail'}}审核不通过{{/if}} 
		    {{if model.checkStatus=='success'}}审核通过{{/if}} 
		 </td>
		<td class="operate text-center">
			<i id="edit-{{model.id}}" data="{{model.id}}"  title="修改"  class="btn-view fa fa-pencil">修改</i>
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='4'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>