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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "dic_cms_comment")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_comment_sequence")
public class Comment {
	
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
	@Column(length = 20, name = "COMMENT_ID", nullable = false, unique = true, updatable = false)
	private BigInteger commentId;// id
	
	@NotNull
	@Column(name = "PARENT_ID",length=32,nullable = false)
	private BigInteger parentId=BigInteger.ZERO;//父类节点ID
	
	@Column(length = 32, name = "USER_ID", nullable = false, updatable = false)
	private BigInteger userId;
	@NotBlank
	@Length(min=3)
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@Column(name = "USER_HOST" ,updatable = false, nullable = false)
	private String userHost;
	
	@Column(nullable = false, name = "CHECK_STATUS" ,length=16)
	@Enumerated(EnumType.STRING)
	private CheckStatus checkStatus = CheckStatus.pending;// 用户审核认证状态，已审核，待审核，审核中，审核驳回
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss",serialize=false)
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
	public BigInteger getCommentId() {
		return commentId;
	}
	public void setCommentId(BigInteger commentId) {
		this.commentId = commentId;
	}
	public BigInteger getParentId() {
		return parentId;
	}
	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
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
	
	
}
