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
 	<!---toolbar begin-->
	 <div id="toolbar">
	
	 </div>

	<!---toolbar end-->
	 	<div class="table-responsive">
		 <table class="table table-responsive table-striped table-bordered table-condensed table-hover">
	        <thead>
	          <tr>
	           <th  class="text-center bs-checkbox" style="width: 30px;" >
			       <input type="checkbox"  class="text-center bs-checkbox-selectAll"  />
			   </th>
	            <th class="page-sort-btn" style="width:120px;" >账号<i class="fa " ></i></th>
	            <th  style="width:120px;" >登录IP</th>
	            <th  style="width:140px;"  >系统IP</th>
	            <th  style="width:150px;"  >创建时间</th>
	            <th  style="width:150px;"  >最后访问时间</th>
	            <th  class="text-center" >userAgent</th>
	            <th  class="text-center" style="width:50px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
  			<#if (result?size<=0) >
	          	<tr ><td class="text-center" scope="row" colspan='8' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			 	<#list result as model>
			    	<tr >
			    	<td scope="row" class="text-center" >
					      <input type="checkbox" id="check_${model.id}" name="code" value="${model.id}" />
					 </td>
					 <td  class="text-center" title="${model.id}" >${model.username!'游客'}</td>
					 <td scope="row" class="text-center" >
					      <a>${model.host}</a>
					 </td>
					 <td  class="text-center" >${model.systemHost}</td>
					 <td  class="text-center" >${model.startTimestamp?datetime}</td>
					 <td  class="text-center" >${model.lastAccessTime?datetime}</td>
					 <td  class="text-center" title="${model.userAgent}" >${model.userAgent}</td>
					  <td  class="operate text-center" >
					  <#if model.userId??&&model.userId!='0'&&sessionId!=model.id>
					  		<#if 'force_logout'!=model.status>
					  		<i id="disabled-${model.id}" data="${model.id}" class="btn-disabled fa fa-lock">
								下线
							</i>
					  		</#if>
					  </#if>			 
					  </td>
			       </tr>
			   </#list >	
 			</#if>
	        </tbody>
	      </table>  		
	  	</div>
	  	<!-----分页-begin---->
		
		<!-----分页-end---->
 	</div>
 </div>