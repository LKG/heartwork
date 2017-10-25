<div class="panel panel-info main-panel"   id="app">
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="tplCode" >编号:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="tplCode" name="tplCode_LIKES" placeholder="编号">
		 </div>
		 <div class="form-group">
			 <label for="tplName" >名称:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="tplName" name="tplName_LIKES" placeholder="名称">
		 </div>
		 <div class="form-group">
			 <label for="tplType" >类型:</label>
			 <select name="tplType" class="form-control">
			 <option value="">---</option> 
				 <@custom.dict dictCode="tpl_type" >	
					<#list items as model>  
						<option value="${model.itemValue}" >${model.itemName}</option>
					</#list>
				</@custom.dict>
			 </select>
		 </div>
		  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
	   </form>	
   </div>
 	<div class="panel-body"  > 
 	<!---toolbar begin-->
	 <div id="toolbar">
	  <div class="bars pull-left" >
		  <a id="btn-add" class="btn btn-danger" href="${contextPath}/admin/tpl/add.jhtml"  title="<@spring.message code="label.default.button.create" />" >
			 <i class="fa fa-cloud-upload"></i> <@spring.message code="label.default.button.create" />
		  </a>
	  </div>
			<#include "/includes/pagination-total.ftl" />
	 </div>
	<!---toolbar end-->
	 	<div class="table-responsive">
		 <table class="table table-bordered table-condensed table-hover">
	        <thead>
	          <tr>
	            <th class="text-center">名称</th>
	            <th class="text-center">描述</th>
	            <th  class="text-center" style="width:150px;">创建时间</th>
	            <th  class="text-center" style="width:150px;">操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='4' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
					     <td class="text-center">${model.tplName}<code>(${model.tplCode!''})</code></td>
					     <td class="text-center">${model.tplDesc}</td>
					     <td class="text-center">${model.createTime}</td>
	 					<td class="text-center operate">
	 						<a   href="${contextPath}/admin/tpl/${model.id}.jhtml"  title="修改"  >
								<i id="edit-${model.id}" data="${model.id}" class="fa fa-pencil">修改</i>
							</a>
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
		<th scope="row" class="text-center hide" >
		     <input type="checkbox" id="check_{{model.id}}" name="id" value="{{model.id}}" />
		</th>
		 <td  class="text-center">{{model.tplName}}<code>({{model.tplCode}})</code></td>
		 <td  class="text-center">{{model.tplDesc}}</td>
		 <td  class="text-center" >{{model.createTime}}</td>
		<td class="text-center operate">
			<a   href="${contextPath}/admin/tpl/{{model.id}}.jhtml"  title="修改"  >
				<i id="edit-{{model.id}}" data="{{model.id}}" class="fa fa-pencil">修改</i>
			</a>
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='4'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>