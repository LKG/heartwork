<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>视频搜索</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
    <#assign template="vis"/>
   <style>
   .main-container{
   		padding:0px 100px;
   }
   .v-thumb{
   	width: 228px;
    height: 126px;
    overflow: hidden;
   }
   .v-thumb img{
		position: relative;
	    width: 200px;
	    height: 112px;
	    margin-top: 0;
	    z-index: 5;
   }
   </style>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
    <div class="full-container main-container clearfix">
	   	   	<div class="panel panel-default">
			  <div class="panel-heading">
			  	<form class="">
				  <div class="form-group">
				     <div class="input-group">
				      <input type="text" class="form-control " name="s" value="${s}"  placeholder="Search for...">
				      <span class="input-group-btn">
				        <button class="btn btn-default" type="submit">搜索</button>
				      </span>
				    </div><!-- /input-group -->
				  </div>
				</form>
			  </div>
			  <div class="panel-body">
			    <div class="row">
			   	    <#list result as model>
			   	      <div class="col-sm-6 col-md-4">
				   	      	<div class="panel panel-default">  
				   	      		<div class="panel-body">
					   	      		<img src="${model.image}"  alt="${model.title}">
				   	      		</div>
				   	      	</div>
			   	      	</div>
			     	</#list>
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

 </body>
</html>