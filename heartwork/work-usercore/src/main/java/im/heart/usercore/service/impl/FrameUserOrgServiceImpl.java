package im.heart.usercore.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
import im.heart.usercore.entity.FrameUserOrg;
import im.heart.usercore.repository.FrameUserOrgRepository;
import im.heart.usercore.repository.FrameUserRepository;
import im.heart.usercore.service.FrameUserOrgService;
@Service(value = FrameUserOrgService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameUserOrgServiceImpl extends CommonServiceImpl<FrameUserOrg, BigInteger> implements FrameUserOrgService {

	@Autowired
	private FrameUserOrgRepository frameUserOrgRepository;
	@Autowired
	private FrameUserRepository frameUserRepository;
	@Override
	public List<FrameUserOrg> save(Iterable<FrameUserOrg> entities) {
		return this.frameUserOrgRepository.save(entities);
	}

	@Override
	public FrameUserOrg save(FrameUserOrg entitie) {
		return this.frameUserOrgRepository.save(entitie);
	}

	@Override
	public void delete(BigInteger id) {
		FrameUserOrg usrOrg = this.frameUserOrgRepository.findOne(id);
		if(usrOrg.getIsDefault()){
			this.frameUserRepository.updateUserDefaultOrg(usrOrg.getUserId(), null);
		}
		this.frameUserOrgRepository.delete(id);
	}
	@Override
	public List<FrameUserOrg> findByOrgId(BigInteger orgId) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("orgId", SearchFilter.Operator.EQ, orgId));
		Specification<FrameUserOrg> spec = DynamicSpecifications.bySearchFilter(filters, FrameUserOrg.class);
		return this.frameUserOrgRepository.findAll(spec);
	}
	
	
	@Override
	public List<FrameUserOrg> findByUserId(BigInteger userId) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("userId", SearchFilter.Operator.EQ, userId));
		Specification<FrameUserOrg> spec = DynamicSpecifications.bySearchFilter(filters, FrameUserOrg.class);
		 Sort sort= new Sort(Sort.Direction.DESC,"isDefault");
		return this.frameUserOrgRepository.findAll(spec, sort);
	}

	@Override
	public void updateUserDefaultOrg(BigInteger userId, BigInteger relateId, BigInteger defaultOrgId) {
		this.frameUserOrgRepository.updateUserDefaultOrg(userId,relateId);
		this.frameUserRepository.updateUserDefaultOrg(userId, defaultOrgId);
	}

	@Override
	public boolean existsUserOrg(BigInteger userId) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("userId", Operator.EQ, userId));
		Specification<FrameUserOrg> spec = DynamicSpecifications.bySearchFilter(filters, FrameUserOrg.class);
		long count = this.frameUserOrgRepository.count(spec);
		if(count>0){
			return true;
		}
		return false;
	}

	@Override
	public void setDefaultOrg(BigInteger userId, BigInteger relateId, BigInteger defaultOrgId) {
		this.frameUserOrgRepository.updateUserDefaultOrg(userId,relateId);
		this.frameUserRepository.updateUserDefaultOrg(userId, defaultOrgId);
	}
}
