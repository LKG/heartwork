package im.heart.oauth.support.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.collect.Maps;

import im.heart.core.CommonConst.RequestResult;
import im.heart.core.utils.BaseUtils;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;

@ControllerAdvice
public class Oauth2RestExceptionHandler extends ResponseEntityExceptionHandler {

	protected ModelAndView chooseView(HttpServletRequest request,Map<String, Object> errorMap){
		if(BaseUtils.isAjaxRequest(request)){
			return new ModelAndView(RequestResult.PAGE_ERROR_UNAUTHORIZED_IN, errorMap);
		}
		return new ModelAndView(RequestResult.PAGE_ERROR_UNAUTHORIZED, errorMap);
	}	
	
	@ExceptionHandler(OAuthSystemException.class)
	public ModelAndView handleOAuthSystemException(HttpServletRequest request, OAuthSystemException ex) {
		Map<String, Object> errorMap = Maps.newConcurrentMap();
		ResponseError responseError = new ResponseError(WebError.temporarily_unavailable);
		errorMap.put(RequestResult.SUCCESS, false);
		errorMap.put(RequestResult.HTTP_STATUS, HttpStatus.OK.toString());
		errorMap.put(RequestResult.RESULT, responseError);
		return this.chooseView(request,errorMap);
	}
	@ExceptionHandler(OAuthProblemException.class)
	public ModelAndView handleOAuthProblemException(HttpServletRequest request, OAuthProblemException ex) {
		logger.debug(ex.getStackTrace()[0].getMethodName(), ex);
		Map<String, Object> errorMap = Maps.newConcurrentMap();
		ResponseError responseError = new ResponseError(WebError.invalid_client);
		errorMap.put(RequestResult.SUCCESS, false);
		errorMap.put(RequestResult.HTTP_STATUS, HttpStatus.OK.toString());
		errorMap.put(RequestResult.RESULT, responseError);
		return this.chooseView(request,errorMap);
	}
}
