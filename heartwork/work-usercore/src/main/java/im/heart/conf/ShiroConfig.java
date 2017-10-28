package im.heart.conf;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authz.SslFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.common.collect.Lists;

import im.heart.security.cache.ShiroCacheConfig;
import im.heart.security.credentials.RetryLimitCredentialsMatcher;
import im.heart.security.filter.ForceLogoutFilter;
import im.heart.security.filter.FrameAuthenticationFilter;
import im.heart.security.filter.FrameLogoutFilter;
import im.heart.security.filter.KickoutSessionControlFilter;
import im.heart.security.filter.ShiroFilterFactory;
import im.heart.security.realm.FrameUserRealm;
import im.heart.security.session.OnlineSessionFactory;
import im.heart.security.session.ShiroSessionDAO;
import im.heart.security.session.ShiroSessionListener;
import im.heart.security.session.ShiroSessionManager;
import im.heart.security.session.ShiroWebSecurityManager;

@Configuration
@PropertySource(value = "classpath:/application-shiro.properties")
public class ShiroConfig {
	protected static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    @Value("${shiro.password.algorithmName}")
	private String algorithmName="md5";
	public static final String CACHEMANAGER_BEAN_NAME = ShiroCacheConfig.CACHEMANAGER_BEAN_NAME;
 
	@Value("${shiro.password.hashIterations}")
	private int hashIterations=2;
	@Value("${shiro.password.storedCredentialsHexEncoded}")
	private boolean storedCredentialsHexEncoded=true;
	private final static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	@Value("${shiro.login.url}")
	private String loginUrl = "/login.jhtml";
	
	@Value("${shiro.login.success.url}")
	private String successUrl = "/";
	@Value("${shiro.uid.cookie.name}")
	private String sessionIdName = "jsid";
	
	@Value("${shiro.unauthorizedUrl}")
	private String unauthorizedUrl = "/unauthorized.jhtml";
	
	@Value("${shiro.logout.success.url}")
	private String logoutSuccessUrl = "/login.jhtml?logout=1";
	@Bean(name = CACHEMANAGER_BEAN_NAME)
	public CacheManager cacheManager() {
		return new EhCacheManager();
	}
	/**
	 * 网络请求的权限过滤, 拦截外部请求
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(WebSecurityManager securityManager) {
		ShiroFilterFactory shiroFilterFactoryBean = new ShiroFilterFactory();
		shiroFilterFactoryBean.setLoginUrl(loginUrl);
		shiroFilterFactoryBean.setSuccessUrl(successUrl);//
		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		/*<!-- 添加自定义过滤链 -->*/
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc",new FrameAuthenticationFilter());
		/*<!-- 用户注销控制过滤链 -->*/
		filters.put("logout",new FrameLogoutFilter());
		/*<!-- 添加ssl过滤链 -->*/
		filters.put("ssl",new SslFilter());
		/*<!-- 控制并发登录人数 -->*/
		filters.put("kickout",kickoutSessionControlFilter());
		/*<!-- 强制退出用户 -->*/
		filters.put("forceLogout",forceLogoutFilter());
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/oauth2/**", "anon");//oauth2认证接口
		filterChainDefinitionMap.put("/3rd/**", "anon");//第三方登录认证接口
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/imgs/**", "anon");
		filterChainDefinitionMap.put("/app/js/**", "anon");
		filterChainDefinitionMap.put("/app/css/**", "anon");
		filterChainDefinitionMap.put("/app/imgs/**", "anon");
		filterChainDefinitionMap.put("/modules/**", "anon");
		filterChainDefinitionMap.put("/login-in**", "anon");
		filterChainDefinitionMap.put("/validate/**", "anon");//验证码
		filterChainDefinitionMap.put("/regist**", "anon");//注册
		filterChainDefinitionMap.put("/regist/**", "anon");//注册
		filterChainDefinitionMap.put("/findPwd/**", "anon");//找回密码
		filterChainDefinitionMap.put("/api/**", "anon");//对外接口
		filterChainDefinitionMap.put("/logout*", "logout");
		filterChainDefinitionMap.put("/", "anon");//首页不需要登录
		filterChainDefinitionMap.put("/admin/druid/**", "perms[druid:monitor]");
		filterChainDefinitionMap.put("/admin/monitor/**", "perms[monitor:monitor]");
		filterChainDefinitionMap.put("/authenticated", "authc");
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean(name = "kickout")
	public KickoutSessionControlFilter kickoutSessionControlFilter() {
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		kickoutSessionControlFilter.setCacheManager(cacheManager());
		return kickoutSessionControlFilter;
	}
	@Bean(name = "forceLogout")
	public ForceLogoutFilter forceLogoutFilter() {
		ForceLogoutFilter forceLogoutFilter = new ForceLogoutFilter();
		return forceLogoutFilter;
	}
	
	@Bean(name = "credentialsMatcher")
	public RetryLimitCredentialsMatcher credentialsMatcher() {
		RetryLimitCredentialsMatcher retryLimitCredentialsMatcher=new RetryLimitCredentialsMatcher();
		retryLimitCredentialsMatcher.setHashAlgorithmName(algorithmName);
		retryLimitCredentialsMatcher.setHashIterations(hashIterations);
		retryLimitCredentialsMatcher.setStoredCredentialsHexEncoded(storedCredentialsHexEncoded);
		retryLimitCredentialsMatcher.setCacheManager(cacheManager());
		return retryLimitCredentialsMatcher;
	}
	@Bean(name="frameUserRealm")
	public FrameUserRealm frameUserRealm() {
		FrameUserRealm frameUserRealm=	new FrameUserRealm();
		frameUserRealm.setCacheManager(cacheManager());
		frameUserRealm.setCredentialsMatcher(credentialsMatcher());
		return frameUserRealm;
	}
	
	/**
	 * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
	 * @return
	 */
	@Bean(name = "securityManager")
	public WebSecurityManager defaultWebSecurityManager() {
		ShiroWebSecurityManager wsm = new ShiroWebSecurityManager();
		wsm.setCacheManager(cacheManager());
		wsm.setRealm(frameUserRealm());
		wsm.setSessionManager(sessionManager());
		wsm.setRememberMeManager(rememberMeManager());
		SecurityUtils.setSecurityManager(wsm);
		return wsm;
	}
	@Bean(name = "sessionIdCookie")
	public Cookie sessionIdCookie() {
        Cookie cookie = new SimpleCookie(sessionIdName);
        cookie.setHttpOnly(true); //more secure, protects against XSS attacks
		return cookie;
	}
	@Bean(name = "rememberMeManager")
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return rememberMeManager;
	}
	@Bean(name = "onlineSessionFactory")
	public SessionFactory onlineSessionFactory() {
		SessionFactory sessionFactory = new OnlineSessionFactory();
		return sessionFactory;
	}
	@Bean
	public SessionManager sessionManager() {
		ShiroSessionManager sessionManager = new ShiroSessionManager();
		sessionManager.setGlobalSessionTimeout(ShiroSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionFactory(onlineSessionFactory());
		sessionManager.setSessionIdUrlRewritingEnabled(false);//移除JSESSIONID小尾巴
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setSessionIdCookie(sessionIdCookie());
		Collection<SessionListener> listeners=Lists.newArrayList();
		listeners.add(new ShiroSessionListener());//设置SESSION 监听器
		sessionManager.setSessionListeners(listeners);
		return sessionManager;
	}
	/**
	 * 自定义sessionListener
	 * @return
	 */
	@Bean(name="sessionListener")
	public SessionListener shiroSessionListener(){
		ShiroSessionListener shiroSessionListener=new ShiroSessionListener();
		return shiroSessionListener;
	}
	/**
	 * 自定义sessionDAO
	 * @return
	 */
	@Bean(name = "sessionDAO")
	public CachingSessionDAO sessionDAO() {
		ShiroSessionDAO sessionDAO=new ShiroSessionDAO();
		return sessionDAO;
	}
	
	/**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{defaultWebSecurityManager()});
        return bean;
    }
	
	/**
	 * 	LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
	 * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
	 * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
	 * 协助shiro初始化, 负责调用shiro的init与destory
	 */
	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	/**
	 *
    * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 * @return
	 */
	@Bean()
	@ConditionalOnMissingBean
   public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
       DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
       daap.setProxyTargetClass(true);
       return daap;
   }
   @Bean()
   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
	 AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	 return sourceAdvisor;
   }
	
}
