package im.heart.core.plugins.captcha;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageCaptchaExServiceImpl implements ImageCaptchaExService {
	@Autowired
	protected CaptchaStore store;
	/**
	 * 验证码字符集
	 */
	public  final static String WORD_DATA="ABCDEFGHJKLMNRSTUWXY235689";
	
	// 字符数量
	private static final int SIZE = 4;
	// 干扰线数量
	private static final int LINES = 5;
	// 宽度
	private static final int IMAGE_WIDTH = 80;
	// 高度
	private static final int IMAGE_HEIGHT = 35;
	// 字体大小
	private static final int FONT_SIZE = 20;

	/**
	 * 随机取色
	 */
	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), ran.nextInt(256),
				ran.nextInt(256));
		return color;
	}

	@Override
	public Boolean validateResponseForID(String sessionId, String validateCode) {
		return validateResponseForID(sessionId, validateCode, true);
	}

	@Override
	public void removeCaptcha(String sessionId) {
		if (sessionId != null && this.store.hasCaptcha(sessionId)) {
			this.store.removeCaptcha(sessionId);
		}
	}

	@Override
	public BufferedImage getImageChallengeForID(String sessionId, Locale locale) {
		Captcha captcha;
		// check if has capthca
		if (!this.store.hasCaptcha(sessionId)) {
			// if not generate and store
			captcha = generateAndStoreCaptcha(locale, sessionId);
		} else {
			// else get it
			captcha = this.store.getCaptcha(sessionId);
			if (captcha == null) {
				captcha = generateAndStoreCaptcha(locale, sessionId);
			} else {
				// if dirty
				if (captcha.hasGetChalengeBeenCalled().booleanValue()) {
					// get a new one and store it
					captcha = generateAndStoreCaptcha(locale, sessionId);
				}
				// else nothing
			}
		}
		return (BufferedImage) captcha.getChallenge();
	}

	@Override
	public Boolean validateResponseForID(String sessionId, String captchaValue,
			boolean isAjax) {
		if (!this.store.hasCaptcha(sessionId)) {
			throw new CaptchaServiceException(
					"Invalid ID, could not validate unexisting or already validated captcha");
		}
		Boolean valid = this.store.getCaptcha(sessionId).validateResponse(captchaValue);
		if (!isAjax) {
			this.removeCaptcha(sessionId);
		}
		return valid;
	}

	public ImageCaptcha getImageCaptcha(Locale locale) {
		StringBuffer sb = new StringBuffer();
		// 1.创建空白图片
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		// 2.获取图片画笔
		Graphics graphic = image.getGraphics();
		// 3.设置画笔颜色
		graphic.setColor(Color.LIGHT_GRAY);
		// 4.绘制矩形背景
		graphic.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		// 5.画随机字符
	   Random ran = new SecureRandom();
	   char[] chars=WORD_DATA.toCharArray();
	   for (int i = 0; i < SIZE; i++) {
			// 取随机字符索引
			int n = ran.nextInt(chars.length);
			// 设置随机颜色
			graphic.setColor(new Color(0, 0, 80));
			// 设置字体大小
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			// 画字符
			graphic.drawString(chars[n] + "", i * IMAGE_WIDTH / SIZE,
					IMAGE_HEIGHT * 2 / 3);
			// 记录字符
			sb.append(chars[n]);
		}
		// 6.画干扰线
		for (int i = 0; i < LINES; i++) {
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 随机画线
			graphic.drawLine(ran.nextInt(IMAGE_WIDTH),
					ran.nextInt(IMAGE_HEIGHT), ran.nextInt(IMAGE_WIDTH),
					ran.nextInt(IMAGE_HEIGHT));
		}
		// 7.返回验证码和图片
		ImageCaptcha captcha = new ImageCaptcha(sb.toString() ,image);
		return captcha;
	}

	protected Captcha generateAndStoreCaptcha(Locale locale, String id) {
		Captcha captcha = getImageCaptcha(locale);
		this.store.storeCaptcha(id, captcha, locale);
		return captcha;
	}

	@Override
	public BufferedImage getImageChallengeForID(String sessionId)
			throws CaptchaServiceException {
		return this.getImageChallengeForID(sessionId, Locale.getDefault());
	}

}
