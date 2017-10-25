define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	 require('validate');
	 var dialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	 var validator = $("#J_changePwdForm").validate({
		rules: {
			oldPassword: {
				 required: true,
				 minlength: 6,
				 maxlength:64,
			},
			passWord: {
				 required: true,
				 notEqualTo:"#oldPassword",
				 minlength: 6,
			},
			retryPassWord: {
				 required: true,
				 equalTo: "#passWord",
			},
			 
		},
		messages:{
			oldPassword: {
				required: "原密码不能为空",
				minlength: "新密码长度最少为{0}",
			},
			passWord: {
				required: "新密码不能为空",
				minlength: "新密码长度最少为{0}",
				notEqualTo: "新密码不能和旧密码相同",
			},
			retryPassWord: {
				 required: "确认密码不能为空",
				 equalTo: "两次输入密码不一致"
			},
		}
	});
	$("#J_changePwdSubmit").on("click",function(){
		var $form=$('#J_changePwdForm');
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
				var content="密码修改成功！";
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