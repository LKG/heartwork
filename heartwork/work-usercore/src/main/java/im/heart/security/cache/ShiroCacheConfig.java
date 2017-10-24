package im.heart.security.cache;

public enum ShiroCacheConfig {
	
	passwordRetry("shiro_password_retry:", 30 * 60 * 1), 
	activesession("shiro_active_session:", 60 * 60 * 1),
	kickout("shiro_kickout_session:", 0),
	userrealm("shiro_frameUserRealm", 0);	
	ShiroCacheConfig(String keyPrefix, long expiredTime) {
		this.keyPrefix = keyPrefix;
		this.expiredTime = expiredTime;
	}
	public static final String CACHEMANAGER_BEAN_NAME = "shiroCacheManager";
	public  String keyPrefix;

	public long expiredTime;

	public long getExpiredTimeByKey(String keyPrefix){
		ShiroCacheConfig[] values = ShiroCacheConfig.values();
		for(ShiroCacheConfig value:values ){
			if(value.getKeyPrefix().equals(keyPrefix)){
				return value.getExpiredTime();
			}
		}
		return 0;
	}
	
	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

}
