 <div class="panel panel-info main-panel" id="app"  >
   <div class="panel-heading"> 
	   <form class="form-search form-inline" id="search_form" >  
	    <input type="hidden" name="page" id="page" value="10" v-model="page"/>
	    <input type="hidden" name="order" id="order" value="" />
	    <input type="hidden" name="sort" id="sort" value="" v-model="sort" />
	    <input type="hidden" name="size" id="size" value="" v-model="size"  />
	     <div class="form-group">
			 <label for="title" >标题:</label>
			 <input type="text" class="form-control" style="width: 300px;" value='' id="title" name="title_LIKES" placeholder="文章标题">
		 </div>
		  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
	   </form>	
   </div>
 	<div class="panel-body" > 
 	<!---toolbar begin-->
	 <div id="toolbar">
	  <div class="bars pull-left" >
		  <a id="btn-add" class="btn btn-danger" href="${contextPath}/admin/article/add.jhtml" >
				 <i class="fa fa-cloud-upload"></i><@spring.message code="label.default.button.create" />
		  </a>
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
	            <th class="page-sort-btn">标题</th>
	            <th style="width:100px;">类型</th>
	            <th style="width:50px;" >状态</th>
	            <th style="width:150px;" >状态</th>
	            <th style="width:120px;" >点击量/点赞数</th>
	            <th style="width:250px;" >操作</th>
	          </tr>
	        </thead>
	        <tbody  id="table-tbody" >
	          <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='7' ><@spring.message  code="label.default.empty" /></td></tr>
			 <#else>
			    <#list result.content as model>
			    	<tr > 
			    		<td scope="row" class="text-center" >
					         <input type="checkbox" id="check_${model.id}" name="id" value="${model.id}" />
					    </td>
					     <td title="${model.title}">${model.shortTitle}</td>
					     <td class="text-center" >${model.type}</td>
					     <td class="text-center" >
					     	<code>${model.categoryName} </code>
					     </td>
					     <td class="text-center" >
					     <code><#if (model.isTop) >置顶<#else>未置顶</#if></code>
					     /
					      <code><#if (model.isPub) >已发布<#else>未发布</#if></code>
					     </td>
					     <td class="text-center" >
					     	<code>${model.hits} </code>
					     	/
					     	<code>${model.rateTimes} </code>
					     </td>
	 					<td class="operate text-center">
	 						<a   href="${contextPath}/admin/article/${model.id}.jhtml"  title="修改"  >
								<i id="edit-${model.id}" data="${model.id}" class="fa fa-chain">修改</i>
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
		<td scope="row" class="text-center" >
		  <input type="checkbox" id="check_{{model.id}}" name="id" value="{{model.id}}" />
		</td>
		 <td  title="{{model.title}}">{{model.shortTitle}}</td> 
		 <td class="text-center" >{{model.type}}</td>
		 <td class="text-center" >
			<code>{{model.categoryName}} </code>
		 </td>
		 <td class="text-center" >
			<code>{{if model.isTop}}置顶{{else}}未置顶{{/if}}</code>
			/
			<code>{{if model.isPub}}已发布{{else}}未发布{{/if}}</code>
		 </td>
		<td class="text-center" >
			<code>{{model.hits}} </code>
				/
			<code>{{model.rateTimes}}</code>
		</td>
		<td class="operate text-center">
			<a   href="${contextPath}/admin/article/{{model.id}}.jhtml"  title="修改"  >
				<i id="edit-{{model.id}}" data="{{model.id}}" class="fa fa-chain">修改</i>
			</a>
		</td>
	  </tr>
	{{/each}}
 {{else}}
	<tr id="ext_{{$index}}" class="text-center" >
		<td class="text-center" scope="row" colspan='7'><@spring.message  code="label.default.empty" /></td>
	</tr>
 {{/if}}	
</script>