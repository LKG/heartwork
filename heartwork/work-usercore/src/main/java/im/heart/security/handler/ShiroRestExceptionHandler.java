package im.heart.security.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import im.heart.core.CommonConst.RequestResult;
import im.heart.core.utils.BaseUtils;

@ControllerAdvice
public class ShiroRestExceptionHandler extends ResponseEntityExceptionHandler {

	protected Map<String, Object> error(HttpServletRequest request) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("httpstatus", HttpStatus.FORBIDDEN.value());
		errorMap.put("success", false);
		errorMap.put("request", request.getRequestURL());
		return errorMap;
	}
	protected Map<String, Object> error(HttpServletRequest request, Exception ex) {
		Map<String, Object> errorMap = this.error(request);
		errorMap.put("exception", ex.getMessage());
		return errorMap;
	}
	protected ModelAndView chooseView(HttpServletRequest request,Map<String, Object> errorMap){
		if(BaseUtils.isAjaxRequest(request)){
			return new ModelAndView(RequestResult.PAGE_ERROR_UNAUTHORIZED_IN, errorMap);
		}
		return new ModelAndView(RequestResult.PAGE_ERROR_UNAUTHORIZED, errorMap);
	}
	@ExceptionHandler(value = AuthorizationException.class)
    public ModelAndView defaultAuthorizationExceptionHandler(HttpServletRequest request, AuthorizationException ex) throws Exception {
		logger.debug(ex.getStackTrace()[0].getMethodName(), ex);
		return this.chooseView(request,this.error(request,ex));
    }
	@ExceptionHandler(UnauthorizedException.class)
	public ModelAndView handleUnauthorizedException(HttpServletRequest request, UnauthorizedException ex) {
		logger.info(ex.getStackTrace()[0].getMethodName(), ex);
		return this.chooseView(request,this.error(request,ex));
	}
		
}
