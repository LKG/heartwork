package im.heart.common.web;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.CommonConst;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.utils.BaseUtils;
import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;
import im.heart.security.exception.IncorrectCaptchaException;
import im.heart.security.utils.ShiroLoginHelper;

/**
 * 
 * @author lkg
 * @Desc : 登录控制
 */
@Controller
public class LoginController extends AbstractController {
	protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	protected static final String loginFrom="login";
	
	protected static final String loginDialogFrom="pages/login-in";
	protected static final String appLoginFrom="app/login-in";
	@Value("${shiro.login.success.url}")
	private String successUrl = "/";
	
	private ModelAndView chooseLoginView(HttpServletRequest request,ModelMap model){
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("Android") != -1){
		    //安卓
			return new ModelAndView(appLoginFrom, model);
		}else if(userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1){
		   //苹果
			return new ModelAndView(appLoginFrom, model);
		}
		if(BaseUtils.isAjaxRequest(request)){
			return new ModelAndView(loginDialogFrom, model);
		}
		return new ModelAndView(loginFrom, model);
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	protected ModelAndView showLoginForm(HttpServletRequest request,
			@RequestParam(value = "logout" ,defaultValue="") Integer logout,
			ModelMap model) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return new ModelAndView(super.redirectToUrl(successUrl));
	    }
		model.put("userName", "");
		model.put("_error", logout);
		return chooseLoginView(request,model);
	}
	@RequestMapping(value = "/unauthorized",method = RequestMethod.GET)
	protected ModelAndView unauthorized(HttpServletRequest request,
			ModelMap model) throws Exception {
		super.fail(model);
		return new ModelAndView(RequestResult.PAGE_ERROR_UNAUTHORIZED, model);
	}
	
	
	@RequestMapping(value = "/login-in",method = RequestMethod.GET)
	protected ModelAndView showLoginInForm(HttpServletRequest request,
			@RequestParam(value = "logout" ,defaultValue="") Integer logout,
			ModelMap model){
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return new ModelAndView(super.redirectToUrl(successUrl));
	    }
		model.put("userName", "");
		model.put("_error", logout);
		return new ModelAndView(loginDialogFrom, model);
	}
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public ModelAndView LoginFail(@RequestParam("userName") String username,
			HttpServletRequest request, ModelMap model) {
		logger.debug("用户需要登录跳转到登录页面############LoginFail########post#########");
		WebError webError = this.parseException(request,username);
		Integer times = ShiroLoginHelper.getLoginTimes();
		if(times>=2){
			model.put(CommonConst.SESSION_LOGIN_TIMES, times);
		}
		if(webError!=null){
			ResponseError responseError=new ResponseError(webError);
			super.fail(model,responseError);
		}
		return chooseLoginView(request,model);
	}
	/**
	 * 	UnknownAccountException.class,
	 *	IncorrectCredentialsException.class ,
	 *	DisabledAccountException.class,
	 *	LockedAccountException.class ,
	 *	ExcessiveAttemptsException.class,
	 *	ExpiredCredentialsException.class,
	 *	IncorrectCaptchaException.class,
	 *	AuthenticationException.class
	 *  UnauthorizedException.class
	 * @Desc：
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	private WebError parseException(ServletRequest request,String userName) throws AuthenticationException {
		String errorString = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (errorString == null || StringUtils.isEmpty(errorString)) {
			return null;
		}
		String host = BaseUtils.getIpAddr((HttpServletRequest)request);
		WebError webError=null;
		if (errorString.equals(UnknownAccountException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】账号不存在", host,userName);
			webError=WebError.auth_account_unknown;
		} else if (errorString.equals(IncorrectCredentialsException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】账号密码错误", host,userName);
			webError=WebError.auth_credentials_incorrect;
		} else if (errorString.equals(DisabledAccountException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】账号被禁用.", host,userName);
			webError=WebError.auth_account_disabled;
		} else if (errorString.equals(LockedAccountException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】的账户锁定或未激活，请联系管理员。", host,userName);
			webError=WebError.auth_account_locked;
		} else if (errorString.equals(ExcessiveAttemptsException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】的用户登录次数过多，有暴力破解的嫌疑.", host,userName);
			webError=WebError.auth_excessive_attempts;
		} else if (errorString.equals(ExpiredCredentialsException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】用户凭证过期.", host,userName);
			webError=WebError.auth_credentials_expired;
		} else if (errorString.equals(IncorrectCaptchaException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】用户验证码错误.", host,userName);
			webError=WebError.auth_captcha_incorrect;
		}else if (errorString.equals(UnauthorizedException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】用户权限不足。", host,userName);
			webError=WebError.access_denied;
		}else if (errorString.equals(AuthenticationException.class.getName())) {
			logger.warn("【{}】:用户名:【{}】用户验证失败，请联系管理员。", host,userName);
			webError=WebError.auth_exception;
		}else{
			webError=WebError.auth_exception;
		}
		return webError;
	}

}
