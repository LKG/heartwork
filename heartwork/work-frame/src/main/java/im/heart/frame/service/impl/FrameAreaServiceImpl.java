package im.heart.frame.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.frame.entity.FrameArea;
import im.heart.frame.repository.FrameAreaRepository;
import im.heart.frame.service.FrameAreaService;
@Service(value = FrameAreaService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
@CacheConfig(cacheNames = FrameAreaServiceImpl.CACHEABLE_NAME)
public class FrameAreaServiceImpl extends CommonServiceImpl<FrameArea, BigInteger> implements FrameAreaService {

	public static final String CACHEABLE_NAME = "areas-cache";
	@Autowired
	private FrameAreaRepository frameAreaRepository;

	@Override
	public List<FrameArea> save(Iterable<FrameArea> entities) {
		return this.frameAreaRepository.save(entities);
	}
	@Cacheable(value=CACHEABLE_NAME, key="#id") 
	@Override
	public FrameArea findOne(BigInteger id) {
		return this.frameAreaRepository.findOne(id);
	}
	//清空缓存
	@CacheEvict(value=CACHEABLE_NAME, allEntries=true)
	@Override
	public FrameArea save(FrameArea entity) {
		return this.frameAreaRepository.save(entity);
	}

	@Override
	@Cacheable(value=CACHEABLE_NAME, key="#areaName") 
	public FrameArea findByName(String areaName) {
		List<FrameArea> list = this.findAreasByName(areaName);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	@Override
	@Cacheable(value=CACHEABLE_NAME, key="#areaName") 
	public List<FrameArea> findAreasByName(String areaName) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("name", Operator.LIKES, areaName));
		Specification<FrameArea> spec = DynamicSpecifications.bySearchFilter(filters, FrameArea.class);
		List<FrameArea> list = this.frameAreaRepository.findAll(spec);
		return list;
	}

	@Override
	public boolean exists(BigInteger id) {
		return this.frameAreaRepository.exists(id);
	}
	@Cacheable(value=CACHEABLE_NAME) 
	@Override
	public Page<FrameArea> findBySearchFilters(Collection<SearchFilter> filters, PageRequest pageRequest) {
		if(filters==null||filters.isEmpty()){
			return null;
		}
		Specification<FrameArea> spec = DynamicSpecifications.bySearchFilter(filters, FrameArea.class);
		return this.frameAreaRepository.findAll(spec,pageRequest);
	}
	@Cacheable(value=CACHEABLE_NAME)
	@Override
	public Page<FrameArea> findBySpecification(Specification<FrameArea> spec , PageRequest pageRequest) {
		return this.frameAreaRepository.findAll(spec,pageRequest);
	}
	@CachePut(value=CACHEABLE_NAME, key="#id")
	@Override
	public void delete(BigInteger id) {
		this.frameAreaRepository.delete(id);
	}
}
