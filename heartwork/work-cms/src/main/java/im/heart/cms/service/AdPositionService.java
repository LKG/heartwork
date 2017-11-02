package im.heart.cms.service;

import java.math.BigInteger;

import im.heart.cms.entity.AdPosition;
import im.heart.core.service.CommonService;

/**
 * 
 * @功能说明：广告位操作接口
 * @作者 LKG
 */
public interface   AdPositionService extends CommonService<AdPosition, BigInteger>{
	
	public static final String BEAN_NAME = "adPositionService";
}
