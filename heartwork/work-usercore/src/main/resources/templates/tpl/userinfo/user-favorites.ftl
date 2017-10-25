<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>用户头像</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
  <#include "/includes/uploadify-css.ftl" />
   <style>
  .main-container{
   	padding:0px 100px;
   	margin-top: 40px;
   	height:583px;
   }
  .user-panel{
  	width:261px;
  }
   .user-img ,.thumbnail{
  	width:100px;
  	height:100px;
  }
   .user-img .thumbnail img{
  	height:100%;
  }
   </style>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
	<div class="clearfix"></div>
    <div class="full-container main-container">
   	   	<div class="row">
		  <div class="col-xs-3">
		  <!--用户面板 begin--->
		  	<div class="panel panel-default user-panel">
			  <div class="panel-body">
			     <!--userInfo begin---->
	 	 		 <#include "/userinfo/user-media.ftl" />
	 	 		 <!--userInfo end---->
			  </div>
			  <!-- List group begin-->
			  <#include "/userinfo/user-menu.ftl" />
			  <!-- List group end-->
			  <div class="panel-footer">
			  	
			  </div>
			</div>
		  <!--用户面板 end--->
		  
		  </div>
		  <div class="col-xs-9">
			<div class="panel panel-info">
				<div class="panel-heading">
			        <h3 class="panel-title"><i class="fa fa-tag"></i></h3>
			    </div>
			  <div class="panel-body" style="min-height:500px;">
			   <div class="row" >
				   	<div class="col-xs-8">
				   		<div class="panel panel-default">
						  <div class="panel-heading">
						  <input id="file_upload" name="file_upload" class="btn btn-default" type="file" multiple="true">
						  <!-批量上传按钮-->  
						  </div>
						  <div class="panel-body">
						    <div id="queue"></div><!-上传队列展示区-->  
						  </div>
						</div>
				   	</div>
				   	<div class="col-xs-4">
				   		<h4>效果预览</h4>
				   		<p>
				   		你上传的图片会自动生成2种尺寸，请注意小尺寸的头像是否清晰
				   		</p>
				   		<div class="cropped"></div>
				   	</div>
			   </div>
			  </div>
			</div>
		  </div>
		</div>
    </div>
   	<!------footer信息 begin----->
  	<#include "/includes/footer.ftl" />
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
	<!------seajs.config 引用信息 begin----->
	<#include "/includes/seajs.config.ftl" />
	<!------seajs.config   引用信息 end----->
<script>
  	seajs.use(["js/userinfo/user-sign.js?v="+Math.random()]);
</script>
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<#include "/includes/uploadify-js.ftl" />

	<script type="text/javascript">
    $(function() {
      $('#file_upload').uploadify({
				'swf'      : '${contextPath}/modules/uploadify/uploadify.swf',
				'uploader' : '${contextPath}/upload/userHeadImg.json',
				'width' : '200',
				
				//'auto'     : false,//关闭
				'fileObjName'   : 'file_upload',  
				 'buttonText'     : '<i class="fa fa-file-image-o"></i>    请选择您要上传的头像',
				 'fileTypeExts': '*.gif; *.jpg; *.png *.JPEG *.BMP',//允许上传文件类型
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