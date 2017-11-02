package im.heart.cms.entity;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;
import im.heart.core.enums.Status;

@Entity
@Table(name = "dic_cms_frame_notice")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "cms_frame_notice_sequence")
public class FrameNotice implements AbstractEntity<BigInteger>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8035254657730900194L;

	public enum NoticeStatus{
		pending,enabled,disabled
	}
	public enum NoticeType{
		image,music,video,unknown
	}
	public enum NoticeGroup{
		index,lgoin,unknown
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, name = "NOTICE_ID", nullable = false, unique = true, updatable = false)
	private BigInteger noticeId;
	
	@NotNull
	@Length(max = 64)
	@Column(length = 64, name = "NOTICE_TITLE", nullable = false)
	private String noticeTitle ;
	
	@NotNull
	@Length(max = 10)
	@Column(length = 10, name = "NOTICE_TYPE", nullable = false)
	private String noticeType=NoticeType.image.toString() ;
	
	@Length(max = 15)
	@Column(length = 15, name = "NOTICE_GROUP")
	private String noticeGroup=NoticeGroup.index.toString() ; 

	@NotNull
	@Length(max = 128)
	@Column(length = 128, name = "NOTICE_WORD", nullable = false)
	private String noticeWord ;//公告简介

	@NotNull
	@Column(length = 10, name = "NOTICE_SORT", nullable = false)
	private Integer noticeSort;
	
	@Column(nullable = false, name = "STATUS",length=16)
	@Enumerated(EnumType.STRING)
	private Status status = Status.pending;// 用户状态，用于表示用户状态

	@NotNull
	@Length(max = 256)
	@Column(length = 256, name = "NOTICE_ADDR", nullable = false)
	private String noticeAddr ;//公告展示宣传图片视频地址
	
	@Length(max = 256)
	@URL
	@Column(length = 256, name = "NOTICE_URL")
	private String noticeUrl="" ;//公告跳转路径
	@Length(max = 512)
	@Column(length = 512, name = "NOTICE_DESC")
	private String noticeDesc;//公告描述信息
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "MODI_TIME")
	@Temporal(TemporalType.TIMESTAMP)
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
	
	public BigInteger getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(BigInteger noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeGroup() {
		return noticeGroup;
	}

	public void setNoticeGroup(String noticeGroup) {
		this.noticeGroup = noticeGroup;
	}

	public String getNoticeWord() {
		return noticeWord;
	}

	public void setNoticeWord(String noticeWord) {
		this.noticeWord = noticeWord;
	}

	public Integer getNoticeSort() {
		return noticeSort;
	}

	public void setNoticeSort(Integer noticeSort) {
		this.noticeSort = noticeSort;
	}

	public String getNoticeAddr() {
		return noticeAddr;
	}

	public void setNoticeAddr(String noticeAddr) {
		this.noticeAddr = noticeAddr;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNoticeDesc() {
		return noticeDesc;
	}

	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
	}

	
}
