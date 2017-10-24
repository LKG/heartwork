package im.heart.security.credentials;

import im.heart.security.cache.ShiroCacheConfig;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author gg
 * @desc 密码校验器 ，防暴力破解 用户密码输入错误次数过多，自动锁定账号
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

	protected static final String CACHE_NAME = ShiroCacheConfig.passwordRetry.keyPrefix;
	protected static final Logger logger = LoggerFactory.getLogger(RetryLimitCredentialsMatcher.class);

	private Cache<Serializable, AtomicInteger> passwordRetryCache;

	private CacheManager cacheManager;
	

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		Serializable username = (Serializable) token.getPrincipal();
		logger.debug(username + ":doCredentialsMatch....."+ this.passwordRetryCache);
		if(this.passwordRetryCache==null){
			this.passwordRetryCache = this.cacheManager.getCache(CACHE_NAME);
		}
		AtomicInteger retryCount = this.passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
		}
		int count = retryCount.incrementAndGet();
		if (count >= 2) {// 需要用户输入验证码
			logger.warn(username + "密码错误>=2次");
		}
		if (count >= 4) {
			logger.warn(username + "被锁定！");
			throw new ExcessiveAttemptsException();
		}
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			this.passwordRetryCache.remove(username);
		}else{
			this.passwordRetryCache.put(username, retryCount);
		}
		return matches;
	}
}
