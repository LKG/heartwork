package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import im.heart.core.service.CommonService;
import im.heart.usercore.model.ResourceCheckModel;
import im.heart.usercore.entity.FrameRole;
import im.heart.usercore.entity.FrameRoleResource;

/**
 * 
 * @author gg
 * @desc : 角色资源关联表 Service
 */
public interface   FrameRoleResourceService extends CommonService<FrameRoleResource, BigInteger>{
	
	public static final String BEAN_NAME = "frameRoleResourceService";
	
	public List<FrameRoleResource> findByRoleCodes(Iterable<String> roleCodes);
	public List<FrameRoleResource> findByRoleCodes(String ... roleCodes);
	public List<FrameRoleResource> findByRoleCode(String roleCode);
	public Map<BigInteger, Set<BigInteger>> findResourceMapByRoleCode(String roleCode);

	public List<FrameRoleResource>  saveRoleResource(FrameRole role,List<ResourceCheckModel> entities);
}
