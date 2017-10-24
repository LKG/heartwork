package im.heart.usercore.vo;

import org.springframework.beans.BeanUtils;

import im.heart.usercore.entity.FrameOrg;

public class FrameOrgVO extends FrameOrg {
	/**
		 * 
		 */
	private static final long serialVersionUID = 2988400809352832540L;


	private Boolean isRelated = Boolean.FALSE;
	private Boolean isDefault=Boolean.FALSE;
	public FrameOrgVO(FrameOrg po){
		BeanUtils.copyProperties(po, this);
	}


	public Boolean getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(Boolean isRelated) {
		this.isRelated = isRelated;
	}


	public Boolean getIsDefault() {
		return isDefault;
	}


	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
