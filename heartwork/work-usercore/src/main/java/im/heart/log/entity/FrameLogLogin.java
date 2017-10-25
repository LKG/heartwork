package im.heart.log.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @desc : 访问日志表
 */
@Entity()
@Table(name = "dic_frame_log_login")
@DynamicUpdate(true)
@DynamicInsert(true)
public class FrameLogLogin implements AbstractEntity<BigInteger> {
	public enum LogType {
		login(1, "login", "登录"), 
		logout(2, "logout", "登出"), 
		;
		public String code;
		public int intValue;
		public final String info;

		LogType(int intValue, String code, String info) {
			this.code = code;
			this.intValue = intValue;
			this.info = info;
		}
		public static LogType findByIntValue(int intValue) {
			for (LogType logType : LogType.values()) {
				if (logType.intValue == intValue) {
					return logType;
				}
			}
			return LogType.login;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7421909296950512439L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ID", nullable = false, unique = true, updatable = false)
	private BigInteger id;// Id
	
	@Column(length = 32, name = "USER_ID", nullable = false, updatable = false)
	private BigInteger userId;// 用户编号
	@NotBlank
	@Size(min = 5, max = 32)
	@Length(max = 32)
	@Column(length = 32, name = "USER_NAME", nullable = false,updatable = false)
	private String userName;// 登录帐号
	

	@Enumerated(EnumType.STRING)
	@Column(length = 20, name = "LOG_TYPE", nullable = false,updatable = false)
	private LogType logType;// 日志类型，1登录，2登出
	
//	@NotBlank
//	@Size(min = 6, max = 32)
//	@Column(length = 32, name = "LOGIN_CHANNEL", nullable = false,updatable = false)
//	private String loginChannel;// 登录渠道
	
	@Column(length = 128, name = "USER_IP_INFO",updatable = false)
	private String userIpInfo;// 根据IP 信息
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "USER_HOST" ,updatable = false, nullable = false)
	private String userHost;
	
	@Column(name = "USER_AGENT" ,updatable = false, nullable = false)
    private String userAgent=""; //用户浏览器类型
	
    @Column(name = "SYSTEM_HOST" ,updatable = false, nullable = false)
    private String systemHost;//用户登录时系统IP
    
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getSystemHost() {
		return systemHost;
	}

	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}

	public String getUserHost() {
		return userHost;
	}

	public void setUserHost(String userHost) {
		this.userHost = userHost;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public String getUserIpInfo() {
		return userIpInfo;
	}

	public void setUserIpInfo(String userIpInfo) {
		this.userIpInfo = userIpInfo;
	}

}
