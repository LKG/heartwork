package im.heart.core.plugins.captcha;

import java.awt.image.BufferedImage;
import java.util.Locale;

public interface ImageCaptchaExService{

	public BufferedImage getImageChallengeForID(String sessionId) throws CaptchaServiceException;
	
	public BufferedImage getImageChallengeForID(String sessionId, Locale locale) throws CaptchaServiceException;
	
	public Boolean validateResponseForID(String sessionId, String validateCode) ;

	public Boolean validateResponseForID(String sessionId, String captchaValue,	boolean isAjax);
	
	public void removeCaptcha(String sessionId);
	
}
