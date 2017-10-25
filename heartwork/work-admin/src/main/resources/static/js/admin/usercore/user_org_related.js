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
		userApi : baseRoot + "/admin/user",
	};
	var $msg= require('/js/common/alerts.js');
	var getAreas = require('/js/common/areas.js');//地区选择控件
	//地区选择BEGIN
	 var $provinceId=$("#org_provinceId");
	 var $cityId=$("#org_cityId");
	 var $areaId=$("#org_areaId");
	 getAreas($provinceId, "0" , $provinceId.attr("data-pid"));
	 getAreas($cityId, $provinceId.attr("data-pid"),$cityId.attr("data-pid"));
	 getAreas($areaId, $cityId.attr("data-pid"), $areaId.attr("data-pid"));
	$provinceId.on("change", function() {
		var parentCode = $(this).val();
		getAreas($cityId, parentCode, "");
		$areaId.empty();
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
	$("#my-tab-rule").on("click", "#my-tab-relating",function(){
		if($(this).hasClass("active")){
			return;
		}
		$("#org-seach-btn").click();
	});
	$("#my-tab-rule").on("click", "#my-tab-related",function(){
		if($(this).hasClass("active")){
			return;
		}
		$("#related-org-seach-btn").click();
	});
	var $orgTbody = $("#org-table-tbody");
	// 全选事件

	$("#orgBtnSelectAll").on("change",function() {
		var $checkBox = $orgTbody.find("input[name='id']:not(:disabled)");
		if ($(this).attr("checked")) {
			$checkBox.attr("checked",'true');// 全选
	    } else {
			$checkBox.removeAttr("checked");// 反选
		}
	});

	var $addOrgBtn = $("#add-org-btn");
	$addOrgBtn.on("click", function() {
		var $this=$(this);
		var $checkedBox = $orgTbody.find("input[name='id']:checked:not(:disabled)");
		if($checkedBox.length==0 ){
			$msg.alert($this,"请选择要添加的数据");
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
		var hideId = $('#hideUserId').val();
		var id = $(this).attr("id");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要关联选中机构？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.userApi +"/"  +hideId+"/addOrg.json",
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
	$("#org-seach-btn").on("click", function() {
		search(false);
		$("#org-page").val("1");
	});

	$("#org-paginationSize").on("change",function(){
		$("#org-size").val($(this).val());
		$("#org-page").val(1);
		search(false);
	});
	var search = function(loading) {
		var hideId = $('#hideUserId').val();
		$("#orgBtnSelectAll").removeAttr("checked");// 取消选中
		var param = $("#org_search_form").serialize();
		$.httpUtil.curl({
					url : url.userApi+"/"+hideId+"/orgs.json",
					type : "get",
					dataType : "json",
					loading : loading,
					data : param,
					success : function(data) {
						if (data.success) {
							var html = template('org-tr-template-js', data.result);
							$("#org-paginationTotal").html(data.result.totalElements);
							$orgTbody.empty();
							$orgTbody.append(html);
							// 加载分页组件
							laypage({
								cont : 'org-table-pagination', // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
								pages : data.result.totalPages, // 通过后台拿到的总页数
								curr : Number(data.result.number) + 1, // 初始化当前页
								skip : true,
								skin : '#AF0000',
								jump : function(obj, first) { // 触发分页后的回调
									if (!first) {
										var curr = obj.curr;
										$("#org_search_form").find("#org-page").val(obj.curr);
										search(false);
									}
								}
							});
						}
					}
		});
	}
	// 已关联记录
	var $relatedOrgTbody = $("#related-org-tbody");
	$("#relatedOrgBtnSelectAll").on("change",function() {
		var $checkBox = $relatedOrgTbody.find("input[name='id']:not(:disabled)");
		if ($(this).attr("checked")) {
			$("#related-remove-btn").removeAttr("disabled");
			$checkBox.attr("checked",'true');// 全选
	    } else {
			$checkBox.removeAttr("checked");// 反选
			$("#related-remove-btn").attr("disabled","disabled");
		}
	});
	$("#related-org-seach-btn").on("click", function() {
		$("#related-org-size").val($(this).val());
		$("#related-org-page").val(1);
		searchRelated(false);
	});
	$("#related-org-paginationSize").on("change",function(){
		$("#related-org-size").val($(this).val());
		$("#related-org-page").val(1);
		searchRelated(false);
	});
	setTimeout(function() {
		$("#related-org-seach-btn").click();
	}, 10);
	var searchRelated = function(loading) {
		var hideId = $('#hideUserId').val();
		$("#relatedOrgBtnSelectAll").removeAttr("checked");// 取消选中
		var param = $("#related_org_search_form").serialize();
		$.httpUtil.curl({
					url : url.userApi+"/"+hideId+"/related/orgs.json",
					type : "get",
					dataType : "json",
					loading : loading,
					data : param,
					success : function(data) {
						if (data.success) {
							var html = template('related-org-tr-template-js', data.result);
							$("#related-org-paginationTotal").html(data.result.totalElements);
							$relatedOrgTbody.empty();
							$relatedOrgTbody.append(html);
							// 加载分页组件
							laypage({
								cont : 'related-org-table-pagination', // 容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div	// id="page1"></div>
								pages : data.result.totalPages, // 通过后台拿到的总页数
								curr : Number(data.result.number) + 1, // 初始化当前页
								skip : true,
								skin : '#AF0000',
								jump : function(obj, first) { // 触发分页后的回调
									if (!first) {
										var curr = obj.curr;
										$("#related_org_search_form").find("#related-size").val(obj.curr);
										searchRelated(false);
									}
								}
							});
						}
					}
		});
	}
	var getRelatedCheckedBox=function(){
		var $checkedBox = $relatedOrgTbody.find("input[name='id']:checked:not(:disabled)");
		var dataIds =[];
		$checkedBox.each(function(){ 
			var val=$(this).val();
			if(val!=''&&val!=null){
				dataIds.push(val); 
			}
		});
		return dataIds;
	};
	$relatedOrgTbody.on("click","input[type=radio]:not(:disabled)",function(){
		var id = $(this).attr("id");
		var data = $(this).attr("data");
		var d = dialog({
			id:id,
			title : '消息',
			content : '是否要设置为默认？',
			okValue : '确 定',
			ok : function() {
				var that = this;
				that.title('提交中..');
				$.httpUtil.curl({
					url : url.userApi+"/setdefaultOrg/" +data + ".json",
					type : "post",
					loading : false,
					dataType : "json",
					data : null,
					success : function(data) {
						 var content="设置默认机构成功";
			        	   if(data.success){
			             	   $msg.alert($(this),content);
			        			$("#related-org-seach-btn").click();
			        		   return ;
			        	   }else{
			        		   if(data.result &&data.result.error_description){
			        			   content=data.result.error_description;
			             	   }else{
			             		  content="取消默认机构失败";
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
	
	
	var $relatedRemoveId = $("#related-remove-btn");
	$relatedRemoveId.on("click", function() {
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
					url : url.userApi+"/org/" +dataIds + "/broken.json",
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
});