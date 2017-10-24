package im.heart.usercore.model;

import java.math.BigInteger;

public class CheckModel {
	private BigInteger id;
	private Boolean isDefault=Boolean.FALSE;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
}