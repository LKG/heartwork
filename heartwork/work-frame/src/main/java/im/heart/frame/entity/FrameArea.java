package im.heart.frame.entity;

import im.heart.core.entity.TreeEntity;
import im.heart.core.enums.Status;
import im.heart.core.utils.StringUtilsEx;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 
 * @author gg
 * @desc 区域表实体类
 */
@Entity
@Table(name = "dic_frame_area")
@DynamicUpdate(true)
public class FrameArea implements TreeEntity<BigInteger>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5831319832079858641L;
	@Id
	@NotNull
	@Column(name = "AREA_CODE", nullable = false,updatable = false, length=32)
	private BigInteger code;
	
	@NotBlank(groups={})
	@Column(name = "AREA_NAME", nullable = false,length=128)
	private String name;
	@NotBlank(groups={})
	@Column(name = "SHORT_NAME",length=128,updatable = false )
	private String shortName;
	
	@JSONField (serialize=false)
	@Min(1)
	@Column(name = "LEVEL", nullable = false)
	private Integer level=1;
	
	@NotNull
	@Column(name = "PARENT_ID",length=32,nullable = false)
	private BigInteger parentId=BigInteger.ZERO;
	
	@Transient
	private String parentName="无"; //父类名称
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	private Date createTime;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	private Date modiTime;
	
	@JSONField (serialize=false)
	@Column(name = "REMARK", nullable = false, length=256)
	private String remark;
	/**
	 * //是否有子节点
	 */
	@Formula(value = "( exists(select 1 from dic_frame_area model where model.parent_id = area_code) )")
	private boolean hasChildren;
	
	
	@Column(name = "STATUS", nullable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private Status status = Status.pending;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
		if(StringUtilsEx.isBlank(shortName)){
			shortName=name;
		}
    }
	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
		if(StringUtilsEx.isBlank(shortName)){
			shortName=name;
		}
    }
	public String getParentName() {
		if(StringUtilsEx.isBlank(parentName)){
			return "无";
		}
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@JSONField (serialize=false)
	public String getSeparator() {
		return "";
	}
	
	public String makeSelfParentIds() {
		return "";
	}

	public String getParentIds() {
		return "";
	}
	public String getIcon() {
		return "";
	}
	@Override
	public boolean isRoot() {
		BigInteger parentId =this.getParentId();
		if (parentId != null&& BigInteger.ZERO.equals(parentId)) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isLeaf() {
		if (isRoot()||isHasChildren()) {
			return false;
		}
		return true;
	}
	

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public BigInteger getCode() {
		return code;
	}
	public void setCode(BigInteger code) {
		this.code = code;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}	
	public BigInteger getParentId() {
		return parentId;
	}
	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}
	public Date getModiTime() {
		return modiTime;
	}
	public void setModiTime(Date modiTime) {
		this.modiTime = modiTime;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	
}
