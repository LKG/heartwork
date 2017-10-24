package im.heart.frame.service.impl;

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
import im.heart.frame.entity.FrameDictItem;
import im.heart.frame.repository.FrameDictItemRepository;
import im.heart.frame.service.FrameDictItemService;

@Service(value = FrameDictItemService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameDictItemServiceImpl extends CommonServiceImpl<FrameDictItem, BigInteger> implements FrameDictItemService {
	@Autowired
	private FrameDictItemRepository frameDictItemRepository;
	@Override
	public List<FrameDictItem> findByDictCode(String dictCode) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("dictCode", SearchFilter.Operator.EQ, dictCode));
		Specification<FrameDictItem> spec = DynamicSpecifications.bySearchFilter(filters, FrameDictItem.class);
		return this.frameDictItemRepository.findAll(spec);
	}
	@Override
	public List<FrameDictItem> findByDictId(BigInteger dictId) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("dictId", SearchFilter.Operator.EQ, dictId));
		Specification<FrameDictItem> spec = DynamicSpecifications.bySearchFilter(filters, FrameDictItem.class);
		return this.frameDictItemRepository.findAll(spec);
	}
	
	@Override
	public void delete(BigInteger id) {
		this.frameDictItemRepository.delete(id);
	}

}
