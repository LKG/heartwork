package im.heart.usercore.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.usercore.model.ResourceCheckModel;
import im.heart.usercore.entity.FrameRole;
import im.heart.usercore.entity.FrameRoleResource;
import im.heart.usercore.repository.FrameRoleResourceRepository;
import im.heart.usercore.service.FrameRoleResourceService;

@Service(value = FrameRoleResourceService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameRoleResourceServiceImpl extends CommonServiceImpl<FrameRoleResource, BigInteger> implements FrameRoleResourceService{
	@Autowired
	private FrameRoleResourceRepository frameRoleResourceRepository;
	@Override
	public List<FrameRoleResource> findByRoleCodes(Iterable<String> roleCodes) {
		List<FrameRoleResource> roleResources=new ArrayList<FrameRoleResource>();
		for(String roleCode:roleCodes){
			List<FrameRoleResource> resources=this.frameRoleResourceRepository.findByRoleCode(roleCode);
			roleResources.addAll(resources);
		}
		return roleResources;
	}
	 
	@Override
	public Map<BigInteger,Set<BigInteger>> findResourceMapByRoleCode(String roleCode) {
		Map<BigInteger,Set<BigInteger>> roleResourceMap=Maps.newHashMap();
		List<FrameRoleResource> roleResources=this.frameRoleResourceRepository.findByRoleCode(roleCode);
		for(FrameRoleResource roleResource:roleResources){
			roleResourceMap.put(roleResource.getResourceId(), roleResource.getPermissions());
		}
		return roleResourceMap;
	}
	
	@Override
	public List<FrameRoleResource> findByRoleCode(String roleCode) {
		List<FrameRoleResource> roleResources=this.frameRoleResourceRepository.findByRoleCode(roleCode);
		return roleResources;
	}
	@Override
	public List<FrameRoleResource> findByRoleCodes(String... roleCodes) {
		List<FrameRoleResource> roleResources=new ArrayList<FrameRoleResource>();
		for(String roleCode:roleCodes){
			List<FrameRoleResource> resources=this.frameRoleResourceRepository.findByRoleCode(roleCode);
			roleResources.addAll(resources);
		}
		return roleResources;
	}
	
	/**
	 *  必须更新所有的权限信息
	 */
	@Override
	public List<FrameRoleResource> saveRoleResource(FrameRole role,List<ResourceCheckModel> checkBoxModels) {
		this.frameRoleResourceRepository.deleteByRoleId(role.getRoleId());
		List<FrameRoleResource>  entities=Lists.newArrayList();
		for(ResourceCheckModel checkBoxModel:checkBoxModels){
			BigInteger resourceId = checkBoxModel.getResourceId();
			String resourceCode=checkBoxModel.getResourceCode();
			List<BigInteger> permissionIds = checkBoxModel.getPermissionIds();
			FrameRoleResource entitie=new FrameRoleResource();
			entitie.setResourceCode(resourceCode);
			entitie.setResourceId(resourceId);
			if(permissionIds!=null&&!permissionIds.isEmpty()){
				entitie.setPermissionIds(StringUtils.join(permissionIds,","));
			}
			entitie.setRoleId(role.getRoleId());
			entitie.setRoleCode(role.getRoleCode());
			entities.add(entitie);
		}
		return this.frameRoleResourceRepository.save(entities);
	}

	
}
