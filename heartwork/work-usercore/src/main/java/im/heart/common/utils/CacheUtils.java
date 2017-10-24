package im.heart.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import im.heart.common.context.ContextManager;
import im.heart.core.CommonConst;
import im.heart.core.utils.StringUtilsEx;
import im.heart.core.utils.ValidatorUtils;

public  class CacheUtils {
	protected static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	public static final String EMAIL_CODE_CACHE_NAME = "emailCode-cache";
	
	public static final String MOBILE_CODE_CACHE_NAME = "mobileCode-cache";
	
	public static final String FINDPWD_CACHE_NAME = "findPwd-cache";
	
	public static final String CACHEMANAGER_BEAN_NAME = CommonConst.CACHEMANAGER_NAME;;
	/**
	 * 
	 * @Desc：获取缓存信息
	 * @param cacheName
	 * @param key
	 * @param value
	 * @return
	 */
	public static Object  getCacheObject(String cacheName,String key){
		Cache cache = getCacheByName(cacheName);
		if(cache!=null){
			ValueWrapper valueWrapper =cache.get(key);
			if(valueWrapper!=null){
				Object cacheCode = valueWrapper.get();
				if (cacheCode != null) {
					return cacheCode;
				}
			}
		}
		return null;
	}
	
	/**
	 * @Desc：检测输入字符串是否和缓存中的数据一致
	 * @param cacheName
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean  checkWrapper(String cacheName,String key,String value){
		Cache cache = getCacheByName(cacheName);
		ValueWrapper valueWrapper =cache.get(key);
		if(valueWrapper!=null){
			Object cacheCode = valueWrapper.get();
			if (cacheCode != null && cacheCode.toString().equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * @Desc：根据缓存名称获取缓存数据信息
	 * @param cacheName
	 * @return
	 */
	public static Cache getCacheByName(String cacheName){
		CacheManager cacheManager = (CacheManager) ContextManager.getBean(CACHEMANAGER_BEAN_NAME);
		Cache cache = cacheManager.getCache(cacheName);
		return cache;
	}
	/**
	 * 
	 * @Desc：设置缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void generatCache(String cacheName,String key,Object value){
		logger.debug("cacheName:[{}] key:[{} ,value:[{}]",cacheName,key,value);
		Cache cache = getCacheByName(cacheName);
		cache.put(key, value);
	}
	/**
	 * @Desc：根据缓存名称和key 清空对应数据
	 * @param cacheName
	 * @param key
	 */
	public static void evictCache(String cacheName,String key){
		logger.debug("cacheName:[{}] key:[{}]",cacheName,key);
		Cache cache = getCacheByName(cacheName);
		cache.evict(key);
	}
	public static void evictMobileCode(String userPhone){
		evictCache(MOBILE_CODE_CACHE_NAME,userPhone);
	}
	public static void evictEmailCode(String userPhone){
		evictCache(EMAIL_CODE_CACHE_NAME,userPhone);
	}
	/**
	 * @Desc：验证短信码是否正确
	 * @param userPhone
	 * @param phoneCode
	 * @return
	 */
	public static boolean checkMobileCode(String userPhone,String phoneCode){
		logger.debug("moblie:[{}] mobileCode:[{}]",userPhone,phoneCode);
		if (StringUtilsEx.isNotBlank(phoneCode)&&StringUtilsEx.isNotBlank(userPhone)&&ValidatorUtils.isPhone(userPhone)){	
			return checkWrapper(MOBILE_CODE_CACHE_NAME,userPhone,phoneCode);
		}
		return false;
	}
	public static void generatEmailCodeCache(String email,Object emailCode){
		generatCache(EMAIL_CODE_CACHE_NAME,email, emailCode);
	}
	
	
	public static void generateMobileCache(String phone,Object phoneCode){
		generatCache(MOBILE_CODE_CACHE_NAME,phone, phoneCode);
	}
	public static void evictEmailCache(String email){
		evictCache(MOBILE_CODE_CACHE_NAME,email);
	}
	
	public static void evictMobileCache(String phone){
		evictCache(MOBILE_CODE_CACHE_NAME,phone);
	}
	
	/**
	 * @Desc：校验邮箱
	 * @param userEmail
	 * @param emailcode
	 * @return
	 */
	public static boolean checkEmailCode(String userEmail,String emailcode){
		logger.debug("email:[{}] emailCode:[{}]",userEmail,emailcode);
		if (StringUtilsEx.isNotBlank(emailcode)&&StringUtilsEx.isNotBlank(userEmail)&&ValidatorUtils.isEmail(userEmail)){	
			return checkWrapper(EMAIL_CODE_CACHE_NAME,userEmail,emailcode);
		}
		return false;
	}
	
}
