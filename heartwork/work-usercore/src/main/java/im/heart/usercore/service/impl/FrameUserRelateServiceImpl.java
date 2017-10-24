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
import im.heart.usercore.entity.FrameUserRelate;
import im.heart.usercore.repository.FrameUserRelateRepository;
import im.heart.usercore.service.FrameUserRelateService;


@Service(value = FrameUserRelateService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameUserRelateServiceImpl extends CommonServiceImpl<FrameUserRelate, BigInteger> implements FrameUserRelateService{
	@Autowired
	private FrameUserRelateRepository frameUserRelateRepository;
	@Override
	public List<FrameUserRelate> save(Iterable<FrameUserRelate> entities) {
		return this.frameUserRelateRepository.save(entities);
	}

	@Override
	public FrameUserRelate save(FrameUserRelate entitie) {
		return this.frameUserRelateRepository.save(entitie);
	}
	

	@Override
	public List<FrameUserRelate> findByOrgId(BigInteger userId) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("userId", SearchFilter.Operator.EQ, userId));
		Specification<FrameUserRelate> spec = DynamicSpecifications.bySearchFilter(filters, FrameUserRelate.class);
		return this.frameUserRelateRepository.findAll(spec);
	}
	@Override
	public void delete(BigInteger id) {
		this.frameUserRelateRepository.delete(id);
	}

	@Override
	public List<FrameUserRelate> findByOrgIdAndType(BigInteger userId, String relateType) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("userId", SearchFilter.Operator.EQ, userId));
		filters.add(new SearchFilter("relateType", SearchFilter.Operator.EQ, relateType));
		Specification<FrameUserRelate> spec = DynamicSpecifications.bySearchFilter(filters, FrameUserRelate.class);
		return this.frameUserRelateRepository.findAll(spec);
	}
}
