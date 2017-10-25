<form class="form-horizontal" action="${contextPath}/userinfo/receiver/save.json" id="J_MainForm"  method="post"  >
 <div class="form-group">
	<div class="col-sm-11 col-xs-11">
		<div class="input-group">
		 	<span class="input-group-addon">收货人</span>
	 	 <input type="text" class="form-control" id="name" value="<#if result.name??>${result.name}</#if>" name="icon" placeholder="收货人">
		</div>
	</div>
 </div>
  <div class="form-group">
	<div class="col-sm-11 col-xs-11">
		<div class="input-group">
		 <span class="input-group-addon">地址</span>
	 	 <input type="text" class="form-control" id="addressDetail" value="<#if result.addressDetail??>${result.addressDetail}</#if>" name="addressDetail" placeholder="收货地址">
		</div>
	</div>
 </div>
  <div class="form-group">
	<div class="col-sm-11 col-xs-11">
		<div class="input-group">
		 	<span class="input-group-addon">手机号</span>
	 	 <input type="text" class="form-control" id="mobile" value="<#if result.mobile??>${result.mobile}</#if>" name="mobile" placeholder="手机号码">
		</div>
	</div>
 </div>
   <div class="form-group">
	<div class="col-sm-11 col-xs-11">
		<div class="input-group">
		 	<span class="input-group-addon">电话号</span>
	 	 <input type="text" class="form-control" id="phone" value="<#if result.phone??>${result.phone}</#if>" name="phone" placeholder="电话号码">
		</div>
	</div>
 </div>
   <div class="form-group">
	<div class="col-sm-11 col-xs-11">
		<div class="input-group">
		 	<span class="input-group-addon">邮箱</span>
	 	 <input type="text" class="form-control" id="email" value="<#if result.email??>${result.email}</#if>" name="email" placeholder="电子邮箱">
		</div>
	</div>
 </div>
    <div class="form-group">
	<div class="col-sm-11 col-xs-11">
		<div class="input-group">
		 	<span class="input-group-addon">标签</span>
	 	 <input type="text" class="form-control" id="remark" value="<#if result.remark??>${result.remark}</#if>" name="remark" placeholder="标签">
		</div>
	</div>
 </div>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>