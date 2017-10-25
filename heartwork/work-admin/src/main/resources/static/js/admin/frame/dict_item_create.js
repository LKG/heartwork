define(function(require, exports, moudles) {
	var $ = require('jquery');
	var template = require('arttemplate');
	var dialog = require('artDialog');
	var laypage = require('laypage');
	window.dialog = dialog;
	var httpUtil = require('js/common/httpUtil.js');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	var url = {
		api : baseRoot + "/admin/dict",
		itemCreateApi : baseRoot + "/admin/dict/item/save.json",
		itemApi : baseRoot + "/admin/dict/item",
	};
	var $msg= require('/js/common/alerts.js');
	 require('/js/common/validate');
	$("#my-tab-rule").on("click", "li", function(h) {
		var g=$(h.target).closest("a");
		if(!g||g.length==0){
			return;
		}
		if($(this).hasClass("active")){
			return;
		}else{
			//移除同级展开节点
			$(this).siblings("li").removeClass("active").removeClass("open");
			$(this).addClass("active");
		}
		var datakey=$(this).find("a").data("key");
		var $mytabpanel=$("#tab-pane-"+datakey);
		if($mytabpanel.hasClass("active")){
			$mytabpanel.removeClass("active").removeClass("in");
		}else{
			//移除同级展开节点
			$mytabpanel.siblings(".tab-pane").removeClass("active").removeClass("in");
			$mytabpanel.addClass("active").addClass("in");
		}
	});
   	var topdialog = top.dialog.get(window);
   	if(undefined===topdialog){
   		topdialog=dialog.getCurrent();
   	}
    var $mainForm=$("#J_ItemMainForm");
    $mainForm.validate({
		rules: {
			itemCode: {
				 required: true,
				 minlength: 3,
				 maxlength: 30,
				 isNotChinese: true,
				 isUid:true,//检验特殊字符
			},
			parentId: {
				required: true,
			},
			itemValue: {
				 required: true,
			},
			itemName: {
				 minlength: 1,
				 required: true,
			},
		},
		messages:{
			itemCode: {
				 required: "字典名称不能为空",
				 remote: "字典标识已经被使用",	 
				 isUid:  "字典标识非法",//检验特殊字符
			},
			itemName: {
				 required: "字典名称不能为空",
			},
			itemValue: {
				 required: "字典值不能为空",
			},
		},
		submitHandler: function(form) { 
//			form.action=url.createArea ;
//			form.submit();
		}
	});
    $("#J_Submit").on("click",function(){
		 if(!$mainForm.valid()){
			 return;
		 }
		var $btn=$(this);
		var dataHtml=$(this).html();
		$mainForm.attr("action",url.createApi);
		$btn.html($btn.attr("data-loading-text")).attr("disabled","disabled");
		$mainForm.ajaxSubmit({
			url:url.itemCreateApi,
			type:"post",
			success: function(data) {
				if(data.success){
					$msg.alert($btn,"<i class='fa  fa-check-circle text-success'></i>数据更新成功");
					$mainForm.resetForm();
					setTimeout(function() {
						$("#my-tab-related").click();
					}, 1000);
				}else{
					$msg.alert($btn,"<i class='fa fa-exclamation-triangle text-danger'></i>数据更新异常");
				}
				
			}
		}); 
		$btn.html(dataHtml).removeAttr("disabled");
	});
	$("#my-tab-rule").on("click", "#my-tab-related",function(){
		if($(this).hasClass("active")){
			return;
		}
		$("#related-item-seach-btn").click();
	});
	var $relatedTbody = $("#related-item-tbody");
	var getRelatedCheckedBox=function(){
		var $checkedBox = $relatedTbody.find("input[name='itemId']:checked:not(:disabled)");
		var dataIds =[];
		$checkedBox.each(function(){ 
			var val=$(this).val();
			if(val!=''&&val!=null){
				dataIds.push(val); 
			}
		});
		return dataIds;
	};
	var $relatedremoveId = $("#related-item-remove-btn");
	$relatedremoveId.on("click", function() {
		var $this=$(this);
		var dataIds =getRelatedCheckedBox();
		if(dataIds.length==0 ){
			$msg.alert($this,"请选择要删除的记录");
			return;
		}
		var id = $(this).attr("id");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要删除记录？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.itemApi+"/" +dataIds + "/delete.json",
					type : "post",
					loading : false,
					dataType : "json",
					data : null,
					success : function(data) {
						 var content="删除成功";
			        	   if(data.success){
			          			$msg.alert($(this),content);
			        		   $("#related-item-seach-btn").click();
			        		   return ;
			        	   }else{
			        		   if(data.result &&data.result.error_description){
			        			   content=data.result.error_description;
			             	   }else{
			             		  content="删除失败";
			             	   }
			        	   }
			   			$msg.alert($(this),content);
					},
				});
				return true;
			},
			cancelValue : '取消',
			cancel : function() {
			}
		}).showModal();
	});
	// 已关联记录

	$("#relatedBtnSelectAll").on("change",function() {
		var $checkBox = $relatedTbody.find("input[name='itemId']:not(:disabled)");
		if ($(this).attr("checked")) {
			$checkBox.attr("checked",'true');// 全选
	    } else {
			$checkBox.removeAttr("checked");// 反选
		}
	});
	$("#related-item-seach-btn").on("click", function() {
		$("#related-item-size").val($(this).val());
		$("#related-item-page").val(1);
		searchRelated(false);
	});
	$("#related-item-paginationSize").on("change",function(){
		$("#related-item-size").val($(this).val());
		$("#related-item-page").val(1);
		searchRelated(false);
	});
	setTimeout(function() {
		$("#related-item-seach-btn").click();
	}, 10);
	var searchRelated = function(loading) {
		var hideDictId = $('#hideDictId').val();
		$("#relatedBtnSelectAll").removeAttr("checked");// 取消选中
		var param = $("#related_item_search_form").serialize();
		$.httpUtil.curl({
					url : url.api+"/"+hideDictId+"/items.json",
					type : "get",
					dataType : "json",
					loading : loading,
					data : param,
					success : function(data) {
						if (data.success) {
							var html = template('related-item-tr-template-js', data.result);
							$("#related-item-paginationTotal").html(data.result.totalElements);
							$relatedTbody.empty();
							$relatedTbody.append(html);
							//处理父类选择框
							var $select=$mainForm.find("#parentId");
							$select.empty();
							$select.append($("<option>").text("无").val("0"));
							$.each(data.result.content, function(index, item) {
								var $option = $("<option>").text(item.itemName+"("+item.itemCode+")").val(item.itemId);
								$select.append($option);
							});
							// 加载分页组件
							laypage({
								cont : 'related-item-table-pagination', // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
								pages : data.result.totalPages, // 通过后台拿到的总页数
								curr : Number(data.result.number) + 1, // 初始化当前页
								skip : true,
								skin : '#AF0000',
								jump : function(obj, first) { // 触发分页后的回调
									if (!first) {
										var curr = obj.curr;
										$("#related_item_search_form").find("#related-item-page").val(curr);
										searchRelated(false);
									}
								}
							});
						}
					}
		});
	}

});