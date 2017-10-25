<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>省份树</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/navbar-left-menu.css?v=${ver!'1'}" />
  <#include "/includes/laypage-css.ftl" />
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
     <#assign template="sysedit"/>
     <#assign submenu="areatree"/> 
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
				<li>控制台</li>
				</ul><!-- .breadcrumb -->
			 </div>
			<!-- .breadcrumb  end -->
			<div class="container-fluid" >
				 <div class="col-xs-4">
	<div class="panel panel-default">
	 <div class="panel-heading"> </div>
	  <div class="panel-body">
	   <div class="loading"> <@spring.message  code="label.default.data.loading" /></div>
	    <ul id="areaTree" class="ztree">
	   	
	    </ul>
	  </div>
	</div>
</div>
<div class="col-xs-8">
	<div class="panel panel-default">
	 <div class="panel-heading"> </div>
	  <div class="panel-body" id="main-body">
	 	 <div class="loading"> <@spring.message  code="label.default.data.loading" /></div>
	  </div>
	</div>
</div>
<#include "/includes/jquery.ftl" />
<#include "/includes/zTree-js.ftl" />
<script>
		$(document).ready(function(){
			var url = {
				api : "${contextPath}/admin/area",
			};
		  var setting = {
				async: {
					enable: true,
					dataFilter: filter,
					autoParam:["code=parentId"],
					otherParam:{"size":"500"},
					url: getUrl
				},
				data: {
					key: {
						title:"name"
					},
					simpleData: {
						enable: true,
						idKey: "code",
						pIdKey: "parentId",
						rootPId: 0,
					}
				},
				callback: {
					onAsyncSuccess: onAsyncSuccess,
					onClick: onNodeClick,
					onAsyncError: onAsyncError
				}
			};
		 	function filter(treeId, parentNode, data) {
				var nodes = [];
				if (data.success) {
					if (data.result.totalElements==0) return null;
					$.each(data.result.content,function(index,node){
						var name=node.name;
						var code=node.code;
						var isParent=false;
						if(node.hasChildren){
							isParent=true;
						}
						var parentId=node.parentId;
						var $node={code:code,name:name,isParent:isParent,parentId:parentId};
						nodes.push($node);
					});
				}
				return nodes;
			}
			function getUrl(treeId, treeNode) {
				var listurl=url.api+"s.json";
				if(undefined===treeNode){
					listurl+="?parentId=0";
				}
				return listurl;
			}
			var treeObj=$.fn.zTree.init($("#areaTree"), setting);
			var flag=0;
			function onAsyncSuccess(event, treeId, treeNode, msg) {
				$(".loading").hide();
				if(flag==0){
					 var nodes = treeObj.getNodes();
				 	var node = treeObj.selectNode(nodes[0]);
					onNodeClick(null,treeObj.setting.treeId,nodes[0]); 
				}
				flag++;
			}
			function  onNodeClick(event, treeId, treeNode, clickFlag){
				$("#main-body").removeClass("loading").load(url.api+"/"+treeNode.code+".jhtml");
			};
			function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
				var zTree = $.fn.zTree.getZTreeObj("areaTree");
				alert("异步获取数据出现异常。");
				treeNode.icon = "";
				zTree.updateNode(treeNode);
			}
			
		});
	
</script>
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
 </body>
</html>