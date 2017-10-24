package im.heart.core.plugins.captcha;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 
 * @author lkg
 * @Desc : 验证码接口
 */
public interface Captcha extends Serializable {

    String getQuestion();

    BufferedImage getChallenge();
    
	Boolean validateResponse(String response);

	Boolean hasGetChalengeBeenCalled();
}
