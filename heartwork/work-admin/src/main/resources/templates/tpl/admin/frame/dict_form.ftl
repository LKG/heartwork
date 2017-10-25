<form class="form-horizontal"  style="padding:5px 15px;"  action="" id="J_MainForm"  method="post"  >
 <div class="form-group">
	<div class="col-xs-12">
		<#if result.id??>
			<div class="input-group">
			 <span class="input-group-addon">代号</span>
			  <input type="hidden" class="form-control" id="dictId"  value="${result.id}" name="id" placeholder="字典代号">
			   <input type="hidden" class="form-control" id="dictType"  value="${result.dictType}" name="dictType" placeholder="字典类型">
			 <input type="hidden" class="form-control" id="dictCode"  value="${result.dictCode}" name="dictCode" placeholder="字典代号">
			 <p class="form-control form-control-static"><code>${result.dictCode}</code></p>
 			</div>
		<#else>
		<div class="input-group">
			<span class="input-group-addon">代号</span>
		 	<input type="text" class="form-control" id="dictCode"  value="${result.dictCode}" name="dictCode" placeholder="字典代号">
		  </div>
		</#if>
	</div>
 </div>
  <div class="form-group" >
	<div class="col-xs-12">
		<div class="input-group">
	  	<span class="input-group-addon">名称</span>
         	<input type="text" class="form-control" id="dictName" value="<#if result.dictName??>${result.dictName}</#if>" name="dictName" placeholder="字典名称"/>
           <#if result.id??>
           	 <p class="form-control form-control-static hide">
           	 	<code>
           	 	  <#if result.dictType=='multiple'>多选参数</#if><#if result.dictType=='single'>单个参数</#if><#if result.dictType=='revisable'>可变参数</#if>
           		</code>
           	 </p>
           <#else>
               <div class="input-group-btn">
           	    <select name="dictType" id="dictType"  data="<#if result.dictType??>${result.dictType}</#if>" style="min-width:140px;"   class="form-control">
				<option value="single">单个</option>
				<option value="multiple">多选</option>
				<option value="revisable">可变</option>
				</select>
			   </div>
           </#if>


        </div>
	</div>
 </div>
<#if result.dictType!='multiple'>
<div class="form-group" id="dictValue-div" >
   <label for="dictValue" class="col-xs-1 control-label" >值</label>
   <div class="col-xs-10">
     <textarea class="form-control" id="dictValue" name="dictValue" placeholder="值" rows="3"><#if result.dictValue??>${result.dictValue}</#if></textarea>
	</div>
 </div>
</#if>
  <div class="form-group">
	<label for="dictDesc" class="col-xs-1 control-label" >描述</label>
	<div class="col-xs-10">
	<textarea class="form-control" id="dictDesc" name="dictDesc" placeholder="描述" rows="3"><#if result.dictDesc??>${result.dictDesc}</#if></textarea>
	</div>
  </div>
  <div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	  <button type="button" id="J_Submit" class="btn btn-primary"><@spring.message  code="label.default.button.save" /></button>
	</div>
  </div>
</form>