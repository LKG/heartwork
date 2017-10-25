<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>常见问题</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${ver!'1'}" />
  <#include "/includes/head.ftl" />
  <style>
  .m-faq{
  	padding-left:20px;
  }
  .m-FAQ{
  	padding:5px 0px;
  	border-bottom: 1px solid #ddd;
  }
  </style>
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
				 <a href="${contextPath}/index/questions.html" class="list-group-item active">常见问题</a>
				  <!--
				  <a href="${contextPath}/index/partners.html" class="list-group-item">合作伙伴</a>
				  --->
				 <a href="${contextPath}/index/contactus.html" class="list-group-item">联系我们</a>
				 <a href="${contextPath}/index/aboutus.html" class="list-group-item">关于我们</a>
			  </div>
			</div>
			</div>
			<div class="col-xs-9" >
				<html>
 <head></head>
 <body>
  <div id="list">
   <h2>常见问题</h2>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题1</span> <span class="m-faq" >什么是众筹？ </span></h6>
    <p>
     <em>筹房网：</em>Crowdfunding，中文译为众筹。是指发起人在互联网上发布某一 筹款项目，然后通过团购加预购的形式，向大众筹集资金，筹款成功后，发起人将实物、现金收益或其他权益作为回报给大众。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题2</span> <span class="m-faq" >什么是房地产众筹？</span></h6>
    <p>
     <em>筹房网：</em>与房地产相关联的众筹项目，包括众筹买地、众筹建设、众筹买房等。众筹成功后，参与者将可享受现金收益、购房优惠、个性定制、优先选房等特权。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题3</span> <span class="m-faq" >客户资金安全如何保障？</span></h6>
   
    <p >
     <em>筹房网：</em>众筹房网的项目产品在众筹过程中所涉及的各个交易环节均严格依照法律法规执行，交由专业团队严格审核把关，并对资金安全进行严格监管。众筹者所有资金全权托管于第三方托管账户，全程定向流入流出，并在个人中心更新资金明细；众筹者和房产开发商双方会根据协议，项目全权由第三方资产管理公司管理，保障众筹者的合法权益和利益。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题4</span>  <span class="m-faq" >筹参与者可否优先选房？</span></h6>
  
    <p>
     <em>筹房网：</em>众筹参与者享有优先选房的权利，通过在线选房或与私人客服沟通确认房号。若没有选中理想房子，可以放弃购房但仍享受投资收益。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题5</span> <span class="m-faq" >众筹金可随时退款吗？</span></h6>
    <p>
     <em>筹房网：</em>众筹参与者成功支付众筹金后，在参与报名期限届满前都可无条件申请退款；期限届满后，用户认筹款将被冻结无法赎回；若众筹目标未达成，用户认筹款将在“众筹失败”信息公示后15个工作日内全额退还。 
   </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题6</span> <span class="m-faq" >积分的用处是什么，如何获得？</span></h6>
    <p>
     <em>筹房网：</em>注册、认筹、推荐好友、上传分享等方式均可获得相应积分。积分可在积分商店中兑换多种礼品或购房优惠，具体内容根据具体活动来定。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题7</span><span class="m-faq" >众筹房网是否收取其他费用？</span></h6>
    <p>
     <em>筹房网：</em>众筹过程中不会向众筹参与者收取任何额外费用。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题8</span> <span class="m-faq" >众筹房网可否保证投资收益？</span></h6>
    <p>
     <em>筹房网：</em>众筹房网作为项目众筹交易平台，仅提供信息发布服务，参与众筹方与房产开发商（或持有人）本着自愿的原则完成相关交易，因此众筹网不对项目本身承诺任何预期收益；认筹用户需自行承担项目潜在收益风险。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题9</span> <span class="m-faq" >为何选择众筹房网？</span></h6>
    <p>
     <em>筹房网：</em>“众筹房网”是中国最大的房产众筹平台，拥有高性价比、个性定制、全程保险、金融回馈等多重优势，并以保险公司担保作为众筹者利益保障，并给予众筹者一定的现金回报，同时为众筹者提供包括购房优惠、选房特权、定制服务、客服服务等多种衍生服务。
    </p>
   </div>
   <div class="m-FAQ">
    <h6><span class="label label-success">问题10</span> <span class="m-faq" >众筹房网是否有涉及非法集资的嫌疑？</span></h6>
    <p>
     <em>筹房网：</em>根据各法律、司法解释及法学专家意见，&quot;行为人非法吸收公众存款并用于货币资本的经营（如发放贷款），并以股权、货币或实物作为回报，承诺向投资人进行还本付息或给付回报的行为，可以被认定为非法集资&quot;。而众筹房网则是采用参与人自行委托购买并处分已确定的目标房产的方式，参与人自负风险、享受收益，与非法集资有本质的区别。
    </p>
   </div>
  </div>
 </body>
</html>
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