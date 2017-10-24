package im.heart.common.context;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author gg
 * @desc : 容器，获取spring 加载的bean
 */
public class ContextManager {

	private static ApplicationContext context = ContextListener.context;

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static String[] getBeanNames(Class<?> clazz) {
		return context.getBeanNamesForType(clazz);
	}

	public static String getAbsolutePath(String url) throws Exception {
		return context.getResource(url).getFile().getAbsolutePath();
	}

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static String[] getBeanAliases(String name) {
		return context.getAliases(name);
	}
}
