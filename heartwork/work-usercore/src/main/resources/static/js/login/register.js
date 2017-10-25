define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	 require('validate');
	 var dialog = require('artDialog');
	 var $baseRoot=$("#baseRoot");
	 var baseRoot=$baseRoot.attr("href");
	 var $httpUtil = require('/js/common/httpUtil.js');
	 $("#quick-regist-btn").on("click",function(){
		 var id = $(this).attr("id");
		 $.httpUtil.curl({
				url : baseRoot+"/regist/quick.jhtml",
				loading : false,
				type : "get",// 
				success : function(data) {
					dialog({
						id : id,
						title : "用户快速注册",
						cancelValue : '取消',
						bodyClass : "ui-dialog-body-min",
						height : "400",
						width : "500",
						content:data,
						onclose: function () {
						
						},
					}).showModal();
				}
			});
		 
	 });
	 $("#protocol-btn").on("click",function(){
		 var agreements=$("#agreements-body").html();
		 var d = dialog({
				id : "protocol-btn",
			    title: '用户注册协议',
			    content: agreements,
			});
		 d.showModal();
	 });
	//发送短信验证码
	$("#J_PhoneCodeBtn").on("click",function(){
		var $userPhone=$("#J_RegisterForm #userPhone");
		var validator = $("#J_RegisterForm").validate();
		var userPhone=$userPhone.val();
		if(userPhone==""){
			var d = dialog({
				id : $userPhone.attr("id"),
			    title: '系统提示',
			    content: '请输入手机号码',
			    okValue: '确定',
			    ok: function () {
			        this.title('关闭中…').close().remove();
			        $userPhone.focus();
			        return false;
			    }
			});
			d.showModal();
			setTimeout(function() {
				d.close().remove();
				$userPhone.focus();
			}, 2000);
			return;
		}
		 var reg="^0?(13|15|18|14|17)[0-9]{9}$";
		if(!(new RegExp(reg).test(userPhone))){
			var d = dialog({
				id : $userPhone.attr("id"),
			    title: '系统提示',
			    content: '手机号码格式错误',
			    okValue: '确定',
			    ok: function () {
			        this.title('关闭中…').close().remove();
			        $userPhone.focus();
			        return false;
			    },
			    cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
			setTimeout(function() {
				d.close().remove();
				$userPhone.focus();
			}, 2000);
			return;
		}
		var $sendBtn=$(this);
		var i=60;
		var interval=setInterval(function(){
			i--;
			$sendBtn.html("剩余"+i+"秒").attr("disabled","disabled");
			if(i<=0){
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
	$("#J_RegisterForm").validate({
		rules: {
			userName: {
				 required: true,
				 minlength: 6,
				 maxlength: 30,
				 isUid:true,//检验是否保护特殊字符
				 isNotfullNumber:true,//检验是否为纯数字
				 remote: {
					 url: baseRoot+"/regist/checkUserName.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userName: function () {
                             return $("#userName").val();
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
					 url: baseRoot+"/regist/checkUserPhone.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userPhone: function () {
                             return $("#userPhone").val();
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
				 equalTo: "#passWord",
			},
			phoneCode: {
				 required: true,
				 maxlength: 5,
				 minlength: 5,
				 remote: {
					 url: baseRoot+"/validate/checkMobliecode.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userPhone: function () {
                             return $("#userPhone").val();
                         },
                         phoneCode: function () {
			                return $("#phoneCode").val();
			            }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
			},
			userEmail: {
				required: true,
				minlength: 5,
				isEmail:true,
				 remote: {
					 url: baseRoot+"/regist/checkUserEmail.json",
                     type: "get",               //数据发送方式
                     dataType: "json",
                     data: {
                    	 userName: function () {
                             return $("#userEmail").val();
                         }
                     },dataFilter: function (data) {
                    	 var json=$.parseJSON(data);
                     	return ""+json.result;
                     }
				 }
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
			userName: {
				 required: "用户名不能为空",
				 remote: "该用户名已经被使用",
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
	
	$("#J_RegisterSubmit").on("click",function(){
		var $form=$('#J_RegisterForm');
		var data = getFormData($form);
		var $btn=$(this);
		var dataHtml=$(this).html();
		var url=$form.attr("action");
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		$form.submit();
		$btn.html(dataHtml).removeAttr("disabled");
	});
});