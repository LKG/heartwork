define(function(require, exports, moudles) {
	var $ = require('jquery');
	var dialog = require('./dialog');
	"use strict";
	var pluginName = 'httpUtil';// 插件名称
	// 构造方法
	function HttpUtil(options) {
		var self = this;
		this.options = $.extend({}, $.fn[pluginName].defaults, options);
	}
	$.fn[pluginName] = function(options) {
		return this.each(function() {
			var obj;
			if (!(obj = $.data(this, pluginName))) {
				var $this = $(this), data = $this.data(), opts = $.extend({},
						options, data);
				if ($this.attr('href') !== '' && $this.attr('href') != '#') {
					opts.url = $this.attr('href');
				}
				obj = new HttpUtil(opts);
				$.data(this, pluginName, obj);
			}
		});
	};
	$.fn[pluginName].defaults = {
		id : "",// 设置请求Id
		url : "",// 请求地址
		data : "",// 参数
		type : "post",// 参数
		dataType : "",// 数据类型
		loading : true,// 是否显示进度条
		async : false,// 是否同步
		debug : true,
		error : null,
		success : null,
	};
	var httpUtil = $[pluginName] = function(elem, options) {
		return new HttpUtil(elem, options);
	};
	// 开放外部的方法
	$.extend(httpUtil, {
		curl : function(options) {
			var self = this;
			this.options = $.extend({}, $.fn[pluginName].defaults, options);
			var loading = this.options.loading;
			var debug = this.options.debug;
			var model = null;
			if (loading) {
				model = dialog({content: '<i class="fa fa-spinner fa-pulse fa-spin"></i>服务器正在处理中。。。',});
				model.showModal();
			}
			$.ajax({
				type : self.options.type,
				async: self.options.async ,
				url : self.options.url,
				dataType : self.options.dataType,
				data : self.options.data,
				success : function(data, textStatus) {
					var isFunc = $.isFunction(self.options.success);
					if (isFunc) {
						self.options.success.call(this, data, textStatus);
					}
					return this;
				},
				complete : function(XMLHttpRequest, textStatus, errorThrown) {
					if (loading) {
						if(textStatus=="success"){
							setTimeout(function() {
								model.close().remove();
							}, 500);
						}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					var isFunc = $.isFunction(self.options.error);
					if (isFunc) {
						self.options.error.call(this, XMLHttpRequest,
								textStatus, errorThrown);
					}else{
						// window.location.reload(true);
					}
					return this;
				},
			});
			return this;
		},
	});
	return httpUtil;
});
