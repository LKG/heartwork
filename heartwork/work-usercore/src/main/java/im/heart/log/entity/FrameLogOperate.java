package im.heart.log.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity()
@Table(name = "dic_frame_log_operate")
@DynamicUpdate(true)
@DynamicInsert(true)
public class FrameLogOperate implements AbstractEntity<BigInteger> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7882801623296987581L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ID", nullable = false, unique = true, updatable = false)
	private BigInteger id;// Id
	
	@Column(length = 32, name = "USER_ID", nullable = false, updatable = false)
	private BigInteger userId;// 操作用户编号
	
	@NotBlank
	@Size(min = 5, max = 32)
	@Length(max = 32)
	@Column(length = 32, name = "USER_NAME", nullable = false,updatable = false)
	private String userName;// 操作用户登录帐号
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "REQUEST_TIME" ,updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTime;
	
	@Column(name = "params" ,updatable = false, nullable = false)
	private String params; //请求参数
	
	@Column(name = "type" ,updatable = false, nullable = false)
	private String type;//操作内容
	
	@Column(name = "content" ,updatable = false, nullable = false)
	private String content;//操作内容
	
	@Column(name = "USER_AGENT" ,updatable = false, nullable = false)
    private String userAgent; //用户浏览器类型
    @Column(name = "USER_HOST" ,updatable = false, nullable = false)
    private String userHost;//用户登录IP
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

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserHost() {
		return userHost;
	}

	public void setUserHost(String userHost) {
		this.userHost = userHost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
