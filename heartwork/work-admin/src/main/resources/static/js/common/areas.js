define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var getAreas = function(obj, parentId, defaultVal) {
			if (undefined===parentId|| parentId == "") {
				return;
			}
			var $select = $(obj);
			$select.empty();
			$select.append($("<option>").text("全部").val(""));
			$.ajax({
				url : baseRoot + "/admin/areas.json?size=500&parentId=" + parentId,
				type : "post",
				dataType : "jsonp",
				jsonp:'jsoncallback', 
				data : null,
				success : function(data) {
					if (data.success) {
						$.each(data.result.content, function(index, item) {
							var $option = $("<option>").text(item.name).val(item.code);
							$select.append($option);
						});
						if (defaultVal) {
							$select.val(defaultVal);
						}
					}
				}
			});
		};
		moudles.exports	=getAreas;
});