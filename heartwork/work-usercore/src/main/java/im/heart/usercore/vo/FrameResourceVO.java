package im.heart.usercore.vo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import im.heart.usercore.entity.FramePermission;
import im.heart.usercore.entity.FrameResource;

public class FrameResourceVO extends FrameResource{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5560379410382708216L;
	private Boolean ischecked = Boolean.FALSE;
	@JSONField(serialize = false)
	private Date modiTime;
	@JSONField (serialize=false)
	private String resourceDesc;//菜单描述信息
	
	List<FramePermissionVO> permissions=new ArrayList<FramePermissionVO>();
	
	
	public FrameResourceVO(){
	}
	public FrameResourceVO(FrameResource po){
		BeanUtils.copyProperties(po, this);
	}

	public FrameResourceVO(FrameResource po, Map<BigInteger, Set<BigInteger>> roleResourceMap){
		this(po);
		BigInteger key = po.getResourceId();
		if(StringUtils.isNotBlank(po.getPermissionStr())){
			List<FramePermission> permissionlists=JSON.parseArray(po.getPermissionStr(), FramePermission.class);
			for(FramePermission permission:permissionlists){
				FramePermissionVO vo = new FramePermissionVO(permission);
				if(roleResourceMap.containsKey(key)){
					setIschecked(Boolean.TRUE);
					Set<BigInteger> roleResourcePermissions=roleResourceMap.get(key);
					if(roleResourcePermissions.contains(permission.getPermissionId())){
						vo.setIsChecked(Boolean.TRUE);
					}
				}
				addPermission(vo);
			}
		}
	}
	public Boolean getIschecked() {
		return ischecked;
	}
	public void setIschecked(Boolean ischecked) {
		this.ischecked = ischecked;
	}

	public void addPermission(FramePermissionVO permissionVO) {
		this.permissions.add(permissionVO);
	}
	public List<FramePermissionVO> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<FramePermissionVO> permissions) {
		this.permissions = permissions;
	}

	
}
