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
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc :模板类
 */
@Entity
@Table(name = "dic_frame_tpl")
@DynamicUpdate(true)
public class FrameTpl implements AbstractEntity<BigInteger> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8169958781949944449L;
	public enum TplType{
		page("page","页面模板",1),
		mail("mail","邮件模板",2),
		word("word","word模板",3),
		excel("excel","excel模板",4),
		print("print","打印模板",5);
		private TplType(String code, String value, int intValue) {
			this.code = code;
			this.value = value;
			this.intValue = intValue;
		}
		public String code;
		public String value;
		public int intValue;
		public static TplType findByIntValue(int intValue) {
			for (TplType tplType : TplType.values()) {
				if (tplType.intValue == intValue) {
					return tplType;
				}
			}
			return TplType.page;
		}
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TPL_ID", nullable = false,updatable = false, length=32)
	private BigInteger id;
	
	@NotBlank
	@Size(min=3,max=30)
	@Column(name = "TPL_CODE", nullable = false, updatable = false,unique=true, length = 32)
	private String tplCode;
	
	@NotBlank
	@Column(name = "TPL_NAME", nullable = false, length = 128)
	private String tplName;
	
	@NotBlank
	@Column(name = "TPL_TYPE", nullable = false, updatable = false, length = 32)
	private String tplType;
	
	@Column(name = "TPL_DESC", length = 256)
	private String tplDesc;
	
	/** 模板文件路径 */
	@Column(name = "TPL_PATH", length = 128)
	private String tplPath="";
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss" )
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "CREATE_TIME" ,updatable = false)
	private Date createTime;

	@JSONField (format="yyyy-MM-dd HH:mm:ss" ,serialize=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable=false, name = "MODI_TIME")
	private Date modiTime;
	
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
    }
	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
    }
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getTplCode() {
		return tplCode;
	}
	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}
	public String getTplDesc() {
		return tplDesc;
	}
	public void setTplDesc(String tplDesc) {
		this.tplDesc = tplDesc;
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
	public String getTplType() {
		return tplType;
	}
	public void setTplType(String tplType) {
		this.tplType = tplType;
	}
	public String getTplPath() {
		return tplPath;
	}
	public void setTplPath(String tplPath) {
		this.tplPath = tplPath;
	}
}
