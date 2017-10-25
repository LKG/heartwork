define(function (require, exports, moudles) {
	var $ = require('jquery');
	var jQuery = require('jquery');
	var template =require('../../modules/arttemplate/template-native.js');
	 template.helper("substr", function(content,begin,end){  
		  var mphone = content.substr(begin,end);
         return content.replace(mphone,"***");  
     });
	  function showNum(f){
		  if(f>=10000){
             return parseInt(f/10000) +"万";
         }else if(f<10000 && f>=1000){
             return parseInt(f/1000) +"千";
         }else{
             return f;
         }
	  }
	  function showMoney(f){
		  if(f>=10000){
             return parseInt(f/10000) +"万";
         }else{
             return f;
         }
	  }
	  template.helper("show_num", showNum); 
	  template.helper("show_money", showMoney);
	  return template;
});