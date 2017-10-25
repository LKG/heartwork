define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	
	var check=function(password){
		 var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
		 var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
		 var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
			if (false == enoughRegex.test(password)) { 
				$("#pass_progress").attr("aria-valuenow","33").css("width","33%");
				$("#pass_progress_text").html("弱");
			} else if (strongRegex.test(password)) { 
				$("#pass_progress").attr("aria-valuenow","100").css("width","100%");
				$("#pass_progress_text").html("高");
				 //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强 
			}else if (mediumRegex.test(password)) { 
				 //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等 
				$("#pass_progress").attr("aria-valuenow","66").css("width","63%");
				$("#pass_progress_text").html("中");
			}else { 
				 //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的 
				$("#pass_progress").attr("aria-valuenow","33").css("width","33%");
				$("#pass_progress_text").html("弱");
			} 
	}
	
	 $("#passWord").on("keyup",function(){
		 	check($(this).val());
			return true; 
	 });
});