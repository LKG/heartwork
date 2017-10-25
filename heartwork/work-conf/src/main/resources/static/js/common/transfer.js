define(function(require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	$.fn.transferItem = function(options) {
		function transferItem($this, options) {
			this.init($this, options)
		}
		transferItem.prototype = {
			init : function($this, options) {
				this.el = $this;
				this.ops = options;
				this.transferAllCheck = this.el.find(".transfer-all-check");
				this.switchBtn = this.el.find(".to-switch");
				this.allCheckedBoxes = this.el.find(".tyue-checkbox-input");
				this.alldivBoxes = this.el.find(".ty-tree-div");
				this.checkBoxEvent();
				this.allCheckEvent();
				this.switchEvent();
				this.checkBoxesDbClick();
				time = null;
			},
			 //按钮切换事件
			switchEvent : function() {
				var that = this;
				this.switchBtn.on("click", function() {
					that.transferAllCheck.removeAttr("checked", "checked");
					var _this = $(this);

					var a_tagClass = null;
					var $transfer=_this.parents(".ty-transfer");
					if (_this.hasClass("ty-transfer-btn-toright")) {
						findCheckbox = $transfer.find(".transfer-list-left li");
						inputCheckbox = $transfer.find(".transfer-list-right ul");
						a_tagClass = "ty-transfer-btn-toright";
					} else {
						findCheckbox = $transfer.find(".transfer-list-right li");
						inputCheckbox = $transfer.find(".transfer-list-left ul");
						a_tagClass = "ty-transfer-btn-toleft";
					}

					var checkBox = findCheckbox.find(":checked");
					if (checkBox != 0) {
						var arrVal = [];
						checkBox.each(function() {
							$(this).removeAttr("checked");
							var appendText = $(this).parents(".ty-tree-div").parent("li");
							arrVal.push(appendText);
							that.removeActiveEvent(a_tagClass, "active");
							that.addActiveEvent(a_tagClass, "disabled");
						});
						inputCheckbox.prepend(arrVal);
					}

				})
			},

			 //所有标签单击选中事件
			checkBoxEvent : function() {
				var that = this;
				this.allCheckedBoxes.on("click", function() {
					clearTimeout(time);
					time = setTimeout(
							function() {
								var classNames = that.checkTagClass($(this));
								if ($(this).is(":checked")) {
									that.removeActiveEvent(classNames[0],"disabled");
									that.addActiveEvent(classNames[0],"active");
									if (!$("." + classNames[1]).hasClass("active")) {
										that.addActiveEvent(classNames[1],"disabled");
									}
								} else {
									var siblingsTag = $(this).parents(".ty-tree-div").parent("li").siblings("li").find(".tyue-checkbox-input");
									if (!siblingsTag.is(":checked")) {
										that.removeActiveEvent(classNames[0],"active");
										that.addActiveEvent(classNames[0],"disabled");
										$(this).parents(".ty-transfer").find(".transfer-all-check").removeAttr("checked","checked");
									}
								}
							}.bind(this), 200);

				});
			},

	           //所有按钮双击事件
			checkBoxesDbClick : function() {
				var that = this;

				this.alldivBoxes.bind("dblclick", function(event) {
					var _this = $(this);
					$(this).removeAttr("checked");

					if (_this.parents(".ty-transfer-list").hasClass("transfer-list-left")) {
						inputCheckbox = _this.parents(".ty-transfer").find(".transfer-list-right ul");
						btnCheckbox = that.el.find(".ty-transfer-btn-toright");
					} else {
						inputCheckbox = _this.parents(".ty-transfer").find(".transfer-list-left ul");
						btnCheckbox = that.el.find(".ty-transfer-btn-toleft");
					}

					var siblingsTag = _this.parent("li").siblings("li").find(".tyue-checkbox-input");
					if (!siblingsTag.is(":checked")) {
						btnCheckbox.removeClass("active");
					}
					var appendText = _this.parent("li");
					inputCheckbox.prepend(appendText);
					appendText.find(".tyue-checkbox-input").removeAttr("checked");

				});
			},
			   //全选按钮事件
			allCheckEvent : function() {
				var that = this;
				this.transferAllCheck.on("click", function() {
					var checkBoxs = $(this).parents(".ty-transfer-list-foot").siblings(".ty-transfer-list-body").find(":checkBox");

					var classNames = that.checkTagClass($(this));

					if ($(this).prop("checked") == true) {
						checkBoxs.attr("checked", "checked");
						that.removeActiveEvent(classNames[0], "disabled");
						that.addActiveEvent(classNames[0], "active");
						if (!$("." + classNames[1]).hasClass("active")) {
							that.addActiveEvent(classNames[1], "disabled");
						}
					} else {
						checkBoxs.removeAttr("checked", "checked");
						that.removeActiveEvent(classNames[0], "active");
						that.addActiveEvent(classNames[0], "disabled");
					}
				})
			},
			//按钮添加class事件
			checkTagClass : function($that) {
				var parentsTransfer = $that.parents(".ty-transfer-list");
				var tagClass = null;
				var tagRemoveClass = null;

				if (parentsTransfer.hasClass("transfer-list-left")) {
					tagClass = "ty-transfer-btn-toright"
					tagRemoveClass = "ty-transfer-btn-toleft";
				} else {
					tagClass = "ty-transfer-btn-toleft"
					tagRemoveClass = "ty-transfer-btn-toright";
				}
				return [ tagClass, tagRemoveClass ];
			},
			addActiveEvent : function(position, addClasses) {
				this.el.find("." + position).addClass(addClasses);
			},
			removeActiveEvent : function(position, addClasses) {
				this.el.find("." + position).removeClass(addClasses);
			},

		}

		new transferItem(this, options);
	}
});