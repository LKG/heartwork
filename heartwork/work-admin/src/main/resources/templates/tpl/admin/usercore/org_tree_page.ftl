<div class="col-xs-4">
	<div class="panel panel-default">
	 <div class="panel-heading"> </div>
	  <div class="panel-body">
	   <div class="loading"> <@spring.message  code="label.default.data.loading" /></div>
	    <ul id="orgTree" class="ztree">
	   	
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
				api : "${contextPath}/admin/org",
			};
		  var setting = {
				async: {
					enable: true,
					dataFilter: filter,
					autoParam:["id=parentId"],
					otherParam:{"size":"500"},
					url: getUrl
				},
				data: {
					key: {
						title:"name"
					},
					simpleData: {
						enable: true,
						idKey: "id",
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
						var id=node.id;
						var isParent=false;
						if(node.hasChildren){
							isParent=true;
						}
						var parentId=node.parentId;
						var $node={id:id,name:name,isParent:isParent,parentId:parentId};
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
			var treeObj=$.fn.zTree.init($("#orgTree"), setting);
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
				$("#main-body").removeClass("loading").load(url.api+"/"+treeNode.id+".jhtml");
			};
			function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
				var zTree = $.fn.zTree.getZTreeObj("orgTree");
				alert("异步获取数据出现异常。");
				treeNode.icon = "";
				zTree.updateNode(treeNode);
			}
			
		});
	
</script>