package im.heart.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.heart.common.context.ContextManager;
import im.heart.core.log.OptLog;
import im.heart.core.utils.BaseUtils;
import im.heart.core.web.utils.WebUtilsEx;
import im.heart.log.entity.FrameLogOperate;
import im.heart.log.service.FrameLogOperateService;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.vo.FrameUserVO;

public class OptLogUtils{
	protected static final Logger logger = LoggerFactory.getLogger(OptLogUtils.class);
	public static  void optLog(HttpServletRequest request,OptLog optLog ){
		FrameLogOperateService logLoginService=(FrameLogOperateService)ContextManager.getBean(FrameLogOperateService.BEAN_NAME);
		String headers = WebUtilsEx.getHeadersJson(request);
		String accept = request.getHeader("accept");
		String userAgent = request.getHeader("User-Agent");
		String url = request.getRequestURI();
		logger.debug("headers:[{}]accept:[{}]userAgent:[{}]url:[{}]",headers,accept,userAgent,url);
		String requestParams = WebUtilsEx.getParametersJson(request);
		FrameLogOperate entity=new FrameLogOperate();
		entity.setSystemHost(BaseUtils.getServerIp()+ ":" + request.getLocalPort());
		String userHost=BaseUtils.getIpAddr(request);
		entity.setParams(requestParams);
		entity.setType(optLog.type());
		entity.setContent(optLog.detail());
		FrameUserVO user = SecurityUtilsHelper.getCurrentUser();
		entity.setUserName(user.getUserName());
		entity.setUserId(user.getUserId());
		entity.setUserHost(userHost);
		entity.setUserAgent(userAgent);
		logLoginService.optlog(entity);
	}
}

