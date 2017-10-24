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

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc : 机构关联表，设置机构非直属关联关系 
 */

@Entity()
@Table(name = "dic_frame_org_relate")
@DynamicUpdate(true)
public class FrameOrgRelate implements AbstractEntity<BigInteger> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4462546725686348247L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, name = "RELATE_ID", nullable = false, unique = true, updatable = false)
	private BigInteger relateId;// 关联Id 不序列化

	@NotNull
	@Column(length = 32, name = "ORG_ID", nullable = false, updatable = false)
	private BigInteger orgId;// 资源Id
	
	@Column(length = 32, name = "RELATE_TYPE", nullable = false, updatable = false)
	private String relateType;// 关联类型
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="RELATE_ORG_ID")//关联的表为org表，其主键是id
    @JSONField(serialzeFeatures={SerializerFeature.DisableCircularReferenceDetect})
	private FrameOrg relateOrg;
	
	@JSONField(serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期 不序列化
	
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

	public BigInteger getOrgId() {
		return orgId;
	}

	public void setOrgId(BigInteger orgId) {
		this.orgId = orgId;
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
	public String getRelateType() {
		return relateType;
	}
	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}
	public FrameOrg getRelateOrg() {
		return relateOrg;
	}
	public void setRelateOrg(FrameOrg relateOrg) {
		this.relateOrg = relateOrg;
	}
	
}
