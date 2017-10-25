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
				<input type="hidden" name="userId" id="hideUserId" value="${result.userId}" />
			  <div class="form-group" style="margin-bottom:5px;" >
				<div class="alert alert-info" style="padding: 5px;margin-bottom: 0px;"  role="alert">
				 		<p class="form-control-static">
						<#if result.userName??>${result.userName}</#if>
						(<code><#if result.nickName??>${result.nickName}</#if></code>)
						(<code><#if result.userPhone??>${result.userPhone}</#if></code>)
						 </p>
				 </div>
			  </div>
			</form >
			   <ul id="my-tab-rule" class="myNavTab nav nav-tabs" role="tablist">
				   <li role="presentation" style="cursor:pointer" class="active" >
						<a id="my-tab-related"  data-key="related"  >
							 已关联机构   		
						</a>
				  </li>
				  <li role="presentation" style="cursor:pointer" class="" >
						<a id="my-tab-relating"  data-key="org-relating"  >
							 可关联机构
						</a>
				  </li>
			</ul>
			 <div id="my-tab-content" class="myTabContent tab-content">
			   <div role="tabpanel"  class="tab-pane fade  active in" id="tab-pane-related" style="min-height:360px;"  >
			     <form class="form-search form-inline" id="related_org_search_form" >  
				         <div class="form-group" >
					 		<input type="hidden" name="page" id="related-page" value="1" />
					 		<input type="hidden" name="size" id="related-size" value="10" />
						 </div>
					  <button type="button" id="related-org-seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
					  <button type="button" id="related-remove-btn" class="btn btn-danger"><@spring.message code="label.default.button.del" /></button>
					<div class="bars pull-right" >
					 <p  style="background-color: #fff;    display: inline-block;" >
							共 <code id="related-org-paginationTotal" >0</code> 记录
					 </p>
					 	<select class="form-control" style="display: inline;width: 75px;" name="pag-size" id="related-org-paginationSize">
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
 						<th style="width: 50px;" class="bs-checkbox" >
				          	<input type="checkbox" id="relatedOrgBtnSelectAll" />
				          </th>
				          <th style="width: 50px;" class="bs-checkbox" >
				          	默
				          </th>
   						   <th >名称</th>
				          <th style="width: 120px;">联系人</th>
				        </tr>
				      </thead>
						 <tbody  id="related-org-tbody" >
						  <tr>
						 	<td colspan="4"><@spring.message  code="label.default.data.loading" /></td>
						 </tr>
						 </tbody >
						 <script id="related-org-tr-template-js"  type="text/html">
						{{if (content.length>0) }}
					    {{each content as model}}
						  <tr  data-num="{{$index}}" 
						  	class="">
							<td class="bs-checkbox text-center" >
							 <div >
							  	<input id="related_{{model.relateId}}" name="id" type="checkbox" value="{{model.relateId}}"/>
							  </div>
							</td>
							<td class="bs-checkbox text-center" >
							 <div >
							  	<input id="related_isDefault_{{model.relateId}}" data="{{model.relateId}}"  name="isDefault" {{if model.isDefault}}disabled checked{{/if}}  type="radio" value="{{model.relateId}}"/>
							  </div>
							</td>
							<td>{{model.relateOrg.name}}(<code>{{model.relateOrg.orgCode}}</code>)</td>
							<td>{{model.relateOrg.contacts}}</td>
							</tr>
						{{/each}}
					  {{else}}
						<tr id="ext_{{$index}}" class="text-center" >
							<td colspan="4"><@spring.message  code="label.default.empty" /></td>
						</tr>
					  {{/if}}	
						</script>
					</table>
					<div  id="related-org-table-pagination" style="margin-top: 5px;"  class="clearfix"></div>
					
			   </div>
			    <div role="tabpanel"  class="tab-pane fade" id="tab-pane-org-relating" >
			    	<div class="panel panel-default" style="">
					  <!-- Default panel contents -->
					  <div class="panel-heading" style="padding:3px !important;"  >
					   <form class="form-search form-inline" id="org_search_form" >  
				         <div class="form-group" >
					 		<input type="hidden" name="page" id="org-page" value="1" />
					 		<input type="hidden" name="size" id="org-size" value="10" />
						 </div>
				       <div class="form-group">
							<div class="input-group">
									<span class="input-group-addon">地区</span>
									 <input type="hidden" class="form-control" id="hideAreaCode" value="" name="areaCode" />
							 		<select class="form-control" style="width: 100px;" data-pid='<#if result.areaCode?length gt 1>${result.areaCode?substring(0,2)}</#if>' id="org_provinceId" name="areaCode_LIKES">
									    	<option value="" >------</option>
									</select>
									<select class="form-control" style="width: 100px;" data-pid='<#if result.areaCode?length gt 3>${result.areaCode?substring(0,4)}</#if>'  id="org_cityId" name="areaCode_LIKES">
									     	<option value="" >------</option>
									</select>
					        </div>
					 </div>
					 <div class="form-group">
						 <label for="name" >类型:</label>
						 <select name="type" class="form-control" >
						 <option value="">---</option>
					 	<@custom.dict dictCode="org_type" >	
							<#list items as model>  
								<option value="${model.itemValue}" >${model.itemName}</option>
							</#list>
						</@custom.dict>
						</select>
					 </div>
					  <div class="form-group">
						 <div class="input-group">
							<span class="input-group-addon">名称</span>
							 <input type="text" class="form-control" style="width: 200px;" value='' id="name" name="name_LIKES" placeholder="名称">
							 <span class="input-group-btn"><button type="button" id="org-seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button></span>
						 </div>
					 </div>

					</form>	
					  </div>
					  <div class="bars pull-left" >
					  		 <button type="button" id="add-org-btn" class="btn btn-danger">关联机构</button>
					  </div>
					  <div class="bars pull-right" >
							 <p  style="background-color: #fff;    display: inline-block;" >
									共 <code id="org-paginationTotal" >0</code> 记录
							 </p>
							 	<select class="form-control" style="display: inline;width: 75px;"name="pag-size" id="org-paginationSize">
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
				           <th style="width: 50px;" class="bs-checkbox" >
				          	<input type="checkbox" id="orgBtnSelectAll" />
				          </th>
				          <th >名称</th>
				          <th style="width: 120px;">联系人</th>
				        </tr>
				      </thead>
						 <tbody id="org-table-tbody"  >
							 <tr>
							 	<td colspan="3"><@spring.message  code="label.default.data.loading" /></td>
							 </tr>
						 </tbody >
					<script id="org-tr-template-js"  type="text/html">
						{{if (content.length>0) }}
					    {{each content as model}}
						  <tr  data-num="{{$index}}" 
						  	class="">
							<td class="bs-checkbox text-center" >
							 <div >
							 	{{if !model.isRelated}} 
							 		  	<input id="org_{{model.id}}" name="id"  type="checkbox" value="{{model.id}}"/>
							 	{{/if}} 
							  </div>
							</td>
							<td>{{model.name}}(<code>{{model.orgCode}}</code>)</td>
							<td>{{model.contacts}}</td>
							</tr>
						{{/each}}
					  {{else}}
						<tr id="ext_{{$index}}" class="text-center" >
							<td colspan="3"><@spring.message  code="label.default.empty" /></td>
						</tr>
					  {{/if}}	
						</script>
					</table>
					  </form  >
					  <!---table---->
					  	<div  id="org-table-pagination" style="margin-top: 5px;"  class="clearfix"></div>
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
  	seajs.use("js/admin/usercore/user_org_related.js?v="+Math.random());
</script>
 </body>
</html>