package im.heart.cms.service;

import java.util.Map;

public interface HtmlStaticService {
	public static final String BEAN_NAME = "htmlStaticService";
	int build(String templatePath, String staticPath, Map<String, Object> model);

	int build(String templatePath, String staticPath);
	void buildHeader();
	void buildFooter();
	void buildHeaderAndFooter();
}
