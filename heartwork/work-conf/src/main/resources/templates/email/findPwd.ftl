<!DOCTYPE html>
<html lang="zh-CN">
  <head> 
  <title>用户邮箱激活</title>
    <meta charset="utf-8">
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
	<style>
	  .text-sj{ 
			text-indent: 2em; /*em是相对单位，2em即现在一个字大小的两倍*/ 
	   }
	</style>
  </head>
  <body class="home-template">
    <div class="container  center-block">
     <div class="page-header">
	  <h3>尊敬的用户，您好：<small></small></h3>
	  	<p class="text-sj" >你的验证码是:  ${emailCode?string('00000')} ,半小时内有效!请尽快完成注册!</p>
	 </div>
    </div>
</body>
</html>