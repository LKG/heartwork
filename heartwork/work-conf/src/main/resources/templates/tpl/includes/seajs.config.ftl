<script src="${contextPath}/modules/seajs/2.3.0/sea.js" data-main="application/application"  id="seajsnode" ></script>
<script   type="text/javascript" id="seajsconfig" >
  	seajs.config({
  	  charset: 'utf-8',
	  base: '${contextPath}/',
	 // 设置路径，方便跨目录调用
	  paths: {
		'jquery': '${contextPath}/modules/jquery',
		'store': '${contextPath}/modules/store',
		'editor.md': '${contextPath}/modules/editor.md',
		'select2': '${contextPath}/modules/select2',
		'galleria': '${contextPath}/modules/galleria',
		'swiper': '${contextPath}/modules/swiper',
		
	  },
	  alias: {
		'$': 'jquery/1.8.2/jquery.min.js',
		'jquery': 'jquery/1.8.2/jquery.min.js',
	    'store': 'store/1.3.7/store.js',
	    'editor.md': 'editor.md/editormd.min.js',
	    'link-dialog': 'editor.md/plugins/link-dialog/link-dialog.js',
	    'reference-link-dialog': 'editor.md/plugins/reference-link-dialog/reference-link-dialog.js',
	    'image-dialog': 'editor.md/plugins/image-dialog/image-dialog.js',
	    'code-block-dialog': 'editor.md/plugins/code-block-dialog/code-block-dialog.js',
	    'table-dialog': 'editor.md/plugins/table-dialog/table-dialog.js',
	    'emoji-dialog': 'editor.md/plugins/emoji-dialog/emoji-dialog.js',
	    'goto-line-dialog': 'editor.md/plugins/goto-line-dialog/goto-line-dialog.js',
	    'help-dialog': 'editor.md/plugins/help-dialog/help-dialog.js',
		'html-entities-dialog': 'editor.md/plugins/html-entities-dialog/html-entities-dialog.js',
		'preformatted-text-dialog': 'editor.md/plugins/preformatted-text-dialog/preformatted-text-dialog.js',
	    'arttemplate-native': '${contextPath}/js/common/template-native.js',
		'arttemplate': '${contextPath}/js/common/template.js',
	    'laypage': '${contextPath}/js/common/laypage.js',
	    'validate': '${contextPath}/js/common/validate.js',
	    'artDialog': '${contextPath}/js/common/dialog.js',
	    'fine-uploader': '${contextPath}/js/common/fine-uploader.js',
	    'lrz': '${contextPath}/modules/localResizeIMG/dist/lrz.all.bundle.js',
	    'select2': 'select2/4.0.3/js/select2.full.min.js',
	    'galleria': 'galleria/dist/galleria.js',
	    'swiper': 'swiper/js/swiper.jquery.umd.min.js',
	  },

	 });
</script>
