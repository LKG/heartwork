define(function(require, exports, moudles) {
	var store = require('store');
	weui.tab('#footer-tabbar',{
		defaultIndex: $("#weui-tabbar-index").attr("data-index"),
		onChange: function(index){
       		  $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
		},
	});
	return {
		init: function() {
		  
		}
	}
});