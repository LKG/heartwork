package im.heart.usercore.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.usercore.entity.FramePermission;
import im.heart.usercore.repository.FramePermissionRepository;
import im.heart.usercore.service.FramePermissionService;

/**
 * 
 * @author gg
 * @desc 权限表操作接口
 */
@Service(value = FramePermissionService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
@CacheConfig(cacheNames = "permissions-cache")
public class FramePermissionServiceImpl extends CommonServiceImpl<FramePermission, BigInteger> implements FramePermissionService{

	@Autowired
	private FramePermissionRepository framePermissionRepository;
	
	@Override
	public FramePermission findOne(BigInteger id) {	
		return this.framePermissionRepository.findOne(id);
	}
	@Cacheable()
    @Override
	public List<FramePermission> findAll(Specification<FramePermission> spec) {
		return this.framePermissionRepository.findAll(spec);
	}
	@Cacheable()
	@Override
	public List<FramePermission> findAll(Iterable<BigInteger> ids) {
		return this.framePermissionRepository.findAll(ids);
	}
}
