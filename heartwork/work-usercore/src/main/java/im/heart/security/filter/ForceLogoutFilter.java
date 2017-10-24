package im.heart.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import im.heart.security.session.OnlineSession;
import im.heart.security.session.ShiroSessionDAO;
import im.heart.security.utils.SecurityUtilsHelper;
import im.heart.usercore.vo.FrameUserVO;
/**
 * 
 * @author gg
 * @desc：强制踢出用户
 */
public class ForceLogoutFilter extends LogoutFilter {

	protected static final Logger logger = LoggerFactory.getLogger(ForceLogoutFilter.class);
    public static final String DEFAULT_REDIRECT_URL = "/login.jhtml?logout=3";
    private String redirectUrl = DEFAULT_REDIRECT_URL;
	@Autowired
	private ShiroSessionDAO shiroSessionDAO;
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
		FrameUserVO user = SecurityUtilsHelper.getCurrentUser();
		if(user==null){// 如果没有登录，直接进行之后的流程
			return true;
		}
		Session onlineSession = this.shiroSessionDAO.readSession(session.getId());
		if (onlineSession != null && onlineSession instanceof OnlineSession) {
			OnlineSession ss = (OnlineSession) onlineSession;
			if (OnlineSession.OnlineStatus.force_logout==ss.getStatus()) {
				try {
					logger.info("检测到用户已经被踢出，移除用户"+user.getUserName());
					subject.logout();
				} catch (SessionException ise) {
					logger.info("Encountered session exception during logout.  This can generally safely be ignored."
							+ ise);
				}
				saveRequest(request);  
				issueRedirect(request, response, redirectUrl);
				return false;
			}
		}
		return true;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
