package im.heart.cms.service;

import java.math.BigInteger;

import im.heart.cms.entity.FriendLink;
import im.heart.core.service.CommonService;

/**
 * 
 * @功能说明：FriendLink操作接口
 * @作者 LKG
 */
public interface   FriendLinkService extends CommonService<FriendLink, BigInteger>{
	
	public static final String BEAN_NAME = "friendLinkService";
	
}
