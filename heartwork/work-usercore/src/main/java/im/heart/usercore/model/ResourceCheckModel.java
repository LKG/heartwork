package im.heart.usercore.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ResourceCheckModel {
	private BigInteger resourceId;
	private String resourceCode;
	private List<BigInteger> permissionIds=new ArrayList<BigInteger>();//权限ids
	public BigInteger getResourceId() {
		return resourceId;
	}
	public void setResourceId(BigInteger resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public List<BigInteger> getPermissionIds() {
		return permissionIds;
	}
	public void setPermissionIds(List<BigInteger> permissionIds) {
		this.permissionIds = permissionIds;
	}
	
}
