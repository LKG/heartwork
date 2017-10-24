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

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.entity.AbstractEntity;

/**
 * 
 * @author gg
 * @Desc : 用户角色关联表
 */
@Entity()
@Table(name = "dic_frame_user_role")
public class FrameUserRole implements AbstractEntity<BigInteger> {

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

	@NotNull
	@Column(length = 32, name = "ROLE_CODE", nullable = false, updatable = false)
	private String roleCode;// 角色编号
	@JSONField(serialize=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_TIME", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 创建日期
	
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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
