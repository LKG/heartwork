package im.heart.security.tags;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.core.Environment;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;
/**
 * <p>Equivalent to {@link im.heart.security.im.heart.security.tags.shiro.web.tags.SecureTag}</p>
 *
 */
public abstract  class SecureTag implements TemplateDirectiveModel {
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		verifyParameters(params);
        render(env, params, body);
	}
	
	public abstract void render(Environment env, Map<?, ?> params,
			TemplateDirectiveBody body) throws IOException, TemplateException;

	protected String getParam(Map<?, ?> params, String name) {
		Object value = params.get(name);
		if (value instanceof SimpleScalar) {
			return ((SimpleScalar) value).getAsString();
		}
		return null;
	}

	protected Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	protected void verifyParameters(Map<?, ?> params) throws TemplateModelException {
	}

	protected void renderBody(Environment env, TemplateDirectiveBody body)
			throws IOException, TemplateException {
		if (body != null) {
			body.render(env.getOut());
		}
	}
}
