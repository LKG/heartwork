package im.heart.security.tags;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

public class GuestTag extends SecureTag {
	protected static final Logger logger = LoggerFactory.getLogger(GuestTag.class);

	@Override
	public void render(Environment env, Map<?,?> params, TemplateDirectiveBody body)
			throws IOException, TemplateException {
		if (getSubject() == null || getSubject().getPrincipal() == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Subject does not exist or does not have a known identity (aka 'principal').  "
						+ "Tag body will be evaluated.");
			}

			renderBody(env, body);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Subject exists or has a known identity (aka 'principal').  "
						+ "Tag body will not be evaluated.");
			}
		}
	}

}
