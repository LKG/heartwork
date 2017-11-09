package im.heart.oauth.service.impl;

import im.heart.core.enums.Status;
import im.heart.oauth.entity.FrameClient;
import im.heart.oauth.repository.FrameClientRepository;
import im.heart.oauth.service.FrameClientService;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Service(value = FrameClientService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class FrameClientServiceImpl implements FrameClientService {

	protected static final Logger logger = LoggerFactory.getLogger(FrameClientServiceImpl.class);
	@Autowired
	private FrameClientRepository frameClientRepository;
	
	@Override
	public Page<FrameClient> findAll(Specification<FrameClient> spec,
			Pageable pageable) {
		return this.frameClientRepository.findAll(spec, pageable);
	}

	@Override
	public List<FrameClient> findAll(Specification<FrameClient> spec) {
		return this.frameClientRepository.findAll(spec);
	}

	@Override
	public Long count(Specification<FrameClient> spec) {
		return this.frameClientRepository.count(spec);
	}

	@Override
	public FrameClient queryClientByClientId(String clientId) {
		return this.frameClientRepository.findByClientId(clientId);
	}

	@Override
	public FrameClient queryClientBySecret(String clientSecret) {
		return this.frameClientRepository.findByClientSecret(clientSecret);
	}

	@Override
	public FrameClient findOne(BigInteger id) {
		return this.frameClientRepository.findOne(id);
	}

	@Override
	public boolean checkClientIdAndUri(String clientId, String uri) {
		FrameClient frameClient=this.frameClientRepository.findByClientId(clientId);
		if(frameClient!=null&&Status.enabled.equals(frameClient.getStatus())){
			if(frameClient.getClientUri().equals(uri)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkClientId(String clientId) {
		FrameClient frameClient=this.frameClientRepository.findByClientId(clientId);
		if(frameClient!=null&&Status.enabled.equals(frameClient.getStatus())){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkClientIdAndSecret(String clientId, String clientSecret) {
		FrameClient frameClient=this.frameClientRepository.findByClientId(clientId);
		if(frameClient!=null&&Status.enabled.equals(frameClient.getStatus())){
			if(frameClient.getClientSecret().equals(clientSecret)){
				return true;
			}
		}
		return false;
	}

	@Override
	public FrameClient save(FrameClient entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(BigInteger id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends FrameClient> entities) {
		// TODO Auto-generated method stub
		
	}

}
