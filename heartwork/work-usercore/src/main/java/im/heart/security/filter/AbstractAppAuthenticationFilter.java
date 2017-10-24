package im.heart.security.filter;

import im.heart.core.CommonConst;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAppAuthenticationFilter extends AuthenticationFilter {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractAppAuthenticationFilter.class);

	public static final String TOKEN = CommonConst.RequestResult.ACCESS_TOKEN;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		logger.debug("客户端校验进入校验！" + getLoginUrl());
		String token = request.getParameter(TOKEN);
		if (isAccess(token)) {
			return onAccessSuccess(WebUtils.toHttp(request), WebUtils.toHttp(response));
		}
		return onAccessFail(WebUtils.toHttp(request), (HttpServletResponse) response);
	}

	public abstract boolean isAccess(String token);

	public abstract boolean onAccessSuccess(HttpServletRequest request, HttpServletResponse response);

	public abstract boolean onAccessFail(HttpServletRequest request, HttpServletResponse response);
}
