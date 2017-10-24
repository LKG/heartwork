package im.heart.security;

import im.heart.core.CommonConst.RequestResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author gg
 * @desc //TODO:令牌拦截 暂不使用 
 */
public class AccessTokenVerifyInterceptor extends HandlerInterceptorAdapter {
	protected static final Logger logger = LoggerFactory.getLogger(AccessTokenVerifyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("AccessToken executing ...");
		boolean flag = false;
		// token
		String accessToken = request.getParameter(RequestResult.ACCESS_TOKEN);
		if (StringUtils.isNotBlank(accessToken)) {

		}

		if (!flag) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().print("AccessToken ERROR");
		}

		return flag;
	}
}
