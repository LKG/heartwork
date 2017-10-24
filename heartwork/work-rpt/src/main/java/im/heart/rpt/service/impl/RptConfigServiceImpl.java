package im.heart.rpt.service.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.rpt.entity.RptConfig;
import im.heart.rpt.repository.RptConfigRepository;
import im.heart.rpt.service.RptConfigService;

@Service(value = RptConfigService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class RptConfigServiceImpl extends CommonServiceImpl<RptConfig, BigInteger> implements RptConfigService{

	@Autowired
	private RptConfigRepository rptConfigRepository;

	@Override
	public RptConfig save(RptConfig entity) {
		return this.rptConfigRepository.save(entity);
	}

}
