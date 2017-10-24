package im.heart.core.template;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author gg
 * @desc :模板指令 - 基类
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		verifyParameters(params);
		render(env, params, body);
	}

	/**
	 * @Desc 获取变量
	 * @param name
	 * @param env          
	 * @return
	 */
	public static TemplateModel getVariable(String name, Environment env) throws TemplateModelException {
		Assert.hasText(name, "'name' must not be empty");
		Assert.notNull(env,"The class must not be null");
		return env.getVariable(name);
	}

	/**
	 * @Desc 设置变量
	 * @param name
	 * @param value
	 * @param env
	 *            
	 */
	public static void setVariable(String name, Object value, Environment env) throws TemplateException {
		Assert.hasText(name, "'name' must not be empty");
		Assert.notNull(env,"The class must not be null");
		if (value instanceof TemplateModel) {
			env.setVariable(name, (TemplateModel) value);
		} else {
			env.setVariable(name, new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_24).build().wrap(value));
		}
	}

	/**
	 * @Desc 设置变量
	 * @param variables
	 * @param env
	 */
	public static void setVariables(Map<String, Object> variables, Environment env) throws TemplateException {
		Assert.notNull(variables,"The class must not be null");
		Assert.notNull(env,"The class must not be null");
		for (Entry<String, Object> entry : variables.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof TemplateModel) {
				env.setVariable(name, (TemplateModel) value);
			} else {
				env.setVariable(name,
						new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_24).build().wrap(value));
			}
		}
	}

	/**
	 * @Desc 获取参数信息
	 * @param params
	 * @param name
	 * @return
	 */
	protected String getParam(Map<?, ?> params, String name) {
		Object value = params.get(name);
		if (value instanceof SimpleScalar) {
			return ((SimpleScalar) value).getAsString();
		}
		return null;
	}

	/**
	 * @Desc 参数校验
	 * @param params
	 * @throws TemplateModelException
	 */
	protected void verifyParameters(Map<?, ?> params) throws TemplateModelException {
	}

	protected void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
		if (body != null) {
			body.render(env.getOut());
		}
	}

	public abstract void render(Environment env, Map<?, ?> params, TemplateDirectiveBody body)
			throws IOException, TemplateException;
}
