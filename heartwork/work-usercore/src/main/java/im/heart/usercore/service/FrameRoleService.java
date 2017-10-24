package im.heart.usercore.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import im.heart.core.service.CommonService;
import im.heart.usercore.entity.FrameRole;

/**
 * 
 * @author gg
 * @desc 角色接口
 */
public interface   FrameRoleService extends CommonService<FrameRole, BigInteger>{
	
	public static final String BEAN_NAME = "frameRoleService";
	
	
	public List<FrameRole> findAll(Sort sort);
	/**
	 * 
	 * @desc：
	 * @param roleCode
	 * @return
	 */
	public FrameRole findByRoleCode(String roleCode);
	
	/**
	 * 
	 * @desc：根据角色ID获取角色权限 标示
	 * @param roleCodes
	 * @return
	 */
	public Set<String> findRolePermissions(Iterable<String> roleCodes);
	
	/**
	 * 
	 * @desc：获取资源Code
	 * @param roleCodes
	 * @return
	 */
	public Set<String> findRoleResourceCodes(Iterable<String> roleCodes);
	/**
	 * 
	 * @desc：获取资源Id
	 * @param roleCodes
	 * @return
	 */
	public Set<BigInteger> findRoleResourceIds(Iterable<String> roleCodes);
	
	public FrameRole save(FrameRole frameRole);
	public boolean existsRoleCode(String roleCode);
}
