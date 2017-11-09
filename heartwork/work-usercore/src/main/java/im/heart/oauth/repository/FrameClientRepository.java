package im.heart.oauth.repository;

import im.heart.oauth.entity.FrameClient;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface FrameClientRepository  extends JpaRepository<FrameClient, BigInteger>  ,JpaSpecificationExecutor<FrameClient>{

	public	FrameClient findByClientId(String clientId);
	
	public	FrameClient findByClientSecret(String clientSecret);
}
