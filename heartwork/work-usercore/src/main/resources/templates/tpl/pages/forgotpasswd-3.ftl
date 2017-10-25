<#include "/includes/taglib.ftl" />
<div id="J_ForgotPasswordBox" > 
<script type="text/javascript">(function(x){x&&(x.className+=" loading")})(document.getElementById("J_RegisterBox"))</script>
<form class="form-horizontal" id="J_ForgotpasswdForm"  action="${contextPath}/findPwd/resetPwd.jhtml" method="post" >
  <div class="form-group">
    <label for="userName" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.password" /></label>
     <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-user"></i></div>
	     <input type="password" class="form-control" id="passWord" name="passWord" placeholder="<@spring.message  code="label.login.password" />" title="<@spring.message  code="label.login.password" />" >
	    </div>
    </div>
  </div>
   <div class="form-group" style="margin-top:-10px;">
    <label for="" class="col-sm-2 col-xs-2 control-label"></label>
     <div class="col-sm-6 col-xs-6">
	   <div class="progress" style="margin-bottom:-10px;">
 			<div id="pass_progress" class="progress-bar  progress-bar-striped active" role="progressbar" 
				aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
 				<span class=""  id="pass_progress_text" ></span>
 			</div>
 		</div>
    </div>
  </div>
   <div class="form-group">
    <label for="userName" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.user.password.confirm" /></label>
     <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-user"></i></div>
	        <input type="password" class="form-control" id="retryPassWord" name="retryPassWord" placeholder="<@spring.message  code="label.user.password.confirm" />" >
	    </div>
    </div>
  </div>
   <div class="form-group">
     <div class="col-sm-2">
     	<!--
   	 <input type="hidden" name="format" value="json" />
   	   ---->
   	  <#if k??>
     	 <input type="hidden" name="k" id="key" value="${k}" />
      </#if>
   	 <#if sub_token??>
   	 <input type="hidden" name="sub_token" value="${sub_token}" />
   	 </#if>
   	  
   	
   	 </div>
   	  <div class="col-sm-6">
     <button  id="J_RegisterSubmit" class="btn btn-success btn-lg btn-block"  data-loading-text="<@spring.message  code="label.default.button.loading" />"   tabindex="5" >
	   提交
	 </button> 
	 </div>
	 
   </div>
</form>
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use(["js/login/forgotpasswd3","js/common/passwordCheck.js?v="+Math.random()]);
</script>
</div>