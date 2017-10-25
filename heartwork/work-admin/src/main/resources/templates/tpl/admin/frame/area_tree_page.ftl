<div class="panel panel-default" style="" >
	  <div class="panel-body">
	   <div class="loading"> <@spring.message  code="label.default.data.loading" /></div>
	    <ul id="areaTree" class="ztree">
	   	
	    </ul>
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