<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>角色授权</title>
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
						 <p><span>(<code >${result.roleCode}</code>)${result.roleName}</span>
							 	<i class="fa fa-check-square fa-lg"></i>
								 <code >${result.roleDesc}</code>
						 </p>
					</div>
					<div class="row" > 
						 <div class="col-xs-12">
  	 						<div id="loading" class="loading"> <@spring.message  code="label.default.data.loading" /></div>
							   
							<!-->
							 <ul id="resourceTree" class="ztree">
							 
							   </ul>
							<!---->
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
   	seajs.use(["js/left-menu.js?v=${ver!'1'}","/js/app.js?v=${ver!'1'}"]);
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