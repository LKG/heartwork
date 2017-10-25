<#include "/includes/taglib.ftl" />
<style>
	.form-control-static{
		border: 1px solid #ccc;border-top-left-radius: 0;border-bottom-left-radius: 0;
		padding: 7px 30px;
	}
</style>
<div id="J_RegisterBox" > 
<form class="form-horizontal" id="J_RegisterForm"  action="${contextPath}/#" method="post" >
  <div class="form-group">
     <label for="userPhone" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.phone" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group ">
	      <div class="input-group-addon"><i class="fa fa-mobile"></i></div>
	       <p class="form-control-static" >
	        <#if result.userPhone??>
	       ${result.userPhone?replace((result.userPhone?substring(3,(result.userPhone?length)-3)),'******')}
	        <#else>
	     	  	******
	     	  </#if>
	       </p>
	    </div>
	</div>
    <div class="col-sm-2 col-xs-2">
    </div>
  </div>
  <div class="form-group">
     <label for="userEmail" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.email" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-envelope-o"></i></div>
	    <p class="form-control-static" >
	     <#if result.userEmail??>
	     	 <#if (result.userEmail?index_of('@')>3)>  
	     		 ${result.userEmail?replace((result.userEmail?substring(3,(result.userEmail?index_of('@')-1))),'******')}
	     	<#else>
	     	 	${result.userEmail}
	     	 </#if>
	    <#else>
	     	******
	     </#if>
	    </p>
	    </div>
    </div>
  </div>
  <div class="form-group">
     <label for="userIdcard" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.nickName" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-credit-card"></i></div>
	       <input type="text" value="${result.nickName!""}" class="form-control" id="nickName" name="nickName" placeholder="<@spring.message  code="label.user.nickName" />" />
	    </div>
    </div>
 </div>
<div class="form-group">
     <label for="userIdcard" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.idcard" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-credit-card"></i></div>
	      <p class="form-control-static" >
	      	 <#if result.idCard??>
	     	    ${result.idCard?replace((result.idCard?substring(6,(result.idCard?length)-6)),'******')}
	     	  <#else>
	     	  	******
	     	  </#if>
	      </p>
	    </div>
    </div>
 </div>
  <div class="form-group">
     <label for="realName" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.realName" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-credit-card"></i></div>
	     <p class="form-control-static" >
	     	 <#if result.realName??>
		     	 <#if result.realName?length gt 1> 
		     	 	${result.realName?substring(0,1)}
		     	 <#else>
		     	 	${result.realName}
		     	 </#if><code><strong>**</strong></code>
	     	 <#else>
	     	  	<code><strong>******</strong></code>
	     	 </#if>
	     </p>
	    </div>
    </div>
  </div>
   <div class="form-group">
     <label for="userQq" class="col-sm-2 col-xs-2 control-label"><@spring.message  code="label.user.qq" /></label>
    <div class="col-sm-6 col-xs-6">
	    <div class="input-group">
	      <div class="input-group-addon"><i class="fa fa-qq"></i></div>
	     	  <p class="form-control-static" >
	     	  <#if result.userQq??>
	     	   ${result.userQq?replace((result.userQq?substring(6,(result.userQq?length)-6)),'******')}
	     	  <#else>
	     	  	******
	     	  </#if>
	     	  </p>
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

	 </div>
	 
   </div>
</form>
</div>