define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var template = require('arttemplate');
	var url = {
		api : baseRoot + "/admin/logOperate",
	};
	var $httpUtil = require('/js/common/httpUtil.js');
	var dialog = require('/js/common/dialog');
	var getAreas = require('/js/common/areas.js');//地区选择控件
	var $msg= require('/js/common/alerts.js');
	window.dialog = dialog;

	  var postData = {
		      size: 10,
		      page:1,
		      sort:'',
		   }
			new Vue({
		        el: '#app',
		        data: postData,
		    })
		//地区选择BEGIN
		getAreas($("#provinceId"), "0");
		$("#provinceId").on("change", function() {
			var parentCode = $(this).val();
			getAreas($("#cityId"), parentCode, "");
			$("#areaId").empty();
		});
		$("#cityId").on("change", function() {
			var parentCode = $(this).val();
			getAreas($("#areaId"), parentCode, "");
		});
	//地区选择END
	var laypage = require('laypage');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var $tbody = $("#table-tbody");
	$(".page-size-sel").on("change", function() {
		$("#size").val($(this).val());
		$("#seach-btn").click();
	});
	$("#refresh,#seach-btn").on("click", function() {
		$("#page").val(1);
		search(true);
	});
	var search = function(loading) {
		$("#btSelectAll").removeAttr("checked");
		var param = $("#search_form").serialize();
		$.httpUtil.curl({url : url.api + "s.json",
			type : "post",
			dataType : "json",
			loading : loading,
			data : param,
			success : function(data) {
				var html = template('tr-template-js', data.result);
				$("#pagination-total").html(data.result.totalElements);
				$tbody.empty();
				$tbody.append(html);
				var $pagination=$("#table-pagination");
				laypage({
					cont : $pagination, // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
					pages : data.result.totalPages, // 通过后台拿到的总页数
					curr : Number(data.result.number) + 1, // 初始化当前页
					skip : true,
					skin : '#AF0000',
					jump : function(obj, first) { // 触发分页后的回调
						if (!first) {
							var curr = obj.curr;
							$("#page").val(obj.curr);
							search(false);
						}
					}
				});
			}
		});
	};
	var $pagination=$("#table-pagination");
	laypage({
		cont : $pagination, // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
		pages : $pagination.attr('data-totalPages'), // 通过后台拿到的总页数
		curr : Number($pagination.attr('data-number')) + 1, // 初始化当前页
		skip : true,
		skin : '#AF0000',
		jump : function(obj, first) { // 触发分页后的回调
			if (!first) {
				var curr = obj.curr;
				$("#page").val(obj.curr);
				search(false);
			}
		}
	});
	
});