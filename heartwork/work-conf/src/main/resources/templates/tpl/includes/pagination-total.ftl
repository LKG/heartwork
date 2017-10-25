<div class="bars pull-right " >
 <p  style="background-color: #fff;display: inline-block;" >
	共 <code id="pagination-total" ><#if result??>${result.totalElements!'0'}</#if></code> 记录
 </p>
 <!----->
 <select class="page-size-sel form-control" ng-model="result.size" style="display: inline;width: 75px;" ng-init="result.size='${result.size}'" name="size">
	<option value="10">10</option>
	<option value="20">20</option>
	<option value="50">50</option>
	<option value="100">100</option>
	<option value="200">200</option>
</select>
 <!----->
</div>
<div class="clearfix"></div>