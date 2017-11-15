<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
  <div class="form-group" >
	<div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">名称</span>
	  	<input type="hidden" class="form-control" id="id"  value="${result.id}" name="id" >
         	<input type="text" class="form-control" id="name" value="<#if result.name??>${result.name}</#if>" name="name" placeholder="名称"/>
          <div class="input-group-btn">
           	<select name="status" id="status"  data="<#if result.status??>${result.status}</#if>" style="min-width:100px;"   class="form-control">
				<option value="pending">未使用</option>
				<option value="disabled">禁用</option>
				<option value="enabled">可用</option>
			</select>
          </div>
        </div>
	</div>
 </div>
  <div class="form-group">
	<div class="col-xs-12">
		<div class="input-group">
			 <span class="input-group-addon">logo</span>
			 	<input type="text" class="form-control" id="logo" value="<#if result.logo??>${result.logo}</#if>" name="logo" placeholder="logo"/>
 		</div>
	</div>
 </div>
 <div class="form-group">
	<div class="col-xs-12">
		<div class="input-group">
			 <span class="input-group-addon">URL</span>
			 <input type="text" class="form-control" id="url" value="<#if result.url??>${result.url}</#if>" name="url" placeholder="友链URL"/>
 		</div>
	</div>
 </div>
  <div class="form-group">
	<label for="remark" class="col-xs-2 control-label" >描述</label>
	<div class="col-xs-10">
	<textarea class="form-control" id="remark" name="remark" placeholder="描述" rows="3"><#if result.remark??>${result.remark}</#if></textarea>
	</div>
  </div>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>