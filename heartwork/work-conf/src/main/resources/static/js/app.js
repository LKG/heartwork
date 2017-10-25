define(function(require, exports, moudles) {
	var $ = require('jquery');
	var store = require('store');
	var setting={local:'zh_cn',sidebar:'min'};
	store.set('setting-config',setting);
	if (store.enabled) {
		var setting=store.get('setting-config');
    }
	$("#header-topbar .dropdown").on("click" ,".topbar-nav-btn",function(){
		var $parent=$(this).parent();
		if($parent.hasClass("open")){
			$parent.removeClass("open");
		}else{
			$parent.addClass("open");
		};
	});
	//$(".viewFramework-body").addClass("viewFramework-sidebar-full");
	
	//$(".sidebar-nav").addClass("sidebar-nav-fold");
	//$(".viewFramework-body").removeClass("viewFramework-sidebar-full").addClass("viewFramework-sidebar-mini");
	return {
		init: function() {
		  
		}
	}
});