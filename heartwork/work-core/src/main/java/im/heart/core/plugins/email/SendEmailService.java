package im.heart.core.plugins.email;

import java.util.Map;

/**
 * 
 * @author lkg
 * @Desc : 邮件发送接口
 */
public interface SendEmailService {
	public static final String BEAN_NAME = "sendEmailService";

	/**
	 * 
	 * @Desc：邮件发送接口
	 * @param emailTitle
	 *            邮件标题
	 * @param mailContent
	 *            邮件内容
	 * @param mailTo
	 *            收件人地址
	 * @param files
	 *            附件
	 */
	public void sendEmail(String emailTitle, String mailContent,
			String[] mailTo, final String[] files);


	/**
	 * 
	 * @Desc：根据模板发送邮件
	 * @param model
	 * @param emailTitle
	 * @param templatePath
	 * @param mailTo
	 * @param files
	 */
	public void sendEmail(Map<String, Object> model, String emailTitle,
			String templatePath, String[] mailTo, final String[] files);

}