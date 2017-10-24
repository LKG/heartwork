package im.heart.usercore.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.usercore.entity.FrameOrgRelate;
import im.heart.usercore.repository.FrameOrgRelateRepository;
import im.heart.usercore.service.FrameOrgRelateService;


@Service(value = FrameOrgRelateService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameOrgRelateServiceImpl extends CommonServiceImpl<FrameOrgRelate, BigInteger> implements FrameOrgRelateService{
	@Autowired
	private FrameOrgRelateRepository frameOrgRelateRepository;
	@Override
	public List<FrameOrgRelate> save(Iterable<FrameOrgRelate> entities) {
		return this.frameOrgRelateRepository.save(entities);
	}

	@Override
	public FrameOrgRelate save(FrameOrgRelate entitie) {
		return this.frameOrgRelateRepository.save(entitie);
	}
	

	@Override
	public List<FrameOrgRelate> findByOrgId(BigInteger orgId) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("orgId", SearchFilter.Operator.EQ, orgId));
		Specification<FrameOrgRelate> spec = DynamicSpecifications.bySearchFilter(filters, FrameOrgRelate.class);
		return this.frameOrgRelateRepository.findAll(spec);
	}
	@Override
	public void delete(BigInteger id) {
		this.frameOrgRelateRepository.delete(id);
	}

	@Override
	public List<FrameOrgRelate> findByOrgIdAndType(BigInteger orgId, String relateType) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("orgId", SearchFilter.Operator.EQ, orgId));
		filters.add(new SearchFilter("relateType", SearchFilter.Operator.EQ, relateType));
		Specification<FrameOrgRelate> spec = DynamicSpecifications.bySearchFilter(filters, FrameOrgRelate.class);
		return this.frameOrgRelateRepository.findAll(spec);
	}
}
