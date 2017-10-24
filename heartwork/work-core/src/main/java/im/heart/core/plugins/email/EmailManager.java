package im.heart.core.plugins.email;

import java.io.File;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import im.heart.core.utils.FreeMarkerUtils;
/**
 * 
 * @author lkg
 * @desc :邮件发送配置管理项
 */
@Component()
public class EmailManager {
	protected static final Logger logger = LoggerFactory.getLogger(EmailManager.class);
	@Value("${spring.mail.from}")
	private String mailFrom="";
	@Value("${spring.mail.template-path}")
	private String mailTemplatePath="classpath:/templates/email/";
	@Autowired
	private JavaMailSender javaMailSender;
	/**
	 * 
	 * @Desc：mergeEmailContent
	 * @param model
	 * @param tplfile
	 * @return
	 */
	public String mergeEmailContent(final Map<String, Object> model,
			final String tplfile) {
		String content = "";
		try {
			Configuration cfg = FreeMarkerUtils.buildConfiguration(this.mailTemplatePath);
			Template template = cfg.getTemplate(tplfile);
			content = FreeMarkerUtils.renderTemplate(template, model);
		} catch (Exception e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
		return content;
	}

	/**
	 * 
	 * @Desc：发送邮件
	 * @param preparator
	 */
	protected void sendJavaMail(MimeMessagePreparator preparator) {
		try {
			logger.info("Begin to send mail...");
			this.javaMailSender.send(preparator);// 发送邮件
			logger.info("End to send mail!");
		} catch (Exception e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}
	}
  
	private MimeMessagePreparator bulidMimeMessagePreparator(final String[] mailTo,final String subject ,final String content ,final String[] files){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage, true, "UTF-8");
					message.setTo(mailTo);// 设置接收方的email地址
					message.setSubject(subject);// 设置邮件主题
					message.setFrom(mailFrom);// 设置发送方地址
					message.setText(content, true);
					if(files!=null){
						for (String s : files){// 添加附件
							FileSystemResource	file = new FileSystemResource(new File(s));// 读取附件
							message.addAttachment(s, file);// 向email中添加附件
						}	
					}

				} catch (Exception e) {
					logger.error(e.getStackTrace()[0].getMethodName(), e);
				}
			}
		};
		return preparator;
	}
	/**
	 * 
	 * @Desc：发送邮件
	 * @param model 传入对象信息
	 * @param subject    邮件标题
	 * @param tplfile    邮件模板路径
	 * @param mailTo  邮件接收方
	 * @param files  附件
	 * 
	 * @throws Exception
	 */
	public void sendEmail(final Map<String, Object> model,
			final String subject, final String tplfile, final String[] mailTo,
			final String[] files) throws Exception {
		String content = mergeEmailContent(model, tplfile);
		MimeMessagePreparator preparator=this.bulidMimeMessagePreparator(mailTo,subject,content,files);
		this.sendJavaMail(preparator);
	}

	/**
	 * 
	 * @Desc：发送邮件
	 * @param subject  邮件标题
	 * @param content  邮件内容
	 * @param mailTo    邮件接收方
	 * @param files   附件
	 * @throws Exception
	 */
	public void sendEmail(final String subject, final String content,
			final String[] mailTo, final String[] files) throws Exception {
		MimeMessagePreparator preparator =this.bulidMimeMessagePreparator(mailTo,subject,content,files);
		this.sendJavaMail(preparator);
	}
	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTemplatePath() {
		return mailTemplatePath;
	}

	public void setMailTemplatePath(String mailTemplatePath) {
		this.mailTemplatePath = mailTemplatePath;
	}


}