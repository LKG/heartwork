package im.heart.usercore.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.core.utils.StringUtilsEx;
import im.heart.usercore.entity.FramePermission;
import im.heart.usercore.entity.FrameRole;
import im.heart.usercore.entity.FrameRoleResource;
import im.heart.usercore.repository.FramePermissionRepository;
import im.heart.usercore.repository.FrameRoleRepository;
import im.heart.usercore.service.FrameRoleResourceService;
import im.heart.usercore.service.FrameRoleService;

@Service(value = FrameRoleService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameRoleServiceImpl extends CommonServiceImpl<FrameRole, BigInteger> implements FrameRoleService {
	
	protected static final Logger logger = LoggerFactory.getLogger(FrameRoleServiceImpl.class);
	@Autowired
	private FrameRoleRepository frameRoleRepository;
	
	@Autowired
	private FrameRoleResourceService frameRoleResourceService;
	
	@Autowired
	private FramePermissionRepository framePermissionRepository;
/*	@Override
	public List<FrameRole> findAll() {
		return this.frameRoleRepository.findAll();
	}*/
	@Override
	public List<FrameRole> findAll(Sort sort) {
		return this.frameRoleRepository.findAll(sort);
	}
	@Override
	public Set<String> findRolePermissions(Iterable<String> roleCodes) {
		Set<String> permissions=new HashSet<String>();
		List<FrameRoleResource> roleResources=this.frameRoleResourceService.findByRoleCodes(roleCodes);
		for(FrameRoleResource roleResource: roleResources){
			Set<BigInteger> ids = roleResource.getPermissions();
			String resourceCode=roleResource.getResourceCode();
			if(ids!=null&&!ids.isEmpty()){
				List<FramePermission> framePermissions = this.framePermissionRepository.findAll(ids);
				for(FramePermission framePermission:framePermissions){
					permissions.add(resourceCode+":"+framePermission.getPermissionCode());
				}
			}
		}
		return permissions;
	}

	@Override
	public Set<String> findRoleResourceCodes(Iterable<String> roleCodes) {
		Set<String> resources=new HashSet<String>();
		List<FrameRoleResource> roleResources=this.frameRoleResourceService.findByRoleCodes(roleCodes);
		for(FrameRoleResource roleResource: roleResources){
			resources.add(roleResource.getResourceCode());
		}
		return resources;
	}
	@Override
	public Set<BigInteger> findRoleResourceIds(Iterable<String> roleCodes) {
		Set<BigInteger> resourceIds=new HashSet<BigInteger>();
		List<FrameRoleResource> roleResources=this.frameRoleResourceService.findByRoleCodes(roleCodes);
		for(FrameRoleResource roleResource: roleResources){
			resourceIds.add(roleResource.getResourceId());
		}
		return resourceIds;
	}
	
	@Override
	public FrameRole save(FrameRole entity) {
		if(entity.getRoleId()==null){
			String roleCode=entity.getRoleCode();
			if(StringUtilsEx.isBlank(roleCode)||this.existsRoleCode(roleCode)){
				logger.warn("roleCode：{} 已存在，或为空。",roleCode);
				throw new ConstraintViolationException("resourceCode.isExit",null);
			};
		}
		return this.frameRoleRepository.save(entity);
	}

	@Override
	public FrameRole findByRoleCode(String roleCode) {
		return this.frameRoleRepository.findByRoleCode(roleCode);
	}
	@Override
	public boolean existsRoleCode(String roleCode) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("roleCode", Operator.EQ, roleCode));
		Specification<FrameRole> spec = DynamicSpecifications.bySearchFilter(filters, FrameRole.class);
		long count = this.frameRoleRepository.count(spec);
		if(count>0){
			return true;
		}
		return false;
	}

}
