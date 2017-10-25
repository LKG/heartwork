<#include "/includes/taglib.ftl" />
<!DOCTYPE html>
<html lang="zh">
 <head> 
	<#include "/includes/head.ftl" />
 </head> 
 <body >
<#include "/userinfo/user-receiver_from.ftl" />
<!------seajs.config 引用信息 begin----->
<#include "/includes/seajs.config.ftl" />

<!------seajs.config   引用信息 end----->
<script>
   seajs.use("/js/userinfo/user_receiver_details.js?v="+Math.random());
</script>
 </body>
</html>