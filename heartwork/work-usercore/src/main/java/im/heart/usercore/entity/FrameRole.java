package im.heart.usercore.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc : 角色实体表
 */
@Entity()
@Table(name = "dic_frame_role")
@DynamicUpdate(true)
public class FrameRole implements AbstractEntity<BigInteger> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7020302756296345240L;
	public enum RoleType {
		SCOPE_ALL(1, "scope_all", "所有数据"), 
		SCOPE_COMPANY_AND_CHILD(2,"scope_company_and_child", "所在公司及以下数据"), 
		SCOPE_COMPANY(3,"scope_company_and_child", "所在公司数据"), 
		SCOPE_OFFICE_AND_CHILD(4,"scope_company_and_child", "所在部门及以下数据"),
		SCOPE_OFFICE(5,"scope_company_and_child", "所在部门数据"), 
		SCOPE_SELF(6,"scope_company_and_child", "仅本人数据"),
		SCOPE_CUSTOM(7,"scope_custom", "按明细设置");
		public String code;
		public int intValue;
		public final String info;
		private RoleType(int intValue, String code, String info) {
			this.code = code;
			this.intValue = intValue;
			this.info = info;
		}
		public static RoleType findByIntValue(int intValue) {
			for (RoleType roleType : RoleType.values()) {
				if (roleType.intValue == intValue) {
					return roleType;
				}
			}
			return RoleType.SCOPE_ALL;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ROLE_ID", nullable = false, unique = true, updatable = false)
	private BigInteger roleId;// 用户编号

	@NotBlank
	@Length(min=3,max=32)
	@Column(length=32, name = "ROLE_CODE" ,nullable=false, unique=true, updatable=false)
	private String roleCode;//角色标识符系统中验证时使用
	
	@NotBlank
	@Length(min=2,max=64)
	@Column(length=64, name = "ROLE_NAME" ,nullable=false)
	private String roleName;// 显示使用
	
	@Column(length=64, name = "ROLE_TYPE" ,nullable=true)
	private RoleType roleType=RoleType.SCOPE_ALL;// 角色类型 权限范围
	
	@NotBlank
	@Length(max=256)
	@Column(length=256, name = "ROLE_DESC" )
	private String roleDesc="";//角色描述信息
	
	/**
	 * //是否有子节点
	 */
	@Formula(value = "( exists(select 1 from dic_frame_role_resource model where model.role_id = role_id) )")
	private boolean hasChildren;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;//修改日期
	
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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

	public BigInteger getRoleId() {
		return roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}


}
