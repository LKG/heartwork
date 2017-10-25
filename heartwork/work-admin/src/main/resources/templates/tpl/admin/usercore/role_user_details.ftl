<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>授予用户角色</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/navbar-left-menu.css?v=${ver!'1'}" />
  <#include "/includes/zTree-css.ftl" />

   <style>
   .main-panel{
      margin-top: -20px;
   }
  .table td{white-space:nowrap;overflow:hidden; text-overflow:ellipsis; } 
  .table  {table-layout:fixed;}  
  .table th{vertical-align:middle !important;text-align: center; font-size:14px;}
  .Wdate{
	  height: 32px !important;
	  padding: 3px 1px !important; 
	  border: 1px solid #ccc !important; 
	}
  .form-search select{
   	  padding: 6px 3px  !important; 
   }
	.form-inline .form-group,.form-inline .form-control{
		width: 100px;
		display: inline;
	}
	@media (max-width: 900px){
		.panel-heading{
			padding: 10px 0px !important; 
		}
	}
	.laypage_main{
		clear: none !important;
	}
  </style>
     <#assign template="sys"/>
     <#assign submenu="role"/> 
</head> 
<body class="page-header-fixed">
    <#include "/admin/navbar-header.ftl" />
    <div class="container-fluid main-container" >
     <div  class="main-container-inner">
     	  <#include "/admin/navbar-left-menu.ftl" />
     	  <div class="main-content"> 
     	  	<!-- .breadcrumb  begin -->
     	 	 <div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
				<li ><i class="icon-home home-icon"></i><a href="#"><@spring.message  code="label.system.index" /></a></li>
				<li>角色管理</li>
				</ul><!-- .breadcrumb -->
			 </div>
			<!-- .breadcrumb  end -->
			<div class="container-fluid" >
					<!-----panel--->
				<div class="panel panel-info main-panel">
			 	<div class="panel-heading" style="padding: 0px 15px;">
			 		<a href="${contextPath}/admin/roles.jhtml" class="btn btn-danger" role="button"><i class="fa fa-reply"></i>返回</a>
			 	</div>
				<div class="panel-body" style="min-height:500px;">
					<div class="alert alert-info" role="alert">
						 <p><span>(<code >${role.roleCode}</code>)${role.roleName}</span>
							 	<i class="fa fa-check-square fa-lg"></i>
								 <code >${role.roleDesc}</code>
						 </p>
					</div>
					<div class="row" > 
						 <div class="col-xs-12">
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
									  <button type="button" id="seach-btn" class="btn btn-primary"><@spring.message code="label.default.button.search" /></button>
								   </form>	
							   </div>
		 					   <div class="panel-body" > 
							 	<!---toolbar begin-->
								 <div id="toolbar">
								  <div class="bars pull-left" >
								  	<button id="btn-add" class="btn btn-danger" data="0">
											 <i class="fa fa-cloud-upload"></i>分配
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
							            <th class="page-sort-btn" style="width: 120px;" data-sort="userName"  >账号<i class="fa " ></i></th>
							            <th class="page-sort-btn" style="width: 110px;" data-sort="userPhone" >手机号<i class="fa " ></i></th>
							            <th class="page-sort-btn" style="width: 130px;" data-sort="userEmail" >邮箱<i class="fa " ></i></th>
							            <th  class="text-center" style="width:150px;" >注册日期</th>
							            <th  class="text-center" style="width:150px;" >默认机构</th>
							          </tr>
							        </thead>
							        <tbody  id="table-tbody" >
							          <#if (result.content?size<=0) >
							          		 <tr ><td class="text-center" scope="row" colspan='5' ><@spring.message  code="label.default.empty" /></td></tr>
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
											     <td title="<#if model.relateOrg??&&model.relateOrg.name??>${model.relateOrg.name!'无'}</#if>"  ><code><#if model.relateOrg??&&model.relateOrg.name??>${model.relateOrg.name!'无'}</#if></code></td>
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
										 <td title="{{if model.relateOrg}} {{model.relateOrg.name}}{{/if}}" ><code>{{if model.relateOrg}} {{model.relateOrg.name}}{{/if}}</code></td>
									  </tr>
									{{/each}}
								 {{else}}
									<tr id="ext_{{$index}}" class="text-center" >
										<td class="text-center" scope="row" colspan='5'><@spring.message  code="label.default.empty" /></td>
									</tr>
								 {{/if}}	
								</script>
							 </div>
							</div>
						</div>
					</div>
					<!-----panel--->
			</div>
     	  </div>
     </div>
    </div>
   	<!------footer信息 begin----->
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
  	<#include "/includes/datePicker.ftl" />
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
<script>
   	seajs.use(["js/left-menu.js?v="+Math.random(),"/js/app.js?v="+Math.random()]);
</script>
<#include "/includes/jquery.ftl" />
  	<#include "/includes/zTree-js.ftl" />
	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			async: {
				enable: true,
				dataFilter: filter,
				autoParam:["resourceId=parentId"],
				otherParam:{"size":"1000"},
				url: getUrl
			},
			check: {
				enable: true,
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			data: {
				key: {
					title:"name"
				},
				simpleData: {
					enable: true,
					idKey: "resourceId",
					pIdKey: "parentId",
					rootPId: 0,
				}
			},
			view: {
				addDiyDom: addDiyDom
			},
			callback: {
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError
			}
		};
		function addDiyDom(treeId, treeNode) {
			var aObj = $("#" + treeNode.tId + "_a");

			var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
				+ "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
				+ "' title='"+treeNode.name+"' onfocus='this.blur();'>@@@@@</button>";
			aObj.append(editStr);
			var btn = $("#diyBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
		}
	 	function filter(treeId, parentNode, data) {
			var nodes = [];
			if (data.success) {
				if (data.result.totalElements==0) return null;
				$.each(data.result.content,function(index,node){
					var name=node.resourceName;
					var resourceId=node.resourceId;
					var isParent=false;
					var checked=false;
					var open=false;
					if(node.hasChildren){
						isParent=true;
						open=true;
					}
					<#list result.roleResources as roleResource>
						if("${roleResource.resourceId}"==resourceId){
							checked=true;
						}
					</#list>
					var parentId=node.parentId;
					var $node={resourceId:resourceId,name:name,isParent:isParent,parentId:parentId,checked:checked,open:open};
					nodes.push($node);
				});
			}
			return nodes;
		}
		function getUrl(treeId, treeNode) {
			var url="${contextPath}/admin/resources.json";
			if(undefined===treeNode){
				url+="?parentCode=0";
			}
			return url;
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			$("#loading").hide();
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			var zTree = $.fn.zTree.getZTreeObj("resourceTree");
			alert("异步获取数据出现异常。");
			treeNode.icon = "";
			zTree.updateNode(treeNode);
		}
		$(document).ready(function(){
			var treeObj=$.fn.zTree.init($("#resourceTree"), setting);
		});
	</SCRIPT>
 </body>
</html>