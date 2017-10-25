<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>关联机构</title>
</head> 
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="container-fluid">
	   <div class="row">
	   	 <div class="col-xs-12">
			<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
				<input type="hidden" name="hideOrgId" id="hideOrgId" value="${result.id}" />
			  <div class="form-group" style="margin-bottom:5px;" >
				<div class="alert alert-info" style="padding: 5px;margin-bottom: 0px;"  role="alert">
				 		<p class="form-control-static">
						<#if result.name??>${result.name}</#if>
						(<code><#if result.orgCode??>${result.orgCode}</#if></code>)
						 </p>
				 </div>
			  </div>
			</form >
			   <ul id="my-tab-rule" class="myNavTab nav nav-tabs" role="tablist">
				   <li role="presentation" style="cursor:pointer" class="active" >
						<a id="my-tab-related"  data-key="related"  >
							 已关联	
						</a>
				  </li>
				  <li role="presentation" style="cursor:pointer" class="" >
						<a id="my-tab-relating"  data-key="repair-relating"  >
							 可关联
						</a>
				  </li>
			</ul>
			 <div id="my-tab-content" class="myTabContent tab-content">
			   <div role="tabpanel"  class="tab-pane fade  active in" id="tab-pane-related" style="min-height:360px;"  >
			     <form class="form-search form-inline" id="related_repair_search_form" >  
				         <div class="form-group" >
					 		<input type="hidden" name="page" id="related-page" value="1" />
					 		<input type="hidden" name="size" id="related-size" value="10" />
						 </div>
					  <button type="button" id="related-org-seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
					  <button type="button" id="related-remove-btn" class="btn btn-danger"><@spring.message code="label.default.button.del" /></button>
					<div class="bars pull-right" >
					 <p  style="background-color: #fff;    display: inline-block;" >
							共 <code id="related-repair-paginationTotal" >0</code> 记录
					 </p>
					 	<select class="form-control" style="display: inline;width: 75px;" name="pag-size" id="related-repair-paginationSize">
						  <option value="10">10</option>
						  <option value="20">20</option>
						  <option value="50">50</option>
						  <option value="100">100</option>
						  <option value="200">200</option>
						</select>
					</div>
					</form>	
			   	 <table style="margin-bottom:0px;"   class="table  table-striped table-bordered table-condensed table-hover"> 
				     	 <thead>
				        <tr>
 						<th style="width: 30px;" class="bs-checkbox" >
				          	<input type="checkbox" id="relatedorgBtnSelectAll" />
				          </th>
				           <th style="width: 30px;" class="bs-checkbox" >
				          	默
				          </th>
				          <th>名称</th>
				          <th style="width: 65px;">类型</th>
				          <th>联系人</th>
				          <th>固话/手机</th>
				          <th>经营范围</th>
				        </tr>
				      </thead>
						 <tbody  id="related-repair-tbody" >
						  <tr>
						 	<td colspan="7"><@spring.message  code="label.default.data.loading" /></td>
						 </tr>
						 </tbody >
						 <script id="related-repair-tr-template-js"  type="text/html">
						{{if (content.length>0) }}
					    {{each content as model}}
						  <tr  data-num="{{$index}}" 
						  	class="">
							<td class="bs-checkbox text-center" >
							 <div >
							  	<input id="related_{{model.relateId}}" name="id" {{if model.isRelated}}disabled checked{{/if}}  type="checkbox" value="{{model.relateId}}"/>
							  </div>
							</td>
							<td class="bs-checkbox text-center" >
							 <div >
							  	<input id="isDefault_{{model.id}}" name="isDefault" {{if model.isRelated}}disabled{{/if}}  {{if model.isDefault}}checked{{/if}}  type="checkbox" value="{{model.id}}"/>
							  </div>
							</td>
						
							<td>{{model.relateOrg.name}}(<code>{{model.relateOrg.orgCode}}</code>)</td>
							<td class="text-center" >
							 <@custom.dict dictCode="org_cate" >
						   	  <#list items as model> 
						   	  		{{if model.relateType=='${model.itemValue}'}} 
						     	  		<code>${model.itemName}</code>
						     	  	{{/if}} 
							 </#list>
							</@custom.dict>
							</td>
							<td >{{model.relateOrg.contacts}}</td>
							<td>{{model.relateOrg.mobilePhone}}<code>{{model.relateOrg.telePhone}}</code></td>
							<td>
							 <@custom.dict dictCode="org_cate" >
						   	  {{each model.relateOrg.category.split(",") as cate}}
						     	  <#list items as model>  
						     	  	{{if cate=='${model.itemValue}'}}
						     	  		<code>${model.itemName}</code>
						     	  	{{/if}} 
								 </#list>
						      {{/each}}	
							</@custom.dict>
							</td>
							</tr>
						{{/each}}
					  {{else}}
						<tr id="ext_{{$index}}" class="text-center" >
							<td colspan="7"><@spring.message  code="label.default.empty" /></td>
						</tr>
					  {{/if}}	
						</script>
					</table>
					<div  id="related-repair-table-pagination" style="margin-top: 5px;"  class="clearfix"></div>
					
			   </div>
			    <div role="tabpanel"  class="tab-pane fade" id="tab-pane-repair-relating" >
			    	<div class="panel panel-default" style="">
					  <!-- Default panel contents -->
					  <div class="panel-heading" style="padding:3px !important;"  >
					   <form class="form-search form-inline" id="repair_search_form" >  
				         <div class="form-group" >
					 		<input type="hidden" name="page" id="repair-page" value="1" />
					 		<input type="hidden" name="size" id="repair-size" value="10" />
						 </div>
				       <div class="form-group">
							<div class="input-group">
									<span class="input-group-addon">地区</span>
									 <input type="hidden" class="form-control" id="hideAreaCode" value="" name="areaCode" />
							 		<select class="form-control" style="width: 100px;" data-pid='<#if result.areaCode?length gt 1>${result.areaCode?substring(0,2)}</#if>' id="repair_provinceId" name="areaCode_LIKES">
									    	<option value="" >------</option>
									</select>
									<select class="form-control" style="width: 100px;" data-pid='<#if result.areaCode?length gt 3>${result.areaCode?substring(0,4)}</#if>'  id="repair_cityId" name="areaCode_LIKES">
									     	<option value="" >------</option>
									</select>
					        </div>
					 </div>
					  <div class="form-group">
					  	<div class="input-group">
						   <span class="input-group-addon">类型</span>
							<select class="form-control" style="width: 100px;" id="categoryIds" name="category_LIKE">
							  <@custom.dict dictCode="org_category" >	
								  	<#list items as model>  
							    	<option value="${model.itemValue}" >${model.itemName}</option>
							 		 </#list>
							  </@custom.dict>
							</select>
						  </div>
					  </div>
					  <div class="form-group">
					  	<div class="input-group">
					    <input type="text" class="form-control" value="" style="width: 200px;" id="q_orgName" name="name_LIKE" placeholder="名称">
					     <span class="input-group-btn">  <button type="button" id="repair-seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button></span>
					 	</div>
					   </div>
					
					</form>	
					  </div>
					  <div class="bars pull-left" >
					   <button type="button" id="add-repair-btn" class="btn btn-danger">关联</button>
					  </div>
					  <div class="bars pull-right" >
							 <p  style="background-color: #fff;    display: inline-block;" >
									共 <code id="repair-paginationTotal" >0</code> 记录
							 </p>
							 	<select class="form-control" style="display: inline;width: 75px;"name="pag-size" id="repair-paginationSize">
								  <option value="10">10</option>
								  <option value="20">20</option>
								  <option value="50">50</option>
								  <option value="100">100</option>
								  <option value="200">200</option>
								</select>
					</div>
					  <form method="post"  id="J_RuleForm"  > 
					  	<table style="margin-bottom:0px;"   class="table table-bordered table-condensed table-hover table-responsive"> 
				     	 <thead>
				        <tr>
				           <th style="width: 30px;" class="bs-checkbox" >
				          	<input type="checkbox" id="orgBtnSelectAll" />
				          </th>
				           <th style="width: 30px;" class="bs-checkbox" >
				          	默
				          </th>
				          <th>名称</th>
				          <th>联系人</th>
				          <th>固话/手机</th>
				          <th>经营范围</th>
				        </tr>
				      </thead>
						 <tbody id="repair-table-tbody"  >
							 <tr>
							 	<td colspan="6"><@spring.message  code="label.default.data.loading" /></td>
							 </tr>
						 </tbody >
					<script id="repair-tr-template-js"  type="text/html">
						{{if (content.length>0) }}
					    {{each content as model}}
						  <tr  data-num="{{$index}}" 
						  	class="">
							<td class="bs-checkbox text-center" >
							 <div >
							 	{{if !model.isRelated &&model.id!=${result.id} }} 
							 		 <input id="repair_{{model.id}}" name="id"  type="checkbox" value="{{model.id}}"/>
							 	{{/if}} 
							  </div>
							</td>
							<td class="bs-checkbox text-center" >
							 <div >
								 {{if !model.isRelated &&model.id!=${result.id} }} 
							  	<input id="repair_isDefault_{{model.id}}" name="isDefault" {{if model.isRelated}}disabled{{/if}}  {{if model.isDefault}}checked{{/if}}  type="checkbox" value="{{model.id}}"/>
							  	 {{/if}} 
							  </div>
							</td>
							<td title="{{model.name}}({{model.orgCode}})" >{{model.name}}(<code>{{model.orgCode}}</code>)</td>
							<td>{{model.contacts}} </td>
							<td title="{{model.telePhone}}({{model.mobilePhone}})"  >{{model.telePhone}} <code>{{model.mobilePhone}}</code></td>
							<td>
							 <@custom.dict dictCode="org_category" >
						   	  {{each model.category.split(",") as cate}}
						     	  <#list items as model>  
						     	  	{{if cate=='${model.itemValue}'}}
						     	  		<code>${model.itemName}</code>
						     	  	{{/if}} 
								 </#list>
						      {{/each}}	
							</@custom.dict>
							</td>
							</tr>
						{{/each}}
					  {{else}}
						<tr id="ext_{{$index}}" class="text-center" >
							<td colspan="6"><@spring.message  code="label.default.empty" /></td>
						</tr>
					  {{/if}}	
						</script>
					</table>
					  </form  >
					  <!---table---->
					  	<div  id="repair-table-pagination" style="margin-top: 5px;"  class="clearfix"></div>
					</div>
			     </div>
			 </div>

		 </div>
	   </div>
	  </div>
  <!------seajs.config 引用信息 begin----->
  	<#include "/includes/seajs.config.ftl" />
  	<!------seajs.config   引用信息 end----->
<script>
  	seajs.use("js/admin/usercore/org_relate.js?v="+Math.random());
</script>
 </body>
</html>