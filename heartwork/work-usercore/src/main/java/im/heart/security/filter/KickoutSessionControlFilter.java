package im.heart.security.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import im.heart.security.cache.ShiroCacheConfig;
import im.heart.security.session.ShiroSessionDAO;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.vo.FrameUserVO;

/**
 * 
 * @author gg
 * @desc 控制用户登录个数
 */
public class KickoutSessionControlFilter extends LogoutFilter {

	protected static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);
    public static final String DEFAULT_REDIRECT_URL = "/login.jhtml?logout=2";
    private String redirectUrl = DEFAULT_REDIRECT_URL;
    private String kickoutParam = "kickout";
	private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private int maxSession = 1; // 同一个帐号最大会话数 默认1
	private String keyPrefix = ShiroCacheConfig.kickout.keyPrefix;
	@Autowired
	private ShiroSessionDAO shiroSessionDAO;
	private Cache<String, Deque<Serializable>> cache;

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache(keyPrefix);
	}
    protected void saveRequest(ServletRequest request) {
        WebUtils.saveRequest(request);
    }
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {// 如果没有登录，直接进行之后的流程
			return true;
		}
		Session session = subject.getSession();
		Serializable sessionId = session.getId();
		FrameUserVO user = SecurityUtilsHelper.getCurrentUser();
		if(user==null){// 如果没有登录，直接进行之后的流程
			return true;
		}
		String username=user.getUserName();
		Deque<Serializable> deque = this.cache.get(username);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
			this.cache.put(username, deque);
		}
		Object kickout = session.getAttribute(kickoutParam);
		if (!deque.contains(sessionId) && kickout == null) {
			deque.push(sessionId);
			this.cache.put(username, deque);
		}
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if (kickoutAfter) { // 如果踢出后者
				kickoutSessionId = deque.removeFirst();
			} else { // 否则踢出前者
				kickoutSessionId = deque.removeLast();
			}
			try {
				Session onlineSession = this.shiroSessionDAO.readSession(kickoutSessionId);
				if (onlineSession != null) {// 设置会话的kickout属性表示踢出了
					onlineSession.setAttribute(kickoutParam, true);
				}
			} catch (SessionException ise) {
				logger.debug("Encountered session exception during logout.  This can generally safely be ignored."
						+ ise);
			}
		
		}
		if (kickout != null) {
			try {
				logger.info("检测到用户重复登录，移除用户"+username);
				subject.logout();
			} catch (SessionException ise) {
				logger.debug("Encountered session exception during logout.  This can generally safely be ignored."
						+ ise);
			}
			saveRequest(request);  
			issueRedirect(request, response, redirectUrl);
			return false;
		}
		return true;
	}


	public String getKeyPrefix() {
		return keyPrefix;
	}
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public boolean isKickoutAfter() {
		return kickoutAfter;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public int getMaxSession() {
		return maxSession;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}
}
