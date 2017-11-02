package im.heart.cms.entity;



import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import im.heart.core.entity.AbstractEntity;

@Entity
@Table(name = "dic_cms_ad")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "dic_cms_ad_sequence")
public class Ad implements AbstractEntity<BigInteger>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747868433205203113L;
	/** 点击数缓存名称. */
	public static final String HITS_CACHE_NAME = "adHits";
	/** 点击数缓存更新间隔时间. */
	public static final int HITS_CACHE_INTERVAL = 600000;
	/**
	 * 类型
	 */
	public enum Type {
		/** 文本 */
		// text,
		/** 图片 */
		image,
		/** flash */
		flash,
		/** 视频，音频 */
		video,
	}

	/**
	 * 广告状态
	 * 
	 * @author hengchen
	 */
	public enum TypeOfAd {
		/** 未上架 */
		unactive,
		/** 正上架 */
		active,
		/** 已下架 */
		actived,
		/** 强制下架 */
		forceActiced,
	}

	/**
	 * 广告状态
	 *
	 * @author hengchen
	 */
	public enum AdState {
		UN_SUPPORT(-1, "未知类型"), UN_ACTIVE(0, "未上架"), ACTIVE(1, "正上架"), 
		ACTIVED(2, "已下架"), FORCE_ACTIVED(3, "已下架");/** 强制下架 */
		public int intValue;
		private final String desc;

		private AdState(int intValue, String desc) {
			this.intValue = intValue;
			this.desc = desc;
		}

		public int getIntValue() {
			return intValue;
		}

		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}

		public String getDesc() {
			return desc;
		}

		public static AdState findByIntValue(int intValue) {
			for (AdState adState : AdState.values()) {
				if (adState.intValue == intValue) {
					return adState;
				}
			}
			return UN_SUPPORT;
		}
	}

	/**
	 * 广告类型
	 * 
	 * @author wenhuiwang
	 *
	 */
	public enum ClientType {
		/** 网页端 **/
		web,
		/** 手机端 **/
		mobile
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "AD_ID", nullable = false, unique = true, updatable = false)
	private BigInteger adId;// id
	
	/** 标题 */
	@NotBlank
	@Length(min=3)
	@Column(name = "AD_TITLE", nullable = false)
	private String title;

	/** 客户端类型 */
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false,name = "CLIENT_TYPE")
	private ClientType clientType;

	/** 类型 */
	@NotNull
	@Column(name = "AD_TYPE", nullable = false)
	private Type type;

	/** 内容 */
	@NotNull
	@Column(name = "AD_CONTENT", nullable = false)
	@Lob
	private String content;

	/** 路径 */
	@NotNull
	@Column(name = "AD_PATH", nullable = false)
	private String path;

	/** 起始日期 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "START_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	/** 结束日期 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "END_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	/** 点击数 . */
	@Column(name = "HITS", nullable = false, length=20)
	private Long hits=0L;
	/** 链接地址 */
	@Length(max = 200)
	@Column(name = "URL", nullable = false, updatable = false)
	private String url;

	/** 广告位 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="POSITION_ID")//关联的表为广告位表，其主键是id
    @JSONField(serialzeFeatures={SerializerFeature.DisableCircularReferenceDetect})
	private AdPosition adPosition;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期

	@JSONField(format = "yyyy-MM-dd HH:mm:ss",serialize=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modiTime;// 修改日期

	/** 商品列表 */
	@Transient
	private String productList;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the clientType
	 */

	public ClientType getClientType() {
		return clientType;
	}

	/**
	 * @param clientType
	 *            the clientType to set
	 */
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */

	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */

	public String getPath() {
		return path;
	}

	/**
	 * 设置路径
	 * 
	 * @param path
	 *            路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	
	/**
	 * 获取链接地址
	 * 
	 * @return 链接地址
	 */

	public String getUrl() {
		return url;
	}

	/**
	 * 设置链接地址
	 * 
	 * @param url
	 *            链接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取广告位
	 * 
	 * @return 广告位
	 */

	public AdPosition getAdPosition() {
		return adPosition;
	}

	/**
	 * 设置广告位
	 * 
	 * @param adPosition
	 *            广告位
	 */
	public void setAdPosition(AdPosition adPosition) {
		this.adPosition = adPosition;
	}

	public BigInteger getAdId() {
		return adId;
	}
	public void setAdId(BigInteger adId) {
		this.adId = adId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 * 判断是否已开始
	 * 
	 * @return 是否已开始
	 */
	@Transient
	public boolean hasBegun() {
		return (getStartTime() == null) || new Date().after(getStartTime());
	}

	public Long getHits() {
		return hits;
	}
	public void setHits(Long hits) {
		this.hits = hits;
	}
	/**
	 * 判断是否已结束
	 * 
	 * @return 是否已结束
	 */
	@Transient
	public boolean hasEnded() {
		return (getEndTime() != null) && new Date().after(getEndTime());
	}

	/**
	 * @return the productList
	 */
	public String getProductList() {
		return productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(String productList) {
		this.productList = productList;
	}
}
