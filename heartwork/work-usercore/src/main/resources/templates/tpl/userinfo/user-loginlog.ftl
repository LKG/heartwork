<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>登录日志</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
    <#assign template="safe"/>
   <style>
   .main-container{
   		padding:0px 100px;
   		height:583px;
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
			  	<div class="alert alert-success" role="alert">
			  		<p>
			  		最多显示<code>20条</code>登录记录
			  		，请您核对登录时间及地点，如您确定在某时间未登录过，请立即“<a href="${contextPath}/userinfo/changePwd.jhtml" >修改登录密码</a>”以保障账号安全
						因宽带提供商导致的IP地址变化，登录地点有可能显示不准确，请您以登录时间为参考基准
			  		 <a class="pull-right" href="${contextPath}/userinfo/safe.jhtml">返回安全中心</a>
			  		</p>
				</div>
			
			  <!-- Table -->
				 <table class="table table-responsive table-striped table-bordered table-condensed table-hover">
			      <thead>
			        <tr>
			          <th class="text-center">登录时间</th>
			          <th class="text-center">登录地点</th>
			          <th class="text-center">ip地址</th>
			          <th class="text-center">登录方式</th>
			        </tr>
			      </thead>
			      <tbody>
			       <#if (result.content?size<=0) >
	          		 <tr ><td class="text-center" scope="row" colspan='7' ><@spring.message  code="label.default.empty" /></td></tr>
				   <#else>
					    <#list result.content as model>
					    	<tr > 
							    <td  class="text-center" >
							    	${model.createTime}
							    </td>
							    <td  class="text-center" >
							    	${model.userHost!''}
							    </td>
							    <td  class="text-center" >
							    	${model.userHost!''}
							    </td>
							    <td  class="text-center" >
							    	<#if (model.logType=='login') >登录</#if>
							    	<#if (model.logType=='logout') >登出</#if>
							    </td>
					    	</tr>
					    </#list >			       	 
					  </#if>
			      </tbody>
			    </table>
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
 </body>
</html>