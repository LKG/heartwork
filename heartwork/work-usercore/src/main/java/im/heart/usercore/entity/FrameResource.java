package im.heart.usercore.entity;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.TreeEntity;
import im.heart.core.enums.Status;
import im.heart.core.utils.StringUtilsEx;

/**
 * 
 * @author gg
 * @Desc : 资源表
 */
@Entity()
@Table(name = "dic_frame_resource")
@DynamicUpdate(true)
@DynamicInsert(true)
public class FrameResource implements TreeEntity<BigInteger>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8658734097434305667L;
	public enum ResourceType {
		menu(1, "menu", "菜单"), button(2,"button", "按钮"),url(3, "url", "外部Url");
		public String code;
		public int intValue;
		public final String info;
		private ResourceType(int intValue, String code, String info) {
			this.code = code;
			this.intValue = intValue;
			this.info = info;
		}
		public static ResourceType findByIntValue(int intValue) {
			for (ResourceType resourceType : ResourceType.values()) {
				if (resourceType.intValue == intValue) {
					return resourceType;
				}
			}
			return ResourceType.menu;
		}
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "RESOURCE_ID", nullable = false,updatable = false)
	private BigInteger resourceId;
	
	@NotBlank
	@Length(min=3,max = 32)
	@Column(length = 32, name = "RESOURCE_CODE", nullable = false,unique = true,  updatable = false)
	private String resourceCode;// 资源Code标识符

	@NotBlank
	@Length(min=2,max = 64)
	@Column(length = 64, name = "RESOURCE_NAME", nullable = false)
	private String resourceName;// 资源名称

	@Column(length = 16, name = "RESOURCE_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private ResourceType resourceType=ResourceType.menu; // 资源类型

	@NotBlank
	@Length(max = 128)
	@Column(length = 128, name = "RESOURCE_URL", nullable = false)
	private String resourceUrl;// 资源路径

	@NotBlank
	@Length(max = 32)
	@Column(length = 32, name = "RESOURCE_TARGET", nullable = false)
	private String resourceTarget;// 菜单target
		
	@NotBlank
	@Length(max = 256)
	@Column(length = 256, name = "RESOURCE_DESC", nullable = false)
	private String resourceDesc;// 菜单描述
	
	@NotBlank
	@Length(max = 128)
	@Column(length = 128, name = "RESOURCE_ICON", nullable = false)
	private String icon;// 菜单图标
	@Min(0)
	@Column(name = "RESOURCE_SORT", nullable = false)
	private Integer resourceSort=0;// 排序号
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; //创建日期

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;//修改日期
	
	@NotNull
	@Column(length = 16, name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status =Status.pending;// 状态

	@NotNull
	@Column(length = 32, name = "PARENT_ID", nullable = false)
	private BigInteger parentId=BigInteger.ZERO;// 父类Id
	
	@Formula(value = "(select model.resource_name from dic_frame_resource model where model.resource_id = parent_id)")
	private String parentName; //父类名称
	
	@Column(length = 128, name = "PERMISSION_IDS")
	private String permissionIds;//
	
	@JSONField(serialize=false)
	@Column(length = 512, name = "PERMISSIONS")
	private String permissionStr;
	
//	@Transient
//	List<FramePermissionVO> permissions=new ArrayList<FramePermissionVO>();
//	public List<FramePermissionVO> getPermissions() {
//		if(StringUtilsEx.isNotBlank(permissionStr)){
//			return JSON.parseArray(permissionStr, FramePermissionVO.class);
//		}
//		return permissions;
//	}
	@Column(length = 256, name = "PARENT_IDS")
	private String parentIds;// 父类Id
		
	/**
	 * //是否有子节点
	 */
	@Formula(value = "( exists(select 1 from dic_frame_resource model where model.parent_id = resource_id) )")
	private boolean hasChildren;
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
	}

	public String getSeparator() {
		return "/";
	}

	public String getParentName() {
		if(StringUtilsEx.isBlank(parentName)){
			return "root";
		}
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String makeSelfParentIds() {
		return getParentIds() + getResourceId() + getSeparator();
	}

	public BigInteger getParentId() {
		return parentId;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	
	public String getParentIds() {
		return parentIds;
	}

	@Override
	public boolean isHasChildren() {
		return hasChildren;
	}

	public String getIcon() {
		return icon;
	}

	@Override
	public boolean isRoot() {
		if (this.getParentId() != null
				&& this.getParentId().toString().equals("0")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isLeaf() {
		if (isRoot()) {
			return false;
		}
		if (isHasChildren()) {
			return false;
		}
		return true;
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

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public BigInteger getResourceId() {
		return resourceId;
	}

	public void setResourceId(BigInteger resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public Integer getResourceSort() {
		return resourceSort;
	}

	public void setResourceSort(Integer resourceSort) {
		this.resourceSort = resourceSort;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceTarget() {
		return resourceTarget;
	}

	public void setResourceTarget(String resourceTarget) {
		this.resourceTarget = resourceTarget;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public String getPermissionStr() {
		return permissionStr;
	}

	public void setPermissionStr(String permissionStr) {
		this.permissionStr = permissionStr;
	}
}
