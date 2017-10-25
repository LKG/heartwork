<#include "/includes/taglib.ftl" />
<div id="J_ForgotPasswordBox" > 
<script type="text/javascript">(function(x){x&&(x.className+=" loading")})(document.getElementById("J_ForgotPasswordBox"))</script>
<form class="form-horizontal" id="J_ForgotpasswdForm"  action="${contextPath}/findPwd/sendFindPwd.jhtml" method="post" >
  <div class="form-group">
    <label for="J_Type_choose" class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span>请选择验证身份方式：</label>
     <div class="col-sm-6 col-xs-6">
			<select class="form-control" name="type" id="J_Type_choose">
			  <option value=1 >手机号码</option>
			  <option value=2 >注册邮箱</option>
			</select>
    </div>
  </div>
    <div class="form-group">
   <label class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span>昵称：</label>
    <div class="col-sm-9 col-xs-9">
      <p class="form-control-static">${result.nickName}</p>
    </div>
  </div>
   <div class="form-group email_div" style="display:none;">
   <label class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span>已验证邮箱：</label>
    <div class="col-sm-9 col-xs-9">
      <p class="form-control-static">${result.userEmail}</p>
    </div>
  </div>
   <div class="form-group phone_div">
   <label class="col-sm-3  col-xs-3 control-label"><sapn class="text-danger" >*</span>已验证手机号：</label>
    <div class="col-sm-9  col-xs-9">
      <p class="form-control-static">${result.userPhone}</p>
    </div>
  </div>
  <div class="form-group phone_div" id="phone_div">
     <label for="phoneCode" class="col-sm-3 col-xs-3 control-label"><sapn class="text-danger" >*</span>短信码：</label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	          <div class="">
		     	    <div class="input-group">
				      <div class="input-group-addon"><i class="fa fa-comment"></i></div>
				      <input type="text" class="form-control" maxlength="5" id="phoneCode" name="phoneCode" tabindex="5"  placeholder="短信码" />
					  <span class="input-group-btn">
				        <button class="btn btn-default" id="J_PhoneCodeBtn" type="button">获取短信验证码</button>
				      </span>
				    </div>
		     </div>
	    </div>
    </div>
    <div class="col-sm-3 col-xs-3">
    </div>
  </div>
   <div class="form-group">
     <div class="col-sm-2">
     	 <#if k??>
     	 <input type="hidden" name="k" id="key" value="${k}" />
     	 </#if>
     	<!--
   	 <input type="hidden" name="format" value="json" />
   	   ---->
   	 <#if sub_token??>
   	 <input type="hidden" name="sub_token" value="${sub_token}" />
   	 </#if>
   	  
   	
   	 </div>
   	  <div class="col-sm-6">
     <button  id="J_PhoneSubmit"   class="btn btn-success btn-lg btn-block phone_div"  data-loading-text="<@spring.message  code="label.default.button.loading" />"   tabindex="5" >
	   下一步
	 </button> 
	 <button  id="J_EmailSubmit"   class="btn btn-success btn-lg btn-block email_div"  style="display:none;" data-loading-text="<@spring.message  code="label.default.button.loading" />"   tabindex="5" >
	 发送邮件
	 </button> 
	 </div>
	 
   </div>
</form>
<#include "/includes/seajs.config.ftl" />
<!------seajs.config   引用信息 end----->
 <script>
  	seajs.use("js/login/forgotpasswd2");
</script>
</div>