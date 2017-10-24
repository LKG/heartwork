package im.heart.usercore.entity;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 
 * @author lkg
 * @Desc : 权限信息表
 */
@Entity()
@Table(name = "dic_frame_permission")
@DynamicUpdate(true)
public class FramePermission implements AbstractEntity<BigInteger> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4399736672828174299L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "PERMISSION_ID", nullable = false,updatable = false)
	private BigInteger permissionId;
	
	@NotBlank
	@Length(min=3,max = 64)
	@Column(length = 64, name = "PERMISSION_CODE", nullable = false, updatable = false)
	private String permissionCode;// 权限CODE

	@NotBlank
	@Length(min=2,max = 64)
	@Column(length = 64, name = "PERMISSION_NAME", nullable = false)
	private String permissionName;// 权限名称
	
	@JSONField(serialize = false)
	@NotBlank
	@Length(max = 64)
	@Column(length = 64, name = "PERMISSION_DESC", nullable = false)
	private String permissionDesc;// 权限描述
	@JSONField(format = "yyyy-MM-dd HH:mm:ss",serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期
	
	@JSONField(serialize = false)
	@NotBlank
	@Length(max = 16)
	@Column(length = 16, name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status=Status.enabled; // 
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
	}

	public BigInteger getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(BigInteger permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
}
