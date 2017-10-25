<#include "/includes/taglib.ftl" />
<div id="J_ForgotPasswordBox" > 
<script type="text/javascript">(function(x){x&&(x.className+=" loading")})(document.getElementById("J_RegisterBox"))</script>
<form class="form-horizontal" id="J_ForgotpasswdForm"  action="${contextPath}/findPwd/subGeneral.jhtml" method="post" >
  <div class="form-group">
    <label for="userName" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.username" /></label>
     <div class="col-sm-6 col-xs-6">
	     <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-user"></i></div>
	     <input type="text" class="form-control" id="account" name="account" maxlength="32" title="<@spring.message  code="label.login.username.help" />" placeholder="<@spring.message  code="label.login.username.help" />" />
	     </div>
    </div>
  </div>
   <div class="form-group">
    <label for="userName" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.username" /></label>
     <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-user"></i></div>
	     	<input type="text" class="form-control" tabindex="3"  maxlength="5" id="validateCode" name="validateCode" tabindex="5" placeholder='<@spring.message  code="label.login.validation" />' title="<@spring.message  code="label.login.validation" />"  />
		     <span class="input-group-btn">
		       	 <img id="J_StandardCode_m" src="${contextPath}/validate/passcode?module=forgot" style="height: 34px;"  title='<@spring.message  code="label.login.validation.img.title" />'  class="check-code-img" /> 
	         <a href="javascript:;" class="change-code right" id="J_StandardCode"  title='<@spring.message  code="label.login.validation.img.title" />' >看不清 </a> 
		     
		      </span>
	    </div>
    </div>
  </div>
  
   <div class="form-group">
   <label for="userName" class="col-sm-2 col-xs-2 control-label"></label>
     <div class="col-sm-6 col-xs-6">
	     <#if sub_token??>
	   	 <input type="hidden" name="sub_token" value="${sub_token}" />
	   	 </#if>
	   <button  id="J_findPwdSubmit" class="btn btn-success btn-lg btn-block"  data-loading-text="<@spring.message  code="label.default.button.loading" />"   tabindex="5" >
	   下一步
	 </button> 
    </div>
  </div>
</form>
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use("js/login/forgotpasswd");
</script>
</div>