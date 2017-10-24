package im.heart.common.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.plugins.captcha.ImageCaptchaExService;
import im.heart.common.utils.CacheUtils;
import im.heart.core.service.ServiceException;
import im.heart.core.plugins.email.SendEmailService;
import im.heart.core.plugins.sms.SmsSendService;
import im.heart.core.utils.BaseUtils;
import im.heart.core.utils.StringUtilsEx;
import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;
import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.service.FrameUserService;
import im.heart.usercore.vo.FrameUserVO;

/**
 * 
 * @author gg
 * @desc 用户找回密码控制器
 */
@Controller
public class ForgetPasswordController extends AbstractController {
	protected static final String apiVer = "/findPwd";
	@Autowired
	private FrameUserService frameUserService;	
	
	@Autowired
	private SmsSendService smsSendService;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private ImageCaptchaExService imageCaptchaService;
	/**
	 * 
	 * @Desc：忘记密码
	 * @param request
	 * @param response
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value=apiVer+"/index",method = RequestMethod.GET)
	public ModelAndView findPwdIndex(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model) {
		if(StringUtilsEx.isNotBlank(key)){
			Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
			if(obj!=null){
				
			}
		}
		super.success(model);
		return new ModelAndView("findpwd/index");
	}
	/**
	 * 
	 * @Desc：忘记密码
	 * @param request
	 * @param response
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value=apiVer+"/",method = RequestMethod.GET)
	public ModelAndView findPwd2(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		super.success(model);
		return new ModelAndView("findpwd/index");
	}
	@RequestMapping(value=apiVer+"/findpwd",method = RequestMethod.GET)
	public ModelAndView findPwd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model) {
		if(StringUtilsEx.isNotBlank(key)){
			Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
			if(obj!=null&&obj instanceof FrameUser){
				FrameUser user =(FrameUser)obj;
				FrameUserVO userVo=new FrameUserVO(user);
				super.success(model, userVo); 
				model.put("k", key);
				return new ModelAndView("findpwd/findPwd");
			}
		}
		ResponseError responseError=new ResponseError(WebError.invalid_request);
		this.fail(model,responseError);
		return new ModelAndView(RESULT_PAGE);
	}
	
	@RequestMapping(value=apiVer+"/subGeneral")
	public ModelAndView findPwdsubGeneral(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "account", required = false ) String account,
			@RequestParam(value = "k", required = false) String key,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "validateCode", required = false ) String validateCode,
			ModelMap model) {
		if(StringUtilsEx.isNotBlank(account)&&StringUtilsEx.isNotBlank(validateCode)){
			String sessionId = request.getRequestedSessionId();
			if(!this.imageCaptchaService.validateResponseForID(sessionId, validateCode).booleanValue()){		
				ResponseError responseError=new ResponseError(WebError.auth_captcha_incorrect);
				this.fail(model,responseError);
				return new ModelAndView("findpwd/index");
			}
			FrameUser user=this.frameUserService.findFrameUser(account);
			if(user!=null){
				super.success(model,user);
				String uuid=StringUtilsEx.getUUID2();
				CacheUtils.generatCache(CacheUtils.FINDPWD_CACHE_NAME,uuid, user);
				if(StringUtilsEx.isBlank(format)){
					format="jhtml";
				}
				return new ModelAndView("redirect:"+apiVer+"/findpwd."+format+"?k="+uuid);
			}
		}
		super.fail(model);
		return new ModelAndView("findpwd/index");
	}
	
	
	/**
	 * @Desc：用户邮箱激活
	 * @param request
	 * @param response
	 * @param email
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = apiVer + "/passEmailcode")
	public ModelAndView passEmailcode(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "emailcode", required = false) String emailcode,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "userEmail", required = false) String userEmail,
			ModelMap model) {
		if (StringUtilsEx.isNotBlank(emailcode)&& StringUtilsEx.isNotBlank(userEmail)) {
			// 检测邮件验证码使用有效
			if(CacheUtils.checkEmailCode(userEmail,emailcode)){
				try {
					FrameUser user = this.frameUserService.activateUserEmail(userEmail);
					if (user!=null) {
						this.success(model, true);
						CacheUtils.evictEmailCache(userEmail);
						super.success(model,user);
						if(StringUtilsEx.isBlank(format)){
							format="jhtml";
						}
						return new ModelAndView("redirect:" + apiVer + "/success."+format);
					}
				} catch (ServiceException e) {
					logger.error(e.getStackTrace()[0].getMethodName(), e);
				}
			}
		}

		this.fail(model, false);
		return new ModelAndView(RESULT_PAGE);
	}
	/**
	 * 
	 * @Desc：忘记密码
	 * @param request
	 * @param response
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value=apiVer,method = RequestMethod.GET)
	public ModelAndView findPwdDefault(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model) {
		super.success(model);
		model.put("k", key);
		return new ModelAndView("findpwd/index");
	}

	
	/**
	 * @Desc：检测帐号信息是否存在、（邮箱，电话号码。帐号 ）
	 * @param jsoncallback
	 * @param account
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = apiVer + "/checkUserAccount", method = RequestMethod.GET)
	protected ModelAndView checkUserPhone(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "account", required = false) String account,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if(StringUtilsEx.isNotBlank(account)&&account.length()  >=5&& account.length() <= 30){
			FrameUser user = this.frameUserService.findFrameUser(account);
			if (user != null) {// 用户不存在
				super.success(model, true);
				return new ModelAndView(RESULT_PAGE);
			}
		}
		super.fail(model, false);
		return new ModelAndView(RESULT_PAGE);
	}
	/**
	 * 
	 * @Desc：邮件发送成功
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @param format
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value = apiVer + "/sendEmailSuccess")
	protected ModelAndView sendFindPwdEmailSuccess(@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model){
		ResponseError responseError=new ResponseError(WebError.request_parameter_missing);
		if(StringUtilsEx.isBlank(key)){
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
		if(obj!=null&&obj instanceof FrameUser){
			FrameUser user =(FrameUser)obj;
			String userEmail=user.getUserEmail();
			if(StringUtilsEx.isNotBlank(userEmail)){
				String emailServer = StringUtilsEx.substringAfterLast(userEmail, "@");
				super.success(model);
				model.put("emailServer", emailServer);
				return new ModelAndView("findpwd/sendEmailSuccess");
			}
		}
		super.success(model, true);
		return new ModelAndView("findpwd/sendEmailSuccess");
	}
	/**
	 * 
	 * @Desc：通过手机号码校验成功
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @param format
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value = apiVer + "/checkPhoneSuccess")
	protected ModelAndView checkPhoneSuccess(@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model){
		ResponseError responseError=new ResponseError(WebError.request_parameter_missing);
		if(StringUtilsEx.isBlank(key)){
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		model.put("k", key);
		Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
		if(obj!=null&&obj instanceof FrameUser){
			super.success(model, true);
			return new ModelAndView("findpwd/resetPwd");
		}
		this.fail(model,responseError);
		return new ModelAndView("findpwd/resetPwd");
	}
	/**
	 * 
	 * @Desc：修改密码
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @param format
	 * @param key
	 * @param passWord
	 * @param retryPassWord
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = apiVer + "/resetPwd")
	protected ModelAndView resetPwd(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "k", required = false) String key,
			@RequestParam(value = "passWord", required = false) String passWord,
			@RequestParam(value = "retryPassWord", required = false) String retryPassWord,
			ModelMap model) throws Exception {
		ResponseError responseError=new ResponseError(WebError.request_parameter_missing);
		if(StringUtilsEx.isBlank(key)){
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
	
		Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
		if(obj!=null&&obj instanceof FrameUser){
			FrameUser user =(FrameUser)obj;
			this.frameUserService.resetPassword(user.getUserId(), retryPassWord);//重置密码
			CacheUtils.evictCache(CacheUtils.FINDPWD_CACHE_NAME, key);
			if(StringUtilsEx.isBlank(format)){
				format="jhtml";
			}
			return new ModelAndView("redirect:"+apiVer+"/resetPwdSuccess."+format+"?k="+key);
		}
		super.fail(model,responseError);
		return new ModelAndView("findpwd/resetPwd");
	}
	@RequestMapping(value = apiVer + "/resetPwdSuccess")
	protected ModelAndView resetPwdSuccess(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model) throws Exception {
		super.success(model);
		return new ModelAndView("findpwd/resetPwdSuccess");
	}
	
	@RequestMapping(value = apiVer + "/sendFindPwd")
	protected ModelAndView sendFindPwd(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "k", required = false) String key,
			@RequestParam(value = "phoneCode", required = false) String phoneCode,
			@RequestParam(value = "type", required = false) String type,
			ModelMap model) throws Exception {
		ResponseError responseError=new ResponseError(WebError.request_parameter_missing);
		if(StringUtilsEx.isBlank(key)){
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
		if(obj!=null&&obj instanceof FrameUser){
			FrameUser user =(FrameUser)obj;
			if(StringUtilsEx.isBlank(format)){
				format="jhtml";
			}
			if(StringUtilsEx.isNotBlank(type)&&"2".equals(type)){//通过邮箱形式找回密码
				this.sendFindPwdEmail(user);
				return new ModelAndView("redirect:"+apiVer+"/sendEmailSuccess."+format+"?k="+key);
			}
			String moblie=user.getUserPhone();
			Boolean isResponseCorrect = Boolean.FALSE;
			isResponseCorrect=CacheUtils.checkMobileCode(moblie, phoneCode);
			if(isResponseCorrect){//校验通过
				return new ModelAndView("redirect:"+apiVer+"/checkPhoneSuccess."+format+"?k="+key);
			}
			responseError=new ResponseError(WebError.auth_phonecode_incorrect);	
		}
		this.fail(model,responseError);
		return new ModelAndView(RESULT_PAGE);
	}
	
	/**
	 * 
	 * @Desc：发送邮件
	 * @param user
	 */
	private void sendFindPwdEmail(FrameUser user){
		int emailCode = (int) ((Math.random() * 9 + 1) * 100000);
		Map<String, Object> modeltemp = new HashMap<String, Object>();// 定义邮件模版
		String userEmail=user.getUserEmail();
		CacheUtils.generatEmailCodeCache(userEmail,emailCode);
		modeltemp.put("emailCode", emailCode);
		modeltemp.put(RequestResult.RESULT, user);
		this.sendEmailService.sendEmail(modeltemp, "忘记密码提示",
				"findPwd.ftl",
				new String[] { userEmail },
				new String[] {});
	}
	
	/**
	 * 
	 * @Desc：验证码校验接口
	 * @param request
	 * @param response
	 * @param captchaValue
	 * @param model
	 * @return
	 */
	@RequestMapping(value = apiVer + "/checkMobliecode")
	public ModelAndView ValidateMobliecode(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "k", required = false ) String key,
			@RequestParam(value = "phoneCode", required = false) String phoneCode,
			ModelMap model){
		ResponseError responseError=new ResponseError(WebError.request_parameter_missing);
		if(StringUtilsEx.isBlank(key)){
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		logger.debug("passcode-host:"+request.getLocalAddr());
		Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
		if(obj!=null&&obj instanceof FrameUser){
			FrameUser user =(FrameUser)obj;
			String moblie=user.getUserPhone();
			Boolean isResponseCorrect = Boolean.FALSE;
			isResponseCorrect=CacheUtils.checkMobileCode(moblie, phoneCode);
			if(isResponseCorrect){//校验通过
				super.success(model,true);
				return new ModelAndView(RESULT_PAGE);
			}
		}
		this.fail(model,false);
		return new ModelAndView(RESULT_PAGE);
	}
	
	
	/**
	 * @Desc： 发送短信验证码 
	 * @param request
	 * @param response
	 * @param model 
	 */
	@RequestMapping(value = apiVer + "/passmobliecode")
	public ModelAndView sendFindPwdPhone(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "k", required = false ) String key,
			@RequestParam(value = "type", required = false ,defaultValue="1") String type,ModelMap model) {
		ResponseError responseError=new ResponseError(WebError.request_parameter_missing);
		if(StringUtilsEx.isBlank(key)){
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		Object obj= CacheUtils.getCacheObject(CacheUtils.FINDPWD_CACHE_NAME, key);
		if(obj!=null&&obj instanceof FrameUser){
			FrameUser user =(FrameUser)obj;
			int mobileCode = (int)((Math.random()*9+1)*10000);
			Map<String,Object> modeltemp = new HashMap<String,Object>();//定义邮件模版
			modeltemp.put("mobileCode", mobileCode);
			String moblie=user.getUserPhone();
			logger.info("mobliecode-host:[{}],moblie:[{}] mobileCode:[{}] type:[{}]",BaseUtils.getIpAddr(request),moblie,mobileCode,type);
			CacheUtils.generateMobileCache(moblie, mobileCode);
			responseError=this.smsSendService.sendSms(modeltemp, "findPwd.ftl", new String[]{moblie});
			if(responseError==null){
				this.success(model,true);
			}else{
				this.fail(model,responseError);
			}
		}
		return new ModelAndView(RESULT_PAGE);
	}
}
