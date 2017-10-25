define(function(require, exports, moudles) {
	var $ = require('jquery');
	var template = require('arttemplate');
	var dialog = require('artDialog');
	var laypage = require('laypage');
	require('swiper',$);
	window.dialog = dialog;
	var httpUtil = require('js/common/httpUtil.js');
	var $msg= require('/js/common/alerts.js');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	//TODO：后期考虑公用全局配置
	var apiV="/api/v1";
	 var swiper = new Swiper('.swiper-container', {
	        pagination: '.swiper-pagination',
	        paginationClickable: true,
	        nextButton: '.swiper-button-next',
	        prevButton: '.swiper-button-prev',
	        spaceBetween: 30,
		    hashnav: true,
	        hashnavWatchState: true,
	        effect: 'fade',
	        loop : true,
	        //lazyLoading : true,
	      //  zoom : true,
	        autoplay: 2500, //自动播放
	        autoplayDisableOnInteraction: false,
	    });
});