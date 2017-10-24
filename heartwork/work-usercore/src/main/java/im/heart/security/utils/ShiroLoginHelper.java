package im.heart.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import im.heart.core.CommonConst;

/**
 * 
 * @author gg
 * @desc 用户尝试登录次数帮助类
 */
public class ShiroLoginHelper {
	/**
	 * 
	 * @Desc：获取登录尝试次数
	 * @return
	 */
	public static Integer getLoginTimes(){
		Session session= SecurityUtils.getSubject().getSession();
		return getLoginTimes(session);
	}
	
	/**
	 * 
	 * @Desc：获取登录尝试次数
	 * @param session
	 * @return
	 */
	public static Integer getLoginTimes(Session session){
		Integer loginTimes=0;
		if(session!=null){
			Object sessionLoginTimes=session.getAttribute(CommonConst.SESSION_LOGIN_TIMES);
			if(sessionLoginTimes!=null&&StringUtils.isNumeric(sessionLoginTimes.toString())){
				loginTimes =  Integer.parseInt(sessionLoginTimes.toString());
			}
		}
		return loginTimes;
	}
	
	/**
	 * 
	 * @Desc：设置登录失败session
	 */
	public static void setLoginFailureSession(){
		Session session= SecurityUtils.getSubject().getSession();
		Integer loginTimes=ShiroLoginHelper.getLoginTimes(session);
		loginTimes++;
		session.setAttribute(CommonConst.SESSION_LOGIN_TIMES,loginTimes);
	}
	/**
	 * 
	 * @Desc：设置登录成功session
	 */
	public static void setLoginSuccessSession(){
		Subject subject = SecurityUtils.getSubject();
		Session session= subject.getSession();
		session.setAttribute(CommonConst.SESSION_LOGIN_TIMES,0);
	}
	
}
