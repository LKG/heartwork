package im.heart.frame.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.plugins.persistence.DynamicSpecifications;
import im.heart.core.plugins.persistence.SearchFilter;
import im.heart.core.plugins.persistence.SearchFilter.Operator;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.frame.entity.FrameTpl;
import im.heart.frame.repository.FrameTplRepository;
import im.heart.frame.service.FrameTplService;
@Service(value = FrameTplService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameTplServiceImpl extends CommonServiceImpl<FrameTpl, BigInteger> implements FrameTplService {
	protected static final Logger logger = LoggerFactory.getLogger(FrameTplServiceImpl.class);
	@Autowired
	private FrameTplRepository frameTplRepository;
	@Override
	public boolean exists(String tplCode) {
		final Collection<SearchFilter> filters = new HashSet<SearchFilter>();
		filters.add(new SearchFilter("tplCode", Operator.EQ, tplCode));
		Specification<FrameTpl> spec = DynamicSpecifications.bySearchFilter(filters, FrameTpl.class);
		long count = this.frameTplRepository.count(spec);
		if(count>0){
			return true;
		}
		return false;
	}
	
}
