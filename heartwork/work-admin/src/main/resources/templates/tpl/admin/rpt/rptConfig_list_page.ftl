 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="1" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="rptName" >名称:</label>
			 <input type="text" class="form-control" style="width: 200px;" value='' id="rptName" name="rptName_LIKES" placeholder="名称">
		 </div>
		  <div class="form-group" >
			<label for="type">日期:</label>
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
		  <button id="btn-add" class="btn btn-danger hide" data="0">
				 <i class="fa fa-cloud-upload"></i><@spring.message code="label.default.button.create" />
		  </button>
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
	            <th class="page-sort-btn"  data-sort="rptName"  >名称<i class="fa " ></i></th>
	            <th  class="text-center" style="width: 80px;" >排序号</th>
	            <th  class="text-center" style="width:100px;" >状态</th>
	            <th  class="text-center" style="width:150px;" >日期</th>
	            <th  class="text-center" style="width:250px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='6' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.rptId}" name="code" value="${model.rptId}" />
					    </td>
					     <td>${model.rptName!''}</td>
					     <td><code>${model.rptOrder!'0'}</code></td>
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
					     </td>
					     <td class="text-center">${model.createTime}</td>
	 					<td class="operate text-center">
	 						<i id="edit-${model.rptId}" data="${model.rptId}" class="btn-view fa fa-pencil">
								修改
							</i>
							<#if model.status!='enabled' >
							<i id="publish-${model.rptId}" title="激活"  data="${model.rptId}" class="btn-publish fa fa-key">激活</i>
							</#if>
							<#if model.status=='enabled' >
								<i id="disabled-${model.rptId}" data="${model.rptId}" class="btn-disabled fa fa-lock">
									禁用
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
		  <input type="checkbox" id="check_{{model.rptId}}" name="rptId" value="{{model.rptId}}" />
		</td>
		 <td>{{model.rptName}}</td> 
		 <td><code>{{model.rptOrder}}</code></td>
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
		 </td>
		  <td class="text-center">{{model.createTime}}</td>
		<td class="operate text-center">
			<i id="edit-{{model.rptId}}" data="{{model.rptId}}" class="btn-view fa fa-pencil ">
				修改
			</i>
			{{if model.status!='enabled'}} 
				<i id="publish-{{model.rptId}}" title="激活"  data="{{model.rptId}}" class="btn-publish fa fa-key">激活</i>
			{{/if}}
			{{if model.status=='enabled'}} 
				<i id="disabled-{{model.rptId}}" data="{{model.rptId}}" class="btn-disabled fa fa-lock">
					禁用
				</i>
			{{/if}}
			
			
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='6'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>