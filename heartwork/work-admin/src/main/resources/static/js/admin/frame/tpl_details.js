define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var template = require('arttemplate');
	var url = {
		api : baseRoot + "/admin/tpl",
		createApi:baseRoot + "/admin/tpl/save.json",
	};

	var $httpUtil = require('/js/common/httpUtil.js');
	var dialog = require('/js/common/dialog');
	window.dialog = dialog;
	 require('/js/common/validate');
	var $msg= require('/js/common/alerts.js');
	 var $mainForm=$("#J_articleForm");
	 $mainForm.validate({
			rules: {
				tplType: {
					 required: true,
				},
				tplCode: {
					 required: true,
				},
				tplName: {
					 required: true,
				},
			},
			messages:{
				tplType: {
					 required: "模板类型不能为空",
				},
				tplCode: {
					 required: "模板代号不能为空",
				},
				tplName: {
					 required: "标题不能为空",
				},
			
			},
			submitHandler: function(form) { 
			}
		});
	 $("#heading-btn-save,#footer-btn-save").on("click",function(){
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
					}else{
						$msg.alert($btn,"<i class='fa fa-exclamation-triangle text-danger'></i>数据更新异常");
					}
					
				}
			}); 
			$btn.html(dataHtml).removeAttr("disabled");
		});
});