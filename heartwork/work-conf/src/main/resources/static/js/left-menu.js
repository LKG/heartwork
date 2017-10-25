define(function(require, exports, moudles) {
	var $ = require('jquery');
	var httpUtil = require('js/common/httpUtil.js');
	var $msg= require('/js/common/alerts.js');
	var store = require('store');
	var $baseRoot = $('#baseRoot');
	var baseRoot = $baseRoot.attr("href");
	$(".btn .fa").on("mouseover",function(){
		var $this=$(this);
		$this.addClass('animated shake infinite');
		setTimeout(function(){
			$this.removeClass("infinite");
		}, 1000);
	}).on("mouseover",function(){
		$(this).addClass('bounce  infinite');
	});
	var url = {
		userInfoApi : baseRoot + "/userinfo/",
	};
	$("#sidebar-collapse-bottom,#sidebar-collapse-top").on("click",function(){
		 var $body = $('body');
		 if ($body.hasClass("page-sidebar-closed")) {
			 $body.removeClass("page-sidebar-closed");
		 }else{
			 $body.addClass("page-sidebar-closed");
		 }	 
	});
	$("#btn-org-sel").on("click",function(){
		var $snOrg=$("#nav-header-org");
		 if ($snOrg.hasClass("open")) {
			 $snOrg.removeClass("open");
		 }else{
			 $snOrg.addClass("open");
		 }
	});
	$("#nav-header-org").on("mouseout","a", function(){
		setTimeout(function() {
			var $snOrg=$("#nav-header-org");
			$snOrg.removeClass("open");
		}, 2000);
	});
	$("#nav-header-org").on("click","a", function(){
		var $this=$(this);
		if($this.hasClass("selected")){
			$("#nav-header-org").removeClass("open");
			return;
		}else{
			var relateId = $this.attr("data-relateId");
			 //更改默认机构
			var d = dialog({
				title : '消息',
				content : '是否要更改默认机构？',
				okValue : '确 定',
				ok : function() {
					$("#nav-header-org").removeClass("open");
					//移除同级展开节点
					$this.siblings().removeClass("selected");
					$this.addClass("selected");
					var that = this;
					that.title('提交中..');
					$.httpUtil.curl({
						url : url.userInfoApi +"/setdefault/"  +relateId+".json",
						type : "post",
						loading : true,
						dataType : "json",
						success : function(data) {
							 var content="更改默认机构成功";
				        	   if(data.success){
				        		   $msg.alert($this,content);
				        			setTimeout(function() {
				        		  		   window.parent.location.reload();
				        			}, 1000);
				        		   return ;
				        	   }else{
				        		   if(data.result &&data.result.error_description){
				        			   content=data.result.error_description;
				             	   }else{
				             		  content="更改默认机构失败";
				             	   }
				        	   }
				        	   $msg.alert($this,content);
						},
					});
					return true;
				},
				cancelValue : '取消',
				cancel : function() {
				}
			}).showModal();
		}
	});
	var $selOrg=$("#nav-header-org a.selected");
	store.set('userOrg', { orgId: $selOrg.attr("data-orgId"), orgType: $selOrg.attr("data-orgType"),  orgCode: $selOrg.attr("data-orgCode")})
	$(".nav-list").on("click" ,"li",function(h){
		var g=$(h.target).closest("a");
		if(!g||g.length==0){
			return;
		}
		if(!g.hasClass("dropdown-toggle")){
			return;
		};
		if($(this).find("a.dropdown-toggle")){
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}else{
				//移除同级展开节点
				$(this).siblings("li").removeClass("active").removeClass("open");
				$(this).addClass("active");
			}
		}
	});
});