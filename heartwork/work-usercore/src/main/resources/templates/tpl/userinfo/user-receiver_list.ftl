 <#list result.content as model>
     <div class="panel panel-success user-receiver">
  <!-- Default panel contents -->
	  <div class="panel-heading">${model.name} <span class="label label-primary pull-right">${model.remark}</span></div>
	 <div class="panel-body">
	   <label >收货人：</label><span>${model.name}</span>
	     <label >手机：</label><span>
	   	<#if (model.mobile??) > 
		  	${model.mobile?replace((model.mobile?substring(3,(model.mobile?length)-3)),'******')}
		 	 <#else>无
	  	</#if>
	   </span>
	   <label >固定电话：</label>
	   <span>
		   <#if (model.phone??)&&(model.phone?length>3) >
			${model.phone?replace((model.phone?substring(3,(model.phone?length)-3)),'******')}
			<#else>无
		   </#if>
	   </span>
	   <br/>
	   <label >所在地区：</label><span>${model.address}</span>
	   <label >地址：</label>
	   <span class="form-control-static">${model.addressDetail}</span>
	   <label >电子邮箱：</label> 
	   <span>
		   <#if (model.email??) >
				${model.email?replace((model.email?substring(3,(model.email?index_of('@')-1))),'******')}
			<#else>无
			</#if>
		</span>
	  </div>
	</div>
</#list >
