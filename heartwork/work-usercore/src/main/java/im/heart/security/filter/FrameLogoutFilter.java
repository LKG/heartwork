package im.heart.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gg
 * @desc 用户登出过滤器
 */
public class FrameLogoutFilter extends LogoutFilter {
	protected static final Logger logger = LoggerFactory.getLogger(FrameLogoutFilter.class);
	private String redirectUrl = "/login.jhtml?logout=1";
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		Subject subject = getSubject(request, response);
		String redirectUrl = getRedirectUrl(request, response, subject);
		logger.info("用户注销控制器....");
		try {
			subject.logout();
			//移除限制登录缓存
		} catch (SessionException ise) {
			logger.debug("Encountered session exception during logout.  This can generally safely be ignored."
					+ ise);
		}
		issueRedirect(request, response, redirectUrl);
		return false;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	
}
