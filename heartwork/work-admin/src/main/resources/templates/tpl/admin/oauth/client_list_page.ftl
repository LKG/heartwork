 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="userName" >账号:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="userName" name="userName_LIKES" placeholder="账号">
		 </div>
		 <div class="form-group">
			 <label for="userPhone" >手机号:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="userPhone" name="userPhone_LIKES" placeholder="手机号">
		 </div>
		  <div class="form-group" >
			<label for="type">注册日期:</label>
				<input type="text" name='createTime_GTE' style="width: 100px;" class="Wdate form-control"
							size='10' maxlength='10' id="startYear"
						 readonly="readonly" value="" 
						 onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endYear\')}' })"/>
				<input type="text" name='createTime_LTE' id="endYear" style="width: 100px;" class="Wdate form-control"
							size='10' maxlength='10' 
						value="${.now?string('yyyy-MM-dd')}" readonly="readonly"
						 onclick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startYear\')}',maxDate:'%y-%M-%d'})"/>
		</div>
		 <div class="form-group">
			 <label for="userPhone" >邮箱:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="userEmail" name="userEmail_LIKES" placeholder="邮箱">
		 </div>
 		<div class="form-group">
			 <label for="name" >状态:</label>
			 <select name="status" class="form-control" style="width: 75px;" >
			 <option value="">---</option>
			  <option value ="pending">未激活</option>
			  <option value ="enabled">可用</option>
			  <option value="disabled">禁用</option>
			</select>
		 </div>
		  <div class="form-group">
			 <label for="name" >类型:</label>
			 <select name="userType" class="form-control" style="width: 85px;">
			 <option value="">---</option>
				<@custom.dict dictCode="user_type" >	
					<#list items as model>  
						<option value="${model.itemValue}" >${model.itemName}</option>
					</#list>
				</@custom.dict>
			</select>
		 </div>
		<div class="form-group">
			 <label for="name" >审核状态-:</label>
			 <select name="checkStatus" class="form-control">
			 <option value="">---</option>
			  <option value ="pending">未审核</option>
			  <option value ="waiting">审核中</option>
			  <option value="fail">审核不通过</option>
			  <option value="success">审核通过</option>
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
	  <@shiro.hasPermission name="user:create">
	   
	  </@shiro.hasPermission>
	  </div>
		<#include "/includes/pagination-total.ftl" />
	 </div>

	<!---toolbar end-->
	 	<div class="table-responsive">
		 <table class="table table-responsive table-striped table-bordered table-condensed table-hover">
	        <thead>
	          <tr>
	           <th  class="text-center bs-checkbox" style="width: 30px;" >
			       <input type="checkbox"  class="text-center bs-checkbox-selectAll"  />
			   </th>
	            <th class="page-sort-btn" style="width: 120px;" data-sort="userName"  >账号<i class="fa " ></i></th>
	            <th class="page-sort-btn" style="width: 110px;" data-sort="userPhone" >手机号<i class="fa " ></i></th>
	            <th class="page-sort-btn" style="width: 130px;" data-sort="userEmail" >邮箱<i class="fa " ></i></th>
	            <th  class="text-center" style="width:150px;" >注册日期</th>
	            <th  class="text-center"  >默认机构</th>
	            <th  class="text-center" style="width:120px;" >激活/认证</th>
	            <th  class="text-center" style="width:260px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='8' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.userId}" name="code" value="${model.userId}" />
					    </td>
					     <td class="text-center">${model.userName!''}</td>
					     <td class="text-center"><code>${model.userPhone!'无'}</code></td>
					     <td class="text-center"><code>${model.userEmail!'无'}</code></td>
					     <td class="text-center">${model.createTime}</td>
					     <td><code><#if model.relateOrg??&&model.relateOrg.name??>${model.relateOrg.name!'无'}</#if></code></td>
					     <td  class="text-center">
					       <#if model.status=='pending'>
							 未激活
						   </#if>
						   <#if model.status=='enabled'>
							 正常
						   </#if>
						   <#if model.status=='disabled'>
							 禁用
						   </#if>
						   <code>/</code> <#if model.checkStatus=='pending'>
							  待审核
						   </#if>
						   <#if model.checkStatus=='waiting'>
							 审核中
						   </#if>
						   <#if model.checkStatus=='fail'>
							 未通过
						   </#if>
						   <#if model.checkStatus=='success'>
							已审核
						   </#if>
					     </td>

	 					<td class="operate text-center">
	 						<a   href="${contextPath}/admin/user/${model.userId}.jhtml"  title="修改"  >
								<i id="view-${model.userId}" data="${model.userId}" class="btn-view fa fa-eye">查看</i>
							</a>
							<#if model.status!='enabled' >
							<i id="publish-${model.userId}"　title="激活"  data="${model.userId}" class="btn-publish fa fa-key">激活</i>
							</#if>
							<#if model.status=='enabled' >
								<i id="disabled-${model.userId}"　title="禁用"　data="${model.userId}"  class="btn-disabled fa fa-lock">
									禁用
								</i>
								<i id="chain-${model.userId}"　title="绑定机构"　data="${model.userId}"  class="btn-chain fa fa-chain">
									绑定
								</i>
								<i id="auth-${model.userId}" title="授权" data="${model.userId}" class="btn-auth fa fa-trophy">
									授权
								</i>
								<i id="reset-${model.userId}"　title="重置密码" data="${model.userId}" class="btn-reset fa fa-chain">
									重置
								</i>
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
		  <input type="checkbox" id="check_{{model.userId}}" name="userId" value="{{model.userId}}" />
		</td>
		 <td class="text-center">{{model.userName}}</td> 
		 <td class="text-center"><code>{{model.userPhone}}</code></td>
		 <td class="text-center"><code>{{model.userEmail}}</code></td>
		 <td class="text-center">{{model.createTime}}</td>
		  <td><code>{{if model.relateOrg}} {{model.relateOrg.name}}{{/if}}</code></td>
		 <td  class="text-center" >
			 {{if model.status=='pending'}}
				未激活
			{{/if}}
			{{if model.status=='enabled'}}
				 正常
			{{/if}}
			 {{if model.status=='disabled'}}
				 禁用
			{{/if}}
			<code>/</code> {{if model.checkStatus=='pending'}}
				 待审核
			{{/if}}
			{{if model.checkStatus=='waiting'}}
				 审核中
			{{/if}}
			{{if model.checkStatus=='fail'}}
				 未通过
			{{/if}}
			{{if model.checkStatus=='success'}}
				已审核
			{{/if}}
		 </td>

		<td class="operate text-center">
			<a   href="${contextPath}/admin/user/{{model.userId}}.jhtml"  title="修改"  >
				<i id="view-{{model.userId}}" data="{{model.userId}}" class="btn-view fa fa-eye">查看</i>
			</a>
			{{if model.status!='enabled'}} 
				<i id="publish-{{model.userId}}" title="激活"  data="{{model.userId}}" class="btn-publish fa fa-key">激活</i>
			{{/if}}
			{{if model.status=='enabled'}} 
				<i id="disabled-{{model.userId}}" title="禁用" data="{{model.userId}}" class="btn-disabled fa fa-lock">
					禁用
				</i>
				<i id="chain-{{model.userId}}" title="绑定机构" data="{{model.userId}}" class="btn-chain fa fa-chain">
				绑定
			   </i>
				<i id="auth-{{model.userId}}" title="授权" data="{{model.userId}}" class="btn-auth fa fa-trophy">
					授权
				</i>
				<i id="reset-{{model.userId}}" title="重置密码" data="{{model.userId}}" class="btn-auth fa fa-trophy">
					重置
				</i>
			{{/if}}
			
			
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='8'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>