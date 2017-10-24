package im.heart.usercore.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc : 用户机构关联表
 */
@Entity()
@Table(name = "dic_frame_user_org")
@DynamicUpdate(true)
@DynamicInsert(true)
public class FrameUserOrg implements AbstractEntity<BigInteger> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3614248751144766845L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20, name = "RELATE_ID", nullable = false, unique = true, updatable = false)
	private BigInteger relateId;// 关联id编号
	@NotNull
	@Column(length = 20, name = "USER_ID", nullable = false, updatable = false)
	private BigInteger userId;// 用户id编号

    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_ID")//关联的表为org表，其主键是id
    @JSONField(serialzeFeatures={SerializerFeature.DisableCircularReferenceDetect})
	private FrameOrg relateOrg;

	@JSONField(serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期
	
	@Column(name = "IS_DEFAULT", nullable = false,length=2)
	private Boolean isDefault=Boolean.FALSE;
	@PrePersist
	protected void onCreate() {
		createTime = new Date();
	}

	public BigInteger getRelateId() {
		return relateId;
	}

	public void setRelateId(BigInteger relateId) {
		this.relateId = relateId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public FrameOrg getRelateOrg() {
		return relateOrg;
	}

	public void setRelateOrg(FrameOrg relateOrg) {
		this.relateOrg = relateOrg;
	}

}
