package im.heart.common.utils;

import javax.servlet.http.HttpServletRequest;

import im.heart.common.context.ContextManager;
import im.heart.core.utils.BaseUtils;
import im.heart.log.entity.FrameLogLogin;
import im.heart.log.service.FrameLogLoginService;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.vo.FrameUserVO;

public class LogLoginUtils{
	
	public static  void loginlog(HttpServletRequest request){
		FrameLogLoginService logLoginService=(FrameLogLoginService)ContextManager.getBean(FrameLogLoginService.BEAN_NAME);
		FrameLogLogin entity=new FrameLogLogin();
		entity.setSystemHost(BaseUtils.getServerIp() + ":" + request.getLocalPort());
		entity.setUserAgent(request.getHeader("User-Agent"));
		String ip=BaseUtils.getIpAddr(request);
		entity.setUserHost(ip);
		FrameUserVO user = SecurityUtilsHelper.getCurrentUser();
		entity.setUserName(user.getUserName());
		entity.setUserId(user.getUserId());
		logLoginService.loginlog(entity);
	}
	
}

