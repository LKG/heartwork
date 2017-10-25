<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>用户资料</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
   <#assign template="level"/>
    <style>
  .main-container{
   	padding:0px 100px;
   	margin-top: 40px;
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
					   您的会员级别是：<code><@shiro.principal property='userLevel'/></code>
					会员级别有效期：<code><@shiro.principal property='expiryTime' defaultValue='不详'/></code>
					您目前的成长值为：<code><@shiro.principal property='userPoint'/> </code>
					  </p>
				</div>
				<!-- Table -->
				 <table class="table table-bordered">
			      <thead>
			        <tr>
			          <th>#</th>
			          <th>发放/减扣时间</th>
			          <th>成长值</th>
			          <th>成长值来源</th>
			          <th>来源详情</th>
			        </tr>
			      </thead>
			      <tbody>
			          <#if result??&&(result.content?size<=0) >
				      	 <tr >
				      	 	<td class="text-center" scope="row" colspan='6' > 无记录</td>
				      	 </tr>
				      </#if>
				       <#list result.content as model>	
				       <tr <#if (model_index%2)=0 >class="info"</#if>>
				         <th scope="row">${model_index+1}</th>
				         <td>${model.createTime}</td>
				         <td>${model.point}</td>
				         <td>${model.type}</td>
				         <td>${model.memo}</td>
				       </tr>
				 	 </#list >
			      </tbody>
			    </table>
			    <code>仅显示最近20条记录 </code>
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