package im.heart.usercore.vo;

import org.springframework.beans.BeanUtils;

import im.heart.usercore.entity.FrameOrg;

public class FrameOrgRelateVO extends FrameOrg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -110106214414914088L;
	private Boolean isDefault=Boolean.FALSE;
	
	private Boolean isRelated=Boolean.FALSE;
	
	public FrameOrgRelateVO(FrameOrg po){
		BeanUtils.copyProperties(po, this);
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(Boolean isRelated) {
		this.isRelated = isRelated;
	}



}
