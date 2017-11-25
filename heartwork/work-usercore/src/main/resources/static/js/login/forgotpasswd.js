define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	 require('validate');
	 var dialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	//验证码
	$("#J_StandardCode, #J_StandardCode_m ").on("click",function(e){	
			var $codeImg=$("#J_StandardCode_m");
			var imgUrl=$codeImg.attr("src")+"?_r_"+Math.random();
			$codeImg.attr("src",imgUrl);
	});
	$("#J_ForgotpasswdForm").validate({
		rules: {
			account: {
				 required: true,
				 minlength: 6,
				 remote: {
					 url: baseRoot+"/findPwd/checkUserAccount.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 account: function () {
                             return $("#account").val();
                         }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
			},
			validateCode: {
				 required: true,
				 maxlength: 4,
				 minlength: 4,
				 remote: {
					 url: baseRoot+"/validate/checkRandCode.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 validateCode: function () {
                             return $("#validateCode").val();
                         },
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
			},	 
		},
		messages:{
			account: {
				 required: "请输入帐号名",
				 minlength: "请输入正确的帐号名",
				 remote: "您输入的账户名不存在，请核对后重新输入。"
			},
			validateCode: {
				 required: "请输入验证码",
				 minlength: "请输入正确的验证码",
				 maxlength: "请输入正确的验证码",
				 remote: "验证码校验失败"
			},
		}
		,submitHandler: function(form) { 
			var $btn=$("#J_findPwdSubmit");
			var dataHtml=$btn.html();
			$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
			form.submit();
			$btn.html(dataHtml).removeAttr("disabled");
		}
	});
});