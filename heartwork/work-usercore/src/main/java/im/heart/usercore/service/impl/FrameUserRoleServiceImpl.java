package im.heart.usercore.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import im.heart.core.service.ServiceException;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.usercore.entity.FrameUserRole;
import im.heart.usercore.repository.FrameUserRoleRepository;
import im.heart.usercore.service.FrameUserRoleService;
@Service(value = FrameUserRoleService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameUserRoleServiceImpl  extends CommonServiceImpl<FrameUserRole, BigInteger> implements FrameUserRoleService {

	@Autowired
	private FrameUserRoleRepository frameUserRoleRepository;
	@Override
	public List<FrameUserRole> save(Iterable<FrameUserRole> entities) {
		return this.frameUserRoleRepository.save(entities);
	}

	@Override
	public FrameUserRole save(FrameUserRole entitie) {
		return this.frameUserRoleRepository.save(entitie);
	}
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	@Override
	public void saveUserRole(BigInteger userId, String... roleCodes) throws ServiceException{
		this.frameUserRoleRepository.deleteByUserId(userId);
		if(roleCodes!=null){
			List<FrameUserRole> userRoles=Lists.newArrayList();
			for(String roleCode :roleCodes){
				FrameUserRole  userRole=new FrameUserRole();
				userRole.setUserId(userId);
				userRole.setRoleCode(roleCode);
				userRoles.add(userRole);
			}
			this.frameUserRoleRepository.save(userRoles);
		}
	}

	@Override
	public Map<String,BigInteger> findRoleCodeMapByUserId(BigInteger userId) {
		List<FrameUserRole> userRoles = this.frameUserRoleRepository.findByUserId(userId);
		Map<String,BigInteger> roleCodes=Maps.newHashMap();
		if(userRoles!=null){
			for(FrameUserRole userRole:userRoles){
				roleCodes.put(userRole.getRoleCode(), userId);
			}
		}
		return roleCodes;
	}

	
}
