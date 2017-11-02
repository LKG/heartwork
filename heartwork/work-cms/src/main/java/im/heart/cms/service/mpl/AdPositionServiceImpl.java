package im.heart.cms.service.mpl;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.cms.entity.AdPosition;
import im.heart.cms.service.AdPositionService;
import im.heart.core.service.impl.CommonServiceImpl;

@Service(value = AdPositionService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class AdPositionServiceImpl extends CommonServiceImpl<AdPosition, BigInteger> implements AdPositionService {
	

}
