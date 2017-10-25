<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>我的工作台</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
    <#include "/includes/uploadify-css.ftl" />
    <#assign template="desk"/>
    <style>
	.tab-pane{
		padding-top:30px;
	}
	.panel-heading {
		padding: 5px 13px;
	}
	.panel-heading  h4{
		border-left: 2px solid #f35d5d;
		padding-left:15px;
		margin-left:-13px;
	}
	.user-panel{
		min-heigth:500px;
	}
</style>
    
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<div class="clearfix" ></div>
	<!------页面header信息 end----->
	<div class="full-container" id="main-container" style="height: 531px;">
		<p class="text-center" style="margin-left: 45%;">
          <a class="btn btn-lg btn-primary btn-shadow" href="#" id="file_upload" role="button" ><i class="fa fa-cloud-upload"></i>导入文件</a>
        </p>
        <div class="progress">
		  <div class="progress-bar progress-bar-success progress-bar-striped active" style="width: 35%">
		    <span class="sr-only">35% Complete (success)</span>
		  </div>
		  <div class="progress-bar progress-bar-success progress-bar-striped active" style="width: 20%">
		    <span class="sr-only">20% Complete (warning)</span>
		  </div>
		  <div class="progress-bar progress-bar-success progress-bar-striped" style="width: 10%">
		    <span class="sr-only">10% Complete (danger)</span>
		  </div>
		</div>
	</div>
   	<!------footer信息 begin----->
   	<#include "/includes/footer.ftl" />
  	<#include "/includes/footer-js.ftl" />
  	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js" ></script>
  	<!------footer信息 end----->
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<#include "/includes/uploadify-js.ftl" />

	<script type="text/javascript">
    $(function() {
      $('#file_upload').uploadify({
				'swf'      : '${contextPath}/modules/uploadify/uploadify.swf',
				'uploader' : '${contextPath}/upload.json',
				'buttonClass' : 'btn btn-lg btn-primary btn-shadow',
				//'auto'     : false,//关闭
				'width' : '126',
				'height' : '46',
				'fileObjName'   : 'file_upload',  
				 'buttonText'     : '<i class="fa fa-cloud-upload"></i> 导入文件',
				 'fileTypeExts': '*.xml',//允许上传文件类型
				 'fileTypeDesc': '仅支持JPG、GIF、PNG、JPEG、BMP格式，文件小于4M',//允许上传文件类型描述
				 'onUploadComplete': function(file, data, response) {
				 	
				 },
				 'onUploadError': function(file, data, response) {
				 	
				 },
				 'onCancel': function(file, data, response) {
				 	//alert($('#' + file.id).find('.data').html(' 上传完毕'));
				 
				 },
				 
				 'onUploadSuccess' : function(file, data, response) {
				  $('#' + file.id).find('.data').html(' 上传完毕');
				 　/**　
				 alert('id: ' + file.id
				　　+ ' - 索引: ' + file.index
				　　+ ' - 文件名: ' + file.name
				　　+ ' - 文件大小: ' + file.size
				　　+ ' - 类型: ' + file.type
				　　+ ' - 创建日期: ' + file.creationdate
				　　+ ' - 修改日期: ' + file.modificationdate
				　　+ ' - 文件状态: ' + file.filestatus
				　　+ ' - 服务器端消息: ' + data
				　　+ ' - 是否上传成功: ' + response);
				
				*/
				 },
			});
    });
</script>
</body>
</html>