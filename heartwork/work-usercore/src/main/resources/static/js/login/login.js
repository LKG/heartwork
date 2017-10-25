define(function (require, exports, moudles) {
	var $ = require('jquery');
	var $loginBox=$("#J_LoginBox");
	var $baseRoot=$("#baseRoot");
	var artDialog = require('artDialog');
	var baseRoot=$baseRoot.attr("href");
	$("#J_2QRCode").show();//显示二维码登录切换
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

	
	$("#J_SubmitStatic").on("click",function(){
		//进行用户信息校验
		var dataHtml=$(this).html();
		var $btn=$(this);
		var username=$("#J_UserName").val();
		if(username==""){
			showLoginError("请输入正确的用户名");
			return false;
		}
		var password=$("#J_PassWord").val();
		if(password==""||password.length<6){
			showLoginError("密码长度至少六位");
			return false;
		}
		
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		var $form=$('#J_StaticForm');
		var data = getFormData($form);
		var url=$form.attr("action");
		$.post(url,data,function(json){
			if(json.success){
				var dialog = dialog=artDialog.getCurrent();
			   	if(dialog){
			   		$.httpUtil.curl({
						url : json.loginSuccUrl,
						type : "get",
						loading : false,
						success :function(data) {
							dialog.content(data);
						},
					});
			   		
			   	}else{
			   		window.parent.location.href= json.loginSuccUrl;
			   	}
			}else{
				$btn.html(dataHtml).removeAttr("disabled");
				if(json._login_times){
					$("#l_f_code").show();	
					$("#J_StandardCode").click();
				}
				if(json.result&&json.result.error_code){
					if(json.result.error_code=='20104'){//用户未激活
						showLoginError(json.result.error_description+"<a href='"+baseRoot+"/forgotpasswd.jhtml' target='_blank' >立即激活</a>");
					}else{
						showLoginError(json.result.error_description);
					}
				}else{
					window.parent.location.reload();
				}
			}
		},"json");
		return  false;
	});

	function showLoginError(message){
		var $Jmessage=$("#J_Message");
		var $i=$("<i class='fa fa-exclamation-triangle'></i>").html(message);
		$Jmessage.empty();
		$Jmessage.addClass("bg-warning text-danger").append($i).show();
	}
	

	//验证码
	$("#J_StandardCode, #J_StandardCode_m ").on("click",function(e){	
		var $codeImg=$("#J_StandardCode_m");
		var imgUrl=$codeImg.attr("data-src")+"?_r_"+Math.random();
		$codeImg.attr("src",imgUrl);
	});
	var _login_times=$("#l_f_code").attr("data");
	if(_login_times>=2){
		showLoginError("为了您的账户安全，请输入验证码。");
		$("#l_f_code").show();
		$("#J_StandardCode").click();
	}else{
		$("#l_f_code").hide();
	}
	//键盘监听
	$("#J_PassWord").on("keyup keydown",function(e){
		if(e.keyCode==20){
			$("#J_CapsLockTip").show();
		}else{
			$("#J_CapsLockTip").hide();
		}
	});
	if($loginBox.hasClass("loading")){
		$loginBox.removeClass("loading");
		$("#J_Static").show();
	}
	
	
	$("#J_QRCodeHandle").on("click",function(){
		var $QRCodeImg=$("#J_QRCodeImg");
		var imgUrl=$QRCodeImg.attr("data-src")+"&_r_"+Math.random();
		$QRCodeImg.attr("src",imgUrl);
		$QRCodeImg.addClass("bounceInLeft");
		 setTimeout(function(){
			 $QRCodeImg.removeClass('bounceInLeft');
		 }, 1000);
		
	});
	$("#J_2QRCode").on("click",function(){
		$("#J_Static").hide();
		$("#J_QRCodeLogin").show();
		$("#J_2Login").show();
		$("#J_QRCodeHandle").click();
	});
	$("#J_2Login").on("click",function(){
		$("#J_Static").show();
		$("#J_2QRCode").show();
		$("#J_QRCodeLogin").hide();
	});
});