<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>添加数据字典子项</title>
</head> 
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="container-fluid">
	   <div class="row">
	   	 <div class="col-xs-12">
			<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
				<input type="hidden" name="dictId" id="hideDictId" value="${result.id}" />
			  <div class="form-group" style="margin-bottom:5px;" >
				<div class="alert alert-info" style="padding: 5px;margin-bottom: 0px;"  role="alert">
				 	<p class="form-control-static">
					<#if result.dictName??>${result.dictName}</#if>
					(<code><#if result.dictCode??>${result.dictCode}</#if></code>)
					</p>
				 </div>
			  </div>
			</form >
			   <ul id="my-tab-rule" class="myNavTab nav nav-tabs" role="tablist">
				   <li role="presentation" style="cursor:pointer" class="active" >
						<a id="my-tab-related"  data-key="related"  >
							 字典明细   		
						</a>
				  </li>
				   <li role="presentation" style="cursor:pointer" >
						<a id="my-tab-add"  data-key="add"  >
							 添加   		
						</a>
				  </li>
			</ul>
			 <div id="my-tab-content" class="myTabContent tab-content">
			   <div role="tabpanel"  class="tab-pane fade  active in" id="tab-pane-related" style="min-height:360px;"  >
			     <form class="form-search form-inline" id="related_item_search_form" >  
				         <div class="form-group" >
					 		<input type="hidden" name="page" id="related-item-page" value="1" />
					 		<input type="hidden" name="size" id="related-item-size" value="10" />
					 		<input type="hidden" name="order" id="related-item-order" value="level" />
						 </div>
					  <button type="button" id="related-item-seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
					  <button type="button" id="related-item-remove-btn" class="btn btn-danger"><@spring.message code="label.default.button.del" /></button>
					<div class="bars pull-right" >
					 <p  style="background-color: #fff;    display: inline-block;" >
							共 <code id="related-item-paginationTotal" >0</code> 记录
					 </p>
					 	<select class="form-control" style="display: inline;width: 75px;" name="pag-size" id="related-item-paginationSize">
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
				          	<input type="checkbox" id="relatedBtnSelectAll" />
				          </th>
				          <th  >名称</th>
				          <th style="width: 120px;">值</th>
				        </tr>
				      </thead>
						 <tbody  id="related-item-tbody" >
						  <tr>
						 	<td colspan="4"><@spring.message  code="label.default.data.loading" /></td>
						 </tr>
						 </tbody >
						 <script id="related-item-tr-template-js"  type="text/html">
						{{if (content.length>0) }}
					    {{each content as model}}
						  <tr  data-num="{{$index}}" 
						  	class="">
							<td class="bs-checkbox text-center" >
							 <div >
							 	{{if (!model.hasChildren) }}
							  	<input id="item_{{model.itemId}}" name="itemId"  type="checkbox" value="{{model.itemId}}"/>
							  	{{/if}} 
							  </div>
							</td>
							<td class="text-center" title="({{model.itemCode}}){{model.itemName}}"  >
								 <code> {{model.itemCode}} </code> {{model.itemName}}
							</td>
							<td class="text-center" title="{{model.itemValue}}"  >
							 {{model.itemValue}}
							</td>
							</tr>
						{{/each}}
					  {{else}}
						<tr id="ext_{{$index}}" class="text-center" >
							<td colspan="3"><@spring.message  code="label.default.empty" /></td>
						</tr>
					  {{/if}}	
						</script>
					</table>
					<div  id="related-item-table-pagination" style="margin-top: 5px;"  class="clearfix"></div>
			   </div>
			     <div role="tabpanel"  class="tab-pane fade" id="tab-pane-add" style="min-height:360px;"  >
  					 <form class="form-horizontal"  style="padding:5px 15px;"  action="" id="J_ItemMainForm"  method="post"  >
  					  <div class="form-group" >
						<div class="col-xs-12">
							<div class="input-group">
						  	<span class="input-group-addon">父节点</span>
					         	<select class="form-control" id="parentId" name="parentId" ><select>
					        </div>
						</div>
					 </div>
					 <div class="form-group">
						<div class="col-xs-12">
						<div class="input-group">
								<span class="input-group-addon">代号</span>
								<input type="hidden" class="form-control" id="dictCode"  value="${result.dictCode}" name="dictCode" placeholder="字典代号">
								<input type="hidden" class="form-control" id="dictId"  value="${result.id}" name="dictId" placeholder="字典Id">
							 	<input type="text" class="form-control" id="itemCode"  value="${result.dictCode}_" name="itemCode" placeholder="${result.dictCode}_">
							  </div>
						</div>
					 </div>
					  <div class="form-group" >
						<div class="col-xs-12">
							<div class="input-group">
						  	<span class="input-group-addon">名称</span>
					         	<input type="text" class="form-control" id="itemName" value="" name="itemName" placeholder="名称"/>
					        </div>
						</div>
					 </div>
					 <div class="form-group">
					   <label for="dictValue" class="col-xs-1 control-label" >值</label>
					   <div class="col-xs-10">
						<textarea class="form-control" id="itemValue" name="itemValue" placeholder="值" rows="3"></textarea>
						</div>
					 </div>
					  <div class="form-group">
						<label for="dictDesc" class="col-xs-1 control-label" >描述</label>
						<div class="col-xs-10">
						<textarea class="form-control" id="itemDesc" name="itemDesc" placeholder="描述" rows="3"></textarea>
						</div>
					  </div>
					  <div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
						  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
						</div>
					  </div>
					</form>
  				</div>
			 </div>

		 </div>
	   </div>
	  </div>
  <!------seajs.config 引用信息 begin----->
  	<#include "/includes/seajs.config.ftl" />
  	<!------seajs.config   引用信息 end----->
<script>
  	seajs.use("js/admin/frame/dict_item_create.js?v="+Math.random());
</script>
 </body>
</html>