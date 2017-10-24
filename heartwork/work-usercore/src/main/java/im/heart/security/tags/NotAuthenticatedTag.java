package im.heart.security.tags;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

public class NotAuthenticatedTag extends SecureTag{
	protected static final Logger logger = LoggerFactory.getLogger(NotAuthenticatedTag.class);

	@Override
	public void render(Environment env, Map<?, ?> params,
			TemplateDirectiveBody body) throws IOException, TemplateException {
		if (getSubject() == null || !getSubject().isAuthenticated()) {
            logger.debug("Subject does not exist or is not authenticated.  Tag body will be evaluated.");
            renderBody(env, body);
        } else {
        	logger.debug("Subject exists and is authenticated.  Tag body will not be evaluated.");
        }
	}

}
