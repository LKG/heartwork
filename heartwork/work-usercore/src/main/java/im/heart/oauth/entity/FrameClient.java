package im.heart.oauth.entity;

import im.heart.core.entity.AbstractEntity;
import im.heart.core.enums.Status;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "dic_frame_client")
@DynamicUpdate(true)
public class FrameClient implements AbstractEntity<BigInteger> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4439068166202285785L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, name = "ID", nullable = false, unique = true, updatable = false)
	private BigInteger id;

	@NotBlank
	@Length(max = 128)
	@Column(length = 128, name = "CLIENT_ID", nullable = false, unique = true, updatable = false)
	private String clientId;
	@NotBlank
	@Length(max = 128)
	@Column(length = 128, name = "CLIENT_SECRET", nullable = false, unique = true, updatable = false)
	private String clientSecret;

	@NotBlank
	@Length(max = 128)
	@Column(length = 128, name = "CLIENT_NAME", nullable = false, unique = true, updatable = false)
	private String clientName;
	
	
	@NotBlank
	@Length(max = 128)
	@Column(length = 128, name = "CLIENT_URI", nullable = false)
	private String clientUri;
	@NotNull
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@NotNull
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;
	@NotBlank
	@Length(max = 15)
	@Enumerated(EnumType.STRING)
	@Column(length = 15, name = "STATUS", nullable = false)
	private Status status = Status.pending;
	
	@Length(max = 512)
	@Column(length = 512, name = "REMARK")
	private String remark;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientUri() {
		return clientUri;
	}

	public void setClientUri(String clientUri) {
		this.clientUri = clientUri;
	}

}
