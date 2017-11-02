<form class="form-horizontal" action="" id="J_MainForm"  method="post"  >
  <div class="form-group" >
	<div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">报表名称</span>
         	<input type="text" class="form-control" id="rptName"  value="${result.rptName!''}" name="rptName" placeholder="名称"/>
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
	<label for="remark" class="col-xs-2 control-label" >SQl/JAVA</label>
	<div class="col-xs-10">
	<textarea class="form-control" id="rptCriCont" name="rptCriCont" placeholder="SQl/JAVA" rows="10"><#if result.rptCriCont??>${result.rptCriCont}</#if></textarea>
	</div>
  </div>
  <div class="form-group">
	<label for="remark" class="col-xs-2 control-label" >描述</label>
	<div class="col-xs-10">
	<textarea class="form-control" id="remark" name="remark" placeholder="描述" rows="2"><#if result.remark??>${result.remark}</#if></textarea>
	</div>
  </div>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>