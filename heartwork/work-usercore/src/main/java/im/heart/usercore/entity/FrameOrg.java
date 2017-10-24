package im.heart.usercore.entity;
import im.heart.core.entity.TreeEntity;
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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author gg
 * @desc 机构实体类
 */
@Entity
@Table(name = "dic_frame_org")
@DynamicUpdate(true)
public class FrameOrg implements TreeEntity<BigInteger> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2300855058921750187L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORG_ID", nullable = false, updatable = false, length = 20)
	private BigInteger id;

	@NotNull
	@Column(name = "ORG_LOGO", length = 128)
	private String logo = "";// 标志

	@NotNull
	@Column(name = "AREA_CODE", nullable = false, length = 32)
	private String areaCode;// 地区CODE

	@NotNull
	@Length(min=2, max=32)
	@Column(name = "ORG_CODE", nullable = false, updatable = false, length = 32)
	private String orgCode;// 机构号

	@NotBlank(groups = {})
	@Length(min=2, max=128)
	@Column(name = "ORG_NAME", nullable = false, length = 128)
	private String name;// 机构名称

	@Column(name = "ORG_ADDRESS", length = 128)
	private String address;// 机构地址

	@Column(name = "ORG_CONTACTS", length = 64)
	private String contacts;// 联系人

	@Column(name = "TELE_PHONE", length = 64)
	private String telePhone;// 联系人电话 固话

	@NotBlank(groups = {})
	@Column(name = "MOBILE_PHONE", length = 64)
	private String mobilePhone;// 联系人电话

	@NotBlank(groups = {})
	@Column(name = "ORG_TYPE", updatable = false, nullable = false, length = 32)
	private String type;// 机构类型

	@Column(name = "ORG_CATEGORY", length = 128)
	private String category;// 机构分类

	@Column(name = "STATUS", nullable = false, length = 16)
	@Enumerated(EnumType.STRING)
	private Status status = Status.pending;

	@JSONField(serialize = false)
	@Min(1)
	@Column(name = "LEVEL", nullable = false)
	private Integer level = 1;
	@NotNull
	@Column(name = "PARENT_ID", length = 20, nullable = false)
	private BigInteger parentId = BigInteger.ZERO;

	@Column(name = "parent_ids")
	private String parentIds="0";
	
	@JSONField(serialize = false)
	@Transient
	private String parentName = "无"; // 父类名称
	/**
	 * //是否有子节点
	 */
	@Formula(value = "( exists(select 1 from dic_frame_org model where model.parent_id = org_id) )")
	private boolean hasChildren;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false, name = "CREATE_TIME", updatable = false)
	private Date createTime;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false, name = "MODI_TIME")
	private Date modiTime;

	@JSONField(serialize = false)
	@Column(name = "REMARK", nullable = false, length = 256)
	private String remark;

	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BigInteger getParentId() {
		return parentId;
	}

	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Override
	public boolean isRoot() {
		BigInteger parentId = this.getParentId();
		if (parentId != null && BigInteger.ZERO.equals(parentId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isLeaf() {
		if (isRoot() || isHasChildren()) {
			return false;
		}
		return true;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public String getIcon() {
		return this.logo;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
