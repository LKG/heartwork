package im.heart.cms.service;

import java.math.BigInteger;

import im.heart.cms.entity.FrameNotice;
import im.heart.core.service.CommonService;

/**
 * 
 * @功能说明：评论操作接口
 * @作者 LKG
 */
public interface   FrameNoticeService extends CommonService<FrameNotice, BigInteger>{
	
	public static final String BEAN_NAME = "frameNoticeService";
	
}
