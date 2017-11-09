package im.heart.oauth.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;
import im.heart.oauth.service.OAuth2Service;


@Controller
public class UserInfoController  extends AbstractController {
	protected static final String apiVer = "/oauth2";
	protected static final String OAUTH2_ERROR_PAGE = "common/oauth-error";
	@Autowired
	private OAuth2Service oAuth2Service;
	@RequestMapping(value = apiVer+"/userinfo", method = RequestMethod.GET)
	protected ModelAndView userinfo(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, ModelMap model) throws OAuthSystemException, OAuthProblemException {
	      //构建OAuth资源请求  
	      OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);  
	      //获取Access Token  
	      String accessToken = oauthRequest.getAccessToken();  
	      //验证Access Token  
	      if (!oAuth2Service.checkAccessToken(accessToken)) {  
				ResponseError responseError = new ResponseError(WebError.redirect_uri_mismatch);
				super.fail(model, responseError);
				return new ModelAndView(OAUTH2_ERROR_PAGE, model);
	      }  
	      //返回用户名  
	      String username = this.oAuth2Service.getUsernameByAccessToken(accessToken);  
	  	super.success(model,"uid", username);
		return new ModelAndView(OAUTH2_ERROR_PAGE, model);
	}
}
