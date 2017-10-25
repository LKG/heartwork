define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	var template = require('arttemplate');
	 require('validate');
	 var dialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	 var httpUtil = require('js/common/httpUtil.js');
	 var url={
		api: baseRoot+"/admin/user",
		roleApi : baseRoot + "/admin/role",
	};
	 var $authRoleForm=$("#J_AuthRolForm");
	 $authRoleForm.validate({
			rules: {
				roleCode: {
					 required: true
				},
			},
			messages:{
				roleCode: {
					 required: "角色标识不能为空",
				},
			},
			submitHandler: function(form) { 
			//	form.submit();
			}
		});
	$("#J_Submit").on("click",function(){
		var $btn=$(this);
		var dataHtml=$(this).html();
		var id=$btn.attr("id");
		var url=$authRoleForm.attr("action");
		var valid=$authRoleForm.valid();
		if(!valid){
			return;
		}
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		var mdoel=dialog.getCurrent();
		if(mdoel){
			$authRoleForm.ajaxSubmit({
				url:url,
				type:"post",
				success: function(data) {
					mdoel.content(data);
				}
			}
			); 
		}else{
			$authRoleForm.submit();
		}
		$btn.html(dataHtml).removeAttr("disabled");
	});
});