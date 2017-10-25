<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<html>
 <head> 
  	<title><@spring.message  code="label.login.title" /></title>
	<!------head 引用信息 begin----->
  	<#include "/includes/head.ftl" />
  	<link rel="stylesheet" type="text/css" href="${contextPath}/css/sigin-in.css?v=${ver!'1'}" /> 
  	<!------head   引用信息 end----->
 </head> 
 <body> 
  <div id="page2"> 
   <div id="content"> 
    <div id="J_LoginBox" class="login-box "> 
     <script type="text/javascript">(function(x){x&&(x.className+=" loading")})(document.getElementById("J_LoginBox"))</script> 
     <div class="bd"> 
      <!--登录的错误信息结束--> 
      <!--标准登录框--> 
      <div id="J_Static" class="login-box-switch static" style="display:none;"> 
       <a href="javascript:;" id="J_SC_Guide" tabindex="1" style="position:absolute; left:-9999px;">点击回车可关闭安全控件</a> 
       <div id="J_Message" class="message <#if _error??&&'2'==_error>bg-warning text-danger</#if>" >
      	 <#if _error??&&'2'==_error><i class="fa fa-exclamation-triangle">您的账号已在其他地方登录，请留意账号安全</i></#if>
       </div>
       <form id="J_StaticForm" action="${contextPath}/login.json" method="post"> 
		<!--输入组---->
		<!--输入组---->
        <div class="field ph-hide"> 
         	<label for="TPL_username_1"><@spring.message  code="label.login.username" /></label> 
         	<div class="input-group">
         		<div class="input-group-addon"><i class="fa fa-user"></i></div>
         		<input type="text" name="userName" autocomplete="off" id="J_UserName" title="<@spring.message  code="label.login.username.help" />" class="login-text required" placeholder='<@spring.message  code="label.login.username.help" />' value="" maxlength="32" tabindex="1" required/>
         	</div>
        </div> 
        <div class="field "> 
         <label id="password-label" for="TPL_password_1"><@spring.message  code="label.login.password" /></label> 
         <a  href="${contextPath}/findPwd/index.html" target="_blank"  id="forget-pw-safe"  style="position: absolute;right: 60px;top: 0;" ><@spring.message  code="label.login.forgot.pass" /></a> 
         <span id="J_StandardPwd">
         	<div class="input-group">
         		<div class="input-group-addon"><i class="fa fa-key"></i></div>
         		<input type="password"  name="passWord" id="J_PassWord" class="login-text required" maxlength="28" tabindex="2" required/>
         	</div>
         </span> 
         <span id="J_PasswordEdit" class="password-edit" style="display:none;"></span> 
         <span id="J_CapsLockTip" class="warning-tip bg-warning text-danger" style="display:none;">Caps Lock键正处于启用状态，<br />启用它可能导致密码输入错误。</span> 
        </div> 
        
          <div class="field " id="l_f_code" data='${_login_times!'0'}' > 
         <input id="J_ValidateCode" type="text" placeholder='<@spring.message  code="label.login.validation" />' title="<@spring.message  code="label.login.validation" />" class="login-text checkcode J_CheckCode" maxlength="5" name="validateCode" tabindex="3" /> 
         <img id="J_StandardCode_m" src="" data-src="${contextPath}/validate/passcode"  title='<@spring.message  code="label.login.validation.img.title" />'  class="check-code-img" /> 
         <a href="javascript:;" class="change-code right" id="J_StandardCode"  title='<@spring.message  code="label.login.validation.img.title" />' >看不清 </a> 
        </div> 
        
        <div class="field submit" style="top:5px;"> 
         <input type="hidden" name="format" value="json" /> 
         <!--
         <input type="hidden" name="type" value="company" /> 
         <input type="hidden" name="loginsite" value="0" id="J_loginsite" /> 
         <input type="hidden" name="loginType" value="3" /> 
         <input type="hidden" id="J_from_encoding" name="from_encoding" value="" /> 
         <input type="hidden" name="oslanguage" /> 
         <input type="hidden" name="sr" /> 
         <input type="hidden" name="osVer" /> 
         <input type="hidden" name="naviVer" /> 
         -->
         <button  class="btn  button" data-color='maleskine' data-loading-text="<@spring.message  code="label.login.button.loading" />"  style='width: 245px; margin-bottom: 8px;' tabindex="5" id="J_SubmitStatic">
		   <@spring.message  code="label.login.button" />
		 </button> 
        </div> 
		<div class="field ">
			<label for="remember-me">
	       		 <input id="remember-me" name= "rememberMe" type="checkbox">
	        	<@spring.message  code="label.login.remember.me" />
	      	</label>
			<ul class="entries" > 
			 <li id="registerUrl_1"  >
				<a id="J_RegisterLink1" href="${contextPath}/regist.jhtml" target="_blank" style="position: absolute;right: 60px;top: 0;" tabindex="8"><@spring.message  code="label.register.button" /></a>
			 </li> 
			</ul> 
		</div>
        <span id="J_PasswordEditTmp" class="password-edit-tmp"> </span> 
       </form>
       <div class="clearfix" ></div>
	    <div class="login-sns" id="J_Login_sns"  >
		 <ul class="login-sns"> 
		   <li class="weibo"> <a target="_parent" href="${contextPath}/3rd/login/sina" ><i class="fa fa-weibo"></i></a> </li> 
		   <li class="qq"> <a target="_parent" href="${contextPath}/3rd/login/qq" ><i class="fa fa-qq"></i></a> </li>
		   <li class="weixin"> <a href="javascript:;"  ><i class="fa fa-weixin"></i></a> </li>  
		   <li class="github"> <a href="javascript:;"><i class="fa fa-github"></i></a> </li>
		   <li class="plus"> <a href="javascript:;"><i class="fa fa-plus"></i></a> </li>
		 </ul>  
		</div> 
		<a href="javascript:;" class="btn-2qrcode" id="J_2QRCode"  >扫码安全登录</a>
	 </div> 
	   <!--二维码登录begin--> 
      <div class="login-box-switch qrcode-login" id="J_QRCodeLogin" style="display:none;"> 
       <div class="qrcode-mod"> 
        <div class="qrcode-desc">
         <h2>手机扫码 安全登录</h2>
        </div> 
        <div class="qrcode-err qrcode-scanerr">
         <h6>扫描失败</h6>请刷新二维码后重新扫描
        </div> 
        <div class="qrcode-err qrcode-lgerr">
         <h6>登录失败</h6>请刷新二维码后重新扫描
        </div> 
        <div class="qrcode-main"> 
         <div class="qrcode-img" id="J_QRCodeImg_div" style="opacity: 1;" >
         	<img id="J_QRCodeImg"  data-src="${contextPath}/validate/passQRcode?w=111&h=111&m=0" title="二维码" class="animated bounceInLeft check-qrcode-img">
         </div> 
         <div class="qrcode-help" id="J_QRCodeHelp" style="opacity: 0;"></div> 
        </div> 
        <div class="qrcode-panel"> 
         <a href="javascript:;" id="J_QRCodeHandle">刷新二维码</a> | 
         <a href="javascript:;" target="_blank" id="J_QRCodeGuide">使用帮助</a> 
        </div> 
       </div> 
       <div class="qrcode-msg"> 
        <div class="qrcode-b-ok qrcode-scanok">
         <h6>扫描成功！</h6>请按手机的提示操作，请勿刷新本页面
        </div> 
       </div> 
       <a href="javascript:;" class="btn-2login" id="J_2Login" >账号登录</a> 
      </div> 
	  <!--二维码登录end--> 
	  
      <!--快速登录--> 
      <div id="J_QuickLogin" class="login-box-switch quick-login" style="display:none;"> 
       <span class="title">检测到您已经登录的账户:</span> 
       <form action=""> 
        <input type="hidden" value="#" id="el_redirectURL" name="redirectURL" /> 
        <ul class="userlist">
        </ul> 
        <div class="submit"> 
         <button type="submit" id="J_SubmitQuick">快速登录</button> 
        </div> 
        <ul class="entries"> 
         <li><a class="module-switch" id="J_Quick2Static" data-target="static" href="javascript:;">使用其他账户登录</a></li> 
		    <p>您还可以通过以下方式直接登录</p> 
        </ul> 
       </form> 
      </div> 
      <!--快速登录结束--> 
     </div> 
    </div> 
   </div> 
  </div> 
  	<!------seajs.config 引用信息 begin----->
  	<#include "/includes/seajs.config.ftl" />
  	<!------seajs.config   引用信息 end----->
<script>
  	seajs.use("${contextPath}/js/login/login.js?v="+Math.random());
</script> 
 </body>
</html>