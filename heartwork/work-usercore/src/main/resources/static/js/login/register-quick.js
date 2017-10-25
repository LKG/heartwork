define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	require('validate');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	 var dialog = require('artDialog');
	//发送短信验证码
	$("#Q_PhoneCodeBtn").on("click",function(){
		var a=null;
		var $userPhone=$("#Q_RegisterForm #Q_userPhone");
		var validator = $("Q_RegisterForm").validate();
		var userPhone=$userPhone.val();
		if(userPhone==""){
			$(this).modal({
				content: "请输入手机号码"
				,clickClose: true	// 自定义样式
				,cssclass: 'modal-sm'	// 自定义样式
			});
			return;
		}
		 var reg="^0?(13|15|18|14|17)[0-9]{9}$";
		if(!(new RegExp(reg).test(userPhone))){
			$(this).modal({
				content: "手机号码格式错误"
				,clickClose: true	// 自定义样式
				,cssclass: 'modal-sm'	// 自定义样式
			});
			return;
		}
		var $sendBtn=$(this);
		var i=60;
		var interval=setInterval(function(){
			i--;
			$sendBtn.html("剩余"+i+"秒").attr("disabled","disabled");
			if(i<0){
				$sendBtn.html("重新发送").removeAttr("disabled");
				clearInterval(interval);
			}
		},1000);
		$.get(baseRoot+"/validate/passmobliecode.json",{moblie:userPhone},function(json){
			var content="验证码发送成功";
			 if(!json.success){
				 content=json.result.error_description;
			 }
			 var d = dialog({
					id : $userPhone.attr("id"),
				    title: '系统提示',
				    content: content,
				    okValue: '确定',
				    ok: function () {
				        this.title('关闭中…').close().remove();
				        $userPhone.focus();
				        return false;
				    },
				});
				d.showModal();
				setTimeout(function() {
					d.close().remove();
					$userPhone.focus();
				}, 2000);
		},"json");
		
	});
	$("#Q_RegisterForm").validate({
		rules: {
			userPhone: {
				 required: true,
				 isPhone: true,
				 remote: {
					 url: baseRoot+"/regist/checkUserPhone.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userPhone: function () {
                             return $("#Q_userPhone").val();
                         }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
			},
			passWord: {
				 required: true,
				 minlength: 6
			},
			retryPassWord: {
				 required: true,
				 equalTo: "#Q_passWord",
			},
			phoneCode: {
				 required: true,
				 maxlength: 5,
				 minlength: 5
			},
			readme: {
				 required: true,
			},
			userIdcard: {
				 isIdcard: true,
			},
			userQq: {
				isQq: true,
			},
			realName: {
				isRealname: true,
			},
			 
		},
		messages:{
			phoneCode: {
				 required: "请输入收到的短信码",
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
		}
		,submitHandler: function(form) { 
			form.submit();
		}
	});
	//注册按钮
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

	
	$("#Q_RegisterSubmit").on("click",function(){
		var $form=$('#Q_RegisterForm');
		
		var data = getFormData($form);
		var $btn=$(this);
		var dataHtml=$(this).html();
		var url=$form.attr("action");
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		$form.submit();
//		$.post(url,data,function(data){
//			$btn.html(dataHtml).removeAttr("disabled");
//			 if (data.status == 302) {
//		          location.href = data.location;
//		     }
//		},"json");
		$btn.html(dataHtml).removeAttr("disabled");
	});
});