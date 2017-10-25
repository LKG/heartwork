<#include "/includes/taglib.ftl" />
<div id="J_RegisterBox" > 
<script type="text/javascript">(function(x){x&&(x.className+=" loading")})(document.getElementById("J_RegisterBox"))</script>
<form class="form-horizontal" id="J_RegisterForm"  action="${contextPath}/regist/subGeneral.jhtml" method="post" >
  <div class="form-group">
    <label for="userName" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.username" /></label>
     <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-user"></i></div>
	      <input type="text" class="form-control" id="userName" name="userName" placeholder="<@spring.message  code="label.user.account" />" />
	    </div>
    </div>
  </div>

  <div class="form-group">
     <label for="userPhone" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.user.phone" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group ">
	      <div class="input-group-addon"><i class="fa fa-mobile"></i></div>
	      <input type="text" class="form-control" id="userPhone" name="userPhone" placeholder="<@spring.message  code="label.user.phone" />" >
	    </div>
	</div>
    <div class="col-sm-2 col-xs-2">
    </div>
  </div>
  <div class="form-group">
     <label for="password" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.password" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-key"></i></div>
	      <input type="password" class="form-control" id="passWord" name="passWord" placeholder="<@spring.message  code="label.login.password" />" >
	    </div>
 		<div class="progress" style="margin-bottom:-10px;margin-top:2px;">
 			<div id="pass_progress" class="progress-bar  progress-bar-striped active" role="progressbar" 
				aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
 				<span class=""  id="pass_progress_text" ></span>
 			</div>
 		</div>
    </div>
  </div>
   <div class="form-group">
     <label for="retryPassWord" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.user.password.confirm" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-key"></i></div>
	      <input type="password" class="form-control" id="retryPassWord" name="retryPassWord" placeholder="<@spring.message  code="label.user.password.confirm" />" >
	    </div>
    </div>
  </div>

  <div class="form-group">
     <label for="phoneCode" class="col-sm-2 col-xs-2 control-label"><sapn class="text-danger" >*</span>短信码：</label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-comment"></i></div>
	      <input type="text" class="form-control" maxlength="5" id="phoneCode" name="phoneCode" tabindex="5"  placeholder="短信码" />
		  <span class="input-group-btn">
	        <button class="btn btn-default" id="J_PhoneCodeBtn" type="button">获取短信验证码</button>
	      </span>
	    </div>
    </div>
    <div class="col-sm-4 col-xs-4">
    </div>
  </div>
  <div class="form-group">
     <label for="userEmail" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.email" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-envelope-o"></i></div>
	      <input type="text" class="form-control" id="userEmail" name="userEmail" placeholder="<@spring.message  code="label.user.email" />">
	    </div>
    </div>
  </div>
  <!-----
<div class="form-group">
     <label for="userIdcard" class="col-sm-2 col-xs-2 control-label">身份证号</label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-credit-card"></i></div>
	      <input type="text" class="form-control" id="userIdcard" name="userIdcard"  placeholder="身份证号"/>
	    </div>
    </div>
  </div>
  <div class="form-group">
     <label for="realName" class="col-sm-2 col-xs-2 control-label">真实姓名</label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-credit-card"></i></div>
	      <input type="text" class="form-control" id="userIdcard" name="realName"  placeholder="真实姓名">
	    </div>
    </div>
  </div>
   <div class="form-group">
     <label for="userQq" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.qq" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-qq"></i></div>
	      <input type="text" class="form-control" id="userQq" name="userQq" placeholder="<@spring.message  code="label.user.qq" />" />
	    </div>
    </div>
  </div>
    ---->
  <div class="form-group">
     <label for="readme" class="col-sm-2 col-xs-2 control-label"></label> 
    <div class="col-sm-6 col-xs-6" >
   	 <div class="checkbox">
       <label  >
	       <div class="input-group">
	       		<input type="checkbox" name="readme" id='readme' checked>
	       		<p class="text-left">
	       		我已阅读并同意<a href="javascript:;" id="protocol-btn" class="blue" data-toggle="modal" data-target="#myModal" >《用户注册协议》</a>
	       		 </p>
	       	</div>
		</label>
      </div>
    </div>
  </div>
   <div class="form-group">
     <div class="col-sm-2">
   	 <!--	<input type="hidden" name="format" value="json" />-->
   	 <#if sub_token??>
   	 <input type="hidden" name="sub_token" value="${sub_token}" />
   	 </#if>
   	  
   	
   	 </div>
   	  <div class="col-sm-6">
     <button  id="J_RegisterSubmit" class="btn btn-success btn-lg btn-block"  data-loading-text="注册中..."   tabindex="5" >
	   <@spring.message  code="label.register.button" />
	 </button> 
	 </div>
	 
   </div>
</form>
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use(["${contextPath}/js/login/register.js?v="+Math.random(),"${contextPath}/js/common/passwordCheck.js?v=${ver!'1'}"]);
</script>
</div>