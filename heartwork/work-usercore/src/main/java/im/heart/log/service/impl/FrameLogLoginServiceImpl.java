package im.heart.log.service.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import im.heart.core.plugins.ip.IPParse;
import im.heart.core.service.impl.CommonServiceImpl;
import im.heart.log.entity.FrameLogLogin;
import im.heart.log.repository.FrameLogLoginRepository;
import im.heart.log.service.FrameLogLoginService;
@Service(value = FrameLogLoginService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameLogLoginServiceImpl extends CommonServiceImpl<FrameLogLogin, BigInteger> implements FrameLogLoginService {

	@Autowired
	private FrameLogLoginRepository frameLogLoginRepository;
	@Autowired
	@Qualifier("taoBaoIP")
	private IPParse ipParse;
	@Async
	@Override
	public void loginlog(FrameLogLogin entity) {
		entity.setUserIpInfo(this.ipParse.getIpInfo(entity.getUserHost()));
		entity.setLogType(FrameLogLogin.LogType.login);
		this.frameLogLoginRepository.save(entity);
	}

}
