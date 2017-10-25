define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var template = require('arttemplate');
	var url = {
		api : baseRoot + "/admin/article",
		createApi:baseRoot + "/admin/article/save.json",
	};
	var editormd = require('editor.md');
	var $httpUtil = require('/js/common/httpUtil.js');
	var dialog = require('/js/common/dialog');
	 require('/js/common/validate');
	var $msg= require('/js/common/alerts.js');
	window.dialog = dialog;
	require('link-dialog');
	require('reference-link-dialog');
	require('code-block-dialog');
	require('image-dialog');
	require('table-dialog');
	require('emoji-dialog');
	require('goto-line-dialog');
	require('html-entities-dialog');
	require('help-dialog');
	require('preformatted-text-dialog');
	var testEditor=editormd("test-editormd", {
        height: 460,
        path : '/modules/editor.md/lib/',
        //toolbar  : false,             // 关闭工具栏
        codeFold : true,
        searchReplace : true,
        saveHTMLToTextarea : true,      // 保存 HTML 到 Textarea
        htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        emoji : true,
        taskList : true,
        tocm            : true,          // Using [TOCM]
        tex : true,                      // 开启科学公式 TeX 语言支持，默认关闭
        atLink: true, 
        flowChart : false,                // 疑似 Sea.js与 Raphael.js 有冲突，必须先加载 Raphael.js ，Editor.md 才能在 Sea.js 下正常进行；
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为 0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为 #fff
        saveHTMLToTextarea : true,
        imageUpload : true,
        imageUploadURL : "./admin/upload.php",
        onload : function() {
      //      console.log('onload', this);
//            this.fullscreen();
//            this.unwatch();
//            this.watch().fullscreen();
// 			  this.setMarkdown("#PHP"+this.settings.pluginPath); 
//            this.width("100%");
//            this.height(480);
//            this.resize("100%", 640);
        }
    });	
	 var $mainForm=$("#J_articleForm");
	 $mainForm.validate({
			rules: {
				title: {
					 required: true,
				},
			},
			messages:{
				title: {
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
		 var md=testEditor.getMarkdown();       // 获取 Markdown 源码
		 if(md.length<=0){
			 $msg.alert($btn,"<i class='fa fa-exclamation-triangle text-danger'></i>文章内容不能为空");
			 return;
		 }
		 testEditor.getHTML();           // 获取 Textarea 保存的 HTML 源码
		 testEditor.getPreviewedHTML();  // 获取预览窗口里的 HTML，在开启 watch 且没有开启 saveHTMLToTextarea 时使用
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
						}, 500);
					}else{
						$msg.alert($btn,"<i class='fa fa-exclamation-triangle text-danger'></i>数据更新异常");
					}
					
				}
			}); 
			$btn.html(dataHtml).removeAttr("disabled");
		});
	
	
});