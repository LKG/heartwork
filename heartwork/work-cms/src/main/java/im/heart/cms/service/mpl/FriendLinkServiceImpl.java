package im.heart.cms.service.mpl;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.cms.entity.FriendLink;
import im.heart.cms.service.FriendLinkService;
import im.heart.core.service.impl.CommonServiceImpl;

@Service(value = FriendLinkService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FriendLinkServiceImpl extends CommonServiceImpl<FriendLink, BigInteger> implements FriendLinkService {

	
}
