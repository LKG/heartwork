package im.heart.security.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import im.heart.core.utils.BaseUtils;
import im.heart.security.AccountToken;
import im.heart.security.WebToken;
import im.heart.security.utils.ShiroLoginHelper;
/**
 * 
 * @author gg
 * @desc 自定义用户验证过滤链
 */
@Component
public class FrameAuthenticationFilter extends FormAuthenticationFilter {
	protected static final Logger logger = LoggerFactory.getLogger(FrameAuthenticationFilter.class);

	private String loginUrl = "/login.jhtml";
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_USERNAME_PARAM = "userName";
    public static final String DEFAULT_PASSWORD_PARAM = "passWord";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String usernameParam = DEFAULT_USERNAME_PARAM;
	private String passwordParam = DEFAULT_PASSWORD_PARAM;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getCaptchaParam(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	@Override
	protected String getUsername(ServletRequest request) {
	  return WebUtils.getCleanParam(request, getUsernameParam());
	}
	@Override
	protected String getPassword(ServletRequest request) {
	  return WebUtils.getCleanParam(request, getPasswordParam());
	}
	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	public String getUsernameParam() {
		return usernameParam;
	}

	public void setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
	}

	public String getPasswordParam() {
		return passwordParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}

	@Override
	protected AccountToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = this.getUsername(request);
		String password = this.getPassword(request);
		boolean rememberMe = this.isRememberMe(request);
		String host = BaseUtils.getIpAddr(WebUtils.toHttp(request));
		String romoteHost = request.getRemoteHost();
		logger.info("{}:createToken .......{},romoteHost:{}", username, host,
				romoteHost);
		String captcha = this.getCaptchaParam(request);
		return new AccountToken(username, password, rememberMe, host, captcha);
	}

	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		logger.info("executeLogin .......");
		if (token == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
					+ "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		logger.debug(token + "onLoginSuccess ......subject:" + subject);
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		ShiroLoginHelper.setLoginSuccessSession();//清空登录次数限制
		//记录登录成功日志
		//SecurityUtilsHelperEx.loginlog(httpServletRequest);
		String loginSuccUrl = httpServletRequest.getContextPath()+ this.getSuccessUrl();
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		if (savedRequest != null) {
			String savedRequestUrl = savedRequest.getRequestUrl();
			if (!savedRequestUrl.equals(getLoginUrl())) {
				loginSuccUrl = savedRequestUrl;
			}
		}
		if (BaseUtils.isAjaxRequest(httpServletRequest)) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter out = httpServletResponse.getWriter();
			Session session = subject.getSession();
			WebToken webToken = new WebToken((String) session.getId(),
					session.getTimeout());
			JSONObject jsobj = JSON.parseObject(JSON.toJSONString(webToken));
			jsobj.put("loginSuccUrl", loginSuccUrl);
			jsobj.put("success", true);
			jsobj.put("httpstatus", HttpStatus.OK.toString());
			out.println(jsobj);
			out.flush();
			out.close();
			return false;
		}
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		logger.debug(token + "onLoginFailure ......subject:" + e);
		ShiroLoginHelper.setLoginFailureSession();
		return super.onLoginFailure(token, e, request, response);
	}
	@Override
	protected boolean isLoginRequest(ServletRequest request,
			ServletResponse response) {
		String requestURI = getPathWithinApplication(request);
		requestURI=StringUtils.substringBefore(requestURI, ".");
		String loginUrlstr = getLoginUrl();
		loginUrlstr=StringUtils.substringBefore(loginUrlstr, ".");
		return super.pathsMatch(loginUrlstr, requestURI);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		logger.info("onAccessDenied ......subject:");
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (logger.isTraceEnabled()) {
					logger.trace("Login submission detected.  Attempting to execute login.");
				}
				return executeLogin(request, response);
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("Login page view.");
				}
				HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
				HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
				if (BaseUtils.isAjaxRequest(httpServletRequest)) {// ajax 请求
					logger.info("ajax@@@@@@@@@@@@@@@@@@@@@@@@"+ httpServletResponse);
					// httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
				// allow them to see the login page ;)
				return true;
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Attempting to access a path which requires authentication.  Forwarding to the "
						+ "Authentication url [" + getLoginUrl() + "]");
			}

			saveRequestAndRedirectToLogin(request, response);
			return false;
		}
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		if (isLoginRequest(request, response)) {
			logger.info("@@@@@@@@@@@@@@@@@isLoginRequest@@@@@@@@@@@@@@@@@@@@@@@@");
		}

		if (SecurityUtils.getSubject().isAuthenticated()) {
			return true;// 已经登录过 继续过滤器链
		}
		return super.preHandle(request, response);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		try {
			logger.info("isAccessAllowed .....");
			// 先判断是否是登录操作
			// 解决不能重复登录问题
			if (isLoginSubmission(request, response)) {
				logger.info("isAccessAllowed ..isLoginSubmission...");
				if (logger.isTraceEnabled()) {
					logger.trace("Login submission detected.  Attempting to execute login.");
				}
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}

}
