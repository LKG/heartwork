define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var template = require('arttemplate');
	var dialog = require('/js/common/dialog');
	window.dialog = dialog;
	 require('/js/common/validate');
	var $msg= require('/js/common/alerts.js');
	 $("#status").val($("#status").attr("data"));
	 var $mainForm=$("#J_MainForm");
	 var url={
		checkCode: baseRoot+"/admin/role/checkRoleCode.json",
		createApi: baseRoot+"/admin/role/save.json",
	};
	 $mainForm.validate({
			rules: {
				roleCode: {
					 required: true,
					 minlength: 3,
					 maxlength: 30,
					 isUid:true,//检验特殊字符
					 remote: {
						 url: url.checkCode,
	                     type: "get",               //数据发送方式
	                     dataType: "json",
	                     data: {
	                    	 code: function () {
	                             return $mainForm.find("#roleCode").val();
	                         }
	                     },dataFilter: function (data) {
	                    	 var json=$.parseJSON(data);
	                    	return ""+json.result;
	                     }
					 }
				},
				roleName: {
					 required: true,
					 minlength: 2,
					 maxlength: 30,
				},
			},
			messages:{
				roleCode: {
					 required:  "代号不能为空",
					 remote: "代号已经被使用",
				},
				roleName: {
					 required: "名称不能为空",
				},
			
			},
			submitHandler: function(form) { 
//				form.action=url.createArea ;
//				form.submit();
			}
		});
	   	var topdialog = top.dialog.get(window);
	   	if(undefined===topdialog){
	   		topdialog=dialog.getCurrent();
	   	}
	 $("#J_Submit").on("click",function(){
			 if(!$mainForm.valid()){
				 return;
			 }
			var $btn=$(this);
			var dataHtml=$(this).html();
			$mainForm.attr("action",url.createApi);
			$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
			$mainForm.ajaxSubmit({
				url:url.createApi,
				type:"post",
				success: function(data) {
					if(data.success){
						$msg.alert($btn,"<i class='fa  fa-check-circle text-success'></i>数据更新成功");
						setTimeout(function() {
							topdialog.close().remove();
						}, 1500);
					}else{
						$msg.alert($btn,"<i class='fa fa-exclamation-triangle text-danger'></i>数据更新异常");
					}
					
				}
			}); 
			$btn.html(dataHtml).removeAttr("disabled");
		});
});