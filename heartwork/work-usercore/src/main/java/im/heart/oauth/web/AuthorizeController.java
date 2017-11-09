package im.heart.oauth.web;


import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.utils.StringUtilsEx;
import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;
import im.heart.oauth.service.OAuth2Service;
import im.heart.usercore.entity.FrameUser;

/**
 * 
 * @功能说明：授权控制器
 * @作者 LKG
 */
@Controller
public class AuthorizeController extends AbstractController {
	protected static final Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
	@Autowired
	private OAuth2Service oAuth2Service;
	protected static final String apiVer = "/oauth2";
	protected static final String loginOauthFrom="login-oauth";
	protected static final String OAUTH2_ERROR_PAGE = "common/oauth-error";
	/**
	 * 
	 * @功能说明：获取用户授权
	 * @param jsoncallback
	 * @param clientId 申请应用时分配的AppKey。
	 * @param redirectUri 授权回调地址，站外应用需与设置的回调地址一致，站内应用需填写canvas page的地址。
	 * @param scope 申请scope权限所需参数，可一次申请多个scope权限，用逗号分隔。使用文档
	 * @param state 用于保持请求和回调的状态，在回调时，会在Query Parameter中回传该参数。开发者可以用这个参数验证请求有效性，也可以记录用户请求授权页前的位置。这个参数可用于防止跨站请求伪造（CSRF）攻击
	 * @param display 授权页面的终端类型，取值见下面的说明。 
	 * @param forcelogin 是否强制用户重新登录，true：是，false：否。默认false。
	 * @param responseType 末前只支持code
	 * @param language 授权页语言，缺省为中文简体版，en为英文版。
	 * @param request
	 * @param model
	 * @return
	 * @throws OAuthSystemException
	 * @throws OAuthProblemException
	 */
	@RequestMapping(value = apiVer+"/authorize", method = RequestMethod.GET)
	protected ModelAndView authorize(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "client_id", required = false) String clientId,
			@RequestParam(value = "redirect_uri", required = false) String redirectUri,
			@RequestParam(value = "scope", required = false) String scope,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "display", required = false) String display,
			@RequestParam(value = "forcelogin", required = false) String forcelogin,
			@RequestParam(value = "response_type", required = false, defaultValue = "code") String responseType,
			@RequestParam(value = "language", required = false,defaultValue="zh_CN") String language,
			HttpServletRequest request, ModelMap model)
			throws OAuthSystemException, OAuthProblemException {

		//检测重定向地址
		if(StringUtilsEx.isBlank(clientId)){
			ResponseError responseError = new ResponseError(WebError.invalid_client);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE, model);
		}
		if(StringUtilsEx.isBlank(redirectUri)){
			ResponseError responseError = new ResponseError(WebError.redirect_uri_mismatch);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE, model);
		}
		// 构建OAuth 授权请求
//		OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
		if (!this.oAuth2Service.checkClientIdAndUri(clientId,redirectUri)) {
			ResponseError responseError = new ResponseError(WebError.invalid_client);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE, model);
		}

		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) { // 用户未登录
			logger.warn("用户未登录");
			ResponseError responseError = new ResponseError(WebError.unauthorized_client);
			super.fail(model, responseError);
			model.put("clientId", clientId);
			return new ModelAndView(loginOauthFrom, model);
		}
		FrameUser user=(FrameUser) subject.getPrincipal();
		OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		String authorizationCode  = oauthIssuerImpl.authorizationCode();
		this.oAuth2Service.addAuthCode(authorizationCode, user.getUserId()+"");
		model.put("code", authorizationCode);
		model.put("state", state);
		if(StringUtilsEx.indexOf(redirectUri, "?")>=0){
			redirectUri=redirectUri+"&code="+authorizationCode;
		}else{
			redirectUri=redirectUri+"?code="+authorizationCode;
		}
		return new ModelAndView(redirectToUrl(redirectUri));
	}
}
