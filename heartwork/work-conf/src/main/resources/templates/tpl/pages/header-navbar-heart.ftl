<nav class="navbar navbar-default " id="header-navbar-heart" style="height: 53px;" >
  <div class="container" id="header-navbar-heart-container" >
    <div class="navbar-header" id="navbar-brand-logo" >
      <a class="navbar-brand" href="${contextPath}/"   style="padding: 0px 0px;height: 51px;" >
      	<img src="${contextPath}/static/imgs/logo.png"  class="img-responsive"  style="height: 51px;" />
      </a>
    </div>
    <div class="row" id="header-navbar-menu" >
     <ul class="nav navbar-nav" style="padding: 0px 0px;margin-top:0px;">
      	<li class="<#if template==''>active</#if>">
      	<a href="${contextPath}/"  style="padding-top: 15px;padding-bottom: 15px;"><@spring.message  code="label.system.index" /></a>
      	</li>
      	<li class="<#if template=='desk'>active</#if>">
      	<a href="${contextPath}/desk.jthml"  style="padding-top: 15px;padding-bottom: 15px;">工作台</a>
      	</li>
    </div>
  </div>
</nav>
