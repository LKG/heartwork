<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title><@spring.message  code="label.system.index" /></title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
    <#include "/includes/swiper-css.ftl" />
    <#assign template=""/>
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
	.swiper-container {
        width: 100%;
        height: 100%;
        max-height: 470px;
    }
    .swiper-slide {
        background-position: center;
        background-size: cover;
        height: 100%;
    }
    .swiper-slide img{
    	 width: 100%;
    }
</style>
    
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
	<div class="full-container clearfix">
		<#include "/pages/swiper.ftl" />
	</div>
	<#include "/index-home.ftl" />
   	<!------footer信息 begin----->
   	<#include "/includes/footer.ftl" />
  	<#include "/includes/footer-js.ftl" />
  	<!------footer信息 end----->
<!------seajs.config 引用信息 begin----->
    <#include "/includes/swiper-js.ftl" />
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use("js/index/index.js?v=${ver!'1'}");
</script>

</body>
</html>