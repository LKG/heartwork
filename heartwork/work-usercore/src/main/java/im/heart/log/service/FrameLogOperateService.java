package im.heart.log.service;

import java.math.BigInteger;

import im.heart.core.service.CommonService;
import im.heart.log.entity.FrameLogOperate;

public interface FrameLogOperateService extends CommonService<FrameLogOperate, BigInteger>{
	public static final String BEAN_NAME = "frameLogOperateService";
	public void optlog(FrameLogOperate frameLogOperate);
}
