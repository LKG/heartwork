define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	 require('./jquery.validate.js')($);
	 require('./jquery.form.js')($);
	 var $msg= require('/js/common/alerts.js');
	/*
	 * Translated default messages for the jQuery validation plugin.
	 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
	 */
	$.extend($.validator.messages, {
		required: "必须填写",
		remote: "请修正此栏位",
		email: "请输入有效的电子邮件",
		url: "请输入有效的网址",
		date: "请输入有效的日期",
		dateISO: "请输入有效的日期 (YYYY-MM-DD)",
		number: "请输入正确的数字",
		digits: "只可输入数字",
		creditcard: "请输入有效的信用卡号码",
		equalTo: "你的输入不相同",
		extension: "请输入有效的后缀",
		maxlength: $.validator.format("最多 {0} 个字"),
		minlength: $.validator.format("最少 {0} 个字"),
		rangelength: $.validator.format("请输入长度为 {0} 至 {1} 之間的字串"),
		range: $.validator.format("请输入 {0} 至 {1} 之间的数值"),
		max: $.validator.format("请输入不大于 {0} 的数值"),
		min: $.validator.format("请输入不小于 {0} 的数值")
	});
	var validateRegExp = {
		    decmal: "^([+-]?)\\d*\\.\\d+$",
		    // 浮点数
		    decmal1: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",
		    // 正浮点数
		    decmal2: "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$",
		    // 负浮点数
		    decmal3: "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$",
		    // 浮点数
		    decmal4: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$",
		    // 非负浮点数（正浮点数 + 0）
		    decmal5: "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",
		    // 非正浮点数（负浮点数 +
		    // 0）
		    intege: "^-?[1-9]\\d*$",
		    // 整数
		    intege1: "^[1-9]\\d*$",
		    // 正整数
		    intege2: "^-[1-9]\\d*$",
		    // 负整数
		    num: "^([+-]?)\\d*\\.?\\d+$",
		    // 数字
		    num1: "^[1-9]\\d*|0$",
		    // 正数（正整数 + 0）
		    num2: "^-[1-9]\\d*|0$",
		    // 负数（负整数 + 0）
		    ascii: "^[\\x00-\\xFF]+$",
		    // 仅ACSII字符
		    chinese: "^[\\u4e00-\\u9fa5]+$",
		    // 仅中文
		    color: "^[a-fA-F0-9]{6}$",
		    // 颜色
		    date: "^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",
		    // 日期
		    email: "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",
		    // 邮件
		    idcard: "^[1-9]([0-9]{14}|[0-9]{17}|[0-9]{16}[Xx]))$",
		    // 身份证
		    ip4: "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",
		    // ip地址
		    letter: "^[A-Za-z]+$",
		    // 字母
		    letter_l: "^[a-z]+$",
		    // 小写字母
		    letter_u: "^[A-Z]+$",
		    // 大写字母
		    mobile: "^0?(13|15|18|14|17)[0-9]{9}$",
		    // 手机
		    notempty: "^\\S+$",
		    // 非空
		    password: "^.*[A-Za-z0-9\\w_-]+.*$",
		    // 密码
		    fullNumber: "^[0-9]+$",
		    // 数字
		    picture: "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",
		    // 图片
		    qq: "^[1-9]*[1-9][0-9]*$",
		   // ^[1-9]*[1-9][0-9]{5,12}$
		    // QQ号码
		    rar: "(.*)\\.(rar|zip|7zip|tgz)$",
		    // 压缩文件
		    tel: "^[0-9\-()（）]{7,18}$",
		    // 电话号码的函数(包括验证国内区号,国际区号,分机号)
		    url: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",
		    // url
		    username: "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$",
		    // 户名
		    deptname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
		    // 单位名
		    zipcode: "^\\d{6}$",
		    // 邮编
		    realname: "^[A-Za-z\\u4e00-\\u9fa5]+$",
		    //车牌号码
		    /**
		     * 1.常规车牌号：仅允许以汉字开头，后面可录入六个字符，由大写英文字母和阿拉伯数字组成。如：粤B12345；
		     * 2.武警车牌：允许前两位为大写英文字母，后面可录入七个字符，由大写英文字母和阿拉伯数字组成，其中第三位可录汉字也可录大写英文字母及阿拉伯数字，如：WJ01警0081、WJ0112345。
		     * 3.最后一个为汉字的车牌：允许以汉字开头，后面可录入六个字符，前五位字符，由大写英文字母和阿拉伯数字组成，而最后一个字符为汉字，汉字包括“挂”、“学”、“警”、“军”、“港”、“澳”。如：粤Z1234港。
		     * 4.新军车牌：以两位为大写英文字母开头，后面以5位阿拉伯数字组成。如：BA12345。
		     * 5.黑龙江车牌存在08或38开头的情况
		     * /(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/
		    ***/
		    carNo: "^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$|^[a-zA-Z]{2}\d{7}$",
		    // 真实姓名
		    companyname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
		    companyaddr: "^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$",
		    companysite: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&#=]*)?$"
			
	};
	 $.validator.addMethod("isZipcode", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.zipcode).test(value));   
       }, "邮政编码格式错误");
	 $.validator.addMethod("isCarNo", function(value, element) {  
		 return this.optional(element) || (new RegExp(validateRegExp.carNo).test(value));   
       }, "车牌号格式错误");
	 $.validator.addMethod("isPhone", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.mobile).test(value));  
       }, "手机号码格式错误");
	 $.validator.addMethod("isQq", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.qq).test(value));  
       }, "请输入正确的QQ号码");
	 $.validator.addMethod("isEmail", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.email).test(value));  
       }, "邮箱号码格式错误");
	 $.validator.addMethod("isNum1", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.num1).test(value));  
       }, "必需为纯数字");
	 $.validator.addMethod("isIdcard", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.idcard).test(value));  
       }, "身份证号码格式错误");
	 $.validator.addMethod("isPassword", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.password).test(value));  
       }, "密码格式错误");
	 
	 $.validator.addMethod("isUsername", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.username).test(value));  
       }, "账号格式错误");
	 $.validator.addMethod("isRealname", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.realname).test(value));  
       }, "真实姓名包含非法字符");
	 $.validator.addMethod("isUid", function(value, element) {  
		 return this.optional(element) || (new RegExp(validateRegExp.username).test(value));  
       }, "用户名不合法");
	 $.validator.addMethod("isNotfullNumber", function(value, element) {  
		 return this.optional(element) || !(new RegExp(validateRegExp.fullNumber).test(value));  
       }, "用户名不能为纯数字");
	 $.validator.addMethod("notEqualTo", function(value, element, param) {  
			var target = $(param);
   			return value != target.val();
       }, "输入不能相同");
	 $.validator.addMethod("isLetter", function(value, element) {  
         return this.optional(element) || (new RegExp(validateRegExp.letter).test(value));  
       }, "必需为纯字母");
	 $.validator.addMethod("isNotChinese", function(value, element) {  
         return this.optional(element) || (!new RegExp(validateRegExp.chinese).test(value));  
       }, "输入不能为中文");

	//设置默认校验属性
	$.validator.setDefaults({
			debug:true,
			errorElement: "span",
			submitHandler: function(form) { 
				//不提交表单
				//$(form).ajaxSubmit(); 
			},
			highlight: function(element) {
				//错误提示
				var $inputWrap=$(element).closest('div');
				if($inputWrap.hasClass("has-error")){//之前有错误信息不需要高亮处理
					return;
				}
				$inputWrap.removeClass('has-success').addClass('has-error');
				$inputWrap.find('.form-control-feedback').removeClass('glyphicon-ok').addClass('glyphicon-warning-sign');	
			},success: function(label) {
				var $labelWrap=label.closest('div');
				var $inputWrap=$labelWrap.find('.form-control-feedback').closest('div');
				$inputWrap.removeClass('has-error').addClass('has-success');
				$inputWrap.find('.form-control-feedback').removeClass('glyphicon-warning-sign').addClass('glyphicon-ok');

			},errorPlacement: function(error, element) {
				var $inputWrap=element.closest('div');//获取父类
				 if (element.is(':radio') || element.is(':checkbox')) {
					 if(error.html()!=""){
						 $msg.alert($inputWrap,"<i class='fa fa-exclamation-triangle text-danger'></i>"+error.html());
					 }
				 }else{
						error.addClass('text-danger');
						if($inputWrap.length!=0){//按钮组
							error.insertAfter($inputWrap);
						}else{
							error.insertAfter(element);
						}
						var $formFeedback=element.closest('.form-control-feedback');//添加图标
						if($formFeedback.length==0){
							var $span=$('<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>');
							//判断是否为bootstap 按钮组，如果是追加在最后面
							if($inputWrap.hasClass('input-group')){
								var $lastInput= $inputWrap.find(".input-group-btn:last");
								if($lastInput.length!=0){
									$lastInput.after($span);
								}else{
									$groupBtn=$('<span class="input-group-btn"></span>');
									$groupBtn.append($span);
									$(element).after($groupBtn);
								}
							}
							$(element).after($span);
						}
				 }
				
			},
	});
});