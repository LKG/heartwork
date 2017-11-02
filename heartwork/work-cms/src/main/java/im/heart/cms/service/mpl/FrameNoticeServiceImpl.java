package im.heart.cms.service.mpl;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.cms.entity.FrameNotice;
import im.heart.cms.service.FrameNoticeService;
import im.heart.core.service.impl.CommonServiceImpl;

@Service(value = FrameNoticeService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameNoticeServiceImpl extends CommonServiceImpl<FrameNotice, BigInteger> implements FrameNoticeService {

	
}
