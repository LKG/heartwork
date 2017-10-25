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
		  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
	   </form>	
   </div>
 	<div class="panel-body" > 
	 	<div class="table-responsive">
		 <table class="table table-responsive table-striped table-bordered table-condensed table-hover">
	        <thead>
	          <tr>
	           <th  class="text-center bs-checkbox" style="width: 30px;" >
			       <input type="checkbox"  class="text-center bs-checkbox-selectAll"  />
			   </th>
	            <th class="page-sort-btn" style="width:120px;" >账号<i class="fa " ></i></th>
	            <th  style="width:120px;" >登录IP</th>
	            <th  class="text-center" style="width:140px;" >系统地址</th>
	            <th  style="width:150px;" >操作日期</th>
	            <th  style="width:150px;" >请求时间</th>
	            <th  style="width:60px;"  ></th>
     			<th  class="text-center" >日志详情</th>
	            <th  class="text-center" >userAgent</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
  			 <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='9' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.id}" name="id" value="${model.id}" />
					    </td>
					    <td  class="text-center" >
					    	${model.userName!''}
					    </td>
					    <td  class="text-center" >
					    	${model.userHost!''}
					    </td>
					    <td  class="text-center" >
					    	${model.systemHost!''}
					    </td>
					    <td  class="text-center" >
					    	${model.createTime!''}
					    </td>
					    <td  class="text-center" >
					    	${model.requestTime!''}
					    </td>
					    <td  class="text-center" >
					    	${model.params!''}
					    </td>
					    <td  class="text-center" >
					    	${model.content!''}
					    </td>
					    <td  class="text-center" title="${model.userAgent!''}" >
					    	${model.userAgent!''}
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
		  <input type="checkbox" id="check_{{model.id}}" name="id" value="{{model.id}}" />
		</td>
		<td  class="text-center" >{{model.userName}}</td>
		<td  class="text-center" >{{model.userHost}}</td>
		<td class="text-center" >{{model.systemHost}}</td>
		<td class="text-center" >{{model.createTime}}</td>
		<td class="text-center" >{{model.requestTime}}</td>
		<td class="text-center" >{{model.params}}</td>
		<td class="text-center" >{{model.content}}</td>
		<td class="text-center" title="{{model.userAgent}}" >{{model.userAgent}}</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='9'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>
 
 