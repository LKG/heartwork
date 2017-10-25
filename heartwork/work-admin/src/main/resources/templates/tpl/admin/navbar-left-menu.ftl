<!-- 左侧菜单begin--->
<div id="left-menu" class="sidebar"> 
 <div class="sidebar-collapse" id="sidebar-collapse-top">
	<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
 </div>
  <ul class="nav nav-list" > 
   <li class="<#if template=='index'>active</#if>"> <a href="${contextPath}/admin/index.jhtml"> <i class="fa fa-dashboard"></i> <span class="menu-text"> 控制台 </span> </a> </li> 
     <li class="<#if template=='sys'>active open</#if>"> <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-wrench"></i> <span class="menu-text">系统管理</span> <i class="fa fa-angle-down "></i> </a> 
    <ul class="submenu"> 
     <li class="<#if template=='sys'&&submenu=='user'>active</#if>"> <a href="${contextPath}/admin/users.jhtml"> <i class="fa fa-users"></i>用户管理 </a> </li> 
     <li class="<#if template=='sys'&&submenu=='role'>active</#if>"> <a href="${contextPath}/admin/roles.jhtml"> <i class="fa fa-mortar-board"></i>角色管理</a> </li> 
     <li class="<#if template=='sys'&&submenu=='func'>active</#if>"> <a href="${contextPath}/admin/resources.jhtml"> <i class="fa fa-dashboard"></i>菜单管理</a> </li> 
     <li class="<#if template=='sys'&&submenu=='org'>active</#if>"> <a href="${contextPath}/admin/orgs.jhtml"> <i class="fa fa fa-sitemap"></i>组织机构</a> </li> 
     
    </ul> 
    </li> 
    <li class="<#if template=='sysedit'>active open</#if>"> <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-cogs"></i> <span class="menu-text"> 系统维护</span> <i class="fa fa-angle-down "></i> </a> 
    <ul class="submenu">
      <li class="<#if template=='sysedit'&&submenu=='area'>active</#if>"> <a href="${contextPath}/admin/areas.jhtml"> <i class="fa fa-cogs"></i>区域管理</a> </li> 
      <li class="hide <#if template=='sysedit'&&submenu=='areatree'>active</#if>"> <a href="${contextPath}/admin/area/tree.jhtml"> <i class="fa fa-map-marker"></i>区域树</a> </li> 
      <li class="<#if template=='sysedit'&&submenu=='dict'>active</#if>"> <a href="${contextPath}/admin/dicts.jhtml">  <i class="fa fa-book"></i>数据字典</a> </li> 
      <li class="<#if template=='sysedit'&&submenu=='tpl'>active</#if>"> <a href="${contextPath}/admin/tpls.jhtml">  <i class="fa fa-flask"></i>模板管理</a> </li> 
    </ul> 
    </li> 
        <li  class="<#if template=='cms'>active open</#if>" > <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-paint-brush"></i> <span class="menu-text">内容管理</span> <i class="fa fa-angle-down "></i> </a> 
   	  <ul class="submenu">
   	   <li class="<#if template=='cms'&&submenu=='notice'>active</#if>"> <a href="${contextPath}/admin/notices.jhtml"> <i class="fa fa-bullhorn"></i>公告管理</a> </li> 
   	   <li class="<#if template=='cms'&&submenu=='adm'>active</#if>"> <a href="${contextPath}/admin/adms.jhtml"> <i class="fa fa-fire"></i>广告管理</a> </li> 
   	   <li class="<#if template=='cms'&&submenu=='article'>active</#if>"> <a href="${contextPath}/admin/articles.jhtml"> <i class="fa fa-bookmark"></i>文章管理</a> </li> 
   	   <li class="<#if template=='cms'&&submenu=='friendlink'>active</#if>"> <a href="${contextPath}/admin/friendlinks.jhtml"> <i class="fa fa-bookmark"></i>友链管理</a> </li> 
      </ul> 
     </li>
     <li  class="<#if template=='monitor'>active open</#if>" > <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-tachometer"></i> <span class="menu-text">系统监控</span> <i class="fa fa-angle-down "></i> </a> 
   	  <ul class="submenu">
   	   <li class="<#if template=='monitor'&&submenu=='online'>active</#if>"> <a href="${contextPath}/admin/onlines.jhtml"> <i class="fa fa-users"></i>在线用户</a> </li> 
   	   <li class="<#if template=='monitor'&&submenu=='logLogin'>active</#if>"> <a href="${contextPath}/admin/logLogins.jhtml"> <i class="fa fa-users"></i>登录日志</a> </li>
   	   <li class="<#if template=='monitor'&&submenu=='logOperate'>active</#if>"> <a href="${contextPath}/admin/logOperates.jhtml"> <i class="fa fa-users"></i>操作日志</a> </li>
   	    <li class="<#if template=='monitor'&&submenu=='smslog'>active</#if>"> <a href="${contextPath}/admin/smslogs.jhtml"> <i class="fa fa-cogs"></i>短信记录</a> </li> 
      </ul> 
     </li>
    <li  class="<#if template=='rpt'>active open</#if>" > <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-bar-chart"></i><span class="menu-text">报表模块</span> <i class="fa fa-angle-down "></i> </a> 
   	  <ul class="submenu">
   	   <li class="<#if template=='rpt'&&submenu=='rptConfig'>active</#if>"> <a href="${contextPath}/admin/rptConfigs.jhtml"> <i class="fa fa-cogs"></i>报表配置</a> </li> 
      </ul> 
     </li>
      <li  class="<#if template=='msg'>active open</#if>" > <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-bell"></i> <span class="menu-text">消息中心</span> <i class="fa fa-angle-down "></i> </a> 
   	  <ul class="submenu">
   	   <li class="<#if template=='msg'&&submenu=='msg'>active</#if>"> <a href="${contextPath}/admin/msgs.jhtml"> <i class="fa fa-cogs"></i>消息中心</a> </li> 
   	
      </ul> 
     </li>
     <li  class="hide <#if template=='act'>active open</#if>" > <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-gavel"></i> <span class="menu-text">流程管理</span> <i class="fa fa-angle-down "></i> </a> 
   	  <ul class="submenu">
   	
      </ul> 
     </li>
     <li  class="<#if template=='ext'>active open</#if>" > <a href="javascript:void(0);" class="dropdown-toggle"> <i class="fa fa-gift"></i> <span class="menu-text">高级模块</span> <i class="fa fa-angle-down "></i> </a> 
   	  <ul class="submenu">
   	  		  <li class="<#if template=='msg'&&submenu=='msgsend'>active</#if>"> <a href="${contextPath}/admin/msgsend.jhtml"> <i class="fa fa-cogs"></i>发送短信</a> </li> 
   	     	  <li class="<#if template=='ext'&&submenu=='task'>active</#if>"> <a href="${contextPath}/admin/tasks.jhtml"> <i class="fa fa-cogs"></i>计划任务</a> </li> 
      </ul> 
    </li>
  </ul>
 <div class="sidebar-collapse" id="sidebar-collapse-bottom">
	<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
 </div>
</div>
<!-- 左侧菜单end--->