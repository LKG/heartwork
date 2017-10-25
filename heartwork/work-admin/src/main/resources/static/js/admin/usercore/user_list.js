define(function(require, exports, moudles) {
	var $ = require('jquery');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var template = require('arttemplate');
	var url = {
		api : baseRoot + "/admin/user",
	};
	var $httpUtil = require('/js/common/httpUtil.js');
	var dialog = require('/js/common/dialog');
	window.dialog = dialog;
	var laypage = require('laypage');
	var postData = {
		      size: 10,
		      page:1,
		      sort:'',
	};
	new Vue({
		 el: '#app',
		 data: postData,
	});
	var $tbody = $("#table-tbody");
	var $msg= require('/js/common/alerts.js');
	var relateOrgView=function(id,dataId){
		$.httpUtil.curl({
			url : url.api  +"/"+ dataId+ "/relate/org.jhtml",
			loading : false,
			success : function(data) {
				dialog({
					id : id,
					title : "关联机构",
					cancelValue : '取消',
					bodyClass : "ui-dialog-body-min",
					height : "600",
					width : "750",
					content:data,
					onclose: function () {
						search(false);
					},
				}).showModal();
			}
		});
	}
	$tbody.on("click", ".operate .btn-chain",function() {
		var dataId = $(this).attr("data");
		var id = $(this).attr("id");
		relateOrgView(id,dataId);
	 });
	var authView=function(id,dataId){
		$.httpUtil.curl({
			url : url.api  +"/"+ dataId+ "/authview.jhtml",
			type : "get",
			loading : false,
			success : function(data) {
				dialog({
					id : id,
					title : "授权用户角色",
					cancelValue : '取消',
					bodyClass : "ui-dialog-body-min",
					height : "220",
					width : "500",
					content:data,
					onclose: function () {
						search(false);
					},
				}).showModal();
			},
		});
	}
	//用户授权
	$tbody.on("click", ".operate .btn-auth",function(event) {
		var dataId = $(this).attr("data");
		var id=$(this).attr("id");
		authView(id,dataId);
	});
	
	$tbody.on("click", ".operate .btn-publish", function() {
		var $this=$(this);
		var id = $(this).attr("id");
		var dataId = $this.attr("data");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要激活该用户？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.api +"/" +dataId+"/activate.json",
					type : "post",
					loading : true,
					dataType : "json",
					data : null,
					success : function(data) {
						$msg.alert($this,"更新成功");
						search(false);
						$this.remove();
					},
				});
				return true;
			},
			cancelValue : '取消',
			cancel : function() {
			}
		}).showModal();
	});
	$tbody.on("click", ".operate .btn-reset", function() {
		var $this=$(this);
		var id = $(this).attr("id");
		var dataId = $this.attr("data");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要重置该用户密码？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.api +"/" +dataId+"/resetpwd.json",
					type : "post",
					loading : true,
					dataType : "json",
					data : null,
					success : function(data) {
						dialog({
							title: '系统消息',
							content : '重置成功,新密码：<code>'+data.result+'</code>',
						}).show();
						search(false);
						$this.remove();
					},
				});
				return true;
			},
			cancelValue : '取消',
			cancel : function() {
			}
		}).showModal();
	});
	
	$tbody.on("click", ".operate .btn-disabled", function() {
		var $this=$(this);
		var id = $(this).attr("id");
		var dataId = $this.attr("data");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要禁用该用户？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.api+"/" +dataId + "/disabled.json",
					type : "post",
					loading : true,
					dataType : "json",
					data : null,
					success :function(data) {
						$msg.alert($this,"更新成功");
						search(false);
						$this.remove();
					},
				});
				return true;
			},
			cancelValue : '取消',
			cancel : function() {
			}
		}).showModal();
	});
	$(".page-size-sel").on("change", function() {
		$("#size").val($(this).val());
		$("#seach-btn").click();
	});
	//新增页面
	var addView=function(id,dataId){
		$.httpUtil.curl({
			url : url.api  +"/add.jhtml",
			loading : false,
			success : function(data) {
				dialog({
					id : id,
					title : "添加新用户",
					cancelValue : '取消',
					bodyClass : "ui-dialog-body-min",
					height : "600",
					width : "450",
					content:data,
					onclose: function () {
						search(false);
					},
				}).showModal();
			}
		});
	};
	var $add= $("#btn-add");
	$add.on("click", function() {
		var id = $(this).attr("id");
		var dataId = $(this).attr("data");
		addView(id,dataId);
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