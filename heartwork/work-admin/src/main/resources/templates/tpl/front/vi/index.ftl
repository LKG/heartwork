<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>视频解析</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
      <#include "/includes/mediaelement-css.ftl" />
    <#assign template="vi"/>
   <style>
   .main-container{
   		padding:0px 100px;
   }
   .panel-media{
  	 width:915px;
  	 height: 514px;
  	 border-right:0px;
  	 -webkit-border-top-right-radius:0px;
	 border-top-right-radius:0px;
	 -webkit-border-bottom-right-radius:0px;
	 -webkit-border-bottom-right-radius:0px;
   }
   .panel-media-list{
   		top:0px;position: absolute;left: 930px;height: 609px;
   		border-left:0px;
   		-webkit-border-top-left-radius:0px;
   		border-top-left-radius:0px;
   		-webkit-border-bottom-left-radius:0px;
   		border-bottom-left-radius:0px;
   }
   #player-body{
		height: 514px;
  		width:914px;
   		padding: 0px 0px;
   		margin-top: -1px; 
   		/** margin-bottom: -5px;**/
   }
    #player-body video{
  		width: 914px;
  		height: 514px;
   }
   </style>
</head> 
<body class="page-header-fixed">
	<!------页面header信息 begin----->
	<#include "/pages/header-navbar-top.ftl" />
	<!------页面header信息 end----->
    <div class="full-container main-container clearfix">
   	   	<div class="row">
		  <div class="col-xs-12">
			  <div class="players" id="player-container"> 
			  <!--media-wrapper begin--->
				  <div class="media-wrapper clearfix">
				  	<!--panel begin--->
				  	<div class="panel panel-default panel-media">
				  		<div class="panel-heading panel-primary">视频</div>
					   <div class="panel-body" id="player-body">
					    <video id="player-video"   poster="http://www.mediaelementjs.com/images/big_buck_bunny.jpg"  playsinline webkit-playsinline controls="controls" preload="none">
							<source src="http://222.73.132.159/variety.tc.qq.com/AmUmXGhyc56f7WYmNP_f_l-Ie887GJlLjYZQpk9Pkbos/q00240jc13s.p209.1.mp4?sdtfrom=v1010&platform=2&guid=fb74ffcc7b14377db9cb5308e598d6e5&vkey=A0CF7F739B690E9566926FCDA7B21365AB5FF9A1EE18775C701E3D99162338B5E5AA6D05138D2AB81E08596450DE6D31E983E7BE6C7B2F179935A7D6BF82BB1007C2C6428E0C688377C3506E6D257E16D773A00B857FDABB0785F269A33AEC4565219FE6FB13CA67CB502723CBE01DC6" type="video/mp4">
		         
							<source type="video/webm" src="myvideo.webm" />
							<source type="video/ogg" src="myvideo.ogv" />
							<object type="application/x-shockwave-flash" data="http://releases.flowplayer.org/swf/flowplayer-3.2.1.swf">
								<param name="movie" value="http://releases.flowplayer.org/swf/flowplayer-3.2.1.swf" />
								<param name="flashvars" value="controls=true&amp;poster=myvideo.jpg&amp;file=myvideo.mp4" />
								<img src="http://www.mediaelementjs.com/images/big_buck_bunny.jpg" width="320" height="240" title="No video playback capabilities" />
							</object>
						</video>
					  </div>
					  <div class="panel-footer">
					  	<button class="btn btn-default" id="collectBtn"><i class="fa fa-heart-o"></i>收藏
					  	
					  	
                    	</button>
                    	<p class="pull-right" ><i class="fa fa-thumbs-o-up"></i>赞 <span>0</span> <i class="fa fa-thumbs-o-down"></i>踩<span>0</span></p>
					  </div>
					</div>
					<!--panel end--->
					     <div class="panel panel-default panel-media-list" > 
					     	<div class="panel-heading panel-primary ">选集</div>
		            	 	<div class="panel-body">
		            	 		<ul class="list-group">
								  <li class="list-group-item">Cras justo odioCras justo odioCras justo odioCras justo odioCras </li>
								  <li class="list-group-item">Dapibus ac facilisis in</li>
								  <li class="list-group-item">Morbi leo risus</li>
								  <li class="list-group-item">Porta ac consectetur ac</li>
								  <li class="list-group-item">Vestibulum at eros</li>
								</ul>
		
							</div>
		            	 </div>
            	</div><!---media-wrapper end--->
     
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
	<#include "/includes/mediaelement-js.ftl" />
 </body>
</html>