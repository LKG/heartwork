<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<html lang="zh">

 <body >
 <#include "/admin/frame/dict_form.ftl" />
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />

<!------seajs.config   引用信息 end----->
<script>
   seajs.use("/js/admin/frame/dict_details.js?v="+Math.random());
</script>
 </body>
</html>