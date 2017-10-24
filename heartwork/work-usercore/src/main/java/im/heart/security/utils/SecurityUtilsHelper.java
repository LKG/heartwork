package im.heart.security.utils;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.heart.usercore.vo.FrameUserVO;

public class SecurityUtilsHelper extends SecurityUtils {
	protected static final Logger logger = LoggerFactory.getLogger(SecurityUtilsHelper.class);

	/**
	 * 
	 * @Desc：获取session 信息
	 * @param create
	 * @return
	 */
	public static Session getSession(boolean create) {
		return SecurityUtils.getSubject().getSession(create);
	}

	/**
	 * 
	 * @Desc：获取session 信息
	 * @return
	 */
	public static Session getSession() {
		return getSession(true);
	}

	public static Serializable getSessionId() {
		return getSession().getId();
	}

	public static boolean isAuthenticated() {
		return getSubject().isAuthenticated();
	}

	public static boolean isRemembered() {
		return getSubject().isRemembered();
	}

	public static void logout() {
		getSubject().logout();
	}

	public static Object getLoginPrincipal() {
		Subject subject = getSubject();
		return subject.getPrincipal();
	}

	/**
	 * 
	 * @Desc：清除用户缓存
	 */
	public static void clearCurrentUserCache() {
		if (isAuthenticated()) {
			logger.debug("clearCurrentUserCache....");
		}
	}

	public static FrameUserVO getLoginUserVO(Subject subject) {
		if (!isAuthenticated()) {
			return null;
		}
		Object po = subject.getPrincipal();
		if (po instanceof FrameUserVO) {
			FrameUserVO user = (FrameUserVO) po;
			return user;
		}
		return null;
	}
	public static BigInteger getCurrentUserId() {
		FrameUserVO userVO = getCurrentUser();
		if(userVO!=null){
			return userVO.getUserId();
		}
		return BigInteger.ZERO;
	}
	public static FrameUserVO getCurrentUser() {
		return getLoginUserVO(getSubject());
	}
}
