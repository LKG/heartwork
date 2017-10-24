package im.heart.usercore.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import im.heart.core.entity.AbstractEntity;
import im.heart.core.enums.Status;

/**
 * 
 * @author gg
 * @Desc : 用户表
 */
@Entity()
@Table(name = "dic_frame_user")
@DynamicUpdate(true)
@DynamicInsert(true)
public class FrameUser implements AbstractEntity<BigInteger> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3658884594841390509L;
	public enum CheckStatus {
		pending(-2, "pending", "未审核"), 
		waiting(-1, "waiting", "审核中"), 
		fail(0,	"fail", "审核不通过"), 
		success(1, "success", "审核通过");
		public String code;
		public int intValue;
		public final String info;

		CheckStatus(int intValue, String code, String info) {
			this.code = code;
			this.intValue = intValue;
			this.info = info;
		}
		public static CheckStatus findByIntValue(int intValue) {
			for (CheckStatus status : CheckStatus.values()) {
				if (status.intValue == intValue) {
					return status;
				}
			}
			return CheckStatus.fail;
		}
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "USER_ID", nullable = false, unique = true, updatable = false)
	private BigInteger userId;// 用户编号

	@NotBlank
	@Size(min = 5, max = 32)
	@Length(max = 32)
	@Column(length = 32, name = "USER_NAME", nullable = false, unique = true, updatable = false)
	private String userName;// 登录帐号
	
	@Column(length = 32, name = "USER_TYPE", nullable = false, unique = true, updatable = false)
	private String userType;// 账号类型
	
	
	@NotBlank
	@Length(min = 6, max = 13)
	@Column(length = 32, name = "USER_PHONE", nullable = false)
	private String userPhone; //用户手机号，可用于登录
	
	@JSONField(serialize = false)
	@Length(max = 128)
	@Column(length = 128, name = "SALT_KEY", nullable = false)
	private String saltKey;// key
	
	@Transient
	@Size(min = 5, max = 15)
	@JSONField(serialize = false)
	private String phoneCode;// 短信验证码 非数据库字段
	
	@Length(max = 128)
	@Column(length = 128, name = "NICK_NAME")
	private String nickName = "";// 昵称

	@Email
	@Length(max = 128)
	@Column(length = 128, name = "USER_EMAIL")
	private String userEmail;//用户邮箱，可用于登录
	
	@NotBlank
	@Size(min = 6, max = 32)
	@JSONField(serialize = false)
	@Column(length = 128, name = "PASS_WORD", nullable = false)
	private String passWord;// 密码
	
	@Column(name = "USER_LEVEL", nullable = false)
	private Integer userLevel = 1; // 用户级别
	
	@Transient
	@Size(min = 6, max = 15)
	@JSONField(serialize = false)
	private String retryPassWord;// 确认密码 非数据库字段
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;// 修改日期
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "EXPIRY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@Future()
	private Date expiryTime;// 有效期
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "LAST_LOGIN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;// 最后登录时间
	
	@Length(max = 64)
	@Column(length = 64, name = "LAST_LOGIN_IP", nullable = false)
	private String lastLoginIP = "";// 最后登录IP
	
	@Length(max = 128)
	@Column(length = 128, name = "head_portrait", nullable = false)
	private String headPortrait = "";
	
	// 新增属性
	@Column(name = "USER_POINT", nullable = false)
	private BigInteger userPoint = BigInteger.ZERO;// 积分
	
	@Column(nullable = false, name = "STATUS",length=16)
	@Enumerated(EnumType.STRING)
	private Status status = Status.pending;// 用户状态，用于表示用户状态

	//-----------------------
	
	@Column(nullable = false, name = "CHECK_STATUS" ,length=16)
	@Enumerated(EnumType.STRING)
	private CheckStatus checkStatus = CheckStatus.pending;// 用户审核认证状态，已审核，待审核，审核中，审核驳回
	
	
	@Length(max = 128)
	@Column(length = 128, name = "REAL_NAME")
	private String realName = "";// 真实名称
	@Length(max = 128)
	@Column(length = 128, name = "USER_CHANNEL", updatable = false)
	private String userChannel = "";// 渠道
	
	@Length(min = 15, max = 32)
	@Column(length = 32, name = "ID_CARD")
	@Pattern(regexp="^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$")
	private String idCard;
	
	//-----------------------
	
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="DEFAULT_ORG_ID")//关联的表为org表，其主键是id
    @JSONField(serialzeFeatures={SerializerFeature.DisableCircularReferenceDetect})
    @Fetch(FetchMode.SELECT)
	private FrameOrg relateOrg;
	
	@Length(max = 256)
	@Column(length = 256, name = "REMARK", updatable = false)
	@JSONField(serialize = false)
	private String remark="";// 备注信息，不随用户更新
	
/*	private Set<FrameUserAttr> userAttrs;*/
	
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
	}
	/**
	 * 
	 * @Desc：获取salt
	 * @return
	 */
	@JSONField(serialize = false)
	public String getCredentialsSalt() {
		return this.userName + this.saltKey;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getSaltKey() {
		return saltKey;
	}

	public void setSaltKey(String saltKey) {
		this.saltKey = saltKey;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public String getRetryPassWord() {
		return retryPassWord;
	}

	public void setRetryPassWord(String retryPassWord) {
		this.retryPassWord = retryPassWord;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModiTime() {
		return modiTime;
	}

	public void setModiTime(Date modiTime) {
		this.modiTime = modiTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	public BigInteger getUserPoint() {
		return userPoint;
	}

	public void setUserPoint(BigInteger userPoint) {
		this.userPoint = userPoint;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public CheckStatus getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(CheckStatus checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public FrameOrg getRelateOrg() {
		return relateOrg;
	}

	public void setRelateOrg(FrameOrg relateOrg) {
		this.relateOrg = relateOrg;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public String getUserChannel() {
		return userChannel;
	}

	public void setUserChannel(String userChannel) {
		this.userChannel = userChannel;
	}

/*	public Set<FrameUserAttr> getUserAttrs() {
		return userAttrs;
	}

	public void setUserAttrs(Set<FrameUserAttr> userAttrs) {
		this.userAttrs = userAttrs;
	}*/
	
	
}
