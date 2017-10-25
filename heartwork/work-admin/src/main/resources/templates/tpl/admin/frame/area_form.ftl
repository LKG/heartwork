<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
 <div class="form-group">
	<div class="col-xs-12">
		<#if result.code??>
			<div class="input-group">
			 <span class="input-group-addon">代号</span>
			 <p class="form-control form-control-static"><code>${result.code}</code></p>
			 <input type="hidden" class="form-control" id="custom_code"  value="${result.customCode}" name="customCode" >
 			<input type="hidden" class="form-control" id="areaCode"  value="${result.code}" name="code" >
 			<input type="hidden" class="form-control" id="level"  value="${result.level}" name="level" >
 			</div>
		<#else>
			<div class="input-group">
			<span class="input-group-addon">代号</span>
		 	<input type="text" class="form-control" id="areaCode"  value="${result.code!''}" name="code" placeholder="">
		  </div>
		</#if>
	</div>
 </div>
  <div class="form-group">
	<div class="col-xs-12">
		<div class="input-group">
		<span class="input-group-addon">父节点</span>
		 <input type="hidden" class="form-control" id="parentId" value="<#if result.parentId??>${result.parentId!'0'}</#if>" name="parentId" />
         <p class="form-control form-control-static" id="parentName" ><code>${result.parentName!'无'}</code></p>
         <div class="input-group-btn hide">
            <button type="button" class="btn btn-danger"><@spring.message  code="label.default.button.query" /></button>
          </div>
        </div>

	</div>
 </div>
  <div class="form-group" >
	<div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">名称</span>
         	<input type="text" class="form-control" id="areaName" value="<#if result.name??>${result.name}</#if>" name="name" placeholder="名称"/>
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