package im.heart.oauth.service.impl;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service()
@Transactional(propagation = Propagation.SUPPORTS)
public class OAuthIssuerImpl implements OAuthIssuer{

	@Override
	public String accessToken() throws OAuthSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String authorizationCode() throws OAuthSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refreshToken() throws OAuthSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
