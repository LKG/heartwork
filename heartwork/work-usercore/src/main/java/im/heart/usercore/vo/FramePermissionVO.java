package im.heart.usercore.vo;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.annotation.JSONField;

import im.heart.core.enums.Status;
import im.heart.usercore.entity.FramePermission;

public class FramePermissionVO extends FramePermission {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3553390353178815126L;
	
	private Boolean isChecked = Boolean.FALSE;
	
	@JSONField(serialize = false)
	private String permissionDesc;// 权限描述
	@JSONField(serialize = false)
	private Date createTime; // 创建日期
	@JSONField(serialize = false)
	private Status status = Status.enabled; //
	public FramePermissionVO(){
	}
	public FramePermissionVO(FramePermission po){
		BeanUtils.copyProperties(po, this);
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
