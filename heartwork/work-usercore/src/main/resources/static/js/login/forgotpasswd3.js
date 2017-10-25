define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	 require('validate');
	var artDialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
		var getFormData=function(obj){
			var data = {}; 
			var $form=$(obj);
			var dataArray=$form.serializeArray();
			 $.each(dataArray, function() { 
				 if (data[this.name]) {  
			           if (!data[this.name].push) {  
			        	   data[this.name] = [data[this.name]];  
			           }  
			           data[this.name].push(this.value || '');  
			       } else {  
			    	   data[this.name] = this.value || '';  
			       }  
			 });
			 return data;
		};
	 var baseRoot=$baseRoot.attr("href");
	 $("#J_ForgotpasswdForm").validate({
			rules: {
				passWord: {
					 required: true,
					 isPassword:true,
					 minlength: 6
				},
				retryPassWord: {
					 required: true,
					 isPassword:true,
					 equalTo: "#passWord",
				},
			},
			messages:{
				passWord: {
					 required: "新密码不能为空",
					 isPassword:"密码格式错误",
				},
				retryPassWord: {
					 required: "确认密码不能为空",
					 isPassword:"密码格式错误",
					 equalTo: "两次输入密码不一致"
				},
			},
			submitHandler: function(form) { 
				form.submit();
			}
		});
});