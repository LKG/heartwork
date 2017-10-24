package im.heart.core.plugins.sms;

import im.heart.core.web.ResponseError;

import java.util.Map;

/**
 * 
 * @author gg
 * @desc 短信发送接口
 */
public interface SmsSendService {
	public static final String BEAN_NAME = "smsSendService";
	/**
	 * 
	 * @Desc：向指定号码发送短信
	 * @param mobilePhone
	 * @param content
	 * @return
	 */
	public  ResponseError sendSms(String mobilePhone,String content);
	

	/**
	 * 
	 * @Desc：根据手机短信模板返送短信
	 * @param model
	 * @param templatePath
	 * @param moblieTo
	 * @return
	 */
	public ResponseError sendSms(Map<String, Object> model, 
			String templatePath, String[] moblieTo);
	
}
