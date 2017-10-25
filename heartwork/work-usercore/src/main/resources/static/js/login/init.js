define(function (require, exports, moudles) {
		var $ = require('jquery');
		var bgColor="rgb(19, 53, 81)";
		var bgUrl="https://img.alicdn.com/tfs/TB174geRVXXXXcYXVXXXXXXXXXX-2880-1024.png";
		//$("#wrap-login").css('background-color',bgColor);
		//$("#wrap-login").css('background-image',"url("+bgUrl+")");
		//处理小图
		var $imgNode = $('#j_mediaImg'),
		url = $imgNode.attr('src'),
		m = url.match(/(\d+)-(\d+)\.(jpg|jpeg|png|gif)/),
		iw = (m && m[1]) || '435',
		ih = (m && m[2]) || '276';
		$imgNode.css('width', iw+'px').css('height', ih+'px');
		$(window).on("load",function(){
			$("#main-loading").hide();	
		});
		//$imgNode.fadeIn("slow");
		//处理小图
	 var _fImgLoader	=function(imgSrc, fSuccCallBack, fErrorCallBack, nTimeout){
				window.bImgLoaderIsLoaded = false;
				var oImg = document.createElement('IMG');
				if(fSuccCallBack){
					oImg.onload = function(){
						fSuccCallBack();
						window.bImgLoaderIsLoaded = true;
					};
				}
				if(fErrorCallBack){
					oImg.onerror = function(){
						fErrorCallBack();
					};
				}
				var nTime = 0;
				if(nTimeout){
					nTime = nTimeout;
				}
				setTimeout(function(){
						oImg.src = imgSrc;
					}, nTime);
			};
			_fImgLoader(url, function(){
					//加载图片
	 });
			//更改背景图片大小
	 var resizeImg=function(init){
				var $f=$(document.body);
				var $lg_bg=$("#lg_bg");
				var fw=$f.width();
				var fh=$f.height();
				var $lg_img=$lg_bg.find("img:first");
				var e=($lg_img.width() || 1E3)/($lg_img.height() || 1E3),
					g = $lg_bg.width() || fw || 1024,
					d = $lg_bg.height() || fh  || 768;
				if(e < g / d ){
					var top=-(((g/e)-d)/2) + "px";
//					if(init){//首次不调整顶部数据
//						
//					}
					top="0px";
					$lg_img.css("width",g+"px").css("height","auto").css("margin-top",top).css("margin-left","0px");
				}else{
					console.log("fh:"+fh+"");
					var left=-((d * e - g) / 2 )+"px";
					$lg_img.css("width","auto").css("height",d).css("margin-top","0px").css("margin-left",left);
				}
				$lg_img.show();
			};
			
	        resizeImg(true);
			$(window).resize(function(){
				resizeImg();	
			});		
});