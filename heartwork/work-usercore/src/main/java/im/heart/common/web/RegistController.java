package im.heart.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import im.heart.common.utils.CacheUtils;
import im.heart.core.CommonConst.RequestResult;
import im.heart.core.enums.Status;
import im.heart.core.plugins.captcha.ImageCaptchaExService;
import im.heart.core.plugins.email.SendEmailService;
import im.heart.core.service.ServiceException;
import im.heart.core.utils.BaseUtils;
import im.heart.core.utils.StringUtilsEx;
import im.heart.core.utils.ValidatorUtils;
import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;
import im.heart.core.web.enums.WebError;
import im.heart.usercore.entity.FrameUser;
import im.heart.usercore.service.FrameUserService;

/**
 * 
 * @author gg
 * @desc 用户注册激活控制器
 */
@Controller
public class RegistController extends AbstractController {
	protected static final Logger logger = LoggerFactory.getLogger(RegistController.class);
	protected static final String registerFrom = "register";
	protected static final String registerDialogFrom = "pages/register-in";
	protected static final String quickRegisterFrom = "pages/register-quick";
	protected static final String registerSuccess = "register-success";
	protected static final String apiVer = "/regist";
	@Autowired
	private FrameUserService frameUserService;

	@Autowired
	private SendEmailService sendEmailService;
	@Autowired
	private ImageCaptchaExService imageCaptchaService;

	@RequestMapping(method = RequestMethod.GET, value = apiVer)
	public ModelAndView register(HttpServletRequest request,ModelMap model) {
		super.success(model);
		if (BaseUtils.isAjaxRequest(request)) {
			return new ModelAndView(registerDialogFrom);
		}
		return new ModelAndView(registerFrom);
	}
	@RequestMapping(method = RequestMethod.GET, value = apiVer+"/quick")
	public ModelAndView quick(HttpServletRequest request,ModelMap model) {
		super.success(model);
		return new ModelAndView(quickRegisterFrom);
	}
	@RequestMapping(value = apiVer + "/checkUserName", method = RequestMethod.GET)
	protected ModelAndView checkUserName(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "userName", required = false) String userName,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (StringUtils.isBlank(userName) || userName.length() < 5
				|| userName.length() > 30) {
			super.fail(model, false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserName(userName);
		if (exist) {// 用户名已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model, true);
		return new ModelAndView(RESULT_PAGE);
	}

	@RequestMapping(value = apiVer + "/checkUserEmail", method = RequestMethod.GET)
	protected ModelAndView checkUserEmail(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "userEmail", required = false) String userEmail,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (!ValidatorUtils.isEmail(userEmail)) {
			super.fail(model, false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserEmail(userEmail);
		if (exist) {// 邮箱已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model, true);
		return new ModelAndView(RESULT_PAGE);
	}

	@RequestMapping(value = apiVer + "/checkUserPhone", method = RequestMethod.GET)
	protected ModelAndView checkUserPhone(
			@RequestParam(value = "jsoncallback", required = false) String jsoncallback,
			@RequestParam(value = "userPhone", required = false) String userPhone,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if (!ValidatorUtils.isPhone(userPhone)) {
			super.fail(model, false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserPhone(userPhone);;
		if (exist) {// 电话号码已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		super.success(model, true);
		return new ModelAndView(RESULT_PAGE);
	}

	@RequestMapping(value = apiVer + "/success", method = RequestMethod.GET)
	protected ModelAndView success(
			@ModelAttribute(RequestResult.RESULT) FrameUser frameUser,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		super.success(model,frameUser);
		return new ModelAndView(registerSuccess);
	}

	
	/**
	 * @Desc：用户快速注册
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = apiVer + "/quickRegister", method = RequestMethod.POST)
	protected ModelAndView quickRegisterAjaxUser(
			@ModelAttribute(RequestResult.RESULT) FrameUser frameUser,
			HttpServletRequest request, ModelMap model,
			@RequestParam(value = "format", required = false) String format,
			RedirectAttributes redirectAttributes) throws ServiceException {
		String userPhone = frameUser.getUserPhone();
		String phoneCode=frameUser.getPhoneCode();
		if (!CacheUtils.checkMobileCode(userPhone,phoneCode)) {
			super.fail(model, false);
			return new ModelAndView(RESULT_PAGE);
		}
		boolean exist = this.frameUserService.existsUserPhone(userPhone);;
		if (exist) {// 电话号码已存在
			super.fail(model,false);
			return new ModelAndView(RESULT_PAGE);
		}
		frameUser.setUserName("q_"+frameUser.getUserPhone());// 快速注册用户
		frameUser.setNickName(frameUser.getUserPhone());
		frameUser.setRemark("quickRegister user");// 快速注册用户
		frameUser.setUserChannel("web");
		frameUser.setStatus(Status.enabled);
		FrameUser newFrameUser = this.frameUserService.save(frameUser);
		redirectAttributes.addFlashAttribute(RequestResult.RESULT, newFrameUser);
		if(StringUtils.isBlank(format)){
			format="jhtml";
		}
		return new ModelAndView(redirectToUrl(apiVer + "/success."+format));
	}

	
	

	/**
	 * 
	 * @Desc：用户注册
	 * @param frameUser
	 * @param result
	 * @param model
	 * @return
	 * @throws ServiceException
	 */

	@RequestMapping(value = apiVer + "/subGeneral", method = RequestMethod.POST)
	public ModelAndView registerAjaxUser(
			@Valid @ModelAttribute(RequestResult.RESULT) FrameUser frameUser,BindingResult result,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "validateCode", required = false ) String validateCode,
			@RequestParam(value = "phoneCode", required = false ) String phoneCode,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			ModelMap model) throws ServiceException {

		if (result.hasErrors()) {
			// List<ObjectError> allerrors = result.getAllErrors();
			// String aa = JSON.toJSONString(allerrors);
			// for( FieldError error:result.getFieldErrors()){

			// };
		}
		String sessionId = request.getRequestedSessionId();
		if(!this.imageCaptchaService.validateResponseForID(sessionId, validateCode).booleanValue()){		
			ResponseError responseError=new ResponseError(WebError.auth_captcha_incorrect);
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		String phone=frameUser.getUserPhone();
		if (!CacheUtils.checkMobileCode(phone,phoneCode)) {
			ResponseError responseError=new ResponseError(WebError.auth_phonecode_incorrect);
			this.fail(model,responseError);
			return new ModelAndView(RESULT_PAGE);
		}
		if(ValidatorUtils.isPhone(phone)){
			CacheUtils.evictMobileCode(phone);
			frameUser.setStatus(Status.enabled);
		}
		frameUser.setUserChannel("web");
		frameUser.setRemark("web user");// 
		FrameUser newFrameUser = this.frameUserService.save(frameUser);
		String userEmail=newFrameUser.getUserEmail();
		if (StringUtilsEx.isNotBlank(userEmail)) {
			Map<String, Object> modeltemp = new HashMap<String, Object>();// 定义邮件模版
			modeltemp.put(RequestResult.RESULT, newFrameUser);
			this.sendEmailService.sendEmail(modeltemp, "用户注册成功提示",
					"register_sucess.ftl",
					new String[] { userEmail },
					new String[] {});
		}
		redirectAttributes.addFlashAttribute(RequestResult.RESULT, newFrameUser);
		if(StringUtils.isBlank(format)){
			format="jhtml";
		}
		return new ModelAndView(redirectToUrl(apiVer + "/success."+format));
	}
}
