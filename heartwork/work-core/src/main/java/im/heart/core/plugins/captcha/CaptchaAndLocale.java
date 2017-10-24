package im.heart.core.plugins.captcha;

import java.io.Serializable;
import java.util.Locale;

/**
 * 
 * @Desc：验证码包装类
 * @作者 LKG
 */
public class CaptchaAndLocale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4099552770828992716L;
	private Captcha captcha;
	private Locale locale;

	public CaptchaAndLocale(Captcha captcha) {
		this.captcha = captcha;
	}

	public CaptchaAndLocale(Captcha captcha, Locale locale) {
		this.captcha = captcha;
		this.locale = locale;
	}

	public Captcha getCaptcha() {
		return captcha;
	}

	public void setCaptcha(Captcha captcha) {
		this.captcha = captcha;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
