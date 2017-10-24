package im.heart.usercore.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc : 用户关系表
 */
@Entity()
@Table(name = "dic_frame_user_relate")
@DynamicUpdate(true)
public class FrameUserRelate implements AbstractEntity<BigInteger> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9013579269873331336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 32, name = "RELATE_ID", nullable = false, unique = true, updatable = false)
	private BigInteger relateId;// 关联Id 不序列化

	@NotNull
	@Column(length = 32, name = "USER_ID", nullable = false, updatable = false)
	private BigInteger userId;// userId
	
	@Column(length = 32, name = "RELATE_TYPE", nullable = false, updatable = false)
	private String relateType;// 关联类型
	
	@NotNull
	@Column(length = 32, name = "RELATE_user_ID", nullable = false, updatable = false)
	private BigInteger relateUserId;// 关联机构ID
	
	@JSONField(serialize = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期 不序列化
	
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


}
