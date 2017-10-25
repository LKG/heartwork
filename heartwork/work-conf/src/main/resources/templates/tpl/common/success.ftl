<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<title>数据更新成功提示页面</title>
</head> 
<body class="page-header-fixed">
<div class="container-fluid" id="main-add-container" style="padding:0 0px">
<div class="panel panel-default">
  <div class="panel-body center-block">
	<i class="fa fa-check-circle"></i>数据更新成功  ,<i class="fa fa-time"></i><code>1</code>秒后自动关闭
  </div>
</div>
 
</div>
 <!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />
<script>
 seajs.use(['$', 'artDialog'], function($,artDialog){
   	var dialog = top.dialog.get(window);
   	if(undefined===dialog){
   		dialog=artDialog.getCurrent();
   	}
   dialog.title('数据更新成功');
	setTimeout(function() {
		dialog.close().remove();
	}, 1000);
  });
</script>
<!------seajs.config   引用信息 end----->
</html>