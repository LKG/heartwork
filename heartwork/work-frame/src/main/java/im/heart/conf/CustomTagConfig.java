package im.heart.conf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import im.heart.frame.tags.CustomTags;

@Component
public class CustomTagConfig {
	@Autowired
	private Configuration configuration;

	@Autowired
	private CustomTags customTags;
	@PostConstruct
	public void setSharedVariable() throws TemplateModelException {
		configuration.setSharedVariable("custom", customTags);
	}
}
