define(function(require, exports, moudles) {
	var $ = require('jquery');
	var dialog = require('artDialog');
	var $baseRoot = $("#baseRoot");
	var baseRoot = $baseRoot.attr("href");
	var url = {
		signUrl : baseRoot + "/userinfo/sign",
	};
	var $signBtn=$("#sign-btn");

	$signBtn.on("click", function() {
		var $btn = $(this);
		
		var dataHtml = $(this).html();
		
		var isSign = $(this).attr("isSign");
		if(isSign||isSign=="true"){
			return;
		}
		$btn.html($btn.attr("data-loading-text")).attr("disabled", "disabled");
		
		$.post(url.signUrl + ".json", null, function(json) {
			var content="";
			if (json.success) {
				if (json.result) {
					content="签到成功！";
					$btn.html("已签到").attr("isSign",true);
				}
			} else {
				content="签到失败！";
				$btn.html(dataHtml).removeAttr("disabled");
			}
			var d = dialog({
				id : $btn.attr("id"),
				title : '系统提示',
				content : content,
				okValue : '确定',
				ok : function() {
					this.title('关闭中…').close().remove();
					return false;
				},
			});
			d.showModal();
			setTimeout(function() {
				d.close().remove();
			}, 2000);
		});
	});
});