<div class="panel panel-info main-panel"   id="app">
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="dictCode" >代号:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="dictCode" name="dictCode_LIKES" placeholder="代号">
		 </div>
		 <div class="form-group">
			 <label for="dictName" >名称:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="dictName" name="dictName_LIKES" placeholder="名称">
		 </div>
		 <div class="form-group">
			 <label for="dictType" >类型:</label>
			<select name="dictType" id="dictType"  style="min-width:140px;"   class="form-control">
				<option value="">---</option>
				<option value="single">单个</option>
				<option value="multiple">多选</option>
				<option value="revisable">可变</option>
			</select>
		 </div>
		  
		  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
	   </form>	
   </div>
 	<div class="panel-body"  > 
 	<!---toolbar begin-->
	 <div id="toolbar">
	  <div class="bars pull-left" >
		  <button id="btn-add" class="btn btn-danger">
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
	            <th class="text-center hide">#</th>
	            <th class="page-sort-btn" data-sort="dictCode" >代号<i class="fa "  ></i></th>
	              <th class="page-sort-btn" data-sort="dictName" >名称<i class="fa "  ></i></th>
	             <th >值</th>
	            <th  style="width:150px;" class="page-sort-btn" data-sort="createTime" >创建时间<i class="fa "  ></i></th>
	            <th  style="width:150px;">操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='5' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<th scope="row" class="text-center hide" >
					         <input type="checkbox" id="check_${model.id}" name="id" value="${model.id}" />
					    </th>
					     <td class="text-center"><code>${model.dictCode!''}</code></td>
					     <td class="text-center">${model.dictName}</td>
					     <td class="text-center">${model.dictValue}</td>
					     <td class="text-center">${model.createTime}</td>
	 					<td class="operate">
							<i id="edit-${model.id}" data="${model.id}" style="margin-left:10px" class="btn-view fa fa-pencil">
								修改
							</i>
							<#if ("multiple"==model.dictType) >
								<i id="child-${model.id}"  data="${model.id}" style="margin-left:10px" class="btn-add-child fa fa-plus">
									添加
								</i>
							</#if>
							
							<#if (!model.hasChildren) >
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
		<th scope="row" class="text-center hide" >
		     <input type="checkbox" id="check_{{model.id}}" name="id" value="{{model.id}}" />
		</th>
		 <td  class="text-center"><code>{{model.dictCode}}</code></td>
		 <td  class="text-center">{{model.dictName}}</td>
		 <td  class="text-center">{{model.dictValue}}</td>
		 <td  class="text-center" >{{model.createTime}}</td>
		<td class="operate">
			<i id="edit-{{model.id}}" data="{{model.id}}" style="margin-left:10px" class="btn-view fa fa-pencil">
				修改
			</i>
			{{if ("multiple"==model.dictType) }}
			  <i id="child-{{model.id}}" data="{{model.id}}" style="margin-left:10px" class="btn-add-child fa fa-plus">
			  	添加
			  </i>
			{{/if}}
			{{if (!model.hasChildren) }}
			 	<i id="del-{{model.id}}"  title="删除" data="{{model.id}}" class="btn-del fa fa-trash-o">删除</i>
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