package im.heart.core.support.handler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import im.heart.core.CommonConst.RequestResult;
import im.heart.core.utils.BaseUtils;
import im.heart.core.validator.BeanValidators;

/**
 * 
 * @author: gg
 * @desc : 统一异常处理
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	protected static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	protected Map<String, Object> error(HttpServletRequest request) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("httpstatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
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
			return new ModelAndView(RequestResult.PAGE_ERROR, errorMap);
		};
		return new ModelAndView(RequestResult.PAGE_ERROR, errorMap);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ModelAndView handleException(HttpServletRequest request, DataIntegrityViolationException e) {
		logger.error("操作数据库出现异常：字段重复、有外键关联等:", e);
		return this.chooseView(request,this.error(request, e));
	}
	
	@ExceptionHandler(ServletException.class)
	public ModelAndView handleServletException(HttpServletRequest request, ServletException ex) {
		logger.error(ex.getStackTrace()[0].getMethodName(), ex);
		return this.chooseView(request,this.error(request, ex));
	}
	/**
	 * @desc : 处理SQL 异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(SQLException.class)
	public ModelAndView handleSQLException(HttpServletRequest request, SQLException ex) {
		logger.error("handleSQLException:"+ex.getStackTrace()[0].getMethodName(), ex);
		return this.chooseView(request,this.error(request, ex));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
		logger.error("IllegalArgumentException:"+ex.getStackTrace()[0].getMethodName(), ex);
		return this.chooseView(request,this.error(request, ex));
	}
	/**
	 * @desc : ConstraintViolationException 校验 异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ModelAndView handleConstraintViolationException(HttpServletRequest request,
			ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		Map<String, String> messages = BeanValidators.extractPropertyAndMessage(constraintViolations);
		Map<String, Object> errorMap = this.error(request);
		errorMap.put("message", messages);
		logger.error("handleConstraintViolationException:"+ex.getStackTrace()[0].getMethodName(), ex);
		return this.chooseView(request,errorMap);
	}
	
//	@ExceptionHandler(StackOverflowError.class)
//	public ModelAndView handleStackOverflowError(HttpServletRequest request, StackOverflowError ex) {
//		logger.error(ex.getStackTrace()[0].getMethodName(), ex);
//		return this.chooseView(request,this.error(request));
//	}
//	@ResponseStatus(HttpStatus.OK)
//	@ExceptionHandler(Exception.class)
//	public ModelAndView handleException(HttpServletRequest request, Exception ex) {
//		logger.error("handleException:"+ex.getStackTrace()[0].getMethodName(), ex);
//		return this.chooseView(request,this.error(request));
//	}
//	
//	/**
//	 * @desc : 处理RuntimeException 异常
//	 * @param request
//	 * @param ex
//	 * @return
//	 */
//	@ExceptionHandler(RuntimeException.class)
//	public ModelAndView handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
//		logger.error("handleRuntimeException:"+ex.getStackTrace()[0].getMethodName(), ex);
//		return this.chooseView(request,this.error(request));
//	}
}
