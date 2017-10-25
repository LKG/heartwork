 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="roleCode" >标识:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="roleCode" name="roleCode_LIKES" placeholder="标识">
		 </div>
		 <div class="form-group">
			 <label for="roleName" >名称:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="roleName" name="roleName_LIKE" placeholder="名称">
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
			       <input type="checkbox"  class="text-center bs-checkbox-selectAll"  />
			   </th>
	            <th class="page-sort-btn" data-sort="roleName" >名称<i class="fa " ></i></th>
	            <th >描述</th>
	            <th style="width:150px;" >创建日期</th>
	            <th style="width:200px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='5' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.roleId}" name="roleId" value="${model.roleId}" />
					    </td>
					     <td><code>(${model.roleCode!''})</code>${model.roleName!''}</td>
					     <td>${model.roleDesc!''}</td>
					     <td>${model.modiTime}</td>
	 					<td class="operate">
							<i id="edit-${model.roleId}" title="修改"  data="${model.roleId}" class="btn-view fa fa-pencil">修改</i>
							<#if (!model.hasChildren) >
						    <i id="del-${model.roleId}"  title="删除" data="${model.roleId}" class="btn-del fa fa-trash-o">删除</i>
						    </#if>
							<a   href="${contextPath}/admin/role/${model.roleId}/resview.jhtml"  title="授权"  >
								<i id="chain-${model.roleId}" data="${model.roleId}" class="btn-chain fa fa-chain">授权</i>
							</a>
							<a   href="${contextPath}/admin/role/${model.roleId}/users.jhtml"  title="分配用户"  >
								<i id="allot-${model.roleId}" data="${model.roleId}" class="btn-allot fa fa-users">分配用户</i>
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
	  <tr  data-num="{{$index}}" 
	  	class="">
		<td scope="row" class="text-center" >
		  <input type="checkbox" id="check_{{model.userId}}" name="userId" value="{{model.userId}}" />
		</td>
		 <td><code>({{model.roleCode}})</code>{{model.roleName}}</td>
		 <td>{{model.roleDesc}}</td>
		  <td>{{model.createTime}}</td>
		<td class="operate">
			<i id="edit-{{model.roleId}}" data="{{model.roleId}}" title="修改" class="btn-view fa fa-pencil">修改</i>
			{{if (!model.hasChildren) }}
			  <i id="del-{{model.roleId}}" data="{{model.roleId}}"  title="删除"  class="btn-del fa fa-trash-o">删除</i>
			{{/if}} 
			<a href="${contextPath}/admin/role/{{model.roleId}}/resview.jhtml""   title="授权" >
				<i id="chain-{{model.roleId}}" data="{{model.roleId}}" class="btn-chain fa fa-chain">授权</i>
			</a>
			<a href="${contextPath}/admin/role/{{model.roleId}}/users.jhtml""   title="分配用户" >
				<i id="allot-{{model.roleId}}" data="{{model.roleId}}" class="btn-allot fa fa-users">分配用户</i>
			</a>
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='5'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>