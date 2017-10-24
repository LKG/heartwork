package im.heart.common.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import im.heart.common.utils.CacheUtils;
import im.heart.core.plugins.captcha.CaptchaServiceException;
import im.heart.core.plugins.captcha.ImageCaptchaExService;
import im.heart.core.plugins.qr.QRCodeService;
import im.heart.core.plugins.sms.SmsSendService;
import im.heart.core.utils.BaseUtils;
import im.heart.core.utils.StringUtilsEx;
import im.heart.core.web.AbstractController;
import im.heart.core.web.ResponseError;

@Controller
@RequestMapping("/validate")
public class ValidateController extends AbstractController {
	protected static final Logger logger = LoggerFactory.getLogger(ValidateController.class);

	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private ImageCaptchaExService imageCaptchaService;

	@Autowired
	private QRCodeService qRCodeService;
	
	/**
	 * @Desc： 生成验证码
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/passcode")
	public void generatImageCaptcha(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "module", required = false ,defaultValue="login") String module,
			@RequestParam(value = "rand", required = false ,defaultValue="sjrand") String rand) {
		ServletOutputStream out = null;
		try {
			logger.debug("module："+module+"rand:"+rand);
			response.setDateHeader("Expires", 0L);
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");
			String sessionId = request.getSession().getId();
			
			BufferedImage bufferedImage = this.imageCaptchaService.getImageChallengeForID(sessionId, request.getLocale());
			out = response.getOutputStream();
			ImageIO.write(bufferedImage, "jpg", out);
			out.flush();
		} catch (IllegalArgumentException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} catch (CaptchaServiceException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} catch (IOException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getStackTrace()[0].getMethodName(), e);
					out = null;
				}
			}
		}
	}
	/**
	 * 
	 * @Desc：生成二维码
	 * @param request
	 * @param response
	 * @param module
	 * @param rand
	 */
	@RequestMapping(value = "/passQRcode")
	public void generatQRcodeImage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "text", required = false ) String contents,
			@RequestParam(value = "bg", required = false ,defaultValue="ffffff") String background,
			@RequestParam(value = "fg", required = false ,defaultValue="cc0000") String foreground,
			@RequestParam(value = "gc", required = false ,defaultValue="cc00000") String gc,
			@RequestParam(value = "el", required = false ,defaultValue="H") String errorCorrectionLevel,
			@RequestParam(value = "w", required = false ,defaultValue="200") int width,
			@RequestParam(value = "h", required = false ,defaultValue="200") int height,
			@RequestParam(value = "m", required = false ,defaultValue="10") int margin,
			@RequestParam(value = "pt", required = false ,defaultValue="00ff00") String pt,
			@RequestParam(value = "inpt", required = false ,defaultValue="000000") String inpt,
			@RequestParam(value = "logo", required = false ) String logo
			) {
		ServletOutputStream out = null;
		if(StringUtilsEx.isBlank(contents)){
			contents="无效输入";
		}else{
			try {
				contents=new String(contents.getBytes(Charsets.ISO_8859_1.toString()),Charsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getStackTrace()[0].getMethodName(), e1);
			}
		}
		
		try {
			ErrorCorrectionLevel errorLevel=ErrorCorrectionLevel.valueOf(errorCorrectionLevel.toUpperCase());
			if(errorLevel==null){
				errorLevel=ErrorCorrectionLevel.L;
			}
			BufferedImage bufferedImage = this.qRCodeService.generateQRcodeImage(contents, width, height,margin, errorLevel,logo);
			response.setDateHeader("Expires", 0L);
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");
			out = response.getOutputStream();
			ImageIO.write(bufferedImage, "png", out);
			out.flush();
		} catch (IllegalArgumentException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} catch (IOException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getStackTrace()[0].getMethodName(), e);
					out = null;
				}
			}
		}
	}

	/**
	 * @Desc： 发送短信验证码 
	 * @param request
	 * @param response
	 * @param model 
	 */
	@RequestMapping(value = "/passmobliecode")
	public ModelAndView passmobliecode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "moblie", required = false ) String moblie,
			@RequestParam(value = "type", required = false,defaultValue="1") String type,ModelMap model) {
		int mobileCode = (int)((Math.random()*9+1)*10000);
		Map<String,Object> modeltemp = new HashMap<String,Object>();//定义邮件模版
		
		modeltemp.put("mobileCode", mobileCode);
		logger.info("mobileCode:[{}],moblie:[{}], type:[{}],mobliecode-host:[{}]",BaseUtils.getIpAddr(request),moblie,mobileCode,type);
		CacheUtils.generateMobileCache(moblie, mobileCode);
		ResponseError responseError=this.smsSendService.sendSms(modeltemp, "register.ftl", new String[]{moblie});
		if(responseError==null){
			this.success(model,true);
			return new ModelAndView(RESULT_PAGE);
		}
		this.fail(model,responseError);
		return new ModelAndView(RESULT_PAGE);
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
	@RequestMapping(value = "/checkMobliecode")
	public ModelAndView ValidateMobliecode(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "userPhone", required = false ) String moblie,
			@RequestParam(value = "phoneCode", required = false) String phoneCode,
			ModelMap model){
		logger.debug("passcode-host:"+request.getLocalAddr());
		Boolean isResponseCorrect = Boolean.FALSE;
		isResponseCorrect=CacheUtils.checkMobileCode(moblie, phoneCode);
		if(isResponseCorrect){//校验通过
			super.success(model,true);
			return new ModelAndView(RESULT_PAGE);
		}
		this.fail(model,false);
		return new ModelAndView(RESULT_PAGE);
	}
	/**
	 * 
	 * @Desc：验证码预校验接口
	 * @param request
	 * @param response
	 * @param captchaValue
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkRandCode")
	public ModelAndView ValidateCaptchaImage(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "validateCode", required = false) String captchaValue,
			ModelMap model){
		logger.debug("passcode-host:"+request.getLocalAddr());
		Boolean isResponseCorrect = Boolean.FALSE;
		String sessionId = request.getRequestedSessionId();
		boolean isAjax = BaseUtils.isAjaxRequest(request);
		try {
			//Ajax 请求不移除session
			isResponseCorrect = this.imageCaptchaService.validateResponseForID(sessionId, captchaValue,isAjax).booleanValue();
		} catch (CaptchaServiceException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
			throw new CaptchaServiceException(e);
		}
		if(isResponseCorrect){//校验通过
			super.success(model,true);
		}else{
			super.fail(model,false);
		}
		return new ModelAndView(RESULT_PAGE);
	}
}
