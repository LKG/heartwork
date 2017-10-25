<#include "/includes/taglib.ftl" />
<!-- List group begin-->
<div class="list-group text-center user-menu" style="min-height: 368px;">
	 <a href="${contextPath}/userinfo.jhtml" class="list-group-item <#if template=='userinfo'>active</#if>">
	 	<i class="fa fa-credit-card"></i>  我的资料
	 </a>
	  <a href="${contextPath}/userinfo/safe.jhtml" class="list-group-item <#if template=='safe'>active</#if>">
		 <i class="fa fa-bullhorn"></i>   账户安全
	 </a>
	 <a href="${contextPath}/userinfo/changePwd.jhtml" class="list-group-item <#if template=='changePwd'>active</#if>">
		 <i class="fa fa-bullhorn"></i>   修改密码
	 </a>

	 <a href="${contextPath}/userinfo/receivers.jhtml" class="list-group-item <#if template=='receivers'>active</#if>" >
     	<i class="fa fa-fighter-jet"></i>收货地址
     </a>
     <a href="${contextPath}/userinfo/levels.jhtml" class="list-group-item <#if template=='level'>active</#if>">
		 <i class="fa fa-legal"></i>   我的级别
	 </a>
     <!--
	 <a href="${contextPath}/userinfo/receivers.jhtml" class="list-group-item ">
		 <i class="fa fa-star"></i>   我的收藏
	 </a>
	  <a href="${contextPath}/userinfo/receivers.jhtml" class="list-group-item ">
		 <i class="fa fa-star"></i>   我的关注
	 </a>
	    -->
	 <a href="${contextPath}/userinfo/messages.jhtml" class="list-group-item <#if template=='message'>active</#if>">
	 	<i class="fa fa-comments"></i>  消息中心
	 </a>
</div>
<!-- List group end-->