package im.heart.frame.entity;


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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.TreeEntity;
import im.heart.core.utils.StringUtilsEx;
/**
 * 
 * @author gg
 * @Desc : 数据字典明细表
 */
@Entity()
@Table(name = "dic_frame_dict_item")
@DynamicUpdate(true)
public class FrameDictItem implements TreeEntity<BigInteger> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2229611729154574422L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ITEM_ID", nullable = false, unique = true, updatable = false)
	private BigInteger itemId;
	
	@Column(name = "ITEM_CODE", nullable = false,length=64)
	private String itemCode;
	
	@Column(name = "ITEM_NAME", nullable = false,length=64)
	private String itemName;
	
	@Column(name = "ITEM_VALUE", nullable = false,length=64)
	private String itemValue;
	
	@Column(name = "DICT_Id", nullable = false,length=32)
	private BigInteger dictId;
	@Column(name = "DICT_CODE", nullable = false,length=64)
	private String dictCode;
	
	@Column(name = "ITEM_DESC", nullable = false,length=512)
	private String itemDesc;
	
	@Column(length = 20, name = "PARENT_ID",nullable = false)
	private BigInteger parentId=BigInteger.ZERO;// 父类Id
	
	@Transient
	private String parentName="无"; //父类名称
	
	@JSONField (serialize=false)
	@Min(1)
	@Column(name = "LEVEL", nullable = false)
	private Integer level=1;
	
	@NotNull
	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	private Date createTime;

	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME" ,updatable = false)
	private Date modiTime;
	
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		if(parentId==null){
			parentId=BigInteger.ZERO;
		}
		modiTime = new Date();
    }
	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
    }
	@Formula(value = "( exists(select 1 from dic_frame_dict_item model where model.parent_id = item_id) )")
	private boolean hasChildren;
	

	public String getSeparator() {
		return "/";
	}


	public String makeSelfParentIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigInteger getParentId() {
		return parentId;
	}

	public String getParentIds() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	
	public String getIcon() {
		return "";
	}

	
	public boolean isRoot() {
		BigInteger parentId =this.getParentId();
		if (parentId != null&& BigInteger.ZERO.equals(parentId)) {
			return true;
		}
		return false;
	}


	public boolean isLeaf() {
		if (isRoot()) {
			return false;
		}
		if (isHasChildren()) {
			return false;
		}
		return true;
	}


	public BigInteger getItemId() {
		return itemId;
	}

	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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

	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
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
	public Date getModiTime() {
		return modiTime;
	}
	public void setModiTime(Date modiTime) {
		this.modiTime = modiTime;
	}
	public BigInteger getDictId() {
		return dictId;
	}
	public void setDictId(BigInteger dictId) {
		this.dictId = dictId;
	}
	
	
}
