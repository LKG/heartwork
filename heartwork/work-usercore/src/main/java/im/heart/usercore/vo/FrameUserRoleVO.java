package im.heart.usercore.vo;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.usercore.entity.FrameRole;

public class FrameUserRoleVO extends  FrameRole {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6662998853899204880L;

	@JSONField (serialize=false)
	private String roleDesc;//角色描述信息
	
	@JSONField (serialize=false)
	private Date createTime;
	
	@JSONField (serialize=false)
	private Date modiTime;//修改日期
	
	private BigInteger userId;// 用户编号
	private String userName;// 用户账号
	
	private Boolean hasRole=Boolean.FALSE;
	public FrameUserRoleVO(FrameRole po){
		BeanUtils.copyProperties(po, this);
	}
	public Boolean getHasRole() {
		return hasRole;
	}
	public void setHasRole(Boolean hasRole) {
		this.hasRole = hasRole;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
