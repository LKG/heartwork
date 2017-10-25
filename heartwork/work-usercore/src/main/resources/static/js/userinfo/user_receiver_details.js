define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var url = {
		api : baseRoot + "/userinfo/receiver",
	};
	 require('validate');
	var $httpUtil = require('/js/common/httpUtil.js');
	var dialog = require('/js/common/dialog');
	window.dialog = dialog;
	 var validator = $("#J_MainForm").validate({
			rules: {
				name: {
				    required: true,
				},
				addressDetail: {
				    required: true,
				},
				mobile: {
				    required: true,
				},
				phone: {
				    required: true,
				},
				email: {
				    required: true,
				},
				remark: {
				    required: true,
				}
			},
			messages:{
				name: {
					required: "原密码不能为空",
				},
				addressDetail: {
					required: "原密码不能为空",
				},
			}
		});
		$("#J_Submit").on("click",function(){
			var $form=$('#J_MainForm');
			var $btn=$(this);
			var valid=$form.valid();
			if(!valid){
				return;
			}
			var dataHtml=$(this).html();
			var url=$form.attr("action");
			$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
			var options = {
				url:url,
				type:"post",
			//	resetForm : true,    // 成功提交后，重置所有表单元素的值
				success: function(json) {
					$btn.html(dataHtml).removeAttr("disabled");
					var content="添加成功！";
					 if(json.success){
						$form.resetForm(); 
					 }else{
						 content=json.result.error_description;
					 }
				    var d = dialog({
						id : $form.attr("id"),
					    title: '系统提示',
					    content: content,
					    okValue: '确定',
					    ok: function () {
					        this.title('关闭中…').close().remove();
					        return false;
					    },
					 });
					d.showModal();
					setTimeout(function() {
						d.close().remove();
					}, 2000);
					
			}};
			$form.ajaxSubmit(options); 
		});
});