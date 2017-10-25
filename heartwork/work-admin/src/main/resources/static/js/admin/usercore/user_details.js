define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	 require('/js/common/validate');
	 var dialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	 var url={
		api: baseRoot+"/admin/user",
	};
	var $registerForm=$("#J_RegisterForm");
	$registerForm.validate({
		rules: {
			userName: {
				 required: true,
				 minlength: 6,
				 maxlength: 30,
				 isUid:true,//检验是否保护特殊字符
				 isNotfullNumber:true,//检验是否为纯数字
				 remote: {
					 url: url.api+"/checkUserName.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userName: function () {
                             return $registerForm.find("#userName").val();
                         }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                    	return ""+json.result;
                     }
				 }
			},
			userPhone: {
				 required: true,
				 isPhone: true,
				 remote: {
					 url: url.api+"/checkUserPhone.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userPhone: function () {
                             return $registerForm.find("#userPhone").val();
                         }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
			},
			userType: {
				 required: true,
			},
			passWord: {
				 required: true,
				 minlength: 6
			},
			retryPassWord: {
				 required: true,
				 equalTo: "#passWord",
			},
			userEmail: {
				required: true,
				minlength: 5,
				isEmail:true,
				 remote: {
					 url: url.api+"/checkUserEmail.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userName: function () {
                             return $registerForm.find("#userEmail").val();
                         }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
			},
			realName: {
				isRealname: true,
				required: true,
			},
			 
		},
		messages:{
			userName: {
				 required: "用户名不能为空",
				 remote: "该用户名已经被使用",
			},
			userType: {
				 required: "请选择用户类型",
			},
			phoneCode: {
				 required: "请输入收到的短信码",
				 remote: "短信码错误"
			},
			readme: {
				 required: "请先阅读注册协议",
			},
			userEmail: {
				 remote: "该邮箱已经被使用",
			},
			userPhone: {
				 required: "手机号码不能为空",
				 remote: "该手机号码已经被使用",
			},
			retryPassWord: {
				 equalTo: "两次输入密码不一致"
			},
		},
		submitHandler: function(form) { 
		//	form.submit();
		}
	});
	$("#J_RegisterSubmit").on("click",function(){
		if(!$registerForm.validate()){
			return;
		}
		var $btn=$(this);
		var dataHtml=$(this).html();
		var url=$registerForm.attr("action");
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		$registerForm.ajaxSubmit({
				url:url,
				type:"post",
				success: function(data) {
					var mdoel=dialog.getCurrent();
					mdoel.content(data);
				}
			}
		); 
		$btn.html(dataHtml).removeAttr("disabled");
	});
});