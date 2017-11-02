package im.heart.cms.entity;

import im.heart.core.entity.AbstractEntity;
import im.heart.core.utils.FreeMarkerUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
/***
 * 
 * @author lkg
 * @Desc 文章实体类
 */
@Entity
@Table(name = "dic_cms_article")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_article_sequence")
public class Article implements AbstractEntity<BigInteger>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3901099490416617775L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Article.class);

	/** 点击数缓存名称. */
	public static final String HITS_CACHE_NAME = "articleHits";
	/** 点击数缓存更新间隔时间. */
	public static final int HITS_CACHE_INTERVAL = 600000;

	/** 内容分页长度 . */
	private static final int PAGE_CONTENT_LENGTH = 800;

	/** 内容分页符 . */
	private static final String PAGE_BREAK_SEPARATOR = "<hr class=\"pageBreak\" />";

	/** 段落分隔符配比. */
	private static final Pattern PARAGRAPH_SEPARATOR_PATTERN = Pattern.compile("[,;\\.!?，；。！？]");

	/** 静态路径. */
	private static String staticPath="";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "ID", nullable = false, unique = true, updatable = false)
	private BigInteger id;// id
	/** 标题 . */
	@NotEmpty
	@NotBlank
	@Length(min=1,max = 200)
	@Column(name = "TITLE", nullable = false,updatable = false, length=200)
	private String title;

	/** 作者. */
	@Length(max = 200)
	@Column(name = "AUTHOR", nullable = false,updatable = false)
	private String author="";

	/** 内容 . */
	@NotBlank
	@Length(min=3)
	@Column(name = "CONTENT", nullable = false)
	private String content;

	/** 页面标题. */
	@Column(name = "SEO_TITLE", nullable = false)
	@Length(max = 200)
	private String seoTitle="";

	/** 页面关键词. */
	@Length(max = 200)
	@Column(name = "SEO_KEYWORDS", nullable = false)
	private String seoKeywords="";

	/** 页面描述 . */
	@Length(max = 200)
	@Column(name = "SEO_DESCRIPTION", nullable = false)
	private String seoDescription="";

	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "TYPE", nullable = false)
	private String type;
	/** 是否发布. */
	@NotNull
	@Column(name = "IS_PUB", nullable = false)
	private Boolean isPub=Boolean.FALSE;

	/** 是否置顶. */
	@NotNull
	@Column(name = "IS_TOP",nullable = false)
	private Boolean isTop=Boolean.FALSE;

	/** 点击数 . */
	@Column(name = "HITS", nullable = false, length=20)
	private Long hits=0L;
	
	@Column(name = "ALLOW_COMMENT")
	private boolean allowComment=Boolean.FALSE;
	
	@Column(name = "RATE_TIMES")
	private BigInteger rateTimes=BigInteger.ZERO;
	
	@Column(length = 32, name = "USER_ID", nullable = false, updatable = false)
	private BigInteger userId;
	/** 页码 . */
	@Transient
	private Integer pageNumber;
	
	@Column(name = "CATEGORY_ID", nullable = false, length=20)
	private BigInteger categoryId;// categoryId
	
	@Formula(value = "(select model.name from dic_cms_article_category model where model.id = category_id)")
	private String categoryName;//

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "PUSH_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pushTime; // 发布日期
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss",serialize=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;// 修改日期
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
		modiTime = new Date();
		pushTime = new Date();
    }
	@PreUpdate
	protected void onUpdate() {
		modiTime = new Date();
		if(isPub){
			pushTime = new Date();
		}
    }
	/**
	 * 获取内容.
	 * 
	 * @return 内容
	 */

	public String getContent() {
		if (pageNumber != null) {
			String[] pageContents = getPageContents();
			if (pageNumber < 1) {
				pageNumber = 1;
			}
			if (pageNumber > pageContents.length) {
				pageNumber = pageContents.length;
			}
			return pageContents[pageNumber - 1];
		} else {
			return content;
		}
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 设置内容.
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取页面标题.
	 * 
	 * @return 页面标题
	 */

	public String getSeoTitle() {
		return seoTitle;
	}

	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	/**
	 * 设置页面标题.
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词.
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词.
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		if (seoKeywords != null) {
			seoKeywords = seoKeywords.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */

	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Boolean getIsPub() {
		return isPub;
	}

	public void setIsPub(Boolean isPub) {
		this.isPub = isPub;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取点击数
	 * 
	 * @return 点击数
	 */

	public Long getHits() {
		return hits;
	}

	/**
	 * 设置点击数
	 * 
	 * @param hits
	 *            点击数
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
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

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", getId());
		model.put("createTime", getCreateTime());
		model.put("modiTime", getModiTime());
		model.put("title", getTitle());
		model.put("seoTitle", getSeoTitle());
		model.put("seoKeywords", getSeoKeywords());
		model.put("seoDescription", getSeoDescription());
		model.put("pageNumber", getPageNumber());
		try {
			return FreeMarkerUtils.renderString(staticPath, model);
		} catch (Exception e) {
			LOGGER.warn("[Article]-IOException " + e.getMessage());
		}
		return null;
	}

	public BigInteger getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(BigInteger categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取文本内容
	 * 
	 * @return 文本内容
	 */
	@Transient
	public String getText() {
		if (getContent() != null) {
			return Jsoup.parse(getContent()).text();
		}
		return null;
	}

	public boolean isAllowComment() {
		return allowComment;
	}
	public void setAllowComment(boolean allowComment) {
		this.allowComment = allowComment;
	}
	public BigInteger getRateTimes() {
		return rateTimes;
	}
	public void setRateTimes(BigInteger rateTimes) {
		this.rateTimes = rateTimes;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	/**
	 * 获取分页内容
	 * 
	 * @return 分页内容
	 */
	@Transient
	public String[] getPageContents() {
		if (StringUtils.isEmpty(content)) {
			return new String[] { "" };
		}
		if (content.contains(PAGE_BREAK_SEPARATOR)) {
			return content.split(PAGE_BREAK_SEPARATOR);
		} else {
			List<String> pageContents = new ArrayList<String>();
			Document document = Jsoup.parse(content);
			List<Node> children = document.body().childNodes();
			if (children != null) {
				int textLength = 0;
				StringBuffer html = new StringBuffer();
				for (Node node : children) {
					if (node instanceof Element) {
						Element element = (Element) node;
						html.append(element.outerHtml());
						textLength += element.text().length();
						if (textLength >= PAGE_CONTENT_LENGTH) {
							pageContents.add(html.toString());
							textLength = 0;
							html.setLength(0);
						}
					} else if (node instanceof TextNode) {
						TextNode textNode = (TextNode) node;
						String text = textNode.text();
						String[] contents = PARAGRAPH_SEPARATOR_PATTERN
								.split(text);
						Matcher matcher = PARAGRAPH_SEPARATOR_PATTERN
								.matcher(text);
						for (String content2 : contents) {
							String contentTemp = content2;
							if (matcher.find()) {
								contentTemp += matcher.group();
							}
							html.append(contentTemp);
							textLength += contentTemp.length();
							if (textLength >= PAGE_CONTENT_LENGTH) {
								pageContents.add(html.toString());
								textLength = 0;
								html.setLength(0);
							}
						}
					}
				}
				String pageContent = html.toString();
				if (StringUtils.isNotEmpty(pageContent)) {
					pageContents.add(pageContent);
				}
			}
			return pageContents.toArray(new String[pageContents.size()]);
		}
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	@Transient
	public int getTotalPages() {
		return getPageContents().length;
	}

	@Transient
	public String getShortTitle() {
		if (!StringUtils.isBlank(title) && (title.length() > 14)) {
			return title.substring(0, 14) + "...";
		}
		return title;
	}

}
