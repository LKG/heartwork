package im.heart.cms.entity;
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
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.enums.Status;

/**
 * 
 * @author lkg
 * @Desc : 友链表
 */
@Entity
@Table(name = "dic_cms_friend_link")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_friend_link_sequence")
public class FriendLink {

	public enum CheckStatus {
		pending(-2, "pending", "未审核"), waiting(-1, "waiting", "审核中"), fail(0, "fail", "审核不通过"), success(1, "success",
				"审核通过");
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
	/**
	 * 类型
	 */
	public enum Type {
		/** 文本 */
		text,
		/** 图片 */
		image
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ID", nullable = false, unique = true, updatable = false)
	private BigInteger id;// id

	@NotEmpty
	@NotBlank
	@Length(min = 1, max = 200)
	@Column(name = "NAME", nullable = false,length = 200)
	private String name;

	@NotEmpty
	@NotBlank
	@Length(min = 1, max = 200)
	@Column(name = "URL", nullable = false, length = 200)
	private String url;
	@NotEmpty
	@NotBlank
	@Length(min = 1, max = 200)
	@Column(name = "LOGO", nullable = false, length = 200)
	private String logo;
	@Column(name = "STATUS", nullable = false, length = 16)
	@Enumerated(EnumType.STRING)
	private Status status = Status.pending;
	
	@Column(name = "TYPE", nullable = false, length = 16)
	@Enumerated(EnumType.STRING)
	private Type type = Type.text;
	
	@Column(nullable = false, name = "CHECK_STATUS", length = 16)
	@Enumerated(EnumType.STRING)
	private CheckStatus checkStatus = CheckStatus.pending;// 用户审核认证状态，已审核，待审核，审核中，审核驳回
	@Length(max = 200)
	@Column(name = "REMARK", nullable = false, length = 200)
	private String remark;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期

	@JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;// 修改日期
	
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public CheckStatus getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(CheckStatus checkStatus) {
		this.checkStatus = checkStatus;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
