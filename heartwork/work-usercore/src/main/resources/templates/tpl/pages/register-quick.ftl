<#include "/includes/taglib.ftl" />
<div id="J_RegisterBox" > 
<script type="text/javascript">(function(x){x&&(x.className+=" loading")})(document.getElementById("J_RegisterBox"))</script>
<form class="form-horizontal" id="Q_RegisterForm"  action="${contextPath}/regist/quickRegister.jhtml" method="post" >
  <div class="form-group">
     <label for="Q_userPhone" class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.user.phone" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group ">
	      <div class="input-group-addon"><i class="fa fa-mobile"></i></div>
	      <input type="text" class="form-control" id="Q_userPhone" name="userPhone" placeholder="<@spring.message  code="label.user.phone" />" >
	    </div>
	</div>
    <div class="col-sm-2 col-xs-2">
    </div>
  </div>
  <div class="form-group">
     <label for="password" class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.login.password" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-key"></i></div>
	      <input type="password" class="form-control" id="Q_passWord" name="passWord" placeholder="<@spring.message  code="label.login.password" />" >
	    </div>
    </div>
  </div>
   <div class="form-group">
     <label for="retryPassWord" class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span><@spring.message  code="label.user.password.confirm" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-key"></i></div>
	      <input type="password" class="form-control" id="Q_retryPassWord" name="retryPassWord" placeholder="<@spring.message  code="label.user.password.confirm" />" >
	    </div>
    </div>
  </div>

  <div class="form-group">
     <label for="phoneCode" class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span>短信码：</label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-comment"></i></div>
	      <input type="text" class="form-control" maxlength="5" id="Q_phoneCode" name="phoneCode" tabindex="5" id="exampleInputAmount" placeholder="短信码" />
		  <span class="input-group-btn">
	        <button class="btn btn-default" id="Q_PhoneCodeBtn" type="button">获取短信验证码</button>
	      </span>
	    </div>
    </div>
    <div class="col-sm-4 col-xs-4">
    </div>
  </div>
  <div class="form-group">
     <label for="readme" class="col-sm-3 col-xs-3 control-label"></label> 
    <div class="col-sm-6 col-xs-6" >
   	 <div class="checkbox">
       <label  >
	       <div class="input-group">
	       		<input type="checkbox" name="readme" id='Q_readme' checked>
	       		<p class="text-left">
	       		我已阅读并同意<a href="javascript:;" class="blue" data-toggle="modal" data-target="#myModal" >《用户注册协议》</a>
	       		 </p>
	       	</div>
		</label>
      </div>
    </div>
  </div>
   <div class="form-group">
     <div class="col-sm-2">
     	<!--
   	 <input type="hidden" name="format" value="json" />
   	   ---->
   	 <#if sub_token??>
   	 <input type="hidden" name="sub_token" value="${sub_token}" />
   	 </#if>
   	 </div>
   	  <div class="col-sm-6">
     <button  id="Q_RegisterSubmit" class="btn btn-success btn-lg btn-block"  data-loading-text="注册中..."   tabindex="5" >
	   <@spring.message  code="label.register.button" />
	 </button> 
	 </div>
	 
   </div>
</form>
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use(["js/login/register-quick.js?v="+Math.random(),"js/common/passwordCheck.js?v=${ver!'1'}"]);
</script>
</div>