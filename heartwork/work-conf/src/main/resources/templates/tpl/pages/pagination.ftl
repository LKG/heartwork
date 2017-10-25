<script id="pagination-template-js" type="text/html">
<nav>
  <div class="pull-right"> 
  <ul class="pagination pagination-sm" style="margin:3px 0px -5px 0px;">
  	<li class="pull-right"><li>
  	<li class="pull-left">
	  	<div class="form-group">
			<select class="form-control" name="size" >
	  	 	<option value="10" selected="selected">10</option>
	  	 	<option value="25">25</option>
	  	 	<option value="50">50</option>
	  	 	<option value="100">100</option>
	  	 	</select>
		</div>
  	</li>
  	<li class="pull-left">
  		<span >记录/页</span>
  	</li>
	<li class="{{if firstPage}}disabled{{/if}}" >
	  <a href="javascript:;" aria-label="Previous">
		<span aria-hidden="true"><@spring.message  code="label.default.pagination.first" /></span>
	  </a>
	</li>
	<li class="{{if firstPage}}hidden{{/if}}" >
	  <a href="javascript:;" aria-label="Previous">
		<span aria-hidden="true"><@spring.message  code="label.default.pagination.previous" /></span>
	  </a>
	</li>

	<li class="disabled"><a href="javascript:;">..</a></li>
	<li class="{{if lastPage}}hidden{{/if}}">
	  <a href="javascript:;" aria-label="Next">
		<span  aria-hidden="true"><@spring.message  code="label.default.pagination.next" /></span>
	  </a>
	</li>
	<li class="{{if lastPage}}disabled{{/if}}" >
	  <a href="javascript:;" aria-label="Next">
		<span aria-hidden="true"><@spring.message  code="label.default.pagination.last" /></span>
	  </a>
	</li>
	<li >
		<span class="pagination-span" aria-hidden="true">
		<input class="input-block-level" maxlength="3" 
		placeholder='<@spring.message  code="label.default.pagination.page" />' title="<@spring.message  code="label.default.pagination.page" />" tabindex="3" type="text"  />
		<a href="javascript:;" class="btn" aria-label="GO">
		<span  aria-hidden="true">&nbsp;GO&nbsp;</span>
	 	 </a>
		</span>
	</li>

  </ul>
  </div>
</nav>
</script>
