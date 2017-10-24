package im.heart.security.session;

import java.math.BigInteger;

import org.apache.shiro.session.mgt.SimpleSession;

public class OnlineSession extends SimpleSession {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7291989300676876442L;

	public static enum OnlineStatus {
		on_line("在线"), hidden("隐身"), force_logout("强制退出");
		private final String info;

		private OnlineStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 	当前登录的用户Id
	 */
	private transient BigInteger userId = BigInteger.ZERO;

	private transient String username;

	/**
	 * 用户浏览器类型
	 */
	private String userAgent;

	/**
	 * 在线状态
	 */
	private OnlineStatus status = OnlineStatus.on_line;

	/**
	 * 用户登录时系统IP
	 */
	private String systemHost;
	/**
	 * 属性是否改变 优化session数据同步
	 */
	private transient boolean attributeChanged = false;

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public OnlineStatus getStatus() {
		return status;
	}

	public void setStatus(OnlineStatus status) {
		this.status = status;
	}

	public String getSystemHost() {
		return systemHost;
	}

	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}

	public boolean isAttributeChanged() {
		return attributeChanged;
	}

	public void setAttributeChanged(boolean attributeChanged) {
		this.attributeChanged = attributeChanged;
	}

	public void markAttributeChanged() {
		this.attributeChanged = true;
	}

	public void resetAttributeChanged() {
		this.attributeChanged = false;
	}	
}
