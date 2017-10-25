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
	//发送短信验证码
	$("#J_PhoneCodeBtn").on("click",function(){
		var key = $("#J_ForgotpasswdForm #key").val();
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
		$.get(baseRoot+"/findPwd/passmobliecode.json",{k:key},function(json){
			var content="验证码发送成功";
			 if(!json.success){
				 content=json.result.error_description;
			 }
			 var d = dialog({
					id : key,
				    title: '系统提示',
				    content: content,
				    okValue: '确定',
				    ok: function () {
				        this.title('关闭中…').close().remove();
				        $userPhone.focus();
				        return false;
				    },
				    cancelValue: '取消',
				});
				d.showModal();
				setTimeout(function() {
					d.close().remove();
					$userPhone.focus();
				}, 2000);
				return;
		},"json");
		
	});

	$("#J_Type_choose").on("change",function(){
		var type=$(this).val();
		if(type=="1"){
			$(".phone_div").show();
			$(".email_div").hide();
		}else{
			$(".phone_div").hide();
			$(".email_div").show();
		}
		
	});
	$("#J_ForgotpasswdForm").validate({
		rules: {
			phoneCode: {
				 required: true,
				 maxlength: 5,
				 minlength: 5,
				 remote: {
					 url: baseRoot+"/findPwd/checkMobliecode.json",
                    type: "get",               //数据发送方式
                    dataType: "json",
                    data: {
                   	 	k: function () {
                            return $("#J_ForgotpasswdForm #key").val();;
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
		},
		messages:{
			phoneCode: {
				 required: "请输入收到的短信码",
				 remote: "短信码错误"
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
	$("#J_PhoneSubmit,#J_PhoneSubmit").on("click",function(){
		var $form=$('#J_ForgotpasswdForm');
		
		var data = getFormData($form);
		var $btn=$(this);
		var dataHtml=$(this).html();
		var url=$form.attr("action");
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		$form.submit();
		$btn.html(dataHtml).removeAttr("disabled");
	});
	
	
});