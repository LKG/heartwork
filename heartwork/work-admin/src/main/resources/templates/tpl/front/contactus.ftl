<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>关于我们</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
<#assign template="helps"/>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
	<div class="container" id="main-container">
		<div class="row">
			<div class="col-xs-3">
				<div class="panel panel-default" id="user-info-panel" >
			  <div class="panel-heading" id="user_<@shiro.principal property='userId' />">
			  	
			  </div>
			  <div class="panel-body">
			   	
			  </div>
			  <div class="list-group" id="user-info-setting" >
			     <a href="${contextPath}/index/agreements.html" class="list-group-item ">服务协议</a>
				 <a href="${contextPath}/index/questions.html" class="list-group-item">常见问题</a>
				  <!--
				  <a href="${contextPath}/index/partners.html" class="list-group-item">合作伙伴</a>
				  --->
				 <a href="${contextPath}/index/contactus.html" class="list-group-item active">联系我们</a>
				 <a href="${contextPath}/index/aboutus.html" class="list-group-item ">关于我们</a>
			  </div>
			</div>
			</div>
			<div class="col-xs-9" >
				<div class="g-inner m-contactus fl">
                <h2>联系我们</h2>

                <div class="m-contactus-line mt20">
                    <div class="m-contactus-line-lift">
                       <p> <i class="fa fa-phone-square fa-3x"></i>     众筹客服电话: 400-987-6088（24h）</p> 
                    </div>
                </div>

                <div class="m-contactus-line">
                    <div class="m-contactus-line-lift">
                       	<i class="fa fa-envelope fa-3x"></i>     客服邮箱: service@jiruikeji.com
                    </div>
                </div>

                <div class="m-contactus-line">
                    <div class="m-contactus-line-lift">
                        <i class="fa fa-map-marker fa-3x"></i>    地址: 杭州市上城区长生路58号西湖国贸中心612室
                    </div>
                    <div class="m-contactus-line-right">
                        <div class="m-contactus-line-right-img1">
                        <img src="http://api.map.baidu.com/staticimage?markers=极瑞科技|120.1668,30.263019&center=120.1668,30.263019&width=600&height=300&zoom=17"/>
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

</body>
</html>