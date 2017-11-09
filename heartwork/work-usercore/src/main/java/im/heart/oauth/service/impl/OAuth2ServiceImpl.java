package im.heart.oauth.service.impl;

import javax.annotation.PostConstruct;

import im.heart.oauth.service.OAuth2Service;
import im.heart.oauth.service.FrameClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = OAuth2Service.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class OAuth2ServiceImpl implements OAuth2Service {
	protected static final Logger logger = LoggerFactory
			.getLogger(OAuth2ServiceImpl.class);

	private static final String codeCacheName="oAuthCode-cache";
	
	private Cache cache;
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private FrameClientService clientService;

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	@PostConstruct
	public void init() {
		if (this.cache == null) {
			logger.warn("this.cache is null create ....");
			this.cache = this.cacheManager.getCache(codeCacheName);
		}
	}

	@Override
	public void addAuthCode(String authCode, String username) {
		logger.debug("authCode:" + authCode);
		this.cache.put(authCode, username);
	}

	@Override
	public void addAccessToken(String accessToken, String username) {
		this.cache.put(accessToken, username);
	}

	@Override
	public String getUsernameByAuthCode(String authCode) {
		return (String) this.cache.get(authCode).get();
	}

	@Override
	public String getUsernameByAccessToken(String accessToken) {
		return (String) this.cache.get(accessToken).get();
	}

	@Override
	public boolean checkAuthCode(String authCode) {
		return this.cache.get(authCode) != null;
	}

	@Override
	public boolean checkAccessToken(String accessToken) {
		return this.cache.get(accessToken) != null;
	}

	@Override
	public boolean checkClientId(String clientId) {
		return this.clientService.checkClientId(clientId);
	}

	@Override
	public boolean checkClientSecret(String clientSecret) {
		return this.clientService.queryClientBySecret(clientSecret) != null;
	}

	@Override
	public long getExpireIn() {
		return 3600L;
	}

	@Override
	public boolean checkClientIdAndUri(String clientId, String uri) {
		
		return this.clientService.checkClientIdAndUri(clientId,uri);
	}

	@Override
	public boolean checkClientIdAndSecret(String clientId, String clientSecret) {
		return this.clientService.checkClientIdAndSecret(clientId, clientSecret);
	}
}
