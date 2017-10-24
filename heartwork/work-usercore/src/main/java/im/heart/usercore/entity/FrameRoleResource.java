package im.heart.usercore.entity;

import im.heart.core.entity.AbstractEntity;
import im.heart.core.utils.StringUtilsEx;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author gg
 * @Desc : 角色资源关联表
 */
@Entity()
@Table(name = "dic_frame_role_resource")
public class FrameRoleResource implements AbstractEntity<BigInteger> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6778091815595393868L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JSONField(serialize = false)
	@Column(length = 20, name = "RELATE_ID", nullable = false, unique = true, updatable = false)
	private BigInteger relateId;// 关联Id 不序列化

	@NotNull
	@Column(length = 20, name = "RESOURCE_ID", nullable = false, updatable = false)
	private BigInteger resourceId;// 资源Id
	
	@NotBlank
	@Column(length = 32, name = "RESOURCE_CODE", nullable = false, updatable = false)
	private String resourceCode;// 资源编号
	
	@NotNull
	@Column(length = 20, name = "ROLE_ID", nullable = false, updatable = false)
	private BigInteger roleId;// 角色Id
	
	@Length(max = 32)
	@Column(length = 32, name = "ROLE_CODE", nullable = false, updatable = false)
	private String roleCode;// 角色编号
	
	@NotBlank
	@Length(max=512)
	@Column(length=512, name = "PERMISSION_IDS" )
	private String permissionIds;//权限ids
	
	@JSONField(serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期 不序列化
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
	}
	@JSONField(serialize = false)
	@Transient
	Set<BigInteger> permissions=new HashSet<BigInteger>();
	public Set<BigInteger> getPermissions() {
		if(StringUtilsEx.isNotBlank(permissionIds)){
			String[] ids = StringUtilsEx.split(permissionIds,",");
			for(String permissionId:ids){
				this.permissions.add(new BigInteger(permissionId));
			}
		}
		return permissions;
	}

	public BigInteger getRoleId() {
		return roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public void setPermissions(Set<BigInteger> permissions) {
		this.permissions = permissions;
	}

	public BigInteger getRelateId() {
		return relateId;
	}

	public void setRelateId(BigInteger relateId) {
		this.relateId = relateId;
	}

	public BigInteger getResourceId() {
		return resourceId;
	}

	public void setResourceId(BigInteger resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
