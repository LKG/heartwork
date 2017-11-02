
package im.heart.cms.entity;

import im.heart.core.entity.TreeEntity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文章分类
 * @author lkg
 *
 */
@Entity
@Table(name = "dic_cms_article_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_article_category_sequence")
public class ArticleCategory implements TreeEntity<BigInteger>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3870304630493086027L;

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ID", nullable = false, unique = true, updatable = false)
	private BigInteger id;// id
	
	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false,name="NAME")
	private String name;

	/** 页面标题. */
	@Column(name = "SEO_TITLE", nullable = false)
	private String seoTitle;

	/** 页面关键词. */
	@Length(max = 200)
	@Column(name = "SEO_KEYWORDS", nullable = false)
	private String seoKeywords;

	/** 页面描述 . */
	@Length(max = 200)
	@Column(name = "SEO_DESCRIPTION", nullable = false)
	private String seoDescription;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss",serialize=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期

	@JSONField(format = "yyyy-MM-dd HH:mm:ss",serialize=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;// 修改日期
	/** 层级 */
	@JSONField (serialize=false)
	@Column(name = "LEVEL", nullable = false)
	private Integer level;

	@Formula(value = "(select count(*) from dic_cms_article_category model where model.parent_id = id)")
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
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getSeparator() {
		return TREE_PATH_SEPARATOR;
	}
	
	public String makeSelfParentIds() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getParentIds() {
		// TODO Auto-generated method stub
		return null;
	}


	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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
	public BigInteger getParentId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isHasChildren() {
		// TODO Auto-generated method stub
		return false;
	}
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isRoot() {
		if (this.getParentId() != null&& BigInteger.ZERO.equals(this.getParentId())) {
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
}
