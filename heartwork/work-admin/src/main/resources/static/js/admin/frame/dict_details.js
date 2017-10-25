define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var template = require('arttemplate');
	var dialog = require('/js/common/dialog');
	window.dialog = dialog;
	 require('/js/common/validate');
	var $msg= require('/js/common/alerts.js');
	var $status=$("#J_MainForm").find("#status");
	$status.val($status.attr("data"));
	 var $mainForm=$("#J_MainForm");
	 var url={
		checkCode: baseRoot+"/admin/dict/checkCode.json",
		createApi: baseRoot+"/admin/dict/save.json",
	};
	 $mainForm.find("#dictType").on("change",function(){
		 $mainForm.find("#dictValue").val("");
		 if($(this).val()=="multiple"||$(this).val()=="2"){
			 $mainForm.find("#dictValue-div").hide();
			 $mainForm.find("#dictValue").val($mainForm.find("#dictCode").val());
		 }else{
			 $mainForm.find("#dictValue-div").show();
		 }
	 });
	 
	 $mainForm.validate({
			rules: {
				dictCode: {
					 required: true,
					 minlength: 3,
					 maxlength: 30,
					 isNotChinese: true,
					 isUid:true,//检验特殊字符
					 remote: {
						 url: url.checkCode,
	                     type: "get",               //数据发送方式
	                     dataType: "json",
	                     data: {
	                    	 dictCode: function () {
	                             return $mainForm.find("#dictCode").val();
	                         }
	                     },dataFilter: function (data) {
	                    	 var json=$.parseJSON(data);
	                    	return ""+json.result;
	                     }
					 }
				},
				dictValue: {
					 required: true,
				},
				dictName: {
					 required: true,
				},
			},
			messages:{
				dictCode: {
					 required: "字典名称不能为空",
					 remote: "字典标识已经被使用",	 
					 isUid:  "字典标识非法",//检验特殊字符
				},
				dictName: {
					 required: "字典名称不能为空",
				},
				dictValue: {
					 required: "字典值不能为空",
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
						}, 1000);
					}else{
						$msg.alert($btn,"<i class='fa fa-exclamation-triangle text-danger'></i>数据更新异常");
					}
					
				}
			}); 
			$btn.html(dataHtml).removeAttr("disabled");
		});
});