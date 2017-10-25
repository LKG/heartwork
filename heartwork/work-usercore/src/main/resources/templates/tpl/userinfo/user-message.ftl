<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>消息中心</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
    <#assign template="message"/>
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
			  	<div class="list-group">
			  	<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
				    <ul id="myTabs" class="nav nav-tabs" role="tablist">
				      <li role="presentation" class="active">
				      	<a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true"><i class="fa fa-inbox " ></i> 收件箱</a>
				      </li>
				      <li role="presentation" >
				      	<a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true"><i class="fa fa-envelope-o" ></i>  已发送</a>
				      </li>
				            <li role="presentation" >
				      	<a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true"><i class="fa fa-file-text-o" ></i> 草稿箱</a>
				      </li>
				    </ul>
				    <div id="myTabContent" class="tab-content">
				      <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
				       <table class="table table-bordered table-condensed table-hover">
				        <thead>
				          <tr>
				           <th  class="text-center bs-checkbox" style="width: 30px;"  >
						       <input type="checkbox"  class="text-center bs-checkbox-selectAll"  />
						   </th>
				            <th class="page-sort-btn">标题</th>
				            <th style="width:100px;">类型</th>
				            <th style="width:50px;" >状态</th>
				            <th style="width:250px;" >操作</th>
				          </tr>
				        </thead>
				             <tbody  id="table-tbody" >
				     	   <#if (result.content?size<=0) >
	          				 <tr ><td class="text-center" scope="row" colspan='5' ><@spring.message  code="label.default.empty" /></td></tr>
						 <#else>
						    <#list result.content as model>
						    	<tr > 
						    		<td scope="row" class="text-center" >
								         <input type="checkbox" id="check_${model.id}" name="id" value="${model.id}" />
								    </td>
								     <td title="${model.title}">${model.title}</td>
								     <td class="text-center" >${model.type}</td>
								     <td class="text-center" >
								   
								     </td>
				 					<td class="operate text-center">
				 		
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