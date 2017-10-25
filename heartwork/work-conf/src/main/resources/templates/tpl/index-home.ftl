<div class="container">
    <div class="page-header">
	      <ul id="myTabs" class="nav nav-tabs" role="tablist">
	      <li role="presentation" class="active"><a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true">Home</a></li>
	      <li role="presentation"><a href="#profile" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">Profile</a></li>
	    </ul>
    </div>
    <div class="row">
     <#list [0,1,2,2,22,2,2,2,2,2,2,2] as model>
    	<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
		<#include "/pages/medal.ftl" />
     </#list >	

 </div>
</div>