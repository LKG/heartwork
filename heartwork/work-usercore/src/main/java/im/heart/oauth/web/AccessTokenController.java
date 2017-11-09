package im.heart.oauth.web;


import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
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
import im.heart.security.WebToken;
import im.heart.oauth.service.OAuth2Service;

/**
 * 
 * @功能说明：访问令牌控制器
 * @作者 LKG
 */
@Controller
public class AccessTokenController extends AbstractController {
	protected static final Logger logger = LoggerFactory.getLogger(AccessTokenController.class);
	
	protected static final String apiVer = "/oauth2";
	protected static final String OAUTH2_ERROR_PAGE = "common/oauth-error";
	@Autowired
	private OAuth2Service oAuth2Service;

	
	/**
	 * 
	 * @功能说明：获取令牌
	 * @param jsoncallback
	 * @param grantType
	 * @param clientId
	 * @param clientSecret
	 * @param code
	 * @param redirectUri
	 * @param request
	 * @param model
	 * @return
	 * @throws OAuthSystemException
	 * @throws OAuthProblemException
	 */
	@RequestMapping(value = apiVer+"/access_token")
	protected ModelAndView accessToken(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "grant_type", required = false, defaultValue = "authorization_code") String grantType,
			@RequestParam(value = "client_id", required = false) String clientId,
			@RequestParam(value = "client_secret", required = false) String clientSecret,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "redirect_uri", required = false) String redirectUri,
			HttpServletRequest request, ModelMap model)
			throws OAuthSystemException, OAuthProblemException {
		if (StringUtilsEx.isBlank(grantType)||StringUtilsEx.isBlank(clientId)||StringUtilsEx.isBlank(clientSecret)) {
			ResponseError responseError = new ResponseError(WebError.invalid_request);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE, model);
		}
		// 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
		if (!GrantType.AUTHORIZATION_CODE.toString().equals(grantType)) {
			ResponseError responseError = new ResponseError(WebError.unsupported_response_type);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE, model);
		}

		// 检查客户端安全KEY是否正确
		if (!this.oAuth2Service.checkClientIdAndSecret(clientId,clientSecret)) {
			ResponseError responseError = new ResponseError(WebError.invalid_client);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE);
		}
		if (!this.oAuth2Service.checkAuthCode(code)) {
			ResponseError responseError = new ResponseError(WebError.invalid_grant);
			super.fail(model, responseError);
			return new ModelAndView(OAUTH2_ERROR_PAGE);
		}
		// 生成Access Token
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		final String accessToken = oauthIssuerImpl.accessToken();
		this.oAuth2Service.addAccessToken(accessToken,this.oAuth2Service.getUsernameByAuthCode(code));
		// 生成OAuth响应
		WebToken webToken=new WebToken(accessToken,this.oAuth2Service.getExpireIn());
		super.success(model,webToken);
		return new ModelAndView(RESULT_PAGE);
	}
	/**
	 * 
	 * @功能说明：获取token 信息 //TODO 暂不处理
	 * @param jsoncallback
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = apiVer+"/get_token_info", method = RequestMethod.GET)
	protected ModelAndView getTokenInfo(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, ModelMap model) {
		WebToken webToken=new WebToken();
		super.success(model, webToken);
		return new ModelAndView(RESULT_PAGE);
	}
	/**
	 * 
	 * @功能说明：授权回收接口，帮助开发者主动取消用户的授权。//TODO 暂不处理
	 * @param jsoncallback
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = apiVer+"/revokeoauth2", method = RequestMethod.GET)
	protected ModelAndView revokeOauth2(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, ModelMap model) {

		super.success(model, true);
		return new ModelAndView(RESULT_PAGE);
	}

}
