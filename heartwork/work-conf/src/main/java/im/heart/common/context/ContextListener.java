package im.heart.common.context;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author gg
 * @desc : 自定义监听器 可以进行一些初始化操作
 */
@Component
public class ContextListener implements ApplicationContextAware {
	protected static final Logger logger = LoggerFactory.getLogger(ContextListener.class); 
	public static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if(context==null){
			context = applicationContext;
		}
	}
}
