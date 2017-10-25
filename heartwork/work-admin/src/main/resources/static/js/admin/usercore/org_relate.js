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
		orgApi : baseRoot + "/admin/org",
		orgRelationApi : baseRoot + "/admin/org/relate",
	};
	var $msg= require('/js/common/alerts.js');
	var getAreas = require('/js/common/areas.js');//地区选择控件
	//地区选择BEGIN
	 var $provinceId=$("#repair_provinceId");
	 var $cityId=$("#repair_cityId");
	 var $areaId=$("#repair_areaId");
	 getAreas($provinceId, "0" , $provinceId.attr("data-pid"));
	 getAreas($cityId, $provinceId.attr("data-pid"),$cityId.attr("data-pid"));
	 getAreas($areaId, $cityId.attr("data-pid"), $areaId.attr("data-pid"));
	$provinceId.on("change", function() {
		var parentCode = $(this).val();
		$cityId.empty();
		$areaId.empty();
		getAreas($cityId, parentCode, "");
		
	});
	$cityId.on("change", function() {
		var parentCode = $(this).val();
		getAreas($areaId, parentCode, "");
	});
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
	var $repairTbody = $("#repair-table-tbody");
	// 全选事件
	$("#my-tab-rule").on("click", "#my-tab-relating",function(){
		if($(this).hasClass("active")){
			return;
		}
		$("#repair-seach-btn").click();
	});
	$("#my-tab-rule").on("click", "#my-tab-related",function(){
		if($(this).hasClass("active")){
			return;
		}
		$("#related-org-seach-btn").click();
	});
	$("#orgBtnSelectAll").on("change",function() {
		var $checkBox = $repairTbody.find("input[name='id']:not(:disabled)");
		if ($(this).attr("checked")) {
			$checkBox.attr("checked",'true');// 全选
	    } else {
			$checkBox.removeAttr("checked");// 反选
		}
	});

	var $addRepairBtn = $("#add-repair-btn");
	$addRepairBtn.on("click", function() {
		var $this=$(this);
		var $checkedBox = $repairTbody.find("input[name='id']:checked:not(:disabled)");
		if($checkedBox.length==0 ){
	     	$msg.alert($(this),"请选择要关联的数据");
			return;
		}
		var datas=[];
		$checkedBox.each(function(){ 
			var val=$(this).val();
			if(val!=''&&val!=null){
				var name=$(this).attr("name");
				var serializeObj={};
				serializeObj[name]=val;
				var $isDefault=$(this).parents("tr:first").find("input[name='isDefault']");
				var isDef=$isDefault.attr("name");
				if($isDefault.is(':checked')){
					serializeObj[isDef]=1;
				}else{
					serializeObj[isDef]=0;
				}
				datas.push(serializeObj)
			}
		});
		datas=JSON.stringify(datas);
		var hideOrgId = $('#hideOrgId').val();
		var relateType = $('#categoryIds').val();
		if(relateType=="" ){
			$msg.alert($this,"请选择维修类型");
			return;
		}
		var id = $(this).attr("id");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要关联？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.orgApi +"/"  +hideOrgId+"/addRelate/"  +relateType+".json",
					type : "post",
					loading : true,
					dataType : "json",
					data : {datas:datas},
					success : function(data) {
						 var content="添加成功";
			        	   if(data.success){
			        		   $msg.alert($(this),content);
				        		search(false);
			        		   return ;
			        	   }else{
			        		   if(data.result &&data.result.error_description){
			        			   content=data.result.error_description;
			             	   }else{
			             		  content="添加失败";
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
	$("#repair-seach-btn").on("click", function() {
		search(false);
		$("#repair-page").val("1");
	});

	$("#repair-paginationSize").on("change",function(){
		$("#repair-size").val($(this).val());
		$("#repair-page").val(1);
		search(false);
	});
	var search = function(loading) {
		var hideOrgId = $('#hideOrgId').val();
		$("#orgBtnSelectAll").removeAttr("checked");// 取消选中
		var param = $("#repair_search_form").serialize();
		var relateType = $('#categoryIds').val();
		if(relateType=="" ){
			$msg.alert($this,"请选择类型");
			return;
		}
		$.httpUtil.curl({
					url : url.orgApi+"/"+hideOrgId+"/relate/orgs.json",
					type : "get",
					dataType : "json",
					loading : loading,
					data : param,
					success : function(data) {
						if (data.success) {
							var html = template('repair-tr-template-js', data.result);
							$("#repair-paginationTotal").html(data.result.totalElements);
							$repairTbody.empty();
							$repairTbody.append(html);
							// 加载分页组件
							laypage({
								cont : 'repair-table-pagination', // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
								pages : data.result.totalPages, // 通过后台拿到的总页数
								curr : Number(data.result.number) + 1, // 初始化当前页
								skip : true,
								skin : '#AF0000',
								jump : function(obj, first) { // 触发分页后的回调
									if (!first) {
										var curr = obj.curr;
										$("#repair_search_form").find("#repair-page").val(curr);
										search(false);
									}
								}
							});
						}
					}
		});
	}
	// 已关联记录
	var $relatedRepairTbody = $("#related-repair-tbody");
	$("#relatedorgBtnSelectAll").on("change",function() {
		var $checkBox = $relatedRepairTbody.find("input[name='id']:not(:disabled)");
		if ($(this).attr("checked")) {
			$("#related-remove-btn").removeAttr("disabled");
			$checkBox.attr("checked",'true');// 全选
	    } else {
			$checkBox.removeAttr("checked");// 反选
			$("#related-remove-btn").attr("disabled","disabled");
		}
	});
	$("#related-org-seach-btn").on("click", function() {
		$("#related-repair-size").val($(this).val());
		$("#related-repair-page").val(1);
		searchRelated(false);
	});
	$("#related-repair-paginationSize").on("change",function(){
		$("#related-repair-size").val($(this).val());
		$("#related-repair-page").val(1);
		searchRelated(false);
	});
	setTimeout(function() {
		$("#related-org-seach-btn").click();
	}, 10);
	var searchRelated = function(loading) {
		var hideOrgId = $('#hideOrgId').val();
		$("#relatedorgBtnSelectAll").removeAttr("checked");// 取消选中
		var param = $("#related_repair_search_form").serialize();
		$.httpUtil.curl({
					url : url.orgApi+"/"+hideOrgId+"/relateds.json",
					type : "get",
					dataType : "json",
					loading : loading,
					data : param,
					success : function(data) {
						if (data.success) {
							var html = template('related-repair-tr-template-js', data.result);
							$("#related-repair-paginationTotal").html(data.result.totalElements);
							$relatedRepairTbody.empty();
							$relatedRepairTbody.append(html);
							// 加载分页组件
							laypage({
								cont : 'related-repair-table-pagination', // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
								pages : data.result.totalPages, // 通过后台拿到的总页数
								curr : Number(data.result.number) + 1, // 初始化当前页
								skip : true,
								skin : '#AF0000',
								jump : function(obj, first) { // 触发分页后的回调
									if (!first) {
										var curr = obj.curr;
										$("#related_repair_search_form").find("#related-page").val(curr);
										searchRelated(false);
									}
								}
							});
						}
					}
		});
	}
	var getRelatedCheckedBox=function(){
		var $checkedBox = $relatedRepairTbody.find("input[name='id']:checked:not(:disabled)");
		var dataIds =[];
		$checkedBox.each(function(){ 
			var val=$(this).val();
			if(val!=''&&val!=null){
				dataIds.push(val); 
			}
		});
		return dataIds;
	};
	var $relatedremoveId = $("#related-remove-btn");
	$relatedremoveId.on("click", function() {
		var $this=$(this);
		var dataIds =getRelatedCheckedBox();
		if(dataIds.length==0 ){
			$msg.alert($this,"请选择要取消关联的记录");
			return;
		}
		var id = $(this).attr("id");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要取消关联记录？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.orgRelationApi+"/" +dataIds + "/broken.json",
					type : "post",
					loading : false,
					dataType : "json",
					data : null,
					success : function(data) {
						 var content="取消关联成功";
			        	   if(data.success){
			          			$msg.alert($(this),content);
			        		   $("#related-org-seach-btn").click();
			        		   return ;
			        	   }else{
			        		   if(data.result &&data.result.error_description){
			        			   content=data.result.error_description;
			             	   }else{
			             		  content="取消关联失败";
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
	
	$("#tab-pane-related").on("click", ".operate .btn-del", function() {
		var $this=$(this);
		var id = $(this).attr("id");
		var dataId = $this.attr("data");
		var d = dialog({
			id : id,
			title : '消息',
			content : '是否要取消关联？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.orgRelationApi+"/" +dataId + "/broken.json",
					type : "post",
					loading : true,
					dataType : "json",
					data : null,
					success :function(data) {
						 var content="取消关联成功";
			        	   if(data.success){
			          			$msg.alert($(this),content);
			          		   $("#related-org-seach-btn").click();
			        		   return ;
			        	   }else{
			        		   if(data.result &&data.result.error_description){
			        			   content=data.result.error_description;
			             	   }else{
			             		  content="取消关联失败";
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
	
});