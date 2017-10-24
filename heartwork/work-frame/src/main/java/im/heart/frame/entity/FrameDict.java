package im.heart.frame.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc :数据字典类
 */
@Entity
@Table(name = "dic_frame_dict")
@DynamicUpdate(true)
public class FrameDict implements AbstractEntity<BigInteger> {
	
	
	public enum DictType{
		single("single","单个",1),
		multiple("multiple","多选",2),
		revisable("revisable","可变",3);
		private DictType(String code, String value, int intValue) {
			this.code = code;
			this.value = value;
			this.intValue = intValue;
		}
		public String code;
		public String value;
		public int intValue;
		public static DictType findByIntValue(int intValue) {
			for (DictType dictType : DictType.values()) {
				if (dictType.intValue == intValue) {
					return dictType;
				}
			}
			return DictType.single;
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1757647178501471325L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false,updatable = false, length=32)
	private BigInteger id;
	
	@NotBlank
	@Size(min=3,max=30)
	@Column(name = "DICT_CODE", nullable = false, updatable = false,unique=true, length = 32)
	private String dictCode;
	
	@NotBlank
	@Column(name = "DICT_NAME", nullable = false, length = 128)
	private String dictName;
	@NotBlank
	@Column(name = "DICT_VALUE", nullable = false, length = 128)
	private String dictValue;
	@Size(max=256)
	@Column(name = "DICT_DESC", length = 256)
	private String dictDesc;
	
	@NotNull
	@Column(name = "DICT_TYPE", updatable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private DictType dictType=DictType.single;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss" )
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	private Date createTime;

	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	private Date modiTime;
	
	@Formula(value = "( exists(select 1 from dic_frame_dict_item model where model.dict_id = id) )")
	private boolean hasChildren;
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
		if(DictType.multiple==dictType){
			dictValue=dictCode;
		}
    }
	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
		if(DictType.multiple==dictType){
			dictValue=dictCode;
		}
    }
	

	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getDictDesc() {
		return dictDesc;
	}
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}
	public DictType getDictType() {
		return dictType;
	}
	public void setDictType(DictType dictType) {
		this.dictType = dictType;
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
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	
}
