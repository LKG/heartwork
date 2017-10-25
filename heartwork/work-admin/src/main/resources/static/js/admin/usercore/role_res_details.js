define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	var template = require('arttemplate');
	 var dialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	 var httpUtil = require('js/common/httpUtil.js');
	 var url={
		roleApi : baseRoot + "/admin/role",
	};
	var $msg= require('/js/common/alerts.js');
	var $addResBtn = $("#heading-btn-add,#footer-btn-add");
	var $roleResourcebody=$("#roleResource-body");
	$roleResourcebody.find("input[name='resourceId']:not(:disabled)").on("change", function() {
		var val=$(this).val();
		var $checkBox = $roleResourcebody.find("input[name=permissionIds_"+val+"]:not(:disabled)");
		if ($(this).attr("checked")) {
			$checkBox.attr("checked",'true');// 全选
	    } else {
			$checkBox.removeAttr("checked");// 反选
		}
	});
	$addResBtn.on("click", function() {
			var $this=$(this);
			var $checkedBox = $roleResourcebody.find("input[name='resourceId']:checked:not(:disabled)");
			if($checkedBox.length==0 ){
				$msg.alert($this,"请选择角色权限");
				return;
			}
			var datas=[];
			
			$checkedBox.each(function(){ 
				var val=$(this).val();
				if(val!=''&&val!=null){
					var name=$(this).attr("name");
					var code=$(this).attr("data-code");
					var $permissionIds=$roleResourcebody.find("input[name=permissionIds_"+val+"]:checked:not(:disabled)");
					if($permissionIds.length==0 ){
						$(this).removeAttr("checked");//没有权限信息跳出循环
						return true;//跳出本次循环
					}
					var serializeObj={};
					serializeObj[name]=val;
					serializeObj['resourceCode']=code;
					var permissionIds=[];
					$permissionIds.each(function(){ 
						var val=$(this).val();
						if(val!=''&&val!=null){
							permissionIds.push(val);
						}
					});
					serializeObj['permissionIds']=permissionIds;
					datas.push(serializeObj);
				}
			});
			datas=JSON.stringify(datas);
			var hideId = $('#hideRoleId').val();
			var id = $(this).attr("id");
			var d = dialog({
				id:id,
				title : '消息',
				content : '是否要关联选中权限？',
				okValue : '确 定',
				ok : function() {
					var that = this;
					that.title('提交中..');
					$.httpUtil.curl({
						url : url.roleApi +"/"  +hideId+"/addRes.json",
						type : "post",
						loading : true,
						dataType : "json",
						data : {datas:datas},
						success : function(data) {
							 var content="添加成功";
				        	   if(data.success){
				        		   $msg.alert($(this),content);
					        		//search(false);
				        		   return ;
				        	   }else{
				        		   if(data.result &&data.result.error_description){
				        			   content=data.result.error_description;
				             	   }else{
				             		  content="添加失败";
				             	   }
				        	   }
				        	   $msg.alert($(this),content);
						},
					});
					return true;
				},
				cancelValue : '取消',
				cancel : function() {
				}
			}).showModal();
		});
});