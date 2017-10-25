  ;(function() {
	if(navigator.userAgent.indexOf("MSIE")!=-1){
		NProgress.configure({
		template: "<div class='bar' role='bar'><div class='peg'></div></div><div class='spinner' role='spinner'><i class='fa fa-circle-o-notch fa-spin'></i></div>"
		});
		
	}
    NProgress.start();
   
    window.onload = NProgress.done;
    var ready = function(fn) {
      var doc = document;
      if(doc.addEventListener) {
        doc.addEventListener('DOMContentLoaded', fn, false);
      } else {
        doc.attachEvent('onreadystatechange', fn);
      }
    };
    ready(function() {
      NProgress.inc(0.6);
    }, false);
  })();