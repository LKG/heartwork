package im.heart.security.filter;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gg
 * @desc ShiroFilterFactoryBean  工厂
 */
public class ShiroFilterFactory extends ShiroFilterFactoryBean{
	protected static final Logger logger = LoggerFactory.getLogger(ShiroFilterFactory.class);

	// 对ShiroFilter来说，需要直接忽略的请求
    private Set<String> ignoreExt;
    public ShiroFilterFactory() {
        super();
        ignoreExt = new HashSet<String>();
        ignoreExt.add(".jpg");
        ignoreExt.add(".png");
        ignoreExt.add(".gif");
        ignoreExt.add(".bmp");
        ignoreExt.add(".js");
        ignoreExt.add(".css");
    }
    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
    	logger.debug(""+ignoreExt);
        return super.createInstance();
    }
	
}
