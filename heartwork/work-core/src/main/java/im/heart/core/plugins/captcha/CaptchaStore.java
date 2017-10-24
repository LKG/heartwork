package im.heart.core.plugins.captcha;

import java.util.Collection;
import java.util.Locale;

import im.heart.core.plugins.captcha.CaptchaServiceException;
/**
 * 
 * @Desc：验证码存储接口
 * @作者 LKG
 */
public interface CaptchaStore {
	public boolean hasCaptcha(String id);

	public boolean removeCaptcha(String id);
	
    public Captcha getCaptcha(String id) throws CaptchaServiceException;

	public void storeCaptcha(String iD, Captcha captcha, Locale locale);
	
	 /**
     * get the size of this store
     */
    int getSize();

    /**
     * Return all the contained keys
     */
    Collection<String> getKeys();

    /**
     * Empty the store
     */
    void empty();
    
    /** 
     * Called by the service in order to initialize and start the Store.
     */
    void initAndStart();
    
    /**
     * Called by the service in order to clean and shutdown the store.
     */
    void cleanAndShutdown();

}
