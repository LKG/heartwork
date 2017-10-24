package im.heart.core.plugins.email;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author lkg
 * @Desc : 邮件发送实现类 Demo
 */
@Component(value = SendEmailService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class EmailServiceImpl implements SendEmailService {
	protected static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
	private EmailManager emailManager;

	@Override
	@Async
	public void sendEmail(String emailTitle, String mailContent,
			String[] mailTo, String[] files) {
		logger.info("模拟发送邮件啦................................");
		try {
			this.emailManager.sendEmail(emailTitle, mailContent, mailTo, files);
		} catch (Exception e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
	}

	@Override
	@Async
	public void sendEmail(Map<String, Object> model, String emailTitle,
			String templatePath, String[] mailTo, String[] files) {
		logger.info("模拟发送邮件啦................................");
		try {
			this.emailManager.sendEmail(model, emailTitle, templatePath,mailTo, files);
		} catch (Exception e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
	}

}
